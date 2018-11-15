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

package org.loboevolution.w3c.html;

import org.loboevolution.html.js.object.Location;
import org.w3c.dom.NodeList;

/**
 * The Interface HTMLDocument.
 */
public interface HTMLDocument {

	HTMLCollection getAnchors();

	HTMLCollection getApplets();

	HTMLCollection getImages();

	HTMLCollection getEmbeds();

	HTMLCollection getPlugins();

	HTMLCollection getLinks();

	HTMLCollection getForms();

	HTMLCollection getScripts();
	
	HTMLCollection getCommands();

	HTMLElement getBody();
	
	HTMLHeadElement getHead();
	
	Location getLocation();

	NodeList getElementsByName(String elementName);

	String getCookie();

	String getDomain();

	String getReferrer();

	String getTitle();

	String getURL();
	
	String getLastModified();
	
	String getCompatMode();

	void open();

	void close();

	void setBody(HTMLElement body);

	void setTitle(String title);

	void setCookie(String cookie);
	
	 void setDomain(String domain);

	void write(String text);

	void writeln(String text);

}
