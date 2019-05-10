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

package org.loboevolution.html.style;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.loboevolution.html.domimpl.HTMLDocumentImpl;
import org.loboevolution.html.domimpl.HTMLElementImpl;
import org.loboevolution.html.info.SelectorInfo;
import org.loboevolution.html.info.StyleRuleInfo;
import org.loboevolution.html.style.selectors.AttributeSelector;
import org.loboevolution.html.style.selectors.SelectorMatcher;
import org.loboevolution.http.UserAgentContext;
import org.loboevolution.util.Nodes;
import org.w3c.dom.Attr;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.css.CSSImportRule;
import org.w3c.dom.css.CSSMediaRule;
import org.w3c.dom.stylesheets.MediaList;

import com.gargoylesoftware.css.dom.AbstractCSSRuleImpl;
import com.gargoylesoftware.css.dom.CSSRuleListImpl;
import com.gargoylesoftware.css.dom.CSSStyleDeclarationImpl;
import com.gargoylesoftware.css.dom.CSSStyleRuleImpl;
import com.gargoylesoftware.css.dom.CSSStyleSheetImpl;

/**
 * Aggregates all style sheets in a document. Every time a new STYLE element is
 * found, it is added to the style sheet aggreagator by means of the
 * {@link #addStyleSheet(CSSStyleSheet)} method. HTML elements have a
 * <code>style</code> object that has a list of <code>CSSStyleDeclaration</code>
 * instances. The instances inserted in that list are obtained by means of the
 * getStyleDeclarations(HTMLElementImpl, String, String, String)} method.
 */
/**
 * @author Administrator
 *
 */
public class StyleSheetAggregator {

	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(StyleSheetAggregator.class);

	/** The attribute. */
	private String attribute;

	/** The attributeValue. */
	private String attributeValue;

	/** The attributeOperator. */
	private String attributeOperator = "";

	/** The element. */
	private String htmlElement;

	/** The document. */
	private final HTMLDocumentImpl document;

	/** The class maps by element. */
	private final Map<String, Map<String, List<StyleRuleInfo>>> classMapsByElement = new HashMap<String, Map<String, List<StyleRuleInfo>>>();

	/** The attr maps by element. */
	private final Map<String, Map<String, List<StyleRuleInfo>>> attrMapsByElement = new HashMap<String, Map<String, List<StyleRuleInfo>>>();

	/** The id maps by element. */
	private final Map<String, Map<String, List<StyleRuleInfo>>> idMapsByElement = new HashMap<String, Map<String, List<StyleRuleInfo>>>();

	/** The rules by element. */
	private final Map<String, List<StyleRuleInfo>> rulesByElement = new HashMap<String, List<StyleRuleInfo>>();

	/** The pseudo element. */
	private String pseudoElement;

	private int cnt = 1;

	/**
	 * Instantiates a new style sheet aggregator.
	 *
	 * @param document
	 *            the document
	 */
	public StyleSheetAggregator(HTMLDocumentImpl document) {
		this.document = document;
	}

	/**
	 * Adds the style sheets.
	 *
	 * @param styleSheets
	 *            the style sheets
	 * @throws Exception exception
	 * @throws UnsupportedEncodingException
	 */
	public final void addStyleSheets(List<CSSStyleSheetImpl> styleSheets) throws Exception {
		for (CSSStyleSheetImpl sheet : styleSheets) {
			addStyleSheet(sheet);
		}
	}

	/**
	 * Adds the style sheet.
	 *
	 * @param styleSheet
	 *            the style sheet
	 * @throws Exception exception
	 */
	private final void addStyleSheet(CSSStyleSheetImpl styleSheet)
			throws Exception {
		CSSRuleListImpl cssRules = styleSheet.getCssRules();
		for (AbstractCSSRuleImpl rule : cssRules.getRules()) {
			addRule(styleSheet, rule);
		}
	}

