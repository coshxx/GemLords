package de.cosh.gemlords.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import de.cosh.gemlords.CustomActions.PlayScribbleSoundAction;
import de.cosh.gemlords.CustomStyle;
import de.cosh.gemlords.GemLord;

/**
 * Created by cosh on 26.02.14.
 */
public class Episode1IntroScreen implements Screen {
    private Image introImage;
    private TextButton continueButton;
    private Stage stage;

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {
        stage = new Stage(GemLord.VIRTUAL_WIDTH, GemLord.VIRTUAL_HEIGHT);
        stage.setCamera(GemLord.getInstance().camera);
        Gdx.input.setInputProcessor(stage);
        Skin s = GemLord.assets.get("data/ui/uiskin.json", Skin.class);
        continueButton = new TextButton("Continue", s);
        continueButton.setStyle(CustomStyle.getInstance().getTextButtonStyle());
        continueButton.setBounds(GemLord.VIRTUAL_WIDTH/2-200, 100, 400, 100);

        continueButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                GemLord.getInstance().setScreen(GemLord.getInstance().episode1TraverseScreen);
            }
        });

        introImage = new Image(new Texture(Gdx.files.internal("data/textures/intro.jpg")));
        introImage.setFillParent(true);



        stage.addActor(introImage);
        stage.addActor(continueButton);



        stage.addAction(Actions.alpha(0));
        stage.addAction(Actions.fadeIn(2f));

        Label.LabelStyle labelStyle = new Label.LabelStyle(s.getFont("intro-font"), Color.WHITE);

        Label label1 = new Label("To whom it may concern", s);
        Label label2 = new Label("Your evil lord Lancenson", s);
        Label label3 = new Label("commands you to kill the", s);
        Label label4 = new Label("giant mutated fly living", s);
        Label label5 = new Label("on the eastern part of", s);
        Label label6 = new Label("the coastal area. Go now.", s);

        label1.setStyle(labelStyle);
        label1.setPosition(20, 800 );

        label2.setStyle(labelStyle);
        label2.setPosition(20, 760 );

        label3.setStyle(labelStyle);
        label3.setPosition(20, 720 );

        label4.setStyle(labelStyle);
        label4.setPosition(20, 680 );

        label5.setStyle(labelStyle);
        label5.setPosition(20, 640 );

        label6.setStyle(labelStyle);
        label6.setPosition(20, 600 );

        label1.addAction(Actions.alpha(0));
        label2.addAction(Actions.alpha(0));
        label3.addAction(Actions.alpha(0));
        label4.addAction(Actions.alpha(0));
        label5.addAction(Actions.alpha(0));
        label6.addAction(Actions.alpha(0));

        label1.addAction(Actions.sequence(
                Actions.delay(1f),
                new PlayScribbleSoundAction(),
                Actions.fadeIn(1f))
        );

        label2.addAction(Actions.sequence(
                Actions.delay(2f),
                Actions.fadeIn(1f))
        );

        label3.addAction(Actions.sequence(
                Actions.delay(3f),
                Actions.fadeIn(1f))
        );

        label4.addAction(Actions.sequence(
                Actions.delay(4f),
                Actions.fadeIn(1f))
        );

        label5.addAction(Actions.sequence(
                Actions.delay(5f),
                Actions.fadeIn(1f))
        );

        label6.addAction(Actions.sequence(
                Actions.delay(6f),
                Actions.fadeIn(1f))
        );


        stage.addActor(label1);
        stage.addActor(label2);
        stage.addActor(label3);
        stage.addActor(label4);
        stage.addActor(label5);
        stage.addActor(label6);

        GemLord.soundPlayer.playSequence();
    }

    @Override
    public void hide() {
        GemLord.soundPlayer.stopSequence();

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
