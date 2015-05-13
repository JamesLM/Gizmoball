package controller;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import view.BuildModeGUI;
import view.IGBallGui;
import model.IGizmoModel;

public class SliderListener implements ChangeListener {
	
	private IGizmoModel model;
	private IGBallGui gui;
	
	public SliderListener(IGizmoModel m, IGBallGui g){
		model = m;
		gui = g;
	}
	@Override
	public void stateChanged(ChangeEvent e) {
		model.setGravity(gui.getGravitySetting());
		model.setFriction(gui.getMuSetting()/1000, gui.getMuSetting()/1000);
		model.setBallSpeed(gui.getBallXVelocitySetting(), gui.getBallYVelocitySetting());
		System.out.println("ball new speed x: " + gui.getBallXVelocitySetting());
		System.out.println("ball new speed y: " + gui.getBallYVelocitySetting());
		System.out.println("mu: " + gui.getMuSetting()/1000);
		System.out.println("grav: " + gui.getGravitySetting());
	}

}