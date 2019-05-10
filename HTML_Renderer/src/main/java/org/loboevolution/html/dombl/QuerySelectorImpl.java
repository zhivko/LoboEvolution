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
package org.loboevolution.html.dombl;

import java.util.ArrayList;

import org.loboevolution.html.domfilter.ClassNameFilter;
import org.loboevolution.html.domfilter.ElementFilter;
import org.loboevolution.html.domimpl.DOMNodeListImpl;
import org.loboevolution.html.domimpl.HTMLCollectionImpl;
import org.loboevolution.html.domimpl.HTMLDocumentImpl;
import org.loboevolution.util.Nodes;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * The Class QuerySelectorImpl.
 */
public class QuerySelectorImpl {

	/**
	 * Document query selector.
	 *
	 * @param document
	 *            the document
	 * @param selectors
	 *            the selectors
	 * @return the element
	 */
	public Element documentQuerySelector(Document document, String selectors) {

		HTMLDocumentImpl doc = (HTMLDocumentImpl) document;
		Element element = null;
		if (selectors.startsWith("#")) {
			element = doc.getElementById(selectors.replace("#", ""));
		} else if (selectors.contains(".")) {
			String[] str = selectors.split("\\.");
			String str0 = str[0].trim();
			String str1 = str[1].trim();
			if ("".equals(str0) || str0 == null) {
				element = (Element) doc.getElementsByClassName(selectors.replace(".", "")).item(0);
			} else {
				NodeList nodeList = doc.getElementsByTagName(str0);
				for (Node node : Nodes.iterable(nodeList)) {
					if (str0.equals(node.getNodeName())) {
						NamedNodeMap attributes = node.getAttributes();
						for (Attr attr : Nodes.iterable(attributes)) {
							if (attr.getName().equals("class") && attr.getValue().equals(str1)) {
								element = (Element) node;
								break;
							}
						}
					}
				}
			}
		} else if (selectors.contains(">")) {

			String[] str = selectors.split(">");
			String str0 = str[0].trim();
			String str1 = str[1].trim();
			NodeList nodeList = doc.getElementsByTagName(str1);
			for (Node node : Nodes.iterable(nodeList)) {
				if (str0.equals(node.getParentNode().getNodeName())) {
					element = (Element)node;
					break;
				}
			}
		} else if (selectors.contains("[") && selectors.contains("]")) {

			String[] str = selectors.split("\\[");
			String str0 = str[0].trim();
			String str1 = str[1].trim();
			NodeList nodeList = doc.getElementsByTagName(str0);
			for (Node node : Nodes.iterable(nodeList)) {
				NamedNodeMap attributes = node.getAttributes();
				for (Attr attr : Nodes.iterable(attributes)) {
					if (attr.getName().equals(str1.replace("]", "").trim())) {
						element = (Element) node;
						break;
					}
				}
			}
		} else if (selectors.contains(",")) {

			String[] str = selectors.split("\\,");
			String str0 = str[0].trim();
			String str1 = str[1].trim();
			NodeList nodeList = new HTMLCollectionImpl(doc, new ElementFilter()).getNodeList();
			for (Node node : Nodes.iterable(nodeList)) {
				if (str0.equals(node.getNodeName()) || str1.equals(node.getNodeName())) {
					element = (Element) doc.getElementsByTagName(node.getNodeName()).item(0);
					break;
				}
			}
		} else {
			element = (Element) doc.getElementsByTagName(selectors).item(0);
		}
		return element;
	}

	/**
	 * Document query selector all.
	 *
	 * @param document
	 *            the document
	 * @param selectors
	 *            the selectors
	 * @return the node list
	 */
	public NodeList documentQuerySelectorAll(Document document, String selectors) {
		HTMLDocumentImpl doc = (HTMLDocumentImpl) document;
		ArrayList<Node> listNode = new ArrayList<Node>();
		NodeList list = null;

		if (selectors.contains(".")) {

			String[] str = selectors.split("\\.");
			String str0 = str[0].trim();
			String str1 = str[1].trim();
			if ("".equals(str0) || str0 == null) {
				list = new HTMLCollectionImpl(doc, new ClassNameFilter(selectors.replace(".", ""))).getNodeList();
			} else {
				NodeList nodeList = doc.getElementsByTagName(str0);
				for (Node node : Nodes.iterable(nodeList)) {
					if (str0.equals(node.getNodeName())) {
						NamedNodeMap attributes = node.getAttributes();
						for (Attr attr : Nodes.iterable(attributes)) {
							if (attr.getName().equals("class") && attr.getValue().equals(str1)) {
								listNode.add(node);
								list = new DOMNodeListImpl(listNode);
							}
						}
					}
				}
			}
		} else if (selectors.contains("[") && selectors.contains("]")) {

			String[] str = selectors.split("\\[");
			String str0 = str[0].trim();
			String str1 = str[1].trim();
			NodeList nodeList = doc.getElementsByTagName(str0);
			for (Node node : Nodes.iterable(nodeList)) {
				NamedNodeMap attributes = node.getAttributes();
				for (Attr attr : Nodes.iterable(attributes)) {
					if (attr.getName().equals(str1.replace("]", "").trim())) {
						listNode.add(node);
						list = new DOMNodeListImpl(listNode);
					}
				}
			}
		} else if (selectors.contains(">")) {

			String[] str = selectors.split(">");
			String str0 = str[0].trim();
			NodeList nodeList = doc.getElementsByTagName(str0);
			for (Node node : Nodes.iterable(nodeList)) {
				if (str0.equals(node.getParentNode().getNodeName())) {
					listNode.add(node);
					list = new DOMNodeListImpl(listNode);
				}
			}
		} else if (selectors.contains(",")) {

			String[] str = selectors.split("\\,");
			NodeList nodeList = new HTMLCollectionImpl(doc, new ElementFilter()).getNodeList();
			for (Node node : Nodes.iterable(nodeList)) {
				for (String element : str) {
					if (node.getNodeName().equals(element.trim())) {
						listNode.add(node);
					}
				}
			}
			list = new DOMNodeListImpl(listNode);
		} else {
			list = new HTMLCollectionImpl(doc, new ClassNameFilter(selectors)).getNodeList();
		}
		return list;
	}
}
