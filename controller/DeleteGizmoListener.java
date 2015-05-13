package controller;

import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputListener;

import model.Coordinate;
import model.IGizmo;
import model.IGizmoModel;
import view.IGBallView;

public class DeleteGizmoListener implements MouseInputListener {
	
	IGizmoModel model;
	IGBallView view;
	private static final double L = 20.0;
	
	public DeleteGizmoListener(IGizmoModel m, IGBallView v){

		model = m;
		view = v;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int x = (int)(Math.floor((e.getX()/L)));
		int y = (int)(Math.floor((e.getY()/L)));
		IGizmo temp = null;
		for (IGizmo g : model.getGizmos()){
			for(Coordinate c : g.getOccupiedSquares()){
				if (c.x() == x && c.y() == y){
					temp = g;
					break;
				}
			}
		}
		model.delete(temp);
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
