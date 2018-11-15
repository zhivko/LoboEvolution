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
 * Created on Jan 28, 2006
 */
package org.loboevolution.html.domimpl;


import org.loboevolution.w3c.html.HTMLFrameSetElement;
import org.mozilla.javascript.Function;

/**
 * The Class HTMLFrameSetElementImpl.
 */
public class HTMLFrameSetElementImpl extends HTMLElementImpl implements HTMLFrameSetElement {

	/**
	 * Instantiates a new HTML frame set element impl.
	 *
	 * @param name
	 *            the name
	 */
	public HTMLFrameSetElementImpl(String name) {
		super(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLFrameSetElement#getCols()
	 */
	@Override
	public String getCols() {
		return this.getAttribute(COLS);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.w3c.html.HTMLFrameSetElement#setCols(java.lang.String)
	 */
	@Override
	public void setCols(String cols) {
		this.setAttribute(COLS, cols);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLFrameSetElement#getRows()
	 */
	@Override
	public String getRows() {
		return this.getAttribute(ROWS);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.w3c.html.HTMLFrameSetElement#setRows(java.lang.String)
	 */
	@Override
	public void setRows(String rows) {
		this.setAttribute(ROWS, rows);
	}
}
