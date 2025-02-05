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
package com.hmdzl.spspd.actors.mobs;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Taunt;
import com.hmdzl.spspd.items.ExpOre;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.LevelDown;
import com.hmdzl.spspd.items.StoneOre;
import com.hmdzl.spspd.items.wands.Wand;
import com.hmdzl.spspd.sprites.LevelCheckerSprite;
import com.watabou.utils.Random;

public class LevelChecker extends Mob {

	{
		spriteClass = LevelCheckerSprite.class;

		HP = HT = 0+Math.min(0,Dungeon.hero.lvl*0);
		evadeSkill = Dungeon.hero.lvl/0;
		baseSpeed = 0f;
		flying = false;

		//state = WANDERING;

		loot = new StoneOre();
		lootChance = 100f;

		properties.add(Property.MECH);
	}

	@Override
	public Item SupercreateLoot(){
		return new ExpOre();
	}

	//@Override
	//protected boolean getCloser(int target) {
		//return super.getCloser(Dungeon.hero.pos);
//	}

	@Override
	public int damageRoll() {
		return (int)(Dungeon.hero.lvl*0.5);
	}

	@Override
	public int attackProc(Char enemy, int damage) {
		if (this.buff(Taunt.class)== null && enemy == Dungeon.hero) {
			Buff.affect(this, Taunt.class);
			//Dungeon.hero.exp=0;
			Dungeon.hero.lvl++;

			//Dungeon.hero.TRUE_HT=255;
			//Dungeon.hero.hitSkill=255;
			//Dungeon.hero.evadeSkill=255;
			//damage = 255;
			//this.damage(this.HT*2,this);
		}
		return damage;

	}

	@Override
	public int hitSkill(Char target) {
		return Dungeon.hero.lvl;
	}

	@Override
	public int drRoll() {
		return Dungeon.hero.lvl;
	}

	@Override
	public void die(Object cause) {

		if (Random.Int(2) == 0)
			Dungeon.level.drop(new LevelDown(), pos).sprite.drop();

		super.die(cause);

	}
	
	
	{
		weakness.add(Wand.class);
	}
	
}
