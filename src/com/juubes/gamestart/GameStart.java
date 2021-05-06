package com.juubes.gamestart;

public class GameStart {
    private final GameSettings settings;

    protected GameHandler gameHandler;
    protected Renderer renderer;
    protected Input inputListener;

    public GameStart(GameSettings settings) {
	this.settings = settings;
    }

    /**
     * Blocking method, the main gamethread.
     */
    public void start(Renderer rendererImpl, GameHandler gameHandlerImpl, Input inputListener) {
	this.renderer = rendererImpl;
	this.gameHandler = gameHandlerImpl;
	this.inputListener = inputListener;
	this.gameHandler.prepare();
	this.renderer.prepare();

	while (true) {
	    long tickStartTime = System.nanoTime();
	    this.gameHandler.update();
	    long tickTime = System.nanoTime() - tickStartTime;
	    this.renderer.fullRender();

	    // Becomes negative when dropping frames
	    double timeLeft = (1000d / settings.getTargetTPS()) - tickTime / 1E9;
	    try {
		if (timeLeft > 0)
		    Thread.sleep((long) timeLeft);
	    } catch (InterruptedException e) {
	    }
	}
    }

    public GameHandler getGameHandler() {
	return gameHandler;
    }

    public Renderer getRenderer() {
	return renderer;
    }

    public int getWidth() {
	return renderer.getCanvas().getWidth();
    }

    public int getHeight() {
	return renderer.getCanvas().getHeight();
    }

    public Input getInputListener() {
	return inputListener;
    }

    public GameSettings getSettings() {
	return settings;
    }

}
