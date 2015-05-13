package controller;

import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputListener;

import view.IGBallView;
import model.IGizmoModel;



public class AddGizmoListener implements MouseInputListener {
	
	IGizmoModel model;
	IGBallView view;
	private static final double L = 20.0;
	
	public AddGizmoListener(IGizmoModel m, IGBallView v){
		
		model = m;
		view = v;
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {
		int x = (int)(Math.floor((arg0.getX()/L)));
		int y = (int)(Math.floor((arg0.getY()/L)));

		if (view.getSelectedGizmo().equals("Square")){
			model.createGizmo('s', "S"+Integer.toString(x)+Integer.toString(y), x, y);
		} else if (view.getSelectedGizmo().equals("Circle")){
			model.createGizmo('c', "C"+Integer.toString(x)+Integer.toString(y), x, y);
		} else if (view.getSelectedGizmo().equals("Triangle")){
			model.createGizmo('t', "T"+Integer.toString(x)+Integer.toString(y), x, y);
		}
		
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
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
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

}
