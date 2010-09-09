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
import	macplacer.geom.Point;
import  macplacer.geom.Rectangle;

/**
 * A placed Instance.
 * @author karl
 */
public class Placed extends Rectangle {
	/**
	 * Create unplaced placement.
	 * @param inst placed instance.
	 */
	public Placed(Instance inst) {
		this(inst, stUnplaced);
	}
	/**
	 * Create placement relative to origin (lower-left corner).
	 * @param inst placed instance.
	 * @param lowerLeft position (in floorplan) of lower-left corner.
	 */
	public Placed(Instance inst, Point lowerLeft) {
		super(lowerLeft, inst.getDimension());
		m_inst = inst;
	}

	public boolean isPlaced() {
		return (super.getX() >= 0);
	}

	public Instance getInstance() {
		return m_inst;
	}

	private final Instance	m_inst;
	private ERotation		m_rotation = ERotation.eNone;

	private static final Point stUnplaced = new Point(-1,-1);
}
