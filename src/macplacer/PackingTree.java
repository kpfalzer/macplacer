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
import  java.util.LinkedList;
import  java.util.List;
import macplacer.BinaryTree.Node;

/**
 * Collection of "per-corner" locally optimized Packing.
 * @author karl
 */
public class PackingTree extends BinaryTree<Packing> {
	public PackingTree() {}

    /**
     * Get placed and unplaced objects in no (guaranteed) particular order.
     * @return list of placed and unplaced objects.
     */
    public PlacedAndUnplaced getPlacedAndUnplaced() {
        PlacedAndUnplaced pnu = new PlacedAndUnplaced();
        for (Packing packing : super.asList()) {
            for (Placed i : packing.asList()) {
                if (i.isPlaced()) {
                    pnu.getPlaced().add(i);
                } else {
                    pnu.getUnplaced().add(i);
                }
            }
        }
        return pnu;
    }

	@Override
	public String toString() {
		final StringBuilder s = new StringBuilder();
		Integer i = 0;
		super.preOrder(new BinaryTreeNodeVisitorWithData<Packing, Integer>(i) {
			public void visit(Node<Packing> node) {
				s.append("\nGroup ").append(m_userData).append('\n')
						.append(node.getData().toString());
				m_userData++;
			}
		});
		return s.toString();
	}


    public static class PlacedAndUnplaced {
        private PlacedAndUnplaced() {}

        public List<Placed> getPlaced() {
            return m_pnu.m_ele1;
        }
        public List<Placed> getUnplaced() {
            return m_pnu.m_ele2;
        }

        private Pair<List<Placed>,List<Placed>> m_pnu = new
                Pair(new LinkedList<Placed>(), new LinkedList<Placed>());
    }
};
