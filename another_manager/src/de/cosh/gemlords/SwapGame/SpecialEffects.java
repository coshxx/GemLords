package de.cosh.gemlords.SwapGame;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import de.cosh.gemlords.GemLord;
import de.cosh.gemlords.LanguageManager;

class SpecialEffects {
    private BitmapFont bmf;

	public void playGood(Group effectGroup) {
        Skin s = GemLord.assets.get("data/ui/uiskin.json", Skin.class);
        GemLord.soundPlayer.playAwesome();
        bmf = s.getFont("sfx-font");
		float halfScreen = GemLord.VIRTUAL_WIDTH/2;
        LanguageManager lm = LanguageManager.getInstance();
        Label.LabelStyle sfxStyle = new Label.LabelStyle(bmf, new Color(1, 1, 1, 1));
        Label label = new Label(lm.getString("Good"), sfxStyle);

        label.setPosition(GemLord.VIRTUAL_WIDTH, GemLord.VIRTUAL_HEIGHT/2);
        label.addAction(Actions.sequence(
                Actions.moveBy(-480, 0, 0.15f),
                Actions.delay(0.5f),
                Actions.moveBy(-550, 0, 0.15f),
                Actions.removeActor())
        );


        effectGroup.addActor(label);

	}
	public void playGreat(Group effectGroup) {
        Skin s = GemLord.assets.get("data/ui/uiskin.json", Skin.class);
        GemLord.soundPlayer.playAwesome();
        bmf = s.getFont("sfx-font");
        float halfScreen = GemLord.VIRTUAL_WIDTH/2;
        LanguageManager lm = LanguageManager.getInstance();
        Label.LabelStyle sfxStyle = new Label.LabelStyle(bmf, new Color(1, 1, 1, 1));
        Label label = new Label(lm.getString("Great"), sfxStyle);

        label.setPosition(GemLord.VIRTUAL_WIDTH, GemLord.VIRTUAL_HEIGHT/2);
        label.addAction(Actions.sequence(
                Actions.moveBy(-480, 0, 0.15f),
                Actions.delay(0.5f),
                Actions.moveBy(-550, 0, 0.15f),
                Actions.removeActor())
        );
        effectGroup.addActor(label);

	}
	public void playAwesome(Group effectGroup) {
        Skin s = GemLord.assets.get("data/ui/uiskin.json", Skin.class);
        GemLord.soundPlayer.playAwesome();
        bmf = s.getFont("sfx-font");
        float halfScreen = GemLord.VIRTUAL_WIDTH/2;
        LanguageManager lm = LanguageManager.getInstance();
        Label.LabelStyle sfxStyle = new Label.LabelStyle(bmf, new Color(1, 1, 1, 1));
        Label label = new Label(lm.getString("Awesome"), sfxStyle);

        label.setPosition(GemLord.VIRTUAL_WIDTH, GemLord.VIRTUAL_HEIGHT/2);
        label.addAction(Actions.sequence(
                Actions.moveBy(-550, 0, 0.15f),
                Actions.delay(0.5f),
                Actions.moveBy(-550, 0, 0.15f),
                Actions.removeActor())
        );
        effectGroup.addActor(label);
	}
}
