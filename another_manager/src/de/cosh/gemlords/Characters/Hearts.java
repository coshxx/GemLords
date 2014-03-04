package de.cosh.gemlords.Characters;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import de.cosh.gemlords.GemLord;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by cosh on 01.03.14.
 */
public class Hearts {
    private final int MAX_HEARTS = 5;
    private final int PAD_X = 40;
    private final int SPACE_X = 40;
    private final int PAD_Y = 1200;

    private Image[] heartImageFull;
    private Image[] heartImageEmpty;
    private long[] unlockMoment;

    private int amountOfHearts;

    public Hearts() {
        TextureAtlas atlas = GemLord.assets.get("data/textures/pack.atlas", TextureAtlas.class);
        heartImageFull = new Image[MAX_HEARTS];
        heartImageEmpty = new Image[MAX_HEARTS];
        unlockMoment = new long[MAX_HEARTS];

        amountOfHearts = MAX_HEARTS;

        for( int i = 0; i < MAX_HEARTS; i++ ) {
            heartImageEmpty[i] = new Image(atlas.findRegion("heartempty"));
            heartImageFull[i] = new Image(atlas.findRegion("heart"));
            unlockMoment[i] = 0; // load

            heartImageEmpty[i].setPosition(PAD_X + SPACE_X * i, PAD_Y );
            heartImageFull[i].setPosition(PAD_X + SPACE_X * i, PAD_Y );
        }
    }

    public void addToStage(Stage stage) {
        for( int i = 0; i < MAX_HEARTS; i++ ) {
            stage.addActor(heartImageEmpty[i]);
            if( unlockMoment[i] == 0 ) {
                stage.addActor(heartImageFull[i]);
            }
        }
    }

    public void update() {
        Date now = new Date();
        long secondsCurrent = now.getTime()*1000;

    }

    public void loseHeart() {
        if( amountOfHearts >= MAX_HEARTS ) {
            heartImageFull[amountOfHearts].setVisible(false);
            long momentLost = new Date().getTime();
            long momentUnlocks = momentLost + ( (60 * 20) * 1000 );
        }
    }
}
