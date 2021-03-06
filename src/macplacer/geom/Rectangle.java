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
 * Rectangle with origin at lower-left.
 * @author karl
 */
public class Rectangle extends Rectangle2D.Double {
	public static enum ERotation {
		eNone, ePos90
	}
	/**
	 * Create Rectangle with lower-left Point origin.
	 * @param ll lower-left origin.
	 * @param dimenstion height and width of rectangle.
	 * @param h height of rectangle.
	 */
	public Rectangle(Point ll, Dimension dimension) {
		setCoordinates(ll, dimension);
	}

	/**
	 * Create Rectangle with lower-left origin at (0,0).
	 * @param llx lower-left x coordinate.
	 * @param lly lower-left y coordinate.
	 * @param dimension dimension of rectangle.
	 */
	public Rectangle(Dimension dimension) {
		this(new Point(), dimension);
	}

	/**
	 * Return lower-left origin.
	 */
	public Point getLowerLeft() {
		return new Point(x, y - height);
	}

	/**
	 * Return dimension.
	 */
	public Dimension getDimension() {
		return new Dimension(getWidth(), getHeight());
	}

	public Iterable<Corner> getContourIterator() {
		return new Contour(this);
	}

	/**
	 * Rotate given orientation and translate to draw orientation.
	 * @param lowerLeft lower-left (origin) of basic orientation.
	 * @param dimension dimension of basic orientation.
	 * @param rotate
	 */
	public void rotate(Point lowerLeft, Dimension dimension, ERotation rotate) {
		switch (rotate) {
			case ePos90: {	//swap height <-> width
				dimension.setSize(dimension.getHeight(), dimension.getWidth());
			}
		}
		setCoordinates(lowerLeft, dimension);
	}

	public void setLowerLeft(Point lowerLeft) {
		setLowerLeft(lowerLeft.getX(),lowerLeft.getY());
	}

	public void setLowerLeft(double x, double y) {
		super.x = x;
		super.y = y;
	}

    /**
     * Check if two rectangles intersect.
     * @param rd other rectangle.
     * @return true if rectangles intersect, otherwise false.
     */
    public boolean intersects(Rectangle rd) {
        boolean xin = anyPointBetween(x, x+width, rd.x, rd.x+rd.width);
        boolean yin = anyPointBetween(y, y+height, rd.y, rd.y+rd.height);
        return xin & yin;
    }

    private boolean anyPointBetween(double p1, double p2, double q1, double q2) {
        return ((p1>q1 && p1 <q2) || (p2>q1 && p2<q2));
    }

	/**
	 * Set coordinates.
	 * @param lowerLeft lower-left corner of rectangle.
	 * @param dimension dimension of rectangle.
	 */
	private void setCoordinates(Point lowerLeft, Dimension dimension) {
		super.width = dimension.getWidth();
		super.height = dimension.getHeight();
		setLowerLeft(lowerLeft);
	}
}
