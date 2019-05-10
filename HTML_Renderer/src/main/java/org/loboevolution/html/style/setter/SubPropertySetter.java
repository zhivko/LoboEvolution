package org.loboevolution.html.style.setter;

import org.loboevolution.html.style.AbstractCSSProperties;
import org.w3c.dom.css.CSSStyleDeclaration;

import com.gargoylesoftware.css.dom.CSSStyleDeclarationImpl;

/**
 * The Interface SubPropertySetter.
 */
public interface SubPropertySetter {

	/**
	 * Change value.
	 *
	 * @param properties
	 *            the properties
	 * @param newValue
	 *            the new value
	 * @param declaration
	 *            the declaration
	 * @param important
	 *            the important
	 */
	public void changeValue(AbstractCSSProperties properties, String newValue, CSSStyleDeclarationImpl declaration,
			boolean important);
}
