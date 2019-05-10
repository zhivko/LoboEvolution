/*
* Copyright (c) 2002 and later by MH Software-Entwicklung. All Rights Reserved.
*
* JTattoo is multiple licensed. If your are an open source developer you can use
* it under the terms and conditions of the GNU General Public License version 2.0
* or later as published by the Free Software Foundation.
*
* see: gpl-2.0.txt
*
* If you pay for a license you will become a registered user who could use the
* software under the terms and conditions of the GNU Lesser General Public License
* version 2.0 or later with classpath exception as published by the Free Software
* Foundation.
*
* see: lgpl-2.0.txt
* see: classpath-exception.txt
*
* Registered users could also use JTattoo under the terms and conditions of the
* Apache License, Version 2.0 as published by the Apache Software Foundation.
*
* see: APACHE-LICENSE-2.0.txt
 */
package com.jtattoo.plaf.texture;

import javax.swing.border.Border;

import com.jtattoo.plaf.AbstractBorderFactory;
import com.jtattoo.plaf.BaseBorders;

/**
 * @author Michael Hagen
 */
public class TextureBorderFactory implements AbstractBorderFactory {

	private static TextureBorderFactory instance = null;

	public static synchronized TextureBorderFactory getInstance() {
		if (instance == null) {
			instance = new TextureBorderFactory();
		}
		return instance;
	}

	private TextureBorderFactory() {
	}

	@Override
	public Border getButtonBorder() {
		return TextureBorders.getButtonBorder();
	}

	@Override
	public Border getComboBoxBorder() {
		return BaseBorders.getComboBoxBorder();
	}

	@Override
	public Border getDesktopIconBorder() {
		return BaseBorders.getDesktopIconBorder();
	}

	@Override
	public Border getFocusFrameBorder() {
		return BaseBorders.getFocusFrameBorder();
	}

	@Override
	public Border getInternalFrameBorder() {
		return TextureBorders.getInternalFrameBorder();
	}

	@Override
	public Border getMenuBarBorder() {
		return BaseBorders.getMenuBarBorder();
	}

	@Override
	public Border getMenuItemBorder() {
		return TextureBorders.getMenuItemBorder();
	}

	@Override
	public Border getPaletteBorder() {
		return BaseBorders.getPaletteBorder();
	}

	@Override
	public Border getPopupMenuBorder() {
		return TextureBorders.getPopupMenuBorder();
	}

	@Override
	public Border getProgressBarBorder() {
		return BaseBorders.getProgressBarBorder();
	}

	@Override
	public Border getScrollPaneBorder() {
		return BaseBorders.getScrollPaneBorder();
	}

	@Override
	public Border getSpinnerBorder() {
		return BaseBorders.getSpinnerBorder();
	}

	@Override
	public Border getTabbedPaneBorder() {
		return BaseBorders.getTabbedPaneBorder();
	}

	@Override
	public Border getTableHeaderBorder() {
		return BaseBorders.getTableHeaderBorder();
	}

	@Override
	public Border getTableScrollPaneBorder() {
		return BaseBorders.getTableScrollPaneBorder();
	}

	@Override
	public Border getTextBorder() {
		return BaseBorders.getTextBorder();
	}

	@Override
	public Border getTextFieldBorder() {
		return BaseBorders.getTextFieldBorder();
	}

	@Override
	public Border getToggleButtonBorder() {
		return TextureBorders.getToggleButtonBorder();
	}

	@Override
	public Border getToolBarBorder() {
		return TextureBorders.getToolBarBorder();
	}

} // end of class TextureBorderFactory
