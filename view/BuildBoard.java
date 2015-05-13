package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.image.ImageObserver;
import java.text.AttributedCharacterIterator;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import physics.Angle;
import physics.Circle;
import physics.Vect;
import model.AGizmoModel;
import model.Ball;
import model.CircleBumper;
import model.IAbsorber;
import model.IFlipper;
import model.IGizmo;
import model.IGizmoModel;
import model.SquareBumper;
import model.TriangleBumper;

public class BuildBoard extends JPanel implements IBoard, Observer{

	private static final long serialVersionUID = 1L;
	private static final double L = 20.0;
	protected int width;
	protected int height;
	protected IGizmoModel gm;
	private Rubberband localRb;

	public BuildBoard(int w, int h, AGizmoModel modelGizmo){
		// Add the Board to the list of Observers
		modelGizmo.addObserver(this);
		width = w;
		height = h;
		gm = modelGizmo;
		this.setBorder(BorderFactory.createLineBorder(Color.black));

		localRb = new Rubberband();
	}

	// Fix onscreen size
	public Dimension getPreferredSize() {
		return new Dimension(width, height);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;

		//draw grid
		int w = getSize().width;
		int h = getSize().height;

		g.setColor(Color.BLACK);
		g.drawLine((int) (w*0.05),0,(int) (w*0.05),h);
		g.drawLine(0,(int) (h*0.05),w,(int) (h*0.05));
		g.drawLine((int) (w*0.1),0,(int) (w*0.1),h);
		g.drawLine(0,(int) (h*0.1),w,(int) (h*0.1));
		g.drawLine((int) (w*0.15),0,(int) (w*0.15),h);
		g.drawLine(0,(int) (h*0.15),w,(int) (h*0.15));
		g.drawLine((int) (w*0.2),0,(int) (w*0.2),h);
		g.drawLine(0,(int) (h*0.2),w,(int) (h*0.2));
		g.drawLine((int) (w*0.25),0,(int) (w*0.25),h);
		g.drawLine(0,(int) (h*0.25),w,(int) (h*0.25));
		g.drawLine((int) (w*0.3),0,(int) (w*0.3),h);
		g.drawLine(0,(int) (h*0.3),w,(int) (h*0.3));
		g.drawLine((int) (w*0.35),0,(int) (w*0.35),h);
		g.drawLine(0,(int) (h*0.35),w,(int) (h*0.35));
		g.drawLine((int) (w*0.4),0,(int) (w*0.4),h);
		g.drawLine(0,(int) (h*0.4),w,(int) (h*0.4));
		g.drawLine((int) (w*0.45),0,(int) (w*0.45),h);
		g.drawLine(0,(int) (h*0.45),w,(int) (h*0.45));
		g.drawLine((int) (w*0.5),0,(int) (w*0.5),h);
		g.drawLine(0,(int) (h*0.5),w,(int) (h*0.5));
		g.drawLine((int) (w*0.55),0,(int) (w*0.55),h);
		g.drawLine(0,(int) (h*0.55),w,(int) (h*0.55));
		g.drawLine((int) (w*0.6),0,(int) (w*0.6),h);
		g.drawLine(0,(int) (h*0.6),w,(int) (h*0.6));
		g.drawLine((int) (w*0.65),0,(int) (w*0.65),h);
		g.drawLine(0,(int) (h*0.65),w,(int) (h*0.65));
		g.drawLine((int) (w*0.7),0,(int) (w*0.7),h);
		g.drawLine(0,(int) (h*0.7),w,(int) (h*0.7));
		g.drawLine((int) (w*0.75),0,(int) (w*0.75),h);
		g.drawLine(0,(int) (h*0.75),w,(int) (h*0.75));
		g.drawLine((int) (w*0.8),0,(int) (w*0.8),h);
		g.drawLine(0,(int) (h*0.8),w,(int) (h*0.8));
		g.drawLine((int) (w*0.85),0,(int) (w*0.85),h);
		g.drawLine(0,(int) (h*0.85),w,(int) (h*0.85));
		g.drawLine((int) (w*0.9),0,(int) (w*0.9),h);
		g.drawLine(0,(int) (h*0.9),w,(int) (h*0.9));
		g.drawLine((int) (w*0.95),0,(int) (w*0.95),h);
		g.drawLine(0,(int) (h*0.95),w,(int) (h*0.95));

		for (IAbsorber ab : gm.getAbsorbers()) {
			g2.setColor(Color.PINK);
			g2.fillRect(ab.getX(), ab.getY(), ab.getWidth(), ab.getHeight());			
		}
		// Make the rubberband
		g2.setColor(Color.GREEN);
		g2.fillRect(localRb.x, localRb.y, localRb.width, localRb.height);
		
		for (IGizmo gizmo : gm.getGizmos()){
			if (gizmo instanceof SquareBumper){
				g2.setColor(gizmo.getColor());
				g2.fillRect(gizmo.xpos(), gizmo.ypos(), gizmo.getWidth(), gizmo.getWidth());
			} else if (gizmo instanceof CircleBumper){
				g2.setColor(gizmo.getColor());
				int width = (int) (gizmo.getWidth());
				g2.fillOval(gizmo.xpos(),gizmo.ypos(), width, width);
			} else if (gizmo instanceof TriangleBumper){
				g2.setColor(gizmo.getColor());
				Polygon p = new Polygon();
				for (Circle c : gizmo.getCircles()){
					p.addPoint((int)c.getCenter().x(), (int)c.getCenter().y());
				}
				g2.fillPolygon(p);
			}
		}
		for (IFlipper lf : gm.getFlippers()){

			/*
			 * For each flipper we only need to know the xpos, ypos and the angle.
			 * The draw code works out how to draw based on that.
			 * It will not 100% match the model.
			 * 
			 * we use polygon to make a rectangle and circles to make endpoints.
			 * 
			 */
			int xpos = lf.xpos();
			int ypos = lf.ypos();
			int x1, x2, x3, x4;
			int y1, y2, y3, y4;
			int height = lf.getHeight();
			int width = lf.getWidth();

			//********************** BEGIN CIRCLE ***************************
			Circle beginCir = lf.getNorthCircle();
			Vect center = beginCir.getCenter();
			double bcx = center.x();
			double bcy = center.y();
			Angle a = new Angle(lf.getAngle().radians() + Math.PI/2);

			x1 = (int) Math.round(bcx + (L/4) * a.cos());
			y1 = (int) Math.round(bcy + (L/4) * a.sin());
			x2 = (int) Math.round(bcx + (L/4) * Math.cos(a.radians() - Math.PI) );
			y2 = (int) Math.round(bcy + (L/4) * Math.sin(a.radians() - Math.PI));
			g2.setColor(Color.YELLOW);
			g2.fillOval((int)(bcx-(L/4)), (int)(bcy-(L/4)), (int)L/2, (int)L/2);

			// *******************************************
			// The end circle position will be worked out the same
			// way..
			Circle endCir = lf.getSouthCircle();
			center = endCir.getCenter();
			double ecx = center.x();
			double ecy = center.y();
			// bc is a fixed circle. let's imaging its center is the center
			// of a circle the flipper makes up
			x3 = (int)(x2 + ((2*L/4)*3) * Math.cos(a.radians() - Math.PI/2));
			y3 = (int)(y2 + ((2*L/4)*3) * Math.sin(a.radians() - Math.PI/2));

			x4 = (int) (x1 + ((2*L/4)*3) * Math.cos(a.radians() - Math.PI/2));
			y4 = (int)(y1 + ((2*L/4)*3) * Math.sin(a.radians() - Math.PI/2));

			g2.fillOval((int) (ecx-(L/4)), (int)(ecy-(L/4)), (int)L/2, (int)L/2);

			Polygon p = new Polygon();
			p.addPoint(x1, y1);
			p.addPoint(x2, y2);
			p.addPoint(x3, y3);
			p.addPoint(x4, y4);

			g2.fillPolygon(p);
		}
		if (gm.getBall() != null){
			Ball ball = gm.getBall();
			g2.setColor(ball.getColour());
			int x = (int) (ball.getExactX() - ball.getRadius());
			int y = (int) (ball.getExactY() - ball.getRadius());
			int width = (int) (2 * ball.getRadius());
			g2.fillOval(x, y, width, width);
		}
	}


	@Override
	public void update(Observable arg0, Object arg1) {
		repaint();
	}

	@Override
	public void rubberband(int x1, int y1, int x2, int y2) {
		localRb.x = (int) (x1 * L);
		localRb.y = (int) (y1 * L);
		localRb.width = (int) ((x2 - x1)*L);
		localRb.height = (int)((y2-y1)*L);
		repaint();
	}

	private class Rubberband{
		int x;
		int y;
		int width;
		int height;
	}

	@Override
	public void eraseRubberband() {
		localRb.x = 0;
		localRb.y = 0;
		localRb.width = 0;
		localRb.height = 0;
		repaint();
	}
}