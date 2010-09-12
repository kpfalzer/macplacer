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
package macplacer;
import static macplacer.Util.asInt;
import  macplacer.geom.Dimension;
import	org.xml.sax.Attributes;

/**
 * Library cell object.
 * @author karl
 */
public class LibCell {
	public LibCell(String name, double height, double width, int npins) {
		m_refName = name;
		m_dimension = new Dimension(width, height);
		m_rotate  = null;
		m_pinCount = npins;
	}
	public LibCell(Attributes atts) {
		m_refName = atts.getValue("name");
		double h = Double.parseDouble(atts.getValue("height"));
		double w = Double.parseDouble(atts.getValue("width"));
		m_dimension = new Dimension(w, h);
		m_rotate  = atts.getValue("rotate");
		m_pinCount = asInt(atts.getValue("npins"), -1);
	}

	public String getName() {
		return m_refName;
	}

	public String getRotate() {
		return m_rotate;
	}

	public Dimension getDimension() {
		return m_dimension;
	}

	public double getArea() {
		return getDimension().getArea();
	}

	private	final String	m_refName, m_rotate;
	private final Dimension	m_dimension;
	/**
	 * Use to approximate wire impact.
	 */
	private final int	m_pinCount;
}
