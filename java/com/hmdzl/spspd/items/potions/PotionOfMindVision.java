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
package com.hmdzl.spspd.items.potions;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.buffs.Awareness;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.MindVision;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.items.misc.Spectacles.MagicSight;
import com.hmdzl.spspd.utils.GLog;
import com.hmdzl.spspd.messages.Messages;import com.hmdzl.spspd.ResultDescriptions;

public class PotionOfMindVision extends Potion {

	{
		//name = "Potion of Mind Vision";
		initials = 9;
	}
	
	@Override
	public void apply(Hero hero) {
		setKnown();

		Buff.affect(hero, MindVision.class, Dungeon.hero.buff(MagicSight.class) != null ? MindVision.DURATION*4 : MindVision.DURATION);
		Buff.affect(hero, Awareness.class, Dungeon.hero.buff(MagicSight.class) != null ? 20f : 5f);
		Dungeon.observe();

		if (Dungeon.level.mobs.size() > 0) {
			GLog.i( Messages.get(this, "see_mobs"));
		} else {
			GLog.i( Messages.get(this, "see_none"));
		}
	}

	@Override
	public int price() {
		return isKnown() ? 35 * quantity : super.price();
	}
}
