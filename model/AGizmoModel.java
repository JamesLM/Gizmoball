package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JFileChooser;

import physics.*;
import view.GBallView;


public abstract class AGizmoModel extends Observable implements IGizmoModel {


	protected static final double L = 20.0;

	private Ball ball;
	private Walls gws;

	// These are Lists for each Gizmo in the game
	private List<Ball> ballList;
	protected List<IGizmo> gizmoList;

	private List<IFlipper> flipperList;
	private List<IAbsorber> absorberList;
	private List<String> saveList;
	
	protected List<Coordinate> occupiedSquares;

	int counter = 0;
	boolean absorberHit;
	IAbsorber struckAb = null;
	CollisionDetails cd;
	double moveTime = 0.05; // 0.05 = 20 times per second as per Gizmoball
	
	float gravityConstant = 25;
	float mu = (float) 0.025;
	float mu2 = (float) 0.025;
	
	
	static ArrayList<String> fileIn = new ArrayList<String>();
	
	IGizmo collisionGizmo;
	
	Map<IGizmo, List<IGizmo>> triggerList;

	public AGizmoModel(){

		// Wall size 400 x 400 pixels
		gws = new Walls(0, 0, 400, 400);

		ballList = new ArrayList<Ball>();
		gizmoList = new ArrayList<IGizmo>();
		saveList = new ArrayList<String>();
		flipperList = new ArrayList<IFlipper>();
		absorberList = new ArrayList<IAbsorber>();
		
		// A List of squares 'occupied' or otherwise exclusively required by Gizmos
		occupiedSquares = new ArrayList<Coordinate>();
		
		// A Map which maps Gizmo's to the gizmos they action
		triggerList = new HashMap<IGizmo, List<IGizmo>>();
		
		// Boolean to assist in Absorber collisions
		absorberHit = false;
	}

	public void createGizmo(char type, String opcode, int x, int y){	
		IGizmo gizmo = createGizmoOp(type, opcode, x, y);
		String actualType = null;
		if(type == 's'){
			actualType = "Square";
		}else if (type == 't'){
			actualType = "Triangle";
		}else if(type == 'c'){
			actualType = "Circle";
		}
		String xCoord = Integer.toString(x);
		String yCoord = Integer.toString(y);
		String full = actualType +" "+ opcode +" "+ xCoord +" " + yCoord;
		if (gizmo != null){
			gizmoList.add(gizmo);
			saveList.add(full);
			setChanged();
			notifyObservers();
		}
	}
	
	public void createAbsorber(String opcode, int x1, int y1, int x2, int y2){
		IAbsorber ab = createAbsorberOp(opcode, x1, y1, x2, y2);
		String xCoord1 = Integer.toString(x1);
		String yCoord1 = Integer.toString(y1);
		String xCoord2 = Integer.toString(x2);
		String yCoord2 = Integer.toString(y2);
		String full = "Absorber"+" "+opcode +" " + xCoord1+" "+ yCoord1+" "+ xCoord2 +" "+ yCoord2;
		if (ab != null){
			absorberList.add(ab);
			gizmoList.add((IGizmo)ab);
			saveList.add(full);
			setChanged();
			notifyObservers();
		}
	}
	
	@Override
	public void createFlipper(char type, String opcode, int x, int y){
		IFlipper fl = createFlipperOp(type, opcode, x, y);
		if (fl!= null){
			flipperList.add(fl);
			gizmoList.add((IGizmo)fl);
			setChanged();
			notifyObservers();
		}
	
	}
	@Override
	public void createBall(String opcode, double x, double y, double vx, double vy){
		ball = createBallOp(opcode, x, y, vx, vy);
		if (ball != null){
			ballList.add(ball);
			gizmoList.add((IGizmo) ball);
			setChanged();
			notifyObservers();
		} else {
		}
		
	}
	public abstract IGizmo createGizmoOp(char type, String opcode, int x, int y);
	public abstract IAbsorber createAbsorberOp(String opcode, int x1, int y1, int x2, int y2);
	public abstract IFlipper createFlipperOp(char type, String opcode, int x, int y);
	public abstract Ball createBallOp(String opcode, double x, double y, double vx, double vy);
	
	
	public void connectGizmo(IGizmo trigger, IGizmo action){
		/* If the trigger is already in the map, simply add
		* the action Gizmo
		*/
		if (triggerList.containsKey(trigger)){
			triggerList.get(trigger).add(action);
		} else {
			// else, make a new list of action Gizmo's and add it to map
			List<IGizmo> actionList = new ArrayList<IGizmo>();
			actionList.add(action);
			triggerList.put(trigger, actionList);
		}
	}
	
