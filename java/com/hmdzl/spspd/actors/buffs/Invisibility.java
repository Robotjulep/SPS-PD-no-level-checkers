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
package com.hmdzl.spspd.actors.buffs;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.items.artifacts.CloakOfShadows;
import com.hmdzl.spspd.items.artifacts.TimekeepersHourglass;
import com.hmdzl.spspd.messages.Messages;import com.hmdzl.spspd.ResultDescriptions;
import com.hmdzl.spspd.sprites.CharSprite;
import com.hmdzl.spspd.ui.BuffIndicator;


public class Invisibility extends FlavourBuff {

	public static final float DURATION = 15f;

	{
		type = buffType.POSITIVE;
	}	
	
	@Override
	public boolean attachTo(Char target) {
		if (super.attachTo(target)) {
			target.invisible++;
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void detach() {
		if (target.invisible > 0)
			target.invisible--;
		super.detach();
	}

	@Override
	public int icon() {
		return BuffIndicator.INVISIBLE;
	}

	@Override
	public void fx(boolean on) {
		if (on) target.sprite.add( CharSprite.State.INVISIBLE );
		else if (target.invisible == 0) target.sprite.remove( CharSprite.State.INVISIBLE );
	}

	@Override
	public String toString() {
		return Messages.get(this, "name");
	}

	@Override
	public String desc() {
		return Messages.get(this, "desc", dispTurns());
	}

	public static void dispel() {
		Invisibility buff = Dungeon.hero.buff(Invisibility.class);
		if (buff != null) {
			buff.detach();
		}
		CloakOfShadows.cloakStealth cloakBuff = Dungeon.hero
				.buff(CloakOfShadows.cloakStealth.class);
		if (cloakBuff != null) {
			cloakBuff.act();
			cloakBuff.detach();
		}
		// this isn't a form of invisibilty, but it is meant to dispel at the
		// same time as it.
		TimekeepersHourglass.timeFreeze timeFreeze = Dungeon.hero
				.buff(TimekeepersHourglass.timeFreeze.class);
		if (timeFreeze != null) {
			timeFreeze.detach();
		}
	}	

}
