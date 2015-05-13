package view;

import java.awt.Graphics;

public interface IBoard {

	public void paintComponent(Graphics g);
	public void eraseRubberband();
	public void rubberband(int x1, int y1, int x2, int y2);
}
