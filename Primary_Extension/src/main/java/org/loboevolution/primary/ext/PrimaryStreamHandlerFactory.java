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
package org.loboevolution.primary.ext;

import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;

/**
 * A factory for creating PrimaryStreamHandler objects.
 */
public class PrimaryStreamHandlerFactory implements URLStreamHandlerFactory {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.net.URLStreamHandlerFactory#createURLStreamHandler(java.lang.String)
	 */
	@Override
	public URLStreamHandler createURLStreamHandler(String protocol) {
		if ("about".equals(protocol)) {
			return new org.loboevolution.protocol.about.Handler();
		} else if ("data".equals(protocol)) {
			return new org.loboevolution.protocol.data.Handler();
		} else {
			return null;
		}
	}
}