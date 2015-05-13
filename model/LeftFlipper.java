package model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import physics.*;

public class LeftFlipper implements IFlipper, IGizmo{

	private static final double L = 20.0;
	double halfL = L/2;
	double quarterL = L/4;
	
	private String opCode;
	
	int xpos;
	int ypos;
	int xpos2;
	int ypos2;
	
	int width;
	int height;
	
	Angle angle;
	
	LineSegment westLine;
	LineSegment eastLine;
	Circle northCircle;
	Circle southCircle;
	
	List<LineSegment> lines;
	List<Circle> circles;
	boolean forward = false;
	boolean backward = false;
	double angularVelocity;
	
	Color color;
	
	private List<Coordinate> occupiedSquares;
	private List<Integer> pressKeyCodesList;
	private List<Integer> releaseKeyCodesList;

	boolean active = false;
	
	GizmoModel model;
	
	public LeftFlipper(String op, int x, int y, GizmoModel m) {

		model = m;		
		
		// Top left corner of the Left Flipper
		xpos = (int)(x*L);
		ypos = (int)(y*L);
		
		// Bottom right of the flippers occupying area
		xpos2 = (int) ((x+2)*L);
		ypos2 = (int) ((y+2)*L);

		occupiedSquares = new ArrayList<Coordinate>();
		
		for (int i = x; i < x + 2; i++){
			for (int j = y; j < y + 2; j++){
				Coordinate c = new Coordinate(i, j);
				occupiedSquares.add(c);
			}
		}
		opCode = op;

		color = Color.YELLOW;
		
		width = (int)halfL;
		height = (int)(2*L);

		westLine = new LineSegment(xpos, ypos+quarterL, xpos, ypos+(L+(L/4)*3));
		angle = new Angle(westLine.angle().radians());
		eastLine = new LineSegment(xpos+halfL, ypos+quarterL, xpos+halfL, ypos+(L+(L/4)*3));
		northCircle = new Circle(xpos+quarterL, ypos+quarterL, quarterL);
		southCircle = new Circle(xpos+quarterL, ypos+(L+(L/4)*3), 5);
		
		lines = new ArrayList<LineSegment>();
		circles = new ArrayList<Circle>();
		
		lines.add(westLine);
		lines.add(eastLine);
		circles.add(northCircle);
		circles.add(southCircle);
		
		active = false;
		
		pressKeyCodesList = new ArrayList<Integer>();
		releaseKeyCodesList = new ArrayList<Integer>();
		
	}
	
	// Getter methods to return the physics objects that make the flipper
	public List<LineSegment> getLines(){
		return lines;
	}
	
	public List<Circle> getCircles(){
		return circles;
	}
	
	public void resetFlipper(double timeInterval){
		flipperAction(timeInterval);
	}
	
	public boolean isMovingForward(){
		return forward;
	}
	public boolean isMovingBackward(){
		return backward;
	}
	
	public void flipperAction(double timeInterval){
		// we must rotate the flipper based on the amount of time passed.
		
		// angularVelocity in radians (1080 degree per second);
		double angleOfMovement = angularVelocity * timeInterval;
		Angle temp = new Angle(angleOfMovement);

		
		if (angularVelocity < 0){
			if (westLine.angle().radians() + temp.radians() < 0){
				// Get how much the flipper will be over by
				Angle adjustAngle = new Angle(westLine.angle().radians()*-1);
				angle = angle.plus(adjustAngle);
				// Rotate only for the over-by amount
				westLine = Geometry.rotateAround(westLine, northCircle.getCenter(), adjustAngle);
				eastLine = Geometry.rotateAround(eastLine, northCircle.getCenter(), adjustAngle);
				southCircle = Geometry.rotateAround(southCircle, northCircle.getCenter(), adjustAngle);
				angularVelocity = 0;
			} else {
				angle = angle.plus(temp);
				// Otherwise rotate for the full amount
				westLine = Geometry.rotateAround(westLine, northCircle.getCenter(), temp);
				eastLine = Geometry.rotateAround(eastLine, northCircle.getCenter(), temp);
				southCircle = Geometry.rotateAround(southCircle, northCircle.getCenter(), temp);
			}
			lines.clear();
			circles.clear();
			lines.add(westLine);
			lines.add(eastLine);
			circles.add(northCircle);
			circles.add(southCircle);
		
		} else if (angularVelocity > 0){
			if (eastLine.angle().radians() <= Math.PI/2){
				if (eastLine.angle().radians() + temp.radians() > Math.PI/2){
					Angle adjustAngle = new Angle(Math.PI/2-eastLine.angle().radians());
					angle = angle.plus(adjustAngle);
					westLine = Geometry.rotateAround(westLine, northCircle.getCenter(), adjustAngle);
					westLine.setName("WEST LINE");
					eastLine = Geometry.rotateAround(eastLine, northCircle.getCenter(), adjustAngle);
					southCircle = Geometry.rotateAround(southCircle, northCircle.getCenter(), adjustAngle);
					angularVelocity = 0;
				} else {
					angle = angle.plus(temp);
					westLine = Geometry.rotateAround(westLine, northCircle.getCenter(), temp);
					eastLine = Geometry.rotateAround(eastLine, northCircle.getCenter(), temp);
					southCircle = Geometry.rotateAround(southCircle, northCircle.getCenter(), temp);
				}
			}
			lines.clear();
			circles.clear();
			lines.add(westLine);
			lines.add(eastLine);
			circles.add(northCircle);
			circles.add(southCircle);
		}
	}
	

