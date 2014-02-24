package de.cosh.gemlords.Screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import de.cosh.gemlords.CustomStyle;
import de.cosh.gemlords.GemLord;
import de.cosh.gemlords.Characters.ActionBar;
import de.cosh.gemlords.GUI.GUIButton;
import de.cosh.gemlords.Items.BaseItem;
import de.cosh.gemlords.LanguageManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by cosh on 07.01.14.
 */
public class LoadoutScreen implements Screen, InputProcessor {
	private Stage stage;
    private SelectBox selectBoxInventory;

    private HashMap<String, Integer> itemsHashMap;
    private ArrayList<String> boxItemList;
    private BaseItem itemInCenter;
    private Skin s;
	public LoadoutScreen() {
	}

	@Override
	public void dispose() {

	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1f);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(final int width, final int height) {
		GemLord.getInstance().stageResize(width, height, stage);
	}

	@Override
	public void resume() {

	}

	@Override
	public void show() {
		stage = new Stage();
        s = GemLord.assets.get("data/ui/uiskin.json", Skin.class);
        InputMultiplexer plex = new InputMultiplexer();
        plex.addProcessor(stage);
        plex.addProcessor(this);
        Gdx.input.setInputProcessor(plex);
        TextureAtlas atlas = GemLord.assets.get("data/textures/pack.atlas", TextureAtlas.class);
		Image background = new Image(atlas.findRegion("background"));
		background.setBounds(0, 0, GemLord.VIRTUAL_WIDTH, GemLord.VIRTUAL_HEIGHT);
		stage.addActor(background);
		GemLord.soundPlayer.playLoadoutMusic();

        itemsHashMap = new HashMap<String, Integer>();
        boxItemList = fillItemList(itemsHashMap);
        GemLord.getInstance().player.getActionBar().addToLoadoutScreen(stage);
        addItemListeners();
        addButtons();

        if( boxItemList.size() == 0 )
            return;

        selectBoxInventory = new SelectBox(boxItemList.toArray(), s);
        selectBoxInventory.getStyle().font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        selectBoxInventory.setBounds(20, GemLord.VIRTUAL_HEIGHT-100, 680, 50);
        selectBoxInventory.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                changeCenterItem();
            }
        });

        changeCenterItem();

        stage.addActor(selectBoxInventory);
	}

    private void addButtons() {
        LanguageManager lm = LanguageManager.getInstance();

        TextButton removeButton = new TextButton(lm.getString("Remove"), s);
        TextButton returnToMapButton = new TextButton(lm.getString("Back to map"), s);

        removeButton.setStyle(CustomStyle.getInstance().getTextButtonStyle());
        returnToMapButton.setStyle(CustomStyle.getInstance().getTextButtonStyle());

        returnToMapButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GemLord.soundPlayer.stopLoadoutMusic();
                GemLord.getInstance().setScreen(GemLord.getInstance().mapTraverseScreen);
            }
        });

        removeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ArrayList<BaseItem> allItems = GemLord.getInstance().player.getInventory().getAllItems();
                for( int i = 0; i < allItems.size(); i++ ) {
                    BaseItem current = allItems.get(i);
                    if( current.isSelected() ) {
                        if( current.isAddedToActionBar() ) {
                            GemLord.getInstance().player.getActionBar().removeFromBar(current);
                            current.unselect();
                            current.remove();
                            refreshItemList();
                        }
                    }
                }
            }
        });

        removeButton.setBounds(0, 0, 200, 100);
        returnToMapButton.setBounds(GemLord.VIRTUAL_WIDTH-200, 0, 200, 100);

        stage.addActor(removeButton);
        stage.addActor(returnToMapButton);
    }

    private void refreshItemList() {
        if( selectBoxInventory == null ) {
            boxItemList = fillItemList(itemsHashMap);
            selectBoxInventory = new SelectBox(boxItemList.toArray(), s);
            selectBoxInventory.setBounds(20, GemLord.VIRTUAL_HEIGHT-100, 680, 50);
            selectBoxInventory.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    changeCenterItem();
                }
            });
            changeCenterItem();
        }
        selectBoxInventory.remove();
        boxItemList = fillItemList(itemsHashMap);
        selectBoxInventory = new SelectBox(boxItemList.toArray(), s);
        selectBoxInventory.setBounds(20, GemLord.VIRTUAL_HEIGHT - 100, 680, 50);
        selectBoxInventory.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                changeCenterItem();
            }
        });
        changeCenterItem();
        stage.addActor(selectBoxInventory);
    }

    private void addItemListeners() {
        final ArrayList<BaseItem> allItems = GemLord.getInstance().player.getInventory().getAllItems();
        if( itemInCenter != null ) {
            itemInCenter.clearListeners();
            itemInCenter.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if( itemInCenter.isSelected() )
                        itemInCenter.unselect();
                    else itemInCenter.selected();
                }
            });
        }

        for( int i = 0; i < allItems.size(); i++ ) {
            final BaseItem current = allItems.get(i);
            if( current.isAddedToActionBar() ) {
                current.clearListeners();
                current.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        for( int i = 0; i < allItems.size(); i++ ) {
                            BaseItem testItem = allItems.get(i);
                            if( testItem == current )
                                continue;
                            if (testItem.isSelected() )
                                testItem.unselect();
                        }
                        if( current.isSelected() )
                            current.unselect();
                        else current.selected();
                    }
                });
            }
        }
    }

    public void refresh() {
        refreshItemList();
        changeCenterItem();
        GemLord.getInstance().player.getActionBar().remove();
        GemLord.getInstance().player.getActionBar().addToLoadoutScreen(stage);
    }

    private void changeCenterItem() {
        if( itemInCenter != null ) {
            itemInCenter.unselect();
            itemInCenter.remove();
            itemInCenter.clearListeners();
            itemInCenter = null;
        }

        boxItemList = fillItemList(itemsHashMap);
        if( boxItemList.size() == 0 ) {
            if( selectBoxInventory != null ) {
                selectBoxInventory.remove();
            }
            addItemListeners();
            return;
        }
        itemInCenter = GemLord.getInstance().player.getInventory().getItemByID(itemsHashMap.get(selectBoxInventory.getSelection()));
        itemInCenter.setPosition( (GemLord.VIRTUAL_WIDTH/2)-(itemInCenter.getWidth()/2), 1000);
        itemInCenter.setDrawText(true);
        itemInCenter.unselect();
        addItemListeners();
        stage.addActor(itemInCenter);
    }

    private ArrayList<String> fillItemList(HashMap<String, Integer> itemsHashMap) {
        ArrayList<BaseItem> invItems = GemLord.getInstance().player.getInventory().getAllItems();
        itemsHashMap.clear();
        ArrayList<String> theList = new ArrayList<String>();
        for( int i = 0; i < invItems.size(); i++ ) {
            BaseItem currentItem = invItems.get(i);
            if( currentItem.isAddedToActionBar() )
                continue;
            itemsHashMap.put(currentItem.getName(), currentItem.getID());
            theList.add(currentItem.getName());
        }
        return theList;
    }

    @Override
    public boolean keyDown(int keycode) {
        if( keycode == Input.Keys.ESCAPE || keycode == Input.Keys.BACK ) {
            GemLord.getInstance().soundPlayer.stopLoadoutMusic();
            GemLord.getInstance().setScreen(GemLord.getInstance().mapTraverseScreen);
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
