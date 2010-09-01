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
	 * Create placement at lower-left corner (i.e., (0,0)).
	 * @param inst placed instance.
	 */
	public Placed(Instance inst) {
		this(inst, new Point());
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

	private final Instance	m_inst;
	private	Point			m_lowerLeft = new Point();
	private ERotation		m_rotation = ERotation.eNone;
}
