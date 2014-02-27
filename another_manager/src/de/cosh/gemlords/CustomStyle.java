package de.cosh.gemlords;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by cosh on 22.02.14.
 */
public class CustomStyle {

    private static CustomStyle instance;
    private TextButton.TextButtonStyle buttonStyle;

    private CustomStyle() {
        createStyles();
    }

    private void createStyles() {
        Drawable buttonUp = new TextureRegionDrawable(new TextureRegion(GemLord.assets.get("data/textures/pack.atlas", TextureAtlas.class).findRegion("buttonup")));
        Drawable buttonDown = new TextureRegionDrawable(new TextureRegion(GemLord.assets.get("data/textures/pack.atlas", TextureAtlas.class).findRegion("buttondown")));
        Skin s = GemLord.assets.get("data/ui/uiskin.json", Skin.class);
        BitmapFont bmf = s.getFont("mytest-font");
        buttonStyle = new TextButton.TextButtonStyle(buttonUp, buttonDown, null, bmf);
    }

    public TextButton.TextButtonStyle getTextButtonStyle() {
        return buttonStyle;
    }

    public static CustomStyle getInstance() {
        if( instance == null )
            instance = new CustomStyle();
        return instance;
    }
}
