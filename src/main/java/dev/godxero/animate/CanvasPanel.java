package dev.godxero.animate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CanvasPanel extends JPanel {
	private Drawer drawer;

	@Override
	protected void paintComponent (Graphics g) {
		super.paintComponent(g);
		drawer.draw(g);
	}

	public void init () {
		this.drawer = new Drawer(this);

		this.addMouseListener(new MouseAdapter () {
			@Override
			public void mouseClicked (MouseEvent event) {
				drawer.addNewParticle(event.getX(), event.getY());
			}
		});
	}

	public Drawer getDrawer () {
		return drawer;
	}
}
