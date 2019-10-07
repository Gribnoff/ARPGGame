package com.arpg.game;

import com.arpg.game.utils.Poolable;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;

public class Monster extends Unit implements Poolable {
    private String title;
    private float aiTimer;
    private float aiTimerTo;
    private float aggrTimer;
    private float aggrTimerTo;
    private float aggrDirectionTimer;
    private float aggrDirectionTimerTo;

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
        this.weapon = new Weapon("Bite", 0.8f, 2, 5);
    }

    // ___title________,__base_att__,__base_def__,__base_hp__,__att_pl__,__def_pl__,__hp_pl__,__speed__
    public Monster(String line) {
        super(null);
        String[] tokens = line.split(",");
        this.title = tokens[0].trim();
        this.texture = new TextureRegion(Assets.getInstance().getAtlas().findRegion(title)).split(80, 80);
        this.stats = new Stats(
                0,
                Integer.parseInt(tokens[1].trim()),
                Integer.parseInt(tokens[2].trim()),
                Integer.parseInt(tokens[3].trim()),
                Integer.parseInt(tokens[4].trim()),
                Integer.parseInt(tokens[5].trim()),
                Integer.parseInt(tokens[6].trim()),
                Float.parseFloat(tokens[7].trim())
        );
        this.weapon = new Weapon("Bite", 0.8f, 2, 5);
    }

    public void setup(int level, float x, float y, Monster pattern) {
        this.stats.set(level, pattern.stats);
        this.title = pattern.title;
        this.texture = pattern.texture;
        if (x < 0 && y < 0) {
            this.gs.getMap().setRefVectorToEmptyPoint(position);
        } else {
            this.position.set(x, y);
        }
        this.area.setPosition(position);
        this.aggrTimerTo = 20.f;
        this.aggrDirectionTimerTo = 0.2f;
    }

    @Override
    public void update(float dt) {
        aiTimer += dt;
        attackTime += dt;

        if (damageTimer > 0.0f) {
            damageTimer -= dt;
        }

        if (aggressive) {
            aggrTimer += dt;
            if (aggrTimer > aggrTimerTo) {
                aggrTimer = 0.f;
                aggressive = false;
            }

            aggrDirectionTimer += dt;
            if (aggrDirectionTimer > aggrDirectionTimerTo) {
                aggrDirectionTimer = 0.f;
                direction = changeDirection();
            }
        } else if (aiTimer > aiTimerTo) {
            aiTimer = 0.0f;
            aiTimerTo = MathUtils.random(2.0f, 4.0f);
            direction = Direction.values()[MathUtils.random(0, 3)];
        }

        tmp.set(position).add(direction.getX() * stats.getSpeed() * dt, direction.getY() * stats.getSpeed() * dt);
        if (gs.getMap().isCellPassable(tmp)) {
            position.set(tmp);
            walkTimer += dt;
            area.setPosition(position);
        }

        tryToAttack();
    }

    private Direction changeDirection() {
        Direction direction;
        tmp.set(gs.getHero().getPosition());
        float angle = tmp.sub(this.getPosition()).angle();
        if (angle >= 45 && angle < 135)
            direction = Direction.UP;
        else if (angle >= 135 && angle < 225)
            direction = Direction.LEFT;
        else if (angle >= 225 && angle < 315)
            direction = Direction.DOWN;
        else
            direction = Direction.RIGHT;

        return direction;
    }

    public void tryToAttack() {
        if (attackTime > weapon.getAttackPeriod()) {
            attackTime = 0.0f;
            tmp.set(position).add(direction.getX() * 60, direction.getY() * 60);
            if (gs.getHero().getArea().contains(tmp)) {
                gs.getEffectController().setup(tmp.x, tmp.y, 1);
                gs.getHero().takeDamage(this, BattleCalc.calculateDamage(this, gs.getHero()), Color.RED);
            }
        }
    }

    public boolean isAggressive() {
        return aggressive;
    }

    public void setAggressive() {
        this.aggressive = true;
    }
}
