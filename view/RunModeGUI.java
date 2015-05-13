package view;

import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import java.awt.CardLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JTextField;
import javax.swing.BoxLayout;

import java.awt.FlowLayout;

import javax.swing.JLabel;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JTextArea;
import javax.swing.JMenuBar;
import javax.swing.JTextPane;

import controller.IGBallListener;
import model.*;

public class RunModeGUI implements IGBallGui {

	
	private JPanel gameDisplayPanel;

	private IGizmoModel model;
	
	private IGBallListener runListener;
	
	private List<JButton> buttonList;
	
	
	JButton btnRun;
	JButton btnStop;
	JButton btnTick;
	JButton btnBuildMode;
	JButton btnQuit;
	
	JMenuBar menuBar;
	
	JTextArea textArea;
	
	private JFrame frame;
	
	public RunModeGUI(IGizmoModel m) {
		
		model = m;

		frame = new JFrame();
		frame.setBounds(100, 100, 1000, 1000);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		menuBar = new JMenuBar();
		
		textArea = new JTextArea();
		//textArea.setBounds(0, 319, 544, 22);
		frame.getContentPane().setLayout(null);
		//frame.getContentPane().add(textArea);
		
		gameDisplayPanel = new JPanel();
		gameDisplayPanel.setBounds(234, 8, 300, 300);
	}

	/**
	 * Method returns a list of buttons necessary for Run Mode.
	 */
	@Override
	public JPanel createButtons(IGBallListener listener) {
		
		runListener = listener;
		
		JPanel buttons = new JPanel();
		buttons.setLayout(new GridLayout(4,2));
		
		btnRun = new JButton("Start");
		btnRun.addActionListener(runListener);
		buttons.add(btnRun);
		
		btnStop = new JButton("Stop");
		btnStop.addActionListener(runListener);
		buttons.add(btnStop);
		
		btnTick = new JButton("Tick");
		btnTick.addActionListener(runListener);
		buttons.add(btnTick);
		
		btnBuildMode = new JButton("Build Mode");
		btnBuildMode.addActionListener(runListener);
		buttons.add(btnBuildMode);
		
		btnQuit = new JButton("Quit");
		btnQuit.addActionListener(listener);
		btnQuit.setActionCommand("Quit");
		
		buttons.validate();
		
		return buttons;
	}
	
	/**
	 * Return the Menu Bar used for Run Mode.
	 */
	@Override
	public JMenuBar createMenuBar(IGBallListener listener) {
		

		menuBar.add(btnBuildMode);
		menuBar.add(btnQuit);
		
		return menuBar;
	}
	
	/**
	 * Return the Message for Run Mode.
	 */
	@Override
	public JFrame createFrame(IGBallListener listener) {
		return frame;
	}

	@Override
	public String getSelectedItem() {
		return null;
	}

	@Override
	public String getSelectedFlipper() {
		return null;
	}

	@Override
	public String triggerAction() {
		return null;
	}

	@Override
	public float getGravitySetting() {
		return 0;
	}

	@Override
	public float getMuSetting() {
		return 0;
	}

	@Override
	public float getBallXVelocitySetting() {
		return 0;
	}

	@Override
	public float getBallYVelocitySetting() {
		return 0;
	}
}
