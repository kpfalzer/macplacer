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
import static macplacer.Util.invariant;
import	java.util.Queue;
import	java.util.LinkedList;
import	java.util.List;

/**
 * A BinaryTree which overrides add to implement breadth-first (level-order).
 * @author karl
 * @param <T>
 */
public class BreadthFirstBinaryTree<T> extends BinaryTree<T> {
	public BreadthFirstBinaryTree() {}

	public BreadthFirstBinaryTree(Iterable<T> eles) {
		super();
		super.constructor(eles);
	}

	public BreadthFirstBinaryTree(BreadthFirstBinaryTree tree) {
		super(tree);
	}

	@Override
	public void add(T data) {
		Node added = null;
		if (m_fifo.isEmpty()) {
			added = super.setRoot(data);
		} else {
			Node node = m_fifo.peek();
			invariant(!node.hasRight());
			if (!node.hasLeft()) {
				added = node.setLeft(data);
			} else {
				added = node.setRight(data);
				m_fifo.remove();	//pop
			}
		}
		m_fifo.add(added);
	}

	/**
	 * Rotate tree.
	 * @param n number of positions to shift.
	 * @return permuted tree.
	 */
	public BreadthFirstBinaryTree<T> rotate(int n) {
		List<T> asList = super.asList();
		for (; n > 0; n--) {
			T ele = asList.remove(0);
			asList.add(ele);
		}
		return new BreadthFirstBinaryTree<T>(asList);
	}
	
	private Queue<Node>	m_fifo = new LinkedList<Node>();
}