	/**
	 * Adds the rule.
	 *
	 * @param styleSheet
	 *            the style sheet
	 * @param rule
	 *            the rule
	 * @throws Exceptionbexception
	 * @throws UnsupportedEncodingException
	 */
	private final void addRule(CSSStyleSheetImpl styleSheet, AbstractCSSRuleImpl rule)
			throws Exception {
		HTMLDocumentImpl document = this.document;
		if (rule instanceof CSSStyleRuleImpl) {
			CSSStyleRuleImpl sr = (CSSStyleRuleImpl) rule;
			String selectorList = sr.getSelectorText();
			String[] selectors = selectorList.split(",");
			ArrayList<SelectorMatcher> selectorMatchers = null;
			for (String commaTok : selectors) {
				selectorMatchers = new ArrayList<SelectorMatcher>();				
				String selectorPart = commaTok.toLowerCase();
				String lastSelectorText = null;
				StringTokenizer tok = new StringTokenizer(selectorPart, " \t\r\n");
				if (tok.hasMoreTokens()) {
					selectorMatchers = new ArrayList<SelectorMatcher>();
					SelectorMatcher prevSelector = null;
					while(true) {
						String token = tok.nextToken();
						if (">".equals(token)) {
							if (prevSelector != null) {
								prevSelector.setSelectorType(SelectorMatcher.PARENT);
							}
							continue;
						} else if ("+".equals(token)) {
							if (prevSelector != null) {
								prevSelector.setSelectorType(SelectorMatcher.PRECEEDING_SIBLING);
							}
							continue;
						} else if ("~".equals(token)) {
							if (prevSelector != null) {
								prevSelector.setSelectorType(SelectorMatcher.NEXT_SIBLING);
							}
							continue;
						}
						
						int colonIdx = token.indexOf(':');
						String selectorText = colonIdx == -1 ? token : token.substring(0, colonIdx);
						pseudoElement = colonIdx == -1 ? null : token.substring(colonIdx + 1);
						prevSelector = new SelectorMatcher(selectorText, pseudoElement);
						selectorMatchers.add(prevSelector);
						if (!tok.hasMoreTokens()) {
							lastSelectorText = selectorText;
							break;
						}
					}
				}

				if (lastSelectorText != null) {
					int dotIdx = lastSelectorText.indexOf('.');
					if (dotIdx != -1) {
						String elemtl = lastSelectorText.substring(0, dotIdx);
						String classtl = lastSelectorText.substring(dotIdx + 1);
						this.addClassRule(elemtl, classtl, sr, selectorMatchers);
					} else {
						int poundIdx = lastSelectorText.indexOf('#');
						if (poundIdx != -1) {
							String elemtl = lastSelectorText.substring(0, poundIdx);
							String idtl = lastSelectorText.substring(poundIdx + 1);
							this.addIdRule(elemtl, idtl, sr, selectorMatchers);
						} else {
							String elemtl = lastSelectorText;
							this.addElementRule(elemtl, sr, selectorMatchers);
						}
					}
				}
			}

			if (selectorList.contains("[") && selectorList.endsWith("]")) {
				SelectorMatcher sm = new SelectorMatcher();
				AttributeSelector am = new AttributeSelector();
				String selector = am.getAttributeSelector(selectorList.replace("\"", ""));
				attributeOperator = sm.getOperator(selector);
				SelectorInfo si = am.getSelector(selector, attributeOperator);
				attribute = si.getAttribute();
				attributeValue = si.getAttributeValue();
				int parenthesis = selectorList.indexOf('[');
				htmlElement = selectorList.substring(0, parenthesis);
				this.addAttributeRule(htmlElement, attributeValue, sr, selectorMatchers);
			}
		} else if (rule instanceof CSSImportRule) {
			UserAgentContext uacontext = document.getUserAgentContext();
			if (uacontext.isExternalCSSEnabled()) {
				CSSImportRule importRule = (CSSImportRule) rule;
				if (CSSUtilities.matchesMedia(importRule.getMedia(), uacontext)) {
					String href = importRule.getHref();
					try {
						CSSStyleSheetImpl sheet = CSSUtilities.parse(href, document);
						if (sheet != null) {
							this.addStyleSheet(sheet);
						}
					} catch (Exception err) {
						logger.error("Unable to parse CSS. URI=[" + href + "]." + err);
					}
				}
			}
		} else if (rule instanceof CSSMediaRule) {
			final CSSMediaRule mrule = (CSSMediaRule) rule;
			final MediaList mediaList = mrule.getMedia();
			if (CSSUtilities.matchesMedia(mediaList, document.getUserAgentContext())) {
				final CSSRuleListImpl ruleList = (CSSRuleListImpl) mrule.getCssRules();
				for (AbstractCSSRuleImpl subRule : ruleList.getRules()) {
					addRule(styleSheet, subRule);
				}
			}
		}
	}

