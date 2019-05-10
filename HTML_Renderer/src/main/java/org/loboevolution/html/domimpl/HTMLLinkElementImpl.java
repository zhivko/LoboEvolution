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

import java.net.MalformedURLException;
import java.net.URL;

import org.loboevolution.html.HtmlRendererContext;
import org.loboevolution.html.parser.HtmlParser;
import org.loboevolution.html.style.CSSUtilities;
import org.loboevolution.http.Urls;
import org.loboevolution.http.UserAgentContext;
import org.loboevolution.w3c.html.DOMSettableTokenList;
import org.loboevolution.w3c.html.DOMTokenList;
import org.loboevolution.w3c.html.HTMLLinkElement;
import org.w3c.dom.UserDataHandler;
import org.w3c.dom.css.CSSStyleSheet;

import com.gargoylesoftware.css.dom.CSSStyleSheetImpl;

/**
 * The Class HTMLLinkElementImpl.
 */
public class HTMLLinkElementImpl extends HTMLAbstractUIElement implements HTMLLinkElement {

	/** The style sheet. */
	private CSSStyleSheetImpl styleSheet;

	/** The disabled. */
	private boolean disabled;

	/**
	 * Instantiates a new HTML link element impl.
	 *
	 * @param name
	 *            the name
	 */
	public HTMLLinkElementImpl(String name) {
		super(name);
	}

	@Override
	public boolean getDisabled() {
		return this.disabled;
	}

	@Override
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
		CSSStyleSheetImpl sheet = this.styleSheet;
		if (sheet != null) {
			sheet.setDisabled(disabled);
		}
	}

	@Override
	public String getHref() {
		String href = this.getAttribute(HREF);
		return href == null ? "" : Urls.removeControlCharacters(href);
	}

	@Override
	public void setHref(String href) {
		this.setAttribute(HREF, href);
	}

	@Override
	public String getHreflang() {
		return this.getAttribute(HREFLANG);
	}

	@Override
	public void setHreflang(String hreflang) {
		this.setAttribute(HREFLANG, hreflang);
	}

	@Override
	public String getMedia() {
		return this.getAttribute(MEDIA);
	}

	@Override
	public void setMedia(String media) {
		this.setAttribute(MEDIA, media);
	}

	@Override
	public String getRel() {
		return this.getAttribute(REL);
	}

	@Override
	public void setRel(String rel) {
		this.setAttribute(REL, rel);
	}

	@Override
	public String getRev() {
		return this.getAttribute(REV);
	}

	@Override
	public void setRev(String rev) {
		this.setAttribute(REV, rev);
	}

	@Override
	public String getTarget() {
		String target = this.getAttribute(TARGET);
		if (target != null) {
			return target;
		}
		HTMLDocumentImpl doc = (HTMLDocumentImpl) this.document;
		return doc == null ? null : doc.getDefaultTarget();
	}

	@Override
	public void setTarget(String target) {
		this.setAttribute(TARGET, target);
	}

	@Override
	public String getType() {
		return this.getAttribute(TYPE);
	}

	@Override
	public void setType(String type) {
		this.setAttribute(TYPE, type);
	}

	@Override
	public Object setUserData(String key, Object data, UserDataHandler handler) {
		if (HtmlParser.MODIFYING_KEY.equals(key) && data != Boolean.TRUE) {
			this.processLink();
		} else if ("styleSheet.disabled.changed".equals(key)) {
			this.informDocumentInvalid();
		}
		return super.setUserData(key, data, handler);
	}

	/**
	 * If the LINK refers to a stylesheet document, this method loads and parses
	 * it.
	 */
	protected void processLink() {
		this.styleSheet = null;
		String rel = this.getAttribute(REL);
		if (rel != null) {
			String cleanRel = rel.trim().toLowerCase();
			boolean isStyleSheet = "stylesheet".equals(cleanRel);
			boolean isAltStyleSheet = "alternate stylesheet".equals(cleanRel);
			boolean isAppendixStyleSheet = "appendix stylesheet".equals(cleanRel);
			if (isStyleSheet || isAltStyleSheet || isAppendixStyleSheet) {
				UserAgentContext uacontext = this.getUserAgentContext();
				if (uacontext.isExternalCSSEnabled()) {
					String media = this.getMedia();
					if (CSSUtilities.matchesMedia(media, uacontext)) {
						HTMLDocumentImpl doc = (HTMLDocumentImpl) this.getOwnerDocument();
						try {

							CSSStyleSheetImpl sheet = CSSUtilities.parse(this.getHref(), doc);
							CSSStyleSheetImpl sheetImpl = null;
							if (sheet != null) {
								doc.addStyleSheet(sheet);
								this.styleSheet = sheet;
								if (sheet instanceof CSSStyleSheetImpl) {
									sheetImpl = (CSSStyleSheetImpl) sheet;
									if (isAltStyleSheet) {
										sheetImpl.setDisabled(true);
									} else {
										sheetImpl.setDisabled(disabled);
									}

									URL baseURL = new URL(doc.getBaseURI());
									URL scriptURL = Urls.createURL(baseURL, getHref());
									if (scriptURL != null) {
										sheetImpl.setHref(scriptURL.toString());
									}
									doc.addStyleSheet(sheetImpl);
								} else {
									if (isAltStyleSheet) {
										sheet.setDisabled(true);
									} else {
										sheet.setDisabled(this.disabled);
									}
									doc.addStyleSheet(sheet);
								}

							}

						} catch (MalformedURLException mfe) {
							logger.error("Will not parse CSS. URI=[" + this.getHref() + "] with BaseURI=["
									+ doc.getBaseURI() + "] does not appear to be a valid URI.");
						} catch (Throwable err) {
							logger.error("Unable to parse CSS. URI=[" + this.getHref() + "].", err);
						}
					}
				}
			}
		}
	}

	/**
	 * Gets the absolute href.
	 *
	 * @return the absolute href
	 */
	public String getAbsoluteHref() {
		HtmlRendererContext rcontext = this.getHtmlRendererContext();
		if (rcontext != null) {
			String href = this.getHref();
			if (href != null && href.length() > 0) {
				try {
					URL url = this.getFullURL(href);
					return url == null ? null : url.toExternalForm();
				} catch (MalformedURLException mfu) {
					logger.error("Malformed URI: [" + href + "].", mfu);
				}
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return this.getHref();
	}

	@Override
	public DOMTokenList getRelList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DOMSettableTokenList getSizes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSizes(String sizes) {
		this.setAttribute(SIZES, sizes);

	}

	/**
	 * Gets the sheet.
	 *
	 * @return the sheet
	 */
	public CSSStyleSheetImpl getSheet() {
		return this.styleSheet;
	}
}
