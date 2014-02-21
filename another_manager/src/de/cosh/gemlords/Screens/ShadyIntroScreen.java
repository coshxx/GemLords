package de.cosh.gemlords.Screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import de.cosh.gemlords.GemLord;

/**
 * Created by cosh on 12.02.14.
 */
public class ShadyIntroScreen implements Screen {

    private SpriteBatch batch, batch2;
    private ShaderProgram shader;
    private Sprite mapImage;
    private Sprite logoSprite;

    private float alpha = 0f;
    private float alphaIncrease = 0.075f;

    private float totalTimePassed = 0f;
    private float logoAlpha = 0f;

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        totalTimePassed += delta;

        if( totalTimePassed >= 10f ) {
            logoAlpha += alphaIncrease * delta;
            if (logoAlpha >= 1f) {
                logoAlpha = 1f;
            }

        }

        if( Gdx.input.isKeyPressed(Input.Keys.ESCAPE )) {
            GemLord.getInstance().setScreen(GemLord.getInstance().mapTraverseScreen);
            GemLord.soundPlayer.stopDONOTUSE();
        }

        GemLord.getInstance().camera.zoom = 0.3f;
        GemLord.getInstance().camera.translate(delta*5, delta*10);

        batch.setProjectionMatrix(GemLord.getInstance().camera.combined);
        batch.begin();
        mapImage.draw(batch, alpha);
        batch.end();
        OrthographicCamera penis = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        penis.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        penis.update();
        batch2.setProjectionMatrix(penis.combined);
        batch2.begin();
        logoSprite.draw(batch2, logoAlpha );
        batch2.end();



        alpha += alphaIncrease * delta;
        if( alpha >= 1f )
            alpha = 1f;
    }

    @Override
    public void resize(int width, int height) {
        shader.begin();
        shader.setUniformf("u_resolution", width, height);
        shader.end();
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        batch2 = new SpriteBatch();
        GemLord.soundPlayer.playDONOTUSE();
        ShaderProgram.pedantic = false;
        shader = new ShaderProgram(Gdx.files.internal("data/shaders/vignette_vert.glsl"), Gdx.files.internal("data/shaders/vignette_frag.glsl"));
        if( !shader.isCompiled() ) {
            System.out.println(shader.getLog());
        }
        batch.setShader(shader);

        mapImage = new Sprite(GemLord.assets.get("data/textures/map.jpg", Texture.class));
        mapImage.setPosition(0, 0);

        logoSprite = new Sprite(GemLord.assets.get("data/textures/title.png", Texture.class));
        logoSprite.setBounds(0, 800, Gdx.graphics.getWidth(), 100);
    }

    @Override
    public void hide() {

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

