/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2018 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    

    Contact info: ivan.difrancesco@yahoo.it
 */
/*
 * Created on Sep 3, 2005
 */
package org.loboevolution.html.domimpl;

import java.awt.Color;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.loboevolution.arraylist.ArrayUtilities;
import org.loboevolution.font.LAFSettings;
import org.loboevolution.html.FormInput;
import org.loboevolution.html.dombl.UINode;
import org.loboevolution.html.parser.HtmlParser;
import org.loboevolution.html.renderstate.ColorRenderState;
import org.loboevolution.html.renderstate.RenderState;
import org.loboevolution.html.renderstate.StyleSheetRenderState;
import org.loboevolution.html.style.AbstractCSSProperties;
import org.loboevolution.html.style.CSSPropertiesContext;
import org.loboevolution.html.style.ComputedCSSProperties;
import org.loboevolution.html.style.HtmlValues;
import org.loboevolution.html.style.LocalCSSProperties;
import org.loboevolution.html.style.StyleSheetAggregator;
import org.loboevolution.html.style.selectors.AttributeSelector;
import org.loboevolution.util.Nodes;
import org.loboevolution.util.Strings;
import org.loboevolution.w3c.html.DOMSettableTokenList;
import org.loboevolution.w3c.html.DOMStringMap;
import org.loboevolution.w3c.html.DOMTokenList;
import org.loboevolution.w3c.html.HTMLElement;
import org.loboevolution.w3c.html.HTMLPropertiesCollection;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.css.CSSStyleDeclaration;

import com.gargoylesoftware.css.dom.CSSStyleDeclarationImpl;
import com.gargoylesoftware.css.parser.CSSOMParser;
import com.gargoylesoftware.css.parser.InputSource;
import com.gargoylesoftware.css.parser.javacc.CSS3Parser;

/**
 * The Class HTMLElementImpl.
 */
public class HTMLElementImpl extends DOMElementImpl implements HTMLElement, CSSPropertiesContext {

	/** The current style declaration state. */
	private volatile AbstractCSSProperties currentStyleDeclarationState;

	/** The local style declaration state. */
	private volatile AbstractCSSProperties localStyleDeclarationState;
	
	/** The computed styles. */
	private Map<String, AbstractCSSProperties> computedStyles;

	/** The is mouse over. */
	private boolean isMouseOver = false;

	/** The is hover style. */
	private Boolean isHoverStyle = null;

	/** The has hover style by element. */
	private Map<HTMLElementImpl, Boolean> hasHoverStyleByElement = null;
	
	/**
	 * Instantiates a new HTML element impl.
	 *
	 * @param name
	 *            the name
	 */
	public HTMLElementImpl(String name) {
		super(name);
	}

	/**
	 * Forget local style.
	 */
	protected final void forgetLocalStyle() {
		synchronized (this) {
			this.currentStyleDeclarationState = null;
			this.localStyleDeclarationState = null;
			this.computedStyles = null;
		}
	}

	/**
	 * Forget style.
	 *
	 * @param deep
	 *            the deep
	 */
	protected final void forgetStyle(boolean deep) {
		synchronized (this) {
			this.currentStyleDeclarationState = null;
			this.computedStyles = null;
			this.isHoverStyle = null;
			this.hasHoverStyleByElement = null;
			if (deep) {
				for (Node node : Nodes.iterable(nodeList)) {
					if (node instanceof HTMLElementImpl) {
						((HTMLElementImpl) node).forgetStyle(deep);
					}
				}
			}
		}
	}

