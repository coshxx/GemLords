package de.cosh.anothermanager;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "another_manager";
		cfg.useGL20 = true;
		/*
		cfg.width = 720;
		cfg.height = 1000;
		*/
		cfg.width = 480;
        cfg.height = 720;
        cfg.resizable = true;
        cfg.foregroundFPS = 100;
        
		new LwjglApplication(new AnotherManager(), cfg);
	}
}
