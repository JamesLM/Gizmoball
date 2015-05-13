package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import physics.Angle;
import physics.Circle;
import physics.LineSegment;
import physics.Vect;
import model.*;

public class RunBoard extends JPanel implements IBoard, Observer {
	
	private static final long serialVersionUID = 1L;
	private static final double L = 20.0;
	protected int width;
	protected int height;
	protected IGizmoModel gm;
	
	public RunBoard(int w, int h, AGizmoModel modelGizmo){
		
		// Add the Board to the list of Observers
		modelGizmo.addObserver(this);
		width = w;
		height = h;
		gm = modelGizmo;
		this.setBorder(BorderFactory.createLineBorder(Color.black));
	}
	
	// Fix onscreen size
	public Dimension getPreferredSize() {
		return new Dimension(width, height);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;
		
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
		
		for (IAbsorber ab : gm.getAbsorbers()) {
			g2.fillRect(ab.getX(), ab.getY(), ab.getWidth(), ab.getHeight());			
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
			g2.setColor(Color.YELLOW);
			g2.fillOval((int) (ecx-(L/4)), (int)(ecy-(L/4)), (int)L/2, (int)L/2);
			
			Polygon p = new Polygon();
			p.addPoint(x1, y1);
			p.addPoint(x2, y2);
			p.addPoint(x3, y3);
			p.addPoint(x4, y4);
			g2.setColor(Color.YELLOW);
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
	public void update(Observable o, Object arg) {
		repaint();
	}

	@Override
	public void eraseRubberband() {
	}

	@Override
	public void rubberband(int x1, int y1, int x2, int y2) {
	}

}
