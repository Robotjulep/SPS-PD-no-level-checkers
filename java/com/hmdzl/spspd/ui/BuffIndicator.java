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
package com.hmdzl.spspd.ui;

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.windows.WndInfoBuff;
import com.watabou.gltextures.SmartTexture;
import com.watabou.gltextures.TextureCache;
import com.watabou.noosa.Image;
import com.watabou.noosa.TextureFilm;
import com.watabou.noosa.tweeners.AlphaTweener;
import com.watabou.noosa.ui.Button;
import com.watabou.noosa.ui.Component;
import com.watabou.utils.SparseArray;

public class BuffIndicator extends Component {

	public static final int NONE = -1;

	public static final int MIND_VISION = 0;
	public static final int LEVITATION = 1;
	public static final int FIRE = 2;
	public static final int POISON = 3;
	public static final int PARALYSIS = 4;
	public static final int HUNGER = 5;
	public static final int STARVATION = 6;
	public static final int SLOW = 7;
	public static final int OOZE = 8;
	public static final int AMOK = 9;
	public static final int TERROR = 10;
	public static final int ROOTS = 11;
	public static final int INVISIBLE = 12;
	public static final int SHADOWS = 13;
	public static final int WEAKNESS = 14;
	public static final int FROST = 15;
	public static final int BLINDNESS = 16;
	public static final int COMBO = 17;
	public static final int FURY = 18;
	public static final int HEALING = 19;
	public static final int ARMOR = 20;
	public static final int HEART = 21;
	public static final int LIGHT = 22;
	public static final int CRIPPLE = 23;
	public static final int BARKSKIN = 24;
	public static final int IMMUNITY = 25;
	public static final int BLEEDING = 26;
	public static final int MARK = 27;
	public static final int DEFERRED = 28;
	public static final int DROWSY = 29;
	public static final int MAGIC_SLEEP = 30;
	public static final int THORNS = 31;
	public static final int FORESIGHT = 32;
	public static final int VERTIGO = 33;
	public static final int RECHARGING 	= 34;
	public static final int LOCKED_FLOOR= 35;
	public static final int CORRUPT     = 36;
	public static final int BLESS       = 37;
	public static final int HASTE = 38;
	public static final int REGEN = 39;
	public static final int COUNTDOWN = 40;
	public static final int MOON_FURY = 41;	
	public static final int DEWCHARGE = 42;
	public static final int DISPEL = 43;
	public static final int PFIRE = 44;
	public static final int PTOXIC = 45;
	public static final int PEARTH = 46;
	public static final int PBLOOD = 47;
	public static final int SHIELDBLOCK = 48;
	public static final int OVERFED = 49;
	public static final int TAR = 50;
	public static final int WET = 51;
	public static final int HOT = 52;
	public static final int COLD = 53;
	public static final int DRY = 54;
	public static final int SILENT = 55;
	public static final int DISARM = 56;
	public static final int GLASS_SHIELD = 57;
	public static final int ARMOR_BREAK = 58;
	public static final int TAUNT = 59;
	public static final int GROW_SEED = 60;
	public static final int ATTACK_UP = 61;
	public static final int SHOCKED = 62;
	public static final int RHYTHM = 63;
	public static final int RHYTHM2 = 64;
	public static final int ARCANE = 65;
	public static final int NO_FUSHIGI = 66;
	public static final int GOLDTOUCH = 67;
	public static final int NEEDLING = 68;
	public static final int FEED = 69;
	public static final int NOTICE = 70;
	public static final int BLOODANGRY = 71;
	public static final int WARGROOVE = 72;
	public static final int MECHARMOR = 73;
	public static final int VOICE_UP = 74;
	public static final int HIGH_ATTACK = 75;
	public static final int SMASH = 76;
	public static final int UNKNOW_BOX = 77;
	public static final int MIRROR_SHIELD = 78;

	public static final int HUMAN_FAITH = 79;
	public static final int DEMON_FAITH = 80;
	public static final int LIFE_FAITH = 81;
	public static final int MECH_FAITH = 82;
	public static final int BALANCE_FAITH = 83;
	
	public static final int STONE_ICE = 84;
	public static final int MAGICWEAK = 85;
	public static final int ICE_BITE = 86;
	public static final int ZERO_DEW = 87;

