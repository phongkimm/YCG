/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.ai.tasks;

import com.artemis.Entity;
import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.utils.Array;
import com.dongbat.game.util.MovementUtil;
import com.dongbat.game.util.PhysicsUtil;

/**
 *
 * @author Admin
 */
public class CheckEatablePlayer extends LeafTask<Entity> {

	@Override
	public void run() {
		Entity e = getObject();
		Array<Entity> playerInRadius = PhysicsUtil.findPlayerInRadius(e.getWorld(), PhysicsUtil.getPosition(e.getWorld(), e), 50);
		Entity nearestPlayer = PhysicsUtil.findNearestEntityInList(e.getWorld(), PhysicsUtil.getPosition(e.getWorld(), e), playerInRadius);
		if (nearestPlayer == null) {
			fail();
		} else {
			float eaterRadius = PhysicsUtil.getRadius(e.getWorld(), e);
			float playerRadius = PhysicsUtil.getRadius(e.getWorld(), nearestPlayer);
			if (eaterRadius > playerRadius * 1.2) {
				MovementUtil.setTarget(e, nearestPlayer);
				success();
			} else {
				fail();
			}
		}
	}

	@Override
	protected Task<Entity> copyTo(Task<Entity> task) {
		return task;
	}

}
