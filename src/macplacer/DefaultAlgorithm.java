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
        m_placedAndUnplaced = m_packing.getPlacedAndUnplaced();
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
		Iterator<Corner> contour = super.m_design.getFplan().getBoundingBox().getContourIterator().iterator();
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
		addPacking(packn, cornerOffset);
	}

    /**
     * Place macros wrt corner. (see dac07-macro.pdf paper.)
     *
     * If node nj is the right child of ni , the block bj is
     *  • the lowest adjacent block on the right with xj = xi + wi
     *      for BL-packing,
     *  • the highest adjacent block on the right with xj = xi + wi
     *      for TL-packing,
     *  • the highest adjacent block on the left with xj = xi − wj for
     *      TR-packing, and
     *  • the lowest adjacent block on the left with xj = xi − wj for
     *      BR-packing.
     *
     * @param pack (relative) packed macros.
     * @param cornerOffset corner offset.
     */
    private void addPacking(Packing pack, Corner cornerOffset) {
        PackData data = new PackData(cornerOffset);
        pack.levelOrder(new BinaryTreeNodeVisitorWithData<Placed,PackData>(data) {
            public void visit(BinaryTree.Node<Placed> node) {
                m_userData.pack(node);
            }
        });
        super.m_packing.add(pack);
    }
    /**
     * Surrogate class for addPacking.
     */
    private static class PackData {
        PackData(Corner cornerOffset) {
            m_cornerOffset = cornerOffset;
        }
        void pack(BinaryTree.Node<Placed> node) {
            Placed placed = node.getData();
			double x = m_cornerOffset.getX();
            double y = m_cornerOffset.getY();
            if (0 == m_nodeCnt) {   //is root
                //TODO: consider concave/convex
                switch(m_cornerOffset.getCorner()) {
                    case eLowerLeft:
                        //do nothing: (0,0)
                        break;
                    case eLowerRight:
                        x -= placed.getWidth();
                        break;
                    case eUpperRight:
                        x -= placed.getWidth();
                        y -= placed.getHeight();
                        break;
                    case eUpperLeft:
                        y -= placed.getHeight();
                        break;
                    default:
                        invariant(false);
                }
            } else {	//non-root
                x = node.getParentData().getX();
				double parentWidth = node.getParentData().getWidth();
				y = node.getParentData().getY();
				double parentHeight = node.getParentData().getHeight();
				if (node.isLeftChild()) {
					switch(m_cornerOffset.getCorner()) {
						case eLowerLeft: //the first block above bi with xj = xi
							y += parentHeight;
							break;
						case eLowerRight: //the first block above bi with xj = xi + wi − wj
							x += parentWidth - placed.getWidth();
							y += parentHeight;
							break;
						case eUpperRight: //the first block below bi with xj = xi + wi − wj
							x += parentWidth - placed.getWidth();
							y -= placed.getHeight();
							break;
						case eUpperLeft: //the first block below bi with xj = xi
							y -= placed.getHeight();
							break;
						default:
							invariant(false);
					}
				} else {	//is right
					switch(m_cornerOffset.getCorner()) {
						case eLowerLeft: //the lowest adjacent block on the right with xj = xi + wi
							x += parentWidth;
							break;
						case eLowerRight: //the lowest adjacent block on the left with xj = xi − wj
							x -= placed.getWidth();
							break;
						case eUpperRight: //the highest adjacent block on the left with xj = xi − wj
							x -= placed.getWidth();
							y = m_cornerOffset.getY() - placed.getHeight();
							break;
						case eUpperLeft: //the highest adjacent block on the right with xj = xi + wi
							x += parentWidth;
							y = m_cornerOffset.getY() - placed.getHeight();
							break;
						default:
							invariant(false);
					}
				}
            }
            placed.setLowerLeft(x, y);
            m_nodeCnt++;
        }
        private final Corner    m_cornerOffset;
        private int             m_nodeCnt = 0;
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
