package controller;

import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputListener;

import model.IGizmoModel;
import view.IGBallView;

public class AddBallListener implements MouseInputListener {

	IGizmoModel model;
	IGBallView view;
	private static final double L = 20.0;
	
	public AddBallListener(IGizmoModel m, IGBallView v) {
		model = m;
		view = v;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		if (model.getBall() != null){
			model.delete(model.getBall());
		}
		double x = arg0.getX()/L;
		double y = arg0.getY()/L;
		
		model.createBall("B" + (int)x + (int)y, x, y, 100, 100);

	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}

}
