package de.cosh.anothermanager.SwapGame;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import de.cosh.anothermanager.AnotherManager;

/**
 * Created by cosh on 13.01.14.
 */
class GemRemover {
	private final Cell[][] cells;
	private final float FADE_TIME = 0.25f;
	private final MatchResult result;
	private final RespawnRequest respawnRequest;

	public GemRemover(final Cell[][] cells, RespawnRequest respawnRequest) {
		this.cells = cells;
		this.result = new MatchResult();
		this.respawnRequest = respawnRequest;
	}

    public boolean doneFading() {
        boolean doneFading = true;

        for( int x = 0; x < Board.MAX_SIZE_X; x++ ) {
            for (int y = 0; y < Board.MAX_SIZE_Y; y++) {
                Gem g = cells[x][y].getGem();
                if( g == null )
                    continue;

                if( g.getActions().size > 0 )
                    doneFading = false;
            }
        }
        return doneFading;
    }

	public MatchResult fadeMarkedGems(final Group effectGroup) {
		result.howMany = 0;
		result.specialExplo = false;
		result.conversion = false;
		for (int x = 0; x < Board.MAX_SIZE_X; x++) {
			for (int y = 0; y < Board.MAX_SIZE_Y; y++) {
				final Gem rem = cells[x][y].getGem();
				if (rem == null)
					continue;
				if (rem.isMarkedForSuperSpecialConversion()) {
					rem.convertToSuperSpecial();
					final ParticleActor effect = new ParticleActor(rem.getX() + rem.getWidth() / 2, rem.getY() + rem.getHeight() / 2);
					effectGroup.addActor(effect);
					rem.unmarkRemoval();
				} else if (rem.isMarkedForSpecialConversion()) {
					rem.convertToSpecialGem();
					final ParticleActor effect = new ParticleActor(rem.getX() + rem.getWidth() / 2, rem.getY() + rem.getHeight() / 2);
					effectGroup.addActor(effect);
					rem.unmarkRemoval();
				} else if (rem.isMarkedForRemoval()) {
					if (rem.isSuperSpecialGem()) {
						superSpecialExplode(x, y, effectGroup);
						result.specialExplo = true;
					} else if (rem.isSpecialHorizontalGem()) {
						specialRowExplode(y, effectGroup);
						result.specialExplo = true;
					} else {
						if (rem.isSpecialVerticalGem()) {
							specialColExplode(x, effectGroup);
							result.specialExplo = true;
						}
					}
					if (rem.getActions().size > 0) {
						continue;
					}
					result.howMany++;
					rem.addAction(Actions.parallel(Actions.scaleTo(0.0f, 0.0f, FADE_TIME),
							Actions.moveBy(rem.getWidth() / 2, rem.getHeight() / 2, FADE_TIME)));
				}
			}
		}
		return result;
	}

	private void superSpecialExplode(int x, int y, final Group effectGroup) {
		for (int exploStartX = x - 2; exploStartX < x + 3; exploStartX++) {
			for (int exploStartY = y - 2; exploStartY < y + 3; exploStartY++) {
				if (exploStartY < 0)
					continue;
				if (exploStartY >= Board.MAX_SIZE_Y)
					continue;
				if (exploStartX < 0)
					continue;
				if (exploStartX >= Board.MAX_SIZE_X)
					continue;
				final Gem rem = cells[exploStartX][exploStartY].getGem();
				if( rem == null )
					continue;
				rem.markGemForRemoval();
				final ParticleActor effect = new ParticleActor(rem.getX() + rem.getWidth() / 2, rem.getY() + rem.getHeight() / 2);
				effectGroup.addActor(effect);
				if (rem.getActions().size > 0 || rem.isMarkedForSpecialConversion() || rem.isMarkedForSuperSpecialConversion()) {
					continue;
				}
				result.howMany++;
				rem.addAction(Actions.parallel(Actions.scaleTo(0.0f, 0.0f, FADE_TIME),
						Actions.moveBy(rem.getWidth() / 2, rem.getHeight() / 2, FADE_TIME)));

			}
		}
	}

	public void removeFadedGems(final AnotherManager myGame, final Group effectGroup) {
		for (int x = 0; x < Board.MAX_SIZE_X; x++) {
			for (int y = 0; y < Board.MAX_SIZE_Y; y++) {
				final Gem rem = cells[x][y].getGem();
				if (rem == null)
					continue;
				if (rem.isDisabled()) {
					rem.getActions().clear();
					continue;
				}
				if (rem.isMarkedForRemoval()) {
					final StarEffect effect = new StarEffect(myGame);
					effect.spawnStars(rem.getX(), rem.getY(), effectGroup);
					rem.remove();
					rem.unmarkRemoval();
					rem.setToNone();
					cells[x][y].setEmpty(true);
					respawnRequest.addOne(x);
				}
			}
		}
	}

	private void specialRowExplode(final int y, final Group effectGroup) {
		for (int x = 0; x < Board.MAX_SIZE_X; x++) {
			final Gem rem = cells[x][y].getGem();
			if (rem == null)
				continue;
			if (rem.isSpecialVerticalGem())
				specialColExplode(x, effectGroup);
			rem.markGemForRemoval();
			final ParticleActor effect = new ParticleActor(rem.getX() + rem.getWidth() / 2, rem.getY() + rem.getHeight() / 2);
			effectGroup.addActor(effect);
			if (rem.getActions().size > 0 || rem.isMarkedForSpecialConversion() || rem.isMarkedForSuperSpecialConversion()) {
				continue;
			}
			result.howMany++;
			rem.addAction(Actions.parallel(Actions.scaleTo(0.0f, 0.0f, FADE_TIME), Actions.moveBy(rem.getWidth() / 2, rem.getHeight() / 2, FADE_TIME)));
		}
	}

	private void specialColExplode(final int x, Group effectGroup) {
		for (int y = 0; y < Board.MAX_SIZE_Y; y++) {
			final Gem rem = cells[x][y].getGem();
			if( rem == null )
				continue;
			if (rem.isSpecialHorizontalGem())
				specialRowExplode(y, effectGroup);
			rem.markGemForRemoval();
			final ParticleActor effect = new ParticleActor(rem.getX() + rem.getWidth() / 2, rem.getY() + rem.getHeight() / 2);
			effectGroup.addActor(effect);
			if (rem.getActions().size > 0 || rem.isMarkedForSpecialConversion() || rem.isMarkedForSuperSpecialConversion()) {
				continue;
			}
			result.howMany++;
			rem.addAction(Actions.parallel(Actions.scaleTo(0.0f, 0.0f, FADE_TIME), Actions.moveBy(rem.getWidth() / 2, rem.getHeight() / 2, FADE_TIME)));
		}
	}
}
