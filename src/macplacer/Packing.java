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
/**
 * Set of cells to be packed together.
 * @author karl
 */
public class Packing extends BreadthFirstBinaryTree<Placed> {
	public Packing() {}

	/**
	 * Create relative packing of unplaced instances.
	 * @param insts level-order (breadth-first) collection of instances.
	 */
	public Packing(Instance... insts) {
		for (Instance inst : insts) {
			add(new Placed(inst));
		}
	}
	
	public double getArea() {
		DoubleValue area = new DoubleValue();
		super.preorder(new BinaryTreeNodeVisitorWithData<Placed,DoubleValue>(area) {
			public void visit(Placed data) {
				m_userData.val += data.getInstance().getLibCell().getArea();
			}
		});
		return area.val;
	}
}
