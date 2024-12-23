package com.cursedecho.characters.enemies;

import com.cursedecho.characters.*;
import javafx.scene.image.Image;
import java.util.ArrayList;
import java.util.List;

public class Wolf extends Enemy {
    private List<Image> idleFrames = new ArrayList<>();
    private List<Image> chaseFrames = new ArrayList<>();
    private List<Image> attackFrames = new ArrayList<>();

    public Wolf(Player player) {
        super(128,128,50,0.8,5,1,30,5, player);
        state = State.IDLE;

        loadFrames(idleFrames, "/assets/Wolf/WolfIdle_strip.png", 12);
        loadFrames(chaseFrames, "/assets/Wolf/WolfMove_strip.png", 8);
        loadFrames(attackFrames, "/assets/Wolf/WolfAttack_strip.png", 16);
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
