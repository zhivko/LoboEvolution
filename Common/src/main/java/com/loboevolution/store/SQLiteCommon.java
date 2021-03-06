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
package com.loboevolution.store;

import java.io.File;

public class SQLiteCommon {

	/** The Constant SETTINGS_DIR. */
	public static final String JDBC_SQLITE = "jdbc:sqlite:";

	/** The Constant LOBO_DB. */
	private static final String LOBO_DB = "LOBOEVOLUTION_STORAGE.sqlite";
	
	public static final String COLORS = "SELECT DISTINCT name, value FROM COLOR";
	
	public static final String FONTS = " SELECT DISTINCT acryl, aero, aluminium, bernstein, fast, graphite,"
			+ " 	      hiFi,luna, mcWin, mint, noire, smart, texture,"
			+ "	 	  subscript, superscript, underline, italic, strikethrough,"
			+ "		  fontSize, font, color, bold" + " FROM LOOK_AND_FEEL";

	public static final String COOKIES = " SELECT DISTINCT cookieName, cookieValue, domain, path, expires, maxAge,secure, httponly "
									   + " FROM COOKIE WHERE domain = ? AND path = ?";
	
	public static final String CONNECTIONS = "SELECT DISTINCT proxyType, userName, password, authenticated, host, port, disableProxyForLocalAddresses FROM CONNECTION";
		
	public static final String USER_AGENT = "SELECT DISTINCT msie, mozilla, include_msie FROM USER_AGENT";
	
	public static final String STARTUP = "SELECT DISTINCT baseUrl FROM STARTUP";
	
	public static final String SIZE = "SELECT DISTINCT width, height FROM SIZE";
	
	public static final String SEARCH = "SELECT DISTINCT description FROM SEARCH WHERE type = ?";

	public static final String SEARCH2 = "SELECT DISTINCT name, description, baseUrl, queryParameter, type, selected FROM SEARCH WHERE type = 'SEARCH_ENGINE' ORDER BY 6 DESC";

	public static final String HOST = "SELECT DISTINCT baseUrl FROM HOST LIMIT ?";

	public static final String HOST2 = "SELECT DISTINCT baseUrl FROM HOST WHERE baseUrl like ?";
	
	public static final String BOOKMARKS = "SELECT DISTINCT name, description, baseUrl, tags FROM BOOKMARKS WHERE baseUrl = ?";

	public static final String MOZ_BOOKMARKS = "SELECT DISTINCT places.url, book.title, places.description FROM moz_bookmarks book, moz_places places WHERE book.fk = places.id AND instr(places.url, 'http') > 0";
	
	public static final String MOZ_HISTORY = "SELECT DISTINCT places.url FROM moz_historyvisits vis, moz_places places WHERE vis.place_id = places.id";
	
	public static final String CHROME_HISTORY = "SELECT DISTINCT url from urls";
	
	public static final String NETWORK = "SELECT DISTINCT js, css, cookie, cache, navigation FROM NETWORK";
	
	public static final String INPUT = "SELECT DISTINCT value from INPUT where (name like ?) ";
	
	public static final String AUTHENTICATION = "SELECT DISTINCT name from AUTHENTICATION where baseUrl like ? ";
	
	public static final String MOZ_COOKIES = "SELECT * from moz_cookies";
	
	public static final String CHROME_COOKIES = "SELECT * from cookies";

	public static final String SOURCE_CACHE = "SELECT source FROM cache WHERE baseUrl = ? AND type = ? AND strftime('%Y-%m-%d %H:%M:%S', lastModified) > strftime('%Y-%m-%d %H:%M:%S', 'now', 'localtime')";
		
	public static final String CHECK_CACHE = "SELECT count(*) FROM cache WHERE baseUrl = ? AND contenLenght = ? AND etag = ? AND type = ?";
	
	public static final String CHAR = "SELECT upper(name), value FROM CHAR";
	
	public static final String INSERT_CACHE = "INSERT INTO CACHE (baseUrl, source, contenLenght, etag, lastModified, type) VALUES(?,?,?,?,?,?)";
	
	public static final String INSERT_COOKIES = "INSERT INTO COOKIE (cookieName, cookieValue, domain, path, expires, maxAge,secure, httponly) VALUES(?,?,?,?,?,?,?,?)";

	public static final String INSERT_CONNECTIONS = "INSERT INTO CONNECTION (proxyType, userName, password, authenticated, host, port, disableProxyForLocalAddresses) VALUES(?,?,?,?,?,?,?)";
	
	public static final String INSERT_USER_AGENT = "INSERT INTO USER_AGENT (msie, mozilla, include_msie) VALUES(?,?,?)";
	
	public static final String INSERT_STARTUP = "INSERT INTO STARTUP (baseUrl) VALUES(?)";
	
	public static final String INSERT_NETWORK = "INSERT INTO NETWORK (js, css, cookie, cache, navigation) VALUES(?,?,?,?,?)";
	
	public static final String INSERT_SIZE = "INSERT INTO SIZE (width, height) VALUES(?,?)";
	
	public static final String INSERT_HOST = "INSERT INTO HOST (baseUrl) VALUES(?)";
	
	public static final String INSERT_INPUT= "INSERT INTO INPUT (name, value) VALUES(?,?)";
	
	public static final String INSERT_AUTH = "INSERT INTO AUTHENTICATION (name, baseUrl) VALUES(?,?)";
	
	public static final String INSERT_BOOKMARKS = "INSERT INTO BOOKMARKS (name, description, baseUrl, tags) VALUES(?,?,?,?)";
	
	public static final String INSERT_SEARCH = "INSERT INTO SEARCH (name, description, type, baseUrl, queryParameter, selected) VALUES(?,?,?,?,?,?)";
	
