package controller;

import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputListener;

import model.IGizmoModel;
import view.IGBallView;

public class AddFlipperListener implements MouseInputListener{
	
	IGizmoModel model;
	IGBallView view;
	private static final double L = 20.0;
	
	public AddFlipperListener(IGizmoModel m, IGBallView v){
		
		model = m;
		view = v;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		
		if (view.getSelectedFlipper().equals("Left Flipper")){
			int x = (int)(Math.floor((arg0.getX()/L)));
			System.out.println("left flipper x press " + x);
			int y = (int)(Math.floor((arg0.getY()/L)));
			model.createFlipper('l',"lf" + x + y, x, y);
		}else if (view.getSelectedFlipper().equals("Right Flipper")){
			int x = (int)(Math.floor(((arg0.getX()-20)/L)));
			System.out.println("right flipper x press " + x);
			int y = (int)(Math.floor(((arg0.getY())/L)));
			model.createFlipper('r', "rf" + x + y, x, y);
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
