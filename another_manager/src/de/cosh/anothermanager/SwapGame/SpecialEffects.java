package de.cosh.anothermanager.SwapGame;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import de.cosh.anothermanager.GemLord;

class SpecialEffects {
	public void playUnstoppable(Group group) {
		GemLord.getInstance();
		Image awesome = new Image(GemLord.assets.get("data/textures/awesome.png", Texture.class));
		awesome.setPosition(GemLord.VIRTUAL_WIDTH, GemLord.VIRTUAL_HEIGHT/2);
		float halfImage = awesome.getWidth()/2;
		float halfScreen = GemLord.VIRTUAL_WIDTH/2;
		awesome.addAction(Actions.sequence(
				Actions.moveBy(-(halfScreen + halfImage), 0, 0.25f),
				Actions.moveBy(10f,  0f, 0.1f),
				Actions.moveBy(-10f,  0f, 0.1f),
				Actions.delay(0.5f),
				Actions.moveBy(-(halfScreen + halfImage), 0, 0.25f)));
		group.addActor(awesome);

	}
	public void playImpressive() {

	}
	public void playGodlike() {

	}
}
