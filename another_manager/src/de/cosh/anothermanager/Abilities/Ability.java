package de.cosh.anothermanager.Abilities;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import de.cosh.anothermanager.AnotherManager;
import de.cosh.anothermanager.Characters.BaseCharacter;

/**
 * Created by cosh on 14.01.14.
 */
public abstract interface Ability {
    // use ability
    public abstract boolean fire(BaseCharacter target);

    // once every turn
    public abstract void update();

    // mhh
    public abstract Image getImage();

    public abstract int getCooldown();
}
