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
import	java.awt.geom.Rectangle2D;

/**
 * Rectangle with origin (state) wrt. awt/drawing space:
 * i.e., origin in awt space is upper-left;
 * in placement space, origin is lower-left.
 * @author karl
 */
public class Rectangle extends Rectangle2D.Double {
	/**
	 * Create Rectangle with lower-left Point origin.
	 * @param ll lower-left origin.
	 * @param w width of rectangle.
	 * @param h height of rectangle.
	 */
	public Rectangle(Point ll, double w, double h) {
		super(ll.getX(), ll.getY() + h, w, h);
	}

	/**
	 * Create Rectangle with lower-left discrete origin points.
	 * @param llx lower-left x coordinate.
	 * @param lly lower-left y coordinate.
	 * @param w width of rectangle.
	 * @param h height of rectangle.
	 */
	public Rectangle(double llx, double lly, double w, double h) {
		this(new Point(llx, lly), w, h);
	}

	/**
	 * Create Rectangle with lower-left origin at (0,0) in placement space.
	 * @param llx lower-left x coordinate.
	 * @param lly lower-left y coordinate.
	 * @param w width of rectangle.
	 * @param h height of rectangle.
	 */
	public Rectangle(double w, double h) {
		this(0, 0, w, h);
	}

	/**
	 * Return lower-left origin.
	 */
	public Point getLowerLeft() {
		return new Point(x, y - height);
	}
}
