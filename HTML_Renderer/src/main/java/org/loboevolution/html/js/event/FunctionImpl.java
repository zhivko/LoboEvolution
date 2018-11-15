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
package org.loboevolution.html.js.event;

import org.loboevolution.js.AbstractScriptableDelegate;
import org.mozilla.javascript.Function;

public class FunctionImpl extends AbstractScriptableDelegate {

	/** The onclick. */
	private Function onclick;

	/** The ondblclick. */
	private Function ondblclick;

	/** The onkeydown. */
	private Function onkeydown;

	/** The onkeypress. */
	private Function onkeypress;

	/** The onkeyup. */
	private Function onkeyup;

	/** The onmousedown. */
	private Function onmousedown;

	/** The onmouseup. */
	private Function onmouseup;

	/** The onunload. */
	private Function onunload;

	/** The onmouseout. */
	private Function onmouseout;

	/** The onmouseover. */
	private Function onmouseover;

	/** The oncanplay. */
	private Function oncanplay;

	/** The onabort. */
	private Function onabort;

	/** The onblur. */
	private Function onblur;

	/** The oncanplaythrough. */
	private Function oncanplaythrough;

	/** The onchange. */
	private Function onchange;

	/** The oncontextmenu. */
	private Function oncontextmenu;

	/** The oncuechange. */
	private Function oncuechange;

	/** The ondrag. */
	private Function ondrag;

	/** The ondragend. */
	private Function ondragend;

	/** The ondragenter. */
	private Function ondragenter;

	/** The ondragleave. */
	private Function ondragleave;

	/** The ondragover. */
	private Function ondragover;

	/** The ondragstart. */
	private Function ondragstart;

	/** The ondrop. */
	private Function ondrop;

	/** The ondurationchange. */
	private Function ondurationchange;

	/** The onemptied. */
	private Function onemptied;

	/** The onended. */
	private Function onended;

	/** The onerror. */
	private Function onerror;

	/** The onfocus. */
	private Function onfocus;

	/** The oninput. */
	private Function oninput;

	/** The oninvalid. */
	private Function oninvalid;

	/** The onload. */
	private Function onload;

	/** The onloadeddata. */
	private Function onloadeddata;

	/** The onloadedmetadata. */
	private Function onloadedmetadata;

	/** The onloadstart. */
	private Function onloadstart;

	/** The onmousewheel. */
	private Function onmousewheel;

	/** The onpause. */
	private Function onpause;

	/** The onplay. */
	private Function onplay;

	/** The onplaying. */
	private Function onplaying;

	/** The onprogress. */
	private Function onprogress;

	/** The onreadystatechange. */
	private Function onreadystatechange;

	/** The onreset. */
	private Function onreset;

	/** The onscroll. */
	private Function onscroll;

	/** The onseeked. */
	private Function onseeked;

	/** The onseeking. */
	private Function onseeking;

	/** The onselect. */
	private Function onselect;

	/** The onshow. */
	private Function onshow;

	/** The onstalled. */
	private Function onstalled;

	/** The onsubmit. */
	private Function onsubmit;

	/** The onsuspend. */
	private Function onsuspend;

	/** The ontimeupdate. */
	private Function ontimeupdate;

	/** The onvolumechange. */
	private Function onvolumechange;

	/** The onwaiting. */
	private Function onwaiting;

	/** The omousemove. */
	private Function onmousemove;

	/** The onratechange. */
	private Function onratechange;

	/** The onload handler. */
	private Function onloadHandler;

	/** The . */
	private Function onafterprint;

	/** The . */
	private Function onbeforeprint;

	/** The onbeforeunload. */
	private Function onbeforeunload;

	/** The onhashchange. */
	private Function onhashchange;

	/** The onmessage. */
	private Function onmessage;

	/** The onoffline. */
	private Function onoffline;

	/** The ononline. */
	private Function ononline;