	public String getOpCode(){
		return opCode;
	}
	

	public int getHeight(){
		return height;
	}
	
	public int getWidth(){
		return width;
	}
	
	public Circle getNorthCircle(){
		return northCircle;
	}
	
	public Circle getSouthCircle(){
		return southCircle;
	}
	@Override
	public int xpos(){
		return xpos;
	}
	@Override
	public int ypos(){
		return ypos;
	}
	@Override
	public int xpos2(){
		return xpos2;
	}
	@Override
	public int ypos2(){
		return ypos2;
	}

	public void action() {
		if (active == false){
			active = true;
			if(westLine.angle().radians() != 0){
				angularVelocity = -18.85;
			} else {
				angularVelocity = 0;
			}
		} else {
			active = false;
			stop();
		}
	}
	
	public void stop() {
		if (eastLine.angle().radians() != Math.PI/2){
			angularVelocity = 18.85;
		} else {
			angularVelocity = 0;
		}
	}

	@Override
	public Angle getAngle() {
		return angle;
	}
	
	@Override
	public double getMomentum() {
		return angularVelocity;
	}

	@Override
	public void move(float newX, float newY) {
		// Update the occupied Squares List in the model
		model.setOccupiedSquares();
		boolean temp = false;
		
		if (newX >= 0 && newX <= 18  && newY >= 0 && newY <= 18){
				for (int i = (int) newX; i < newX + 2; i++){
					for (int j = (int) newY; j < newY + 2; j++){
						for (Coordinate c : model.getOccupiedSquares()){
							if (c.x == i && c.y == j){
								temp = true;
							}
					}
				}
			}
		} else {
			temp = true;
		}
		
		if (temp == false){
			xpos = (int) (newX*L);
			ypos = (int) (newY*L);
			
			// Now change the objects occupied Squares
			occupiedSquares.clear();
			
			occupiedSquares.add(new Coordinate((int)(xpos/L), (int) (ypos/L)));
			occupiedSquares.add(new Coordinate((int)((xpos/L)+1), (int) (ypos/L)));
			occupiedSquares.add(new Coordinate((int)(xpos/L), (int) ((ypos/L)+1)));
			occupiedSquares.add(new Coordinate((int)((xpos/L))+1, (int) ((ypos/L)+1)));
			reposition();
			model.notifyUpdate();
		}
	}

	private void reposition() {
		westLine = new LineSegment(xpos, ypos+quarterL, xpos, ypos+(L+(L/4)*3));
		eastLine = new LineSegment(xpos+halfL, ypos+quarterL, xpos+halfL, ypos+(L+(L/4)*3));
		northCircle = new Circle(xpos+quarterL, ypos+quarterL, quarterL);
		southCircle = new Circle(xpos+quarterL, ypos+(L+(L/4)*3), 5);
		
		
		lines.clear();
		lines.add(westLine);
		lines.add(eastLine);
		circles.clear();
		circles.add(northCircle);
		circles.add(southCircle);
		
	}

	@Override
	public List<Coordinate> getOccupiedSquares() {
		return occupiedSquares;
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
		return null;
	}
	
	@Override
	public Color getColor() {
		return color;
	}

	@Override
	public void changeColor() {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void rotate() {
		// TODO Auto-generated method stub
		
	}
}