package dev.godxero.animate;

import javax.swing.*;
import java.awt.*;

public class CanvasPanel extends JPanel {
	private Drawer drawer;

	@Override
	protected void paintComponent (Graphics g) {
		super.paintComponent(g);
		drawer.draw(g);
	}

	public void init () {
		this.drawer = new Drawer(this);
	}

	public Drawer getDrawer () {
		return drawer;
	}
}