	/** The onpagehide. */
	private Function onpagehide;

	/** The onpageshow. */
	private Function onpageshow;

	/** The onpopstate. */
	private Function onpopstate;

	/** The onredo. */
	private Function onredo;

	/** The onresize. */
	private Function onresize;

	/** The onstorage. */
	private Function onstorage;

	/** The onundo. */
	private Function onundo;

	/** The onbounce. */
	private Function onbounce;

	/** The onfinish. */
	private Function onfinish;

	/** The onstart. */
	private Function onstart;

	/** The onenter. */
	private Function onenter;

	/** The onexit. */
	private Function onexit;

	/** The onchecking. */
	private Function onchecking;

	/** The onnoupdate. */
	private Function onnoupdate;

	/** The ondownloading. */
	private Function ondownloading;

	/** The onupdateready. */
	private Function onupdateready;

	/** The oncached. */
	private Function oncached;

	/** The onobsolete. */
	private Function onobsolete;

	/** The onloadend. */
	private Function onloadend;

	/**
	 * @return the onclick
	 */
	public Function getOnclick() {
		return onclick;
	}

	/**
	 * @param onclick the onclick to set
	 */
	public void setOnclick(Function onclick) {
		this.onclick = onclick;
	}

	/**
	 * @return the ondblclick
	 */
	public Function getOndblclick() {
		return ondblclick;
	}

	/**
	 * @param ondblclick the ondblclick to set
	 */
	public void setOndblclick(Function ondblclick) {
		this.ondblclick = ondblclick;
	}

	/**
	 * @return the onkeydown
	 */
	public Function getOnkeydown() {
		return onkeydown;
	}

	/**
	 * @param onkeydown the onkeydown to set
	 */
	public void setOnkeydown(Function onkeydown) {
		this.onkeydown = onkeydown;
	}

	/**
	 * @return the onkeypress
	 */
	public Function getOnkeypress() {
		return onkeypress;
	}

	/**
	 * @param onkeypress the onkeypress to set
	 */
	public void setOnkeypress(Function onkeypress) {
		this.onkeypress = onkeypress;
	}

	/**
	 * @return the onkeyup
	 */
	public Function getOnkeyup() {
		return onkeyup;
	}

	/**
	 * @param onkeyup the onkeyup to set
	 */
	public void setOnkeyup(Function onkeyup) {
		this.onkeyup = onkeyup;
	}

	/**
	 * @return the onmousedown
	 */
	public Function getOnmousedown() {
		return onmousedown;
	}

	/**
	 * @param onmousedown the onmousedown to set
	 */
	public void setOnmousedown(Function onmousedown) {
		this.onmousedown = onmousedown;
	}

	/**
	 * @return the onmouseup
	 */
	public Function getOnmouseup() {
		return onmouseup;
	}

	/**
	 * @param onmouseup the onmouseup to set
	 */
	public void setOnmouseup(Function onmouseup) {
		this.onmouseup = onmouseup;
	}

	/**
	 * @return the onunload
	 */
	public Function getOnunload() {
		return onunload;
	}

	/**
	 * @param onunload the onunload to set
	 */
	public void setOnunload(Function onunload) {
		this.onunload = onunload;
	}

	/**
	 * @return the onmouseout
	 */
	public Function getOnmouseout() {
		return onmouseout;
	}

	/**
	 * @param onmouseout the onmouseout to set
	 */
	public void setOnmouseout(Function onmouseout) {
		this.onmouseout = onmouseout;
	}

	/**
	 * @return the onmouseover
	 */
	public Function getOnmouseover() {
		return onmouseover;
	}

	/**
	 * @param onmouseover the onmouseover to set
	 */
	public void setOnmouseover(Function onmouseover) {
		this.onmouseover = onmouseover;
	}

	/**
	 * @return the oncanplay
	 */
	public Function getOncanplay() {
		return oncanplay;
	}

