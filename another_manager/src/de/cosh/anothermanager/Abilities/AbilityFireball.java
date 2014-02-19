package de.cosh.anothermanager.Abilities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import de.cosh.anothermanager.Characters.BaseCharacter;
import de.cosh.anothermanager.Characters.Damage;
import de.cosh.anothermanager.GemLord;
import de.cosh.anothermanager.SwapGame.Board;
import de.cosh.anothermanager.SwapGame.Cell;
import de.cosh.anothermanager.SwapGame.Gem;
import de.cosh.anothermanager.SwapGame.ParticleActor;

/**
 * Created by cosh on 15.01.14.
 */
public class AbilityFireball extends BaseAbility {
    private int xTarget, yTarget;
    private float x, y;
    private boolean fireballInTheAir;

    public AbilityFireball() {
        abilityImageLocation = "data/textures/abilityfireball.png";
        abilityImage = new Image(GemLord.assets.get(abilityImageLocation, Texture.class));
        setAbilityDamage(0);
        fireballInTheAir = false;
    }

    @Override
    public boolean fire(final BaseCharacter target) {
        return false;
    }

    public void update(float delta) {
        passedTime += delta;

        if (getCurrentCooldown() == 0 && !fireballInTheAir) {

            xTarget = MathUtils.random(1, Board.MAX_SIZE_X-2);
            yTarget = MathUtils.random(1, Board.MAX_SIZE_Y-2);

            x = Board.CELL_PAD_X + (xTarget * Board.CELL_SIZE);
            x += Board.CELL_SIZE / 2;
            x -= abilityImage.getWidth() / 4;
            y = Board.CELL_PAD_Y + (yTarget * Board.CELL_SIZE);
            y += Board.CELL_SIZE / 2;
            y -= abilityImage.getWidth() / 4;

            fireballInTheAir = true;
            Image fireBall = new Image(GemLord.assets.get(abilityImageLocation, Texture.class));
            fireBall.setPosition(abilityImage.getX(), abilityImage.getY());
            fireBall.addAction(Actions.sequence(
                    Actions.moveTo(x, y, 0.5f),
                    Actions.fadeOut(0.15f),
                    Actions.removeActor()));

            GemLord.getInstance().gameScreen.getBoard().getEffectGroup().addActor(fireBall);
        }
        if (fireballInTheAir) {
            if (passedTime > 0.5f) {
                super.fire(GemLord.getInstance().player);
                passedTime = 0f;
                fireballInTheAir = false;
                Cell[][] cells = GemLord.getInstance().gameScreen.getBoard().getCells();
                Group effectGroup = GemLord.getInstance().gameScreen.getBoard().getEffectGroup();

                int xEnd = xTarget+1;
                int yEnd = yTarget+1;

                Damage damage = new Damage();

                for( int xStart = xTarget-1; xStart <= xEnd; xStart++ ) {
                    for( int yStart = yTarget-1; yStart <= yEnd; yStart++ ) {
                        if( xStart < 0 || yStart < 0 )
                            continue;

                        if( yStart >= Board.MAX_SIZE_Y || xStart >= Board.MAX_SIZE_Y )
                            continue;

                        Gem gem = cells[xStart][yStart].getGem();
                        cells[xStart][yStart].setEmpty(true);

                        ParticleActor effect = new ParticleActor(gem.getX() + gem.getWidth() / 2, gem.getY() + gem.getHeight() / 2);
                        effectGroup.addActor(effect);

                        gem.remove();
                        gem.unmarkRemoval();
                        gem.setToNone();

                        damage.damage++;

                        GemLord.getInstance().gameScreen.getBoard().getRespawnRequest().addOne(xStart);
                    }
                }

                if( MathUtils.randomBoolean() ) {
                    damage.damage += damage.damage;
                    damage.isCrit = true;
                }

                GemLord.getInstance().player.damage(damage);
                owner.setRequestMovementUpdate(true);
                Group gemGroup = GemLord.getInstance().gameScreen.getBoard().getGemGroup();
                GemLord.getInstance().gameScreen.getBoard().getGemHandler().respawn(gemGroup);
            }
        }
    }
}
