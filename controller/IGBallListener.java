package controller;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowListener;

import javax.swing.event.MouseInputListener;

public interface IGBallListener extends MouseInputListener, MouseMotionListener, KeyListener, ActionListener, WindowListener{
	
	public void setKeyBoardListener(KeyListener kl);
	public void setMouseListener(MouseInputListener ml);
	
}