/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package macplacer.test;
import	macplacer.geom.Canvas;
import	macplacer.geom.Dimension;
import	macplacer.geom.Point;
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
			cnvs.m_design = new Design(args[0]);
		} catch (Exception ex) {
			error(ex);
		}
		Instance inst = cnvs.m_design.getInstances().get(0);
		Placed placed = new Placed(inst);//,new Point (800,600));
		Dimension dmsn = cnvs.m_design.getFplanDimension();
		cnvs.m_fplan = new Floorplan(dmsn);
		cnvs.m_fplan.addPlaced(placed);
		SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                cnvs.createAndShowGUI();
            }
        });
    }

	private Floorplan	m_fplan;
	private Design		m_design;

    private void createAndShowGUI() {
        JFrame f = new JFrame("Canvas1 test");
		f.setLayout(new BorderLayout());	//layout gaps
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(new Canvas(m_fplan), BorderLayout.CENTER);
        f.pack();
        f.setVisible(true);
    }

}
