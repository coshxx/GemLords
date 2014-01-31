package de.cosh.anothermanager.SwapGame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;

import de.cosh.anothermanager.AnotherManager;
import de.cosh.anothermanager.Characters.Enemy;
import de.cosh.anothermanager.Characters.Player;
import de.cosh.anothermanager.GUI.GUIWindow;

/**
 * Created by cosh on 13.01.14.
 */
public class Board extends Group {
	public Cell[][] getCells() {
		return cells;
	}

	public enum BoardState {
		STATE_CHECK, STATE_EMPTY, STATE_FADING, STATE_IDLE, STATE_INACTIVE, STATE_MOVING, STATE_SWAPPING
	}

	public static final int CELL_PAD_X = 0;
	public static final int CELL_PAD_Y = 250;
	public static final int CELL_SIZE = 80;
	public static final int MAX_SIZE_X = 9;
	public static final int MAX_SIZE_Y = 9;
	private final Group backGround;
	private BoardState boardState;
	private final Cell[][] cells;
	private final Group effectGroup;
	private Enemy enemy;
	private final Group foreGround;
	private final GemRemover gemRemover;
	private final GemHandler gemHandler;
	private boolean initialized;
	private boolean justSwapped;
	private GridPoint2 lastSwap;
	private int lastX, lastY;
	private int matchesDuringCurrentMove;
	private final MatchFinder matchFinder;
	private final AnotherManager myGame;
	private Player player;
	private final SwapController swapController;
	private final SpecialEffects sfx;
	private final RespawnRequest respawnRequest;
	private boolean foregroundWindowActive;
	private Array<Gem> uncelledGems;

	private BitmapFont bmf;

	public Board(final AnotherManager myGame) {
		this.myGame = myGame;
		cells = new Cell[MAX_SIZE_X][MAX_SIZE_Y];
		swapController = new SwapController(cells);
		matchFinder = new MatchFinder(cells);

		respawnRequest = new RespawnRequest();
		gemRemover = new GemRemover(cells, respawnRequest);
		gemHandler = new GemHandler(cells, respawnRequest);

		backGround = new Group();
		backGround.setBounds(0, 0, AnotherManager.VIRTUAL_WIDTH, AnotherManager.VIRTUAL_HEIGHT);
		foreGround = new Group();
		foreGround.setBounds(0, 0, AnotherManager.VIRTUAL_WIDTH, AnotherManager.VIRTUAL_HEIGHT);
		effectGroup = new Group();
		effectGroup.setBounds(0, 0, AnotherManager.VIRTUAL_WIDTH, AnotherManager.VIRTUAL_HEIGHT);
		final Image backImage = new Image(AnotherManager.assets.get("data/textures/background.png", Texture.class));
		backImage.setBounds(0, 0, AnotherManager.VIRTUAL_WIDTH, AnotherManager.VIRTUAL_HEIGHT);
		backGround.addActor(backImage);
		addActor(backGround);
		addActor(foreGround);
		addActor(effectGroup);
		effectGroup.setTouchable(Touchable.disabled);
		boardState = BoardState.STATE_EMPTY;
		initialized = false;
		justSwapped = false;
		sfx = new SpecialEffects();
		foregroundWindowActive = false;
		uncelledGems = new Array<Gem>();
		Skin s = AnotherManager.getInstance().assets.get("data/ui/uiskin.json", Skin.class);
		bmf = s.getFont("default-font");
	}

	public void addToUncelledGems(Gem g) {
		uncelledGems.add(g);
	}

	public void draw(SpriteBatch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		bmf.setColor(1f, 1f, 1f, 1f);
		bmf.draw(batch, boardState.toString(), 100, 100);
	}

	public Group getEffectGroup() {
		return effectGroup;
	}

	private GridPoint2 convertToBoardIndex(final Vector2 position) {
		position.x -= CELL_PAD_X;
		position.x /= CELL_SIZE;
		position.y -= CELL_PAD_Y;
		position.y /= CELL_SIZE;
		final int gemX = (int) position.x, gemY = (int) position.y;
		final GridPoint2 point = new GridPoint2(gemX, gemY);
		return point;
	}