	/**
	 * @param oncanplay the oncanplay to set
	 */
	public void setOncanplay(Function oncanplay) {
		this.oncanplay = oncanplay;
	}

	/**
	 * @return the onabort
	 */
	public Function getOnabort() {
		return onabort;
	}

	/**
	 * @param onabort the onabort to set
	 */
	public void setOnabort(Function onabort) {
		this.onabort = onabort;
	}

	/**
	 * @return the onblur
	 */
	public Function getOnblur() {
		return onblur;
	}

	/**
	 * @param onblur the onblur to set
	 */
	public void setOnblur(Function onblur) {
		this.onblur = onblur;
	}

	/**
	 * @return the oncanplaythrough
	 */
	public Function getOncanplaythrough() {
		return oncanplaythrough;
	}

	/**
	 * @param oncanplaythrough the oncanplaythrough to set
	 */
	public void setOncanplaythrough(Function oncanplaythrough) {
		this.oncanplaythrough = oncanplaythrough;
	}

	/**
	 * @return the onchange
	 */
	public Function getOnchange() {
		return onchange;
	}

	/**
	 * @param onchange the onchange to set
	 */
	public void setOnchange(Function onchange) {
		this.onchange = onchange;
	}

	/**
	 * @return the oncontextmenu
	 */
	public Function getOncontextmenu() {
		return oncontextmenu;
	}

	/**
	 * @param oncontextmenu the oncontextmenu to set
	 */
	public void setOncontextmenu(Function oncontextmenu) {
		this.oncontextmenu = oncontextmenu;
	}

	/**
	 * @return the oncuechange
	 */
	public Function getOncuechange() {
		return oncuechange;
	}

	/**
	 * @param oncuechange the oncuechange to set
	 */
	public void setOncuechange(Function oncuechange) {
		this.oncuechange = oncuechange;
	}

	/**
	 * @return the ondrag
	 */
	public Function getOndrag() {
		return ondrag;
	}

	/**
	 * @param ondrag the ondrag to set
	 */
	public void setOndrag(Function ondrag) {
		this.ondrag = ondrag;
	}

	/**
	 * @return the ondragend
	 */
	public Function getOndragend() {
		return ondragend;
	}

	/**
	 * @param ondragend the ondragend to set
	 */
	public void setOndragend(Function ondragend) {
		this.ondragend = ondragend;
	}

	/**
	 * @return the ondragenter
	 */
	public Function getOndragenter() {
		return ondragenter;
	}

	/**
	 * @param ondragenter the ondragenter to set
	 */
	public void setOndragenter(Function ondragenter) {
		this.ondragenter = ondragenter;
	}

	/**
	 * @return the ondragleave
	 */
	public Function getOndragleave() {
		return ondragleave;
	}

	/**
	 * @param ondragleave the ondragleave to set
	 */
	public void setOndragleave(Function ondragleave) {
		this.ondragleave = ondragleave;
	}

	/**
	 * @return the ondragover
	 */
	public Function getOndragover() {
		return ondragover;
	}

	/**
	 * @param ondragover the ondragover to set
	 */
	public void setOndragover(Function ondragover) {
		this.ondragover = ondragover;
	}

	/**
	 * @return the ondragstart
	 */
	public Function getOndragstart() {
		return ondragstart;
	}

	/**
	 * @param ondragstart the ondragstart to set
	 */
	public void setOndragstart(Function ondragstart) {
		this.ondragstart = ondragstart;
	}

	/**
	 * @return the ondrop
	 */
	public Function getOndrop() {
		return ondrop;
	}

	/**
	 * @param ondrop the ondrop to set
	 */
	public void setOndrop(Function ondrop) {
		this.ondrop = ondrop;
	}

	/**
	 * @return the ondurationchange
	 */
	public Function getOndurationchange() {
		return ondurationchange;
	}

