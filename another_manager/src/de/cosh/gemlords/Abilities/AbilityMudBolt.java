package de.cosh.gemlords.Abilities;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import de.cosh.gemlords.Characters.BaseCharacter;
import de.cosh.gemlords.Characters.Damage;
import de.cosh.gemlords.CustomActions.PlayFireBallEndAction;
import de.cosh.gemlords.GemLord;
import de.cosh.gemlords.SwapGame.Board;
import de.cosh.gemlords.SwapGame.Cell;
import de.cosh.gemlords.SwapGame.Gem;
import de.cosh.gemlords.SwapGame.ParticleActor;

/**
 * Created by cosh on 15.01.14.
 */
public class AbilityMudBolt extends BaseAbility {
    private int xTarget, yTarget;
    private float x, y;
    private boolean fireballInTheAir;
    private ParticleActor effect;

    public AbilityMudBolt() {
        abilityImageLocation = "abilitymudbolt";
        TextureAtlas atlas = GemLord.assets.get("data/textures/pack.atlas", TextureAtlas.class);
        abilityImage = new Image(atlas.findRegion(abilityImageLocation));
        setAbilityDamage(0);
        fireballInTheAir = false;
        effect = new ParticleActor();
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
            GemLord.soundPlayer.playMudBolt();
            fireballInTheAir = true;
            TextureAtlas atlas = GemLord.assets.get("data/textures/pack.atlas", TextureAtlas.class);
            Image fireBall = new Image(atlas.findRegion(abilityImageLocation));
            fireBall.setPosition(abilityImage.getX(), abilityImage.getY());
            fireBall.addAction(Actions.sequence(
                    Actions.moveTo(x, y, 0.5f),
                    new PlayFireBallEndAction(),
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

                        effect.newParticleEffect(gem.getX() + gem.getWidth() / 2, gem.getY() + gem.getHeight() / 2);
                        effectGroup.addActor(effect);

                        gem.remove();
                        gem.unmarkRemoval();
                        gem.setToNone();

                        damage.damage++;

                        GemLord.getInstance().gameScreen.getBoard().getRespawnRequest().addOne(xStart);
                    }
                }

                float temp = damage.damage;
                temp *= 1.5f;
                damage.damage = (int)temp;

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
