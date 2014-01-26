package de.cosh.anothermanager.Characters;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import de.cosh.anothermanager.AnotherManager;

public class FloatingNumbers extends Actor {
	private BitmapFont healFont;
	private BitmapFont damageFont;

	private Integer value;

	public FloatingNumbers() {
		AnotherManager.getInstance();
		Skin s = AnotherManager.assets.get("data/ui/uiskin.json", Skin.class)	;
		healFont = s.getFont("default-font");
		damageFont = s.getFont("default-font");
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		this.translate(0, delta * 100);
	}

	public void setup(int value, float x, float y) {
		this.value = value;
		setX(x);
		setY(y);
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
        healFont.setColor(0f, 1f, 0f, parentAlpha * this.getColor().a);
        damageFont.setColor(1f, 0f, 0f, parentAlpha * this.getColor().a);
		if (value > 0) {
			healFont.draw(batch, "+" + value.toString(), getX(), getY());
		} else if (value < 0) {
			damageFont.draw(batch, value.toString(), getX(), getY());
		}
	}
}
