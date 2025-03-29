package dev.godxero.animate;

import dev.godxero.animate.system.Particle;
import dev.godxero.animate.system.Vector;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Drawer {
	private final CanvasPanel canvas;
	private final List<Particle> particles;
	private final double gravity;
	private final double friction;

	public Drawer (CanvasPanel canvas) {
		this.canvas = canvas;
		this.particles = new ArrayList<>();
		this.gravity = 0.0;
		this.friction = 0.0;

		this.generateParticles(this.canvas.getWidth(), this.canvas.getHeight());
	}

	private void generateParticles (int width, int height) {
		for (int a = 0; a < 100; a++) {
			this.particles.add(new Particle(
				new Vector((Math.random() * width), (Math.random() * height)),
				new Vector((Math.random() * 2 - 1), (Math.random() * 2 - 1)),
				new Vector(0, this.gravity),
				(int) (Math.random() * 5 + 5),
				Color.WHITE
			));
		}
	}

	public void draw (Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		this.particles.forEach(particle -> particle.draw(g));
	}

	public void update () {
		int width = canvas.getWidth();
		int height = canvas.getHeight();
		final int subSteps = 10;

		this.particles.forEach(particle -> particle.update(width, height, this.friction, 1));

		for (int step = 0; step < subSteps; step++) {
			this.particles.forEach(particle1 -> this.particles.forEach(particle2 -> {
				if (particle1 == particle2) return;

				final double dx = particle1.position.x - particle2.position.x;
				final double dy = particle1.position.y - particle2.position.y;
				final double disSquared = dx * dx + dy * dy;
				final double radius = (particle1.size + particle2.size) * 0.5;

				if (disSquared < radius * radius) {
					final double angle = Math.atan2(dy, dx);
					final double distance = Math.sqrt(disSquared);
					final double overlap = Math.max(0, (radius - distance) / 2);

					particle1.position.x += Math.cos(angle) * overlap;
					particle1.position.y += Math.sin(angle) * overlap;
					particle2.position.x -= Math.cos(angle) * overlap;
					particle2.position.y -= Math.sin(angle) * overlap;

					final double v1x = particle1.velocity.x;
					final double v1y = particle1.velocity.y;
					final double v2x = particle2.velocity.x;
					final double v2y = particle2.velocity.y;

					final double mass1 = particle1.size;
					final double mass2 = particle2.size;

					final double v1FinalX = ((mass1 - mass2) * v1x + 2 * mass2 * v2x) / (mass1 + mass2);
					final double v1FinalY = ((mass1 - mass2) * v1y + 2 * mass2 * v2y) / (mass1 + mass2);
					final double v2FinalX = ((mass2 - mass1) * v2x + 2 * mass1 * v1x) / (mass1 + mass2);
					final double v2FinalY = ((mass2 - mass1) * v2y + 2 * mass1 * v1y) / (mass1 + mass2);

					particle1.velocity.x = v1FinalX;
					particle1.velocity.y = v1FinalY;
					particle2.velocity.x = v2FinalX;
					particle2.velocity.y = v2FinalY;
				}
			}));
		}
	}
}
