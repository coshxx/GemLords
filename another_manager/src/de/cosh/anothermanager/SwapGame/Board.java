package de.cosh.anothermanager.SwapGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;
import com.badlogic.gdx.utils.Array;
import de.cosh.anothermanager.GemLord;
import de.cosh.anothermanager.Characters.Damage;
import de.cosh.anothermanager.Characters.Enemy;
import de.cosh.anothermanager.Characters.Player;
import de.cosh.anothermanager.GUI.GUIWindow;

/**
 * Created by cosh on 13.01.14.
 */
public class Board extends Group {
    public static final int CELL_PAD_X = 0;
    public static final int CELL_PAD_Y = 280;
    public static final int CELL_SIZE = 80;
    public static final int MAX_SIZE_X = 9;
    public static final int MAX_SIZE_Y = 9;
    private final Group backGround;
    private final Cell[][] cells;
    private final Group effectGroup;
    private final Group foreGround;
    private final Group gemGroup;
    private final GemRemover gemRemover;
    private final GemHandler gemHandler;
    private final MatchFinder matchFinder;
    private final GemLord myGame;
    private final SwapController swapController;
    private final SpecialEffects sfx;
    private final RespawnRequest respawnRequest;
    private BoardState boardState;
    private Enemy enemy;
    private Player player;
    private boolean initialized;
    private boolean justSwapped;
    private GridPoint2 lastSwap;
    private int lastX, lastY;
    private int matchesDuringCurrentMove;
    private boolean foregroundWindowActive;
    private Array<Gem> uncelledGems;
    private TurnIndicator turnIndicator;
    private BitmapFont bmf;

    public Board(final GemLord myGame) {
        this.myGame = myGame;
        cells = new Cell[MAX_SIZE_X][MAX_SIZE_Y];
        swapController = new SwapController(cells);
        matchFinder = new MatchFinder(cells);

        respawnRequest = new RespawnRequest();
        gemRemover = new GemRemover(cells, respawnRequest);
        gemHandler = new GemHandler(cells, respawnRequest);

        backGround = new Group();
        backGround.setBounds(0, 0, GemLord.VIRTUAL_WIDTH, GemLord.VIRTUAL_HEIGHT);
        gemGroup = new Group();
        gemGroup.setBounds(0, 0, GemLord.VIRTUAL_WIDTH, GemLord.VIRTUAL_HEIGHT);
        foreGround = new Group();
        foreGround.setBounds(0, 0, GemLord.VIRTUAL_WIDTH, GemLord.VIRTUAL_HEIGHT);
        effectGroup = new Group();
        effectGroup.setBounds(0, 0, GemLord.VIRTUAL_WIDTH, GemLord.VIRTUAL_HEIGHT);
        final Image backImage = new Image(GemLord.assets.get("data/textures/background.png", Texture.class));
        //backImage.setBounds(0, 0, GemLord.VIRTUAL_WIDTH, GemLord.VIRTUAL_HEIGHT);
        backImage.setFillParent(true);
        backGround.addActor(backImage);
        addActor(backGround);
        addActor(foreGround);
        addActor(gemGroup);
        addActor(effectGroup);
        effectGroup.setTouchable(Touchable.disabled);
        gemGroup.setTouchable(Touchable.disabled);
        backGround.setTouchable(Touchable.disabled);
        boardState = BoardState.STATE_EMPTY;
        initialized = false;
        justSwapped = false;
        sfx = new SpecialEffects();
        foregroundWindowActive = false;
        uncelledGems = new Array<Gem>();
        Skin s = GemLord.getInstance().assets.get("data/ui/uiskin.json", Skin.class);
        bmf = s.getFont("default-font");
        turnIndicator = new TurnIndicator();
    }

    public Cell[][] getCells() {
        return cells;
    }

    public void addToUncelledGems(Gem g) {
        uncelledGems.add(g);
    }

