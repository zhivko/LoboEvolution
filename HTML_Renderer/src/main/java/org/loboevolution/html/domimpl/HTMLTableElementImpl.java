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
 * Created on Dec 3, 2005
 */
package org.loboevolution.html.domimpl;

import org.loboevolution.html.domfilter.ElementTableAttributeFilter;
import org.loboevolution.html.renderstate.RenderState;
import org.loboevolution.html.renderstate.TableRenderState;
import org.loboevolution.html.style.AbstractCSSProperties;
import org.loboevolution.html.style.HtmlLength;
import org.loboevolution.html.style.HtmlValues;
import org.loboevolution.util.Nodes;
import org.loboevolution.w3c.html.HTMLCollection;
import org.loboevolution.w3c.html.HTMLElement;
import org.loboevolution.w3c.html.HTMLTableCaptionElement;
import org.loboevolution.w3c.html.HTMLTableElement;
import org.loboevolution.w3c.html.HTMLTableSectionElement;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * The Class HTMLTableElementImpl.
 */
public class HTMLTableElementImpl extends HTMLAbstractUIElement implements HTMLTableElement {
	
	/** The caption. */
	private HTMLTableCaptionElement caption;
	
	/** The thead. */
	private HTMLTableSectionElement thead;

	/** The tfoot. */
	private HTMLTableSectionElement tfoot;

	/**
	 * Instantiates a new HTML table element impl.
	 */
	public HTMLTableElementImpl() {
		super(TABLE);
	}

