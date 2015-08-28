/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.system.localSystem;

import com.artemis.BaseSystem;
import com.artemis.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.dongbat.game.component.AbilityComponent;
import com.dongbat.game.component.Player;
import com.dongbat.game.component.UnitMovement;
import com.dongbat.game.dataobject.CustomInput;
import com.dongbat.game.util.AbilityUtil;
import com.dongbat.game.util.objectUtil.Constants;
import com.dongbat.game.util.ECSUtil;
import com.dongbat.game.util.EntityUtil;
import com.dongbat.game.util.PhysicsUtil;
import com.dongbat.game.util.UuidUtil;
import com.dongbat.game.util.localUtil.LocalPlayerUtil;
import static com.dongbat.game.util.localUtil.PhysicsCameraUtil.getCamera;
import java.util.UUID;

/**
 * @author Admin
 */
public class LocalInputSystem extends BaseSystem implements InputProcessor {

  private Entity e;
  private final CustomInput pendingInput = null;
  private boolean touchDown;
  private boolean skillOne;
  private boolean skillTwo;
  private boolean pause;

  public LocalInputSystem() {
    touchDown = false;
    skillOne = true;
    skillTwo = false;
    pause = false;
    Gdx.input.setInputProcessor(this);
  }

  @Override
  protected void processSystem() {
    UUID localPlayerId = LocalPlayerUtil.getLocalPlayer(world);
    e = UuidUtil.getEntityByUuid(world, localPlayerId);
    if (e == null || localPlayerId == null) {
      return;
    }
    UnitMovement move = EntityUtil.getComponent(world, e, UnitMovement.class);
    Vector2 destination = move.getDirectionVelocity();
    Vector2 position = PhysicsUtil.getPosition(world, e);
    if (touchDown) {
      Vector3 vector = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
      Vector3 unproject = getCamera().unproject(vector);
      long lastFrameIndex = ECSUtil.getFrame(world);
      Vector2 direction = new Vector2(unproject.x - position.x, unproject.y - position.y);
      CustomInput customInput = new CustomInput(Constants.inputType.MOVE, direction, 0);
      EntityUtil.getComponent(world, e, Player.class).getInputs().put(lastFrameIndex + 3, customInput);
      touchDown = false;
    }
    if (skillOne == true) {
      skillOne = false;
      AbilityComponent abilityComponent = EntityUtil.getComponent(world, e, AbilityComponent.class);
      String abilityName = abilityComponent.getAbilitiesOrder().get(0);
      if (abilityName == null) {
        return;
      }
      AbilityUtil.use(world, e, abilityName, destination);
    }
    if (skillTwo == true) {
      skillTwo = false;
      AbilityComponent abilityComponent = EntityUtil.getComponent(world, e, AbilityComponent.class);
      String abilityName = abilityComponent.getAbilitiesOrder().get(1);
      if (abilityName == null) {
        return;
      }
      AbilityUtil.use(world, e, abilityName, destination);
    }
  }

  @Override
  public boolean keyDown(int keycode) {
    if (Input.Keys.F1 == keycode) {
      ECSUtil.getWorldProgress(world).setSave(true);
    }
    if (Input.Keys.F2 == keycode) {
      ECSUtil.getWorldProgress(world).setLoad(true);
    }
    if (Input.Keys.Q == keycode) {
      skillOne = true;
//      BuffUtil.addBuff(world, e, e, "SpeedUp", 1000, 2);
    }
    if (Input.Keys.W == keycode) {
      skillTwo = true;
      if (e == null) {
        return false;
      }
//      Body body = PhysicsUtil.getBody(world, e);
//      Array<Entity> players = findUnitAndPlayerInRadius(world, body.getPosition(), 100);
//      for (Entity player : players) {
//        System.out.println(player);
//        if (player.equals(e)) {
//          continue;
//        }
//        BuffUtil.addBuff(world, e, player, "SlowDown", 2000, 2);
//
//      }
    }
    if (Input.Keys.E == keycode) {
//			BuffUtil.addBuff(world, e, e, "DuplicateOnTouch", 2000, 2);
    }
    return true;
  }

  @Override
  public boolean keyUp(int keycode) {
    return true;
  }

  @Override
  public boolean keyTyped(char character) {
    return true;
  }

  @Override
  public boolean touchDown(int screenX, int screenY, int pointer, int button) {
    touchDown = true;
    return true;
  }

  @Override
  public boolean touchUp(int screenX, int screenY, int pointer, int button) {
    return true;
  }

  @Override
  public boolean touchDragged(int screenX, int screenY, int pointer) {
    return true;
  }

  @Override
  public boolean mouseMoved(int screenX, int screenY) {
    return true;
  }

  @Override
  public boolean scrolled(int amount) {
    return true;
  }

}
