/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dongbat.game.component;

import com.artemis.Component;
import com.badlogic.gdx.utils.ObjectMap;
import com.dongbat.game.ability.AbilityInfo;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class AbilityComponent extends Component {

  private ObjectMap<String, AbilityInfo> abilities = new ObjectMap<String, AbilityInfo>();
  public ArrayList<String> abilitiesOrder = new ArrayList<String>();

  public ObjectMap<String, AbilityInfo> getAbilities() {
    return abilities;
  }

  public ArrayList<String> getAbilitiesOrder() {
    return abilitiesOrder;
  }

  public void setAbilitiesOrder(ArrayList<String> abilitiesOrder) {
    this.abilitiesOrder = abilitiesOrder;
  }

  public void setAbilities(ObjectMap<String, AbilityInfo> abilities) {
    this.abilities = abilities;
  }

  public void addAbility(String name, AbilityInfo ability) {
    abilities.put(name, ability);
  }

  public AbilityInfo getAbility(String name) {
    return abilities.get(name);
  }
}
