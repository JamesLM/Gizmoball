package controller;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.JFileChooser;
import javax.swing.JTextArea;
import javax.swing.event.MouseInputListener;

import model.IGizmoModel;

import view.IGBallView;

public class BuildListener implements IGBallListener {

	MouseInputListener mouseListener;
	KeyListener keyListener;
	private IGizmoModel model;
	private IGBallView view;

	
	JTextArea log;
	static private final String newline = "\n";
	
	public BuildListener(IGizmoModel m, IGBallView v){
		model = m;
		view = v;
	}
	
	@Override
	public final void actionPerformed(final ActionEvent e) {
		
		if (e.getActionCommand().equals("Run Mode")){
			view.runMode();
		} else switch (e.getActionCommand()){
		case "Clear Board":
			model.deleteAllGizmos();
			break;
		case "Save Game":
			model.saveBoard();
			break;
		case "Load Game":
			try {
				model.loading();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			break;
		case "Quit":
			System.exit(0);
			break;
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		mouseListener.mouseClicked(arg0);
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
		mouseListener.mousePressed(arg0);
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		mouseListener.mouseReleased(arg0);
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		mouseListener.mouseDragged(arg0);
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		keyListener.keyPressed(arg0);
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		keyListener.keyReleased(arg0);
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		keyListener.keyTyped(arg0);
	}

	@Override
	public void setKeyBoardListener(KeyListener kl) {
		keyListener = kl;
	}

	@Override
	public void setMouseListener(MouseInputListener ml) {
		mouseListener = ml;
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
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
}
