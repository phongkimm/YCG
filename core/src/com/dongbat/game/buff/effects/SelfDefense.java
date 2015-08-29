/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.buff.effects;

import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.dongbat.game.buff.BuffEffect;
import com.dongbat.game.component.Detection;
import com.dongbat.game.util.BuffUtil;
import com.dongbat.game.util.EntityUtil;
import com.dongbat.game.util.PhysicsUtil;
import com.dongbat.game.util.UuidUtil;
import com.dongbat.game.util.factory.EntityFactory;
import java.util.UUID;

/**
 *
 * @author Admin
 */
public class SelfDefense implements BuffEffect {

  private int foodPerFrame;

  @Override
  public void durationStart(World world, Entity source, Entity target) {
    foodPerFrame = 1;
  }

  @Override
  public void update(World world, Entity source, Entity target) {

    Detection detection = EntityUtil.getComponent(world, target, Detection.class);
    UUID id = detection.getNearestPlayer();
    if (id != null) {
      Entity e = UuidUtil.getEntityByUuid(world, id);
      Vector2 entityPos = PhysicsUtil.getPosition(world, e);
      Vector2 queenPos = PhysicsUtil.getPosition(world, source);
      Vector2 destination = entityPos.cpy().sub(queenPos.cpy());
      float degree = destination.angle();
      for (int i = 0; i < 1; i++) {
        float d = MathUtils.random(- 30, 30);

        Vector2 direction = destination.cpy().scl(1).nor().rotate(d);
        Entity food = EntityFactory.createSteeringFood(world, queenPos, UuidUtil.getUuid(source));
        BuffUtil.addBuff(world, source, food, "ToBeRemoved", 10000, 1);
        BuffUtil.addBuff(world, source, food, "ToxicFood", 400, 1);
        BuffUtil.addBuff(world, source, food, "Forced", (int) (400 * MathUtils.random(.5f, 1.25f)), 1, "forceStrength", 0.5f, "direction", direction);
      }
    }
  }

  @Override
  public void durationEnd(World world, Entity source, Entity target) {
  }

}