	/**
	 * Gets the current style.
	 *
	 * @return the current style
	 */
	public AbstractCSSProperties getCurrentStyle() {
		AbstractCSSProperties sds;
		synchronized (this) {
			sds = this.currentStyleDeclarationState;
			if (sds != null) {
				return sds;
			}
		}
		// Can't do the following in synchronized block (reverse locking order
		// with document).
		// First, add declarations from stylesheet
		sds = this.createDefaultStyleSheet();
		sds = this.addStyleSheetDeclarations(sds, this.getPseudoNames());
		// Now add local style if any.
		AbstractCSSProperties localStyle = this.getStyle();
		if (sds == null) {
			sds = new ComputedCSSProperties(this);
			sds.setLocalStyleProperties(localStyle);
		} else {
			sds.setLocalStyleProperties(localStyle);
		}
		synchronized (this) {
			// Check if style properties were set while outside
			// the synchronized block (can happen).
			AbstractCSSProperties setProps = this.currentStyleDeclarationState;
			if (setProps != null) {
				return setProps;
			}
			this.currentStyleDeclarationState = sds;
			return sds;
		}
	}

	/**
	 * Gets the local style object associated with the element. The properties
	 * object returned only includes properties from the local style attribute.
	 * It may return null only if the type of element does not handle
	 * stylesheets.
	 *
	 * @return the style
	 */
	@Override
	public AbstractCSSProperties getStyle() {
		AbstractCSSProperties sds;
		synchronized (this) {
			sds = this.localStyleDeclarationState;
			if (sds != null) {
				return sds;
			}
			sds = new LocalCSSProperties(this);
			// Add any declarations in style attribute (last takes precedence).
			final String style = getAttribute("style");
			if (Strings.isNotBlank(style)) {
				final CSSOMParser parser = new CSSOMParser(new CSS3Parser());
				try {
					final CSSStyleDeclarationImpl sd = parser.parseStyleDeclaration(style);
					sds.addStyleDeclaration(sd);
				} catch (final Exception err) {
					final String id = getId();
					final String withId = id == null ? "" : " with ID '" + id + "'";
					logger.warn("Unable to parse style attribute value for element " + getTagName() + withId + " in "
							+ getDocumentURL() + ".", err);
				}
			}
			this.localStyleDeclarationState = sds;
		}
		return sds;
	}

	/**
	 * Creates the default style sheet.
	 *
	 * @return the abstract cs s2 properties
	 */
	protected AbstractCSSProperties createDefaultStyleSheet() {
		ComputedCSSProperties css = new ComputedCSSProperties(this);
		css.internalSetLC("font-size", String.valueOf((int) new LAFSettings().getIstance().getFontSize()) + "px");
		return css;
	}

	/**
	 * Gets the computed style.
	 *
	 * @param pseudoElement
	 *            the pseudo element
	 * @return the computed style
	 */
	public AbstractCSSProperties getComputedStyle(String pseudoElement) {
		String tmpElement = pseudoElement;
		if (tmpElement == null) {
			tmpElement = "";
		}
		synchronized (this) {
			Map<String, AbstractCSSProperties> cs = this.computedStyles;
			if (cs != null) {
				AbstractCSSProperties sds = cs.get(tmpElement);
				if (sds != null) {
					return sds;
				}
			}
		}
		// Can't do the following in synchronized block (reverse locking order
		// with document).
		// First, add declarations from stylesheet
		Set<String> pes = Strings.isBlank(tmpElement) ? null : Collections.singleton(tmpElement);
		AbstractCSSProperties sds = this.createDefaultStyleSheet();
		sds = this.addStyleSheetDeclarations(sds, pes);
		// Now add local style if any.
		AbstractCSSProperties localStyle = this.getStyle();
		if (sds == null) {
			sds = new ComputedCSSProperties(this);
			sds.setLocalStyleProperties(localStyle);
		} else {
			sds.setLocalStyleProperties(localStyle);
		}
		synchronized (this) {
			// Check if style properties were set while outside
			// the synchronized block (can happen). We need to
			// return instance already set for consistency.
			Map<String, AbstractCSSProperties> cs = this.computedStyles;
			if (cs == null) {
				cs = new HashMap<String, AbstractCSSProperties>(2);
				this.computedStyles = cs;
			} else {
				AbstractCSSProperties sds2 = cs.get(tmpElement);
				if (sds2 != null) {
					return sds2;
				}
			}
			cs.put(tmpElement, sds);
		}
		return sds;
	}