	private void actionConnectedGizmos(IGizmo trigger){
		if (triggerList.containsKey(trigger)){
			for (IGizmo g : triggerList.get(trigger)){
				g.action();
			}
		}
	}
	@Override
	public void runLogic(){
		moveBall();
		moveFlippers();
		if (collisionGizmo != null){
			actionConnectedGizmos(collisionGizmo);
			System.out.println("triiger list size: " + triggerList.size());
		}
	}
	
	public void moveFlippers(){

		if (cd.getTuc() < moveTime){
			for (IFlipper fl : flipperList){
				fl.flipperAction(cd.getTuc());
			}
		} else {
			for (IFlipper fl : flipperList){
				fl.flipperAction(moveTime);
			}
		}
	}
	public void moveBall() {

		applyGravity(moveTime);
		applyFriction(moveTime);
	

		if (absorberHit == true){
			ball = struckAb.launchNewBall(0, 0);
			absorberHit = false;
		}		
		if (ball != null && !ball.stopped()) {
			cd = timeUntilCollision();	
			double tuc = cd.getTuc();
			if (tuc > moveTime) {
				// No collision ...
				ball = movelBallForTime(ball, moveTime);
			} else {
				ball = movelBallForTime(ball, tuc);
				// Post collision velocity ...
				ball.setVelo(cd.getVelo());
			}
		}
		this.setChanged();
		this.notifyObservers();
	}
	
	@Override
	public void setBallSpeed(float x,float y){
		ball.setVelo(new Vect(x, y));
	}
	
	private Ball movelBallForTime(Ball ball, double time) {
		double newX = 0.0;
		double newY = 0.0;
		double xVel = ball.getVelo().x();
		double yVel = ball.getVelo().y();
		newX = ball.getExactX() + (xVel * time);
		newY = ball.getExactY() + (yVel * time);
		ball.setExactX(newX);
		ball.setExactY(newY);
		return ball;
	}

