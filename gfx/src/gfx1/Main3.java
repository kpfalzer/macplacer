/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gfx1;

import javax.swing.SwingUtilities;
import javax.swing.JFrame;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.util.Random;

public class Main3 {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private static void createAndShowGUI() {
        System.out.println("Created GUI on EDT? "+
        SwingUtilities.isEventDispatchThread());
        JFrame f = new JFrame("Swing Paint Demo");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(new MyCanvas3());
        f.pack();
        f.setVisible(true);
    }
}


class MyCanvas3 extends Canvas {

    public MyCanvas3() {
        //setBorder(BorderFactory.createLineBorder(Color.black));
        this.setBackground(Color.WHITE);
        MyListener handler = new MyListener();
        this.addMouseMotionListener(handler);
        this.addMouseListener(handler);
    }

    static final int WIDTH = 1000, HEIGHT = 1000;

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(HEIGHT,WIDTH);
    }

    static class Block extends Rectangle {
        Block(int x, int y, Color clr) {
           super(x, y, WIDTH, HEIGHT);
           m_color = clr;
        }
        void draw(Graphics2D g) {
            g.draw(this);
            Color was = g.getColor();
            g.setColor(m_color);
            g.fill(this);
            g.setColor(was);
        }
        private Color m_color;
        final static int HEIGHT=8, WIDTH=8;
    }


    final static Random RND = new Random();

    static Color getRandomColor() {
       return new Color(RND.nextInt(256), RND.nextInt(256), RND.nextInt(256));
    }

    //final static int NX = 1000, NY = 2000;
    final static int NX = 200, NY = 200;

    final static Color COLORS[][] = new Color[NX][NY];

    static {
        for (int i = 0; i < NX; i++) {
            for (int j = 0; j < NY; j++) {
                COLORS[i][j] = getRandomColor();
            }
        }
    }

    AffineTransform m_scaleXform;

    @Override
    public void paint(Graphics g) {
        //debug(g);
        Graphics2D g2 = (Graphics2D)g;
        Dimension dm = this.getSize();
        int needX = NX * Block.WIDTH;
        int needY = NY * Block.HEIGHT;
        double scaleX = dm.getWidth() / needX;
        double scaleY = dm.getHeight() / needY;
        g2.scale(scaleX, scaleY);
        m_scaleXform = g2.getTransform();
        Block blk;
        for (int ix = 0; ix < NX; ix++) {
            for (int iy = 0; iy < NY; iy++) {
                blk = new Block(ix*Block.WIDTH,iy*Block.HEIGHT,COLORS[ix][iy]);//getRandomColor());
                blk.draw(g2);
            }
        }
        drawSelect(g2);
    }

    final static float dash1[] = { 10.0f };
    final static Stroke stSelectStroke = 
            new BasicStroke(4.0f,BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f);

    private void drawSelect(Graphics2D g) {
        if (null == m_selectRect) {
            return;
        }
        Shape select = stSelectStroke.createStrokedShape(m_selectRect);
        Color was = g.getColor();
        Stroke was2 = g.getStroke();
        g.setStroke(stSelectStroke);
        g.setColor(Color.BLACK);
        g.draw(m_selectRect);//g.draw(select);
        g.setColor(was);
        g.setStroke(was2);
    }

    Rectangle   m_selectRect = null;

    private static void debug(Object o) {
        System.out.println("DBG: "+o);
    }

    class MyListener extends MouseInputAdapter {

        private void setOrigin(Point pt) {
            m_selectRect = new Rectangle(scale(pt));
        }

        private Point scale(Point pt) {
            Point scaled = new Point();
            try {
                m_scaleXform.inverseTransform(pt, scaled);
            } catch (Exception ex) {}
            return scaled;
        }

        @Override
        public void mousePressed(MouseEvent e) {
            //debug(e);
            setOrigin(e.getPoint());
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            //debug(e);
            if (null == m_selectRect) {
                setOrigin(e.getPoint());
            }
            Point sloc = scale(e.getPoint());
            Point orig = m_selectRect.getLocation();
            int width = sloc.x - orig.x;
            int height = sloc.y - orig.y;
            m_selectRect.setSize(width, height);
            //debug(m_selectRect);
            repaint();  //TODO: optimize
        }
    }
}
