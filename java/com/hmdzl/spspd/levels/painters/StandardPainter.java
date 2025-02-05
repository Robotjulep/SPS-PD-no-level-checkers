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
package com.hmdzl.spspd.levels.painters;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.blobs.weather.WeatherOfDead;
import com.hmdzl.spspd.actors.blobs.weather.WeatherOfQuite;
import com.hmdzl.spspd.actors.blobs.weather.WeatherOfRain;
import com.hmdzl.spspd.actors.blobs.weather.WeatherOfSand;
import com.hmdzl.spspd.actors.blobs.weather.WeatherOfSnow;
import com.hmdzl.spspd.actors.blobs.weather.WeatherOfSun;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.items.Gold;
import com.hmdzl.spspd.items.Heap;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.levels.Room;
import com.hmdzl.spspd.levels.Terrain;
import com.hmdzl.spspd.levels.traps.FireTrap;
import com.watabou.utils.Point;
import com.watabou.utils.Random;

public class StandardPainter extends Painter {

	public static void paint(Level level, Room room) {

		fill(level, room, Terrain.WALL );

		for (Room.Door door : room.connected.values()) {
			door.set(Room.Door.Type.REGULAR);
		}
		
		if (Dungeon.depth==31){
			if (Math.min(room.width(), room.height()) >= 4
					&& Math.max(room.width(), room.height()) >= 6) {
				paintGraveyard(level, room);
				return;
			}
		}
		if (Dungeon.depth==32){
			if (Math.min(room.width(), room.height()) >= 4
					&& Math.max(room.width(), room.height()) >= 6) {
				paintStriped(level, room);
				return;
			}
		}
		if (Dungeon.depth==33){
			if (Math.min(room.width(), room.height()) >= 4
					&& Math.max(room.width(), room.height()) >= 6) {
				paintStudy(level, room);
				return;
			}
		}

		if (!Dungeon.bossLevel() && Random.Int(4) == 0) {
			switch (Random.Int(8)) {
				case 0:
					if (level.feeling != Level.Feeling.GRASS) {
						if (Math.min(room.width(), room.height()) >= 5
								&& Math.max(room.width(), room.height()) >= 6) {
							paintGraveyard(level, room);
							return;
						}
						break;
					} else {
						// Burned room
					}
				case 1:
					if (Dungeon.depth > 1) {
						paintBurned(level, room);
						return;
					}
					break;
				case 2:
					if (Math.max(room.width(), room.height()) >= 4) {
						paintStriped(level, room);
						return;
					}
					break;
				case 3:
					if (room.width() >= 6 && room.height() >= 6) {
						paintStudy(level, room);
						return;
					}
					break;
				case 4:
					if (room.width() >= 6 && room.height() >= 6) {
						paintStudy2(level, room);
						return;
					}
					break;
				case 5:
					if (level.feeling != Level.Feeling.WATER) {
						if (room.connected.size() == 2 && room.width() >= 4
								&& room.height() >= 4) {
							paintBridge(level, room);
							return;
						}
						break;
					} else {
						// Fissure
					}
				case 6:
					if (!Dungeon.bossLevel()
							&& !Dungeon.bossLevel(Dungeon.depth + 1)
							&& (Dungeon.depth < 22 &&  Dungeon.depth > 1)
							&& Math.min(room.width(), room.height()) >= 5) {
						paintFissure(level, room);
						return;
					}
					break;
				case 7:
					if (level.feeling != Level.Feeling.SPECIAL_FLOOR) {
						if (Math.min(room.width(), room.height()) >= 6
								&& Math.max(room.width(), room.height()) >= 6) {
							paintGlassN(level, room);
							return;
						}
						break;
					} else {
						// Burned room
					}
			}
		}

		fill(level, room, 1, Terrain.EMPTY);

		if (!Dungeon.bossLevel() && Random.Int(5) == 0) {
			switch (Random.Int(4)) {
				case 0:
					WeatherOfRain rain = (WeatherOfRain) level.blobs.get(WeatherOfRain.class);
					if (rain == null) {
						rain = new WeatherOfRain();
					}
					for (int i = room.top + 1; i < room.bottom; i++) {
						for (int j = room.left + 1; j < room.right; j++) {
							rain.seed(j + Level.getWidth() * i, 1);
						}
					}
					level.blobs.put(WeatherOfRain.class, rain);
					break;
				case 1:
					WeatherOfSand sand = (WeatherOfSand) level.blobs.get(WeatherOfSand.class);
					if (sand == null) {
						sand = new WeatherOfSand();
					}
					for (int i = room.top + 1; i < room.bottom; i++) {
						for (int j = room.left + 1; j < room.right; j++) {
							sand.seed(j + Level.getWidth() * i, 1);
						}
					}
					level.blobs.put(WeatherOfSand.class, sand);
					break;
				case 2:
					WeatherOfSnow snow = (WeatherOfSnow) level.blobs.get(WeatherOfSnow.class);
					if (snow == null) {
						snow = new WeatherOfSnow();
					}
					for (int i = room.top + 1; i < room.bottom; i++) {
						for (int j = room.left + 1; j < room.right; j++) {
							snow.seed(j + Level.getWidth() * i, 1);
						}
					}
					level.blobs.put(WeatherOfSnow.class, snow);
					break;
				case 3:
					WeatherOfSun sun = (WeatherOfSun) level.blobs.get(WeatherOfSun.class);
					if (sun == null) {
						sun = new WeatherOfSun();
					}
					for (int i = room.top + 1; i < room.bottom; i++) {
						for (int j = room.left + 1; j < room.right; j++) {
							sun.seed(j + Level.getWidth() * i, 1);
						}
					}
					level.blobs.put(WeatherOfSun.class, sun);
					break;

			}
		}
	}

