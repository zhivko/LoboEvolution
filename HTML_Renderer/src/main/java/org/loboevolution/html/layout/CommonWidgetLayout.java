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
package org.loboevolution.html.layout;


import org.loboevolution.html.HtmlAttributeProperties;
import org.loboevolution.html.dombl.UINode;
import org.loboevolution.html.domimpl.HTMLElementImpl;
import org.loboevolution.html.renderer.RElement;
import org.loboevolution.html.rendererblock.RBlockPosition;
import org.loboevolution.html.rendererblock.RBlockViewport;

/**
 * The Class CommonWidgetLayout.
 */
public abstract class CommonWidgetLayout implements MarkupLayout, HtmlAttributeProperties {

	/** The Constant ADD_INLINE. */
	protected static final int ADD_INLINE = 0;

	/** The Constant ADD_AS_BLOCK. */
	protected static final int ADD_AS_BLOCK = 1;
	
	/** The Constant ADD_INLINE_BLOCK. */
	protected static final int ADD_INLINE_BLOCK = 2;

    private final int method;

    public CommonWidgetLayout(int method) {
    	this.method = method;
    }

    @Override
    public void layoutMarkup(RBlockViewport bodyLayout, HTMLElementImpl markupElement) {
        final UINode node = markupElement.getUINode();
        RElement renderable = null;
        if (node == null) {
            renderable = createRenderable(bodyLayout, markupElement);
            if (renderable == null) {
                return;
            }
            markupElement.setUINode(renderable);
        } else {
            renderable = (RElement) node;
        }
        renderable.setOriginalParent(bodyLayout);
        switch (method) {
        case ADD_INLINE:
            bodyLayout.addRenderableToLineCheckStyle(renderable, markupElement, true);
            break;
        case ADD_AS_BLOCK:
        case ADD_INLINE_BLOCK:
            RBlockPosition ps = new RBlockPosition();
            ps.positionRElement(markupElement, renderable, bodyLayout, true, true, false);
            break;
        default:
            return;
        }
    }
    
    protected abstract RElement createRenderable(RBlockViewport bodyLayout, HTMLElementImpl markupElement);
}
