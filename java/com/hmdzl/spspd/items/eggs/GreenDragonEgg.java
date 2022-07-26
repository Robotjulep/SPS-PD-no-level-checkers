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
package com.hmdzl.spspd.items.eggs;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.hero.Hero;

import com.hmdzl.spspd.actors.mobs.pets.GreenDragon;
import com.hmdzl.spspd.actors.mobs.pets.PET;

import com.hmdzl.spspd.effects.Pushing;
import com.hmdzl.spspd.messages.Messages;import com.hmdzl.spspd.ResultDescriptions;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.noosa.tweeners.AlphaTweener;
import com.watabou.utils.Bundle;

public class GreenDragonEgg extends Egg {
	
	public static final float TIME_TO_USE = 1;

	public static final int LERY_FIRE = 10;
	

		{
		//name = "shadow dragon egg";
		image = ItemSpriteSheet.GREEN_DRAGONEGG;

		stackable = false;
		}
		
		public int startMoves = 0;
		public int moves = 0;
		public int burns = 0;
		public int freezes = 0;
		public int poisons = 0;
		public int lits = 20;
		public int summons = 0;
		public int light = 0;
		
		private static final String STARTMOVES = "startMoves";
		private static final String MOVES = "moves";
		private static final String BURNS = "burns";
		private static final String FREEZES = "freezes";
		private static final String POISONS = "poisons";
		private static final String LITS = "lits";
		private static final String SUMMONS = "darks";
		private static final String LIGHT = "lighthit";
		
		
		@Override
		public void storeInBundle(Bundle bundle) {
			super.storeInBundle(bundle);
			bundle.put(STARTMOVES, startMoves);
			bundle.put(MOVES, moves);
			bundle.put(BURNS, burns);
			bundle.put(FREEZES, freezes);
			bundle.put(POISONS, poisons);
			bundle.put(LITS, lits);
			bundle.put(SUMMONS, summons);
			bundle.put(LIGHT, light);
			
		}

		@Override
		public void restoreFromBundle(Bundle bundle) {
			super.restoreFromBundle(bundle);
			startMoves = bundle.getInt(STARTMOVES);
			moves = bundle.getInt(MOVES);
			burns = bundle.getInt(BURNS);
			freezes = bundle.getInt(FREEZES);
			poisons = bundle.getInt(POISONS);
			lits = bundle.getInt(LITS);
			summons = bundle.getInt(SUMMONS);
			light = bundle.getInt(LIGHT);
			
		}
						
		public int checkMoves () {
			return moves;
		}
		public int checkBurns () {
			return burns;
		}
		public int checkFreezes () {
			return freezes;
		}
		public int checkPoisons () {
			return poisons;
		}
		public int checkLits () {
			return lits;
		}
		public int checkDarks() {
			return summons;
		}
		
		public int checkLight () {
			return light;
		}	
		
		/*@Override
		public boolean doPickUp(Hero hero) {
				
				GLog.w("The egg likes to be warm in your pack.");
				
				Egg egg = hero.belongings.getItem(Egg.class);
				if (egg!=null){
					GLog.w("You can probably only keep one egg warm at a time.");
				}
						 
			 return super.doPickUp(hero);				
		}	*/
		
	/*@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		actions.add(AC_BREAK);
		actions.add(AC_SHAKE);
		
		return actions;
	}*/

	@Override
	public void execute(Hero hero, String action) {

		/*
		if (action == AC_BREAK) {

			if (Dungeon.depth>26) {
				hero.spend(EasterEgg.TIME_TO_USE);
				GLog.w(TXT_PREVENTING);
				return;
			}			
		}
		*/

		if (action.equals(AC_BREAK)) {

			GreenDragon pet = new GreenDragon();
			eggHatch(pet);
		  
		  hero.next();
		
		}
		
		else if (action.equals(AC_SHAKE)) {

				  GLog.w(Messages.get(Egg.class,"kick"));
			  				
		} else {

			super.execute(hero, action);

		}
		
		
		
	}	
	
	/*public int getSpawnPos(){
		int newPos = -1;
		int pos = Dungeon.hero.pos;
			ArrayList<Integer> candidates = new ArrayList<Integer>();
			boolean[] passable = Level.passable;

			for (int n : Level.NEIGHBOURS8) {
				int c = pos + n;
				if (passable[c] && Actor.findChar(c) == null) {
					candidates.add(c);
				}
			}

			newPos = candidates.size() > 0 ? Random.element(candidates) : -1;
			
		return newPos;
	}*/
	
	
	public void eggHatch (PET pet) {		
		
		  int spawnPos = getSpawnPos();
		  if (spawnPos != -1 && !Dungeon.hero.haspet) {
				
				pet.spawn(1);
				pet.HP = pet.HT;
				pet.pos = spawnPos;
				pet.state = pet.HUNTING;

				GameScene.add(pet);
				Actor.addDelayed(new Pushing(pet, Dungeon.hero.pos, spawnPos), -1f);

				pet.sprite.alpha(0);
				pet.sprite.parent.add(new AlphaTweener(pet.sprite, 1, 0.15f));
				
				detach(Dungeon.hero.belongings.backpack);		 			 
				GLog.w(Messages.get(Egg.class,"hatch"));
				Dungeon.hero.haspet=true;
				
				assignPet(pet);
				
		  } else {
			  
			  Dungeon.hero.spend(LeryFireEgg.TIME_TO_USE);
			  GLog.w(Messages.get(Egg.class,"notready"));

		  }
	}
	
	private void assignPet(PET pet){
		
		  Dungeon.hero.petType=pet.type;
		  Dungeon.hero.petLevel=pet.level;
		   
		  Dungeon.hero.petHP=pet.HP;
		  Dungeon.hero.petExperience=pet.experience;
		  Dungeon.hero.petCooldown=pet.cooldown;		
	}
		
	@Override
	public int price() {
		return 500 * quantity;
	}

}
