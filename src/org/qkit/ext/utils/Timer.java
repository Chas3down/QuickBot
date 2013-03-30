/*
 This file is a part of QuickBot.

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package org.qkit.ext.utils;

/**
 * For use in specific tasks.
 *
 * @author Clisprail
 * @author Parameter
 * 
 */
public class Timer
{

	private long start;
    private long end;

	/**
	 * Timer Constructor
	 * 
	 * @param end How long the timer should last in milliseconds.
	 */
	public Timer(long end)
	{

		start = System.currentTimeMillis();
		this.end = System.currentTimeMillis() + end;
	}

	/**
	 * Timer Constructor
	 */
	public Timer()
	{
		this(0);
	}

	/**
	 * Determines the remaining time left.
	 * 
	 * @return the remaining time.
	 */
	public long getRemaining() {
		return end - System.currentTimeMillis();
	}

	/**
	 * Determines if the end time has been reached, does not mean it stopped running.
	 */
	public boolean isFinished() {
		return System.currentTimeMillis() > end;
	}

	/**
	 * Stops and resets the timer
	 */
	public void restart() {
		stop();
		reset();
	}

	/**
	 * Resets the timer if stopped
	 */
	public void reset() {
		if (start == 0) {
			start = System.currentTimeMillis();
		}
	}

	/**
	 * Resets the timer
	 */
	public void stop() {
		end = (end - start) + System.currentTimeMillis();
		start = 0;
	}

	/**
	 * Determines if timer is running
	 * 
	 * @return <b>true</b> if timer is running
	 */
	public boolean isRunning() {
		return start != 0;
	}

	/**
	 * Gets the run time in long millis.
	 * 
	 * @return the elapsed time.
	 */
	public long getElapsedTime() {
		return System.currentTimeMillis() - start;
	}

	/**
	 * Calculates hourly gains based on given variable
	 * 
	 * @param gained
	 *            variable
	 * @return hourly gains
	 */
	public int getPerHour(int gained) {
		return (int) ((gained) * 3600000D / (System.currentTimeMillis() - start));
	}

	/**
	 * Generates string based on HH:MM:SS
	 * 
	 * @return String
	 */
	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		long elapsed = getElapsedTime();
		int second = (int) (elapsed / 1000 % 60);
		int minute = (int) (elapsed / 60000 % 60);
		int hour = (int) (elapsed / 3600000 % 60);
		b.append(hour < 10 ? "0" : "").append(hour).append(":");
		b.append(minute < 10 ? "0" : "").append(minute).append(":");
		b.append(second < 10 ? "0" : "").append(second);
		return new String(b);
	}
}