	/**
	 * @param ondurationchange the ondurationchange to set
	 */
	public void setOndurationchange(Function ondurationchange) {
		this.ondurationchange = ondurationchange;
	}

	/**
	 * @return the onemptied
	 */
	public Function getOnemptied() {
		return onemptied;
	}

	/**
	 * @param onemptied the onemptied to set
	 */
	public void setOnemptied(Function onemptied) {
		this.onemptied = onemptied;
	}

	/**
	 * @return the onended
	 */
	public Function getOnended() {
		return onended;
	}

	/**
	 * @param onended the onended to set
	 */
	public void setOnended(Function onended) {
		this.onended = onended;
	}

	/**
	 * @return the onerror
	 */
	public Function getOnerror() {
		return onerror;
	}

	/**
	 * @param onerror the onerror to set
	 */
	public void setOnerror(Function onerror) {
		this.onerror = onerror;
	}

	/**
	 * @return the onfocus
	 */
	public Function getOnfocus() {
		return onfocus;
	}

	/**
	 * @param onfocus the onfocus to set
	 */
	public void setOnfocus(Function onfocus) {
		this.onfocus = onfocus;
	}

	/**
	 * @return the oninput
	 */
	public Function getOninput() {
		return oninput;
	}

	/**
	 * @param oninput the oninput to set
	 */
	public void setOninput(Function oninput) {
		this.oninput = oninput;
	}

	/**
	 * @return the oninvalid
	 */
	public Function getOninvalid() {
		return oninvalid;
	}

	/**
	 * @param oninvalid the oninvalid to set
	 */
	public void setOninvalid(Function oninvalid) {
		this.oninvalid = oninvalid;
	}

	/**
	 * @return the onload
	 */
	public Function getOnload() {
		return onload;
	}

	/**
	 * @param onload the onload to set
	 */
	public void setOnload(Function onload) {
		this.onload = onload;
	}

	/**
	 * @return the onloadeddata
	 */
	public Function getOnloadeddata() {
		return onloadeddata;
	}

	/**
	 * @param onloadeddata the onloadeddata to set
	 */
	public void setOnloadeddata(Function onloadeddata) {
		this.onloadeddata = onloadeddata;
	}

	/**
	 * @return the onloadedmetadata
	 */
	public Function getOnloadedmetadata() {
		return onloadedmetadata;
	}

	/**
	 * @param onloadedmetadata the onloadedmetadata to set
	 */
	public void setOnloadedmetadata(Function onloadedmetadata) {
		this.onloadedmetadata = onloadedmetadata;
	}

	/**
	 * @return the onloadstart
	 */
	public Function getOnloadstart() {
		return onloadstart;
	}

	/**
	 * @param onloadstart the onloadstart to set
	 */
	public void setOnloadstart(Function onloadstart) {
		this.onloadstart = onloadstart;
	}

	/**
	 * @return the onmousewheel
	 */
	public Function getOnmousewheel() {
		return onmousewheel;
	}

	/**
	 * @param onmousewheel the onmousewheel to set
	 */
	public void setOnmousewheel(Function onmousewheel) {
		this.onmousewheel = onmousewheel;
	}

	/**
	 * @return the onpause
	 */
	public Function getOnpause() {
		return onpause;
	}

	/**
	 * @param onpause the onpause to set
	 */
	public void setOnpause(Function onpause) {
		this.onpause = onpause;
	}

	/**
	 * @return the onplay
	 */
	public Function getOnplay() {
		return onplay;
	}

	/**
	 * @param onplay the onplay to set
	 */
	public void setOnplay(Function onplay) {
		this.onplay = onplay;
	}

	/**
	 * @return the onplaying
	 */
	public Function getOnplaying() {
		return onplaying;
	}

	/**
	 * @param onplaying the onplaying to set
	 */
	public void setOnplaying(Function onplaying) {
		this.onplaying = onplaying;
	}

	/**
	 * @return the onprogress
	 */
	public Function getOnprogress() {
		return onprogress;
	}

