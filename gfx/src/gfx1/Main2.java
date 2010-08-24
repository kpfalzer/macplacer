/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gfx1;

import javax.swing.SwingUtilities;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Canvas;
import java.awt.Shape;
import java.awt.Rectangle;
import java.awt.Dimension;
import java.util.Random;

public class Main2 {

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
        f.add(new MyCanvas());
        f.pack();
        f.setVisible(true);
    }
}


class MyCanvas extends Canvas {

    public MyCanvas() {
        //setBorder(BorderFactory.createLineBorder(Color.black));
        this.setBackground(Color.WHITE);
        Random rnd = new Random();
        Color c;
        final int dx = Block.WIDTH, dy = Block.HEIGHT;
        for (int i=0; i < NROWS; i++) {
            for (int j=0; j < NCOLS; j++) {
                c = new Color(rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
                m_blocks[i][j] = new Block(i*dy,j*dx,c);
            }
        }
    }

    final static int NROWS = 250, NCOLS = 250;
    Block m_blocks[][] = new Block[NROWS][NCOLS];

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(Block.HEIGHT*NROWS,Block.WIDTH*NCOLS);
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

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        Dimension dm = this.getSize();
        final int nCols = NROWS; //dm.width / Block.WIDTH;
        final int nRows = NCOLS; //dm.height / Block.HEIGHT;
        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nCols; j++) {
                m_blocks[i][j].draw(g2);
            }
        }
    }
}
