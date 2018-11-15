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

package org.loboevolution.w3c.html;

import org.mozilla.javascript.Function;

/**
 * The Interface HTMLBodyElement.
 */
public interface HTMLBodyElement extends HTMLElement {

	/**
	 * Gets the text.
	 *
	 * @return the text
	 */
	public String getText();

	/**
	 * Sets the text.
	 *
	 * @param text
	 *            the new text
	 */
	public void setText(String text);

	/**
	 * Gets the bg color.
	 *
	 * @return the bg color
	 */
	public String getBgColor();

	/**
	 * Sets the bg color.
	 *
	 * @param bgColor
	 *            the new bg color
	 */
	public void setBgColor(String bgColor);

	/**
	 * Gets the background.
	 *
	 * @return the background
	 */
	public String getBackground();

	/**
	 * Sets the background.
	 *
	 * @param background
	 *            the new background
	 */
	public void setBackground(String background);

	/**
	 * Gets the link.
	 *
	 * @return the link
	 */
	public String getLink();

	/**
	 * Sets the link.
	 *
	 * @param link
	 *            the new link
	 */
	public void setLink(String link);

	/**
	 * Gets the v link.
	 *
	 * @return the v link
	 */
	public String getVLink();

	/**
	 * Sets the v link.
	 *
	 * @param vLink
	 *            the new v link
	 */
	public void setVLink(String vLink);

	/**
	 * Gets the a link.
	 *
	 * @return the a link
	 */
	public String getALink();

	/**
	 * Sets the a link.
	 *
	 * @param aLink
	 *            the new a link
	 */
	public void setALink(String aLink);
}
