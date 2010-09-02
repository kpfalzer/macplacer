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
import	java.util.ArrayList;
import	java.util.Comparator;
import	java.util.Collection;
import	java.util.HashMap;

/**
 * Default algorithm.
 * @author karl
 */
public class DefaultAlgorithm implements Algorithm {
	/**
	 * Cluster macros which share the longest instance name prefix.
	 * @param desgn design containing macros.
	 * @return un-ordered list of clusters.
	 */
	public Cluster getClusters(Design desgn) {
		Graph g = Util.measureCommonPrefix(desgn.getInstances(), Design.stHierSep);
		g.sortEdgesDescending(new Comparator<Integer>() {
			public int compare(Integer o1, Integer o2) {
				return o1.compareTo(o2);
			}
		});
		/*DBG*/System.out.println(g.toString());
		Collection<Graph.Edge<Instance,Integer>> edges = g.getEdges();
		HashMap<Instance,Integer> mapToCluster = new HashMap<Instance,Integer>();
		ArrayList<Lcluster> clusters = new ArrayList<Lcluster>(10);
		/*
		 * Visit every edge and put nodes into same cluster.
		 */
		for (Graph.Edge<Instance,Integer> edge : edges) {
			Graph.Node<Instance> nodes[] = edge.getNodes();
			Instance key = null;
			for (Graph.Node<Instance> node : nodes) {
				key = node.getVal();
				if (mapToCluster.containsKey(key)) {
					break;	//for
				}
				key = null;
			}
			Lcluster useCluster = null;
			int mapIx = -1;	//index into cluster
			if (null == key) {	//start new cluster
				useCluster = new Lcluster();
				key = nodes[0].getVal();	//just pick one
				useCluster.add(key);
				mapIx = clusters.size();
				mapToCluster.put(key, mapIx);
				clusters.add(useCluster);
			} else {
				mapIx = mapToCluster.get(key);
				useCluster = clusters.get(mapIx);
			}
			for (Graph.Node<Instance> node : nodes) {
				key = node.getVal();
				if (!mapToCluster.containsKey(key)) {
					useCluster.add(key);
					mapToCluster.put(key, mapIx);
				}
			}
		}
		/*
		 * Visit all nodes and add any un-clustered ones to together in
		 * new cluster.
		 */
		Lcluster miscCluster = null;
		for (Instance inst : desgn.getInstances()) {
			if (!mapToCluster.containsKey(inst)) {
				if (null == miscCluster) {
					miscCluster = new Lcluster();
					clusters.add(miscCluster);
				}
				miscCluster.add(inst);
			}
		}
		clusters.trimToSize();
		/*DBG*/ System.out.println(clusters.toString());
		m_clusters = toCluster(clusters);
		return m_clusters;
	}

	private Cluster	m_clusters;

	/**
	 * Return binary tree of clusters.
	 * @param clusters list of Lcluster.
	 * @return binary tree representation of clusters.
	 */
	private Cluster toCluster(ArrayList<Lcluster> clist) {
		ArrayList<ClusterNode> clusters = new ArrayList<ClusterNode>(clist.size());
		for (Lcluster c : clist) {
			ClusterNode clust = c.toNode();
			clusters.add(clust);
		}
		Cluster rval = new Cluster(clusters);
		return rval;
	}

	/**
	 * Group of macro instances to be clustered.
	 * @author karl
	 */
	private class Lcluster extends ArrayList<Instance> {
	
		@Override
		public String toString() {
			StringBuilder buf = new StringBuilder(getClass().getName()+":\n");
			final String pfx = "    ";
			for (Instance inst : this) {
				buf.append(pfx).append(inst.getName()).append('\n');
			}
			return buf.toString();
		}
	
		/**
		 * Convert to left-biased binary tree (i.e., only left children are present).
		 * @return binary tree representation.
		 */
		public ClusterNode toNode() {
			return new ClusterNode(this);
		}
	};
};
