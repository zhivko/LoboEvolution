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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.loboevolution.arraylist.ArrayUtilities;
import org.loboevolution.html.HtmlRendererContext;
import org.loboevolution.html.dombl.ElementFactory;
import org.loboevolution.html.io.WritableLineReader;
import org.loboevolution.http.UserAgentContext;
import org.loboevolution.util.Nodes;
import org.loboevolution.util.Strings;
import org.loboevolution.w3c.events.DocumentEvent;
import org.loboevolution.w3c.xpath.XPathEvaluator;
import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.DOMException;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class DocumentImpl extends DOMFunctionImpl implements Document, DocumentEvent, XPathEvaluator {

	/** The document uri. */
	private String documentURI;

	/** The strict error checking. */
	private boolean strictErrorChecking = true;

	/** The xml encoding. */
	private String xmlEncoding;

	/** The xml version. */
	private String xmlVersion = null;

	/** The xml standalone. */
	private boolean xmlStandalone;

	/** The input encoding. */
	private String inputEncoding;

	/** The doctype. */
	private DocumentType doctype;

	/** The elements by name. */
	private final Map<String, Element> elementById = new HashMap<String, Element>(0);

	/** The dom config. */
	private DOMConfiguration domConfig;

	/** The dom implementation. */
	private DOMImplementation domImplementation;

	/** The ucontext. */
	protected UserAgentContext ucontext;
	
	/** The rcontext. */
	protected HtmlRendererContext rcontext;
	
	/** The reader. */
	protected WritableLineReader reader;

	@Override
	public Element createElement(String tagName) throws DOMException {
		return ElementFactory.getInstance().createElement(this, tagName);
	}

	@Override
	protected Node createSimilarNode() {
		return new HTMLDocumentImpl(this.ucontext, this.rcontext, this.reader, this.getDocumentURI());
	}
	
	@Override
	public DocumentType getDoctype() {
		return this.doctype;
	}

	@Override
	public Element getDocumentElement() {
		for (Node node : Nodes.iterable(this.nodeList)) {
			if (node instanceof Element) {
				return (Element) node;
			}
		}
		return null;
	}

	@Override
	public String getDocumentURI() {
		return this.documentURI;
	}

	@Override
	public DOMConfiguration getDomConfig() {
		synchronized (this) {
			if (this.domConfig == null) {
				this.domConfig = new DOMConfigurationImpl();
			}
			return this.domConfig;
		}
	}

	@Override
	public Element getElementById(String elementId) {
		if (Strings.isNotBlank(elementId)) {
			synchronized (this) {
				return (Element) this.elementById.get(elementId);
			}
		} else {
			return null;
		}
	}

	@Override
	public DOMImplementation getImplementation() {
		synchronized (this) {
			if (this.domImplementation == null) {
				this.domImplementation = new DOMImplementationImpl(this.ucontext);
			}
			return this.domImplementation;
		}
	}

	@Override
	public String getInputEncoding() {
		return this.inputEncoding;
	}

	@Override
	public boolean getStrictErrorChecking() {
		return this.strictErrorChecking;
	}

	@Override
	public String getXmlEncoding() {
		return this.xmlEncoding;
	}

	@Override
	public boolean getXmlStandalone() {
		return this.xmlStandalone;
	}

	@Override
	public String getXmlVersion() {
		return this.xmlVersion;
	}

	@Override
	public void setDocumentURI(String documentURI) {
		this.documentURI = documentURI;
	}

	@Override
	public void setStrictErrorChecking(boolean strictErrorChecking) {
		this.strictErrorChecking = strictErrorChecking;
	}

	@Override
	public void setXmlStandalone(boolean xmlStandalone) throws DOMException {
		this.xmlStandalone = xmlStandalone;
	}

	@Override
	public void setXmlVersion(String xmlVersion) throws DOMException {
		this.xmlVersion = xmlVersion;

	}

	protected Map<String, Element> getElementById() {
		return elementById;
	}

}