    public void draw(SpriteBatch batch, float parentAlpha) {
        //super.draw(batch, parentAlpha);
        backGround.draw(batch, parentAlpha);
        foreGround.draw(batch, parentAlpha);

		Stage stage = getStage();
		GemLord myGame = GemLord.getInstance();

		final Vector2 begin = stage.stageToScreenCoordinates(new Vector2(0, GemLord.VIRTUAL_HEIGHT - 277));
		final Vector2 end = stage.stageToScreenCoordinates(new Vector2(GemLord.VIRTUAL_WIDTH, GemLord.VIRTUAL_HEIGHT - 720));
		final Rectangle scissor = new Rectangle();
		final Rectangle clipBounds = new Rectangle(begin.x, begin.y, end.x, end.y);
		ScissorStack.calculateScissors(myGame.camera, 0, 0, GemLord.VIRTUAL_WIDTH, GemLord.VIRTUAL_HEIGHT, batch.getTransformMatrix(),
                clipBounds, scissor);
		batch.flush();
		ScissorStack.pushScissors(scissor);
        gemGroup.draw(batch, parentAlpha);
		batch.flush();
		ScissorStack.popScissors();

        player.draw(batch, parentAlpha);
        enemy.draw(batch, parentAlpha);
        effectGroup.draw(batch, parentAlpha);
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

    private void fillWithRandomGems() {
        for (int x = 0; x < MAX_SIZE_X; x++) {
            for (int y = 0; y < MAX_SIZE_Y; y++) {
                cells[x][y] = new Cell(myGame, x, y);
                cells[x][y].setColor(1f, 1f, 1f, 0.35f);
                cells[x][y].setBounds(CELL_PAD_X + (x * CELL_SIZE) + GemLord.VIRTUAL_WIDTH, CELL_PAD_Y + (y * CELL_SIZE), 80, 80);
                cells[x][y].addAction(Actions.sequence(Actions.moveTo(CELL_PAD_X + (x * CELL_SIZE), CELL_PAD_Y + (y * CELL_SIZE), 0.50f),
                        Actions.moveBy(10f, 0f, 0.1f), Actions.moveBy(-10f, 0f, 0.1f)));
                cells[x][y].putGem(gemHandler.getGemFactory().newRandomGem());
                cells[x][y].getGem().addAction(
                        Actions.sequence(Actions.moveTo(CELL_PAD_X + (x * CELL_SIZE), CELL_PAD_Y + (y * CELL_SIZE), 0.50f),
                                Actions.moveBy(10f, 0f, 0.1f), Actions.moveBy(-10f, 0f, 0.1f)));
                // GemLord.soundPlayer.playSlideIn();
                backGround.addActor(cells[x][y]);
                gemGroup.addActor(cells[x][y].getGem());
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
        if( !turnIndicator.isPlayerTurn() )
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
            myGame.afterActionReport.reset();
            while (matchFinder.hasMatches()) {
                backGround.clear();
                foreGround.clear();
                gemGroup.clear();
                final Image backImage = new Image(GemLord.assets.get("data/textures/background.png", Texture.class));
                backImage.setFillParent(true);
                backGround.addActor(backImage);
                fillWithRandomGems();
            }
            prepareEnemy();
            preparePlayer();
            turnIndicator.addToBoard(foreGround);
            boardState = BoardState.STATE_CHECK;
            matchesDuringCurrentMove = 0;
        }
    }

    void turnComplete(float delay) {
        addAction(Actions.sequence(
                Actions.delay(delay),
                Actions.run(new Runnable() {

            @Override
            public void run() {
                turnIndicator.switchPlayers();
            }
        })));
    }

    public void update(float delta) {
        if( boardState == BoardState.STATE_IDLE ) {
            checkPlayerAndEnemyStatus();
            if( justSwapped && boardState == BoardState.STATE_IDLE) {
                justSwapped = false;
                matchesDuringCurrentMove = 0;
                turnComplete(0.0f);
                player.turn();
                enemy.turn(player);
                boardState = BoardState.STATE_MOVING;
                return;
            }
        }

        if( !turnIndicator.isPlayerTurn())
            enemy.update(delta);

        if (boardState == BoardState.STATE_CHECK) {
            MatchResult result = matchFinder.markAllMatchingGems();
            if (result.howMany > 0) {
                if (result.conversion) {
                    GemLord.soundPlayer.playConvert();
                }

                GemLord.soundPlayer.playDing(matchesDuringCurrentMove++);
                if( turnIndicator.isPlayerTurn() )
                    myGame.afterActionReport.setLongestCombo(matchesDuringCurrentMove);
                result.howMany = 0;
                result = gemRemover.fadeMarkedGems(effectGroup);
                Damage damage = new Damage();
                damage.damage = result.howMany;
                if( turnIndicator.isPlayerTurn() ) {
                    damage.damage += player.getItemDamageBuffs(damage);
                    enemy.damage(damage);
                }
                else {
                    player.damage(damage);
                }
                if (result.specialExplo) {
                    GemLord.soundPlayer.playWoosh();
                }
                boardState = BoardState.STATE_FADING;
            } else {
                boardState = BoardState.STATE_IDLE;
                if( !turnIndicator.isPlayerTurn() ) {
                    if( enemy.allAbilitiesDone() )
                        turnComplete(0f);
                }
                /*
                // TODO: meh
                if( !turnIndicator.isPlayerTurn()) {
                    addAction(Actions.run(new Runnable() {

                        @Override
                        public void run() {
                            turnComplete(0.5f);
                        }
                    }));
                }
                */
            }
        } else if (boardState == BoardState.STATE_MOVING) {
            updateGems(delta);
            if (!gemsHaveWork()) {
                boardState = BoardState.STATE_CHECK;
            }
        } else if (boardState == BoardState.STATE_SWAPPING) {
            if (!gemsHaveWork()) {
                if( justSwapped ) {
                    if( !matchFinder.hasMatches() ) {
                        swapController.swap(lastSwap, lastX, lastY);
                    }
                }
                boardState = BoardState.STATE_CHECK;
            }
        } else if (boardState == BoardState.STATE_FADING) {
            if (gemRemover.doneFading()) {
                gemRemover.removeFadedGems(myGame, effectGroup);
                gemHandler.respawnAndApplyGravity(gemGroup);
                boardState = BoardState.STATE_MOVING;
            }
        }
    }
    private void updateGems(float delta) {
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
    }

    private boolean gemsHaveWork() {
        boolean gemsHaveWork = false;

        for (int i = 0; i < uncelledGems.size; i++) {
            if (uncelledGems.get(i).isFalling())
                gemsHaveWork = true;
        }

        for (int x = 0; x < Board.MAX_SIZE_X; x++) {
            for (int y = 0; y < Board.MAX_SIZE_Y; y++) {
                if (cells[x][y].isEmpty())
                    gemsHaveWork = true;

                Gem g = cells[x][y].getGem();

                if (g == null)
                    continue;

                if (g.getActions().size > 0)
                    gemsHaveWork = true;

                if (g.isFalling())
                    gemsHaveWork = true;
            }
        }
        return gemsHaveWork;
    }

    public void pressedBack() {
        // TODO
        // add giveupQuestion......
        player.setHealth(0);
    }

    private void checkPlayerAndEnemyStatus() {
        if (foregroundWindowActive)
            return;
        if (enemy.getHealth() <= 0) {
            foregroundWindowActive = true;
            effectGroup.setTouchable(Touchable.enabled);
            final GUIWindow guiWindow = new GUIWindow(getStage());
            guiWindow.createVictoryWindow(foreGround, backGround, effectGroup);
            GemLord.soundPlayer.stopGameMusic();
            GemLord.soundPlayer.playVictorySound();
            boardState = BoardState.STATE_INACTIVE;
        } else if (player.getHealth() <= 0) {
            foregroundWindowActive = true;
            effectGroup.setTouchable(Touchable.enabled);
            final GUIWindow guiWindow = new GUIWindow(getStage());
            guiWindow.createDefeatWindow(foreGround, backGround, effectGroup);
            GemLord.soundPlayer.stopGameMusic();
            GemLord.soundPlayer.playLoseSound();
            boardState = BoardState.STATE_INACTIVE;
        }
    }

    public BoardState getBoardState() {
        return boardState;
    }

    public RespawnRequest getRespawnRequest() {
        return respawnRequest;
    }

    public GemHandler getGemHandler() {
        return gemHandler;
    }

    public Group getGemGroup() {
        return gemGroup;
    }

    public MatchFinder getMatchFinder() {
        return matchFinder;
    }

    public SwapController getSwapController() {
        return swapController;
    }

    public Enemy getEnemy() {
        return enemy;
    }

    public enum BoardState {
        STATE_CHECK, STATE_EMPTY, STATE_FADING, STATE_IDLE, STATE_INACTIVE, STATE_MOVING, STATE_ENEMYTURN, STATE_SWAPPING
    }
}
