package de.cosh.gemlords;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class GemLordDesktop implements IActivityRequestHandler {
    private static GemLordDesktop application;
    public static void main(String[] args) {

        if( application == null ) {
            application = new GemLordDesktop();
        }
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "Gem Lords";
        cfg.width = 518;
        cfg.height = 921;
        cfg.x = 300;
        cfg.y = 0;
        cfg.foregroundFPS = 0;
        cfg.useGL20 = true;
        cfg.vSyncEnabled = true;
		/*
		cfg.width = 900;
		cfg.height = 900;
		*/
		/*
		cfg.width = 432;
        cfg.height = 768;
        */
        cfg.resizable = true;

        new LwjglApplication(new GemLord(application), cfg);
    }

    @Override
    public void showAds(boolean show) {
        // nope
    }

    @Override
    public void showRemoveAdsInAppPurchaseWindow() {
        // nope
    }

    @Override
    public boolean isFullVersionUser() {
        return true;
    }

    @Override
    public boolean googleConnectionOrCacheEstablished() {
        return false;
    }
}
