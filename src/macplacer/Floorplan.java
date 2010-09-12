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
import	macplacer.geom.Dimension;
import	macplacer.geom.Rectangle;

/**
 *
 * @author karl
 */
public class Floorplan {
	public Floorplan(Dimension dimension) {
		m_bbox = new Rectangle(dimension);
	}

	public Rectangle getBoundingBox() {
		return m_bbox;
	}

	public void addPlaced(Iterable<Placed> placed) {
		m_placed = placed;
	}

	public Iterable<Placed> getPlaced() {
		return m_placed;
	}

	/**
	 * Floorplan bounding box (in drawing space).
	 */
	private final Rectangle		m_bbox;
	private Iterable<Placed>	m_placed;
}
