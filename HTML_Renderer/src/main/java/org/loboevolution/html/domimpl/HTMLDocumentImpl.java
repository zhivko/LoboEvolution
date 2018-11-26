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

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.SocketPermission;
import java.net.URL;
import java.net.URLConnection;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.loboevolution.arraylist.ArrayUtilities;
import org.loboevolution.html.HtmlRendererContext;
import org.loboevolution.html.dombl.DocumentNotificationListener;
import org.loboevolution.html.dombl.ImageEvent;
import org.loboevolution.html.dombl.ImageListener;
import org.loboevolution.html.domfilter.AnchorFilter;
import org.loboevolution.html.domfilter.AppletFilter;
import org.loboevolution.html.domfilter.CommandFilter;
import org.loboevolution.html.domfilter.ElementNameFilter;
import org.loboevolution.html.domfilter.EmbedFilter;
import org.loboevolution.html.domfilter.FormFilter;
import org.loboevolution.html.domfilter.FrameFilter;
import org.loboevolution.html.domfilter.HeadFilter;
import org.loboevolution.html.domfilter.ImageFilter;
import org.loboevolution.html.domfilter.LinkFilter;
import org.loboevolution.html.domfilter.PluginsFilter;
import org.loboevolution.html.domfilter.ScriptFilter;
import org.loboevolution.html.info.ImageInfo;
import org.loboevolution.html.io.WritableLineReader;
import org.loboevolution.html.js.Executor;
import org.loboevolution.html.js.object.Location;
import org.loboevolution.html.js.object.Window;
import org.loboevolution.html.parser.HtmlParser;
import org.loboevolution.html.style.StyleSheetAggregator;
import org.loboevolution.http.Domains;
import org.loboevolution.http.HttpRequest;
import org.loboevolution.http.Method;
import org.loboevolution.http.ReadyState;
import org.loboevolution.http.SSLCertificate;
import org.loboevolution.http.Urls;
import org.loboevolution.http.UserAgentContext;
import org.loboevolution.util.io.EmptyReader;
import org.loboevolution.w3c.events.EventTarget;
import org.loboevolution.w3c.html.HTMLCollection;
import org.loboevolution.w3c.html.HTMLDocument;
import org.loboevolution.w3c.html.HTMLElement;
import org.loboevolution.w3c.html.HTMLHeadElement;
import org.w3c.dom.DOMException;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.css.CSSStyleSheet;
import org.w3c.dom.stylesheets.StyleSheetList;
import org.w3c.dom.views.AbstractView;
import org.w3c.dom.views.DocumentView;
import org.xml.sax.SAXException;

import com.gargoylesoftware.css.dom.CSSStyleSheetListImpl;

/**
 * The Class HTMLDocumentImpl.
 */
public class HTMLDocumentImpl extends DocumentImpl implements HTMLDocument, DocumentView, EventTarget{

	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(HTMLDocumentImpl.class);

	/** The window. */
	private final Window window;

	/** The style sheets. */
	private final CSSStyleSheetListImpl styleSheets = new CSSStyleSheetListImpl();

	/** The image infos. */
	private final Map<String, ImageInfo> imageInfos = new HashMap<String, ImageInfo>(4);

	/** The document notification listeners. */
	private final ArrayList<DocumentNotificationListener> documentNotificationListeners = new ArrayList<DocumentNotificationListener>(1);

	/** The blank image event. */
	private final ImageEvent BLANK_IMAGE_EVENT = new ImageEvent(this, null);

	/** The document url. */
	private URL documentURL;

	/** The body. */
	private HTMLElement body;
	
	/** The images. */
	private HTMLCollection images;

	/** The applets. */
	private HTMLCollection applets;

	/** The links. */
	private HTMLCollection links;

	/** The forms. */
	private HTMLCollection forms;

	/** The anchors. */
	private HTMLCollection anchors;

	/** The frames. */
	private HTMLCollection frames;

	/** The embeds. */
	private HTMLCollection embeds;

	/** The scripts. */
	private HTMLCollection scripts;

	/** The plugins. */
	private HTMLCollection plugins;

	/** The commands. */
	private HTMLCollection commands;

	/** The style sheet aggregator. */
	private StyleSheetAggregator styleSheetAggregator = null;

