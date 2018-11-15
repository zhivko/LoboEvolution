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

import org.loboevolution.html.style.AbstractCSSProperties;
import org.mozilla.javascript.Function;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * The Interface HTMLElement.
 */
public interface HTMLElement extends Element {

	/**
	 * Gets the elements by class name.
	 *
	 * @param classNames
	 *            the class names
	 * @return the elements by class name
	 */
	// HTMLElement
	public NodeList getElementsByClassName(String classNames);

	/**
	 * Gets the inner html.
	 *
	 * @return the inner html
	 */
	public String getInnerHTML();

	/**
	 * Sets the inner html.
	 *
	 * @param innerHTML
	 *            the new inner html
	 */
	public void setInnerHTML(String innerHTML);

	/**
	 * Gets the outer html.
	 *
	 * @return the outer html
	 */
	public String getOuterHTML();

	/**
	 * Sets the outer html.
	 *
	 * @param outerHTML
	 *            the new outer html
	 */
	public void setOuterHTML(String outerHTML);

	/**
	 * Insert adjacent html.
	 *
	 * @param position
	 *            the position
	 * @param text
	 *            the text
	 */
	public void insertAdjacentHTML(String position, String text);

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId();

	/**
	 * Sets the id.
	 *
	 * @param id
	 *            the new id
	 */
	public void setId(String id);

	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle();

	/**
	 * Sets the title.
	 *
	 * @param title
	 *            the new title
	 */
	public void setTitle(String title);

	/**
	 * Gets the lang.
	 *
	 * @return the lang
	 */
	public String getLang();

	/**
	 * Sets the lang.
	 *
	 * @param lang
	 *            the new lang
	 */
	public void setLang(String lang);

	/**
	 * Gets the dir.
	 *
	 * @return the dir
	 */
	public String getDir();

	/**
	 * Sets the dir.
	 *
	 * @param dir
	 *            the new dir
	 */
	public void setDir(String dir);

	/**
	 * Gets the class name.
	 *
	 * @return the class name
	 */
	public String getClassName();

	/**
	 * Sets the class name.
	 *
	 * @param className
	 *            the new class name
	 */
	public void setClassName(String className);

	/**
	 * Gets the class list.
	 *
	 * @return the class list
	 */
	public DOMTokenList getClassList();

	/**
	 * Gets the dataset.
	 *
	 * @return the dataset
	 */
	public DOMStringMap getDataset();

	/**
	 * Gets the item scope.
	 *
	 * @return the item scope
	 */
	public boolean getItemScope();

	/**
	 * Sets the item scope.
	 *
	 * @param itemScope
	 *            the new item scope
	 */
	public void setItemScope(boolean itemScope);

	/**
	 * Gets the item type.
	 *
	 * @return the item type
	 */
	public String getItemType();

	/**
	 * Sets the item type.
	 *
	 * @param itemType
	 *            the new item type
	 */
	public void setItemType(String itemType);

	/**
	 * Gets the item id.
	 *
	 * @return the item id
	 */
	public String getItemId();

	/**
	 * Sets the item id.
	 *
	 * @param itemId
	 *            the new item id
	 */
	public void setItemId(String itemId);

	/**
	 * Gets the item ref.
	 *
	 * @return the item ref
	 */
	public DOMSettableTokenList getItemRef();

	/**
	 * Sets the item ref.
	 *
	 * @param itemRef
	 *            the new item ref
	 */
	public void setItemRef(String itemRef);

	/**
	 * Gets the item prop.
	 *
	 * @return the item prop
	 */
	public DOMSettableTokenList getItemProp();

	/**
	 * Sets the item prop.
	 *
	 * @param itemProp
	 *            the new item prop
	 */
	public void setItemProp(String itemProp);

	/**
	 * Gets the properties.
	 *
	 * @return the properties
	 */
	public HTMLPropertiesCollection getProperties();

	/**
	 * Gets the item value.
	 *
	 * @return the item value
	 */
	public Object getItemValue();

	/**
	 * Sets the item value.
	 *
	 * @param itemValue
	 *            the new item value
	 */
	public void setItemValue(Object itemValue);

	/**
	 * Gets the hidden.
	 *
	 * @return the hidden
	 */
	public boolean getHidden();

