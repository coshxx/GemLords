package de.cosh.anothermanager.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import de.cosh.anothermanager.GemLord;

/**
 * Created by cosh on 10.02.14.
 */
public class CreditScreen implements Screen, InputProcessor {

    private BitmapFont bmf;
    private String credits;
    private SpriteBatch batch;
    private float yHeight;

    private ShaderProgram shader;

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);
        Gdx.input.setCatchBackKey(true);

        batch = new SpriteBatch();
        Skin s = GemLord.assets.get("data/ui/uiskin.json", Skin.class);
        bmf = s.getFont("credit-font");
        FileHandle fileHandle = Gdx.files.internal("data/credits.txt");
        credits = fileHandle.readString();
        yHeight = 0f;
        GemLord.soundPlayer.playCreditsMusic();

        ShaderProgram.pedantic = false;
        shader = new ShaderProgram(Gdx.files.internal("data/shaders/vignette_vert.glsl"), Gdx.files.internal("data/shaders/vignette_frag.glsl"));
        if( !shader.isCompiled() ) {
            System.out.println(shader.getLog());
        }
        batch.setShader(shader);


    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        yHeight += delta * 75;



        batch.setProjectionMatrix(GemLord.getInstance().camera.combined);
        batch.begin();
        bmf.drawMultiLine(batch, credits, 0, yHeight, GemLord.VIRTUAL_WIDTH, BitmapFont.HAlignment.CENTER);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        shader.begin();
        shader.setUniformf("u_resolution", width, height);
        shader.end();
    }

    @Override
    public void hide() {
        GemLord.soundPlayer.stopGameMusic();
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

    @Override
    public boolean keyDown(int keycode) {
        if( keycode == Input.Keys.BACK || keycode == Input.Keys.ESCAPE ) {
            GemLord.getInstance().setScreen(GemLord.getInstance().menuScreen);
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
