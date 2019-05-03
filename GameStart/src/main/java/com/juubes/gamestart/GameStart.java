package com.juubes.gamestart;

public class GameStart {
	private final GameSettings settings;

	protected GameHandler gameHandler;
	protected Renderer renderer;
	protected Input inputListener;

	private int lastFPS;

	public GameStart(GameSettings settings) {
		this.settings = settings;
	}

	/**
	 * Blocking method, the main gamethread.
	 */
	public void begin(Renderer rendererImpl, GameHandler gameHandlerImpl, Input inputListener) {
		this.renderer = rendererImpl;
		this.gameHandler = gameHandlerImpl;
		this.inputListener = inputListener;
		this.gameHandler.prepare();
		this.renderer.prepare();

		while (true) {
			long frameTimeStart = System.nanoTime();
			this.gameHandler.update();
			this.renderer.fullRender();
			long frameTime = System.nanoTime() - frameTimeStart;

			// Becomes negative when dropping frames
			double time = Math.max(0, (1000d / settings.targetFPS) - (frameTime / 1E9));

			try {
				Thread.sleep((long) time);
			} catch (InterruptedException e) {
			}
			lastFPS = (int) (1000d / time);
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

	public int getCurrentFPS() {
		return lastFPS;
	}

	public GameSettings getSettings() {
		return settings;
	}

}
