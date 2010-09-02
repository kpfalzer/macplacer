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
import	java.util.List;

/**
 *
 * @author karl
 */
public class BinaryTree<T> {
	/**
	 * Create an empty binary tree.
	 */
	public BinaryTree() {}

	/**
	 * Create a binary tree with root.
	 * @param root root node.
	 */
	public BinaryTree(T root) {
		m_root = new Node<T>(root);
	}

	/**
	 * Create left-biased (only left children used) binary tree.
	 * @param list of elements.
	 */
	public BinaryTree(List<T> eles) {
		if (eles.isEmpty()) {
			return;
		}
		m_root = new Node(eles.get(0));
		Node<T> last = getRoot();
		for (int i = 1; i < eles.size(); i++) {
			last.setLeft(eles.get(i));
			last = last.getLeft();
		}
	}

	/**
	 * Count number of non-null nodes.
	 * @return number of non-null nodes.
	 */
	public int getNodeCount() {
		IntegerValue cnt = new IntegerValue();
		preorder(new BinaryTreeNodeVisitorWithData<T,IntegerValue>(cnt) {
			public void visit(T data) {
				m_userData.val++;
			}
		});
		return cnt.val;
	}

	public boolean isEmpty() {
		return (null == m_root);
	}

	public final Node<T> getRoot() {
		return m_root;
	}

	/**
	 * Depth-first (preorder) traversal.
	 * Preorder is root, left-subtree, right-subtree.
	 * @param callback execute for each node during traversal.
	 */
	public void preorder(BinaryTreeNodeVisitor<T> callback) {
		if (null != m_root) {
			m_root.preorder(callback);
		}
	}

	private Node<T>	m_root = null;

	public static class Node<T> {
		public Node(T data) {
			m_data = data;
		}

		public Node(T data, T left, T right) {
			this(data);
			setLeft(left);
			setRight(right);
		}

		public void preorder(BinaryTreeNodeVisitor<T> callback) {
			callback.visit(getData());
			if (null != getLeft()) {
				getLeft().preorder(callback);
			}
			if (null != getRight()) {
				getRight().preorder(callback);
			}
		}

		public T getData() {
			return m_data;
		}

		public final void setLeft(T ele) {
			assert(null == m_left);
			m_left = new Node(ele);
		}

		public final void setRight(T ele) {
			assert(null == m_right);
			m_right = new Node(ele);
		}

		public Node getLeft() {
			return m_left;
		}

		public Node getRight() {
			return m_right;
		}

		public boolean hasLeft() {
			return (null != m_left);
		}

		public boolean hasRight() {
			return (null != m_right);
		}

		private final T	m_data;
		private Node	m_left, m_right;
	}
}

/**
 * Interface for binary tree node visitor.
 * @author karl
 * @param <N> node type.
 */
interface BinaryTreeNodeVisitor<N> {
	/**
	 * Callback during binary tree traversal/visits.
	 * @param node current node being traversed.
	 */
	public void visit(N node);
}

/**
 * Extends binary tree node visitor to add user data.
 * @author karl
 * @param <N> node type.
 * @param <T> user data type.
 */
abstract class BinaryTreeNodeVisitorWithData<N,T>
		implements BinaryTreeNodeVisitor<N> {
	public BinaryTreeNodeVisitorWithData(T userData) {
		m_userData = userData;
	}
	protected T	m_userData;
}


