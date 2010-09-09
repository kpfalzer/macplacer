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
import	macplacer.geom.Corner;
import	java.util.Iterator;
import	java.util.ArrayList;
import	java.util.Comparator;
import	java.util.Collection;
import	java.util.HashMap;
import	java.util.Arrays;

/**
 * Default algorithm.
 * @author karl
 */
public class DefaultAlgorithm extends Algorithm {
	public DefaultAlgorithm(Design design) {
		super(design);
	}
	/**
	 * PackingTree macros which share the longest instance name prefix.
	 * @param desgn design containing macros.
	 * @param maxPacks maximum number of packing trees.
	 */
	public void getInitialPackingTree() {
		Graph g = Util.measureCommonPrefix(m_design.getInstances(), Design.stHierSep);
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
		for (Instance inst : m_design.getInstances()) {
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
		invariant(m_maxPacks >= clusters.size());
		initialize(clusters);
	}

	public void iterate() {
		//TODO
		m_iteration++;
	}

	/**
	 * Create initial packing tree.
	 * @param clusters cluster per corner.
	 */
	private void initialize(Iterable<Cluster> clusters) {
		/*
		 * Pack starting from lower-left corner, proceeding in a
		 * counter-clockwise order.
		 */
		m_packing = new PackingTree();
		Iterator<Corner> contour = super.m_design.getFplan().getContourIterator().iterator();
		for (Cluster cluster : clusters) {
			assert(contour.hasNext());
			Corner corner = contour.next();
			initialize(cluster,corner);
		}
	}

	/**
	 * Create initial packing for corner.
	 * @param cluster to pack (together).
	 * @param cornerOffset offset from LL to packing corner.
	 * @return initial packing.
	 */
	private void initialize(Cluster cluster, Corner cornerOffset) {
		Instance sorted[] = new Instance[cluster.size()];
		//Sort in ascending order.
		cluster.toArray(sorted);
		Arrays.sort(sorted, new Comparator<Instance>() {
			public int compare(Instance o1, Instance o2) {
				double area[] = {o1.getDimension().getArea(),o2.getDimension().getArea()};
				return (area[0] < area[1]) ? -1 : ((area[0] > area[1]) ? 1 : 0);
			}
		});
		/*
		 * Add nodes in level-order from array to tree.
		 */
		Packing packn = new Packing(sorted);
		super.addPacking(packn, cornerOffset);
	}

	/**
	 * Group of macro instances to be clustered.
	 * @author karl
	 */
	private class Cluster extends ArrayList<Instance> {
		@Override
		public String toString() {
			StringBuilder buf = new StringBuilder(getClass().getName()+":\n");
			final String pfx = "    ";
			for (Instance inst : this) {
				buf.append(pfx).append(inst.getName()).append('\n');
			}
			return buf.toString();
		}
	};
};
