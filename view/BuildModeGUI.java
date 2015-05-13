package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeListener;
import javax.swing.event.MouseInputListener;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.AbstractAction;

import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JToggleButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JInternalFrame;
import javax.swing.JList;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;

import java.awt.event.ActionListener;
import java.util.Hashtable;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JTree;

import model.IGizmoModel;
import controller.AddAbsorberListener;
import controller.AddBallListener;
import controller.AddFlipperListener;
import controller.AddGizmoListener;
import controller.ConnectGizmoListener;
import controller.DeleteGizmoListener;
import controller.IGBallListener;
import controller.KeyConnectGizmoListener;
import controller.MoveGizmoListener;
import controller.RotateGizmoListener;
import controller.SliderListener;

public class BuildModeGUI implements IGBallGui {

	private JPanel gameDisplayPanel;
	private IGizmoModel model;
	private IGBallView view;
	private IBoard buildBoard;
	private IGBallListener buildListener;
	private List<JButton> buttonList;

	
	// Menu Buttons
	JButton btnRunMode;
	JButton btnSave;
	JButton btnLoad;
	JButton btnQuit;
	
	// Panel Buttons
	JButton btnAddGizmo;
	JComboBox<String> gizmoSelectBox;
	JButton btnAddFlipper;
	JComboBox<String> flipperSelectBox;
	JButton btnAddAbsorber;
	JButton btnAddBall;
	JButton btnMoveGizmo;
	JButton btnRotateGizmo;
	JButton btnDeleteGizmo;
	JButton btnConnectKey;
	JRadioButton keyPress;
    JRadioButton keyRelease;
    ButtonGroup bg;
    
	JButton btnConnectGizmo;
	JButton btnClearBoard;
	
	// Sliders
	JSlider ballVelocityXSlider;
	JSlider ballVelocityYSlider;
	JSlider frictionSlider;
	JSlider gravSlider; 
	
	// labels
	JLabel lblFriction;
	JLabel lblBallXSpeed;
	JLabel lblBallYSpeed;
	JLabel lblGravity;
	
	JPanel buttons;
	
	// Listeners
	final MouseInputListener ag;
	final MouseInputListener lf; 
	final MouseInputListener ab;
	final MouseInputListener addBall;
	final MouseInputListener moveGizmoListener;
	final MouseInputListener rg;
	final MouseInputListener mousekc;
	final KeyListener keykc;
	final MouseInputListener dg;
	final MouseInputListener mv;
	final MouseInputListener connectGizmoListener;
	
	
	JMenuBar menuBar;
	private JFrame frame;
	
