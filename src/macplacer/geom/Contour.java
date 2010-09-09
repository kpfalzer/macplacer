/*
 *************************************************************************
 *************************************************************************
 **                                                                     **
 **  MACPLACER                                                          **
 **  Copyright (C) 2010         Karl W. Pfalzer                         **
 **                                                                     **
 **  This program is free software; you can redistribute it and/or      **
 **  modify it under the terms of the GNU General Public License        **
 **  as published by the Free Software Foundation; either version 2     **
 **  of the License, or (at your option) any later version.             **
 **                                                                     **
 **  This program is distributed in the hope that it will be useful,    **
 **  but WITHOUT ANY WARRANTY; without even the implied warranty of     **
 **  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the      **
 **  GNU General Public License for more details.                       **
 **                                                                     **
 **  You should have received a copy of the GNU General Public License  **
 **  along with this program; if not, write to the                      **
 **  Free Software Foundation, Inc.                                     **
 **  51 Franklin Street, Fifth Floor                                    **
 **  Boston, MA  02110-1301, USA.                                       **
 **                                                                     **
 *************************************************************************
 *************************************************************************
 */
package macplacer.geom;

import	java.util.Iterator;
import	java.util.ArrayList;

/**
 * Iterate contour in a counter-clockwise direction starting from lower-left corner.
 * @author karl
 */
public class Contour implements Iterable<Corner> {

	public Contour(Rectangle rect) {
		m_iterator = new RectangleContour(rect);
	}

	public Iterator<Corner> iterator() {
		return m_iterator;
	}

	private Iterator<Corner> m_iterator;

	public static class RectangleContour implements Iterator<Corner> {
		public RectangleContour(Rectangle rectangle) {
			m_rectangle = rectangle;
			initialize();
		}

		public boolean hasNext() {
			return m_iter.hasNext();
		}

		public Corner next() {
			return m_iter.next();
		}

		public void remove() {
			throw new UnsupportedOperationException("Invalid operation.");
		}

		private void initialize() {
			double x = 0, y = 0;
			addCorner(x, y, Corner.ECorner.eLowerLeft);
			x = m_rectangle.getWidth();
			addCorner(x, y, Corner.ECorner.eLowerRight);
			y = m_rectangle.getHeight();
			addCorner(x, y, Corner.ECorner.eUpperRight);
			x = 0;
			addCorner(x, y, Corner.ECorner.eUpperLeft);
			m_iter = m_contour.iterator();
		}

		private void addCorner(double x, double y, Corner.ECorner corner) {
			m_contour.add(new Corner(x, y, corner));
		}

		private final Rectangle		m_rectangle;
		private ArrayList<Corner>	m_contour = new ArrayList<Corner>(4);
		private Iterator<Corner>	m_iter;
	}
}