	public void drawCells(SpriteBatch batch, float parentAlpha) {
		for (int x = 0; x < MAX_SIZE_X; x++) {
			for (int y = 0; y < MAX_SIZE_Y; y++) {
				// cells[x][y].draw(batch, 0.5f);
			}
		}
	}

	public void drawGems(SpriteBatch batch, float parentAlpha) {
		for (int x = 0; x < MAX_SIZE_X; x++) {
			for (int y = 0; y < MAX_SIZE_Y; y++) {
				cells[x][y].getGem().draw(batch, parentAlpha);
			}
		}
	}

	private void fillWithRandomGems() {
		for (int x = 0; x < MAX_SIZE_X; x++) {
			for (int y = 0; y < MAX_SIZE_Y; y++) {
				cells[x][y] = new Cell(myGame, x, y);
				cells[x][y].setColor(1f, 1f, 1f, 0.35f);
				cells[x][y].setBounds(CELL_PAD_X + (x * CELL_SIZE) + AnotherManager.VIRTUAL_WIDTH, CELL_PAD_Y + (y * CELL_SIZE), 80, 80);
				cells[x][y].addAction(Actions.sequence(Actions.moveTo(CELL_PAD_X + (x * CELL_SIZE), CELL_PAD_Y + (y * CELL_SIZE), 0.50f),
						Actions.moveBy(10f, 0f, 0.1f), Actions.moveBy(-10f, 0f, 0.1f)));
				cells[x][y].putGem(gemHandler.getGemFactory().newRandomGem());
				cells[x][y].getGem().addAction(
						Actions.sequence(Actions.moveTo(CELL_PAD_X + (x * CELL_SIZE), CELL_PAD_Y + (y * CELL_SIZE), 0.50f),
								Actions.moveBy(10f, 0f, 0.1f), Actions.moveBy(-10f, 0f, 0.1f)));
				// AnotherManager.soundPlayer.playSlideIn();
				backGround.addActor(cells[x][y]);
				foreGround.addActor(cells[x][y].getGem());
			}
		}
	}

	private void prepareEnemy() {
		enemy = myGame.enemyManager.getSelectedEnemy();
		enemy.addToBoard(foreGround);

	}

	private void preparePlayer() {
		player = myGame.player;
		player.init(100 + player.getItemBuffsHP());
		player.addToBoard(foreGround);
	}

	public void swapTo(final Vector2 flingStartPosition, final int x, final int y) {
		if (boardState != BoardState.STATE_IDLE)
			return;
		lastX = x;
		lastY = y;
		justSwapped = true;
		matchesDuringCurrentMove = 0;
		final GridPoint2 start = convertToBoardIndex(flingStartPosition);
		lastSwap = start;

		GridPoint2 end = new GridPoint2(start);
		end.x += x;
		end.y += y;

		if (start.x < 0 || start.x >= MAX_SIZE_X || start.y < 0 || start.y >= MAX_SIZE_Y)
			return;

		if (end.x < 0 || end.x >= MAX_SIZE_X || end.y < 0 || end.y >= MAX_SIZE_Y)
			return;

		swapController.swap(start, x, y);
		boardState = BoardState.STATE_SWAPPING;
	}

	public void init() {
		if (!initialized) {
			initialized = true;
			fillWithRandomGems();
			while (matchFinder.hasMatches()) {
				backGround.clear();
				foreGround.clear();
				final Image backImage = new Image(AnotherManager.assets.get("data/textures/background.png", Texture.class));
				backGround.addActor(backImage);
				fillWithRandomGems();
			}
			prepareEnemy();
			preparePlayer();
			boardState = BoardState.STATE_CHECK;
			matchesDuringCurrentMove = 0;
		}
	}