	/**
	 * Instantiates a new HTML table element impl.
	 *
	 * @param name
	 *            the name
	 */
	public HTMLTableElementImpl(String name) {
		super(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLTableElement#getCaption()
	 */
	@Override
	public HTMLTableCaptionElement getCaption() {
		return this.caption;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.w3c.html.HTMLTableElement#setCaption(org.loboevolution.html
	 * .w3c.HTMLTableCaptionElement)
	 */
	@Override
	public void setCaption(HTMLTableCaptionElement caption) throws DOMException {
		this.caption = caption;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLTableElement#getTHead()
	 */
	@Override
	public HTMLTableSectionElement getTHead() {
		return this.thead;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.w3c.html.HTMLTableElement#setTHead(org.loboevolution.w3c.
	 * html .HTMLTableSectionElement)
	 */
	@Override
	public void setTHead(HTMLTableSectionElement tHead) throws DOMException {
		this.thead = tHead;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLTableElement#getTFoot()
	 */
	@Override
	public HTMLTableSectionElement getTFoot() {
		return this.tfoot;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.w3c.html.HTMLTableElement#setTFoot(org.loboevolution.w3c.
	 * html .HTMLTableSectionElement)
	 */
	@Override
	public void setTFoot(HTMLTableSectionElement tFoot) throws DOMException {
		this.tfoot = tFoot;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLTableElement#getRows()
	 */
	@Override
	public HTMLCollection getRows() {
		return new HTMLCollectionImpl(this, new ElementTableAttributeFilter(TR));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLTableElement#getTBodies()
	 */
	@Override
	public HTMLCollection getTBodies() {
		return new HTMLCollectionImpl(this, new ElementTableAttributeFilter(TBODY));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLTableElement#getAlign()
	 */
	@Override
	public String getAlign() {
		return this.getAttribute(ALIGN);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLTableElement#setAlign(java.lang.String)
	 */
	@Override
	public void setAlign(String align) {
		this.setAttribute(ALIGN, align);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLTableElement#getBgColor()
	 */
	@Override
	public String getBgColor() {
		return this.getAttribute(BGCOLOR);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.w3c.html.HTMLTableElement#setBgColor(java.lang.String)
	 */
	@Override
	public void setBgColor(String bgColor) {
		this.setAttribute(BGCOLOR, bgColor);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLTableElement#getBorder()
	 */
	@Override
	public String getBorder() {
		return this.getAttribute(BORDER);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.w3c.html.HTMLTableElement#setBorder(java.lang.String)
	 */
	@Override
	public void setBorder(String border) {
		this.setAttribute(BORDER, border);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLTableElement#getCellPadding()
	 */
	@Override
	public String getCellPadding() {
		return this.getAttribute(CELLPADDING);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLTableElement#setCellPadding(java.lang.
	 * String)
	 */
	@Override
	public void setCellPadding(String cellPadding) {
		this.setAttribute(CELLPADDING, cellPadding);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLTableElement#getCellSpacing()
	 */
	@Override
	public String getCellSpacing() {
		return this.getAttribute(CELLSPACING);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLTableElement#setCellSpacing(java.lang.
	 * String)
	 */
	@Override
	public void setCellSpacing(String cellSpacing) {
		this.setAttribute(CELLSPACING, cellSpacing);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLTableElement#getFrame()
	 */
	@Override
	public String getFrame() {
		return this.getAttribute(FRAME_ATTR);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLTableElement#setFrame(java.lang.String)
	 */
	@Override
	public void setFrame(String frame) {
		this.setAttribute(FRAME_ATTR, frame);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLTableElement#getRules()
	 */
	@Override
	public String getRules() {
		return this.getAttribute(RULES);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLTableElement#setRules(java.lang.String)
	 */
	@Override
	public void setRules(String rules) {
		this.setAttribute(RULES, rules);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLTableElement#getSummary()
	 */
	@Override
	public String getSummary() {
		return this.getAttribute(SUMMARY);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.w3c.html.HTMLTableElement#setSummary(java.lang.String)
	 */
	@Override
	public void setSummary(String summary) {
		this.setAttribute(SUMMARY, summary);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLTableElement#getWidth()
	 */
	@Override
	public String getWidth() {
		return this.getAttribute(WIDTH);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLTableElement#setWidth(java.lang.String)
	 */
	@Override
	public void setWidth(String width) {
		this.setAttribute(WIDTH, width);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.render.RenderableContext#getHeightLength()
	 */
	/**
	 * Gets the height length.
	 *
	 * @param availHeight
	 *            the avail height
	 * @return the height length
	 */
	public HtmlLength getHeightLength(int availHeight) {
		try {
			AbstractCSSProperties props = this.getCurrentStyle();
			String heightText = props == null ? null : props.getHeight();
			if (heightText == null) {
				return new HtmlLength(this.getAttribute(HEIGHT));
			} else {
				return new HtmlLength(HtmlValues.getPixelSize(heightText, this.getRenderState(), 0, availHeight));
			}
		} catch (Exception err) {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.render.RenderableContext#getWidthLength()
	 */
	/**
	 * Gets the width length.
	 *
	 * @param availWidth
	 *            the avail width
	 * @return the width length
	 */
	public HtmlLength getWidthLength(int availWidth) {
		try {
			AbstractCSSProperties props = this.getCurrentStyle();
			String widthText = props == null ? null : props.getWidth();
			if (widthText == null) {
				return new HtmlLength(this.getAttribute(WIDTH));
			} else {
				return new HtmlLength(HtmlValues.getPixelSize(widthText, this.getRenderState(), 0, availWidth));
			}
		} catch (Exception err) {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLTableElement#createTHead()
	 */
	@Override
	public HTMLElement createTHead() {
		Document doc = this.document;
		return doc == null ? null : (HTMLElement) doc.createElement(THEAD);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLTableElement#deleteTHead()
	 */
	@Override
	public void deleteTHead() {
		this.removeChildren(new ElementTableAttributeFilter(THEAD));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLTableElement#createTFoot()
	 */
	@Override
	public HTMLElement createTFoot() {
		Document doc = this.document;
		return doc == null ? null : (HTMLElement) doc.createElement(TFOOT);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLTableElement#deleteTFoot()
	 */
	@Override
	public void deleteTFoot() {
		this.removeChildren(new ElementTableAttributeFilter(TFOOT));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLTableElement#createCaption()
	 */
	@Override
	public HTMLElement createCaption() {
		Document doc = this.document;
		return doc == null ? null : (HTMLElement) doc.createElement(CAPTION);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLTableElement#deleteCaption()
	 */
	@Override
	public void deleteCaption() {
		this.removeChildren(new ElementTableAttributeFilter(CAPTION));
	}

	/**
	 * Inserts a row at the index given. If <code>index</code> is
	 * <code>-1</code>, the row is appended as the last row.
	 *
	 * @param index
	 *            the index
	 * @return the HTML element
	 * @throws DOMException
	 *             the DOM exception
	 */
	@Override
	public HTMLElement insertRow(int index) throws DOMException {
		final org.w3c.dom.Document doc = this.document;
		if (doc == null) {
			throw new DOMException(DOMException.WRONG_DOCUMENT_ERR, "Orphan element");
		}
		final HTMLElement rowElement = (HTMLElement) doc.createElement("TR");
		if (index == -1) {
			appendChild(rowElement);
			return rowElement;
		}
		int trcount = 0;
		for (Node node : Nodes.iterable(nodeList)) {
			if ("TR".equalsIgnoreCase(node.getNodeName())) {
				if (trcount == index) {
					insertAt(rowElement, nodeList.indexOf(node));
					return rowElement;
				}
				trcount++;
			}
		}
		appendChild(rowElement);
		return rowElement;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLTableElement#deleteRow(int)
	 */
	@Override
	public void deleteRow(int index) throws DOMException {
		int trcount = 0;
		for (Node node : Nodes.iterable(nodeList)) {
			if ("TR".equalsIgnoreCase(node.getNodeName())) {
				if (trcount == index) {
					removeChildAt(nodeList.indexOf(node));
					return;
				}
				trcount++;
			}
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLTableElement#insertRow()
	 */
	@Override
	public HTMLElement insertRow() {
		return insertRow(-1);
	}

	@Override
	public HTMLElement createTBody() {
		Document doc = this.document;
		return doc == null ? null : (HTMLElement) doc.createElement(TBODY);
	}

	@Override
	public HTMLElement deleteTBody() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.domimpl.HTMLElementImpl#createRenderState(org.
	 * loboevolution .html.renderstate.RenderState)
	 */
	@Override
	protected RenderState createRenderState(RenderState prevRenderState) {
		RenderState tmpRenderState = prevRenderState;
		return new TableRenderState(tmpRenderState, this);
	}
}