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
 * Created on Sep 3, 2005
 */
package org.loboevolution.html.domimpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.loboevolution.js.AbstractScriptableDelegate;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * The Class DOMNodeListImpl.
 */
public class DOMNodeListImpl extends AbstractScriptableDelegate implements NodeList {

	private final List<Node> nodeList = Collections.synchronizedList(new ArrayList<Node>());

	public DOMNodeListImpl() {
	}

	public DOMNodeListImpl(List<Node> collection) {
		this.nodeList.addAll(collection);
	}

	@Override
	public int getLength() {
		return this.nodeList.size();
	}

	@Override
	public Node item(int index) {
		int size = this.nodeList.size();
		if (size > index && index > -1) {
			return (Node) this.nodeList.get(index);
		} else {
			return null;
		}
	}

	public void add(Node newChild) {
		this.nodeList.add(newChild);
	}

	public void add(int firstIdx, Node textNode) {
		this.nodeList.add(firstIdx, textNode);
	}

	public int indexOf(Node child) {
		return this.nodeList.indexOf(child);
	}

	public Node remove(int i) {
		return this.nodeList.remove(i);
	}

	public Node get(int index) {
		return this.nodeList.get(index);
	}

	public boolean remove(Node oldChild) {
		return this.nodeList.remove(oldChild);
	}

	public void clear() {
		this.nodeList.clear();
	}

	public DOMNodeImpl[] toArray() {
		return this.nodeList.toArray(new DOMNodeImpl[0]);
	}

	public void set(int idx, Node newChild) {
		this.nodeList.set(idx, newChild);
	}

	public void removeAll(List<Node> toDelete) {
		this.nodeList.removeAll(toDelete);
	}
}