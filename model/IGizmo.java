package model;

import java.awt.Color;
import java.util.List;

import physics.Circle;
import physics.LineSegment;

public interface IGizmo {
	/** Gizmo's implement this interface allowing them to be treated as IGizmo
	 * 	- SquareBumpers, TriangleBumpers and CircleBumpers.
	 * 	- Possibly also Absorbers and Flippers but for the moment no.
	 */
	
	public String getOpCode();
	public int xpos();
	public int ypos();
	public int xpos2();
	public int ypos2();
	
	public void rotate();
	public int getWidth();
	public List<LineSegment> getLines();
	public List<Circle> getCircles();
	public Color getColor();
	public void changeColor();
	public void move(float f, float g);
	
	public List<Coordinate> getOccupiedSquares();
	public void action();
	
	public List<Integer> pressKeyCodes();
	public List<Integer> releaseKeyCodes();
	void setReleaseConnectKey(int key);
	void setPressConnectKey(int key);
	public String getOrientation();

}
