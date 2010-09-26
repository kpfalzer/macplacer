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
/**
 *
 * @author karl
 */
public class Canvas extends java.awt.Canvas {
	public Canvas(Floorplan fplan) {
		m_fplan = fplan;
		/**
		 * Preferred height based on aspect ratio
		 */
		macplacer.geom.Dimension dim = m_fplan.getBoundingBox().getDimension();
		if (dim.getWidth() > dim.getHeight()) {
			m_preferredDimension.width = stPreferredMax;
			m_preferredDimension.height = (int)(stPreferredMax * dim.getAspectRatio());
		} else {
			m_preferredDimension.height = stPreferredMax;
			m_preferredDimension.width = (int)(stPreferredMax / dim.getAspectRatio());
		}
	}

	public void update(Floorplan fplan) {
		m_fplan = fplan;
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		double needWidth = m_fplan.getBoundingBox().getWidth();
		double needHeight = m_fplan.getBoundingBox().getHeight();
		m_scaleX = m_preferredDimension.getWidth() / needWidth;
		m_scaleY = m_preferredDimension.getHeight() / needHeight;
		g2.setStroke(stLine);
		transformAndDraw(g2, m_fplan.getBoundingBox(), false);
		for (Rectangle rect : m_fplan.getPlaced()) {
			transformAndDraw(g2, rect, true);
		}
	}

	@Override
	public Dimension getPreferredSize() {
		return m_preferredDimension;
	}

	/**
	 * Transform, scale and draw rectangle.
	 * @param g2 graphics context.
	 * @param rect rectangle to draw (after transform origin from ll to ul).
	 * @param doFill fill rectangle if true.
	 */
	private void transformAndDraw(Graphics2D g2, Rectangle rect, boolean doFill) {
		rect = transform(rect);
		if (doFill) {
			Color was = g2.getColor();
			g2.setColor(stBoxColor);
			g2.fill(rect);
			g2.setColor(was);
		}
		g2.draw(rect);
	}

	/**
	 * Transform origin from ll (lower-left) to ul (upper-left) and scale.
	 * @param rect rectangle to transform and scale.
	 * @return transform and scaled rectangle.
	 */
	private Rectangle transform(Rectangle rect) {
		double x = rect.getX() * m_scaleX;
		double y = m_fplan.getBoundingBox().getHeight();
		y -= (rect.getY() + rect.getHeight());
		y *= m_scaleY;
		double width = rect.getWidth() * m_scaleX;
		double height = rect.getHeight() * m_scaleY;
		return new Rectangle(new Point(x, y), new macplacer.geom.Dimension(width, height));
	}

	private Floorplan	m_fplan;
	private final static BasicStroke stLine = new BasicStroke(1.0f);
	/**
	 * Box color.
	 */
	private final static Color stBoxColor = Color.LIGHT_GRAY;
	/**
	 * Preferred max dimension.
	 */
	private final int stPreferredMax = 800;
	/**
	 * Preferred dimension.
	 */
	private Dimension	m_preferredDimension = new Dimension();
	/**
	 * Scaling factors.
	 */
	private double m_scaleX, m_scaleY;
}
