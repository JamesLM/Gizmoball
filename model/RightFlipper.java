package model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import physics.*;

public class RightFlipper implements IFlipper, IGizmo{

	// *****************
	// L = 20
	// *****************
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

	private List<Coordinate> occupiedSquares;
	private List<Integer> pressKeyCodesList;
	private List<Integer> releaseKeyCodesList;

	Angle angle;

	// Flipper can be made of 2 circles and 2 lines.
	LineSegment westLine;
	LineSegment eastLine;
	Circle northCircle;
	Circle southCircle;
	
	Circle pointCircle1;
	Circle pointCircle2;
	Circle pointCircle3;
	Circle pointCircle4;

	List<LineSegment> lines;
	List<Circle> circles;
	List<Circle> pointCircles;
	
	double angularVelocity = 0;

	boolean active = false;

	GizmoModel model;
	private Color color;

	public RightFlipper(String op, int x, int y, GizmoModel m) {

		model = m;

		xpos = (int)((x)*L);
		ypos = (int)(y*L);
		
		xpos2 = (int) ((x-2)*L);
		ypos2 = (int) ((y+2)*L);
		
		// temporary variable to handle the right flippers right-alignment
		int xposShifted = (int) (xpos+40);

		occupiedSquares = new ArrayList<Coordinate>();
		for (int i = x; i < x +2; i++){
			for (int j = y; j < y + 2; j++){
				Coordinate c = new Coordinate(i, j);
				occupiedSquares.add(c);
			}
		}
		
		color = Color.GREEN;
		opCode = op;
		width = (int)halfL;
		height = (int)(2*L);

		westLine = new LineSegment(xposShifted-halfL, ypos+quarterL, xposShifted-halfL, ypos+(L+(L/4)*3));
		angle = new Angle(westLine.angle().radians());
		eastLine = new LineSegment(xposShifted, ypos+quarterL, xposShifted, ypos+(L+(L/4)*3));
		
		northCircle = new Circle(xposShifted-quarterL, ypos+quarterL, quarterL);
		southCircle = new Circle(xposShifted-quarterL, ypos+(L+(L/4)*3), 5);
		pointCircle1 = new Circle(xposShifted-quarterL, ypos+quarterL, 0);
		pointCircle2 = new Circle(xposShifted-halfL, ypos+quarterL, 0);
		pointCircle3 = new Circle(xposShifted-quarterL, ypos+(L+(L/4)*3), 0);
		pointCircle4 = new Circle(xposShifted-halfL, ypos+(L+(L/4)*3), 0);

		lines = new ArrayList<LineSegment>();
		circles = new ArrayList<Circle>();
		pointCircles = new ArrayList<Circle>();
		
		lines.add(westLine);
		lines.add(eastLine);
		circles.add(northCircle);
		circles.add(southCircle);
		circles.add(pointCircle1);
		circles.add(pointCircle2);
		circles.add(pointCircle3);
		circles.add(pointCircle4);

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
	
	@Override
	public void flipperAction(double timeInterval){

		double angleOfMovement = angularVelocity * timeInterval;
		Angle temp = new Angle(angleOfMovement);

		if (angularVelocity > 0) {
			if (westLine.angle().radians() + temp.radians() > 3.14){
				// Get how much the flipper will be over by
				Angle adjustAngle = new Angle((3.14) - westLine.angle().radians());
				angle = angle.plus(adjustAngle);
				// Rotate only for the over-by amount
				westLine = Geometry.rotateAround(westLine, northCircle.getCenter(), adjustAngle);
				eastLine = Geometry.rotateAround(eastLine, northCircle.getCenter(), adjustAngle);
				southCircle = Geometry.rotateAround(southCircle, northCircle.getCenter(), adjustAngle);
				pointCircle1 = Geometry.rotateAround(pointCircle1, northCircle.getCenter(), adjustAngle);
				pointCircle2 = Geometry.rotateAround(pointCircle2, northCircle.getCenter(), adjustAngle);
				pointCircle3 = Geometry.rotateAround(pointCircle3, northCircle.getCenter(), adjustAngle);
				pointCircle4 = Geometry.rotateAround(pointCircle4, northCircle.getCenter(), adjustAngle);
				angularVelocity = 0;
			} else {
				angle = angle.plus(temp);
				// Otherwise rotate for the full amount
				westLine = Geometry.rotateAround(westLine, northCircle.getCenter(), temp);
				eastLine = Geometry.rotateAround(eastLine, northCircle.getCenter(), temp);
				southCircle = Geometry.rotateAround(southCircle, northCircle.getCenter(), temp);
				pointCircle1 = Geometry.rotateAround(pointCircle1, northCircle.getCenter(), temp);
				pointCircle2 = Geometry.rotateAround(pointCircle2, northCircle.getCenter(), temp);
				pointCircle3 = Geometry.rotateAround(pointCircle3, northCircle.getCenter(), temp);
				pointCircle4 = Geometry.rotateAround(pointCircle4, northCircle.getCenter(), temp);
			}
			lines.clear();
			circles.clear();
			lines.add(westLine);
			lines.add(eastLine);
			circles.add(northCircle);
			circles.add(southCircle);
			circles.add(pointCircle1);
			circles.add(pointCircle2);
			circles.add(pointCircle3);
			circles.add(pointCircle4);

	} else if (angularVelocity < 0){
		if (eastLine.angle().radians() >= 3.14/2){
			if (eastLine.angle().radians() + temp.radians() < 3.14/2){
				Angle adjustAngle = new Angle(3.14/2-eastLine.angle().radians());
				angle = angle.plus(adjustAngle);
				westLine = Geometry.rotateAround(westLine, northCircle.getCenter(), adjustAngle);
				eastLine = Geometry.rotateAround(eastLine, northCircle.getCenter(), adjustAngle);
				southCircle = Geometry.rotateAround(southCircle, northCircle.getCenter(), adjustAngle);
				pointCircle1 = Geometry.rotateAround(pointCircle1, northCircle.getCenter(), adjustAngle);
				pointCircle2 = Geometry.rotateAround(pointCircle2, northCircle.getCenter(), adjustAngle);
				pointCircle3 = Geometry.rotateAround(pointCircle3, northCircle.getCenter(), adjustAngle);
				pointCircle4 = Geometry.rotateAround(pointCircle4, northCircle.getCenter(), adjustAngle);
				angularVelocity = 0;
			} else {
				angle = angle.plus(temp);
				westLine = Geometry.rotateAround(westLine, northCircle.getCenter(), temp);
				eastLine = Geometry.rotateAround(eastLine, northCircle.getCenter(), temp);
				southCircle = Geometry.rotateAround(southCircle, northCircle.getCenter(), temp);
				pointCircle1 = Geometry.rotateAround(pointCircle1, northCircle.getCenter(), temp);
				pointCircle2 = Geometry.rotateAround(pointCircle2, northCircle.getCenter(), temp);
				pointCircle3 = Geometry.rotateAround(pointCircle3, northCircle.getCenter(), temp);
				pointCircle4 = Geometry.rotateAround(pointCircle4, northCircle.getCenter(), temp);
			}
		}
			
 			lines.clear();
			circles.clear();
			lines.add(westLine);
			lines.add(eastLine);
			circles.add(northCircle);
			circles.add(southCircle);
			circles.add(pointCircle1);
			circles.add(pointCircle2);
			circles.add(pointCircle3);
			circles.add(pointCircle4);
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
			if(westLine.angle().radians() != 3.14){
				angularVelocity = 18.85;
			} else {
				angularVelocity = 0;
			}
		} else {
			active = false;
			stop();
		}
	}

	public void stop() {
		if (eastLine.angle().radians() != 3.14/2){
			angularVelocity = -18.85;
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

		if (newX >= 1 && newX <= 20  && newY >= 0 && newY <= 18){
			for (int i = (int) newX; i > newX - 2; i--){
				for (int j = (int) newY; j < newY + 2; j++){
					for (Coordinate c : occupiedSquares){
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
		int xposShifted = (int) (xpos + L);
		westLine = new LineSegment(xposShifted-halfL, ypos+quarterL, xposShifted-halfL, ypos+(L+(L/4)*3));
		eastLine = new LineSegment(xposShifted, ypos+quarterL, xposShifted, ypos+(L+(L/4)*3));
		northCircle = new Circle(xposShifted-quarterL, ypos+quarterL, quarterL);
		southCircle = new Circle(xposShifted-quarterL, ypos+(L+(L/4)*3), 5);

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
		if (!pressKeyCodesList.contains(key)){
			pressKeyCodesList.add(key);
		}
	}
	
	@Override
	public void setReleaseConnectKey(int key){
		if (!releaseKeyCodesList.contains(key)){
			releaseKeyCodesList.add(key);
		}
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
	public void rotate() {
	}

	@Override
	public Color getColor() {
		return color;
	}

	@Override
	public void changeColor() {
	}

}