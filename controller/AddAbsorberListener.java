package controller;

import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputListener;

import view.IBoard;
import view.IGBallView;
import model.IGizmoModel;

public class AddAbsorberListener implements MouseInputListener{
	
	private IGizmoModel model;
	private IGBallView view;
	private IBoard board;
	double L = 20.0;
	private int x1;
	private int y1;
	
	public AddAbsorberListener(IGizmoModel m, IGBallView v, IBoard b){
		model = m;
		view = v;
		board = b;
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		/* get top left corner
		 * 
		 */
		x1 = (int)(e.getX() / L);
		y1 = (int)(e.getY() / L);
		board.rubberband(x1, y1, x1, y1);
	}
	

	@Override
	public void mouseDragged(MouseEvent e) {
		/* - get mouse bottom right corner
		 * - erase the current rubber band
		 * - draw the new rubberband
		 */
		
		int xtemp = (int) Math.ceil(e.getX() / L);
		int ytemp = (int) Math.ceil(e.getY() / L);

		board.eraseRubberband();
		board.rubberband(x1, y1, xtemp, ytemp);
		
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		int xtemp = (int) Math.ceil(e.getX() / L);
		int ytemp = (int) Math.ceil(e.getY() / L);
		
		System.out.println("xtemp: " + xtemp);
		System.out.println("ytemp: " + ytemp);
		model.createAbsorber("ab"+x1+y1, x1, y1, xtemp, ytemp);
		board.eraseRubberband();
		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
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
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
