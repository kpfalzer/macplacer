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
		setRoot(root);
	}

	public BinaryTree(BinaryTree root) {
		m_root = root.m_root;
	}

	/**
	 * Create tree using add method (to impose any order).
	 * @param collection of elements.
	 */
	public BinaryTree(T... eles) {
		for (T ele : eles) {
			add(ele);
		}
	}

	public final Node setRoot(T root) {
		m_root = new Node<T>(root);
		return m_root;
	}

	/**
	 * Add new node in preorder (depth first).
	 * @param data node to add.
	 */
	public void add(T data) {
		if (isEmpty()) {
			setRoot(data);
		} else {
			Node last = getRoot();
			while (last.hasLeft()) {
				last = last.getLeft();
			}
			last.setLeft(last);
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

		public final Node setLeft(T ele) {
			assert(null == m_left);
			m_left = new Node(ele);
			return m_left;
		}

		public final Node setRight(T ele) {
			assert(null == m_right);
			m_right = new Node(ele);
			return m_right;
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
