package de.cosh.gemlords.SwapGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import de.cosh.gemlords.GemLord;

class TurnIndicator {

    private Label yourTurnLabel;
    private Label enemyTurnLabel;
    private Skin skin;
    private boolean playerTurn;

    private Image turnSign;

    public TurnIndicator() {
        skin = new Skin(Gdx.files.internal("data/ui/uiskin.json"));
        yourTurnLabel = new Label("Your turn", skin, "credit-font", Color.WHITE);
        enemyTurnLabel = new Label("Enemy turn", skin, "credit-font", Color.WHITE);
        playerTurn = true;

        TextureAtlas atlas = GemLord.assets.get("data/textures/pack.atlas", TextureAtlas.class);
        turnSign = new Image(atlas.findRegion("turnindicator"));
        turnSign.setPosition(10, GemLord.VIRTUAL_HEIGHT - turnSign.getHeight());
        yourTurnLabel.setPosition(50, GemLord.VIRTUAL_HEIGHT - turnSign.getHeight() + 20);
        enemyTurnLabel.setPosition(35, GemLord.VIRTUAL_HEIGHT - turnSign.getHeight() + 20);
        enemyTurnLabel.addAction(Actions.moveBy(0, turnSign.getHeight()));

    }

    public void addToBoard(Group group) {
        group.addActor(turnSign);
        group.addActor(yourTurnLabel);
        group.addActor(enemyTurnLabel);
    }

    public void switchPlayers() {
        GemLord.soundPlayer.playTurnSwitch();
        if (playerTurn) {
            turnSign.addAction(Actions.sequence(
                    Actions.moveBy(0, turnSign.getHeight(), 0.1f),
                    Actions.delay(0.1f), //switch sign
                    Actions.moveBy(0, -turnSign.getHeight(), 0.1f)));

            yourTurnLabel.addAction(Actions.moveBy(0, turnSign.getHeight(), 0.1f));

            enemyTurnLabel.addAction(Actions.sequence(
                    Actions.delay(0.2f),
                    Actions.moveBy(0, -turnSign.getHeight(), 0.1f)));
        } else {
            turnSign.addAction(Actions.sequence(
                    Actions.moveBy(0, turnSign.getHeight(), 0.1f),
                    Actions.delay(0.1f), //switch sign
                    Actions.moveBy(0, -turnSign.getHeight(), 0.1f)));

            enemyTurnLabel.addAction(Actions.moveBy(0, turnSign.getHeight(), 0.1f));

            yourTurnLabel.addAction(Actions.sequence(
                    Actions.delay(0.2f),
                    Actions.moveBy(0, -turnSign.getHeight(), 0.1f)));
        }
        playerTurn = !playerTurn;
    }

    public boolean isPlayerTurn() {
        return playerTurn;
    }
}