	private static void paintBurned(Level level, Room room) {
		for (int i = room.top + 1; i < room.bottom; i++) {
			for (int j = room.left + 1; j < room.right; j++) {
				int cell = i * Level.WIDTH + j;
				int t = Terrain.EMBERS;
				switch (Random.Int(5)) {
				case 0:
					t = Terrain.EMPTY;
					break;
				case 1:
					t = Terrain.TRAP;
					level.setTrap(new FireTrap().reveal(), cell);
					break;
				case 2:
					t = Terrain.SECRET_TRAP;
					level.setTrap(new FireTrap().hide(), cell);
					break;
				case 3:
					t = Terrain.INACTIVE_TRAP;
					FireTrap trap = new FireTrap();
					trap.reveal().active = false;
					level.setTrap(trap, cell);
					break;
				}
				level.map[i * Level.getWidth() + j] = t;
			}
		}
	}

	private static void paintGraveyard(Level level, Room room) {
		fill(level, room.left + 1, room.top + 1, room.width() - 1,
				room.height() - 1, Terrain.GRASS);

		int w = room.width() - 1;
		int h = room.height() - 1;
		int nGraves = Math.max(w, h) / 2;

		int index = Random.Int(nGraves);

		int shift = Random.Int(2);
		for (int i = 0; i < nGraves; i++) {
			int pos = w > h ? room.left + 1 + shift + i * 2
					+ (room.top + 2 + Random.Int(h - 2)) * Level.getWidth()
					: (room.left + 2 + Random.Int(w - 2))
							+ (room.top + 1 + shift + i * 2) * Level.getWidth();
			level.drop(i == index ? Generator.random() : new Gold().random(),
					pos).type = Heap.Type.TOMB;		
		}

		if (Random.Int(10)==0){
		WeatherOfDead light = (WeatherOfDead) level.blobs.get(WeatherOfDead.class);
			if (light == null) {
				light = new WeatherOfDead();
			}
			for (int i = room.top + 1; i < room.bottom; i++) {
				for (int j = room.left + 1; j < room.right; j++) {
					light.seed(j + Level.getWidth() * i, 1);
				}
			}
			level.blobs.put(WeatherOfDead.class, light);
		}
		
	}

	private static void paintStriped(Level level, Room room) {
		fill(level, room.left + 1, room.top + 1, room.width() - 1,
				room.height() - 1, Terrain.EMPTY_SP);

		if (room.width() > room.height()) {
			for (int i = room.left + 2; i < room.right; i += 2) {
				fill(level, i, room.top + 1, 1, room.height() - 1,
						Terrain.HIGH_GRASS);
			}
		} else {
			for (int i = room.top + 2; i < room.bottom; i += 2) {
				fill(level, room.left + 1, i, room.width() - 1, 1,
						Terrain.HIGH_GRASS);
			}
		}
	}

