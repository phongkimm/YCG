/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.util;

import com.dongbat.game.util.objectUtil.MapperCache;
import com.artemis.Aspect;
import com.artemis.AspectSubscriptionManager;
import com.artemis.Component;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.World;
import com.artemis.utils.IntBag;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.dongbat.game.component.AiControl;
import com.dongbat.game.component.Food;
import com.dongbat.game.component.Player;
import com.dongbat.game.component.Queen;
import static com.dongbat.game.util.FoodSpawningUtil.scaleX;
import static com.dongbat.game.util.FoodSpawningUtil.scaleY;
import java.util.UUID;

/**
 * @author Admin
 */
public class EntityUtil {

  /**
   * Caching Component Mapper, avoiding create too much new instance. It reduce
   * memory leak
   */
  private static ObjectMap<World, MapperCache> mapperCaches = new ObjectMap<World, MapperCache>();

  /**
   * Get Mapper of an Component in artemis
   *
   * @param world artemis world
   * @param type type of Component you want to get Mapper
   * @param <T> class type
   * @return ComponentMapper
   */
  public static <T extends Component> ComponentMapper<T> getMapper(World world, Class<T> type) {
    if (!mapperCaches.containsKey(world)) {
      mapperCaches.put(world, new MapperCache(world));
    }
    MapperCache cache = mapperCaches.get(world);
    return cache.getMapper(type);
  }

  public static IntBag getAllEntities(World world) {
    //TODO: world is not update, entities bag is not update when world progress 
    IntBag entities = world.getManager(AspectSubscriptionManager.class).get(Aspect.all()).getEntities();
    return entities;
  }

  public static int getEntityById(World world, int id) {
    //TODO: world is not update, entities bag is not update when world progress 
    IntBag entities = world.getManager(AspectSubscriptionManager.class).get(Aspect.all()).getEntities();
    return entities.get(id);
  }

  /**
   * Get specific Component class of an entity in the artemis world
   *
   * @param world artemis world
   * @param e entity that you want to get Component class
   * @param type type of Component you want to get, example: Stats.class,
   * Food.class
   * @param <T> class type
   * @return Component class
   */
  public static <T extends Component> T getComponent(World world, Entity e, Class<T> type) {
    return getMapper(world, type).getSafe(e);
  }

}