	/**
	 * Adds the class rule.
	 *
	 * @param elemtl
	 *            the elemtl
	 * @param classtl
	 *            the classtl
	 * @param styleRule
	 *            the style rule
	 * @param ancestorSelectors
	 *            the ancestor selectors
	 */
	private final void addClassRule(String elemtl, String classtl, CSSStyleRuleImpl styleRule,
			ArrayList<SelectorMatcher> ancestorSelectors) {

		if (elemtl == null || "".equals(elemtl)) {
			elemtl = "*";
		}

		Map<String, List<StyleRuleInfo>> classMap = this.classMapsByElement.get(elemtl);
		if (classMap == null) {
			classMap = new HashMap<String, List<StyleRuleInfo>>();
			this.classMapsByElement.put(elemtl, classMap);
		}
		List<StyleRuleInfo> rules = classMap.get(classtl);
		if (rules == null) {
			rules = new LinkedList<StyleRuleInfo>();
			classMap.put(classtl, rules);
		}
		rules.add(new StyleRuleInfo(ancestorSelectors, styleRule));
	}

	/**
	 * Adds the attribute rule.
	 *
	 * @param elemtl
	 *            the elemtl
	 * @param classtl
	 *            the classtl
	 * @param styleRule
	 *            the style rule
	 * @param ancestorSelectors
	 *            the ancestor selectors
	 */
	private final void addAttributeRule(String elemtl, String attrtl, CSSStyleRuleImpl styleRule,
			ArrayList<SelectorMatcher> ancestorSelectors) {

		if (elemtl == null || "".equals(elemtl)) {
			elemtl = "*";
		}

		Map<String, List<StyleRuleInfo>> attrMap = this.attrMapsByElement.get(elemtl);
		if (attrMap == null) {
			attrMap = new HashMap<String, List<StyleRuleInfo>>();
			this.attrMapsByElement.put(elemtl, attrMap);
		}
		List<StyleRuleInfo> rules = attrMap.get(attrtl);
		if (rules == null) {
			rules = new LinkedList<StyleRuleInfo>();
			attrMap.put(attrtl, rules);
		}
		rules.add(new StyleRuleInfo(ancestorSelectors, styleRule));
	}

	/**
	 * Adds the id rule.
	 *
	 * @param elemtl
	 *            the elemtl
	 * @param idtl
	 *            the idtl
	 * @param styleRule
	 *            the style rule
	 * @param ancestorSelectors
	 *            the ancestor selectors
	 */
	private final void addIdRule(String elemtl, String idtl, CSSStyleRuleImpl styleRule,
			ArrayList<SelectorMatcher> ancestorSelectors) {

		if (elemtl == null || "".equals(elemtl)) {
			elemtl = "*";
		}

		Map<String, List<StyleRuleInfo>> idsMap = this.idMapsByElement.get(elemtl);
		if (idsMap == null) {
			idsMap = new HashMap<String, List<StyleRuleInfo>>();
			this.idMapsByElement.put(elemtl, idsMap);
		}
		List<StyleRuleInfo> rules = idsMap.get(idtl);
		if (rules == null) {
			rules = new LinkedList<StyleRuleInfo>();
			idsMap.put(idtl, rules);
		}
		rules.add(new StyleRuleInfo(ancestorSelectors, styleRule));
	}

