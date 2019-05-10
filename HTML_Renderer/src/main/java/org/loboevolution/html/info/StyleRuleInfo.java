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
package org.loboevolution.html.info;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.loboevolution.html.domimpl.HTMLElementImpl;
import org.loboevolution.html.style.selectors.AncestorSelector;
import org.loboevolution.html.style.selectors.NextSelector;
import org.loboevolution.html.style.selectors.ParentSelector;
import org.loboevolution.html.style.selectors.PrecedingSelector;
import org.loboevolution.html.style.selectors.SelectorMatcher;
import org.loboevolution.util.Objects;

import com.gargoylesoftware.css.dom.CSSStyleRuleImpl;

/**
 * The Class StyleRuleInfo.
 */
public class StyleRuleInfo implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 9165715430607111555L;

	/** The style rule. */
	private transient CSSStyleRuleImpl styleRule;

	/** The ancestor selectors. */
	private final List<SelectorMatcher> ancestorSelectors;

	/**
	 * Instantiates a new style rule info.
	 *
	 * @param SelectorMatchers
	 *            A collection of SelectorMatcher's.
	 * @param rule
	 *            A CSS rule.
	 */
	public StyleRuleInfo(List<SelectorMatcher> SelectorMatchers, CSSStyleRuleImpl rule) {
		super();
		ancestorSelectors = SelectorMatchers;
		this.styleRule = rule;
	}

	/**
	 * Affected by pseudo name in ancestor.
	 *
	 * @param element
	 *            the element
	 * @param ancestor
	 *            the ancestor
	 * @param pseudoName
	 *            the pseudo name
	 * @return true, if successful
	 */
	public final boolean affectedByPseudoNameInAncestor(HTMLElementImpl element, HTMLElementImpl ancestor,
			String pseudoName) {
		List<SelectorMatcher> as = this.ancestorSelectors;
		HTMLElementImpl currentElement = element;
		int size = as.size();
		boolean first = true;
		for (int i = size; --i >= 0;) {
			SelectorMatcher selectorMatcher = as.get(i);
			if (first) {
				if (Objects.equals(ancestor, element)) {
					return selectorMatcher.hasPseudoName(pseudoName);
				}
				first = false;
				continue;
			}
			String selectorText = selectorMatcher.getSimpleSelectorText();
			int dotIdx = selectorText.indexOf('.');
			HTMLElementImpl newElement;
			if (dotIdx != -1) {
				String elemtl = selectorText.substring(0, dotIdx);
				String classtl = selectorText.substring(dotIdx + 1);
				AncestorSelector ancestorSelecto = new AncestorSelector(currentElement);
				newElement = ancestorSelecto.getAncestorWithClass(elemtl, classtl);
			} else {
				int poundIdx = selectorText.indexOf('#');
				if (poundIdx != -1) {
					String elemtl = selectorText.substring(0, poundIdx);
					String idtl = selectorText.substring(poundIdx + 1);
					AncestorSelector ancestorSelecto = new AncestorSelector(currentElement);
					newElement = ancestorSelecto.getAncestorWithId(elemtl, idtl);
				} else {
					String elemtl = selectorText;
					AncestorSelector ancestorSelecto = new AncestorSelector(currentElement);
					newElement = ancestorSelecto.getAncestor(elemtl);
				}
			}
			if (newElement == null) {
				return false;
			}
			currentElement = newElement;
			if (Objects.equals(currentElement, ancestor)) {
				return selectorMatcher.hasPseudoName(pseudoName);
			}
		}
		return false;
	}

	/**
	 * Checks if is selector match.
	 *
	 * @param element
	 *            The element to test for a match.
	 * @param pseudoNames
	 *            A set of pseudo-names in lowercase.
	 * @return true, if is selector match
	 */
	public final boolean isSelectorMatch(HTMLElementImpl element, Set<?> pseudoNames) {
		List<SelectorMatcher> as = this.ancestorSelectors;
		HTMLElementImpl currentElement = element;
		int size = as.size();
		boolean first = true;
		for (int i = size; --i >= 0;) {
			SelectorMatcher selectorMatcher = as.get(i);
			
			if (first) {
				if (!selectorMatcher.matches(pseudoNames)) {
					return false;
				}
				first = false;
				continue;
			}
			
			String selectorText = selectorMatcher.getSimpleSelectorText();
			int dotIdx = selectorText.indexOf('.');
			int selectorType = selectorMatcher.getSelectorType();
			HTMLElementImpl priorElement = null;
			
			if (dotIdx != -1) {
				String elemtl = getElement(selectorText.substring(0, dotIdx)); 
				String classtl = selectorText.substring(dotIdx + 1);
				
				switch (selectorType) {
				case SelectorMatcher.ANCESTOR:
					AncestorSelector ancestor = new AncestorSelector(currentElement);
					priorElement = ancestor.getAncestorWithClass(elemtl, classtl);
					break;
				case SelectorMatcher.PARENT:
					ParentSelector parent = new ParentSelector(currentElement);
					priorElement = parent.getParentWithClass(elemtl, classtl);
					break;
				case SelectorMatcher.PRECEEDING_SIBLING:
					PrecedingSelector preceding = new PrecedingSelector(currentElement);
					priorElement = preceding.getPrecedingSiblingWithClass(elemtl, classtl);
					break;
				case SelectorMatcher.NEXT_SIBLING:
					NextSelector next = new NextSelector(currentElement);
					priorElement = next.getNextWithClass(elemtl, classtl);
					break;
				default:
					throw new IllegalStateException("selectorType=" + selectorType);
				}
				
			} else {
				int poundIdx = selectorText.indexOf('#');
				if (poundIdx != -1) {
					String elemtl = getElement(selectorText.substring(0, poundIdx));
					String idtl = selectorText.substring(poundIdx + 1);
					
					switch (selectorType) {
					case SelectorMatcher.ANCESTOR:
						AncestorSelector ancestor = new AncestorSelector(currentElement);
						priorElement = ancestor.getAncestorWithId(elemtl, idtl);
						break;
					case SelectorMatcher.PARENT:
						ParentSelector parent = new ParentSelector(currentElement);
						priorElement = parent.getParentWithId(elemtl, idtl);
						break;
					case SelectorMatcher.PRECEEDING_SIBLING:
						PrecedingSelector preceding = new PrecedingSelector(currentElement);
						priorElement = preceding.getPrecedingSiblingWithId(elemtl, idtl);
						break;
					case SelectorMatcher.NEXT_SIBLING:
						NextSelector next = new NextSelector(currentElement);
						priorElement = next.getNextWithId(elemtl, idtl);
						break;
					default:
						throw new IllegalStateException("selectorType=" + selectorType);
					}

				} else {
					String elemtl = getElement(selectorText);
					switch (selectorType) {
					case SelectorMatcher.ANCESTOR:
						AncestorSelector ancestor = new AncestorSelector(currentElement);
						priorElement = ancestor.getAncestor(elemtl);
						break;
					case SelectorMatcher.PARENT:
						ParentSelector parent = new ParentSelector(currentElement);
						priorElement = parent.getParent(elemtl);
						break;
					case SelectorMatcher.PRECEEDING_SIBLING:
						PrecedingSelector preceding = new PrecedingSelector(currentElement);
						priorElement = preceding.getPrecedingSibling(elemtl);
						break;
					case SelectorMatcher.NEXT_SIBLING:
						NextSelector next = new NextSelector(currentElement);
						priorElement = next.getNext();
						break;
					default:
						throw new IllegalStateException("selectorType=" + selectorType);
					}
					
				}
			}
			if (priorElement == null) {
				return false;
			}
			if (!selectorMatcher.matches(priorElement)) {
				return false;
			}
			currentElement = priorElement;
		}
		return true;
	}
	
	private static String getElement(String elemtl) {

		if (elemtl == null || "".equals(elemtl)) {
			return "*";
		}

		try {
			Pattern p = Pattern.compile(".*\\[ *(.*) *\\].*");
			Matcher m = p.matcher(elemtl.replace("\"", ""));
			m.find();
			return m.group(1);
		} catch (Exception e) {
			return elemtl;
		}

	}

	/**
	 * Gets the style rule.
	 *
	 * @return the style rule
	 */
	public CSSStyleRuleImpl getStyleRule() {
		return styleRule;
	}

	/**
	 * Sets the style rule.
	 *
	 * @param styleRule
	 *            the new style rule
	 */
	public void setStyleRule(CSSStyleRuleImpl styleRule) {
		this.styleRule = styleRule;
	}

	/**
	 * get Ancestor Selectors.
	 */
	public List<SelectorMatcher> getAncestorSelectors() {
		return ancestorSelectors;
	}
}
