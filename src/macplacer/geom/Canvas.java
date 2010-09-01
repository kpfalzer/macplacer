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
import	macplacer.Floorplan;
import	java.awt.Dimension;
import	java.awt.Graphics;
import	java.awt.Graphics2D;
import	java.awt.BasicStroke;
import	java.awt.Color;
import	java.awt.geom.AffineTransform;

/**
 *
 * @author karl
 */
public class Canvas extends java.awt.Canvas {
	public Canvas(Floorplan fplan) {
		m_fplan = fplan;
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		g2.setStroke(stLine);
		transformAndDraw(g2, m_fplan.getBoundingBox(), false);
		for (Rectangle rect : m_fplan.getPlaced()) {
			transformAndDraw(g2, rect, true);
		}
	}

	@Override
	public Dimension getPreferredSize() {
		double width  = (2 * stX0) + m_fplan.getBoundingBox().getWidth();
		double height = (2 * stY0) + m_fplan.getBoundingBox().getHeight();
		return new Dimension((int)width, (int)height);
	}

	private void transformAndDraw(Graphics2D g2, Rectangle rect, boolean doFill) {
		AffineTransform xform = getAffineTransform(rect);
		g2.setTransform(xform);
		if (doFill) {
			Color was = g2.getColor();
			g2.setColor(stBoxColor);
			g2.fill(rect);
			g2.setColor(was);
		}
		g2.draw(rect);
	}

	private AffineTransform getAffineTransform(Rectangle rect) {
		double tx = stX0;
		double ty = stY0 + m_fplan.getBoundingBox().getHeight();
		ty -= (rect.getY() + rect.getHeight());
		return AffineTransform.getTranslateInstance(tx, ty);
	}

	private final Floorplan	m_fplan;
	private final static BasicStroke stLine = new BasicStroke(1.0f);
	/**
	 * (x,y) offset of upper-left corner.
	 */
	private final static double stX0 = 20.0, stY0 = 20.0;
	/**
	 * Box color.
	 */
	private final static Color stBoxColor = Color.LIGHT_GRAY;
}