	private CollisionDetails timeUntilCollision() {

		Circle ballCircle = ball.getCircle();
		Vect ballVelocity = ball.getVelo();
		Vect newVelo = new Vect(0, 0);

		double shortestTime = Double.MAX_VALUE;
		double time = 0.0;

		/*
		 * Time to collide with 4 Walls.
		 */
		ArrayList<LineSegment> lss = gws.getLineSegments();
		for (LineSegment line : lss) {
			time = Geometry.timeUntilWallCollision(line, ballCircle, ballVelocity);
			if (time < shortestTime) {
				shortestTime = time;
				collisionGizmo = null;
				newVelo = Geometry.reflectWall(line, ball.getVelo(), 1.0);
			}
		}

		/*
		 * Time to collide with all the Gizmos
		 */
		for (IGizmo g : gizmoList){
			if (g instanceof Absorber == false && g instanceof LeftFlipper == false && g instanceof RightFlipper == false){
				if (g.getLines() != null){
					for (LineSegment ls : g.getLines()){
						time = Geometry.timeUntilWallCollision(ls, ballCircle, ballVelocity);
						if (time < shortestTime) {
							shortestTime = time;
							collisionGizmo = g;
							newVelo = Geometry.reflectWall(ls, ball.getVelo(), 1.0);
						}
					}
				}
				if (g.getCircles() != null){
					for (Circle ci: g.getCircles()){
						time = Geometry.timeUntilCircleCollision(ci, ballCircle, ballVelocity);
						if (time < shortestTime) {
							shortestTime = time;
							collisionGizmo = g;
							newVelo = Geometry.reflectCircle(ci.getCenter(), ball.getCircle().getCenter(), ball.getVelo(), 1.0);
						}
					}
				}
			}
		}
		

		for (IFlipper f : flipperList){
			for (LineSegment ls : f.getLines()){
				time = Geometry.timeUntilRotatingWallCollision(ls, f.getNorthCircle().getCenter(), f.getMomentum(), ball.getCircle(), ball.getVelo());
				if (time < shortestTime){
					shortestTime = time;
					collisionGizmo = (IGizmo) f;
					newVelo = Geometry.reflectRotatingWall(ls, f.getNorthCircle().getCenter(), f.getMomentum(), ball.getCircle(), ball.getVelo(), 0.95);
				}
			}
			for (Circle ci : f.getCircles()){
				time = Geometry.timeUntilRotatingCircleCollision(ci, f.getNorthCircle().getCenter(), f.getMomentum(), ball.getCircle(), ball.getVelo());
				if (time < shortestTime){
					shortestTime = time;
					collisionGizmo = (IGizmo) f;
					newVelo = Geometry.reflectRotatingCircle(ci, f.getNorthCircle().getCenter(), f.getMomentum(), ball.getCircle(), ball.getVelo(), 0.95);
				}
			}
		}
	
		/*
		 * Time to collide with the absorbers
		 * 
		 * Check the absorbers last because if we get an absorber collision the ball
		 * must be moved.
		 */
		for (IAbsorber ab : absorberList) {
			if (!ab.contains(ball)){				
				for (LineSegment ls : ab.getLines()){
					time = Geometry.timeUntilWallCollision(ls, ballCircle, ballVelocity);
					if (time < shortestTime) {
						shortestTime = time;
						collisionGizmo = (IGizmo) ab;
						if (shortestTime <= moveTime){
							absorberHit = true;
							struckAb =  ab;
						}
					}
				}
				for (Circle cir : ab.getCircles()){
					time = Geometry.timeUntilCircleCollision(cir, ballCircle, ballVelocity);
					if (time < shortestTime) {
						shortestTime = time;
						collisionGizmo = (IGizmo) ab;
	
						if (shortestTime <= moveTime){
							absorberHit = true;
							struckAb =  ab;
						}
					}
				}
			}
		}
		if (shortestTime > moveTime){
			collisionGizmo = null;
			}
		return new CollisionDetails(shortestTime, newVelo);
	}

	public void applyFriction(double time) {

		if (ball.stopped() == false){	
			Vect temp = ball.getVelo();
			double newVectX = temp.x()*(1-mu*time-mu2*Math.abs(temp.x()/20)*time);
			double newVectY = temp.y()*(1-mu*time-mu2*Math.abs(temp.y()/20)*time);
			Vect frictVect = new Vect(newVectX, newVectY);
			ball.setVelo(frictVect);
		}
	}

	private void applyGravity(double time) {

		if (ball.stopped() == false){
			Vect temp = ball.getVelo();
			double xVect = temp.x();
			double yVect = temp.y() + ((L*gravityConstant)*time);

			ball.setVelo(new Vect(xVect, yVect));
		}
	}
	
	public void setGravity(float g){
		gravityConstant = g;
	}
	public void setFriction(float newMu, float newMu2){
		mu = newMu;
		mu2 = newMu2;
	}

	public void setBallSpeed(int x, int y) {
		ball.setVelo(new Vect(x, y));
	}

	@Override
	public Ball getBall() {
		return ball;
	}

	@Override
	public List<IAbsorber> getAbsorbers() {
		return absorberList;
	}

	public void addAbsorber(Absorber ab){
		absorberList.add(ab);
	}

	@Override
	public List<IGizmo> getGizmos() {
		return gizmoList;
	}

	@Override
	public List<IFlipper> getFlippers() {
		return flipperList;
	}

	@Override
	public void delete(IGizmo g){
		gizmoList.remove(g);
		flipperList.remove(g);
		absorberList.remove(g);
		setChanged();
		notifyObservers();
	}
	
	@Override
	public void deleteAllGizmos(){
		gizmoList.clear();
		flipperList.clear();
		absorberList.clear();
		setChanged();
		notifyObservers();
	}
	