	/**
	 * @param onprogress the onprogress to set
	 */
	public void setOnprogress(Function onprogress) {
		this.onprogress = onprogress;
	}

	/**
	 * @return the onreadystatechange
	 */
	public Function getOnreadystatechange() {
		return onreadystatechange;
	}

	/**
	 * @param onreadystatechange the onreadystatechange to set
	 */
	public void setOnreadystatechange(Function onreadystatechange) {
		this.onreadystatechange = onreadystatechange;
	}

	/**
	 * @return the onreset
	 */
	public Function getOnreset() {
		return onreset;
	}

	/**
	 * @param onreset the onreset to set
	 */
	public void setOnreset(Function onreset) {
		this.onreset = onreset;
	}

	/**
	 * @return the onscroll
	 */
	public Function getOnscroll() {
		return onscroll;
	}

	/**
	 * @param onscroll the onscroll to set
	 */
	public void setOnscroll(Function onscroll) {
		this.onscroll = onscroll;
	}

	/**
	 * @return the onseeked
	 */
	public Function getOnseeked() {
		return onseeked;
	}

	/**
	 * @param onseeked the onseeked to set
	 */
	public void setOnseeked(Function onseeked) {
		this.onseeked = onseeked;
	}

	/**
	 * @return the onseeking
	 */
	public Function getOnseeking() {
		return onseeking;
	}

	/**
	 * @param onseeking the onseeking to set
	 */
	public void setOnseeking(Function onseeking) {
		this.onseeking = onseeking;
	}

	/**
	 * @return the onselect
	 */
	public Function getOnselect() {
		return onselect;
	}

	/**
	 * @param onselect the onselect to set
	 */
	public void setOnselect(Function onselect) {
		this.onselect = onselect;
	}

	/**
	 * @return the onshow
	 */
	public Function getOnshow() {
		return onshow;
	}

	/**
	 * @param onshow the onshow to set
	 */
	public void setOnshow(Function onshow) {
		this.onshow = onshow;
	}

	/**
	 * @return the onstalled
	 */
	public Function getOnstalled() {
		return onstalled;
	}

	/**
	 * @param onstalled the onstalled to set
	 */
	public void setOnstalled(Function onstalled) {
		this.onstalled = onstalled;
	}

	/**
	 * @return the onsubmit
	 */
	public Function getOnsubmit() {
		return onsubmit;
	}

	/**
	 * @param onsubmit the onsubmit to set
	 */
	public void setOnsubmit(Function onsubmit) {
		this.onsubmit = onsubmit;
	}

	/**
	 * @return the onsuspend
	 */
	public Function getOnsuspend() {
		return onsuspend;
	}

	/**
	 * @param onsuspend the onsuspend to set
	 */
	public void setOnsuspend(Function onsuspend) {
		this.onsuspend = onsuspend;
	}

	/**
	 * @return the ontimeupdate
	 */
	public Function getOntimeupdate() {
		return ontimeupdate;
	}

	/**
	 * @param ontimeupdate the ontimeupdate to set
	 */
	public void setOntimeupdate(Function ontimeupdate) {
		this.ontimeupdate = ontimeupdate;
	}

	/**
	 * @return the onvolumechange
	 */
	public Function getOnvolumechange() {
		return onvolumechange;
	}

	/**
	 * @param onvolumechange the onvolumechange to set
	 */
	public void setOnvolumechange(Function onvolumechange) {
		this.onvolumechange = onvolumechange;
	}

	/**
	 * @return the onwaiting
	 */
	public Function getOnwaiting() {
		return onwaiting;
	}

	/**
	 * @param onwaiting the onwaiting to set
	 */
	public void setOnwaiting(Function onwaiting) {
		this.onwaiting = onwaiting;
	}

	/**
	 * @return the onmousemove
	 */
	public Function getOnmousemove() {
		return onmousemove;
	}

