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
import	macplacer.geom.Corner;
import static macplacer.Util.invariant;

/**
 * Base class for macro placer algorithm.
 * @author karl
 */
public abstract class Algorithm {
	/**
	 * Create base algorithm.
	 */
	protected Algorithm(Design design) {
		m_design = design;
		for (Corner corner : m_design.getFplan().getBoundingBox().getContourIterator()) {
			m_maxPacks++;
		}
	}
	/**
	 * Create initial packing tree.
	 */
	public abstract void getInitialPackingTree();

	/**
	 * Create another packing tree by perturbing current one.
	 */
	public abstract void iterate();

    /**
     * Update placements in floorplan.
     * This method must be called after any packing tree updates.
     * However, since this method is only useful for displaying floorplan
     * it can be skipped during iterations.
     */
    public void updateFloorplan() {
        invariant(1 > m_placedAndUnplaced.getUnplaced().size());
        m_design.getFplan().addPlaced(m_placedAndUnplaced.getPlaced());
    }

    /**
     * Check for overlaps.
     * @return number of overlaps.
     */
    public int checkForOverlaps() {
        int overlaps = 0;
        for (Placed p1 : m_placedAndUnplaced.getPlaced()) {
            for (Placed p2 : m_placedAndUnplaced.getPlaced()) {
                if (!p1.equals(p2) && p1.intersects(p2)) {
                    overlaps++;
                }
            }
        }
        return overlaps;
    }
	
	/**
	 * Iteration.
	 */
	protected int	m_iteration = 0;
	/**
	 * Maximum/preferred number of packs.
	 */
	protected int	m_maxPacks = 0;
	/**
	 * Current packing.
	 */
	protected PackingTree	m_packing;
	protected Design		m_design;

    protected PackingTree.PlacedAndUnplaced m_placedAndUnplaced;
};