	public static final int FROST_BITE = 88;
	public static final int INF_JUMP = 89;
	public static final int LIGHT_ATK = 90;
	public static final int BE_CORRUPT = 91;
	public static final int BE_OLD = 92;
	public static final int CRAZY_MIND = 93;
	//public static final int ZERO_DEW = 94;



	public static final int SIZE = 7;

	/*private static BuffIndicator heroInstance;

	private SmartTexture texture;
	private TextureFilm film;

	private SparseArray<Image> icons = new SparseArray<Image>();

	private Char ch;

	public BuffIndicator(Char ch) {
		super();

		this.ch = ch;
		if (ch == Dungeon.hero) {
			heroInstance = this;
		}
	}

	@Override
	public void destroy() {
		super.destroy();

		if (this == heroInstance) {
			heroInstance = null;
		}
	}

	@Override
	protected void createChildren() {
		texture = TextureCache.get(Assets.BUFFS_SMALL);
		film = new TextureFilm(texture, SIZE, SIZE);
	}

	@Override
	protected void layout() {
		clear();

		SparseArray<Image> newIcons = new SparseArray<Image>();

		for (Buff buff : ch.buffs()) {
			int icon = buff.icon();
			if (icon != NONE) {
				Image img = new Image(texture);
				img.frame(film.get(icon));
				img.x = x + members.size() * (SIZE + 2);
				img.y = y;
				add(img);

				newIcons.put(icon, img);
			}
		}

		for (Integer key : icons.keyArray()) {
			if (newIcons.get(key) == null) {
				Image icon = icons.get(key);
				icon.origin.set(SIZE / 2);
				add(icon);
				add(new AlphaTweener(icon, 0, 0.6f) {
					@Override
					protected void updateValues(float progress) {
						super.updateValues(progress);
						image.scale.set(1 + 5 * progress);
					};
				});
			}
		}

		icons = newIcons;
	}

	public static void refreshHero() {
		if (heroInstance != null) {
			heroInstance.layout();
		}
	}*/
		private static BuffIndicator heroInstance;
	
	private SmartTexture texture;
	private TextureFilm film;
	
	private SparseArray<BuffIcon> icons = new SparseArray<BuffIcon>();
	
	private Char ch;
	
	public BuffIndicator( Char ch ) {
		super();
		
		this.ch = ch;
		if (ch == Dungeon.hero) {
			heroInstance = this;
		}
	}
	
	@Override
	public void destroy() {
		super.destroy();
		
		if (this == heroInstance) {
			heroInstance = null;
		}
	}
	
	@Override
	protected void createChildren() {
		texture = TextureCache.get( Assets.BUFFS_SMALL );
		film = new TextureFilm( texture, SIZE, SIZE );
	}
	
	@Override
	protected void layout() {
		clear();
		
		SparseArray<BuffIcon> newIcons = new SparseArray<BuffIcon>();
		
		for (Buff buff : ch.buffs()) {
			if (buff.icon() != NONE) {
				BuffIcon icon = new BuffIcon( buff );
				icon.setRect(x + members.size() * (SIZE + 2), y, 9, 12);
				add(icon);
				newIcons.put( buff.icon(), icon );
			}
		}
		
		for (Integer key : icons.keyArray()) {
			if (newIcons.get( key ) == null) {
				Image icon = icons.get( key ).icon;
				icon.origin.set( SIZE / 2 );
				add( icon );
				add( new AlphaTweener( icon, 0, 0.6f ) {
					@Override
					protected void updateValues( float progress ) {
						super.updateValues( progress );
						image.scale.set( 1 + 5 * progress );
					}
                } );
			}
		}
		
		icons = newIcons;
	}

	private class BuffIcon extends Button {

		private Buff buff;

		public Image icon;

		public BuffIcon( Buff buff ){
			super();
			this.buff = buff;

			icon = new Image( texture );
			icon.frame( film.get( buff.icon() ) );
			add( icon );
		}

		@Override
		protected void layout() {
			super.layout();
			icon.x = this.x+1;
			icon.y = this.y+2;
		}

		@Override
		protected void onClick() {
			GameScene.show(new WndInfoBuff(buff));
		}
	}
	
	public static void refreshHero() {
		if (heroInstance != null) {
			heroInstance.layout();
		}
	}

}
