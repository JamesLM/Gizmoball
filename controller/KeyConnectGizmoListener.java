package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputListener;

import view.IGBallGui;
import view.IGBallView;
import model.Coordinate;
import model.IFlipper;
import model.IGizmo;
import model.IGizmoModel;

public class KeyConnectGizmoListener implements MouseInputListener, KeyListener{
	
	double L = 20.0;
	private IGizmoModel model;
	private IGBallView view;
	private IGBallGui gui;
	IGizmo activeGizmo;
	
	public KeyConnectGizmoListener(IGizmoModel m, IGBallView v, IGBallGui gui){
		model = m;
		view = v;
		this.gui = gui;
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {
		
		double x = Math.floor(arg0.getX()/L);
		double y = Math.floor(arg0.getY()/L);
		
		System.out.println("mouse X" + x);
		boolean temp = false;
		for (IGizmo g: model.getGizmos()){
			for (Coordinate c : g.getOccupiedSquares()){
				if (c.x() == x && c.y() == y){
					temp = true;
					activeGizmo = g;
					break;
				}
			}
		}
		view.setFocus();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		if (activeGizmo != null){
			if (gui.triggerAction().equals("keyPress")){
				activeGizmo.setPressConnectKey(arg0.getKeyCode());
			} else if (gui.triggerAction().equals("keyRelease")){
				activeGizmo.setReleaseConnectKey(arg0.getKeyCode());
			}
		}
		activeGizmo = null;
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
