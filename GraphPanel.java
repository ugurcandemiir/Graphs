package lab11;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;

import javax.swing.JPanel;

public class GraphPanel extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    private int padding = 25;
    private int labelPadding = 25;
    private Color lineColor = new Color(44, 102, 230, 180);
    // private Color pointColor = new Color(100, 100, 100, 180);
    private Color gridColor = new Color(200, 200, 200, 200);
    private static final Stroke GRAPH_STROKE = new BasicStroke(2f);
    private int pointWidth = 4;
    private int numberYDivisions = 10;
    private double points[][];

    public GraphPanel(double[][] points) {
    	this.points=points;  
    	setPreferredSize(new Dimension(800, 600));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        double xmin=points[0][0];
        double xmax=points[0][0];
        double ymin=points[0][1];
        double ymax=points[0][1];
        
        for(int i=0;i<points.length;i++) {
        	if (points[i][0]<xmin) xmin=points[i][0];
        	if (points[i][0]>xmax) xmax=points[i][0];
        	if (points[i][1]<ymin) ymin=points[i][1];
        	if (points[i][1]>ymax) ymax=points[i][1];
        }
        double xScale = ((double) getWidth() - (2 * padding) - labelPadding) / (xmax-xmin);
        double yScale = ((double) getHeight() - 2 * padding - labelPadding) / (ymax-ymin);
        Point[] graphPoints = new Point[points.length];
        
        for (int i = 0; i < points.length; i++) {
            int x1 = (int) ((points[i][0]-xmin) * xScale + padding + labelPadding);
            int y1 = (int) ((ymax - points[i][1]) * yScale + padding);
            graphPoints[i]=new Point(x1, y1);
        }

        // draw white background
        g2.setColor(Color.WHITE);
        g2.fillRect(padding + labelPadding, padding, getWidth() - (2 * padding) - labelPadding, getHeight() - 2 * padding - labelPadding);
        g2.setColor(Color.BLACK);

        // create hatch marks and grid lines for y axis.
        double yDivSize=(ymax-ymin)/numberYDivisions;
        for (int i = 0; i < numberYDivisions + 1; i++) {
            int x0 = padding + labelPadding;
            int x1 = pointWidth + padding + labelPadding;
            int y0 = getHeight() - ((i * (getHeight() - padding * 2 - labelPadding)) / numberYDivisions + padding + labelPadding);
            int y1 = y0;
            g2.setColor(gridColor);
            g2.drawLine(padding + labelPadding + 1 + pointWidth, y0, getWidth() - padding, y1);
            g2.setColor(Color.BLACK);
            // String yLabel = ((int) ((ymin + (ymax - ymin) * ((i * 1.0) / numberYDivisions)) * 100)) / 100.0 + "";
            String yLabel=String.format("%.2f",ymin+i*yDivSize);
            FontMetrics metrics = g2.getFontMetrics();
            int labelWidth = metrics.stringWidth(yLabel);
            g2.drawString(yLabel, x0 - labelWidth - 5, y0 + (metrics.getHeight() / 2) - 3);
            g2.drawLine(x0, y0, x1, y1);
        }

        // and for x axis
        double nextTick=xmin;
        for (int i = 0; i < points.length; i++) {
        	if (points[i][0]>=nextTick) {
        		nextTick+=1.0;
        		// int x0 = i * (getWidth() - padding * 2 - labelPadding) / (nPoints - 1) + padding + labelPadding;
        		int x0=(int)((points[i][0]-xmin) * xScale + padding + labelPadding);
        		int x1 = x0;
        		int y0 = getHeight() - padding - labelPadding;
        		int y1 = y0 - pointWidth;
        		g2.setColor(gridColor);
        		g2.drawLine(x0, getHeight() - padding - labelPadding - 1 - pointWidth, x1, padding);
        		g2.setColor(Color.BLACK);
        		// String xLabel = i + "";
        		String xLabel = "" + (int)(points[i][0]);
        		FontMetrics metrics = g2.getFontMetrics();
        		int labelWidth = metrics.stringWidth(xLabel);
        		g2.drawString(xLabel, x0 - labelWidth / 2, y0 + metrics.getHeight() + 3);
        		g2.drawLine(x0, y0, x1, y1);
        	}
        }

        // create x and y axes 
        g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, padding + labelPadding, padding);
        g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, getWidth() - padding, getHeight() - padding - labelPadding);

        // Stroke oldStroke = g2.getStroke();
        g2.setColor(lineColor);
        g2.setStroke(GRAPH_STROKE);
        for (int i = 0; i < (points.length-1); i++) {
            int x1 = graphPoints[i].x;
            int y1 = graphPoints[i].y;
            int x2 = graphPoints[i + 1].x;
            int y2 = graphPoints[i + 1].y;
            g2.drawLine(x1, y1, x2, y2);
        }

        /* g2.setStroke(oldStroke);
        g2.setColor(pointColor);
        for (int i = 0; i < points.length; i++) {
            int x1 = graphPoints[i].x - pointWidth / 2;
            int y1 = graphPoints[i].y - pointWidth / 2;
            int ovalW = pointWidth;
            int ovalH = pointWidth;
            g2.fillOval(x1, y1, ovalW, ovalH);
        } */
    }

    public void setPoints(double[][] points) {
        this.points=points;
        invalidate();
        this.repaint();
    }
}
