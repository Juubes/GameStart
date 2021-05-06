package com.juubes.gamestart;

import java.awt.Dimension;

import lombok.Getter;
import lombok.Setter;

public class GameSettings {

    @Getter
    @Setter
    private int targetFPS = 60;

    @Getter
    @Setter
    private int targetTPS = 20;

    @Getter
    @Setter
    private Dimension screenSize = new Dimension(1280, 720);
    
    @Getter
    @Setter
    private boolean screenBorders = true;

    @Getter
    @Setter
    private String title = "GameStart implementation";

}