	public void update(final float delta) {
		if (boardState == BoardState.STATE_CHECK) {
			MatchResult result = matchFinder.markAllMatchingGems();
			if (result.howMany > 0) {
				if (result.conversion) {
					AnotherManager.soundPlayer.playConvert();
				}
				AnotherManager.soundPlayer.playDing(matchesDuringCurrentMove++);
				result.howMany = 0;
				result = gemRemover.fadeMarkedGems(effectGroup);
				enemy.damage(result.howMany);
				if (result.specialExplo) {
					AnotherManager.soundPlayer.playWoosh();
				}
				boardState = BoardState.STATE_FADING;
			} else {
				if (matchesDuringCurrentMove >= 8) {
					AnotherManager.soundPlayer.playGodlike();
					sfx.playUnstoppable(effectGroup);
				} else if (matchesDuringCurrentMove >= 6) {
					AnotherManager.soundPlayer.playUnstoppable();
					sfx.playUnstoppable(effectGroup);
				} else if (matchesDuringCurrentMove >= 4) {
					AnotherManager.soundPlayer.playImpressive();
					sfx.playUnstoppable(effectGroup);
				}

			}
		}

		if (boardState == BoardState.STATE_MOVING) {
			for (int i = 0; i < uncelledGems.size; i++) {
				Gem g = uncelledGems.get(i);
				g.update(delta);
				if (g.foundACell()) {
					uncelledGems.removeIndex(i);
					i--;
				}
			}
			
			for (int x = 0; x < Board.MAX_SIZE_X; x++) {
				for (int y = 0; y < Board.MAX_SIZE_Y; y++) {
					Gem g = cells[x][y].getGem();
					if (g == null)
						continue;
					if (cells[x][y].isEmpty())
						continue;
					g.update(delta);
				}
			}
		}

		updateBoardState();
	}

	public void pressedBack() {
		// TODO
		// add giveupQuestion......
		player.setHealth(0);
	}

	private void updateBoardState() {
		boolean stillMovement = false;
		for (int x = 0; x < MAX_SIZE_X; x++) {
			for (int y = 0; y < MAX_SIZE_Y; y++) {
				if (cells[x][y].isEmpty())
					stillMovement = true;
				Gem g = cells[x][y].getGem();
				if (g == null)
					continue;
				if (g.getActions().size > 0)
					stillMovement = true;
			}
		}

		gemHandler.respawnAndApplyGravity(foreGround);

		if (boardState == BoardState.STATE_IDLE) {
			checkPlayerAndEnemyStatus();
		}

		if (!stillMovement && boardState == BoardState.STATE_FADING) {
			gemRemover.removeFadedGems(myGame, effectGroup);
			gemHandler.respawnAndApplyGravity(foreGround);
			boardState = BoardState.STATE_MOVING;
		} else if (!stillMovement && boardState == BoardState.STATE_SWAPPING) {
			if (!AnotherManager.DEBUGMODE) {
				if (!matchFinder.hasMatches()) {
					boardState = BoardState.STATE_MOVING;
					swapController.swap(lastSwap, lastX, lastY);
				}
			}
			boardState = BoardState.STATE_CHECK;
		} else if (!stillMovement && boardState == BoardState.STATE_CHECK) {
			if (justSwapped) {
				justSwapped = false;
				player.turn();
				enemy.turn(player);
			}
			boardState = BoardState.STATE_IDLE;
		} else if (!stillMovement && boardState == BoardState.STATE_MOVING) {
			boardState = BoardState.STATE_CHECK;
		}
	}

	private void checkPlayerAndEnemyStatus() {
		if (foregroundWindowActive)
			return;
		if (enemy.getHealth() <= 0) {
			foregroundWindowActive = true;
			effectGroup.setTouchable(Touchable.enabled);
			final GUIWindow guiWindow = new GUIWindow(getStage());
			guiWindow.createVictoryWindow(foreGround, backGround, effectGroup);
			AnotherManager.soundPlayer.stopGameMusic();
			AnotherManager.soundPlayer.playVictorySound();
			boardState = BoardState.STATE_INACTIVE;
		} else if (player.getHealth() <= 0) {
			foregroundWindowActive = true;
			effectGroup.setTouchable(Touchable.enabled);
			final GUIWindow guiWindow = new GUIWindow(getStage());
			guiWindow.createDefeatWindow(foreGround, backGround, effectGroup);
			AnotherManager.soundPlayer.stopGameMusic();
			AnotherManager.soundPlayer.playLoseSound();
			boardState = BoardState.STATE_INACTIVE;
		}
	}

	public BoardState getBoardState() {
		return boardState;
	}
}