	/**
	 * @param onmousemove the onmousemove to set
	 */
	public void setOnmousemove(Function onmousemove) {
		this.onmousemove = onmousemove;
	}

	/**
	 * @return the onratechange
	 */
	public Function getOnratechange() {
		return onratechange;
	}

	/**
	 * @param onratechange the onratechange to set
	 */
	public void setOnratechange(Function onratechange) {
		this.onratechange = onratechange;
	}

	/**
	 * @return the onloadHandler
	 */
	public Function getOnloadHandler() {
		return onloadHandler;
	}

	/**
	 * @param onloadHandler the onloadHandler to set
	 */
	public void setOnloadHandler(Function onloadHandler) {
		this.onloadHandler = onloadHandler;
	}

	/**
	 * @return the onafterprint
	 */
	public Function getOnafterprint() {
		return onafterprint;
	}

	/**
	 * @param onafterprint the onafterprint to set
	 */
	public void setOnafterprint(Function onafterprint) {
		this.onafterprint = onafterprint;
	}

	/**
	 * @return the onbeforeprint
	 */
	public Function getOnbeforeprint() {
		return onbeforeprint;
	}

	/**
	 * @param onbeforeprint the onbeforeprint to set
	 */
	public void setOnbeforeprint(Function onbeforeprint) {
		this.onbeforeprint = onbeforeprint;
	}

	/**
	 * @return the onbeforeunload
	 */
	public Function getOnbeforeunload() {
		return onbeforeunload;
	}

	/**
	 * @param onbeforeunload the onbeforeunload to set
	 */
	public void setOnbeforeunload(Function onbeforeunload) {
		this.onbeforeunload = onbeforeunload;
	}

	/**
	 * @return the onhashchange
	 */
	public Function getOnhashchange() {
		return onhashchange;
	}

	/**
	 * @param onhashchange the onhashchange to set
	 */
	public void setOnhashchange(Function onhashchange) {
		this.onhashchange = onhashchange;
	}

	/**
	 * @return the onmessage
	 */
	public Function getOnmessage() {
		return onmessage;
	}

	/**
	 * @param onmessage the onmessage to set
	 */
	public void setOnmessage(Function onmessage) {
		this.onmessage = onmessage;
	}

	/**
	 * @return the onoffline
	 */
	public Function getOnoffline() {
		return onoffline;
	}

	/**
	 * @param onoffline the onoffline to set
	 */
	public void setOnoffline(Function onoffline) {
		this.onoffline = onoffline;
	}

	/**
	 * @return the ononline
	 */
	public Function getOnonline() {
		return ononline;
	}

	/**
	 * @param ononline the ononline to set
	 */
	public void setOnonline(Function ononline) {
		this.ononline = ononline;
	}

	/**
	 * @return the onpagehide
	 */
	public Function getOnpagehide() {
		return onpagehide;
	}

	/**
	 * @param onpagehide the onpagehide to set
	 */
	public void setOnpagehide(Function onpagehide) {
		this.onpagehide = onpagehide;
	}

	/**
	 * @return the onpageshow
	 */
	public Function getOnpageshow() {
		return onpageshow;
	}

	/**
	 * @param onpageshow the onpageshow to set
	 */
	public void setOnpageshow(Function onpageshow) {
		this.onpageshow = onpageshow;
	}

	/**
	 * @return the onpopstate
	 */
	public Function getOnpopstate() {
		return onpopstate;
	}

	/**
	 * @param onpopstate the onpopstate to set
	 */
	public void setOnpopstate(Function onpopstate) {
		this.onpopstate = onpopstate;
	}

	/**
	 * @return the onredo
	 */
	public Function getOnredo() {
		return onredo;
	}

	/**
	 * @param onredo the onredo to set
	 */
	public void setOnredo(Function onredo) {
		this.onredo = onredo;
	}

