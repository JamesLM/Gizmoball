package controller;

import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputListener;

import model.Coordinate;
import model.IGizmo;
import model.IGizmoModel;
import view.IGBallView;

public class ConnectGizmoListener implements MouseInputListener {
	
	double L = 20.0;
	private IGizmoModel model;
	private IGBallView view;
	IGizmo triggerGizmo;
	IGizmo actionGizmo;
	
	public ConnectGizmoListener(IGizmoModel m, IGBallView v){
		model = m;
		view = v;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		double x = Math.floor(arg0.getX()/L);
		double y = Math.floor(arg0.getY()/L);
		
		if (triggerGizmo == null){
			for (IGizmo g: model.getGizmos()){
				for (Coordinate c : g.getOccupiedSquares()){
					if (c.x() == x && c.y() == y){
						triggerGizmo = g;
						break;
					}
				}
			}
		} else {
			for (IGizmo g: model.getGizmos()){
				for (Coordinate c : g.getOccupiedSquares()){
					if (c.x() == x && c.y() == y){
						actionGizmo = g;
						break;
					}
				}
			}
		}
		if (triggerGizmo != null && actionGizmo != null){
			model.connectGizmo(triggerGizmo, actionGizmo);
			triggerGizmo = null;
			actionGizmo = null;
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
	}
	
}