	/**
	 * Sets the hidden.
	 *
	 * @param hidden
	 *            the new hidden
	 */
	public void setHidden(boolean hidden);

	/**
	 * Click.
	 */
	public void click();

	/**
	 * Gets the tab index.
	 *
	 * @return the tab index
	 */
	public int getTabIndex();

	/**
	 * Sets the tab index.
	 *
	 * @param tabIndex
	 *            the new tab index
	 */
	public void setTabIndex(int tabIndex);

	/**
	 * Focus.
	 */
	public void focus();

	/**
	 * Blur.
	 */
	public void blur();

	/**
	 * Gets the access key.
	 *
	 * @return the access key
	 */
	public String getAccessKey();

	/**
	 * Sets the access key.
	 *
	 * @param accessKey
	 *            the new access key
	 */
	public void setAccessKey(String accessKey);

	/**
	 * Gets the access key label.
	 *
	 * @return the access key label
	 */
	public String getAccessKeyLabel();

	/**
	 * Gets the draggable.
	 *
	 * @return the draggable
	 */
	public boolean getDraggable();

	/**
	 * Sets the draggable.
	 *
	 * @param draggable
	 *            the new draggable
	 */
	public void setDraggable(boolean draggable);

	/**
	 * Gets the dropzone.
	 *
	 * @return the dropzone
	 */
	public DOMSettableTokenList getDropzone();

	/**
	 * Sets the dropzone.
	 *
	 * @param dropzone
	 *            the new dropzone
	 */
	public void setDropzone(String dropzone);

	/**
	 * Gets the content editable.
	 *
	 * @return the content editable
	 */
	public String getContentEditable();

	/**
	 * Sets the content editable.
	 *
	 * @param contentEditable
	 *            the new content editable
	 */
	public void setContentEditable(String contentEditable);

	/**
	 * Gets the checks if is content editable.
	 *
	 * @return the checks if is content editable
	 */
	public boolean getIsContentEditable();

	/**
	 * Gets the context menu.
	 *
	 * @return the context menu
	 */
	public HTMLMenuElement getContextMenu();

	/**
	 * Sets the context menu.
	 *
	 * @param contextMenu
	 *            the new context menu
	 */
	public void setContextMenu(HTMLMenuElement contextMenu);

	/**
	 * Gets the spellcheck.
	 *
	 * @return the spellcheck
	 */
	public String getSpellcheck();

	/**
	 * Sets the spellcheck.
	 *
	 * @param spellcheck
	 *            the new spellcheck
	 */
	public void setSpellcheck(boolean spellcheck);

	/**
	 * Gets the command type.
	 *
	 * @return the command type
	 */
	public String getCommandType();

	/**
	 * Gets the label.
	 *
	 * @return the label
	 */
	public String getLabel();

	/**
	 * Gets the icon.
	 *
	 * @return the icon
	 */
	public String getIcon();

	/**
	 * Gets the disabled.
	 *
	 * @return the disabled
	 */
	public boolean getDisabled();

	/**
	 * Gets the checked.
	 *
	 * @return the checked
	 */
	public boolean getChecked();

	/**
	 * Gets the style.
	 *
	 * @return the style
	 */
	public AbstractCSSProperties getStyle();

	/**
	 * Gets the offset parent.
	 *
	 * @return the offset parent
	 */
	public Element getOffsetParent();

	/**
	 * Gets the offset top.
	 *
	 * @return the offset top
	 */
	public int getOffsetTop();

	/**
	 * Gets the offset left.
	 *
	 * @return the offset left
	 */
	public int getOffsetLeft();

	/**
	 * Gets the offset width.
	 *
	 * @return the offset width
	 */
	public int getOffsetWidth();

	/**
	 * Gets the offset height.
	 *
	 * @return the offset height
	 */
	public int getOffsetHeight();

	/**
	 * Query selector all.
	 *
	 * @param selectors
	 *            the selectors
	 * @return the node list
	 */
	NodeList querySelectorAll(String selectors);

	/**
	 * Query selector.
	 *
	 * @param selectors
	 *            the selectors
	 * @return the element
	 */
	Element querySelector(String selectors);
}
