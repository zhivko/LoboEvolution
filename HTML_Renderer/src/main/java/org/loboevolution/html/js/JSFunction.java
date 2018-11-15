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

package org.loboevolution.html.js;

import java.util.HashMap;
import java.util.Map;

import javax.swing.Timer;

import org.loboevolution.html.domimpl.HTMLDocumentImpl;
import org.loboevolution.html.domimpl.HTMLIFrameElementImpl;
import org.loboevolution.html.domimpl.HTMLImageElementImpl;
import org.loboevolution.html.domimpl.HTMLOptionElementImpl;
import org.loboevolution.html.domimpl.HTMLScriptElementImpl;
import org.loboevolution.html.domimpl.HTMLSelectElementImpl;
import org.loboevolution.html.js.event.FunctionImpl;
import org.loboevolution.html.js.object.Console;
import org.loboevolution.html.js.object.DOMParser;
import org.loboevolution.html.js.xml.XMLHttpRequest;
import org.loboevolution.html.js.xml.XMLSerializer;
import org.loboevolution.html.xpath.XPathResultImpl;
import org.loboevolution.http.UserAgentContext;
import org.loboevolution.js.JavaClassWrapper;
import org.loboevolution.js.JavaClassWrapperFactory;
import org.loboevolution.js.JavaInstantiator;
import org.loboevolution.js.JavaObjectWrapper;
import org.loboevolution.js.JavaScript;
import org.loboevolution.util.Objects;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.w3c.dom.Document;

public class JSFunction extends FunctionImpl {
	
	/** The Constant XMLHTTPREQUEST_WRAPPER. */
	private static final JavaClassWrapper XMLHTTPREQUEST_WRAPPER = JavaClassWrapperFactory.getInstance().getClassWrapper(XMLHttpRequest.class);

	/** The Constant DOMPARSER_WRAPPER. */
	private static final JavaClassWrapper DOMPARSER_WRAPPER = JavaClassWrapperFactory.getInstance().getClassWrapper(DOMParser.class);

	/** The Constant XMLSERIALIZER_WRAPPER. */
	private static final JavaClassWrapper XMLSERIALIZER_WRAPPER = JavaClassWrapperFactory.getInstance().getClassWrapper(XMLSerializer.class);
		
	/** The ua context. */
	private final UserAgentContext uaContext;
	
	/** The window scope. */
	private ScriptableObject windowScope;
	
	/** The document. */
	private volatile HTMLDocumentImpl document;
	
	/** The task map. */
	private Map<Integer, TaskWrapper> taskMap;
	
	public JSFunction(UserAgentContext uaContext) {
		this.uaContext = uaContext;
	}
	
	/**
	 * Sets the document.
	 *
	 * @param document
	 *            the new document
	 */
	public void setDocument(HTMLDocumentImpl document) {
		Document prevDocument = this.document;
		if (!Objects.equals(prevDocument, document)) {
			// Should clearing of the state be done
			// when window "unloads"?
			if (prevDocument != null) {
				// Only clearing when the previous document was not null
				// because state might have been set on the window before
				// the very first document is added.
				this.clearState();
			}
			this.initWindowScope(document);
			this.forgetAllTasks();
			Function onunload = this.getOnbeforeunload();
			if (onunload != null) {
				HTMLDocumentImpl oldDoc = this.document;
				Executor.executeFunction(this.getWindowScope(), onunload, oldDoc.getDocumentURL(), this.uaContext);
				this.setOnbeforeunload(null);
			}
			this.document = document;
		}
	}
	
	/**
	 * Gets the window scope.
	 *
	 * @return the window scope
	 */
	public Scriptable getWindowScope() {
		synchronized (this) {
			ScriptableObject windowScope = this.windowScope;
			if (windowScope != null) {
				return windowScope;
			}
			try {
				Context ctx = Context.enter();
				// Window scope needs to be top-most scope.
				windowScope = (ScriptableObject) JavaScript.getInstance().getJavascriptObject(this, null);
				ctx.initStandardObjects(windowScope);
				Object consoleJSObj = JavaScript.getInstance().getJavascriptObject(new Console(), windowScope);
				ScriptableObject.putProperty(windowScope, "console", consoleJSObj);
				Object xpathresult = JavaScript.getInstance().getJavascriptObject(new XPathResultImpl(), windowScope);
				ScriptableObject.putProperty(windowScope, "XPathResult", xpathresult);
				this.windowScope = windowScope;
				return windowScope;
			} finally {
				Context.exit();
			}
		}
	}
	
