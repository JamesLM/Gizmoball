package controller;

import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputListener;

import model.IGizmo;
import model.IGizmoModel;
import view.IGBallView;

public class MoveGizmoListener implements MouseInputListener {
	
	IGizmoModel model;
	IGBallView view;
	private static final double L = 20.0;
	IGizmo activeGizmo;

	public MoveGizmoListener(IGizmoModel m, IGBallView v){
		
		model = m;
		view = v;
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		int x = (int)(Math.floor((e.getX()/L)));
		int y = (int)(Math.floor((e.getY()/L)));
		IGizmo temp = null;
		for (IGizmo g : model.getGizmos()){
			if ((g.xpos() == (x*L)) && (g.ypos() == (y*L))) {
				temp = g;
			}
		}
		if (temp != null){
			activeGizmo = temp;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		int xtemp = (int) Math.floor(e.getX() / L);
		int ytemp = (int) Math.floor(e.getY() / L);
		
		// Call the gizmos move method then set the active gizmo to null
		if (activeGizmo != null){
			activeGizmo.move(xtemp, ytemp);
			activeGizmo = null;
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
