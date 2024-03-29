package common;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.util.Iterator;

import javax.swing.JPanel;

import interfaces.*;

public class TicTacToePanel extends JPanel implements IView, IInputDevice {

    private IModel model;
    private IMoveListener iml;
    private double scale;
    private static Color BOARDCOLOR = new Color(180, 140, 55);
    private static Color WINNINGFIELDCOLOR = new Color(180, 130, 70);

    public TicTacToePanel(IModel model) {
        this.model = model;
        Color c = BOARDCOLOR;
        setBackground(c);
        setPreferredSize(new Dimension(450, 450));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = (int) (e.getX() / scale / 100);
                int y = (int) (e.getY() / scale / 100);
                if (x >= 0 && x <= 2 && y >= 0 && y <= 2 && iml != null) {
                    iml.moveOccured(y, x);
                }
            }
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        configureGraphicsForUsageOfUserCoordinateSystem(g2d);
        drawWinningFields(g2d);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        drawGrid(g2d);
        drawFieldStates(g2d);
    }

    private void drawWinningFields(Graphics2D g2d) {
        if(!model.getWinningFields().isEmpty()) {
            g2d.setColor(WINNINGFIELDCOLOR);
            Iterator<Point> it = model.getWinningFields().iterator();
            while (it.hasNext()) {
                Point point = (Point) it.next();
                g2d.fillRect(point.y *100, point.x*100, 100 , 100);
            }
            g2d.setColor(Color.BLACK);
        }
    }

    private void drawFieldStates(Graphics2D g2d) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                EFieldState efs = model.getFieldState(j, i);
                switch (efs) {
                case CROSS:
                    g2d.drawLine(i * 100 + 10, j * 100 + 10, i * 100 + 90, j * 100 + 90);
                    g2d.drawLine(i * 100 + 90, j * 100 + 10, i * 100 + 10, j * 100 + 90);
                    break;
                case CIRCLE:
                    g2d.drawOval(10 + (i * 100), 10 + (j * 100), 80, 80);
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
        scale = Math.min(sx, sy);
        AffineTransform aft = AffineTransform.getScaleInstance(scale, scale);
        g2d.setTransform(aft);
    }

    @Override
    public void refresh() {
        repaint();

    }

    @Override
    public void setMoveListener(IMoveListener iml) {
        this.iml = iml;

    }

}
