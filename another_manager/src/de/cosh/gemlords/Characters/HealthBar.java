package de.cosh.gemlords.Characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;

import de.cosh.gemlords.GemLord;

/**
 * Created by cosh on 27.12.13.
 */
public class HealthBar extends Actor {
    private transient BitmapFont bmf;
    private transient float done;

    private int hp, maxHP;
    private transient float left, bot, width, height;
    private transient Array<FloatingNumbers> floatingNumbers;

    private Sprite emptyBar;
    private TextureRegion fullBar;
    private float shrinkRate = 150f;
    private float last = 1f;


    @Override
    public void act(float delta) {
        float target = (float) hp / (float) maxHP;
        target *= width;

        if( target < last ) {
            last -= delta * shrinkRate;
            if( last < 0 )
                last = 0;

        }

        if( target > last ) {
            last += delta * shrinkRate;
        }

        //fullBar.setBounds(left, bot, done*width, height);

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
    public void draw(Batch batch, float parentAlpha) {
        //super.draw(batch, parentAlpha);
        final Integer health = hp;
        final Integer maxhealth = maxHP;
        emptyBar.draw(batch, parentAlpha);
        //fullBar.draw(batch, parentAlpha);
        //batch.draw(fullBar, left, bot, last, height);
        batch.draw(fullBar.getTexture(), left, bot, 0, 0, last, height, 1, 1, 0, fullBar.getRegionX(), fullBar.getRegionY(), (int)last/2, fullBar.getRegionHeight(), false, false);
        Skin s = GemLord.assets.get("data/ui/uiskin.json", Skin.class)	;
        bmf = s.getFont("default-font");
        bmf.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        bmf.setColor(1, 1, 1, parentAlpha);
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

        FloatingNumbers f = new FloatingNumbers();
        floatingNumbers.add(f);
        f.setup(-damage.damage, left + width/2 + width/4, bot + 100 - (floatingNumbers.size * 25), damage.isCrit);
        if( damage.isCrit ) {
        }
        f.addAction(Actions.fadeOut(6f));
        getStage().addActor(f);
    }

    public void init(final int hp) {
        this.hp = hp;
        this.maxHP = hp;
        done = 1f;

        TextureAtlas atlas = GemLord.assets.get("data/textures/pack.atlas", TextureAtlas.class);
        emptyBar = new Sprite(atlas.findRegion("emptyhp"));
        fullBar = new TextureRegion(atlas.findRegion("fullhp"));

        /*
        emptyBar = new Sprite(new Texture(Gdx.files.internal("data/textures/raw-atlas/emptyhp.png")));
        fullBar = new TextureRegion(new Texture(Gdx.files.internal("data/textures/raw-atlas/fullhp.png")));
        */
        emptyBar.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        fullBar.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        floatingNumbers = new Array<FloatingNumbers>();
    }

    public void setPosition(final float left, final float bot, final float width, final float height) {
        this.left = left;
        this.bot = bot;
        this.width = width;
        this.height = height;

        emptyBar.setBounds(left, bot, width, height);
        last = done * width;
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
