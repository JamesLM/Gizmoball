package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.Timer;
import javax.swing.event.MouseInputListener;

import model.*;
import view.*;

public class RunListener implements IGBallListener {
	
	private IGizmoModel model;
	private IGBallView view;
	private Timer timer;
	private int counter;
	
	public RunListener(IGizmoModel m, IGBallView v){
		model = m;
		view = v;
		timer = new Timer(50, this);		
	}

	@Override
	public final void actionPerformed(final ActionEvent e) {
		
		
		if (e.getSource() == timer) {
			model.runLogic();

		} else switch (e.getActionCommand()) {
		case "Start":
			timer.start();
			view.setFocus();
			break;
		case "Stop":
			timer.stop();
			break;
		case "Tick":
			model.moveBall();
			break;
		case "Build Mode":
			view.buildMode();
			timer.stop();
			break;
		case "Quit":
			System.exit(0);
			break;
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
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
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
		List<IAbsorber> abs = model.getAbsorbers();
		for (IAbsorber a : abs){
			if (a.getConnectKey() == e.getKeyCode()){
				a.fireBall();
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setMouseListener(MouseInputListener ml) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setKeyBoardListener(KeyListener kl) {
		// TODO Auto-generated method stub
		
	}
}
