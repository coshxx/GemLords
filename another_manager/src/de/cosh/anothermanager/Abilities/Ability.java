package de.cosh.anothermanager.Abilities;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import de.cosh.anothermanager.AnotherManager;
import de.cosh.anothermanager.Characters.BaseCharacter;

/**
 * Created by cosh on 14.01.14.
 */
public interface Ability {
    // use ability
    public boolean fire(BaseCharacter target);

    // once every turn
    public void update();

    // mhh
    public Image getImage();

    public int getCooldown();
}
