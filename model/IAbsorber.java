package model;

import java.util.List;

import physics.Circle;
import physics.LineSegment;
import physics.Vect;

public interface IAbsorber {
	
	public Ball launchNewBall(int velX, int velY);
	public void fireBall();
	public String getOpCode();

	public void setConnectKey(int key);
	public int getConnectKey();
	public List<LineSegment> getLines();
	
	public List<Circle> getCircles();
	public int getX();
	public int getY();
	
	public int getWidth();
	public int getHeight();
	public boolean contains(Ball b);
	
}
