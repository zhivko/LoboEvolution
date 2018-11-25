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

import java.util.ArrayList;
import java.util.Collection;

import org.loboevolution.html.domfilter.NodeFilter;
import org.loboevolution.js.AbstractScriptableDelegate;
import org.loboevolution.util.Nodes;
import org.loboevolution.util.Objects;
import org.loboevolution.w3c.html.HTMLCollection;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * The Class HTMLCollectionImpl.
 */
public class HTMLCollectionImpl extends AbstractScriptableDelegate implements HTMLCollection {

	/** The root node. */
	private final DOMNodeImpl rootNode;
	
	/** The node filter. */
	private final NodeFilter nodeFilter;

	/**
	 * Instantiates a new child html collection.
	 *
	 * @param node the node
	 */
	public HTMLCollectionImpl(DOMNodeImpl rootNode, NodeFilter filter) {
		this.rootNode = rootNode;
		this.nodeFilter = filter;
	}

	/**
	 * Instantiates a new child html collection.
	 *
	 * @param node the node
	 */
	public HTMLCollectionImpl(DOMNodeImpl rootNode) {
		this.rootNode = rootNode;
		this.nodeFilter = null;
	}

	@Override
	public int getLength() {
		return nodeList().getLength();
	}

	@Override
	public Node item(int index) {
		return nodeList().item(index);
	}

	@Override
	public Node namedItem(String name) {
		Document doc = this.rootNode.getOwnerDocument();
		if (doc == null) {
			return null;
		}
		Node node = doc.getElementById(name);
		if (Objects.equals(node.getParentNode(), this.rootNode)) {
			return node;
		}
		return null;
	}

	public int indexOf(Node node) {
		return this.nodeList().indexOf(node);
	}

	public DOMNodeListImpl nodeList() {
		if (nodeFilter == null) {
			return new DOMNodeListImpl(rootNode.nodeList);
		} else {
			return getNodeList(nodeFilter);
		}
	}

	private DOMNodeListImpl getNodeList(NodeFilter filter) {
		Collection<DOMNodeImpl> collection = new ArrayList<DOMNodeImpl>();
		this.appendChildrenToCollectionImpl(filter, collection);
		return new DOMNodeListImpl(collection);
	}

	private void appendChildrenToCollectionImpl(NodeFilter filter, Collection<DOMNodeImpl> collection) {
		ArrayList<Node> list = rootNode.nodeList;
		for (Node node : list) {
			 if (filter.accept(node)) collection.add((DOMNodeImpl) node);
			NodeList gChildNodes = node.getChildNodes();
			child(gChildNodes, filter, collection);
		}
	}

	private void child(NodeList gChildNodes, NodeFilter filter, Collection<DOMNodeImpl> collection) {
		for (Node n1 : Nodes.iterable(gChildNodes)) {
            DOMNodeImpl n = (DOMNodeImpl) n1;
            if (filter.accept(n)) collection.add((DOMNodeImpl) n);
			child(n.getChildNodes(), filter, collection);
		}
	}	
}
