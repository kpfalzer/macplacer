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

package macplacer.test;
import	macplacer.geom.Canvas;
import	macplacer.geom.Dimension;
import	macplacer.Floorplan;
import	macplacer.Design;
import  macplacer.Algorithm;
import  macplacer.DefaultAlgorithm;
import	macplacer.Instance;
import	macplacer.Placed;
import static macplacer.Util.error;
import  javax.swing.SwingUtilities;
import	javax.swing.JFrame;
import	javax.swing.JButton;
import	java.awt.BorderLayout;
import	java.awt.event.ActionListener;
import	java.awt.event.ActionEvent;

/**
 *
 * @author karl
 */
public class Canvas1 {
    public static void main(String[] args) {
        final Canvas1 tc = new Canvas1();
		try {
			tc.m_design = new Design(args[0]);
		} catch (Exception ex) {
			error(ex);
		}
		tc.m_algo = new DefaultAlgorithm(tc.m_design);
		tc.m_algo.getInitialPackingTree();
        tc.m_algo.updateFloorplan();
		tc.m_fplan = tc.m_design.getFplan();
		SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                tc.createAndShowGUI();
            }
        });
    }

	private Floorplan	m_fplan;
	private Design		m_design;
	private Algorithm	m_algo;
	private Canvas		m_cnvs;
	private JButton		m_butt;

    private void createAndShowGUI() {
        JFrame f = new JFrame("Canvas1 test");
		f.setLayout(new BorderLayout());	//layout gaps
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		m_cnvs = new Canvas(m_fplan);
		f.add(m_cnvs, BorderLayout.CENTER);
		m_butt = new JButton("Iterate");
		m_butt.addActionListener(new ButtonHandler());
		f.add(m_butt, BorderLayout.SOUTH);
        f.pack();
        f.setVisible(true);
    }

	private void iterate() {
		m_algo.iterate();
		m_algo.updateFloorplan();
		m_fplan = m_design.getFplan();
		m_cnvs.update(m_fplan);
		m_cnvs.repaint();
	}

	private class ButtonHandler implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			//DBG System.out.println("DBG1: "+e);
			iterate();
		}

	}
}
