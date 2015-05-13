package model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public interface IGizmoModel {

	public Ball getBall();
	
	public List<IGizmo> getGizmos();
	public List<IAbsorber> getAbsorbers();
	public List<IFlipper> getFlippers();
	
	public void moveBall();
	public void moveFlippers();
	
	public void createGizmo(char type, String opcode, int x, int y);
	public void createFlipper(char type, String opcode, int x, int y);
	public void createAbsorber(String opcode, int x1, int y1, int x2, int y2);
	public void createBall(String opcode, double x, double y, double vx, double vy);
	
	public void delete(IGizmo g);
	public void deleteAllGizmos();
	
	public void connectGizmo(IGizmo trigger, IGizmo action);

	public void keyPress(int keyCode);
	public void keyReleased(int keyCode);
	
	public void applyFriction(double time);
	
	public void loading() throws FileNotFoundException;

	public void saveBoard();

	public void runLogic();
	
	public void setGravity(float g);
	public void setFriction(float mu, float mu2);

	public void setBallSpeed(float x,float y);

	
}
