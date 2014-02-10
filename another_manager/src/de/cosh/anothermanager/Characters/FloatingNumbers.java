package de.cosh.anothermanager.Characters;

import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import de.cosh.anothermanager.GemLord;

class FloatingNumbers extends Actor {
    private BitmapFont bmf;
    private Integer value;
    private boolean isCrit;

    public FloatingNumbers() {
        GemLord.getInstance();
        Skin s = GemLord.assets.get("data/ui/uiskin.json", Skin.class);
        bmf = s.getFont("default-font");
        bmf.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        this.translate(0, delta * 50);
    }

    public void setup(int value, float x, float y, boolean isCrit) {
        this.value = value;
        this.isCrit = isCrit;
        setX(x);
        setY(y);
    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        bmf.setScale(2f);
        if (value > 0) {
            bmf.setColor(0f, 1f, 0f, parentAlpha * this.getColor().a);
            bmf.draw(batch, "+" + value.toString(), getX(), getY());
        } else if (value < 0) {
            bmf.setColor(1f, 0f, 0f, parentAlpha * this.getColor().a);
            if( isCrit ) {
                bmf.draw(batch, "*" + value.toString() + "*", getX(), getY());
            }
            else {
                bmf.draw(batch, value.toString(), getX(), getY());
            }
        }
        bmf.setScale(1f);
    }
}