	/**
	 * Adds the element rule.
	 *
	 * @param elemtl
	 *            the elemtl
	 * @param styleRule
	 *            the style rule
	 * @param ancestorSelectors
	 *            the ancestor selectors
	 */
	private final void addElementRule(String elemtl, CSSStyleRuleImpl styleRule,
			ArrayList<SelectorMatcher> ancestorSelectors) {

		if (elemtl == null || "".equals(elemtl)) {
			elemtl = "*";
		}

		List<StyleRuleInfo> rules = this.rulesByElement.get(elemtl);
		if (rules == null) {
			rules = new LinkedList<StyleRuleInfo>();
			this.rulesByElement.put(elemtl, rules);
		}
		rules.add(new StyleRuleInfo(ancestorSelectors, styleRule));
	}

	/**
	 * Gets the active style declarations.
	 *
	 * @param element
	 *            the element
	 * @param elementName
	 *            the element name
	 * @param elementId
	 *            the element id
	 * @param className
	 *            the class name
	 * @param pseudoNames
	 *            the pseudo names
	 * @return the active style declarations
	 */
	public final List<CSSStyleDeclarationImpl> getActiveStyleDeclarations(HTMLElementImpl element, String elementName,
			String elementId, String className, Set<String> pseudoNames, NamedNodeMap attributes) {

		List<CSSStyleDeclarationImpl> styleDeclarations = null;
		String elementTL = elementName.toLowerCase();
		List<StyleRuleInfo> elementRules = this.rulesByElement.get(elementTL);
		if (elementRules != null) {
			AttributeSelector am = new AttributeSelector();
			String psElement = pseudoElement;
			if (psElement != null && psElement.contains("(")) {
				psElement = psElement.substring(0, psElement.indexOf('('));
			}

			int c = cnt++;
			for (StyleRuleInfo styleRuleInfo : elementRules) {
				for (SelectorMatcher selectorMatcher : styleRuleInfo.getAncestorSelectors()) {
				boolean matches = am.matchesPseudoClassSelector(selectorMatcher.getPseudoElement(), element, c);
					boolean contains = element.getPseudoNames().contains(psElement);
					if ((matches && contains) || (!matches && !contains)) {
						styleDeclarations = putStyleDeclarations(elementRules, styleDeclarations, element, pseudoNames);
					}
				}
			}
		}
		elementRules = this.rulesByElement.get("*");
		if (elementRules != null) {
			styleDeclarations = putStyleDeclarations(elementRules, styleDeclarations, element, pseudoNames);
		}

		if (className != null) {
			String classNameTL = className.toLowerCase();
			Map<String, List<StyleRuleInfo>> classMaps = this.classMapsByElement.get(elementTL);
			if (classMaps != null) {
				List<StyleRuleInfo> classRules = classMaps.get(classNameTL);
				if (classRules != null) {
					styleDeclarations = putStyleDeclarations(classRules, styleDeclarations, element, pseudoNames);
				}
			}
			classMaps = this.classMapsByElement.get("*");
			if (classMaps != null) {
				List<StyleRuleInfo> classRules = classMaps.get(classNameTL);
				if (classRules != null) {
					styleDeclarations = putStyleDeclarations(classRules, styleDeclarations, element, pseudoNames);
				}
			}
		}
		if (elementId != null) {
			Map<String, List<StyleRuleInfo>> idMaps = this.idMapsByElement.get(elementTL);
			if (idMaps != null) {
				String elementIdTL = elementId.toLowerCase();
				List<StyleRuleInfo> idRules = idMaps.get(elementIdTL);
				if (idRules != null) {
					styleDeclarations = putStyleDeclarations(idRules, styleDeclarations, element, pseudoNames);
				}
			}
			idMaps = this.idMapsByElement.get("*");
			if (idMaps != null) {
				String elementIdTL = elementId.toLowerCase();
				List<StyleRuleInfo> idRules = idMaps.get(elementIdTL);
				if (idRules != null) {
					styleDeclarations = putStyleDeclarations(idRules, styleDeclarations, element, pseudoNames);
				}
			}
		}

		if (attributes != null && attributes.getLength() > 0) {
			for (Attr attr : Nodes.iterable(attributes)) {
				if (isAttributeOperator(attr, element)) {
					Map<String, List<StyleRuleInfo>> classMaps = this.attrMapsByElement.get(htmlElement);
					if (classMaps != null) {
						List<StyleRuleInfo> attrRules = classMaps.get(attributeValue);
						if (attrRules != null) {
							styleDeclarations = putStyleDeclarations(attrRules, styleDeclarations, element,
									pseudoNames);
						}
					}
				}
			}
		}
		return styleDeclarations;
	}