	public void setOccupiedSquares(){
		occupiedSquares.clear();
		for (IGizmo g : getGizmos()){
			for (Coordinate c : g.getOccupiedSquares()){
				occupiedSquares.add(c);
			}
		}
	}
	
	public List<Coordinate> getOccupiedSquares(){
		return occupiedSquares;
	}
	
	public void notifyUpdate(){
		setChanged();
		notifyObservers();
	}
	
	@Override
	public void keyPress(int keyCode){
		for (IGizmo g : gizmoList){
			for (int code : g.pressKeyCodes()){
				if (code == keyCode){
					g.action();
				}
			}
		}
	}
	
	@Override
	public void keyReleased(int keyCode){
		for (IGizmo g : gizmoList){
			for (int code : g.releaseKeyCodes()){
				if (code == keyCode){
					g.action();
				}
			}
		}
	}
	
	public void loading() throws FileNotFoundException{
		deleteAllGizmos();
		File file = null;
		JFileChooser fc = new JFileChooser();
		int returnVal2 = fc.showOpenDialog(fc);
		if (returnVal2 == JFileChooser.APPROVE_OPTION) {
			file = fc.getSelectedFile();
		}

		Scanner s = new Scanner(file);
		while (s.hasNextLine()){
			String input = s.next();
			switch (input) {
			case "Triangle":
				createGizmo('t', s.next(), s.nextInt(), s.nextInt());
				break;
			case "Square":
				createGizmo('s', s.next(), s.nextInt(), s.nextInt());
				break;
			case "Circle":
				createGizmo('c', s.next(), s.nextInt(), s.nextInt());
				break;
			case "LeftFlipper":
				createFlipper('l', s.next(), s.nextInt(), s.nextInt());
				break;
			case "RightFlipper":
				createFlipper('r', s.next(), s.nextInt(), s.nextInt());
				break;
			case "Absorber":
				createAbsorber(s.next(), s.nextInt(), s.nextInt(), s.nextInt(), s.nextInt());
				break;
			case "Rotate":
				String temp = s.next();
				for(IGizmo g : gizmoList){
					if (g.getOpCode().equals(temp)){
						g.rotate();
					}
				}
				break;
			case "KeyConnect":
				s.next();
				Integer keyCode = s.nextInt();
				System.out.println("keycode: " + keyCode);
				String keyAction = s.next();
				String opcode = s.next();
				for(IGizmo g : gizmoList){
					if (g.getOpCode().equals(opcode)){
						if (keyAction.equals("down")){
 							g.setPressConnectKey(keyCode);
						} else if (keyAction.equals("up")){
							g.setReleaseConnectKey(keyCode);
						}
					}
				}
				break;
			case "Connect":
				String producer = s.next();
				String consumer = s.next();
				IGizmo tempProducer = null;
				IGizmo tempConsumer = null;
				for(IGizmo g : gizmoList){
					if (g.getOpCode().equals(producer)){
						tempProducer = g;
						break;
					}
				}
				for(IGizmo g : gizmoList){
					if (g.getOpCode().equals(consumer)){
						tempConsumer = g;
						break;
					}
				}
				connectGizmo(tempProducer, tempConsumer);
				break;

			case "Ball":
				createBall(s.next(), s.nextDouble(), s.nextDouble(), s.nextDouble(), s.nextDouble());
				break;
			case "Gravity":
				setGravity(s.nextFloat());
				break;
			case "Friction":
				setFriction(s.nextFloat(), s.nextFloat());
				break;
			case "Move":
				String tempOpcode = s.next();
				for(IGizmo g : gizmoList){
					if (g.getOpCode().equals(tempOpcode)){
						g.move(s.nextInt(), s.nextInt());
					}
				}

			case "Delete":
				for(IGizmo g : gizmoList){
					if (g.getOpCode().equals(s.next())){
						delete(g);
					}
				}
			}
		}
	}



