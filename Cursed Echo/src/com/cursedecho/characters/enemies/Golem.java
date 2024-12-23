package com.cursedecho.characters.enemies;

import com.cursedecho.characters.*;
import javafx.scene.image.Image;
import java.util.ArrayList;
import java.util.List;

public class Golem extends Enemy {
    private List<Image> idleFrames = new ArrayList<>();
    private List<Image> chaseFrames = new ArrayList<>();
    private List<Image> attackFrames = new ArrayList<>();

    public Golem(Player player) {
        super(128,128,30,0.5,5,0.2,30,40, player);
        state = State.IDLE;

        loadFrames(idleFrames, "/assets/Golem/GolemIdle_strip.png", 12);
        loadFrames(chaseFrames, "/assets/Golem/GolemMove_strip.png", 7);
        loadFrames(attackFrames, "/assets/Golem/GolemAttack_strip.png", 16);
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