	public static final String INSERT_SEARCH2 = "INSERT INTO SEARCH (name, description, type, selected) VALUES(?,?,?,?)";
	
	public static final String INSERT_LAF = " INSERT INTO LOOK_AND_FEEL (acryl, aero, aluminium, bernstein, fast, graphite,"
										  + "hiFi,luna, mcWin, mint, noire, smart, texture, subscript, superscript, underline, italic, "
										  + "strikethrough, fontSize, font, color, bold) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	
	public static final String UPDATE_SEARCH = "UPDATE SEARCH SET selected = 0 WHERE selected = 1 and type = 'SEARCH_ENGINE'";
	
	public static final String UPDATE_SEARCH2 = "UPDATE SEARCH SET selected = 1 WHERE name = ? and type = 'SEARCH_ENGINE'";
	
	public static final String DELETE_CONNECTIONS = "DELETE FROM CONNECTION";
	
	public static final String DELETE_USER_AGENT = "DELETE FROM USER_AGENT";
	
	public static final String DELETE_STARTUP = "DELETE FROM STARTUP";
	
	public static final String DELETE_NETWORK = "DELETE FROM NETWORK";
	
	public static final String DELETE_SIZE = "DELETE FROM SIZE";
	
	public static final String DELETE_LAF = "DELETE FROM LOOK_AND_FEEL";
	
	public static final String DELETE_SEARCH = "DELETE FROM SEARCH WHERE type = 'SEARCH_ENGINE'";
	
	public static final String DELETE_HOST = "DELETE FROM HOST";
	
	public static final String DELETE_COOKIES = "DELETE FROM COOKIE";
	
	public static final String DELETE_INPUT = "DELETE FROM INPUT";
	
	public static final String DELETE_BOOKMARKS = "DELETE FROM BOOKMARKS";
	
	public static final String DELETE_SOURCE_CACHE = "DELETE FROM cache WHERE baseUrl = ? AND type = ? AND strftime('%Y-%m-%d %H:%M:%S', lastModified) < strftime('%Y-%m-%d %H:%M:%S', 'now', 'localtime')";
	
	
	private SQLiteCommon() {}

	/**
	 * Gets the settings directory.
	 *
	 * @return the settings directory
	 */
	public static String getDatabaseDirectory() {
		File homeDir = new File(System.getProperty("user.home"));
		File storeDir = new File(homeDir, ".lobo");
		File store = new File(storeDir, "store");
		return JDBC_SQLITE + store + "\\" + LOBO_DB;
	}
	
	/**
	 * Gets the directory.
	 *
	 * @return the directory
	 */
	public static String getDatabaseStore() {
		File homeDir = new File(System.getProperty("user.home"));
		File storeDir = new File(homeDir, ".lobo");
		File store = new File(storeDir, "store");
		return store + "\\" + LOBO_DB;
	}
	
	/**
	 * Gets the cache.
	 *
	 * @return the cache
	 */
	public static String getCacheStore() {
		File homeDir = new File(System.getProperty("user.home"));
		File storeDir = new File(homeDir, ".lobo");
		File store = new File(storeDir, "cache");
		return store.getPath();
	}
		
	/**
	 * Gets the mozilla directory.
	 *
	 * @return the mozilla directory
	 */
	public static String getMozillaDirectory() {
		
		// pre Win7
		String filePath = System.getProperty("user.home") + "\\Application Data\\Roaming\\Mozilla\\Firefox\\Profiles\\";
		boolean isDir = new File(filePath).isDirectory();
		if (isDir) {
			return filePath;
		}

		// Win 7+
		filePath = System.getProperty("user.home") + "\\AppData\\Roaming\\Mozilla\\Firefox\\Profiles\\";
		isDir = new File(filePath).isDirectory();
		if (isDir) {
			return filePath;
		}

		// Mac
		filePath = System.getProperty("user.home") + "/Library/Application Support/Mozilla/Firefox/Profiles/";
		isDir = new File(filePath).isDirectory();
		if (isDir) {
			return filePath;
		}
		
		return "";
	}
	
	/**
	 * Gets the chrome directory.
	 *
	 * @return the chrome directory
	 */
	public static String getChromeDirectory() {
		
		// pre Win7
		String filePath = System.getProperty("user.home") + "\\Application Data\\Google\\Chrome\\User Data\\Default\\";
		boolean isDir = new File(filePath).isDirectory();
		if (isDir) {
			return filePath;
		}
		
		// Win 7+
		filePath = System.getProperty("user.home") + "\\AppData\\Local\\Google\\Chrome\\User Data\\Default\\";
		isDir = new File(filePath).isDirectory();
		if (isDir) {
			return filePath;
		}

		// Mac
		filePath = System.getProperty("user.home") + "/Library/Application Support/Google/Chrome/Default/";
		isDir = new File(filePath).isDirectory();
		if (isDir) {
			return filePath;
		}

		// Linux
		filePath = System.getProperty("user.home") + "/.config/chromium/Default/";
		isDir = new File(filePath).isDirectory();
		if (isDir) {
			return filePath;
		}

		return "";
	}
	
	/**
	 * Gets the ie directory.
	 *
	 * @return the ie directory
	 */
	public static String getIEDirectory() {

		// Win 7+
		String filePath = System.getProperty("user.home") + "\\AppData\\Roaming\\Microsoft\\Windows\\Cookies\\";
		boolean isDir = new File(filePath).isDirectory();
		if (isDir) {
			return filePath;
		}

		// Win 8
		filePath = System.getProperty("user.home") + "\\AppData\\Local\\Microsoft\\Windows\\INetCookies\\";
		isDir = new File(filePath).isDirectory();
		if (isDir) {
			return filePath;
		}

		return "";
	}
}