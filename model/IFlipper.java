package model;
import java.util.List;

import physics.*;

public interface IFlipper {
	
	public int xpos();
	public int ypos();
	public int xpos2();
	public int ypos2();
	public int getWidth();
	public int getHeight();
	public Angle getAngle();
	public double getMomentum();
	public List<LineSegment> getLines();
	public List<Circle> getCircles();
	public void flipperAction(double moveTime);
	public void stop();
	public void action();
	public Circle getNorthCircle();
	public Circle getSouthCircle();
	

}
