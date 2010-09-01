/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package macplacer.test;
import	macplacer.geom.Canvas;
import	macplacer.geom.Dimension;
import	macplacer.Floorplan;
import	macplacer.Design;
import	macplacer.Instance;
import	macplacer.Placed;
import static macplacer.Util.error;
import  javax.swing.SwingUtilities;
import	javax.swing.JFrame;
import	java.awt.BorderLayout;

/**
 *
 * @author karl
 */
public class Canvas1 {
    public static void main(String[] args) {
        final Canvas1 cnvs = new Canvas1();
		try {
			Design dsgn = new Design(args[0]);
			Instance inst = dsgn.getInstances().get(0);
			Placed placed = new Placed(inst);
			cnvs.m_fplan.addPlaced(placed);
		} catch (Exception ex) {
			error(ex);
		}
		SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                cnvs.createAndShowGUI();
            }
        });
    }

	private Floorplan	m_fplan = new Floorplan(new Dimension(600,600));

    private void createAndShowGUI() {
        JFrame f = new JFrame("Canvas1 test");
		f.setLayout(new BorderLayout());	//layout gaps
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(new Canvas(m_fplan), BorderLayout.CENTER);
        f.pack();
        f.setVisible(true);
    }

}
