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
	public List<Cluster> getClusters(Design desgn) {
		Graph g = Util.measureCommonPrefix(desgn.getInstances(), Design.stHierSep);
		g.sortEdgesDescending(new Comparator<Integer>() {
			public int compare(Integer o1, Integer o2) {
				return o1.compareTo(o2);
			}
		});
		/*DBG*/System.out.println(g.toString());
		Collection<Graph.Edge<Instance,Integer>> edges = g.getEdges();
		HashMap<Instance,Integer> mapToCluster = new HashMap<Instance,Integer>();
		ArrayList<Cluster> clusters = new ArrayList<Cluster>(10);
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
			Cluster useCluster = null;
			int mapIx = -1;	//index into cluster
			if (null == key) {	//start new cluster
				useCluster = new Cluster();
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
		Cluster miscCluster = null;
		for (Instance inst : desgn.getInstances()) {
			if (!mapToCluster.containsKey(inst)) {
				if (null == miscCluster) {
					miscCluster = new Cluster();
					clusters.add(miscCluster);
				}
				miscCluster.add(inst);
			}
		}
		clusters.trimToSize();
		/*DBG*/ System.out.println(clusters.toString());
		return clusters;
	}
};