	/**
	 * Sets the style.
	 *
	 * @param value
	 *            the new style
	 */
	public void setStyle(String value) {
		this.setAttribute(STYLE_HTML, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLElement#getClassName()
	 */
	@Override
	public String getClassName() {
		String className = this.getAttribute(CLASS);
		return className == null ? "" : className;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLElement#setClassName(java.lang.String)
	 */
	@Override
	public void setClassName(String className) {
		this.setAttribute(CLASS, className);
	}

	/**
	 * Gets the charset.
	 *
	 * @return the charset
	 */
	public String getCharset() {
		return this.getAttribute(CHARSET);
	}

	/**
	 * Sets the charset.
	 *
	 * @param charset
	 *            the new charset
	 */
	public void setCharset(String charset) {
		this.setAttribute(CHARSET, charset);
	}

	/**
	 * Gets the attribute as int.
	 *
	 * @param name
	 *            the name
	 * @param defaultValue
	 *            the default value
	 * @return the attribute as int
	 */
	protected int getAttributeAsInt(String name, int defaultValue) {
		String valueText = this.getAttribute(name);
		return HtmlValues.getPixelSize(valueText, this.getRenderState(), 0);
	}

	/**
	 * Gets the attribute as boolean.
	 *
	 * @param name
	 *            the name
	 * @return the attribute as boolean
	 */
	public boolean getAttributeAsBoolean(String name) {
		return this.getAttribute(name) != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.html.domimpl.DOMElementImpl#assignAttributeField(java.
	 * lang .String, java.lang.String)
	 */
	@Override
	protected void assignAttributeField(String normalName, String value) {
		if (!this.notificationsSuspended) {
			this.informInvalidAttibute(normalName);
		} else {
			if ("style".equals(normalName)) {
				this.forgetLocalStyle();
			}
		}
		super.assignAttributeField(normalName, value);
	}

	/**
	 * Adds style sheet declarations applicable to this element. A properties
	 * object is created if necessary when the one passed is <code>null</code>.
	 *
	 * @param style
	 *            the style
	 * @param pseudoNames
	 *            the pseudo names
	 * @return the abstract cs s2 properties
	 */
	protected final AbstractCSSProperties addStyleSheetDeclarations(AbstractCSSProperties style, Set<String> pseudoNames) {
		final Node pn = this.parentNode;
		if (pn == null) {
			// do later
			return style;
		}
		final String classNames = getClassName();
		if (classNames != null && classNames.length() != 0) {
			final String id = getId();
			final String elementName = getTagName();
			final String[] classNameArray = Strings.split(classNames);
			for (int i = classNameArray.length; --i >= 0;) {
				final String className = classNameArray[i];
				final List<CSSStyleDeclarationImpl> sds = findStyleDeclarations(elementName, id, className, pseudoNames);
				if (sds != null) {
					for (CSSStyleDeclarationImpl sd : sds) {
						if (style == null) {
							style = new ComputedCSSProperties(this);
						}
						style.addStyleDeclaration(sd);
					}
				}
			}
		} else {
			final String id = getId();
			final String elementName = getTagName();
			final List<CSSStyleDeclarationImpl> sds = findStyleDeclarations(elementName, id, null, pseudoNames);
			if (sds != null) {
				for (CSSStyleDeclarationImpl sd : sds) {
					if (style == null) {
						style = new ComputedCSSProperties(this);
					}
					style.addStyleDeclaration(sd);
				}
			}
		}
		return style;
	}
	
	/**
	 * Sets the mouse over.
	 *
	 * @param mouseOver
	 *            the new mouse over
	 */
	public void setMouseOver(boolean mouseOver) {
		if (this.isMouseOver != mouseOver) {
			// Change isMouseOver field before checking to invalidate.
			this.isMouseOver = mouseOver;
			// Check if descendents are affected (e.g. div:hover a {...} )
			this.invalidateDescendentsForHover();
			if (this.hasHoverStyle()) {
				// TODO: OPTIMIZATION: In some cases it should be much
				// better to simply invalidate the "look" of the node.
				this.informInvalid();
			}
		}
	}

	/**
	 * Invalidate descendents for hover.
	 */
	private void invalidateDescendentsForHover() {
		synchronized (this.getTreeLock()) {
			this.invalidateDescendentsForHoverImpl(this);
		}
	}

	/**
	 * Invalidate descendents for hover impl.
	 *
	 * @param ancestor
	 *            the ancestor
	 */
	private void invalidateDescendentsForHoverImpl(HTMLElementImpl ancestor) {
		for (Node node : Nodes.iterable(nodeList)) {
			if (node instanceof HTMLElementImpl) {
				final HTMLElementImpl descendent = (HTMLElementImpl) node;
				if (descendent.hasHoverStyle(ancestor)) {
					descendent.informInvalid();
				}
				descendent.invalidateDescendentsForHoverImpl(ancestor);
			}
		}
	}
	
	/**
	 * Checks for hover style.
	 *
	 * @return true, if successful
	 */
	private boolean hasHoverStyle() {
		Boolean ihs;
		synchronized (this) {
			ihs = this.isHoverStyle;
			if (ihs != null) {
				return ihs.booleanValue();
			}
		}
		HTMLDocumentImpl doc = (HTMLDocumentImpl) this.document;
		if (doc == null) {
			ihs = Boolean.FALSE;
		} else {
			StyleSheetAggregator ssa = doc.getStyleSheetAggregator();
			String id = this.getId();
			String elementName = this.getTagName();
			String classNames = this.getClassName();
			String[] classNameArray = null;
			if (Strings.isNotBlank(classNames)) {
				classNameArray = Strings.split(classNames);
			}
			ihs = Boolean
					.valueOf(ssa.affectedByPseudoNameInAncestor(this, this, elementName, id, classNameArray, "hover"));
		}
		synchronized (this) {
			this.isHoverStyle = ihs;
		}
		return ihs.booleanValue();
	}

	/**
	 * Checks for hover style.
	 *
	 * @param ancestor
	 *            the ancestor
	 * @return true, if successful
	 */
	private boolean hasHoverStyle(HTMLElementImpl ancestor) {
		Map<HTMLElementImpl, Boolean> ihs;
		synchronized (this) {
			ihs = this.hasHoverStyleByElement;
			if (ihs != null) {
				Boolean f = ihs.get(ancestor);
				if (f != null) {
					return f.booleanValue();
				}
			}
		}
		Boolean hhs;
		HTMLDocumentImpl doc = (HTMLDocumentImpl) this.document;
		if (doc == null) {
			hhs = Boolean.FALSE;
		} else {
			StyleSheetAggregator ssa = doc.getStyleSheetAggregator();
			String id = this.getId();
			String elementName = this.getTagName();
			String classNames = this.getClassName();
			String[] classNameArray = null;
			if (Strings.isNotBlank(classNames)) {
				classNameArray = Strings.split(classNames);
			}
			hhs = Boolean.valueOf(
					ssa.affectedByPseudoNameInAncestor(this, ancestor, elementName, id, classNameArray, "hover"));
		}
		synchronized (this) {
			ihs = this.hasHoverStyleByElement;
			if (ihs == null) {
				ihs = new HashMap<HTMLElementImpl, Boolean>(2);
				this.hasHoverStyleByElement = ihs;
			}
			ihs.put(ancestor, hhs);
		}
		return hhs.booleanValue();
	}

	/**
	 * Gets the pseudo names.
	 *
	 * @return the pseudo names
	 */
	public Set<String> getPseudoNames() {
		Set<String> pnset = new HashSet<String>();
		pnset.add(AttributeSelector.LAST_CHILD);
		pnset.add(AttributeSelector.LAST_OF_TYPE);
		pnset.add(AttributeSelector.FIRST_CHILD);
		pnset.add(AttributeSelector.FIRST_OF_TYPE);
		pnset.add(AttributeSelector.ONLY_CHILD);
		pnset.add(AttributeSelector.ONLY_OF_TYPE);
		pnset.add(AttributeSelector.NTH_CHILD);
		pnset.add(AttributeSelector.NTH_LAST_CHILD);
		pnset.add(AttributeSelector.NTH_OF_TYPE);
		pnset.add(AttributeSelector.NTH_LAST_OF_TYPE);
		pnset.add(AttributeSelector.HOVER);
		pnset.add(AttributeSelector.ROOT);
		pnset.add(AttributeSelector.EMPTY);
		pnset.add(AttributeSelector.LANG);
		return pnset;
	}

	/**
	 * Find style declarations.
	 *
	 * @param elementName
	 *            the element name
	 * @param id
	 *            the id
	 * @param className
	 *            the class name
	 * @param pseudoNames
	 *            the pseudo names
	 * @return the collection
	 */
	protected final List<CSSStyleDeclarationImpl> findStyleDeclarations(String elementName, String id, String className, Set<String> pseudoNames) {
		final HTMLDocumentImpl doc = (HTMLDocumentImpl) this.document;
		if (doc == null) {
			return null;
		}
		final StyleSheetAggregator ssa = doc.getStyleSheetAggregator();
		return ssa.getActiveStyleDeclarations(this, elementName, id, className, pseudoNames, getAttributes());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.domimpl.DOMNodeImpl#informInvalid()
	 */
	@Override
	public void informInvalid() {
		// This is called when an attribute or child changes.
		this.forgetStyle(false);
		super.informInvalid();
	}

	/**
	 * Inform invalid attibute.
	 *
	 * @param normalName
	 *            the normal name
	 */
	public void informInvalidAttibute(String normalName) {
		if ("style".equals(normalName)) {
			this.forgetLocalStyle();
		}
		forgetStyle(true);
		informInvalidRecursive();

	}

	private void informInvalidRecursive() {
		super.informInvalid();
		DOMNodeImpl[] nodeList = this.getChildrenArray();
		if (nodeList != null) {
			for (DOMNodeImpl n : nodeList) {
				if (n instanceof HTMLElementImpl) {
					HTMLElementImpl htmlElementImpl = (HTMLElementImpl) n;
					htmlElementImpl.informInvalidRecursive();
				}
			}
		}
	}

	/**
	 * Gets the form inputs.
	 *
	 * @return the form inputs
	 */
	protected FormInput[] getFormInputs() {
		// Override in input elements
		return null;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLElement#setInnerHTML(java.lang.String)
	 */
	@Override
	public void setInnerHTML(String newHtml) {
		final HTMLDocumentImpl document = (HTMLDocumentImpl) this.document;
		if (document == null) {
			this.warn("setInnerHTML(): Element " + this + " does not belong to a document.");
			return;
		}
		final HtmlParser parser = new HtmlParser(document.getUserAgentContext(), document);
		this.nodeList.clear();
		// Should not synchronize around parser probably.
		try {
			final Reader reader = new StringReader(newHtml);
			try {
				parser.parse(reader, this);
			} finally {
				reader.close();
			}
		} catch (final Exception thrown) {
			this.warn("setInnerHTML(): Error setting inner HTML.");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLElement#getOuterHTML()
	 */
	@Override
	public String getOuterHTML() {
		StringBuffer buffer = new StringBuffer();
		synchronized (this) {
			this.appendOuterHTMLImpl(buffer);
		}
		return buffer.toString();
	}

	/**
	 * Append outer html impl.
	 *
	 * @param buffer
	 *            the buffer
	 */
	protected void appendOuterHTMLImpl(StringBuffer buffer) {
		final String tagName = getTagName();
		buffer.append('<');
		buffer.append(tagName);
		final Map<String, String> attributes = this.attributes;
		if (attributes != null) {
			attributes.forEach((k, v) -> {
				if (v != null) {
					buffer.append(' ');
					buffer.append(k);
					buffer.append("=\"");
					buffer.append(Strings.strictHtmlEncode(v, true));
					buffer.append("\"");
				}
			});
		}
		if (nodeList.getLength() == 0) {
			buffer.append("/>");
			return;
		}
		buffer.append('>');
		appendInnerHTMLImpl(buffer);
		buffer.append("</");
		buffer.append(tagName);
		buffer.append('>');
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.domimpl.DOMNodeImpl#createRenderState(org.
	 * loboevolution .html.renderstate.RenderState)
	 */
	@Override
	protected RenderState createRenderState(RenderState prevRenderState) {
		RenderState tmpRenderState = prevRenderState;
		if (tmpRenderState.getColor() == null) {
			tmpRenderState = new ColorRenderState(tmpRenderState, Color.BLACK);
		}
		return new StyleSheetRenderState(tmpRenderState, this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLElement#getOffsetTop()
	 */
	@Override
	public int getOffsetTop() {
		UINode uiNode = this.getUINode();
		return uiNode == null ? 0 : uiNode.getBoundsRelativeToBlock().y;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLElement#getOffsetLeft()
	 */
	@Override
	public int getOffsetLeft() {
		UINode uiNode = this.getUINode();
		return uiNode == null ? 0 : uiNode.getBoundsRelativeToBlock().x;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLElement#getOffsetWidth()
	 */
	@Override
	public int getOffsetWidth() {
		UINode uiNode = this.getUINode();
		return uiNode == null ? 0 : uiNode.getBoundsRelativeToBlock().width;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLElement#getOffsetHeight()
	 */
	@Override
	public int getOffsetHeight() {
		UINode uiNode = this.getUINode();
		return uiNode == null ? 0 : uiNode.getBoundsRelativeToBlock().height;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.style.CSSPropertiesContext#getParentStyle()
	 */
	@Override
	public AbstractCSSProperties getParentStyle() {
		Object parent = this.parentNode;
		if (parent instanceof HTMLElementImpl) {
			return ((HTMLElementImpl) parent).getCurrentStyle();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.html.style.CSSPropertiesContext#getDocumentBaseURI()
	 */
	@Override
	public String getDocumentBaseURI() {
		HTMLDocumentImpl doc = (HTMLDocumentImpl) this.document;
		if (doc != null) {
			return doc.getBaseURI();
		} else {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.domimpl.DOMElementImpl#toString()
	 */
	@Override
	public String toString() {
		return super.toString() + "[currentStyle=" + this.getCurrentStyle() + "]";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLElement#setOuterHTML(java.lang.String)
	 */
	@Override
	public void setOuterHTML(String outerHTML) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.w3c.html.HTMLElement#insertAdjacentHTML(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void insertAdjacentHTML(String position, String text) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLElement#getClassList()
	 */
	@Override
	public DOMTokenList getClassList() {
		return new DOMTokenListImpl(this, this.getClassName());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLElement#getDataset()
	 */
	@Override
	public DOMStringMap getDataset() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLElement#click()
	 */
	@Override
	public void click() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLElement#focus()
	 */
	@Override
	public void focus() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLElement#blur()
	 */
	@Override
	public void blur() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLElement#getAccessKeyLabel()
	 */
	@Override
	public String getAccessKeyLabel() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLElement#getCommandType()
	 */
	@Override
	public String getCommandType() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLElement#getLabel()
	 */
	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLElement#getIcon()
	 */
	@Override
	public String getIcon() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLElement#getOffsetParent()
	 */
	@Override
	public Element getOffsetParent() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLElement#getItemRef()
	 */
	@Override
	public DOMSettableTokenList getItemRef() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLElement#setItemRef(java.lang.String)
	 */
	@Override
	public void setItemRef(String itemRef) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLElement#getItemProp()
	 */
	@Override
	public DOMSettableTokenList getItemProp() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLElement#setItemProp(java.lang.String)
	 */
	@Override
	public void setItemProp(String itemProp) {
		// TODO Auto-generated method stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLElement#getProperties()
	 */
	@Override
	public HTMLPropertiesCollection getProperties() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLElement#getItemValue()
	 */
	@Override
	public Object getItemValue() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLElement#setItemValue(java.lang.Object)
	 */
	@Override
	public void setItemValue(Object itemValue) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setHidden(boolean hidden) {
		// TODO Auto-generated method stub

	}

	@Override
	public DOMSettableTokenList getDropzone() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDropzone(String dropzone) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setSpellcheck(boolean spellcheck) {
		// TODO Auto-generated method stub

	}
}
