package com.cursedecho.characters.enemies;

import com.cursedecho.characters.*;
import javafx.scene.image.Image;
import java.util.ArrayList;
import java.util.List;

public class Witch extends Enemy {
    private List<Image> idleFrames = new ArrayList<>();
    private List<Image> chaseFrames = new ArrayList<>();
    private List<Image> attackFrames = new ArrayList<>();

    public Witch(Player player) {
        super(128,128,50,0.7,7,0.5, 30,10, player);
        state = State.IDLE;

        loadFrames(idleFrames, "/assets/Witch/WitchIdle_strip.png", 7);
        loadFrames(chaseFrames, "/assets/Witch/WitchMove_strip.png", 8);
        loadFrames(attackFrames, "/assets/Witch/WitchAttack_strip.png", 18);
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