	/**
	 * Create the frame.
	 */
	public BuildModeGUI(IGizmoModel m, IGBallView v, IBoard board) {
		
		model = m;
		view = v;
		buildBoard = board;
		
		frame = new JFrame();
		frame.setBounds(100, 100, 1000, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		menuBar = new JMenuBar();
		
	
	
		// Listeners
		 ag = new AddGizmoListener(model, view);
		 lf = new AddFlipperListener(model, view);
		 ab = new AddAbsorberListener(model, view, buildBoard);
		 addBall = new AddBallListener(model, view);
		 moveGizmoListener = new MoveGizmoListener(model, view);
		 rg  = new RotateGizmoListener(model, view);
		 mousekc = new KeyConnectGizmoListener(model, view, this);
		 keykc = (KeyListener) mousekc;
		 dg = new DeleteGizmoListener(model, view);
		 mv = new DeleteGizmoListener(model, view);
		 connectGizmoListener = new ConnectGizmoListener(model, view);
	}
	
	public JPanel createButtons(IGBallListener listener) {
		
		buildListener = listener;
		buttons = new JPanel();
	
		/*
		 * MAIN GUI PANEL BUTTON INITIALISATION
		 */
		// ********* ADD GIZMO BUTTON *********
		btnAddGizmo = new JButton("Add Gizmo");
		//final MouseInputListener ag = new AddGizmoListener(model, view);
		btnAddGizmo.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				buildListener.setMouseListener(ag);
			}
		});
		buttons.add(btnAddGizmo);
		
		// ********* SELECT GIZMO COMBOBOX *********
		String[] gizmoNames = {"Square", "Triangle", "Circle"};
		gizmoSelectBox = new JComboBox<String>(gizmoNames);
		buttons.add(gizmoSelectBox);
		
		// ********* ADD FLIPPER BUTTON *********
		btnAddFlipper = new JButton("Add Flipper");
		//final MouseInputListener lf = new AddFlipperListener(model, view);
		btnAddFlipper.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				buildListener.setMouseListener(lf);
			}
		});
		buttons.add(btnAddFlipper);
		
		// ********* SELECT FLIPPER COMBOBOX *********
		String[] flipperNames = {"Left Flipper", "Right Flipper"};
		flipperSelectBox = new JComboBox<String>(flipperNames);
		buttons.add(flipperSelectBox);
		
		// ********* ADD ABSORBER BUTTON *********
		btnAddAbsorber = new JButton("Add Absorber");
		//final MouseInputListener ab = new AddAbsorberListener(model, view, buildBoard);
		btnAddAbsorber.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				buildListener.setMouseListener(ab);
			}
		});
		buttons.add(btnAddAbsorber);
		
		// ********* ADD BALL BUTTON *********
		btnAddBall = new JButton("Add Ball");
		btnAddBall.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				buildListener.setMouseListener(addBall);
			}
		});
		buttons.add(btnAddBall);

		// ********* MOVE GIZMO BUTTON *********
		btnMoveGizmo = new JButton("Move Gizmo");
		btnMoveGizmo.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				buildListener.setMouseListener(moveGizmoListener);
			}
		});
		buttons.add(btnMoveGizmo);	
		
		// ********* ROTATE GIZMO BUTTON *********
		btnRotateGizmo = new JButton("Rotate");
		btnRotateGizmo.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				buildListener.setMouseListener(rg);
			}
		});
		buttons.add(btnRotateGizmo);
		
		// ********* CONNECT KEY BUTTON *********
		btnConnectKey = new JButton("Connect Key");
		btnConnectKey.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				buildListener.setMouseListener(mousekc);
				buildListener.setKeyBoardListener(keykc);
			}
		});
		buttons.add(btnConnectKey);
		
		// ********* CONNECT KEY RADIO BUTTONS *********
		JPanel temp = new JPanel();
		keyPress = new JRadioButton("Key Press");
	    keyRelease = new JRadioButton("key Release");
	    bg = new ButtonGroup();
	    bg.add(keyPress);
	    bg.add(keyRelease);
	    
	    keyPress.setActionCommand("keyPress");
	    keyRelease.setActionCommand("keyRelease");
	    
	    temp.add(keyPress);
	    temp.add(keyRelease);
		
	    buttons.add(temp);
		
		// CONNECT GIZMO BUTTON
	    btnConnectGizmo = new JButton("Connect Gizmo");
	    btnConnectGizmo.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				buildListener.setMouseListener(connectGizmoListener);
			}
		});
		buttons.add(btnConnectGizmo);
		
		// DELETE GIZMO BUTTON
		btnDeleteGizmo = new JButton("Delete Gizmo");

		btnDeleteGizmo.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				buildListener.setMouseListener(dg);
			}
		});
		buttons.add(btnDeleteGizmo);
		
		// **************************************
		// ************** SLIDERS ***************
		// **************************************
		// Simple Slider Listener
		ChangeListener changeListener = new SliderListener(model, this);
		
		// ********* BALL SPEED SLIDER **********
		
		
		ballVelocityXSlider = new JSlider(JSlider.HORIZONTAL, -200, 200, 50);
		ballVelocityXSlider.addChangeListener(changeListener);
		ballVelocityXSlider.setMajorTickSpacing(100);
		ballVelocityXSlider.setMinorTickSpacing(5);
		ballVelocityXSlider.setPaintLabels(true);
		
		buttons.add(ballVelocityXSlider);
		
		ballVelocityYSlider = new JSlider(JSlider.HORIZONTAL, -200, 200, 50);
		ballVelocityYSlider.addChangeListener(changeListener);
		ballVelocityYSlider.setMajorTickSpacing(100);
		ballVelocityYSlider.setMinorTickSpacing(5);
		ballVelocityYSlider.setPaintLabels(true);
		
		buttons.add(ballVelocityYSlider);
		
		lblBallXSpeed = new JLabel("Ball X Speed:", SwingConstants.CENTER);
		buttons.add(lblBallXSpeed);
		lblBallYSpeed = new JLabel("Ball Y Speed:", SwingConstants.CENTER);
		buttons.add(lblBallYSpeed);

		
		
		// ********* FRICTION SLIDER **********
		
		
		frictionSlider = new JSlider(JSlider.HORIZONTAL, 0, 60, 25);
		frictionSlider.addChangeListener(changeListener);
		frictionSlider.setMajorTickSpacing(250);
		frictionSlider.setMinorTickSpacing(250);
		frictionSlider.setPaintLabels(true);
		Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();
		labelTable.put(new Integer(0), new JLabel("0.0"));
		labelTable.put(new Integer(15), new JLabel("0.015"));
		labelTable.put(new Integer(30), new JLabel("0.03"));
		labelTable.put(new Integer(45), new JLabel("0.045"));
		labelTable.put(new Integer(60), new JLabel("0.06"));
		frictionSlider.setLabelTable(labelTable);
		buttons.add(frictionSlider);
		
		
		// ********* GRAVITY SLIDER **********
		
		
		gravSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
		gravSlider.addChangeListener(changeListener);
		gravSlider.setMajorTickSpacing(20);
		gravSlider.setMinorTickSpacing(10);
		gravSlider.setPaintLabels(true);
		buttons.add(gravSlider);
		
		lblFriction = new JLabel("Friction Rate:", SwingConstants.CENTER);
		buttons.add(lblFriction);
		
		lblGravity = new JLabel("Gravity:", SwingConstants.CENTER);
		buttons.add(lblGravity);
	    
		return buttons;
	}
	
	/**
	 * Return the Menu Bar used for Build Mode.
	 */
	@Override
	public JMenuBar createMenuBar(IGBallListener listener) {
	/*
	* Menu bar gets added to in here.
	*/
		
		btnRunMode = new JButton("Run Mode");
		btnRunMode.addActionListener(listener);
		btnRunMode.setActionCommand("Run Mode");
		menuBar.add(btnRunMode);
		
		btnSave = new JButton("Save Board");
		btnSave.addActionListener(listener);
		btnSave.setActionCommand("Save Game");
		menuBar.add(btnSave);
		
		btnLoad = new JButton("Load Board");
		btnLoad.addActionListener(listener);
		btnLoad.setActionCommand("Load Game");
		menuBar.add(btnLoad);
		
		btnClearBoard = new JButton("Clear Board");
		btnClearBoard.addActionListener(listener);
		btnClearBoard.setActionCommand("Clear Board");
		menuBar.add(btnClearBoard);
		
		btnQuit = new JButton("Quit");
		btnQuit.addActionListener(listener);
		btnQuit.setActionCommand("Quit");
		menuBar.add(btnQuit);
		
		JButton menuDeleteGizmo = new JButton("Delete Gizmo");
		menuDeleteGizmo.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				buildListener.setMouseListener(dg);
			}
		});
		menuBar.add(menuDeleteGizmo);
		
		return menuBar;
	}
	
	/**
	 * Return the Message for Build Mode.
	 */

	@Override
	public JFrame createFrame(IGBallListener listener) {
		return frame;
	}

	@Override
	public String getSelectedItem() {
		return (String) gizmoSelectBox.getSelectedItem();
	}

	@Override
	public String getSelectedFlipper() {
		return (String) flipperSelectBox.getSelectedItem();
	}

	@Override
	public String triggerAction() {
		return bg.getSelection().getActionCommand();
	}
	
	@Override
	public float getGravitySetting(){
		return gravSlider.getValue();
	}
	
	@Override
	public float getMuSetting(){
		return frictionSlider.getValue();
	}
	
	@Override 
	public float getBallXVelocitySetting(){
		return ballVelocityXSlider.getValue();
	}
	@Override 
	public float getBallYVelocitySetting(){
		return ballVelocityYSlider.getValue();
	}

}
