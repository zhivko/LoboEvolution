/*
 * (c) COPYRIGHT 1999 World Wide Web Consortium
 * (Massachusetts Institute of Technology, Institut National de Recherche
 *  en Informatique et en Automatique, Keio University).
 * All Rights Reserved. http://www.w3.org/Consortium/Legal/
 *
 * $Id: NegativeSelector.java 477010 2006-11-20 02:54:38Z mrglavas $
 */
package org.w3c.css.sac;

/**
 * The Interface NegativeSelector.
 *
 * @author Philippe Le Hegaret
 * @version $Revision: 477010 $
 * @see Selector#SAC_NEGATIVE_SELECTOR
 */
public interface NegativeSelector extends SimpleSelector {

	/**
	 * Returns the simple selector.
	 *
	 * @return the simple selector
	 */
	public SimpleSelector getSimpleSelector();
}
