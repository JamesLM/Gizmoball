package model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import physics.LineSegment;
import physics.Circle;

public class SquareBumper implements IGizmo {
	/**
	 * Square is made up of 4 lines and 4 zero-radius circles
	 * 
	 */
	
	private String opCode;
	
	private int xpos;
	private int ypos;
	private int xpos2;
	private int ypos2;
	
	private int width;
	private static final double L = 20.0;

	private LineSegment lsNorth;
	private LineSegment lsSouth;
	private LineSegment lsWest;
	private LineSegment lsEast;

	private List<LineSegment> lines;

	private Circle cirNW;
	private Circle cirNE;
	private Circle cirSW;
	private Circle cirSE;

	private List<Circle> circles; 
	
	private List<Coordinate> occupiedSquares;
	private List<Integer> pressKeyCodesList;
	private List<Integer> releaseKeyCodesList;
	
	Color color;
	
	GizmoModel model;

	public SquareBumper(String op, int x, int y, GizmoModel m) {
		
		model = m;
		
		opCode = op;

		xpos = (int)(x*L);
		ypos = (int)(y*L);
		xpos2 = (int) ((x+1)*L);
		ypos2 = (int) ((y+1)*L);
		
		occupiedSquares = new ArrayList<Coordinate>();
		
		Coordinate c = new Coordinate(x, y);
		occupiedSquares.add(c);
		
		width = (int) L;

		lines = new ArrayList<LineSegment>();
		lsNorth = new LineSegment(xpos, ypos, xpos + width, ypos);
		lsSouth = new LineSegment(xpos, ypos + width, xpos + width, ypos + width);
		lsWest = new LineSegment(xpos, ypos, xpos, ypos + width);
		lsEast = new LineSegment(xpos + width, ypos, xpos + width, ypos + width);
		
		lines.add(lsNorth);
		lines.add(lsSouth);
		lines.add(lsWest);
		lines.add(lsEast);
		
		circles = new ArrayList<Circle>();
		cirNW = new Circle(xpos, ypos , 0);
		cirNE = new Circle(xpos + width, ypos, 0);
		cirSW = new Circle(xpos, ypos + width, 0);
		cirSE = new Circle(xpos + width, ypos + width, 0);
		
		circles.add(cirNW);
		circles.add(cirNE);
		circles.add(cirSW);
		circles.add(cirSE);
		
		color = new Color(255, 255, 255);
		color = Color.green;
		
		pressKeyCodesList = new ArrayList<Integer>();
		releaseKeyCodesList = new ArrayList<Integer>();
	}
	
	@Override
	public String getOpCode(){
		return opCode;
	}
	@Override
	public int xpos(){
		return xpos;
	}
	@Override
	public int ypos(){
		return ypos;
	}
	
	public int xpos2(){
		return xpos2;
	}
	
	public int ypos2(){
		return ypos2;
	}
	@Override
	public int getWidth(){
		return width;
	}
	@Override
	public List<LineSegment> getLines(){
		return lines;
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
		// Swap colors when this method is triggered.
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
	public void action() {
		changeColor();
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
		lsNorth = new LineSegment(xpos, ypos, xpos + width, ypos);
		lsSouth = new LineSegment(xpos, ypos + width, xpos + width, ypos + width);
		lsWest = new LineSegment(xpos, ypos, xpos, ypos + width);
		lsEast = new LineSegment(xpos + width, ypos, xpos + width, ypos + width);
		
		lines.clear();
		
		lines.add(lsNorth);
		lines.add(lsSouth);
		lines.add(lsWest);
		lines.add(lsEast);

		cirNW = new Circle(xpos, ypos , 0);
		cirNE = new Circle(xpos + width, ypos, 0);
		cirSW = new Circle(xpos, ypos + width, 0);
		cirSE = new Circle(xpos + width, ypos + width, 0);
		
		circles.clear();
		
		circles.add(cirNW);
		circles.add(cirNE);
		circles.add(cirSW);
		circles.add(cirSE);
		
	}

	@Override
	public List<Coordinate> getOccupiedSquares() {
		return occupiedSquares;
	}

	@Override
	public String getOrientation() {
		// TODO Auto-generated method stub
		return null;
	}
	


}

