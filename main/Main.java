package main;

import java.awt.EventQueue;
import view.GBallView;
import model.AGizmoModel;
import model.GizmoModel;

public class Main {

	static AGizmoModel modelGizmo = new GizmoModel();

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {

			public void run() {
				GBallView window = new GBallView(modelGizmo);

			}
		});
	}

	
}