	/**
	 * @return the onresize
	 */
	public Function getOnresize() {
		return onresize;
	}

	/**
	 * @param onresize the onresize to set
	 */
	public void setOnresize(Function onresize) {
		this.onresize = onresize;
	}

	/**
	 * @return the onstorage
	 */
	public Function getOnstorage() {
		return onstorage;
	}

	/**
	 * @param onstorage the onstorage to set
	 */
	public void setOnstorage(Function onstorage) {
		this.onstorage = onstorage;
	}

	/**
	 * @return the onundo
	 */
	public Function getOnundo() {
		return onundo;
	}

	/**
	 * @param onundo the onundo to set
	 */
	public void setOnundo(Function onundo) {
		this.onundo = onundo;
	}

	/**
	 * @return the onbounce
	 */
	public Function getOnbounce() {
		return onbounce;
	}

	/**
	 * @param onbounce the onbounce to set
	 */
	public void setOnbounce(Function onbounce) {
		this.onbounce = onbounce;
	}

	/**
	 * @return the onfinish
	 */
	public Function getOnfinish() {
		return onfinish;
	}

	/**
	 * @param onfinish the onfinish to set
	 */
	public void setOnfinish(Function onfinish) {
		this.onfinish = onfinish;
	}

	/**
	 * @return the onstart
	 */
	public Function getOnstart() {
		return onstart;
	}

	/**
	 * @param onstart the onstart to set
	 */
	public void setOnstart(Function onstart) {
		this.onstart = onstart;
	}

	/**
	 * @return the onenter
	 */
	public Function getOnenter() {
		return onenter;
	}

	/**
	 * @param onenter the onenter to set
	 */
	public void setOnenter(Function onenter) {
		this.onenter = onenter;
	}

	/**
	 * @return the onexit
	 */
	public Function getOnexit() {
		return onexit;
	}

	/**
	 * @param onexit the onexit to set
	 */
	public void setOnexit(Function onexit) {
		this.onexit = onexit;
	}

	/**
	 * @return the onchecking
	 */
	public Function getOnchecking() {
		return onchecking;
	}

	/**
	 * @param onchecking the onchecking to set
	 */
	public void setOnchecking(Function onchecking) {
		this.onchecking = onchecking;
	}

	/**
	 * @return the onnoupdate
	 */
	public Function getOnnoupdate() {
		return onnoupdate;
	}

	/**
	 * @param onnoupdate the onnoupdate to set
	 */
	public void setOnnoupdate(Function onnoupdate) {
		this.onnoupdate = onnoupdate;
	}

	/**
	 * @return the ondownloading
	 */
	public Function getOndownloading() {
		return ondownloading;
	}

	/**
	 * @param ondownloading the ondownloading to set
	 */
	public void setOndownloading(Function ondownloading) {
		this.ondownloading = ondownloading;
	}

	/**
	 * @return the onupdateready
	 */
	public Function getOnupdateready() {
		return onupdateready;
	}

	/**
	 * @param onupdateready the onupdateready to set
	 */
	public void setOnupdateready(Function onupdateready) {
		this.onupdateready = onupdateready;
	}

	/**
	 * @return the oncached
	 */
	public Function getOncached() {
		return oncached;
	}

	/**
	 * @param oncached the oncached to set
	 */
	public void setOncached(Function oncached) {
		this.oncached = oncached;
	}

	/**
	 * @return the onobsolete
	 */
	public Function getOnobsolete() {
		return onobsolete;
	}

	/**
	 * @param onobsolete the onobsolete to set
	 */
	public void setOnobsolete(Function onobsolete) {
		this.onobsolete = onobsolete;
	}

	/**
	 * @return the onloadend
	 */
	public Function getOnloadend() {
		return onloadend;
	}

	/**
	 * @param onloadend the onloadend to set
	 */
	public void setOnloadend(Function onloadend) {
		this.onloadend = onloadend;
	}

}