package de.cosh.anothermanager.Characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import de.cosh.anothermanager.AnotherManager;
import de.cosh.anothermanager.GUI.GUIWindow;

/**
 * Created by cosh on 10.01.14.
 */
public class Enemy extends Character {
    private Image enemyImage;
    private AnotherManager myGame;
    private boolean isDefeated;
    private TextureRegion pointTexture, pointDoneTexture;
    private ImageButton.ImageButtonStyle style, styleDone;
    private ImageButton pointButton, pointButtonDone;

    public Enemy(AnotherManager myGame, Texture texture) {
        super(myGame, 20);
        this.myGame = myGame;
        this.enemyImage = new Image(texture);

        this.isDefeated = false;
        this.myGame = myGame;

        pointTexture = new TextureRegion(myGame.assets.get("data/point.png", Texture.class));
        pointDoneTexture = new TextureRegion(myGame.assets.get("data/pointdone.png", Texture.class));

        style = new ImageButton.ImageButtonStyle();
        style.up = new TextureRegionDrawable(pointTexture);
        style.down = new TextureRegionDrawable(pointTexture);

        styleDone = new ImageButton.ImageButtonStyle();
        styleDone.up = new TextureRegionDrawable(pointDoneTexture);
        styleDone.down = new TextureRegionDrawable(pointDoneTexture);

        pointButton = new ImageButton(style.up, style.down);
        pointButtonDone = new ImageButton(styleDone.up, styleDone.down);



    }

    public void setPosition(float x, float y) {
        enemyImage.setPosition(x, y);
    }

    public Image getImage() {
        return enemyImage;
    }

    public boolean isDefeated() { return isDefeated; }

    public void setDefeated(boolean defeated) {
        isDefeated = defeated;
    }

    public void addPositionalButtonToMap(float x, float y, Image enemyImage, final int enemyHP, final Stage stage) {
        this.enemyImage = enemyImage;
        final Enemy e = this;

        pointButton.setPosition(x, y);
        pointButtonDone.setPosition(x, y);
        pointButton.addListener(new ClickListener() {

            public void clicked(InputEvent event, float x, float y) {
                myGame.soundPlayer.PlayBlub1();
                GUIWindow guiWindow = new GUIWindow(myGame, stage);
                guiWindow.showMapEnemyWindow(enemyHP);
                myGame.enemyManager.setSelectedEnemy(e);
            }
        });

        stage.addActor(isDefeated ? pointButtonDone : pointButton);
    }
}

