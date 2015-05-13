package model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import physics.*;

public class CircleBumper implements IGizmo {


	private String opCode;
	private int xpos;
	private int ypos;
	private int xpos2;
	private int ypos2;
	
	private double radius;
	private static final double L = 20.0;

	private List<Circle> circles;
	private Circle cir;

	Color color;
	
	GizmoModel model;
	
	private List<Coordinate>occupiedSquares;
	private List<Integer> pressKeyCodesList;
	private List<Integer> releaseKeyCodesList;
	
	public CircleBumper(String op,int x, int y, GizmoModel m){

		model = m;
		opCode = op;
		xpos = (int)(x*L);
		ypos = (int)(y*L);
		xpos2 = (int) ((x+1)*L);
		ypos2 = (int) ((y+1)*L);
		
		occupiedSquares = new ArrayList<Coordinate>();
		
		Coordinate c = new Coordinate(x, y);
		occupiedSquares.add(c);
		radius = L/2;

		circles = new ArrayList<Circle>();
		cir = new Circle(xpos+radius,ypos+radius,radius);	
		circles.add(cir);
		
		color = new Color(255, 255, 255);
		color = Color.red;
	
		pressKeyCodesList = new ArrayList<Integer>();
		releaseKeyCodesList = new ArrayList<Integer>();
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
	public int xpos2() {
		return xpos2;
	}

	@Override
	public int ypos2() {
		return ypos2;
	}

	public double getRadius(){
		return radius;
	}

	public Circle getCircle(){
		return cir;
	}

	@Override
	public String getOpCode(){
		return opCode;
	}
	@Override
	public int getWidth() {
		return (int) (radius*2);
	}

	@Override
	public List<LineSegment> getLines() {
		return null;
	}

	@Override
	public List<Circle> getCircles() {
		return circles;
	}

	@Override
	public Color getColor() {
		return color;
	}

	@Override
	public void changeColor() {
		if (color.equals(Color.green)){
			color = Color.BLUE;
		} else if (color.equals(Color.BLUE)){
			color = Color.green;
		}	
	}

	@Override
	public void rotate() {
		// TODO Auto-generated method stub
		
	}

	
	@Override
	public void move(float newX, float newY) {
		// Update the occupied Squares List in the model
		model.setOccupiedSquares();
		boolean temp = false;
		
		if (newX >= 0 && newX <= 19  && newY >= 0 && newY <= 19){
				for (Coordinate c : model.getOccupiedSquares()){
					if (c.x == newX && c.y == newY){
						temp = true;
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
			reposition();
			model.notifyUpdate();
		}
	}


	private void reposition() {
		circles.clear();
		cir = new Circle(xpos+radius,ypos+radius,radius);	
		circles.add(cir);
	}

	@Override
	public List<Coordinate> getOccupiedSquares() {
		return occupiedSquares;
	}

	@Override
	public void action() {
		changeColor();
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