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
import	org.xml.sax.Attributes;

/**
 * Library cell object.
 * @author karl
 */
public class LibCell {
	public LibCell(String name, double height, double width) {
		m_refName = name;
		m_height  = height;
		m_width	  = width;
		m_rotate  = null;
	}
	public LibCell(Attributes atts) {
		m_refName = atts.getValue("name");
		m_height  = Double.parseDouble(atts.getValue("height"));
		m_width	  = Double.parseDouble(atts.getValue("width"));
		m_rotate  = atts.getValue("rotate");
	}

	public String getName() {
		return m_refName;
	}

	public String getRotate() {
		return m_rotate;
	}

	public double getWidth() {
		return m_width;
	}

	public double getHeight() {
		return m_height;
	}
	
	private	final String	m_refName, m_rotate;
	private final double	m_height, m_width;
}
