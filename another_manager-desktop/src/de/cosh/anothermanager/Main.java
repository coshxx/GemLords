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
		cfg.height = 1280;
		*/
		cfg.width = 640;
        cfg.height = 960;
        cfg.resizable = false;
        cfg.foregroundFPS = 100;

		new LwjglApplication(new AnotherManager(), cfg);
	}
}
