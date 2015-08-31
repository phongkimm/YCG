/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.util;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.ResolutionFileResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.ObjectMap;

/**
 * @author implicit-invocation
 */
public class AssetUtil {

    private static AssetManager manager;
    private static TextureLoader.TextureParameter parameter;

    private static ObjectMap<String, TextureAtlas> unitAtlas;

    public static ObjectMap<String, TextureAtlas> getUnitAtlas() {
        return unitAtlas;
    }

    public static void setUnitAtlas(ObjectMap<String, TextureAtlas> unitAtlas) {
        AssetUtil.unitAtlas = unitAtlas;
    }

    public static AssetManager getManager() {
        if (manager == null) {
            init();
        }
        return manager;
    }

    private static void init() {
        manager = new AssetManager(new ResolutionFileResolver(new InternalFileHandleResolver(), new ResolutionFileResolver.Resolution(800, 480, "hdpi"),
                new ResolutionFileResolver.Resolution(1280, 720, "xhdpi"),
                new ResolutionFileResolver.Resolution(1600, 960, "xxhdpi")));

        parameter = new TextureLoader.TextureParameter();
        parameter.minFilter = Texture.TextureFilter.Linear;
        parameter.magFilter = Texture.TextureFilter.Linear;

        unitAtlas = new ObjectMap<String, TextureAtlas>();
    }

    public static void loadAsset() {
        AssetManager manager = getManager();
        manager.load("texture/unit/move/move.atlas", TextureAtlas.class);
        manager.load("texture/queen/queen.atlas", TextureAtlas.class);
        manager.load("texture/food/hot/hot_food.atlas", TextureAtlas.class);
        manager.load("texture/food/cold/cold_food.png", Texture.class, parameter);
        manager.load("texture/background/bg00.png", Texture.class, parameter);
        manager.load("texture/background/bg01.png", Texture.class, parameter);
        manager.load("texture/background/bg02.png", Texture.class, parameter);
        manager.load("texture/background/bg03.png", Texture.class, parameter);

    }

    public static Texture cold;
    public static Texture bg00;
    public static Texture bg01;
    public static Texture bg02;
    public static Texture bg03;

    public static boolean update() {
        AssetManager manager = getManager();
        boolean done = manager.update();

        if (done) {
            unitAtlas.put("move", manager.get("texture/unit/move/move.atlas", TextureAtlas.class));
            unitAtlas.put("queen", manager.get("texture/queen/queen.atlas", TextureAtlas.class));
            unitAtlas.put("hot_food", manager.get("texture/food/hot/hot_food.atlas", TextureAtlas.class));
            cold = manager.get("texture/food/cold/cold_food.png", Texture.class);
            bg00 = manager.get("texture/background/bg00.png", Texture.class);
            bg01 = manager.get("texture/background/bg01.png", Texture.class);
            bg02 = manager.get("texture/background/bg02.png", Texture.class);
            bg03 = manager.get("texture/background/bg03.png", Texture.class);
        }
        return done;
    }

}
