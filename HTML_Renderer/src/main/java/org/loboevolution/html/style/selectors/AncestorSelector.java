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

public class AncestorSelector {

	/** The current Element. */
	private HTMLElementImpl currentElement;

	public AncestorSelector(HTMLElementImpl currentElement) {
		this.currentElement = currentElement;
	}
	
	/**
	 * Get an ancestor that matches the element tag name given and the style
	 * class given.
	 *
	 * @param elementTL
	 *            An tag name in lowercase or an asterisk (*).
	 * @param classTL
	 *            A class name in lowercase.
	 * @return the ancestor with class
	 */
	public HTMLElementImpl getAncestorWithClass(String elementTL, String classTL) {
		Object nodeObj = currentElement.getParentNode();
		if (nodeObj instanceof HTMLElementImpl) {
			HTMLElementImpl parentElement = (HTMLElementImpl) nodeObj;
			String pelementTL = parentElement.getTagName().toLowerCase();
			SelectorMatcher sm = new SelectorMatcher();
			if (("*".equals(elementTL) || elementTL.equals(pelementTL)) && sm.classMatch(classTL, parentElement)) {
				return parentElement;
			}
			return new AncestorSelector(parentElement).getAncestorWithClass(elementTL, classTL);
		} else {
			return null;
		}
	}

	/**
	 * Gets the ancestor with id.
	 *
	 * @param elementTL
	 *            the element tl
	 * @param idTL
	 *            the id tl
	 * @return the ancestor with id
	 */
	public HTMLElementImpl getAncestorWithId(String elementTL, String idTL) {
		Object nodeObj = currentElement.getParentNode();
		if (nodeObj instanceof HTMLElementImpl) {
			HTMLElementImpl parentElement = (HTMLElementImpl) nodeObj;
			String pelementTL = parentElement.getTagName().toLowerCase();
			String pid = parentElement.getId();
			String pidTL = pid == null ? null : pid.toLowerCase();
			if (("*".equals(elementTL) || elementTL.equals(pelementTL)) && idTL.equals(pidTL)) {
				return parentElement;
			}
			return new AncestorSelector(parentElement).getAncestorWithId(elementTL, idTL);
		} else {
			return null;
		}
	}

	/**
	 * Gets the ancestor.
	 *
	 * @param elementTL
	 *            the element tl
	 * @return the ancestor
	 */
	public HTMLElementImpl getAncestor(String elementTL) {
		Object nodeObj = currentElement.getParentNode();
		if (nodeObj instanceof HTMLElementImpl) {
			HTMLElementImpl parentElement = (HTMLElementImpl) nodeObj;
			if ("*".equals(elementTL)) {
				return parentElement;
			}
			String pelementTL = parentElement.getTagName().toLowerCase();
			if (elementTL.equals(pelementTL)) {
				return parentElement;
			}
			return new AncestorSelector(parentElement).getAncestor(elementTL);
		} else {
			return null;
		}
	}
}
