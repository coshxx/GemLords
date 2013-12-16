package de.cosh.anothermanager;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

/**
 * Created by cosh on 16.12.13.
 */
public class SwapGame extends Table{
    private AnotherManager myGame;

    private int MAX_SIZE_X = 9;
    private int MAX_SIZE_Y = 9;
    private int CELL_SIZE = 70;
    private int PADDING_LEFT = 45;
    private int PADDING_BOT = 45;

    private Cell[][] board;
    private Image backGroundImage;


    public SwapGame(AnotherManager game) {
        myGame = game;
        setBounds(0, 0, myGame.VIRTUAL_WIDTH, myGame.VIRTUAL_HEIGHT);
        setClip(true);
    }

    public void init() {
        board = new Cell[MAX_SIZE_X][MAX_SIZE_Y];
        backGroundImage = new Image(myGame.assets.get("data/background.png", Texture.class));
        backGroundImage.setBounds(0, 0, myGame.VIRTUAL_WIDTH, myGame.VIRTUAL_HEIGHT);
        this.addActor(backGroundImage);
        for( int x = 0; x < MAX_SIZE_X; x++ ) {
            for( int y = 0; y < MAX_SIZE_Y; y++) {
                board[x][y] = new Cell(myGame.assets.get("data/cell_back.png", Texture.class));
                board[x][y].setPosition(PADDING_LEFT + (x * CELL_SIZE), PADDING_BOT + (y * CELL_SIZE));
                board[x][y].setColor(1f, 1f, 1f, 0.33f);

                board[x][y].setOccupant(new Gem(myGame, myGame.assets.get("data/ball_red.png", Texture.class)));
                board[x][y].getOccupant().setPosition(PADDING_LEFT + (x * CELL_SIZE), PADDING_BOT + (y * CELL_SIZE));

                addActor(board[x][y]);
                addActor(board[x][y].getOccupant());
            }
        }
    }

    public void swapToTheRight(Vector2 flingStartPosition) {
        System.out.println("swap right");
    }

    public void swapToTheLeft(Vector2 flingStartPosition) {
        System.out.println("swap left");
    }

    public void swapToTheTop(Vector2 flingStartPosition) {
        System.out.println("swap top");
    }

    public void swapToTheBottom(Vector2 flingStartPosition) {
        System.out.println("swap bot");
    }
}
