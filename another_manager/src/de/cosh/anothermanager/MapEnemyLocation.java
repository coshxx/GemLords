package de.cosh.anothermanager;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by cosh on 10.01.14.
 */
public class MapEnemyLocation {
    public boolean revealed;
    private TextureRegion pointTexture, pointDoneTexture;
    private AnotherManager myGame;
    private Vector2 position;
    private Image enemyImage;
    private boolean locationComplete;
    private ImageButton pointButton, pointButtonDone;
    private ImageButton.ImageButtonStyle style, styleDone;


    public MapEnemyLocation(AnotherManager myGame) {
        this.locationComplete = false;
        this.myGame = myGame;
        this.revealed = false;

        pointTexture = new TextureRegion(myGame.assets.get("data/point.png", Texture.class));
        pointDoneTexture = new TextureRegion(myGame.assets.get("data/pointdone.png", Texture.class));

        style = new ImageButton.ImageButtonStyle();
        style.up = new TextureRegionDrawable(pointTexture);
        style.down = new TextureRegionDrawable(pointTexture);

        styleDone = new ImageButton.ImageButtonStyle();
        styleDone.up = new TextureRegionDrawable(pointTexture);
        styleDone.down = new TextureRegionDrawable(pointTexture);

        pointButton = new ImageButton(style.up, style.down);
        pointButtonDone = new ImageButton(styleDone.up, styleDone.down);
    }

    public void addPositionalButtonToMap(Vector2 position, Image enemyImage, final Stage stage) {
        this.position = position;
        this.enemyImage = enemyImage;

        pointButton.setPosition(position.x, position.y);
        pointButtonDone.setPosition(position.x, position.y);
        pointButton.addListener(new ClickListener() {

            public void clicked(InputEvent event, float x, float y) {
                    myGame.soundPlayer.PlayBlub1();
                    GUIWindow guiWindow = new GUIWindow(myGame, stage);
                    guiWindow.showMapEnemyWindow();
            }
        });

        stage.addActor(locationComplete ? pointButtonDone : pointButton);
    }
}
