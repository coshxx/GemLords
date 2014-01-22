package de.cosh.anothermanager.Characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class FloatingNumbers extends Actor {
	private BitmapFont healFont;
	private BitmapFont damageFont;

	private Integer value;

	public FloatingNumbers() {
		healFont = new BitmapFont(Gdx.files.internal("data/fonts/heal.fnt"));
		damageFont = new BitmapFont(Gdx.files.internal("data/fonts/damage.fnt"));
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
		healFont.setColor(getColor());
		damageFont.setColor(getColor());
		if (value > 0) {
			healFont.draw(batch, "+" + value.toString(), getX(), getY());
		} else if (value < 0) {
			damageFont.draw(batch, value.toString(), getX(), getY());
		}
	}
}
