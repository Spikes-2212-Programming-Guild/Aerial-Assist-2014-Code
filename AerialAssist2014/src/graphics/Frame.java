package graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import physics.Scalar;
import physics.Util;
import physics.Vector;

public class Frame extends JFrame {

	static Sun sun = new Sun(new Vector(0, 0, Pixel.get()));
	static Earth earth = new Earth(sun, Util.forCircularMovement(sun,
			Constants.EARTH_ROUND_RADIUS), 0,
			Rotation.CLOCKWISE);
	static Moon moon = new Moon(earth, Util.forCircularMovement(earth,
			Constants.MOON_ROUND_RADIUS)
			.add(earth.getVelocity().getMagnitude()), 0, Rotation.CLOCKWISE);

	private static final long serialVersionUID = 1L;

	Vector focus;

	public Frame(final PhysicalSystem system) {
		setBackground(Color.DARK_GRAY);
		add(new JPanel() {
			{
				InputMap imap = getInputMap();
				imap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0),
						"move right");
				imap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0),
						"move left");
				ActionMap amap = getActionMap();
				amap.put("move right", new AbstractAction() {

					@Override
					public void actionPerformed(ActionEvent e) {
						focus = focus.add(new Vector(-10, 0, Pixel.get()));
					}
				});
				amap.put("move left", new AbstractAction() {

					@Override
					public void actionPerformed(ActionEvent e) {
						focus = focus.add(new Vector(10, 0, Pixel.get()));
					}
				});
			}

			@Override
			public void paint(Graphics g) {
				system.draw(g, focus);
				g.setColor(Color.WHITE);
				g.drawString(
						"Vest = "
								+ Scalar.sqrt(moon.getAcceleration()
										.multiply(moon.distance(earth))
										.getMagnitude()), 10, 30);
				g.drawString("V = " + moon.getVelocity().getMagnitude(), 10, 40);
				g.drawString("A = " + moon.getAcceleration().getMagnitude(),
						10, 50);
				g.drawString("R = " + moon.distance(earth), 10, 60);
				g.drawString(
						"X = " + moon.getCenter().getX().convert(Pixel.get()),
						10, 70);
				g.drawString(
						"Y = " + moon.getCenter().getY().convert(Pixel.get()),
						10, 80);
				g.drawString("Ek = " + moon.getKinecticEnergy(), 10, 90);

			}
		});
		setSize(Toolkit.getDefaultToolkit().getScreenSize());
		focus = new Vector(getWidth() / 2, getHeight() / 2, Pixel.get());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	public static void main(String[] args) throws InterruptedException {
		PhysicalSystem s = new PhysicalSystem(sun, earth, moon);
		Frame f = new Frame(s);
		long lastMove = System.nanoTime();
		while (true) {
			long now = System.nanoTime();
			s.move(Scalar.NANOSECOND.multiply(now - lastMove));
			lastMove = now;
			s.applyGravityForces();
			f.repaint();

		}

	}
}
