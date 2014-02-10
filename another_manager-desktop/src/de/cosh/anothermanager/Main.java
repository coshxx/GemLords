package de.cosh.anothermanager;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "another_manager";
		cfg.useGL20 = true;
		cfg.width = 518;
		cfg.height = 921;
        cfg.x = 300;
        cfg.y = 0;
		/*
		cfg.width = 900;
		cfg.height = 900;
		*/
		/*
		cfg.width = 432;
        cfg.height = 768;
        */
        cfg.resizable = true;
        cfg.foregroundFPS = 100;
        
		new LwjglApplication(new GemLord(), cfg);
	}
}
