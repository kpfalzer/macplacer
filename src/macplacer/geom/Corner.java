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

/**
 * Corner objects indicates offset from (0,0) wrt. indicated corner.
 * @author karl
 */
public class Corner extends Point {
	public static enum ECorner {
		eLowerLeft, eLowerRight, eUpperLeft, eUpperRight
	}

	public Corner(double x, double y) {
		this(x, y, ECorner.eLowerLeft, true);
	}

	public Corner(double x, double y, ECorner corner) {
		this(x, y, corner, true);
	}

	public Corner(double x, double y, ECorner corner, boolean concave) {
		super(x, y);
		m_corner = corner;
		m_concave = true;
	}

	public ECorner getCorner() {
		return m_corner;
	}

	/**
	 * Corner is 90deg concave (as opposed to 270deg convex).
	 * @return true if corner is 90deg (concave).
	 */
	public boolean isConcave() {
		return m_concave;
	}

	private final ECorner m_corner;
	private final boolean m_concave;
}
