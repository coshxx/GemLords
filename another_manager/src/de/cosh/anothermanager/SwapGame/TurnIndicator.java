package de.cosh.anothermanager.SwapGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

class TurnIndicator extends Actor {

	private Label yourTurnLabel;
	private Label enemyTurnLabel;
	private Skin skin;
	private boolean playerTurn;

	public TurnIndicator() {
		skin = new Skin(Gdx.files.internal("data/ui/uiskin.json"));
		yourTurnLabel = new Label("Your turn", skin);
		enemyTurnLabel = new Label("Enemy turn", skin);
		playerTurn = true;
		yourTurnLabel.setPosition(10, 1200);
		enemyTurnLabel.setPosition(-90, 1200);
	}

	public void draw(SpriteBatch batch, float parentAlpha) {
		yourTurnLabel.draw(batch, parentAlpha);
		enemyTurnLabel.draw(batch, parentAlpha);
	}

	public void act(float delta) {
		super.act(delta);
		yourTurnLabel.act(delta);
		enemyTurnLabel.act(delta);
	}

	public void switchPlayers() {
		if (playerTurn) {
			yourTurnLabel.addAction(Actions.moveBy(-100, 0, 0.25f));
			enemyTurnLabel.addAction(Actions.moveBy(100, 0, 0.25f));
		} else {
			yourTurnLabel.addAction(Actions.moveBy(100, 0, 0.25f));
			enemyTurnLabel.addAction(Actions.moveBy(-100, 0, 0.25f));
		}
		playerTurn = !playerTurn;
	}

    public boolean isPlayerTurn() {
        return playerTurn;
    }
}
