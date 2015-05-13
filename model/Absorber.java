package model;


import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import physics.Circle;
import physics.LineSegment;
import physics.Vect;

public class Absorber implements IAbsorber, IGizmo {
	

	private String opCode;
	
	private int xpos1;
	private int ypos1;
	private int xpos2;
	private int ypos2;
	private static final double L = 20.0;
	
	private LineSegment lsNorth;
	private LineSegment lsSouth;
	private LineSegment lsEast;
	private LineSegment lsWest;
	public List<LineSegment> lineList;
	
	private Circle cir1;
	private Circle cir2;
	private Circle cir3;
	private Circle cir4;
	private List<Circle> circleList;
	
	private List<Coordinate> occupiedSquares;
	Ball ball;
	
	Color color;
	
	private int newVelX;
	private int newVelY;
	private int connectKey;
	
	boolean holdingBall = false;
	
	GizmoModel model;
	
	private List<Integer> pressKeyCodesList;
	private List<Integer> releaseKeyCodesList;
	

	public Absorber(String op, int x, int y, int x2, int y2, GizmoModel m){
		
		
		model = m;
		
		//Check the arguments are within boundaries
		if (x < 0){
			x = 0;
		}
		if (x2 > 20){
			x2 = 20;
		}
		if (y < 0){
			y = 0;
		}
		if (y2 > 20){
			y2 = 20; 
		}
		
		occupiedSquares = new ArrayList<Coordinate>();
		pressKeyCodesList = new ArrayList<Integer>();
		releaseKeyCodesList = new ArrayList<Integer>();
		
		for (int i = x; i < x2 - 1; i++){
			for (int j = y; j < y2 - 1; j++){
				Coordinate c = new Coordinate(i, j);
				occupiedSquares.add(c);
			}
		}
		
		// Coordinates of Top Left Corner
		xpos1 = (int)(x*L);
		ypos1 = (int)(y*L);
		
		opCode = op;
		
		// Coordinates of Bottom Right Corner
		xpos2 = (int)(x2*L);
		ypos2 = (int)(y2*L);
		
		// 4 lines of the Rectangle
		lsNorth = new LineSegment(xpos1, ypos1, xpos2, ypos1);
		lsSouth = new LineSegment(xpos1, ypos2, xpos2, ypos2);
		lsEast = new LineSegment(xpos2, ypos1, xpos2, ypos2);
		lsWest = new LineSegment(xpos1, ypos1, xpos1, ypos2);
		lineList = new ArrayList<LineSegment>();
		lineList.add(lsNorth);
		lineList.add(lsSouth);
		lineList.add(lsEast);
		lineList.add(lsWest);

		// 4 Zero-Radius Circles at the corners
		cir1 = new Circle(xpos1, ypos1, 0);
		cir2 = new Circle(xpos2, ypos1, 0);
		cir3 = new Circle(xpos1, ypos2, 0);
		cir4 = new Circle(xpos2, ypos2, 0);
		circleList = new ArrayList<Circle>();
		circleList.add(cir1);
		circleList.add(cir2);
		circleList.add(cir3);
		circleList.add(cir4);
		
		color = Color.PINK;
		
	}
	
	public Ball launchNewBall(int velX, int velY){
		newVelX = velX;
		newVelY = velY;
		
		ball = new Ball("opcode",xpos2/L-0.25, ypos2/L-0.25, newVelX, newVelY, model);
		ball.stop();
		
		holdingBall = true;
		
		return ball;
	}
	
	public void fireBall() {
		if (ball.stopped()){
			ball.setVelo(new Vect(0, -1000));
			ball.start();
		}	
	}


	
	public void setConnectKey(int key){
		this.connectKey = key;
	}
	
	public int getConnectKey(){
		return connectKey;
	}
	
	public List<LineSegment> getLines(){
		return lineList;
	}
	
	public List<Circle> getCircles(){
		return circleList;
	}
	
	public int getX(){
		return xpos1;
	}
	
	public int getY(){
		return ypos1;
	}
	
	public int getWidth(){
		int width = xpos2 - xpos1;
		return width;
	}
	
	public int getHeight(){
		return (ypos2 - ypos1);
	}
	
	/*
	 * Check if the ball is in this absorber
	 */
	public boolean contains(Ball b){
		Ball ball = b;
		boolean temp = false;
		if ((ball.getExactX() > getX()) && (ball.getExactX() < (getX() + getWidth()))){
			if (((ball.getExactY() > getY()) && (ball.getExactY() < (getY() + getHeight())))){
				temp = true;
			}
		}
		return temp;
	}

	@Override
	public String getOpCode() {
		return opCode;
	}

	@Override
	public int xpos() {
		return xpos1;
	}

	@Override
	public int ypos() {
		return ypos1;
	}
	
	@Override
	public int xpos2() {
		return xpos2;
	}

	@Override
	public int ypos2() {
		return ypos2;
	}
	
	@Override
	public void rotate() {
		// TODO Auto-generated method stub
		
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
	public void move(float newX, float newY) {
		
		// if the new top-left coordinates are okay
		if (newX > 0 && newX <= 19*L && newY > 0 && newY <= 19*L)
		{ // Check the new bottom right coordinates.
			int diffX = (int) (newX - xpos1);
			int diffY = (int) (newY - ypos1);
			int newX2 = xpos2 + diffX;
			int newY2 = ypos2 + diffX;
			
			if (newX2 > 0 && newX2 <= 19*L && newY2 > 0 && newY2 <= 19*L){
				boolean temp = false;
				for (IGizmo g : model.getGizmos()){
					if (g.xpos() > newX && g.xpos() < newX2 && g.ypos() > newY && g.ypos() < newY2){
						temp = true;
					}
				}
				if (temp == false){
					// No Gizmo's in the way, change the coordinates
					xpos1 = (int) newX;
					ypos1 = (int) newY;
					xpos2 = newX2;
					ypos2 = newY2;
				}
			}
		}
	}

	@Override
	public List<Coordinate> getOccupiedSquares() {
		return occupiedSquares;
	}

	@Override
	public void action() {
		if (holdingBall == true){
			fireBall();
		}
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