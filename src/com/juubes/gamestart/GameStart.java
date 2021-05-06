package com.juubes.gamestart;

import java.awt.Toolkit;

import lombok.Getter;
import lombok.Setter;

public class GameStart {
    @Getter
    private final GameSettings settings;

    @Getter
    @Setter
    protected GameHandler gameHandler;

    @Getter
    @Setter
    protected Renderer renderer;

    @Getter
    @Setter
    protected Input inputHandler;

    protected Thread gameLoopThread, renderingThread;

    public GameStart(GameSettings settings) {
	this.settings = settings;
    }

    /**
     * Blocking method, the main gamethread.
     */
    public void start() {
	if (renderer == null) {
	    System.err.println("Renderer class not implemented");
	    System.exit(1);
	}

	if (gameHandler == null) {
	    System.err.println("GameHandler class not implemented");
	    System.exit(1);
	}

	if (inputHandler == null) {
	    System.err.println("InputListener class not implemented");
	    System.exit(1);
	}

	this.gameHandler.prepare();
	this.renderer.prepare();

	gameLoopThread = new Thread(() -> {
	    while (true) {
		long tickStartTime = System.nanoTime();
		this.gameHandler.update();
		long tickTime = System.nanoTime() - tickStartTime;

//		System.out.println("TickTime = " + tickTime);

		// Becomes negative when dropping frames
		double timeLeft = (1000d / settings.getTargetTPS()) - tickTime / 1E9;
		try {
		    if (timeLeft > 0)
			Thread.sleep((long) timeLeft);
		} catch (InterruptedException e) {
		}
	    }
	});
	renderingThread = new Thread(() -> {
	    while (true) {
		long renderStartTime = System.nanoTime();
		this.renderer.fullRender();
		long renderDurationNanos = System.nanoTime() - renderStartTime;

		// Becomes negative when dropping frames
		long timeLeft = (1000L / settings.getMaxFPS()) - renderDurationNanos / (long) 1E6;

		System.out.println("Render duration: " + renderDurationNanos / 1E6 + "ms");

//		try {
//		    if (timeLeft > 0)
//			Thread.sleep((long) timeLeft);
//		} catch (InterruptedException e) {
//		}
	    }
	});

	gameLoopThread.start();
	renderingThread.start();
    }

    public int getWidth() {
	return renderer.getCanvas().getWidth();
    }

    public int getHeight() {
	return renderer.getCanvas().getHeight();
    }
}
