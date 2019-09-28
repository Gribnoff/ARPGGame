package com.arpg.game;

import com.arpg.game.utils.ObjectPool;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MonsterController extends ObjectPool<Monster> {
    private TextureRegion hpTexture;

    @Override
    protected Monster newObject() {
        return new Monster(ScreenManager.getInstance().getGameScreen());
    }

    public void setup(int level) {
        Monster monster = getActiveElement();
        monster.setup(level);
        this.hpTexture = Assets.getInstance().getAtlas().findRegion("monsterHp");
    }

    public void render(SpriteBatch batch) {
        for (Monster monster : activeList) {
            if (monster.damageTimer > 0)
                batch.setColor(1, 1 - monster.damageTimer, 1 - monster.damageTimer, 1);
            batch.draw(monster.texture, monster.getPosition().x - 40, monster.getPosition().y - 40);
            if (monster.hp < monster.hpMax) {
                batch.setColor(1, 1, 1, 0.9f);
                batch.draw(hpTexture, monster.position.x - 40, monster.position.y + 40, 80 * ((float) monster.hp / monster.hpMax), 12);
            }
            batch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        }
    }

    public void update(float dt) {
        for (Monster monster : activeList) {
            monster.update(dt);
        }
        checkPool();
    }
}
