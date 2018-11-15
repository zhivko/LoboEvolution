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

import org.mozilla.javascript.Function;

/**
 * The Interface MediaController.
 */
public interface MediaController {

	/**
	 * Gets the buffered.
	 *
	 * @return the buffered
	 */
	// MediaController
	public TimeRanges getBuffered();

	/**
	 * Gets the seekable.
	 *
	 * @return the seekable
	 */
	public TimeRanges getSeekable();

	/**
	 * Gets the duration.
	 *
	 * @return the duration
	 */
	public double getDuration();

	/**
	 * Gets the current time.
	 *
	 * @return the current time
	 */
	public double getCurrentTime();

	/**
	 * Sets the current time.
	 *
	 * @param currentTime
	 *            the new current time
	 */
	public void setCurrentTime(double currentTime);

	/**
	 * Gets the paused.
	 *
	 * @return the paused
	 */
	public boolean getPaused();

	/**
	 * Gets the played.
	 *
	 * @return the played
	 */
	public TimeRanges getPlayed();

	/**
	 * Play.
	 */
	public void play();

	/**
	 * Pause.
	 */
	public void pause();

	/**
	 * Gets the default playback rate.
	 *
	 * @return the default playback rate
	 */
	public double getDefaultPlaybackRate();

	/**
	 * Sets the default playback rate.
	 *
	 * @param defaultPlaybackRate
	 *            the new default playback rate
	 */
	public void setDefaultPlaybackRate(double defaultPlaybackRate);

	/**
	 * Gets the playback rate.
	 *
	 * @return the playback rate
	 */
	public double getPlaybackRate();

	/**
	 * Sets the playback rate.
	 *
	 * @param playbackRate
	 *            the new playback rate
	 */
	public void setPlaybackRate(double playbackRate);

	/**
	 * Gets the volume.
	 *
	 * @return the volume
	 */
	public double getVolume();

	/**
	 * Sets the volume.
	 *
	 * @param volume
	 *            the new volume
	 */
	public void setVolume(double volume);

	/**
	 * Gets the muted.
	 *
	 * @return the muted
	 */
	public boolean getMuted();

	/**
	 * Sets the muted.
	 *
	 * @param muted
	 *            the new muted
	 */
	public void setMuted(boolean muted);
}
