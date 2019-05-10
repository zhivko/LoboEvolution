package org.loboevolution.html.style.setter;

import org.loboevolution.html.style.AbstractCSSProperties;
import org.loboevolution.html.style.HtmlValues;
import org.loboevolution.util.Strings;
import org.w3c.dom.css.CSSStyleDeclaration;

import com.gargoylesoftware.css.dom.CSSStyleDeclarationImpl;

public class FourCornersSetter implements SubPropertySetter {

	/** The prefix. */
	private final String prefix;

	/** The suffix. */
	private final String suffix;

	/** The property. */
	private final String property;

	/**
	 * Instantiates a new four corners setter.
	 *
	 * @param property
	 *            the property
	 * @param prefix
	 *            the prefix
	 * @param suffix
	 *            the suffix
	 */
	public FourCornersSetter(String property, String prefix, String suffix) {
		this.prefix = prefix;
		this.suffix = suffix;
		this.property = property;
	}

	@Override
	public void changeValue(AbstractCSSProperties properties, String newValue, CSSStyleDeclarationImpl declaration,
			boolean important) {
		properties.setPropertyValueLCAlt(this.property, newValue, important);
		if (Strings.isNotBlank(newValue)) {
			String[] array = HtmlValues.splitCssValue(newValue);
			int size = array.length;
			if (size == 1) {
				String prefix = this.prefix;
				String suffix = this.suffix;
				String value = array[0];
				properties.setPropertyValueLCAlt(prefix + "top" + suffix, value, important);
				properties.setPropertyValueLCAlt(prefix + "right" + suffix, value, important);
				properties.setPropertyValueLCAlt(prefix + "bottom" + suffix, value, important);
				properties.setPropertyValueLCAlt(prefix + "left" + suffix, value, important);
			} else if (size >= 4) {
				String prefix = this.prefix;
				String suffix = this.suffix;
				properties.setPropertyValueLCAlt(prefix + "top" + suffix, array[0], important);
				properties.setPropertyValueLCAlt(prefix + "right" + suffix, array[1], important);
				properties.setPropertyValueLCAlt(prefix + "bottom" + suffix, array[2], important);
				properties.setPropertyValueLCAlt(prefix + "left" + suffix, array[3], important);
			} else if (size == 2) {
				String prefix = this.prefix;
				String suffix = this.suffix;
				properties.setPropertyValueLCAlt(prefix + "top" + suffix, array[0], important);
				properties.setPropertyValueLCAlt(prefix + "right" + suffix, array[1], important);
				properties.setPropertyValueLCAlt(prefix + "bottom" + suffix, array[0], important);
				properties.setPropertyValueLCAlt(prefix + "left" + suffix, array[1], important);
			} else if (size == 3) {
				String prefix = this.prefix;
				String suffix = this.suffix;
				properties.setPropertyValueLCAlt(prefix + "top" + suffix, array[0], important);
				properties.setPropertyValueLCAlt(prefix + "right" + suffix, array[1], important);
				properties.setPropertyValueLCAlt(prefix + "bottom" + suffix, array[2], important);
				properties.setPropertyValueLCAlt(prefix + "left" + suffix, array[1], important);
			}
		}
	}
	
	/**
	 * Change value.
	 *
	 * @param properties
	 *            the properties
	 * @param newValue
	 *            the new value
	 * @param declaration
	 *            the declaration
	 */
	public void changeValue(AbstractCSSProperties properties, String newValue, CSSStyleDeclarationImpl declaration) {
		this.changeValue(properties, newValue, declaration, true);
	}
}