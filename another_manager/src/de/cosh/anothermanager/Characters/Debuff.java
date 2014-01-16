package de.cosh.anothermanager.Characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import de.cosh.anothermanager.AnotherManager;

/**
 * Created by cosh on 15.01.14.
 */
public class Debuff {
    private AnotherManager myGame;
    private Image debuffBorderImage;
    private Image debuffImage;

    private final int width = 30;
    private final int height = 30;

    private BaseCharacter toChar;
    private int damagePerTurn;
    private int turns;
    private int turnsComplete;
    private BitmapFont bmf;

    public Debuff(AnotherManager myGame) {
        this.myGame = myGame;
        this.debuffBorderImage =  new Image(myGame.assets.get("data/debuff_border.png", Texture.class));
        this.bmf = new BitmapFont();
    }
    public boolean turn() {
        turnsComplete++;
        if( turnsComplete > turns ) {
            // remove
            debuffBorderImage.remove();
            debuffImage.remove();
            return true;
        }

        toChar.damage(damagePerTurn);
        debuffBorderImage.addAction(Actions.sequence(Actions.scaleTo(2f, 2f, 0.15f), Actions.scaleTo(1f, 1f, 0.15f)));
        debuffImage.addAction(Actions.sequence(Actions.scaleTo(2f, 2f, 0.15f), Actions.scaleTo(1f, 1f, 0.15f)));
        return false;
    }

    public void setDebuffImage(Image img) {
        debuffImage = img;
    }

    public void setup( int damagePerTurn, int turns, BaseCharacter toChar ) {
        this.damagePerTurn = damagePerTurn;
        this.turns = turns;
        this.toChar = toChar;
        this.turnsComplete = 0;
    }

    public void drawCooldown(SpriteBatch batch, float parentAlpha) {
        Integer cooldown = turns-turnsComplete;
        bmf.setColor(1f, 1f, 1f, parentAlpha);
        bmf.draw(batch, cooldown.toString(), debuffImage.getX(), debuffImage.getY());
    }

    public void setPosition(float x, float y) {
        debuffBorderImage.setBounds(x, y, width, height);
        debuffImage.setBounds(x+2.5f, y+2.5f, width-5, height-5);
    }

    public void addDebuffToGroup(Group group) {
        group.addActor(debuffBorderImage);
        group.addActor(debuffImage);
    }

    public void moveRight() {
        debuffBorderImage.addAction(Actions.moveBy(debuffBorderImage.getWidth(), 0));
        debuffImage.addAction(Actions.moveBy(debuffBorderImage.getWidth(), 0));
    }
}
