/*
 * Pixel Dungeon
 * Copyright (C) 2012-2014  Oleg Dolya
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package com.hmdzl.spspd.items.reward;

import java.util.ArrayList;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.items.Item;

import com.hmdzl.spspd.items.sellitem.Crystalnucleus;
import com.hmdzl.spspd.sprites.ItemSprite;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;

public class CityReward extends Item {

	public static final String AC_USE = "USE";

	public static final float TIME_TO_USE = 1;
	private static ItemSprite.Glowing YELLOW = new ItemSprite.Glowing( 0xFFFF44 );

	@Override
	public ItemSprite.Glowing glowing() {
		return YELLOW;
	}
	{
		//name = "reward";
		image = ItemSpriteSheet.PETFOOD;

		stackable = false;
	}

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		actions.add(AC_USE);
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {

		if (action.equals(AC_USE)) {

			hero.spend(TIME_TO_USE);
			hero.busy();

			hero.sprite.operate(hero.pos);

			Crystalnucleus cry = new Crystalnucleus();
			Dungeon.level.drop(cry, Dungeon.hero.pos).sprite.drop(Dungeon.hero.pos);
			Dungeon.level.drop(cry, Dungeon.hero.pos).sprite.drop(Dungeon.hero.pos);
			Dungeon.level.drop(cry, Dungeon.hero.pos).sprite.drop(Dungeon.hero.pos);
			Dungeon.level.drop(cry, Dungeon.hero.pos).sprite.drop(Dungeon.hero.pos);
			Dungeon.level.drop(cry, Dungeon.hero.pos).sprite.drop(Dungeon.hero.pos);
			
			detach(hero.belongings.backpack);

		} else {
			super.execute(hero, action);

		}
	}
	
	@Override
	public boolean isUpgradable() {
		return false;
	}

	@Override
	public boolean isIdentified() {
		return true;
	}

	@Override
	public int price() {
		return 10 * quantity;
	}
}
