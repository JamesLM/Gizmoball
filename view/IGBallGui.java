package view;

import java.util.List;

import javax.swing.*;

import controller.*;

public interface IGBallGui {
	
	public JPanel createButtons(IGBallListener listener);
	public JMenuBar createMenuBar(IGBallListener listener);
	public JFrame createFrame(IGBallListener listener);
	public String getSelectedItem();
	public String getSelectedFlipper();
	public String triggerAction();
	float getGravitySetting();
	float getMuSetting();
	float getBallXVelocitySetting();
	float getBallYVelocitySetting();

}