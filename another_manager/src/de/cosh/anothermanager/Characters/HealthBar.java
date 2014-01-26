package de.cosh.anothermanager.Characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;

import de.cosh.anothermanager.AnotherManager;

/**
 * Created by cosh on 27.12.13.
 */
public class HealthBar extends Actor {
	private BitmapFont bmf;
	private float done;

	private NinePatch empty, full;
	private Texture emptyT, fullT;

	private int healthpoints, maxHP;
	private float left, bot, width, height;

	private Array<FloatingNumbers> floatingNumbers;

	@Override
	public void act(final float delta) {
		done = (float) healthpoints / (float) maxHP;
		for( FloatingNumbers f : floatingNumbers ) {
			f.act(delta);
		}
	}

	@Override
	public void draw(final SpriteBatch batch, final float parentAlpha) {
        super.draw(batch, parentAlpha);
        empty.draw(batch, left, bot, width, height);
		if (healthpoints > 0) {
			full.draw(batch, left, bot, done * width, height);
		}

		final Integer health = healthpoints;
		final Integer maxhealth = maxHP;

		AnotherManager.getInstance();
		Skin s = AnotherManager.assets.get("data/ui/uiskin.json", Skin.class)	;
		bmf = s.getFont("default-font");

		bmf.setColor(1f, 1f, 1f, parentAlpha);
		bmf.draw(batch, health.toString() + " / " + maxhealth.toString(), left + (width / 2), bot + 25);

		for( int i = 0; i < floatingNumbers.size; i++ ) {
			FloatingNumbers f = floatingNumbers.get(i);
			f.draw(batch, parentAlpha);
		}
	}

	public int getHealthpoints() {
		return healthpoints;
	}

	@Override
	public float getHeight() {
		return height;
	}

	@Override
	public float getWidth() {
		return width;
	}

	public void hit(final int damage) {
		healthpoints -= damage;
		if (healthpoints <= 0) {
			healthpoints = 0;
		}

		FloatingNumbers f = new FloatingNumbers();
		f.setup(-damage, left + width/2 + width/4, bot + 70);
		floatingNumbers.add(f);
		f.addAction(Actions.fadeOut(3f));
		getStage().addActor(f);
	}

	public void init(final int hp) {
		this.healthpoints = hp;
		this.maxHP = hp;



		emptyT = new Texture(Gdx.files.internal("data/textures/empty.png"));
		fullT = new Texture(Gdx.files.internal("data/textures/full.png"));

		empty = new NinePatch(new TextureRegion(emptyT, 24, 24), 8, 8, 8, 8);
		full = new NinePatch(new TextureRegion(fullT, 24, 24), 8, 8, 8, 8);
		done = 1f;

		floatingNumbers = new Array<FloatingNumbers>();
	}

	public void setPosition(final float left, final float bot, final float width, final float height) {
		this.left = left;
		this.bot = bot;
		this.width = width;
		this.height = height;
	}

	public void increaseHealth(int hp) {
		this.healthpoints += hp;
		FloatingNumbers f = new FloatingNumbers();
		f.setup(hp, left + width/4, bot + 70);
		if( floatingNumbers == null )
			floatingNumbers = new Array<FloatingNumbers>();
		floatingNumbers.add(f);
		f.addAction(Actions.fadeOut(3f));
		if( getStage() != null )
			getStage().addActor(f);
	}
}
