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
import  java.util.Arrays;
import	java.util.List;
import	java.util.StringTokenizer;
import	java.util.ArrayList;
import	java.util.Collection;

/**
 *
 * @author karl
 */
public class Util {
    public static void error(Exception ex) {
        ex.printStackTrace();
    }

	public static boolean toBool(String s) {
		return ((null != s) && !s.equals("0"));
	}

	/**
	 * Convert string to integer.
	 * @param sval string value of integer or null.
	 * @param dflt return as value if sval is null.
	 * @return sval as int or dflt.
	 */
	 public static int asInt(String sval, int dflt) {
		return (null == sval) ? dflt : Integer.parseInt(sval);
	 }

	/**
	 * Return Graph of common name prefix count.
	 */
	public static Graph<Instance,Integer> measureCommonPrefix(List<Instance> eles, String sep) {
		Graph<Instance,Integer> graph = new Graph<Instance,Integer>();
		//
		//Create delimited names.
		final int n = eles.size();
		ArrayList<List<String>> splitNames = new ArrayList<List<String>>(n);
		for (Instance inst : eles) {
			splitNames.add(split(inst.getName(),sep));
		}
		//
		//Create graph w/ edge weight as the common prefix length.
		int i = 0, j;
		for (List<String> n1 : splitNames.subList(0, n-1)) {  //[0..end-1]
			j = i + 1;
			for (List<String> n2 : splitNames.subList(j, n)) {//[i+1..end]
				int w = measureCommonPrefix(n1, n2);
				if (0 < w) {
					graph.addEdge(w, eles.get(i), eles.get(j));
				}
				j++;
			}
			i++;
		}
		return graph;
	}

	public static int measureCommonPrefix(List<String> s1, List<String> s2) {
		int cnt = 0;
		final int n = (s1.size() > s2.size()) ? s2.size() : s1.size();
		for (; cnt < n; cnt++) {
			if (!s1.get(cnt).equals(s2.get(cnt))) {
				break;//for
			}
		}
		return cnt;
	}

	/**
	 * Return array of elements from name which are separated by sep.
	 * @param name name with sep delimiter.
	 * @param sep delimiter.
	 */
	public static List<String> split(String name, String sep) {
		ArrayList<String> parts = new ArrayList<String>(10);
		StringTokenizer toks = new StringTokenizer(name, sep);
		for (String s = null; toks.hasMoreTokens(); ) {
			s = toks.nextToken();
			if (0 < s.length()) {
				parts.add(s);
			}
		}
		return parts;
	}

	public static <T> List<T> toList(T ar[]) {
		return Arrays.asList(ar);
	}

}
