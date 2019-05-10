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

import org.loboevolution.html.domfilter.NodeFilter;
import org.loboevolution.w3c.html.HTMLCollection;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * The Class HTMLCollectionImpl.
 */
public class HTMLCollectionImpl implements HTMLCollection {

	private DOMNodeImpl rootNode;
	
	private DOMNodeListImpl nodeList = null;
	
	public HTMLCollectionImpl(DOMNodeImpl rootNode) {
		this.rootNode = rootNode;
	}

	public HTMLCollectionImpl(DOMNodeImpl rootNode, NodeFilter nodeFilter) {
		this.rootNode = rootNode;
		nodeList = (DOMNodeListImpl) rootNode.getNodeList(nodeFilter);
	}

	@Override
	public int getLength() {
		if(nodeList == null ) {
			return this.rootNode.getChildCount();
		} else {
			return this.nodeList.getLength();
		}
	}

	public int indexOf(Node node) {
		if (nodeList == null) {
			return this.rootNode.getChildIndex(node);
		} else {
			return this.nodeList.indexOf(node);
		}
	}

	@Override
	public Node item(int index) {
		if (nodeList == null) {
			return this.rootNode.getChildAtIndex(index);
		} else {
			return this.nodeList.get(index);
		}
	}

	@Override
	public Node namedItem(String name) {
		final Document doc = this.rootNode.getOwnerDocument();
		if (doc == null) {
			return null;
		}
		final Node node = doc.getElementById(name);
		if (node != null && node.getParentNode() == this.rootNode) {
			return node;
		}
		return null;
	}
	
	/**
	 * @return the nodeList
	 */
	public DOMNodeListImpl getNodeList() {
		return nodeList;
	}
}