	/**
	 * Affected by pseudo name in ancestor.
	 *
	 * @param element
	 *            the element
	 * @param ancestor
	 *            the ancestor
	 * @param elementName
	 *            the element name
	 * @param elementId
	 *            the element id
	 * @param classArray
	 *            the class array
	 * @param pseudoName
	 *            the pseudo name
	 * @return true, if successful
	 */
	public final boolean affectedByPseudoNameInAncestor(HTMLElementImpl element, HTMLElementImpl ancestor,
			String elementName, String elementId, String[] classArray, String pseudoName) {
		String elementTL = elementName.toLowerCase();
		List<StyleRuleInfo> elementRules = this.rulesByElement.get(elementTL);
		if (elementRules != null) {
			return isAffectedByPseudoNameInAncestor(elementRules, ancestor, element, pseudoName);
		}
		elementRules = this.rulesByElement.get("*");
		if (elementRules != null) {
			return isAffectedByPseudoNameInAncestor(elementRules, ancestor, element, pseudoName);
		}
		if (classArray != null) {
			for (String className : classArray) {
				String classNameTL = className.toLowerCase();
				Map<String, List<StyleRuleInfo>> classMaps = this.classMapsByElement.get(elementTL);
				if (classMaps != null) {
					List<StyleRuleInfo> classRules = classMaps.get(classNameTL);
					if (classRules != null) {
						return isAffectedByPseudoNameInAncestor(elementRules, ancestor, element, pseudoName);
					}
				}
				classMaps = this.classMapsByElement.get("*");
				if (classMaps != null) {
					List<StyleRuleInfo> classRules = classMaps.get(classNameTL);
					if (classRules != null) {
						return isAffectedByPseudoNameInAncestor(elementRules, ancestor, element, pseudoName);
					}
				}
			}
		}
		if (elementId != null) {
			Map<String, List<StyleRuleInfo>> idMaps = this.idMapsByElement.get(elementTL);
			if (idMaps != null) {
				String elementIdTL = elementId.toLowerCase();
				List<StyleRuleInfo> idRules = idMaps.get(elementIdTL);
				if (idRules != null) {
					return isAffectedByPseudoNameInAncestor(elementRules, ancestor, element, pseudoName);
				}
			}
			idMaps = this.idMapsByElement.get("*");
			if (idMaps != null) {
				String elementIdTL = elementId.toLowerCase();
				List<StyleRuleInfo> idRules = idMaps.get(elementIdTL);
				if (idRules != null) {
					return isAffectedByPseudoNameInAncestor(elementRules, ancestor, element, pseudoName);
				}
			}
		}
		return false;
	}