	/**
	 * Put and start task.
	 *
	 * @param timeoutID
	 *            the timeout id
	 * @param timer
	 *            the timer
	 * @param retained
	 *            the retained
	 */
	public void putAndStartTask(Integer timeoutID, Timer timer) {
		TaskWrapper oldTaskWrapper = null;
		synchronized (this) {
			Map<Integer, TaskWrapper> taskMap = this.taskMap;
			if (taskMap == null) {
				taskMap = new HashMap<Integer, TaskWrapper>(4);
				this.taskMap = taskMap;
			} else {
				oldTaskWrapper = taskMap.get(timeoutID);
			}
			taskMap.put(timeoutID, new TaskWrapper(timer));
		}
		// Do this outside synchronized block, just in case.
		if (oldTaskWrapper != null) {
			oldTaskWrapper.timer.stop();
		}
		timer.start();
	}

	/**
	 * Forget task.
	 *
	 * @param timeoutID
	 *            the timeout id
	 * @param cancel
	 *            the cancel
	 */
	public void forgetTask(Integer timeoutID, boolean cancel) {
		TaskWrapper oldTimer = null;
		synchronized (this) {
			Map<Integer, TaskWrapper> taskMap = this.taskMap;
			if (taskMap != null) {
				oldTimer = taskMap.remove(timeoutID);
			}
		}
		if (oldTimer != null && cancel) {
			oldTimer.timer.stop();
		}
	}

	/**
	 * Forget all tasks.
	 */
	public void forgetAllTasks() {
		TaskWrapper[] oldTaskWrappers = null;
		synchronized (this) {
			Map<Integer, TaskWrapper> taskMap = this.taskMap;
			if (taskMap != null) {
				oldTaskWrappers = taskMap.values().toArray(new TaskWrapper[0]);
				this.taskMap = null;
			}
		}
		if (oldTaskWrappers != null) {
			for (TaskWrapper taskWrapper : oldTaskWrappers) {
				taskWrapper.timer.stop();
			}
		}
	}
	
	/**
	 * Inits the window scope.
	 *
	 * @param doc
	 *            the doc
	 */
	private void initWindowScope(final Document doc) {
		final Scriptable ws = this.getWindowScope();
		JavaInstantiator jiXhttp = () -> {
			Document d = doc;
			if (d == null) {
				throw new IllegalStateException("Cannot perform operation when document is unset.");
			}
			HTMLDocumentImpl hd;
			try {
				hd = (HTMLDocumentImpl) d;
			} catch (ClassCastException err) {
				throw new IllegalStateException(
						"Cannot perform operation with documents of type " + d.getClass().getName() + ".");
			}
			return new XMLHttpRequest(uaContext, hd.getDocumentURL(), ws);
		};

		JavaInstantiator jiDomParser = () -> new DOMParser();

		JavaInstantiator jiXMLSerializer = () -> new XMLSerializer();

		Function xmlHttpRequestC = JavaObjectWrapper.getConstructor("XMLHttpRequest", XMLHTTPREQUEST_WRAPPER, jiXhttp);
		ScriptableObject.defineProperty(ws, "XMLHttpRequest", xmlHttpRequestC, ScriptableObject.READONLY);

		Function domParser = JavaObjectWrapper.getConstructor("DOMParser", DOMPARSER_WRAPPER, jiDomParser);
		ScriptableObject.defineProperty(ws, "DOMParser", domParser, ScriptableObject.READONLY);

		Function xmlserial = JavaObjectWrapper.getConstructor("XMLSerializer", XMLSERIALIZER_WRAPPER, jiXMLSerializer);
		ScriptableObject.defineProperty(ws, "XMLSerializer", xmlserial, ScriptableObject.READONLY);

		// HTML element classes
		this.defineElementClass(ws, doc, "Image", "img", HTMLImageElementImpl.class);
		this.defineElementClass(ws, doc, "Script", "script", HTMLScriptElementImpl.class);
		this.defineElementClass(ws, doc, "IFrame", "iframe", HTMLIFrameElementImpl.class);
		this.defineElementClass(ws, doc, "Option", "option", HTMLOptionElementImpl.class);
		this.defineElementClass(ws, doc, "Select", "select", HTMLSelectElementImpl.class);
	}
	