	// TODO: this is almost a special room type now, consider moving this into
	// its own painter if/when you address room gen significantly.
	private static void paintStudy(Level level, Room room) {
		fill(level, room.left + 1, room.top + 1, room.width() - 1,
				room.height() - 1, Terrain.BOOKSHELF);
		fill(level, room.left + 2, room.top + 2, room.width() - 3,
				room.height() - 3, Terrain.EMPTY_SP);

		for (Point door : room.connected.values()) {
			if (door.x == room.left) {
				set(level, door.x + 1, door.y, Terrain.EMPTY);
			} else if (door.x == room.right) {
				set(level, door.x - 1, door.y, Terrain.EMPTY);
			} else if (door.y == room.top) {
				set(level, door.x, door.y + 1, Terrain.EMPTY);
			} else if (door.y == room.bottom) {
				set(level, door.x, door.y - 1, Terrain.EMPTY);
			}
		}
		Point center = room.center();
		set(level, center, Terrain.PEDESTAL);
		if (Random.Int(2) != 0) {
			Item prize = level.findPrizeItem();
			if (prize != null) {
				level.drop(prize, (room.center().x + center.y * Level.getWidth()));
				return;
			}
		}

		level.drop(Generator.random(Random.oneOf(Generator.Category.POTION,
				Generator.Category.SCROLL)), (room.center().x + center.y
				* Level.getWidth()));
				
		if (Random.Int(5)==0){
		WeatherOfQuite light = (WeatherOfQuite) level.blobs.get(WeatherOfQuite.class);
		if (light == null) {
			light = new WeatherOfQuite();
		}
		for (int i = room.top + 1; i < room.bottom; i++) {
			for (int j = room.left + 1; j < room.right; j++) {
				light.seed(j + Level.getWidth() * i, 1);
			}
		}
		level.blobs.put(WeatherOfQuite.class, light);	}
				
	}
	
	private static void paintStudy2( Level level, Room room ) {

        fill( level, room.left + 1, room.top + 1, room.width() - 1, room.height() - 1 , Terrain.EMPTY_SP );

        if (room.width() > room.height()) {
            for (int i=room.left + 2; i < room.right; i += 2) {
                fill( level, i, room.top + 2, 1, room.height() - 3, Terrain.BOOKSHELF );
            }
        } else {
            for (int i=room.top + 2; i < room.bottom; i += 2) {
                fill( level, room.left + 2, i, room.width() - 3, 1, Terrain.BOOKSHELF );
            }
        }

//		fill( bonus, room.left + 1, room.top + 1, room.width() - 1, room.height() - 1 , Terrain.SHELF_EMPTY );
//		fill( bonus, room.left + 2, room.top + 2, room.width() - 3, room.height() - 3 , Terrain.EMPTY_SP );
//
		for (Point door : room.connected.values()) {
			if (door.x == room.left) {
				set( level, door.x + 1, door.y, Terrain.EMPTY_SP );
			} else if (door.x == room.right) {
				set( level, door.x - 1, door.y, Terrain.EMPTY_SP );
			} else if (door.y == room.top) {
				set( level, door.x, door.y + 1, Terrain.EMPTY_SP );
			} else if (door.y == room.bottom) {
				set( level, door.x , door.y - 1, Terrain.EMPTY_SP );
			}
		}
		
		if (Random.Int(5)==0){
		WeatherOfQuite light = (WeatherOfQuite) level.blobs.get(WeatherOfQuite.class);
		if (light == null) {
			light = new WeatherOfQuite();
		}
		for (int i = room.top + 1; i < room.bottom; i++) {
			for (int j = room.left + 1; j < room.right; j++) {
				light.seed(j + Level.getWidth() * i, 1);
			}
		}
		level.blobs.put(WeatherOfQuite.class, light);}

//		set( bonus, room.center(), Terrain.PEDESTAL );
	}	
	
	private static void paintGlassN( Level level, Room room ) {

		fill(level, room.left + 1, room.top + 1, room.width() - 1,
				room.height() - 1, Terrain.EMPTY_SP);
		fill(level, room.left + 2, room.top + 2, room.width() - 3,
				room.height() - 3, Terrain.GLASS_WALL);

		for (Point door : room.connected.values()) {
			if (door.x == room.left) {
				set(level, door.x + 1, door.y, Terrain.EMPTY);
			} else if (door.x == room.right) {
				set(level, door.x - 1, door.y, Terrain.EMPTY);
			} else if (door.y == room.top) {
				set(level, door.x, door.y + 1, Terrain.EMPTY);
			} else if (door.y == room.bottom) {
				set(level, door.x, door.y - 1, Terrain.EMPTY);
			}
		}
		Point center = room.center();
		set(level, center, Terrain.PEDESTAL);
		level.drop(Generator.random(Random.oneOf(Generator.Category.ARTIFACT,
				Generator.Category.RING)), (room.center().x + center.y
				* Level.getWidth()));


//
		
	}		
	