	public void saveBoard(){
	
		JFileChooser fc = new JFileChooser();
		int returnVal = fc.showSaveDialog(fc);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			try(FileWriter fw = new FileWriter(fc.getSelectedFile()+".txt")) {
				for (IGizmo g : gizmoList){
					if (g instanceof Absorber){
						fw.write("Absorber"+" "+g.getOpCode()+" "+(int)(g.xpos()/L)+" "+(int)(g.ypos()/L)+" "+(int)(g.xpos2()/L)+" "+(int)(g.ypos2()/L));
						fw.append(System.lineSeparator());
						if (g.pressKeyCodes().size() > 0){
							for (Integer s : g.pressKeyCodes()){
								fw.write("KeyConnect key"+" "+s+" down "+ g.getOpCode());
								fw.append(System.lineSeparator());
							}
						}
						if (g.releaseKeyCodes().size() > 0){
							for (Integer s : g.releaseKeyCodes()){
								fw.write("KeyConnect key "+s+" up "+ g.getOpCode());
								fw.append(System.lineSeparator());
							}
						}
					} else if(g instanceof LeftFlipper){
						fw.write("LeftFlipper"+" "+g.getOpCode()+" "+(int)(g.xpos()/L)+" "+(int)(g.ypos()/L));
						fw.append(System.lineSeparator());
						if (g.pressKeyCodes().size() > 0){
							for (Integer s : g.pressKeyCodes()){
								fw.write("KeyConnect key"+" "+s+" down "+ g.getOpCode());
								fw.append(System.lineSeparator());
							}
						}
						if (g.releaseKeyCodes().size() > 0){
							for (Integer s : g.releaseKeyCodes()){
								fw.write("KeyConnect key "+s+" up "+ g.getOpCode());
								fw.append(System.lineSeparator());
							}
						}
					} else if (g instanceof RightFlipper){
						fw.write("RightFlipper"+" "+g.getOpCode()+" "+(int)(g.xpos()/L)+" "+(int)(g.ypos()/L));
						fw.append(System.lineSeparator());
						if (g.pressKeyCodes().size() > 0){
							for (Integer s : g.pressKeyCodes()){
								fw.write("KeyConnect key"+" "+s+" down "+ g.getOpCode());
								fw.append(System.lineSeparator());
							}
						}
						if (g.releaseKeyCodes().size() > 0){
							for (Integer s : g.releaseKeyCodes()){
								fw.write("KeyConnect key "+s+" up "+ g.getOpCode());
								fw.append(System.lineSeparator());
							}
						}
					} else if (g instanceof Ball){
						fw.write("Ball"+" "+g.getOpCode()+" "+(g.xpos()+L/4)/L+" "+(g.ypos()+L/4)/L+" " + ball.getVelo().x()+ " " + ball.getVelo().y());
						fw.append(System.lineSeparator());
					} else if (g instanceof TriangleBumper){
						fw.write("Triangle"+" "+g.getOpCode()+" "+(int)(g.xpos()/L)+" "+(int)(g.ypos()/L));
						fw.append(System.lineSeparator());
						for (int i = 0; i < Integer.parseInt(g.getOrientation())-1; i++){
							fw.write("Rotate " + g.getOpCode());
							fw.append(System.lineSeparator());
						}
					} else if (g instanceof CircleBumper){
						fw.write("Circle"+" "+g.getOpCode()+" "+(int)(g.xpos()/L)+" "+(int)(g.ypos()/L));
						fw.append(System.lineSeparator());
					} else if (g instanceof SquareBumper){
						fw.write("Square"+" "+g.getOpCode()+" "+(int)(g.xpos()/L)+" "+(int)(g.ypos()/L));
						fw.append(System.lineSeparator());
					}
				}
				
				Set<IGizmo> tempSet = triggerList.keySet();
				for (IGizmo f : tempSet){
					List<IGizmo> tempList = triggerList.get(f);
					for (IGizmo g : tempList){
						fw.write("Connect"+" "+f.getOpCode()+" "+(g.getOpCode()));
						fw.append(System.lineSeparator());
					}
				}
				
				fw.write("Gravity " + gravityConstant);
				fw.append(System.lineSeparator());
				fw.write("Friction " + mu + " " + mu2);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}