	private final void defineElementClass(Scriptable scope, final Document document, final String jsClassName,  final String elementName, Class<?> javaClass) {
		JavaInstantiator ji = () -> {
			Document d = document;
			if (d == null) {
				throw new IllegalStateException("Document not set in current context.");
			}
			return d.createElement(elementName);
		};
		JavaClassWrapper classWrapper = JavaClassWrapperFactory.getInstance().getClassWrapper(javaClass);
		Function constructorFunction = JavaObjectWrapper.getConstructor(jsClassName, classWrapper, ji);
		ScriptableObject.defineProperty(scope, jsClassName, constructorFunction, ScriptableObject.READONLY);
	}
	
	/**
	 * Clear state.
	 */
	private void clearState() {
		Context.enter();
		try {
			Scriptable s = this.getWindowScope();
			if (s != null) {
				Object[] ids = s.getIds();
				for (Object id : ids) {
					if (id instanceof String) {
						s.delete((String) id);
					} else if (id instanceof Integer) {
						s.delete(((Integer) id).intValue());
					}
				}
			}
		} finally {
			Context.exit();
		}
	}

	/**
	 * @return the document
	 */
	public HTMLDocumentImpl getWindowDocument() {
		return document;
	}
	
	/**
	 * Gets the onclick.
	 *
	 * @return the onclick
	 */
	public Function getOnclick() {

		Document doc = this.getWindowDocument();
		if (doc instanceof HTMLDocumentImpl) {
			return ((HTMLDocumentImpl) doc).getOnclick();
		} else {
			return null;
		}
	}

	/**
	 * Sets the onclick.
	 *
	 * @param onclick
	 *            the new onclick
	 */
	public void setOnclick(Function onclick) {
		Document doc = this.getWindowDocument();
		if (doc instanceof HTMLDocumentImpl) {
			((HTMLDocumentImpl) doc).setOnclick(onclick);

		}
	}

	/**
	 * Gets the ondblclick.
	 *
	 * @return the ondblclick
	 */
	public Function getOndblclick() {
		Document doc = this.getWindowDocument();
		if (doc instanceof HTMLDocumentImpl) {
			return ((HTMLDocumentImpl) doc).getOndblclick();
		} else {
			return null;
		}
	}

	/**
	 * Sets the ondblclick.
	 *
	 * @param ondblclick
	 *            the new ondblclick
	 */
	public void setOndblclick(Function ondblclick) {
		Document doc = this.getWindowDocument();
		if (doc instanceof HTMLDocumentImpl) {
			((HTMLDocumentImpl) doc).setOndblclick(ondblclick);

		}
	}

	/**
	 * Gets the onkeydown.
	 *
	 * @return the onkeydown
	 */
	public Function getOnkeydown() {
		Document doc = this.getWindowDocument();
		if (doc instanceof HTMLDocumentImpl) {
			return ((HTMLDocumentImpl) doc).getOnkeydown();
		} else {
			return null;
		}
	}

	/**
	 * Sets the onkeydown.
	 *
	 * @param onkeydown
	 *            the new onkeydown
	 */
	public void setOnkeydown(Function onkeydown) {
		Document doc = this.getWindowDocument();
		if (doc instanceof HTMLDocumentImpl) {
			((HTMLDocumentImpl) doc).setOnkeydown(onkeydown);
		}
	}

