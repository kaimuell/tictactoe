package foo;

import java.awt.*;
import java.awt.geom.AffineTransform;

import javax.swing.JPanel;

public class TicTacToePanel extends JPanel implements IView{

    private IModel model;

    public TicTacToePanel(IModel model) {
        this.model = model;
        Color c = new Color(180, 140, 55);
        setBackground(c);
        setPreferredSize(new Dimension(450, 450));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        configureGraphicsForUsageOfUserCoordinateSystem(g2d);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        drawGrid(g2d);
        drawFieldStates(g2d);
    }

    private void drawFieldStates(Graphics2D g2d) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                EFieldState efs = model.getFeldZustand(j, i);
                switch (efs) {
                case CROSS:
                    g2d.drawLine( i*100 +10,  j*100 +10, i*100 + 90, j*100 + 90);
                    g2d.drawLine( i*100 +90,  j*100 + 10, i*100 +10, j*100 + 90);
                    break;
                case CIRCLE:
                    g2d.drawOval(10 + (i*100), 10 + (j*100), 80, 80);
                    break;
                default:
                    break;
                }
            }
        }
    }

    private void drawGrid(Graphics2D g2d) {
        g2d.drawRect(0, 0, 300, 300);
        g2d.drawLine(0, 100, 300, 100);
        g2d.drawLine(0, 200, 300, 200);
        g2d.drawLine(100, 0, 100, 300);
        g2d.drawLine(200, 0, 200, 300);
    }        

    private void configureGraphicsForUsageOfUserCoordinateSystem(Graphics2D g2d) {
        double sx = this.getWidth() / 300.0;
        double sy = this.getHeight() / 300.0;
        double scale = Math.min(sx, sy);
        AffineTransform aft = AffineTransform.getScaleInstance(scale, scale);
        g2d.setTransform(aft);
    }

    @Override
    public void refresh() {
        repaint();
        
    }

}
