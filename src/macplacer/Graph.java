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
import	java.util.LinkedList;
import	java.util.List;
import	java.util.Collection;
import	java.util.Arrays;
import	java.util.Comparator;


/**
 * Undirected graph of nodes<N> and edges<E>.
 * @author karl
 */
public class Graph<N,E> {
	public Graph() {}

	public void addEdge(E edge, N val1, N val2) {
		Node nodes[] = {new Node<N>(val1), new Node<N>(val2)};
		Edge nedge = new Edge<N,E>(edge, nodes);
		m_edges.add(nedge);
	}

	public Collection<Edge<N,E>> getEdges() {
		return m_edges;
	}

	/**
	 * Sort edges.
	 * @param comp implements Comparator interface.
	 * @param descending set true for descending order, else ascending.
	 */
	public void sortEdges(final Comparator<E> comp, final boolean descending) {
		Edge<N,E> edges[] = m_edges.toArray(new Edge[0]);
		Arrays.sort(edges, new Comparator<Edge<N,E>>() {
			public int compare(Edge<N,E> o1, Edge<N,E> o2) {
				E e1 = o1.getEdgeValue();
				E e2 = o2.getEdgeValue();
				int c = comp.compare(e1, e2);
				if (descending) {	//reverse for descending
					c = (0 == c) ? 0 : ((c < 0) ? 1 : -1);
				}
				return c;
			}
		});
		m_edges = Util.toList(edges);
	}

	/**
	 * Sort edges in descending order.
	 * @param comp implements Comparator interface.
	 */
	public void sortEdgesDescending(final Comparator<E> comp) {
		sortEdges(comp, true);
	}

	@Override
	public String toString() {
		StringBuilder buf = new StringBuilder(this.getClass().getName()+":\n");
		for (Edge<N,E> edge : m_edges) {
			buf.append(edge.toString()).append('\n');
		}
		return buf.toString();
	}

	private List<Edge<N,E>>	m_edges = new LinkedList<Edge<N,E>>();

	public static class Node<N> {
		public Node(N val) {
			m_val = val;
		}
		public Node() {
			this(null);
		}
		public N getVal() {
			return m_val;
		}
		@Override
		public String toString() {
			return m_val.toString();
		}

		private N	m_val;
	}
	public static class Edge<N,E> {
		public Edge(E edgeVal, Node<N> n1, Node<N> n2) {
			m_edgeValue = edgeVal;
			m_nodes[0] = n1;
			m_nodes[1] = n2;
		}
		public Edge(E edgeVal, Node<N>[] nodes) {
			this(edgeVal, nodes[0], nodes[1]);
		}
		public Edge(Node<N> n1, Node<N> n2) {
			this(null,n1,n2);
		}
		public Edge() {
			this(null,null,null);
		}
		public void setEdgeValue(E edgeVal) {
			m_edgeValue = edgeVal;
		}
		public E getEdgeValue() {
			return m_edgeValue;
		}
		public Node<N>[] getNodes() {
			return m_nodes;
		}
		@Override
		public String toString() {
			StringBuilder buf = new StringBuilder("Edge val: ");
			buf.append(m_edgeValue.toString()).append(". Nodes: ");
			buf.append(m_nodes[0].toString()).append(" : ").append(m_nodes[1].toString());
			return buf.toString();
		}

		private	E		m_edgeValue;
		private Node	m_nodes[] = {null,null};
	}
}
