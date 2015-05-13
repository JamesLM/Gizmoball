package controller;

import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputListener;

import view.IGBallView;
import model.IGizmo;
import model.IGizmoModel;


import view.IGBallView;
import model.IGizmo;
import model.IGizmoModel;

public class RotateGizmoListener implements MouseInputListener {
	
	IGizmoModel model;
	IGBallView view;
	private static final double L = 20.0;
	
	public RotateGizmoListener(IGizmoModel m, IGBallView v){
		model = m;
		view = v;	
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int x = (int)(Math.floor((e.getX()/L)));
		int y = (int)(Math.floor((e.getY()/L)));
		for (IGizmo g : model.getGizmos()){
			if ((g.xpos() == (x*L)) && (g.ypos() == (y*L))) {
				g.rotate();
			}
		}
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
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
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