	/**
	 * Gets the onkeypress.
	 *
	 * @return the onkeypress
	 */
	public Function getOnkeypress() {
		Document doc = this.getWindowDocument();
		if (doc instanceof HTMLDocumentImpl) {
			return ((HTMLDocumentImpl) doc).getOnkeypress();
		} else {
			return null;
		}
	}

	/**
	 * Sets the onkeypress.
	 *
	 * @param onkeypress
	 *            the new onkeypress
	 */
	public void setOnkeypress(Function onkeypress) {
		Document doc = this.getWindowDocument();
		if (doc instanceof HTMLDocumentImpl) {
			((HTMLDocumentImpl) doc).setOnkeypress(onkeypress);
		}
	}

	/**
	 * Gets the onkeyup.
	 *
	 * @return the onkeyup
	 */
	public Function getOnkeyup() {
		Document doc = this.getWindowDocument();
		if (doc instanceof HTMLDocumentImpl) {
			return ((HTMLDocumentImpl) doc).getOnkeyup();
		} else {
			return null;
		}
	}

	/**
	 * Sets the onkeyup.
	 *
	 * @param onkeyup
	 *            the new onkeyup
	 */
	public void setOnkeyup(Function onkeyup) {
		Document doc = this.getWindowDocument();
		if (doc instanceof HTMLDocumentImpl) {
			((HTMLDocumentImpl) doc).setOnkeyup(onkeyup);
		}
	}

	/**
	 * Gets the onmousedown.
	 *
	 * @return the onmousedown
	 */
	public Function getOnmousedown() {
		Document doc = this.getWindowDocument();
		if (doc instanceof HTMLDocumentImpl) {
			return ((HTMLDocumentImpl) doc).getOnmousedown();
		} else {
			return null;
		}
	}

	/**
	 * Sets the onmousedown.
	 *
	 * @param onmousedown
	 *            the new onmousedown
	 */
	public void setOnmousedown(Function onmousedown) {
		Document doc = this.getWindowDocument();
		if (doc instanceof HTMLDocumentImpl) {
			((HTMLDocumentImpl) doc).setOnmousedown(onmousedown);
		}
	}

	/**
	 * Gets the onmouseover.
	 *
	 * @return the onmouseover
	 */
	public Function getOnmouseover() {
		Document doc = this.getWindowDocument();
		if (doc instanceof HTMLDocumentImpl) {
			return ((HTMLDocumentImpl) doc).getOnmouseover();
		} else {
			return null;
		}
	}

	/**
	 * Sets the onmouseover.
	 *
	 * @param onmouseover
	 *            the new onmouseover
	 */
	public void setOnmouseover(Function onmouseover) {
		Document doc = this.getWindowDocument();
		if (doc instanceof HTMLDocumentImpl) {
			((HTMLDocumentImpl) doc).setOnmouseover(onmouseover);
		}
	}

	/**
	 * Gets the onmouseup.
	 *
	 * @return the onmouseup
	 */
	public Function getOnmouseup() {
		Document doc = this.getWindowDocument();
		if (doc instanceof HTMLDocumentImpl) {
			return ((HTMLDocumentImpl) doc).getOnmouseup();
		} else {
			return null;
		}
	}

	/**
	 * Sets the onmouseup.
	 *
	 * @param onmouseup
	 *            the new onmouseup
	 */
	public void setOnmouseup(Function onmouseup) {
		Document doc = this.getWindowDocument();
		if (doc instanceof HTMLDocumentImpl) {
			((HTMLDocumentImpl) doc).setOnmouseup(onmouseup);
		}
	}
	
	/**
	 * Gets the onload.
	 *
	 * @return the onload
	 */
	public Function getOnload() {
		Document doc = this.getWindowDocument();
		if (doc instanceof HTMLDocumentImpl) {
			return ((HTMLDocumentImpl) doc).getOnloadHandler();
		} else {
			return null;
		}
	}

	/**
	 * Sets the onload.
	 *
	 * @param onload
	 *            the new onload
	 */
	public void setOnload(Function onload) {
		Document doc = this.getWindowDocument();
		if (doc instanceof HTMLDocumentImpl) {
			((HTMLDocumentImpl) doc).setOnloadHandler(onload);
		}
	}

}
