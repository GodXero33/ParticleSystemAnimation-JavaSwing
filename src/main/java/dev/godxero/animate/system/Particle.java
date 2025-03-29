package dev.godxero.animate.system;

import lombok.AllArgsConstructor;

import java.awt.*;

@AllArgsConstructor
public class Particle {
	public Vector position;
	public Vector velocity;
	public Vector acceleration;
	public int size;
	public Color color;

	public void draw (Graphics g) {
		g.setColor(this.color);
		g.fillArc((int) this.position.x, (int) this.position.y, this.size, this.size, 0, 360);
	}

	public void update (int w, int h, double friction, int subSteps) {
		friction /= subSteps;

		this.velocity.x += this.acceleration.x / subSteps;
		this.velocity.y += this.acceleration.y / subSteps;

		this.position.x += this.velocity.x / subSteps;
		this.position.y += this.velocity.y / subSteps;

		if (this.position.x < 0) {
			this.position.x = 0;
			this.velocity.x *= friction - 1;
		}

		if (this.position.x + this.size > w) {
			this.position.x = w - (double) this.size;
			this.velocity.x *= friction - 1;
		}

		if (this.position.y < 0) {
			this.position.y = 0;
			this.velocity.y *= friction - 1;
		}

		if (this.position.y + this.size > h) {
			this.position.y = h - (double) this.size;
			this.velocity.y *= friction - 1;
		}
	}
}
