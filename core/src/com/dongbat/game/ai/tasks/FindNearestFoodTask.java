/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.ai.tasks;

import com.artemis.Entity;
import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.dongbat.game.component.UnitMovement;
import com.dongbat.game.util.ECSUtil;
import com.dongbat.game.util.EntityUtil;
import com.dongbat.game.util.MovementUtil;
import com.dongbat.game.util.PhysicsUtil;
import static com.dongbat.game.util.WorldQueryUtil.*;

/**
 *
 * @author Admin
 */
public class FindNearestFoodTask extends LeafTask<Entity> {

  @Override
  public void run() {
    Entity e = getObject();
    long lastFrameIndex = ECSUtil.getFrame(e.getWorld());
    UnitMovement unitMovement = EntityUtil.getComponent(e.getWorld(), e, UnitMovement.class);
    Array<Entity> foodInRadius = findFoodInRadius(e.getWorld(), PhysicsUtil.getPosition(e.getWorld(), e), 50);
    Array<Entity> foodInMap = findFoodInRadius(e.getWorld(), PhysicsUtil.getPosition(e.getWorld(), e), 5000);
    Entity nearestFood = findNearestEntityInList(e.getWorld(), PhysicsUtil.getPosition(e.getWorld(), e), foodInRadius);
    Vector2 currentPos = PhysicsUtil.getPosition(e.getWorld(), e);

    if (nearestFood == null) {
      Entity mapsFood = findNearestEntityInList(e.getWorld(), PhysicsUtil.getPosition(e.getWorld(), e), foodInMap);
      if (mapsFood == null) {
        if (unitMovement.getDirectionVelocity() == null) {
          MovementUtil.addMoveInput(e.getWorld(), e, currentPos);
        } else {
//          MovementUtil.addMoveInput(e.getWorld(), e, unitMovement.getDirectionVelocity());
//          MovementUtil.setTarget(e, unitMovement.getDirectionVelocity());
        }
      } else {
        Vector2 foodPosition = PhysicsUtil.getPosition(mapsFood.getWorld(), mapsFood);
        Vector2 destinationToFood = foodPosition.cpy().sub(currentPos);
        MovementUtil.addMoveInput(e.getWorld(), e, destinationToFood);
//        MovementUtil.setTarget(e, foodPosition.cpy().sub(currentPos));
      }

    } else {

      Vector2 foodPosition = PhysicsUtil.getPosition(e.getWorld(), nearestFood);
      Vector2 destinationToFood = foodPosition.cpy().sub(currentPos);
      MovementUtil.addMoveInput(e.getWorld(), e, destinationToFood);

//      MovementUtil.setTarget(e, foodPosition.cpy().sub(currentPos));
    }
    success();
  }

  @Override
  protected Task<Entity> copyTo(Task<Entity> task) {
    return task;
  }

}
