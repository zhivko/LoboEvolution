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

package org.loboevolution.html.style;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.util.StringTokenizer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.loboevolution.html.dombl.ExternalResourcesCache;
import org.loboevolution.html.domimpl.HTMLDocumentImpl;
import org.loboevolution.http.Urls;
import org.loboevolution.http.UserAgentContext;
import org.loboevolution.util.Strings;
import com.gargoylesoftware.css.parser.InputSource;
import org.w3c.dom.stylesheets.MediaList;

import com.gargoylesoftware.css.dom.CSSStyleSheetImpl;
import com.gargoylesoftware.css.parser.CSSOMParser;

/**
 * The Class CSSUtilities.
 */
public class CSSUtilities {

	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(CSSUtilities.class);

	/**
	 * Instantiates a new CSS utilities.
	 */
	private CSSUtilities() {
	}

	/**
	 * Pre process css.
	 *
	 * @param text
	 *            the text
	 * @return the string
	 */
	public static String preProcessCss(String text) {
		try (BufferedReader reader = new BufferedReader(new StringReader(text))) {
			String line;
			StringBuilder sb = new StringBuilder();
			String pendingLine = null;
			// Only last line should be trimmed.
			while ((line = reader.readLine()) != null) {
				String tline = line.trim();
				if (tline.length() != 0) {
					if (pendingLine != null) {
						sb.append(pendingLine);
						sb.append("\r\n");
						pendingLine = null;
					}
					if (tline.startsWith("//")) {
						pendingLine = line;
						continue;
					}
					sb.append(line);
					sb.append("\r\n");
				}
			}
			return sb.toString();
		} catch (IOException ioe) {
			throw new IllegalStateException(ioe);
		}
	}

	/**
	 * Gets the css input source for style sheet.
	 *
	 * @param text
	 *            the text
	 * @param scriptURI
	 *            the script uri
	 * @return the css input source for style sheet
	 */
	public static InputSource getCssInputSourceForStyleSheet(String text, String scriptURI) {
		java.io.Reader reader = new StringReader(text);
		InputSource is = new InputSource(reader);
		is.setURI(scriptURI);
		return is;
	}

	/**
	 * @param href
	 * @param href
	 * @param doc
	 * @return
	 * @throws Exception
	 */
	public static CSSStyleSheetImpl parse(String href, HTMLDocumentImpl doc) throws Exception {
		CSSOMParser parser = new CSSOMParser();
		URL baseURL = new URL(doc.getBaseURI());
		URL scriptURL = Urls.createURL(baseURL, href);
		String scriptURI = scriptURL == null ? href : scriptURL.toExternalForm();
		String source = ExternalResourcesCache.getSourceCache(scriptURI, "CSS");
		InputSource is = getCssInputSourceForStyleSheet(source, doc.getBaseURI());
		return parser.parseStyleSheet(is, null);

	}

	/**
	 * Matches media.
	 *
	 * @param mediaValues
	 *            the media values
	 * @param rcontext
	 *            the rcontext
	 * @return true, if successful
	 */
	public static boolean matchesMedia(String mediaValues, UserAgentContext rcontext) {
		if (Strings.isBlank(mediaValues)) {
			return true;
		}
		if (rcontext == null) {
			return false;
		}
		StringTokenizer tok = new StringTokenizer(mediaValues, ",");
		while (tok.hasMoreTokens()) {
			String token = tok.nextToken().trim();
			String mediaName = Strings.trimForAlphaNumDash(token);
			if (rcontext.isMedia(mediaName)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Matches media.
	 *
	 * @param mediaList
	 *            the media list
	 * @param rcontext
	 *            the rcontext
	 * @return true, if successful
	 */
	public static boolean matchesMedia(MediaList mediaList, UserAgentContext rcontext) {
		if (mediaList == null) {
			return true;
		}
		int length = mediaList.getLength();
		if (length == 0) {
			return true;
		}
		if (rcontext == null) {
			return false;
		}
		for (int i = 0; i < length; i++) {
			String mediaName = mediaList.item(i);
			if (rcontext.isMedia(mediaName)) {
				return true;
			}
		}
		return false;
	}

}
