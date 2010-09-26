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
import  java.util.Queue;

/**
 * A binary tree structure with links to parent node too.
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
	public void constructor(Iterable<T> eles) {
		for (T ele : eles) {
			add(ele);
		}
	}

	public final Node<T> setRoot(T root) {
		m_root = new Node<T>(root, null);
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
			last.setLeft(data);
		}
	}

	/**
	 * Count number of non-null nodes.
	 * @return number of non-null nodes.
	 */
	public int getNodeCount() {
		IntegerValue cnt = new IntegerValue();
		preOrder(new BinaryTreeNodeVisitorWithData<T,IntegerValue>(cnt) {
			public void visit(Node node) {
				m_userData.val++;
			}
		});
		return cnt.val;
	}

    /**
     * Return tree as list in level-order.
     * @return level-order (breadth-first) tree elements.
     */
    public List<T> asList() {
        List<T> list = new LinkedList<T>();
        levelOrder(new BinaryTreeNodeVisitorWithData<T, List<T>>(list) {

            public void visit(Node<T> node) {
                m_userData.add(node.getData());
            }

        });
        return list;
    }

	public boolean isEmpty() {
		return (null == m_root);
	}

	public final Node<T> getRoot() {
		return m_root;
	}

	/**
	 * Depth-first (pre-order) traversal.
	 * Pre-order is root, left-subtree, right-subtree.
	 * @param callback execute for each node during traversal.
	 */
	public void preOrder(BinaryTreeNodeVisitor<T> callback) {
		if (null != m_root) {
			m_root.preOrder(callback);
		}
	}

	/**
	 * Breadth-first (level-order) traversal.
	 * @param callback execute for each node during traversal.
	 */
	public void levelOrder(BinaryTreeNodeVisitor<T> callback) {
		if (isEmpty()) {
            return;
        }
        Queue<Node<T>> queue = new LinkedList<Node<T>>();
        queue.add(m_root);
        while (!queue.isEmpty()) {
            Node<T> node = queue.peek();
            callback.visit(node);
            if (node.hasLeft()) {
                queue.add(node.getLeft());
            }
            if (node.hasRight()) {
                queue.add(node.getRight());
            }
            queue.remove();
        }
	}

	private Node<T>	m_root = null;

	public static class Node<T> {
		public Node(T data, Node parent) {
			m_data = data;
            m_parent = parent;
		}

		public void preOrder(BinaryTreeNodeVisitor<T> callback) {
			callback.visit(this);
			if (null != getLeft()) {
				getLeft().preOrder(callback);
			}
			if (null != getRight()) {
				getRight().preOrder(callback);
			}
		}

		public T getData() {
			return m_data;
		}

		public final Node<T> setLeft(T ele) {
			assert(null == m_left);
			m_left = new Node(ele, this);
			return m_left;
		}

		public final Node<T> setRight(T ele) {
			assert(null == m_right);
			m_right = new Node(ele, this);
			return m_right;
		}

		public Node<T> getLeft() {
			return m_left;
		}

		public Node<T> getRight() {
			return m_right;
		}

        public Node<T> getParent() {
            return m_parent;
        }

		/**
		 * Check if this node is the left child of its parent.
		 * @return true if node is left child of parent, else false.
		 *         Return false if node has no parent.
		 */
		public boolean isLeftChild() {
			return (null == getParent()) ? false : getParent().getLeft().equals(this);
		}
		
		/**
		 * Check if this node is the right child of its parent.
		 * @return true if node is right child of parent, else false.
		 *         Return false if node has no parent.
		 */
		public boolean isRightChild() {
			return (null == getParent()) ? false : getParent().getRight().equals(this);
		}

		public T getParentData() {
			return (null == getParent()) ? null : getParent().getData();
		}

		public boolean hasLeft() {
			return (null != m_left);
		}

		public boolean hasRight() {
			return (null != m_right);
		}

		private final T	m_data;
		private Node<T>	m_left, m_right, m_parent;
	}
}
