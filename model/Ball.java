package model;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import physics.Circle;
import physics.LineSegment;
import physics.Vect;

public class Ball implements IGizmo {

	private Vect velocity;
	private double radius;
	private double xpos;
	private double ypos;
	private int xpos2;
	private int ypos2;
	
	private Color colour;
	private String opcode;
	private boolean stopped;
	
	private List<Coordinate> occupiedSquares;
	private List<Integer> pressKeyCodesList;
	private List<Integer> releaseKeyCodesList;
	private static final double L = 20.0;
	
	GizmoModel model;

	// x, y coordinates and x,y velocity
	public Ball(String oc, double x, double y, double xv, double yv, GizmoModel m) {
		
		model = m;
		
		xpos = x*L; // Centre coordinates
		ypos = y*L;
		//Bottom right of the box the ball is in
		xpos2 = (int) (Math.floor(x) + 1); 
		ypos2 = (int) (Math.floor(y) + 1);
		
		occupiedSquares = new ArrayList<Coordinate>();
		
		Coordinate c = new Coordinate((int)x, (int)y);
		occupiedSquares.add(c);
		
		colour = Color.BLUE;
		velocity = new Vect(xv, yv);
		radius = 5;
		stopped = false;
		opcode = oc;
		
		pressKeyCodesList = new ArrayList<Integer>();
		releaseKeyCodesList = new ArrayList<Integer>();
	}

	public Vect getVelo() {
		return velocity;
	}

	public void setVelo(Vect v) {
		velocity = v;
	}

	public double getRadius() {
 		return radius;
	}

	public Circle getCircle() {
		return new Circle(xpos, ypos, radius);

	}

	// Ball specific methods that deal with double precision.
	public double getExactX() {
		return xpos;
	}

	public double getExactY() {
		return ypos;
	}

	public void setExactX(double x) {
		xpos = x;
	}

	public void setExactY(double y) {
		ypos = y;
	}

	public void stop() {
		stopped = true;
	}

	public void start() {
		stopped = false;
	}

	public boolean stopped() {
		return stopped;
	}

	public Color getColour() {
		return colour;
	}

	@Override
	public String getOpCode() {
		return opcode;
	}

	@Override
	public int xpos() {
		return (int) (xpos - L/4);
	}

	@Override
	public int ypos() {
		// returns the top left corner.
		// Used only for buildMode placement
		return (int)(ypos - L/4);
	}

	@Override
	public void rotate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<LineSegment> getLines() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Circle> getCircles() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Color getColor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void changeColor() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void move(float newX, float newY) {
		setExactX(newX);
		setExactY(newY);
	}

	@Override
	public int xpos2() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int ypos2() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Coordinate> getOccupiedSquares() {
		return occupiedSquares;
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void setPressConnectKey(int key) {
		pressKeyCodesList.add(key);
	}
	
	@Override
	public void setReleaseConnectKey(int key){
		releaseKeyCodesList.add(key);
	}

	@Override
	public List<Integer> pressKeyCodes() {
		return pressKeyCodesList;
	}

	@Override
	public List<Integer> releaseKeyCodes() {
		return releaseKeyCodesList;
	}

	@Override
	public String getOrientation() {
		// TODO Auto-generated method stub
		return null;
	}

}
