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
	public BinaryTree() {}

	private Node<T>	m_root = null;

	public static class Node<T> {
		public Node(T data) {
			m_data = data;
		}

		public Node(T data, Node left, Node right) {
			this(data);
			m_left = left;
			m_right = right;
		}

		public void setLeft(Node ele) {
			assert(null == m_left);
			m_left = ele;
		}

		public void setRight(Node ele) {
			assert(null == m_right);
			m_right = ele;
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
