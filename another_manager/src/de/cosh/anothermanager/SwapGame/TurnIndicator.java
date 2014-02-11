package de.cosh.anothermanager.SwapGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import de.cosh.anothermanager.GemLord;

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
        turnSign = new Image(GemLord.assets.get("data/textures/turnindicator.png", Texture.class));
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
                    Actions.moveBy(0, turnSign.getHeight(), 0.15f),
                    Actions.delay(0.15f), //switch sign
                    Actions.moveBy(0, -turnSign.getHeight(), 0.15f)));

            yourTurnLabel.addAction(Actions.moveBy(0, turnSign.getHeight(), 0.15f));

            enemyTurnLabel.addAction(Actions.sequence(
                    Actions.delay(0.3f),
                    Actions.moveBy(0, -turnSign.getHeight(), 0.15f)));
        } else {
            turnSign.addAction(Actions.sequence(
                    Actions.moveBy(0, turnSign.getHeight(), 0.15f),
                    Actions.delay(0.15f), //switch sign
                    Actions.moveBy(0, -turnSign.getHeight(), 0.15f)));

            enemyTurnLabel.addAction(Actions.moveBy(0, turnSign.getHeight(), 0.15f));

            yourTurnLabel.addAction(Actions.sequence(
                    Actions.delay(0.3f),
                    Actions.moveBy(0, -turnSign.getHeight(), 0.15f)));
        }

        playerTurn = !playerTurn;
    }

    public boolean isPlayerTurn() {
        return playerTurn;
    }
}
