package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.*;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import model.AGizmoModel;
import model.Absorber;
import model.GizmoModel;
import model.IGizmoModel;
import controller.*;


public class GBallView implements IGBallView{
	
	IGBallGui buildModeGui;
	IGBallGui runModeGui;
	
	
	JMenuBar bar;
	
	public JFrame frame;
	JFrame runFrame;
	JFrame buildFrame;
	
	IGizmoModel model;
	
	IGBallListener listener;
	IGBallListener runListener;
	IGBallListener buildListener;

	private JMenuBar runBar;
	private JMenuBar buildBar;
	private JPanel buttons;
	private JPanel runButtons;
	private JPanel buildButtons;
	
	JPanel board;
	JPanel runBoard;
	IBoard buildBoard;
	JComboBox gizmoSelectBox;
	JComboBox flipperSelectBox;
	JButton btnRun;
	JButton btnStop;
	JButton btnTick;
	JButton btnBuildMode;
	JButton btnQuit;
	
	KeyListener magickeylistener;
	
	public GBallView(AGizmoModel modelGizmo){
		
		//createBuildGui();
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		runBoard = new RunBoard(400, 400, modelGizmo);
		buildBoard = new BuildBoard(400, 400, modelGizmo);

		model = modelGizmo;
		createRunGui();
		createBuildGui();
		
		// Load the game initially in Build Mode
		buildMode();
		

		frame.addWindowListener(listener);
		//frame.addKeyListener(listener);
		frame.add(buttons);
		frame.add(board);
		
		magickeylistener = new MagicKeyListener(new GizRunKeyListener(model));

	}


	private void createBuildGui() {
		buildModeGui = new BuildModeGUI(model, this, buildBoard);
		buildListener = new BuildListener(model, this);
		
		buildButtons = buildModeGui.createButtons(buildListener);
		buildBar = buildModeGui.createMenuBar(buildListener);
		buildFrame = buildModeGui.createFrame(buildListener);
		buildFrame.addKeyListener(new MagicKeyListener(buildListener));
	}

	

	// This method simply sets the focus to the frame. It is invoked
	// from the controller when the user clicks start. We always want
	// focus on the frame immediately after 'start'.
	@Override
	public void setFocus() {
		frame.requestFocus();
	}
	private void createRunGui() {
		runModeGui = new RunModeGUI(model);
		runListener = new RunListener(model, this);
		
		// Get the Run Buttons and Run Bar
		runButtons = runModeGui.createButtons(runListener);
		runBar = runModeGui.createMenuBar(runListener);
		runFrame = runModeGui.createFrame(runListener);
		
		/********************************************
		 * ***** DECORATOR PATTERN CODE EXAMPLE *****
		 * ******************************************
		 * This code assigns the MagicKeyListener to the frame and 
		 * takes the actual listener we want to use as the argument.
		 * Use this approach when assigning listeners to keys! (I think)
		 * 	- James M.
		 */
		runFrame.addKeyListener(new MagicKeyListener(new GizRunKeyListener(model)));

	}
	

	
	@Override
	public void buildMode() {
		board = (JPanel) buildBoard;
		buttons = buildButtons;
		buttons.setPreferredSize(new Dimension(400, 400));
		bar = buildBar;
		listener = buildListener;
		
		buttons.setLayout(new GridLayout(10,2));
		frame.getContentPane().removeAll();
		frame.getContentPane().setPreferredSize(new Dimension(800, 400));
		frame.addWindowListener(listener);
		board.addMouseListener(listener);
		board.addMouseMotionListener(listener);

		frame.addKeyListener(listener);
		frame.add(buttons);
		frame.add(board);
		frame.setResizable(false);
		buttons.validate();
		buttons.addKeyListener(listener);
		buttons.setFocusable(true);
		frame.setJMenuBar(bar);
		
		frame.setLayout(new GridLayout(1,2));
		
		frame.setFocusable(true);
		frame.pack();
		frame.setVisible(true);
		
		board.repaint();
		
	}
	/*
	 * Switch to Run Mode.
	 *  When a user clicks run mode, this method should run and switch everything
	 *  we need to switch.
	 */
	@Override
	public void runMode() {
		frame.removeKeyListener(listener);
		bar = runBar;
		board = runBoard;
		listener = runListener;
		buttons = runButtons;
		frame.getContentPane().removeAll();
		frame.addWindowListener(listener);
		//frame.addKeyListener(new MagicKeyListener(new GizRunKeyListener(model)));
		frame.addKeyListener(magickeylistener);
		frame.add(buttons);
		frame.add(board);
		board.setBackground(Color.black);
		buttons.setLayout(new GridLayout(4,2));

		buttons.validate();
		buttons.setFocusable(true);
		frame.setJMenuBar(bar);
		
		frame.setLayout(new GridLayout(1,2));

		frame.setFocusable(true);
		frame.pack();
		frame.setVisible(true);
	}


	@Override
	public String getSelectedGizmo() {
		return (String) buildModeGui.getSelectedItem();
	}
	
	@Override
	public String getSelectedFlipper(){
		return (String) buildModeGui.getSelectedFlipper();
	}


	@Override
	public String triggerAction() {
		// TODO Auto-generated method stub
		return null;
	}
}