	/**
	 * is affected by pseudo name in ancestor
	 *
	 * @param elementRules
	 * @param ancestor
	 * @param element
	 * @param pseudoName
	 * @return
	 */
	private boolean isAffectedByPseudoNameInAncestor(List<StyleRuleInfo> elementRules, HTMLElementImpl ancestor,
			HTMLElementImpl element, String pseudoName) {
		if (elementRules != null) {
			for (StyleRuleInfo styleRuleInfo : elementRules) {
				CSSStyleSheetImpl styleSheet = styleRuleInfo.getStyleRule().getParentStyleSheet();
				if (styleSheet != null && styleSheet.getDisabled()) {
					continue;
				}
				if (styleRuleInfo.affectedByPseudoNameInAncestor(element, ancestor, pseudoName)) {
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * put style declarations
	 *
	 * @param elementRules
	 * @param styleDeclarations
	 * @param element
	 * @param pseudoNames
	 * @return
	 */
	private List<CSSStyleDeclarationImpl> putStyleDeclarations(List<StyleRuleInfo> elementRules,
			List<CSSStyleDeclarationImpl> styleDeclarations, HTMLElementImpl element, Set pseudoNames) {
		for (StyleRuleInfo styleRuleInfo : elementRules) {
			if (styleRuleInfo.isSelectorMatch(element, pseudoNames)) {
				CSSStyleRuleImpl styleRule = styleRuleInfo.getStyleRule();
				CSSStyleSheetImpl styleSheet = styleRule.getParentStyleSheet();
				if (styleSheet != null && styleSheet.getDisabled()) {
					continue;
				}
				if (styleDeclarations == null) {
					styleDeclarations = new LinkedList<CSSStyleDeclarationImpl>();
				}
				styleDeclarations.add(styleRule.getStyle());
			}
		}
		return styleDeclarations;
	}

	/**
	 * is attribute operator
	 *
	 * @param attr
	 * @return
	 */
	private boolean isAttributeOperator(Attr attr, HTMLElementImpl element) {

		String name = attr.getName();
		String value = attr.getValue();
		String nodeName = element.getNodeName();

		switch (attributeOperator) {
		case SelectorMatcher.OP_EQUAL:
			if (name.equals(attribute) && value.equals(attributeValue) && "*".equals(htmlElement)) {
				return true;
			} else if (name.equals(attribute) && value.equals(attributeValue)
					&& nodeName.equalsIgnoreCase(htmlElement)) {
				return true;
			}
			break;
		case SelectorMatcher.OP_TILDE_EQUAL:
		case SelectorMatcher.OP_STAR_EQUAL:
			if (name.equals(attribute) && value.contains(attributeValue) && "*".equals(htmlElement)) {
				return true;
			} else if (name.equals(attribute) && value.contains(attributeValue)
					&& nodeName.equalsIgnoreCase(htmlElement)) {
				return true;
			}
			break;
		case SelectorMatcher.OP_PIPE_EQUAL:
		case SelectorMatcher.OP_CIRCUMFLEX_EQUAL:
			if (name.equals(attribute) && value.startsWith(attributeValue) && "*".equals(htmlElement)) {
				return true;
			} else if (name.equals(attribute) && value.startsWith(attributeValue)
					&& nodeName.equalsIgnoreCase(htmlElement)) {
				return true;
			}
			break;
		case SelectorMatcher.OP_DOLLAR_EQUAL:
			if (name.equals(attribute) && value.endsWith(attributeValue) && "*".equals(htmlElement)) {
				return true;
			} else if (name.equals(attribute) && value.endsWith(attributeValue)
					&& nodeName.equalsIgnoreCase(htmlElement)) {
				return true;
			}
			break;
		case SelectorMatcher.OP_ALL:
			if (name.equals(attribute) && "*".equals(htmlElement)) {
				return true;
			} else if (name.equals(attribute) && nodeName.equalsIgnoreCase(htmlElement)) {
				return true;
			}
			break;
		default:
			break;
		}
		return false;
	}
}
