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
	private transient BitmapFont bmf;
	private transient float done;

	private transient NinePatch empty, full;
	private transient Texture emptyT, fullT;

	private int hp, maxHP;
	private transient float left, bot, width, height;

	private transient Array<FloatingNumbers> floatingNumbers;

	@Override
	public void act(final float delta) {
		done = (float) hp / (float) maxHP;

        for( int i = 0; i < floatingNumbers.size; i++ ) {
            FloatingNumbers f = floatingNumbers.get(i);
            if( f.getColor().a <= 0f ) {
                floatingNumbers.removeIndex(i);
                f.remove();
                i--;
            } else
                f.act(delta);
        }
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        empty.draw(batch, left, bot, width, height);
		if (hp > 0) {
			full.draw(batch, left, bot, done * width, height);
		}

		final Integer health = hp;
		final Integer maxhealth = maxHP;

		AnotherManager.getInstance();
		Skin s = AnotherManager.assets.get("data/ui/uiskin.json", Skin.class)	;
		bmf = s.getFont("default-font");

		bmf.setColor(0.25f, 0.25f, 1.0f, parentAlpha);
        String text = health.toString() + " / " + maxhealth.toString();
		bmf.draw(batch, text, left + (width / 2) - bmf.getBounds(text).width/2, bot + 25 + bmf.getBounds(text).height/2);

		for( int i = 0; i < floatingNumbers.size; i++ ) {
			FloatingNumbers f = floatingNumbers.get(i);
			f.draw(batch, parentAlpha);
		}
	}

	public int getHp() {
		return hp;
	}

	@Override
	public float getHeight() {
		return height;
	}

	@Override
	public float getWidth() {
		return width;
	}

	public void hit(Damage damage) {
		hp -= damage.damage;
		if (hp <= 0) {
			hp = 0;
		}

		FloatingNumbers f = new FloatingNumbers();
        floatingNumbers.add(f);
		f.setup(-damage.damage, left + width/2 + width/4, bot + 100 - (floatingNumbers.size * 25), damage.isCrit);
		f.addAction(Actions.fadeOut(6f));
		getStage().addActor(f);
	}

	public void init(final int hp) {
		this.hp = hp;
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
		this.hp += hp;
        if( this.hp >= maxHP )
            this.hp = maxHP;

		if( floatingNumbers == null )
			floatingNumbers = new Array<FloatingNumbers>();

        FloatingNumbers f = new FloatingNumbers();
        f.addAction(Actions.fadeOut(6f));
        floatingNumbers.add(f);
        f.setup(hp, left + width/4, bot + 100 - (floatingNumbers.size * 25), false);
		if( getStage() != null )
			getStage().addActor(f);
	}
}
