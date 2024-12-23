package com.cursedecho.characters.enemies;

import com.cursedecho.characters.*;
import javafx.scene.image.Image;
import java.util.ArrayList;
import java.util.List;

public class Bat extends Enemy {
    private List<Image> idleFrames = new ArrayList<>();
    private List<Image> chaseFrames = new ArrayList<>();
    private List<Image> attackFrames = new ArrayList<>();

    public Bat(Player player) {
        super(128,128,25,0.7,5,0.7,30,0, player);
        state = State.IDLE;

        loadFrames(idleFrames, "/assets/Bat/BatFlight_strip.png", 8);
        loadFrames(chaseFrames, "/assets/Bat/BatFlight_strip.png", 8);
        loadFrames(attackFrames, "/assets/Bat/BatAttack_strip.png", 10);
        setAnimation(idleFrames);
    }

    @Override
    protected List<Image> getIdleFrames() {
        return idleFrames;
    }

    @Override
    protected List<Image> getMoveFrames() {
        return chaseFrames;
    }

    @Override
    protected List<Image> getAttackFrames() {
        return attackFrames;
    }
}