	private static void paintBridge(Level level, Room room) {

	    if( room.connected.size() == 2 ) {
	
		fill(level, room.left + 1, room.top + 1, room.width() - 1,
				room.height() - 1,
				!Dungeon.bossLevel() && !Dungeon.bossLevel(Dungeon.depth + 1) && (Dungeon.depth < 22 || Dungeon.depth > 26)
						&& Random.Int(3) == 0 ? Terrain.TRAP_AIR : Terrain.WATER);

		Point door1 = null;
		Point door2 = null;
		for (Point p : room.connected.values()) {
			if (door1 == null) {
				door1 = p;
			} else {
				door2 = p;
			}
		}

		if ((door1.x == room.left && door2.x == room.right)
				|| (door1.x == room.right && door2.x == room.left)) {

			int s = room.width() / 2;

			drawInside(level, room, door1, s, Terrain.EMPTY_SP);
			drawInside(level, room, door2, s, Terrain.EMPTY_SP);
			fill(level, room.center().x, Math.min(door1.y, door2.y), 1,
					Math.abs(door1.y - door2.y) + 1, Terrain.EMPTY_SP);

		} else if ((door1.y == room.top && door2.y == room.bottom)
				|| (door1.y == room.bottom && door2.y == room.top)) {

			int s = room.height() / 2;

			drawInside(level, room, door1, s, Terrain.EMPTY_SP);
			drawInside(level, room, door2, s, Terrain.EMPTY_SP);
			fill(level, Math.min(door1.x, door2.x), room.center().y,
					Math.abs(door1.x - door2.x) + 1, 1, Terrain.EMPTY_SP);

		} else if (door1.x == door2.x) {

			fill(level, door1.x == room.left ? room.left + 1 : room.right - 1,
					Math.min(door1.y, door2.y), 1,
					Math.abs(door1.y - door2.y) + 1, Terrain.EMPTY_SP);

		} else if (door1.y == door2.y) {

			fill(level, Math.min(door1.x, door2.x),
					door1.y == room.top ? room.top + 1 : room.bottom - 1,
					Math.abs(door1.x - door2.x) + 1, 1, Terrain.EMPTY_SP);

		} else if (door1.y == room.top || door1.y == room.bottom) {

			drawInside(level, room, door1, Math.abs(door1.y - door2.y),
					Terrain.EMPTY_SP);
			drawInside(level, room, door2, Math.abs(door1.x - door2.x),
					Terrain.EMPTY_SP);

		} else if (door1.x == room.left || door1.x == room.right) {

			drawInside(level, room, door1, Math.abs(door1.x - door2.x),
					Terrain.EMPTY_SP);
			drawInside(level, room, door2, Math.abs(door1.y - door2.y),
					Terrain.EMPTY_SP);

		}
	    } else {
            fill( level, room.left + 1, room.top + 1, room.width() - 1, room.height() - 1, Terrain.EMPTY_SP );

            fill( level, room.left + 2, room.top + 2, room.width() - 3, room.height() - 3,
                    level.feeling == Level.Feeling.WATER ?
                            Terrain.WATER : Terrain.CHASM );
        }

        for (Room.Door door : room.connected.values()) {
            door.set( Room.Door.Type.REGULAR );
        }	
	}

	private static void paintFissure(Level level, Room room) {
		fill(level, room.left + 1, room.top + 1, room.width() - 1,
				room.height() - 1, Terrain.EMPTY);

		for (int i = room.top + 2; i < room.bottom - 1; i++) {
			for (int j = room.left + 2; j < room.right - 1; j++) {
				int v = Math.min(i - room.top, room.bottom - i);
				int h = Math.min(j - room.left, room.right - j);
				if (Math.min(v, h) > 2 || Random.Int(2) == 0) {
					set(level, j, i, Terrain.CHASM);
				}
			}
		}
	}
}
