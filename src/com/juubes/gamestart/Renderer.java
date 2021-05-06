package com.juubes.gamestart;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public abstract class Renderer {
    protected final GameStart game;
    private final JFrame frame;
    private final Canvas canvas;
//    private BufferStrategy bufferStrategy;

    public Renderer(GameStart game) {
	this.game = game;

	this.frame = new JFrame(game.getSettings().getTitle());
	this.frame.setSize(game.getSettings().getScreenSize());
	this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	this.frame.setUndecorated(!game.getSettings().isScreenBorders());
	this.frame.setLocationRelativeTo(null);

	this.canvas = new Canvas();
	this.canvas.setSize(game.getSettings().getScreenSize());
	this.canvas.setBackground(Color.BLACK);

	this.frame.add(canvas);

	this.frame.pack();
	this.frame.setVisible(true);
    }

    /**
     * Prepares the screen for rendering. Creates the bufferstrategy.
     */
    void prepare() {
	this.frame.setVisible(true);

	this.frame.addKeyListener(game.getInputHandler());
	this.canvas.addKeyListener(game.getInputHandler());

	this.frame.addMouseListener(game.getInputHandler());
	this.canvas.addMouseListener(game.getInputHandler());

	this.canvas.requestFocus();
    }

    /**
     * Draws to the screen the provided graphics.
     */
    public abstract void render(Graphics2D g);

    void fullRender() {
	BufferStrategy bufferStrategy = canvas.getBufferStrategy();
	if (bufferStrategy == null) {
	    this.canvas.createBufferStrategy(2);
	    bufferStrategy = canvas.getBufferStrategy();
	}

	Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();

	this.render(g);

	g.dispose();
	bufferStrategy.show();
    }

    public Canvas getCanvas() {
	return canvas;
    }
}
