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
package org.loboevolution.html.domimpl;

import java.util.ArrayList;

import org.loboevolution.html.dombl.NodeVisitor;
import org.loboevolution.html.dombl.StopVisitorException;
import org.loboevolution.html.domfilter.NodeFilter;
import org.loboevolution.html.renderstate.RenderState;
import org.loboevolution.html.renderstate.TableRowRenderState;
import org.loboevolution.util.Nodes;
import org.loboevolution.w3c.html.HTMLCollection;
import org.loboevolution.w3c.html.HTMLElement;
import org.loboevolution.w3c.html.HTMLTableCellElement;
import org.loboevolution.w3c.html.HTMLTableRowElement;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * The Class HTMLTableRowElementImpl.
 */
public class HTMLTableRowElementImpl extends HTMLAbstractUIElement implements HTMLTableRowElement {

	
	public HTMLTableRowElementImpl(String name) {
		super(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLTableRowElement#getRowIndex()
	 */
	@Override
	public int getRowIndex() {
		DOMNodeImpl parent = (DOMNodeImpl) this.getParentNode();
		if (parent == null) {
			return -1;
		}
		try {
			parent.visit(new NodeVisitor() {
				private int count = 0;

				@Override
				public void visit(Node node) {
					if (node instanceof HTMLTableRowElementImpl) {
						if (HTMLTableRowElementImpl.this == node) {
							throw new StopVisitorException(Integer.valueOf(this.count));
						}
						this.count++;
					}
				}
			});
		} catch (StopVisitorException sve) {
			return ((Integer) sve.getTag()).intValue();
		}
		return -1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLTableRowElement#getSectionRowIndex()
	 */
	@Override
	public int getSectionRowIndex() {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLTableRowElement#getCells()
	 */
	@Override
	public HTMLCollection getCells() {
		NodeFilter filter = node -> node instanceof HTMLTableCellElementImpl;
		return new HTMLCollectionImpl(this, filter);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLTableRowElement#getAlign()
	 */
	@Override
	public String getAlign() {
		return this.getAttribute(ALIGN);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.w3c.html.HTMLTableRowElement#setAlign(java.lang.String)
	 */
	@Override
	public void setAlign(String align) {
		this.setAttribute(ALIGN, align);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLTableRowElement#getBgColor()
	 */
	@Override
	public String getBgColor() {
		return this.getAttribute(BGCOLOR);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.w3c.html.HTMLTableRowElement#setBgColor(java.lang.String)
	 */
	@Override
	public void setBgColor(String bgColor) {
		this.setAttribute(BGCOLOR, bgColor);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLTableRowElement#getCh()
	 */
	@Override
	public String getCh() {
		return this.getAttribute(CH);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLTableRowElement#setCh(java.lang.String)
	 */
	@Override
	public void setCh(String ch) {
		this.setAttribute(CH, ch);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLTableRowElement#getChOff()
	 */
	@Override
	public String getChOff() {
		return this.getAttribute(CHOFF);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.w3c.html.HTMLTableRowElement#setChOff(java.lang.String)
	 */
	@Override
	public void setChOff(String chOff) {
		this.setAttribute(CHOFF, chOff);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLTableRowElement#getVAlign()
	 */
	@Override
	public String getVAlign() {
		return this.getAttribute(VALIGN);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.w3c.html.HTMLTableRowElement#setVAlign(java.lang.String)
	 */
	@Override
	public void setVAlign(String vAlign) {
		this.setAttribute(VALIGN, vAlign);
	}

	/**
	 * Inserts a TH element at the specified index.
	 * <p>
	 * Note: This method is non-standard.
	 *
	 * @param index
	 *            The cell index to insert at.
	 * @return The element that was inserted.
	 * @throws DOMException
	 *             When the index is out of range.
	 */
	public HTMLElement insertHeader(int index) throws DOMException {
		return this.insertCell(index, TH);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLTableRowElement#insertCell(int)
	 */
	@Override
	public HTMLElement insertCell(int index) throws DOMException {
		return this.insertCell(index, TD);
	}

	/**
	 * Insert cell.
	 *
	 * @param index
	 *            the index
	 * @param tagName
	 *            the tag name
	 * @return the HTML element
	 * @throws DOMException
	 *             the DOM exception
	 */
	private HTMLElement insertCell(int index, String tagName) throws DOMException {
		final Document doc = this.document;
		if (doc == null) {
			throw new DOMException(DOMException.WRONG_DOCUMENT_ERR, "Orphan element");
		}
		final HTMLElement cellElement = (HTMLElement) doc.createElement(tagName);
		synchronized (this.treeLock) {
			if (index == -1) {
				appendChild(cellElement);
				return cellElement;
			}

			int trcount = 0;
			for (Node node : Nodes.iterable(nodeList)) {
				if (node instanceof HTMLTableCellElement) {
					if (trcount == index) {
						insertAt(cellElement, nodeList.indexOf(node));
						return cellElement;
					}
					trcount++;
				}
			}
			appendChild(cellElement);
			return cellElement;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLTableRowElement#deleteCell(int)
	 */
	@Override
	public void deleteCell(int index) throws DOMException {
		int trcount = 0;
		for (Node node : Nodes.iterable(nodeList)) {
			if (node instanceof HTMLTableCellElement) {
				if (trcount == index) {
					removeChildAt(index);
				}
				trcount++;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLTableRowElement#insertCell()
	 */
	@Override
	public HTMLElement insertCell() {
		return this.insertCell(-1, TD);
	}
	
	@Override
	protected RenderState createRenderState(RenderState prevRenderState) {
		RenderState tmpRenderState = prevRenderState;
		return new TableRowRenderState(tmpRenderState, this);
	}
}
