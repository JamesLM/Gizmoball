package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import model.IGizmoModel;

public class GizRunKeyListener implements KeyListener {

	private IGizmoModel model;
	
	public GizRunKeyListener(IGizmoModel m){
		model = m;
	}
	@Override
	public void keyPressed(KeyEvent arg0) {
		model.keyPress(arg0.getKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		model.keyReleased(arg0.getKeyCode());
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}

}
