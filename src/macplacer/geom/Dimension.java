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
import	java.awt.geom.Dimension2D;

/**
 *
 * @author karl
 */
public class Dimension extends Dimension2D {

	public Dimension() {
		this(0.0,0.0);
	}

	public Dimension(double width, double height) {
		setSize(width, height);
	}

	@Override
	public double getHeight() {
		return m_height;
	}

	@Override
	public double getWidth() {
		return m_width;
	}

	@Override
	public void setSize(double width, double height) {
		m_width = width;
		m_height = height;
	}

	public void setWidth(double width) {
		m_width = width;
	}

	public void setHeight(double height) {
		m_height = height;
	}

	public double getArea() {
		return getHeight() * getWidth();
	}

	private double	m_height, m_width;
}
