package com.juubes.gamestart;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public abstract class Renderer {
	private final GameStart game;
	private final JFrame frame;
	private final Canvas canvas;
	private BufferStrategy bs;

	public Renderer(GameStart game) {
		this.game = game;

		this.frame = new JFrame("GAI v1.0 by Juubes");
		this.frame.setSize(game.getSettings().screenSize);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setUndecorated(!game.getSettings().screenBorders);

		this.canvas = new Canvas();
		this.canvas.setSize(game.getSettings().screenSize);
		this.canvas.setBackground(Color.BLACK);

		this.frame.add(canvas);
	}

	/**
	 * Prepares the screen for rendering. Creates the bufferstrategy.
	 */
	void prepare() {
		this.frame.setVisible(true);
		this.canvas.createBufferStrategy(2);
		this.bs = canvas.getBufferStrategy();

		this.frame.addKeyListener(game.getInputListener());
		this.canvas.addKeyListener(game.getInputListener());

		this.frame.addMouseListener(game.getInputListener());
		this.canvas.addMouseListener(game.getInputListener());

		this.canvas.requestFocus();
	}

	/**
	 * Draws to the screen the provided graphics.
	 */
	public abstract void render(Graphics2D g);

	void fullRender() {
		Graphics2D g = (Graphics2D) bs.getDrawGraphics();

		this.render(g);

		g.dispose();
		bs.show();
	}

	public Canvas getCanvas() {
		return canvas;
	}
}
