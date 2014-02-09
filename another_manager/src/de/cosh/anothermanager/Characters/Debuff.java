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
public class Debuff {
	private final BitmapFont bmf;
	private int damagePerTurn;
	private final Image debuffBorderImage;

	private Image debuffImage;

    private final int height = 30;
    private final int width = 30;

	private BaseCharacter toChar;
	private int turns;
	private int turnsComplete;

	public Debuff() {
		this.debuffBorderImage = new Image(AnotherManager.assets.get("data/textures/debuff_border.png", Texture.class));
		AnotherManager.getInstance();
		Skin s = AnotherManager.assets.get("data/ui/uiskin.json", Skin.class)	;
		bmf = s.getFont("default-font");

	}

	public void addDebuffToGroup(final Group group) {
		group.addActor(debuffBorderImage);
		group.addActor(debuffImage);
	}

	public void drawCooldown(final SpriteBatch batch, final float parentAlpha) {
		final Integer cooldown = turns - turnsComplete;
		bmf.setColor(1f, 1f, 1f, parentAlpha);
		bmf.draw(batch, cooldown.toString(), debuffImage.getX()+6, debuffImage.getY()+20);
	}

	public void setDebuffImage(final Image img) {
		debuffImage = img;
	}

	public void setPosition(final float x, final float y) {
		debuffBorderImage.setBounds(x, y, width, height);
		debuffImage.setBounds(x + 2.5f, y + 2.5f, width - 5, height - 5);
	}

	public void setup(int damagePerTurn, int turns, BaseCharacter toChar ) {
		this.damagePerTurn = damagePerTurn;
		this.turns = turns;
		this.toChar = toChar;
		this.turnsComplete = 0;
	}

	public boolean turn() {
		turnsComplete++;
		if (turnsComplete > turns) {
			// remove
			debuffBorderImage.remove();
			debuffImage.remove();
			return true;
		}
        Damage damage = new Damage();
        damage.damage = damagePerTurn;
		toChar.damage(damage);
		debuffBorderImage.addAction(Actions.sequence(Actions.scaleTo(2f, 2f, 0.15f), Actions.scaleTo(1f, 1f, 0.15f)));
		debuffImage.addAction(Actions.sequence(Actions.scaleTo(2f, 2f, 0.15f), Actions.scaleTo(1f, 1f, 0.15f)));
		return false;
	}
}
