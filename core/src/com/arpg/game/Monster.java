package com.arpg.game;

import com.arpg.game.utils.Poolable;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;

public class Monster extends Unit implements Poolable {
    private String title;
    private float aiTimer;
    private float aiTimerTo;

    public String getTitle() {
        return title;
    }

    @Override
    public boolean isActive() {
        return stats.getHp() > 0;
    }

    public Monster(GameScreen gameScreen) {
        super(gameScreen);
        this.stats = new Stats();
        this.weapon = new Weapon("Short Dark Sword", 0.8f, 2, 5);
    }

    // ___title________,__base_att__,__base_def__,__base_hp__,__att_pl__,__def_pl__,__hp_pl__,__speed__,__gained_exp__
    public Monster(String line) {
        super(null);
        String[] tokens = line.split(",");
        this.title = tokens[0].trim();
        this.texture = Assets.getInstance().getAtlas().findRegion(title);
        this.stats = new Stats(
                0,
                Integer.parseInt(tokens[1].trim()),
                Integer.parseInt(tokens[2].trim()),
                Integer.parseInt(tokens[3].trim()),
                Integer.parseInt(tokens[4].trim()),
                Integer.parseInt(tokens[5].trim()),
                Integer.parseInt(tokens[6].trim()),
                Float.parseFloat(tokens[7].trim()),
                Integer.parseInt(tokens[8].trim())
        );
        this.weapon = new Weapon("Short Dark Sword", 0.8f, 2, 5);
    }

    public void setup(int level, float x, float y, Monster pattern) {
        this.stats.set(level, pattern.stats);
        this.texture = pattern.texture;
        if (x < 0 && y < 0) {
            this.gs.getMap().setRefVectorToEmptyPoint(position);
        } else {
            this.position.set(x, y);
        }
        this.area.setPosition(position);
    }

    @Override
    public void update(float dt) {
        aiTimer += dt;
        attackTime += dt;

        if (damageTimer > 0.0f) {
            damageTimer -= dt;
        }

        if (aiTimer > aiTimerTo) {
            aiTimer = 0.0f;
            aiTimerTo = MathUtils.random(2.0f, 4.0f);
            direction = Direction.values()[MathUtils.random(0, 3)];
        }

        tmp.set(position).add(direction.getX() * stats.getSpeed() * dt, direction.getY() * stats.getSpeed() * dt);
        if (gs.getMap().isCellPassable(tmp)) {
            position.set(tmp);
            area.setPosition(position);
        }

        tryToAttack();
    }

    public void tryToAttack() {
        if (attackTime > weapon.getAttackPeriod()) {
            attackTime = 0.0f;
            tmp.set(position).add(direction.getX() * 60, direction.getY() * 60);
            if (gs.getHero().getArea().contains(tmp)) {
                gs.getEffectController().setup(tmp.x, tmp.y, 1);
                gs.getHero().takeDamage(weapon.getDamage() + this.stats.getAtt(), Color.RED);
            }
        }
    }
}
