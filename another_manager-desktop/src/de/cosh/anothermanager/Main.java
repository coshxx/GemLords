package de.cosh.anothermanager;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "another_manager";
		cfg.useGL20 = true;
		cfg.width = 1024;
		cfg.height = 640;
		
		new LwjglApplication(new AnotherManager(), cfg);
	}
}