	/** The locales. */
	private Set<?> locales;

	/** The base uri. */
	private volatile String baseURI;

	/** The default target. */
	private String defaultTarget;

	/** The title. */
	private String title;
	/** The referrer. */
	private String referrer;

	/** The domain. */
	private String domain;

	/**
	 * Instantiates a new HTML document impl.
	 *
	 * @param rcontext
	 *            the rcontext
	 */
	public HTMLDocumentImpl(HtmlRendererContext rcontext) {
		this(rcontext.getUserAgentContext(), rcontext, null, null);
	}

	/**
	 * Instantiates a new HTML document impl.
	 *
	 * @param ucontext
	 *            the ucontext
	 */
	public HTMLDocumentImpl(UserAgentContext ucontext) {
		this(ucontext, null, null, null);
	}

	/**
	 * Instantiates a new HTML document impl.
	 *
	 * @param ucontext
	 *            the ucontext
	 * @param rcontext
	 *            the rcontext
	 * @param reader
	 *            the reader
	 * @param documentURI
	 *            the document uri
	 */
	public HTMLDocumentImpl(final UserAgentContext ucontext, final HtmlRendererContext rcontext,
			WritableLineReader reader, String documentURI) {
		this.rcontext = rcontext;
		this.ucontext = ucontext;
		this.reader = reader;
		this.setDocumentURI(documentURI);

		if (documentURI != null) {
			try {
				URL docURL = new URL(documentURI);
				SecurityManager sm = System.getSecurityManager();
				if (sm != null) {
					// Do not allow creation of HTMLDocumentImpl if there's
					// no permission to connect to the host of the URL.
					// This is so that cookies cannot be written arbitrarily
					// with setCookie() method.
					sm.checkPermission(new SocketPermission(docURL.getHost(), "connect"));
				}
				this.documentURL = docURL;
				this.domain = docURL.getHost();
			} catch (MalformedURLException mfu) {
				logger.warn("HTMLDocumentImpl(): Document URI [" + documentURI + "] is malformed.");
			}
		}
		this.document = this;
		// Get Window object
		Window windowObject;
		if (rcontext != null) {
			windowObject = Window.getWindow(rcontext);
			windowObject.setDocument(this);
			// Set up Javascript scope
			this.setUserData(Executor.SCOPE_KEY, windowObject.getWindowScope(), null);
		} else {
			// Plain parsers may use Javascript too.
			windowObject = null;// new Window(null, ucontext);
		}
		this.window = windowObject;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.loboevolution.w3c.html.HTMLDocument#getCookie()
	 */
	@Override
	public String getCookie() {
		SecurityManager sm = System.getSecurityManager();
		if (sm != null) {
			return (String) AccessController
					.doPrivileged((PrivilegedAction<Object>) () -> ucontext.getCookie(documentURL));
		} else {
			return this.ucontext.getCookie(this.documentURL);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.loboevolution.w3c.html.HTMLDocument#setCookie(java.lang.String)
	 */
	@Override
	public void setCookie(final String cookie) throws DOMException {
		SecurityManager sm = System.getSecurityManager();
		if (sm != null) {
			AccessController.doPrivileged((PrivilegedAction<Object>) () -> {
				ucontext.setCookie(documentURL, cookie);
				return null;
			});
		} else {
			this.ucontext.setCookie(this.documentURL, cookie);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.loboevolution.w3c.html.HTMLDocument#open()
	 */
	@Override
	public void open() {
		synchronized (this.getTreeLock()) {
			if (this.reader != null) {
				if (this.reader instanceof LocalWritableLineReader) {
					try {
						this.reader.close();
					} catch (IOException ioe) {
						logger.error(ioe);
					}
					this.reader = null;
				} else {
					// Already open, return.
					// Do not close http/file documents in progress.
					return;
				}
			}
			this.removeAllChildrenImpl();
			this.reader = new LocalWritableLineReader(new EmptyReader());
		}
	}

	/**
	 * Loads the document from the reader provided when the current instance of
	 * <code>HTMLDocumentImpl</code> was constructed. It then closes the reader.
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws SAXException
	 *             the SAX exception
	 * @throws UnsupportedEncodingException
	 *             the unsupported encoding exception
	 */
	public void load() throws IOException, SAXException, UnsupportedEncodingException {
		this.load(true);
	}

	/**
	 * Load.
	 *
	 * @param closeReader
	 *            the close reader
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws SAXException
	 *             the SAX exception
	 * @throws UnsupportedEncodingException
	 *             the unsupported encoding exception
	 */
	public void load(boolean closeReader) throws IOException, SAXException, UnsupportedEncodingException {
		WritableLineReader read;
		synchronized (this.getTreeLock()) {
			this.removeAllChildrenImpl();
			this.setTitle(null);
			this.setBaseURI(null);
			this.setDefaultTarget(null);
			this.styleSheets.getCSSStyleSheets().clear();
			this.styleSheetAggregator = null;
			read = this.reader;
		}
		if (read != null) {
			try {
				HtmlParser parser = new HtmlParser(this.ucontext, this);
				parser.parse(read);
			} finally {
				if (closeReader) {
					try {
						read.close();
					} catch (Exception err) {
						logger.error("load(): Unable to close stream", err);
					}
					synchronized (this.getTreeLock()) {
						this.reader = null;
					}
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.loboevolution.w3c.html.HTMLDocument#close()
	 */
	@Override
	public void close() {
		synchronized (this.getTreeLock()) {
			if (this.reader instanceof LocalWritableLineReader) {
				try {
					this.reader.close();
				} catch (IOException ioe) {
					logger.error(ioe);
				}
				this.reader = null;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.loboevolution.w3c.html.HTMLDocument#write(java.lang.String)
	 */
	@Override
	public void write(String text) {
		synchronized (this.getTreeLock()) {
			if (this.reader != null) {
				try {
					// This can end up in openBufferChanged
					this.reader.write(text);
				} catch (IOException ioe) {
					logger.error(ioe);
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.loboevolution.w3c.html.HTMLDocument#writeln(java.lang.String)
	 */
	@Override
	public void writeln(String text) {
		synchronized (this.getTreeLock()) {
			if (this.reader != null) {
				try {
					// This can end up in openBufferChanged
					this.reader.write(text + "\r\n");
				} catch (IOException ioe) {
					logger.error(ioe);
				}
			}
		}
	}

	/**
	 * Open buffer changed.
	 *
	 * @param text
	 *            the text
	 */
	private void openBufferChanged(String text) {
		// Assumed to execute in a lock
		// Assumed that text is not broken up HTML.
		HtmlParser parser = new HtmlParser(this.ucontext, this);
		StringReader strReader = new StringReader(text);
		try {
			// This sets up another Javascript scope Window. Does it matter?
			parser.parse(strReader);
		} catch (Exception err) {
			logger.error("Unable to parse written HTML text. BaseURI=[" + this.getBaseURI() + "].", err);
		}
	}

	/**
	 * Gets the collection of elements whose <code>name</code> attribute is
	 * <code>elementName</code>.
	 *
	 * @param elementName
	 *            the element name
	 * @return the elements by name
	 */
	@Override
	public NodeList getElementsByName(String elementName) {
		return new HTMLCollectionImpl(this,new ElementNameFilter(elementName)).nodeList();
	}
	
	/*
	 * (non-Javadoc)
	 *
	 * @see org.loboevolution.html.domimpl.DOMNodeImpl#getHtmlRendererContext()
	 */
	@Override
	public final HtmlRendererContext getHtmlRendererContext() {
		return this.rcontext;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.loboevolution.html.domimpl.DOMNodeImpl#getUserAgentContext()
	 */
	@Override
	public UserAgentContext getUserAgentContext() {
		return this.ucontext;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.loboevolution.html.domimpl.DOMNodeImpl#getFullURL(java.lang.String)
	 */
	@Override
	public final URL getFullURL(String uri) {
		try {
			String bsURI = this.getBaseURI();
			URL docURL = bsURI == null ? null : new URL(bsURI);
			return Urls.createURL(docURL, uri);
		} catch (MalformedURLException | UnsupportedEncodingException mfu) {
			// Try agan, without the baseURI.
			try {
				return new URL(uri);
			} catch (MalformedURLException mfu2) {
				logger.error("Unable to create URL for URI=[" + uri + "], with base=[" + this.getBaseURI() + "].", mfu);
				return null;
			}
		}
	}

	/**
	 * Gets the location.
	 *
	 * @return the location
	 */
	@Override
	public final Location getLocation() {
		return this.window.getLocation();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.loboevolution.w3c.html.HTMLDocument#getURL()
	 */
	@Override
	public String getURL() {
		return this.getDocumentURI();
	}

	/**
	 * Adds the style sheet.
	 *
	 * @param ss
	 *            the ss
	 */
	public final void addStyleSheet(CSSStyleSheet ss) {
		synchronized (this.getTreeLock()) {
			this.styleSheets.add(ss);
			this.styleSheetAggregator = null;
			this.forgetRenderState();
			ArrayList<Node> nl = this.nodeList;
			if (ArrayUtilities.isNotBlank(nl)) {
				for (Node node : nl) {
					if (node instanceof HTMLElementImpl) {
						((HTMLElementImpl) node).forgetStyle(true);
					}
				}
			}
		}
		this.allInvalidated();
	}

	/**
	 * All invalidated.
	 *
	 * @param forgetRenderStates
	 *            the forget render states
	 */
	public void allInvalidated(boolean forgetRenderStates) {
		if (forgetRenderStates) {
			synchronized (this.getTreeLock()) {
				this.styleSheetAggregator = null;
				this.forgetRenderState();
				ArrayList<Node> nl = this.nodeList;
				if (ArrayUtilities.isNotBlank(nl)) {
					for (Node node : nl) {
						if (node instanceof HTMLElementImpl) {
							((HTMLElementImpl) node).forgetStyle(true);
						}
					}
				}
			}
		}
		this.allInvalidated();
	}

	/**
	 * Gets the style sheets.
	 *
	 * @return the style sheets
	 */
	public StyleSheetList getStyleSheets() {
		return this.styleSheets;
	}

	/**
	 * Gets the style sheet aggregator.
	 *
	 * @return the style sheet aggregator
	 */
	public final StyleSheetAggregator getStyleSheetAggregator() {
		synchronized (this.getTreeLock()) {
			StyleSheetAggregator ssa = this.styleSheetAggregator;
			if (ssa == null) {
				ssa = new StyleSheetAggregator(this);
				try {
					ssa.addStyleSheets(this.styleSheets.getCSSStyleSheets());
				} catch (MalformedURLException | UnsupportedEncodingException mfu) {
					logger.error("getStyleSheetAggregator()", mfu);
				}
				this.styleSheetAggregator = ssa;
			}
			return ssa;
		}
	}

	/**
	 * Adds a document notification listener, which is informed about changes to
	 * the document.
	 *
	 * @param listener
	 *            An instance of {@link DocumentNotificationListener}.
	 */
	public void addDocumentNotificationListener(DocumentNotificationListener listener) {
		ArrayList<DocumentNotificationListener> listenersList = this.documentNotificationListeners;
		synchronized (listenersList) {
			listenersList.add(listener);
		}
	}

	/**
	 * Removes the document notification listener.
	 *
	 * @param listener
	 *            the listener
	 */
	public void removeDocumentNotificationListener(DocumentNotificationListener listener) {
		ArrayList<DocumentNotificationListener> listenersList = this.documentNotificationListeners;
		synchronized (listenersList) {
			listenersList.remove(listener);
		}
	}

	/**
	 * Size invalidated.
	 *
	 * @param node
	 *            the node
	 */
	public void sizeInvalidated(DOMNodeImpl node) {
		ArrayList<DocumentNotificationListener> listenersList = this.documentNotificationListeners;
		for (DocumentNotificationListener dnl : listenersList) {
			dnl.sizeInvalidated(node);
		}
	}
   
	/**
	 * Called if something such as a color or decoration has changed. This would
	 * be something which does not affect the rendered size, and can be
	 * revalidated with a simple repaint.
	 *
	 * @param node
	 *            the node
	 */
	public void lookInvalidated(DOMNodeImpl node) {
		ArrayList<DocumentNotificationListener> listenersList = this.documentNotificationListeners;
		for (DocumentNotificationListener dnl : listenersList) {
			dnl.lookInvalidated(node);
		}
	}

	/**
	 * Changed if the position of the node in a parent has changed.
	 *
	 * @param node
	 *            the node
	 */
	public void positionInParentInvalidated(DOMNodeImpl node) {
		ArrayList<DocumentNotificationListener> listenersList = this.documentNotificationListeners;
		for (DocumentNotificationListener dnl : listenersList) {
			dnl.positionInvalidated(node);
		}
	}

	/**
	 * This is called when the node has changed, but it is unclear if it's a
	 * size change or a look change. An attribute change should trigger this.
	 *
	 * @param node
	 *            the node
	 */
	public void invalidated(DOMNodeImpl node) {
		ArrayList<DocumentNotificationListener> listenersList = this.documentNotificationListeners;
		for (DocumentNotificationListener dnl : listenersList) {
			dnl.invalidated(node);
		}
	}

	/**
	 * This is called when children of the node might have changed.
	 *
	 * @param node
	 *            the node
	 */
	public void structureInvalidated(DOMNodeImpl node) {
		ArrayList<DocumentNotificationListener> listenersList = this.documentNotificationListeners;
		for (DocumentNotificationListener dnl : listenersList) {
			dnl.structureInvalidated(node);
		}
	}

	/**
	 * Node loaded.
	 *
	 * @param node
	 *            the node
	 */
	public void nodeLoaded(DOMNodeImpl node) {
		ArrayList<DocumentNotificationListener> listenersList = this.documentNotificationListeners;
		for (DocumentNotificationListener dnl : listenersList) {
			dnl.nodeLoaded(node);
		}
	}

	/**
	 * External script loading.
	 *
	 * @param node
	 *            the node
	 */
	public void externalScriptLoading(DOMNodeImpl node) {
		ArrayList<DocumentNotificationListener> listenersList = this.documentNotificationListeners;
		for (DocumentNotificationListener dnl : listenersList) {
			dnl.externalScriptLoading(node);
		}
	}

	/**
	 * Informs listeners that the whole document has been invalidated.
	 */
	public void allInvalidated() {
		ArrayList<DocumentNotificationListener> listenersList = this.documentNotificationListeners;
		for (DocumentNotificationListener dnl : listenersList) {
			dnl.allInvalidated();
		} 
	}

	/**
	 * Loads images asynchronously such that they are shared if loaded
	 * simultaneously from the same URI. Informs the listener immediately if an
	 * image is already known.
	 *
	 * @param relativeUri
	 *            the relative uri
	 * @param imageListener
	 *            the image listener
	 */
	protected void loadImage(String relativeUri, ImageListener imageListener) {
		HtmlRendererContext rctext = this.getHtmlRendererContext();
		if (rctext == null || !rctext.isImageLoadingEnabled()) {
			// ignore image loading when there's no renderer context.
			// Consider Cobra users who are only using the parser.
			imageListener.imageLoaded(BLANK_IMAGE_EVENT);
			return;
		}
		final URL url = this.getFullURL(relativeUri);
		if (url == null) {
			imageListener.imageLoaded(BLANK_IMAGE_EVENT);
			return;
		}
		final String urlText = url.toExternalForm();
		final Map<String, ImageInfo> map = this.imageInfos;
		ImageEvent event = null;
		synchronized (map) {
			ImageInfo info = map.get(urlText);
			if (info != null) {
				if (info.loaded) {
					event = info.imageEvent;
				} else {
					info.addListener(imageListener);
				}
			} else {
				UserAgentContext uac = rctext.getUserAgentContext();
				final HttpRequest httpRequest = uac.createHttpRequest();
				final ImageInfo newInfo = new ImageInfo();
				map.put(urlText, newInfo);
				newInfo.addListener(imageListener);
				httpRequest.addReadyStateChangeListener(evt -> {
					if (httpRequest.getReadyState() == ReadyState.COMPLETE) {
						java.awt.Image newImage = httpRequest.getResponseImage();
						ImageEvent newEvent = newImage == null ? null : new ImageEvent(HTMLDocumentImpl.this, newImage);
						ImageListener[] listeners;
						synchronized (map) {
							newInfo.imageEvent = newEvent;
							newInfo.loaded = true;
							listeners = newEvent == null ? null : newInfo.getListeners();
							map.remove(urlText);
						}
						if (listeners != null) {
							int llength = listeners.length;
							for (int i = 0; i < llength; i++) {
								listeners[i].imageLoaded(newEvent);
							}
						}
					}

				});
				SecurityManager sm = System.getSecurityManager();
				if (sm == null) {
					httpRequest.open(Method.GET, url, true);
					httpRequest.send();
				} else {
					AccessController.doPrivileged((PrivilegedAction<Object>) () -> {
						httpRequest.open(Method.GET, url, true);
						httpRequest.send();
						return null;
					});
				}
			}
		}
		if (event != null) {
			// Call holding no locks.
			imageListener.imageLoaded(event);
		}
	}
	
	/**
	 * The Class LocalWritableLineReader.
	 */
	private class LocalWritableLineReader extends WritableLineReader {

		/**
		 * Instantiates a new local writable line reader.
		 *
		 * @param reader
		 *            the reader
		 */
		public LocalWritableLineReader(LineNumberReader reader) {
			super(reader);
		}

		/**
		 * Instantiates a new local writable line reader.
		 *
		 * @param reader
		 *            the reader
		 */
		public LocalWritableLineReader(Reader reader) {
			super(reader);
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see
		 * org.loboevolution.html.io.WritableLineReader#write(java.lang.String)
		 */
		@Override
		public void write(String text) throws IOException {
			super.write(text);
			if ("".equals(text)) {
				openBufferChanged(text);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.loboevolution.w3c.html.HTMLDocument#getBody()
	 */
	@Override
	public HTMLElement getBody() {
		synchronized (this) {
			return this.body;
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.loboevolution.w3c.html.HTMLDocument#setBody(org.loboevolution.w3c.html.
	 * HTMLElement)
	 */
	@Override
	public void setBody(HTMLElement body) {
		synchronized (this) {
			this.body = body;
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.loboevolution.w3c.html.HTMLDocument#getReferrer()
	 */
	@Override
	public String getReferrer() {
		return this.referrer;
	}

	/**
	 * Sets the referrer.
	 *
	 * @param value
	 *            the new referrer
	 */
	public void setReferrer(String value) {
		this.referrer = value;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.loboevolution.w3c.html.HTMLDocument#getDomain()
	 */
	@Override
	public String getDomain() {
		return this.domain;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.loboevolution.w3c.html.HTMLDocument#setDomain(java.lang.String)
	 */
	@Override
	public void setDomain(String domain) {
		String oldDomain = this.domain;
		if (oldDomain != null && Domains.isValidCookieDomain(domain, oldDomain)) {
			this.domain = domain;
		} else {
			throw new SecurityException(
					"Cannot set domain to '" + domain + "' when current domain is '" + oldDomain + "'");
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.loboevolution.w3c.html.HTMLDocument#getImages()
	 */
	@Override
	public HTMLCollection getImages() {
		synchronized (this) {
			if (this.images == null) {
				this.images = new HTMLCollectionImpl(this,new ImageFilter());
			}
			return this.images;
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.loboevolution.w3c.html.HTMLDocument#getApplets()
	 */
	@Override
	public HTMLCollection getApplets() {
		synchronized (this) {
			if (this.applets == null) {
				this.applets = new HTMLCollectionImpl(this,new AppletFilter());
			}
			return this.applets;
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.loboevolution.w3c.html.HTMLDocument#getLinks()
	 */
	@Override
	public HTMLCollection getLinks() {
		synchronized (this) {
			if (this.links == null) {
				this.links =  new HTMLCollectionImpl(this,new LinkFilter());
			}
			return this.links;
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.loboevolution.w3c.html.HTMLDocument#getForms()
	 */
	@Override
	public HTMLCollection getForms() {
		synchronized (this) {
			if (this.forms == null) {
				this.forms = new HTMLCollectionImpl(this,new FormFilter());
			}
			return this.forms;
		}
	}

	/**
	 * Gets the frames.
	 *
	 * @return the frames
	 */
	public HTMLCollection getFrames() {
		synchronized (this) {
			if (this.frames == null) {
				this.frames = new HTMLCollectionImpl(this,new FrameFilter());
			}
			return this.frames;
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.loboevolution.w3c.html.HTMLDocument#getAnchors()
	 */
	@Override
	public HTMLCollection getAnchors() {
		synchronized (this) {
			if (this.anchors == null) {
				this.anchors = new HTMLCollectionImpl(this,new AnchorFilter());
			}
			return this.anchors;
		}
	}
	
	/*
	 * (non-Javadoc)
	 *
	 * @see org.loboevolution.w3c.html.HTMLDocument#getEmbeds()
	 */
	@Override
	public HTMLCollection getEmbeds() {
		synchronized (this) {
			if (this.embeds == null) {
				this.embeds = new HTMLCollectionImpl(this,new EmbedFilter());
			}
			return this.embeds;
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.loboevolution.w3c.html.HTMLDocument#getPlugins()
	 */
	@Override
	public HTMLCollection getPlugins() {
		synchronized (this) {
			if (this.plugins == null) {
				this.plugins = new HTMLCollectionImpl(this,new PluginsFilter());
			}
			return this.plugins;
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.loboevolution.w3c.html.HTMLDocument#getScripts()
	 */
	@Override
	public HTMLCollection getScripts() {
		synchronized (this) {
			if (this.scripts == null) {
				this.scripts = new HTMLCollectionImpl(this,new ScriptFilter());
			}
			return this.scripts;
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.loboevolution.w3c.html.HTMLDocument#getCommands()
	 */
	@Override
	public HTMLCollection getCommands() {
		synchronized (this) {
			if (this.commands == null) {
				this.commands = new HTMLCollectionImpl(this,new CommandFilter());
			}
			return this.commands;
		}
	}
	
	/*
	 * (non-Javadoc)
	 *
	 * @see org.loboevolution.html.dombl.DOMNodeImpl#getbaseURI()
	 */
	@Override
	public String getBaseURI() {
		String buri = this.baseURI;
		return buri == null ? this.getDocumentURI() : buri;
	}
	
	/*
	 * (non-Javadoc)
	 *
	 * @see org.w3c.dom.views.DocumentView#getDefaultView()
	 */
	@Override
	public AbstractView getDefaultView() {
		return (Window) this.window;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.loboevolution.w3c.html.HTMLDocument#getTitle()
	 */
	@Override
	public String getTitle() {
		return this.title;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.loboevolution.w3c.html.HTMLDocument#setTitle(java.lang.String)
	 */
	@Override
	public void setTitle(String title) {
		this.title = title;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.loboevolution.html.domimpl.DOMNodeImpl#getDocumentURL()
	 */
	@Override
	public URL getDocumentURL() {
		return this.documentURL;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.loboevolution.w3c.html.HTMLDocument#getLastModified()
	 */
	@Override
	public String getLastModified() {

		String result = "";
		try {
			SSLCertificate.setCertificate();
			URL docURL = new URL(this.getDocumentURI());
			URLConnection connection = docURL.openConnection();
			result = connection.getHeaderField("Last-Modified");
		} catch (Exception e) {
			logger.error(e);
		}

		return result;
	}
	
	/*
	 * (non-Javadoc)
	 *
	 * @see org.loboevolution.w3c.html.HTMLDocument#getCompatMode()
	 */
	@Override
	public String getCompatMode() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.loboevolution.w3c.html.HTMLDocument#getHead()
	 */
	@Override
	public HTMLHeadElement getHead() {
		NodeList elementsByName = new HTMLCollectionImpl(this, new HeadFilter()).nodeList();
		if (elementsByName != null && elementsByName.getLength() > 0) {
			return (HTMLHeadElement) elementsByName.item(0);
		} else {
			return null;
		}
	}
	
	/**
	 * Gets the locales.
	 *
	 * @return the locales
	 */
	public Set<?> getLocales() {
		return locales;
	}

	/**
	 * Sets the locales.
	 *
	 * @param locales
	 *            the new locales
	 */
	public void setLocales(Set<?> locales) {
		this.locales = locales;
	}

	/**
	 * Sets the base uri.
	 *
	 * @param value
	 *            the new base uri
	 */
	public void setBaseURI(String value) {
		this.baseURI = value;
	}

	/**
	 * Gets the default target.
	 *
	 * @return the default target
	 */
	public String getDefaultTarget() {
		return this.defaultTarget;
	}

	/**
	 * Sets the default target.
	 *
	 * @param value
	 *            the new default target
	 */
	public void setDefaultTarget(String value) {
		this.defaultTarget = value;
	}
}
