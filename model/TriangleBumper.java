package model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import physics.Circle;
import physics.LineSegment;
import physics.Vect;


public class TriangleBumper implements IGizmo {


	private String opCode;
	private int xpos;
	private int ypos;
	private int xpos2;
	private int ypos2;
	private int width;
	private static final double L = 20.0;
	
	String position;


	private LineSegment line1;
	private LineSegment line2;
	private LineSegment line3;

	private List<LineSegment> lines;

	private Circle circle1;
	private Circle circle2;
	private Circle circle3;
	private List<Circle> circles;
	
	private List<Coordinate> occupiedSquares;
	private List<Integer> pressKeyCodesList;
	private List<Integer> releaseKeyCodesList;
	
	GizmoModel model;
	
	Color color;
	
	public TriangleBumper(String op ,int x, int y, GizmoModel m) {
		
		model = m;
		
		opCode = op;
		xpos = (int) (x*L);
		ypos = (int) (y*L);
		
		occupiedSquares = new ArrayList<Coordinate>();
		
		Coordinate c = new Coordinate(x, y);
		occupiedSquares.add(c);
		
		xpos2 = (int) ((x+1)*L);
		ypos2 = (int) ((y+1)*L);
		
		width = (int) L;
		
		position = "1";
		
		circle1 = new Circle(xpos, ypos, 0);
		circle2 = new Circle(xpos+width, ypos, 0);
		circle3 = new Circle(xpos, ypos + width,0);

		circles = new ArrayList<Circle>();

		circles.add(circle1);
		circles.add(circle2);
		circles.add(circle3);

		line1 = new LineSegment(circle1.getCenter(), circle2.getCenter());
		line2 = new LineSegment(circle2.getCenter(), circle3.getCenter()); 
		line3 = new LineSegment(circle3.getCenter(), circle1.getCenter());

		lines = new ArrayList<LineSegment>();

		lines.add(line1);
		lines.add(line2);
		lines.add(line3);
		
		color = new Color(255, 255, 255);
		color = Color.blue;
		
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
	public int xpos2(){
		return xpos2;
	}
	@Override
	public int ypos2(){
		return ypos2;
	}
	
	public int getWidth(){
		return width;
	}
	
	@Override
	public void rotate(){
		if (position.equals("1")){
			position = position2();
		} else if (position.equals("2")){
			position = position3();
		} else if (position.equals("3")){
			position = position4();
		} else if (position.equals("4")){
			position = position1();
		}
		model.notifyUpdate();
	}
	
	private String position1(){
		circles.clear();
		circle1 = new Circle(xpos, ypos, 0);
		circle2 = new Circle(xpos+width, ypos, 0);
		circle3 = new Circle(xpos, ypos + width,0);
		circles.add(circle1);
		circles.add(circle2);
		circles.add(circle3);
		lines.clear();
		line1 = new LineSegment(circle1.getCenter(), circle2.getCenter());
		line2 = new LineSegment(circle2.getCenter(), circle3.getCenter()); 
		line3 = new LineSegment(circle3.getCenter(), circle1.getCenter());
		lines.add(line1);
		lines.add(line2);
		lines.add(line3);
		return "1";
	}
	
	private String position2(){
		circles.clear();
		circle1 = new Circle(xpos+width, ypos, 0);
		circle2 = new Circle(xpos + width, ypos + width, 0);
		circle3 = new Circle(xpos, ypos, 0);
		circles.add(circle1);
		circles.add(circle2);
		circles.add(circle3);
		lines.clear();
		line1 = new LineSegment(circle1.getCenter(), circle2.getCenter());
		line2 = new LineSegment(circle2.getCenter(), circle3.getCenter()); 
		line3 = new LineSegment(circle3.getCenter(), circle1.getCenter());
		lines.add(line1);
		lines.add(line2);
		lines.add(line3);
		return "2";
	}
	
	private String position3(){
		circles.clear();
		circle1 = new Circle(xpos + width, ypos + width, 0);
		circle2 = new Circle(xpos, ypos + width, 0);
		circle3 = new Circle(xpos + width, ypos,0);
		circles.add(circle1);
		circles.add(circle2);
		circles.add(circle3);
		lines.clear();
		line1 = new LineSegment(circle1.getCenter(), circle2.getCenter());
		line2 = new LineSegment(circle2.getCenter(), circle3.getCenter()); 
		line3 = new LineSegment(circle3.getCenter(), circle1.getCenter());
		lines.add(line1);
		lines.add(line2);
		lines.add(line3);
		return "3";
	}
	
	private String position4(){
		circles.clear();
		circle1 = new Circle(xpos, ypos + width, 0);
		circle2 = new Circle(xpos, ypos, 0);
		circle3 = new Circle(xpos + width, ypos + width, 0);
		circles.add(circle1);
		circles.add(circle2);
		circles.add(circle3);
		lines.clear();
		line1 = new LineSegment(circle1.getCenter(), circle2.getCenter());
		line2 = new LineSegment(circle2.getCenter(), circle3.getCenter()); 
		line3 = new LineSegment(circle3.getCenter(), circle1.getCenter());
		lines.add(line1);
		lines.add(line2);
		lines.add(line3);
		return "4";
	}
	
	private void reposition(){
		if (position.equals("1")){
			position1();
		} else if (position.equals("2")){
			position2();
		} else if (position.equals("3")){
			position3();
		} else if (position.equals("4")){
			position4();
		}
	}
	
	@Override
	public List<Circle> getCircles() {
		return circles;
	}

	@Override
	public String getOpCode(){
		return opCode;
	}
	@Override
	public List<LineSegment> getLines() {
		return lines;
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
	public void move(float newX, float newY) {
		// Update the occupied Squares List in the model
		
		model.setOccupiedSquares();
		boolean temp = false;
		
		if (newX >= 0 && newX <= 19*L  && newY >= 0 && newY <= 19*L){
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
		return position;
	}
}
