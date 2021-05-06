package com.juubes.gamestart;

public abstract class GameHandler {
    /**
     * Initializes the gamesettings. Only called once.
     */
    public abstract void prepare();

    /**
     * Starts immidiately after {@link #prepare()}
     */
    public abstract void update();
}
