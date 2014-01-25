package de.cosh.anothermanager.Characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import de.cosh.anothermanager.AnotherManager;

/**
 * Created by cosh on 15.01.14.
 */
public class Buff {
	private final BitmapFont bmf;
	private int healthPerTurn;
	private final Image buffBorderImage;

	private Image buffImage;
	private final int height = 30;

	private BaseCharacter toChar;
	private int turns;
	private int turnsComplete;
	private final int width = 30;

	public Buff() {
		this.buffBorderImage = new Image(AnotherManager.assets.get("data/textures/buff_border.png", Texture.class));
		AnotherManager.getInstance();
		Skin s = AnotherManager.assets.get("data/ui/uiskin.json", Skin.class)	;
		bmf = s.getFont("default-font");
	}

	public void addBuffToGroup(final Group group) {
		group.addActor(buffBorderImage);
		group.addActor(buffImage);
	}

	public void drawCooldown(final SpriteBatch batch, final float parentAlpha) {
		final Integer cooldown = turns - turnsComplete;
		bmf.setColor(1f, 1f, 1f, parentAlpha);
		bmf.draw(batch, cooldown.toString(), buffImage.getX(), buffImage.getY());
	}

	public void moveRight() {
		buffBorderImage.addAction(Actions.moveBy(buffBorderImage.getWidth(), 0));
		buffImage.addAction(Actions.moveBy(buffBorderImage.getWidth(), 0));
	}

	public void setBuffImage(final Image img) {
		buffImage = img;
	}

	public void setPosition(final float x, final float y) {
		buffBorderImage.setBounds(x, y, width, height);
		buffImage.setBounds(x + 2.5f, y + 2.5f, width - 5, height - 5);
	}

	public void setup(final int healthPerTurn, final int turns, final BaseCharacter toChar) {
		this.healthPerTurn = healthPerTurn;
		this.turns = turns;
		this.toChar = toChar;
		this.turnsComplete = 0;
	}

	public boolean turn() {
		turnsComplete++;
		if (turnsComplete > turns) {
			// remove
			buffBorderImage.remove();
			buffImage.remove();
			return true;
		}

		toChar.increaseHealth(healthPerTurn);
		buffBorderImage.addAction(Actions.sequence(Actions.scaleTo(2f, 2f, 0.15f), Actions.scaleTo(1f, 1f, 0.15f)));
		buffImage.addAction(Actions.sequence(Actions.scaleTo(2f, 2f, 0.15f), Actions.scaleTo(1f, 1f, 0.15f)));
		return false;
	}
}
