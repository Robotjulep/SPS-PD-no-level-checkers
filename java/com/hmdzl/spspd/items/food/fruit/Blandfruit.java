package com.hmdzl.spspd.items.food.fruit;


import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.FrostImbue;
import com.hmdzl.spspd.actors.buffs.Recharging;
import com.hmdzl.spspd.actors.buffs.EarthImbue;
import com.hmdzl.spspd.actors.buffs.FireImbue;
import com.hmdzl.spspd.actors.buffs.Hunger;
import com.hmdzl.spspd.actors.buffs.ToxicImbue;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.effects.SpellSprite;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.potions.Potion;
import com.hmdzl.spspd.items.potions.PotionOfExperience;
import com.hmdzl.spspd.items.potions.PotionOfFrost;
import com.hmdzl.spspd.items.potions.PotionOfHealing;
import com.hmdzl.spspd.items.potions.PotionOfInvisibility;
import com.hmdzl.spspd.items.potions.PotionOfLevitation;
import com.hmdzl.spspd.items.potions.PotionOfLiquidFlame;
import com.hmdzl.spspd.items.potions.PotionOfMight;
import com.hmdzl.spspd.items.potions.PotionOfMindVision;
import com.hmdzl.spspd.items.potions.PotionOfMixing;
import com.hmdzl.spspd.items.potions.PotionOfOverHealing;
import com.hmdzl.spspd.items.potions.PotionOfParalyticGas;
import com.hmdzl.spspd.items.potions.PotionOfPurity;
import com.hmdzl.spspd.items.potions.PotionOfShield;
import com.hmdzl.spspd.items.potions.PotionOfStrength;
import com.hmdzl.spspd.items.potions.PotionOfToxicGas;
import com.hmdzl.spspd.items.scrolls.ScrollOfRecharging;
import com.hmdzl.spspd.plants.Plant.Seed;
import com.hmdzl.spspd.sprites.ItemSprite;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.utils.GLog;
import com.hmdzl.spspd.messages.Messages;import com.hmdzl.spspd.ResultDescriptions;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

/**
 * Created by debenhame on 12/08/2014.
 */
public class Blandfruit extends Fruit {

	public Potion potionAttrib = null;
	public ItemSprite.Glowing potionGlow = null;

	{
		//name = "Blandfruit";
		stackable = true;
		image = ItemSpriteSheet.BLANDFRUIT;
		energy = 50;
		hornValue = 6; // only applies when blandfruit is cooked

	}

	@Override
	public boolean isSimilar(Item item) {
		if (item instanceof Blandfruit) {
			if (potionAttrib == null) {
                return ((Blandfruit) item).potionAttrib == null;
			} else if (((Blandfruit) item).potionAttrib != null) {
                return ((Blandfruit) item).potionAttrib.getClass() == potionAttrib
                        .getClass();
			}
		}
		return false;
	}

	@Override
	public void execute(Hero hero, String action) {
		if (action.equals(AC_EAT)) {

			if (potionAttrib == null) {

                GLog.w( Messages.get(this, "raw"));
				
			} else {

				hero.buff(Hunger.class).satisfy(150);

				detach(hero.belongings.backpack);

				hero.spend(1f);
				hero.busy();

                if (potionAttrib instanceof PotionOfLiquidFlame) {
					GLog.i(Messages.get(this, "fire_msg"));
					Buff.affect(hero, FireImbue.class).set(FireImbue.DURATION);
				} else if (potionAttrib instanceof PotionOfToxicGas) {
					GLog.i(Messages.get(this, "toxic_msg"));
					Buff.affect(hero, ToxicImbue.class)
							.set(ToxicImbue.DURATION);
				} else if (potionAttrib instanceof PotionOfParalyticGas) {
					GLog.i(Messages.get(this, "para_msg"));
					Buff.affect(hero, EarthImbue.class, EarthImbue.DURATION);
				} else if (potionAttrib instanceof PotionOfFrost) {
					GLog.i(Messages.get(this, "frost_msg"));
					Buff.affect(hero, FrostImbue.class, FrostImbue.DURATION);
				} else if (potionAttrib instanceof PotionOfMixing) {
					GLog.i(Messages.get(this, "para_msg"));
					Buff.affect(hero, FrostImbue.class, FrostImbue.DURATION);
					Buff.affect(hero, EarthImbue.class, EarthImbue.DURATION);
					Buff.affect(hero, ToxicImbue.class).set(ToxicImbue.DURATION);
					Buff.affect(hero, FireImbue.class).set(FireImbue.DURATION);
				} else
					potionAttrib.apply(hero);

				Sample.INSTANCE.play(Assets.SND_EAT);
				SpellSprite.show(hero, SpellSprite.FOOD);
				hero.sprite.operate(hero.pos);

				/*switch (hero.heroClass) {
				case WARRIOR:
					if (hero.HP < hero.TRUE_HT) {
						hero.HP = Random.Int(hero.HP + 5, hero.TRUE_HT);
						hero.sprite.emitter().burst(
								Speck.factory(Speck.HEALING), 1);
					}
					break;
				case MAGE:
					Buff.affect( hero, Recharging.class, 4f );
					ScrollOfRecharging.charge(hero);
					break;
				case ROGUE:
				case HUNTRESS:
				case PERFORMER:
				case SOLDIER:
				case FOLLOWER:
					break;
				}*/
			}
		} else {
			super.execute(hero, action);
		}
	}

