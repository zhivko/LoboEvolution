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
package org.loboevolution.html.style.selectors;

import org.loboevolution.html.domimpl.HTMLElementImpl;
import org.loboevolution.util.Nodes;
import org.loboevolution.util.Objects;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class PrecedingSelector {
	
	/** The current Element. */
	private HTMLElementImpl currentElement;

	public PrecedingSelector(HTMLElementImpl currentElement) {
		this.currentElement = currentElement;
	}

	/**
	 * Gets the preceding sibling with class.
	 *
	 * @param elementTL the element tl
	 * @param classTL   the class tl
	 * @return the preceding sibling with class
	 */
	public HTMLElementImpl getPrecedingSiblingWithClass(String elementTL, String classTL) {
		HTMLElementImpl psibling = getPrecedingSiblingElement();
		if (psibling != null) {
			SelectorMatcher sm = new SelectorMatcher();
			String pelementTL = psibling.getTagName().toLowerCase();
			if (("*".equals(elementTL) || elementTL.equals(pelementTL)) && sm.classMatch(classTL, psibling)) {
				return psibling;
			}
		}
		return null;
	}

	/**
	 * Gets the preceding sibling with id.
	 *
	 * @param elementTL the element tl
	 * @param idTL      the id tl
	 * @return the preceding sibling with id
	 */
	public HTMLElementImpl getPrecedingSiblingWithId(String elementTL, String idTL) {
		HTMLElementImpl psibling = getPrecedingSiblingElement();
		if (psibling != null) {
			String pelementTL = psibling.getTagName().toLowerCase();
			String pid = psibling.getId();
			String pidTL = pid == null ? null : pid.toLowerCase();
			if (("*".equals(elementTL) || elementTL.equals(pelementTL)) && idTL.equals(pidTL)) {
				return psibling;
			}
		}
		return null;
	}

	/**
	 * Gets the preceding sibling.
	 *
	 * @param elementTL the element tl
	 * @return the preceding sibling
	 */
	public HTMLElementImpl getPrecedingSibling(String elementTL) {
		HTMLElementImpl psibling = getPrecedingSiblingElement();
		if (psibling != null) {
			if ("*".equals(elementTL)) {
				return psibling;
			}
			String pelementTL = psibling.getTagName().toLowerCase();
			if (elementTL.equals(pelementTL)) {
				return psibling;
			}
		}
		return null;
	}
	
	/**
	 * Gets the preceding sibling element.
	 *
	 * @return the preceding sibling element
	 */
	private HTMLElementImpl getPrecedingSiblingElement() {
		Node parentNode = currentElement.getParentNode();
		if (parentNode == null) {
			return null;
		}
		NodeList childNodes = parentNode.getChildNodes();
		if (childNodes == null) {
			return null;
		}
		HTMLElementImpl priorElement = null;
		for (Node child : Nodes.iterable(childNodes)) {
			if (Objects.equals(child, currentElement)) {
				return priorElement;
			}
			if (child instanceof HTMLElementImpl) {
				priorElement = (HTMLElementImpl) child;
			}
		}
		return null;
	}

}