	@Override
	public String desc() {
		if (potionAttrib== null) return super.desc();
		else return Messages.get(this, "desc_cooked");
	}

	@Override
	public int price() {
		return 20 * quantity;
	}	
	
	public Item cook(Seed seed) {

		try {
			return imbuePotion((Potion) seed.alchemyClass.newInstance());
		} catch (Exception e) {
			return null;
		}

	}

	public Item imbuePotion(Potion potion) {

		potionAttrib = potion;
		potionAttrib.ownedByFruit = true;

		potionAttrib.image = ItemSpriteSheet.BLANDFRUIT;

		if (potionAttrib instanceof PotionOfHealing){
			name = Messages.get(this, "sunfruit");
			potionGlow = new ItemSprite.Glowing( 0x2EE62E );
		} else if (potionAttrib instanceof PotionOfStrength){
			name = Messages.get(this, "rotfruit");
			potionGlow = new ItemSprite.Glowing( 0xCC0022 );
		} else if (potionAttrib instanceof PotionOfParalyticGas){
			name = Messages.get(this, "earthfruit");
			potionGlow = new ItemSprite.Glowing( 0x67583D );
		} else if (potionAttrib instanceof PotionOfInvisibility){
			name = Messages.get(this, "blindfruit");
			potionGlow = new ItemSprite.Glowing( 0xE5D273 );
		} else if (potionAttrib instanceof PotionOfLiquidFlame){
			name = Messages.get(this, "firefruit");
			potionGlow = new ItemSprite.Glowing( 0xFF7F00 );
		} else if (potionAttrib instanceof PotionOfFrost){
			name = Messages.get(this, "icefruit");
			potionGlow = new ItemSprite.Glowing( 0x66B3FF );
		} else if (potionAttrib instanceof PotionOfMindVision){
			name = Messages.get(this, "fadefruit");
			potionGlow = new ItemSprite.Glowing( 0xB8E6CF );
		} else if (potionAttrib instanceof PotionOfToxicGas){
			name = Messages.get(this, "sorrowfruit");
			potionGlow = new ItemSprite.Glowing( 0xA15CE5 );
		} else if (potionAttrib instanceof PotionOfLevitation) {
			name = Messages.get(this, "stormfruit");
			potionGlow = new ItemSprite.Glowing( 0x1C3A57 );
		} else if (potionAttrib instanceof PotionOfPurity) {
			name = Messages.get(this, "dreamfruit");
			potionGlow = new ItemSprite.Glowing( 0x8E2975 );
		} else if (potionAttrib instanceof PotionOfExperience) {
			name = Messages.get(this, "starfruit");
			potionGlow = new ItemSprite.Glowing( 0xA79400 );
		} else if (potionAttrib instanceof PotionOfOverHealing) {
			name = Messages.get(this, "heartfruit");
			potionGlow = new ItemSprite.Glowing( 0xB20000 );
		} else if (potionAttrib instanceof PotionOfShield) {
			name = Messages.get(this, "nutfruit");
			potionGlow = new ItemSprite.Glowing(0x67583D);
		} else if (potionAttrib instanceof PotionOfMixing) {
				name = Messages.get(this, "mixfruit");
				potionGlow = new ItemSprite.Glowing(0xB20000);
		} else if (potionAttrib instanceof PotionOfMight) {
			name = Messages.get(this, "mightfruit");
			potionGlow = new ItemSprite.Glowing(0xB20000);

		}
		return this;
	}

	public static final String POTIONATTRIB = "potionattrib";

	@Override
	public void cast(final Hero user, int dst) {
		if (potionAttrib instanceof PotionOfLiquidFlame
				|| potionAttrib instanceof PotionOfToxicGas
				|| potionAttrib instanceof PotionOfParalyticGas
				|| potionAttrib instanceof PotionOfFrost
				|| potionAttrib instanceof PotionOfLevitation
				|| potionAttrib instanceof PotionOfPurity
				) {
			potionAttrib.cast(user, dst);
			detach(user.belongings.backpack);
		} else {
			super.cast(user, dst);
		}

	}

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(POTIONATTRIB, potionAttrib);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		if (bundle.contains(POTIONATTRIB)) {
			imbuePotion((Potion) bundle.get(POTIONATTRIB));

		}
	}

	@Override
	public ItemSprite.Glowing glowing() {
		return potionGlow;
	}

}
