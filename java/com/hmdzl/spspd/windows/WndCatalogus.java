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
package com.hmdzl.spspd.windows;

import com.hmdzl.spspd.ShatteredPixelDungeon;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.potions.Potion;
import com.hmdzl.spspd.items.scrolls.Scroll;
import com.hmdzl.spspd.messages.Messages;import com.hmdzl.spspd.ResultDescriptions;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.scenes.PixelScene;
import com.hmdzl.spspd.sprites.ItemSprite;
import com.hmdzl.spspd.ui.ScrollPane;
import com.hmdzl.spspd.ui.Window;
import com.watabou.noosa.RenderedText;
import com.watabou.noosa.ui.Component;

import java.util.ArrayList;

public class WndCatalogus extends WndTabbed {

	private static final int WIDTH_P = 112;
	private static final int HEIGHT_P = 160;

	private static final int WIDTH_L = 128;
	private static final int HEIGHT_L = 128;

	private static final int ITEM_HEIGHT = 18;

	private RenderedText txtTitle;
	private ScrollPane list;

	private ArrayList<ListItem> items = new ArrayList<WndCatalogus.ListItem>();

	private static boolean showPotions = true;

	public WndCatalogus() {

		super();

		if (ShatteredPixelDungeon.landscape()) {
			resize(WIDTH_L, HEIGHT_L);
		} else {
			resize(WIDTH_P, HEIGHT_P);
		}

		txtTitle = PixelScene.renderText(Messages.get(this, "title"), 9);
		txtTitle.hardlight(Window.TITLE_COLOR);
		//txtTitle.measure();
		add(txtTitle);

		list = new ScrollPane(new Component()) {
			@Override
			public void onClick(float x, float y) {
				int size = items.size();
				for (int i = 0; i < size; i++) {
					if (items.get(i).onClick(x, y)) {
						break;
					}
				}
			}
		};
		add(list);
		list.setRect(0, txtTitle.height(), width, height - txtTitle.height());

		boolean showPotions = WndCatalogus.showPotions;
		Tab[] tabs = { new LabeledTab(Messages.get(this, "potions")) {
			@Override
			protected void select(boolean value) {
				super.select(value);
				WndCatalogus.showPotions = value;
				updateList();
			}
        }, new LabeledTab(Messages.get(this, "scrolls")) {
			@Override
			protected void select(boolean value) {
				super.select(value);
				WndCatalogus.showPotions = !value;
				updateList();
			}
        } };
		for (Tab tab : tabs) {
			add(tab);
		}

		layoutTabs();

		select(showPotions ? 0 : 1);
	}

	private void updateList() {

		txtTitle.text(Messages.get(this, "title", showPotions ? Messages.get(this, "potions") : Messages.get(this, "scrolls") ));
		//txtTitle.measure();
		txtTitle.x = PixelScene.align(PixelScene.uiCamera,
				(width - txtTitle.width()) / 2);

		items.clear();

		Component content = list.content();
		content.clear();
		list.scrollTo(0, 0);

		float pos = 0;
		for (Class<? extends Item> itemClass : showPotions ? Potion.getKnown()
				: Scroll.getKnown()) {
			ListItem item = new ListItem(itemClass);
			item.setRect(0, pos, width, ITEM_HEIGHT);
			content.add(item);
			items.add(item);

			pos += item.height();
		}

		for (Class<? extends Item> itemClass : showPotions ? Potion
				.getUnknown() : Scroll.getUnknown()) {
			ListItem item = new ListItem(itemClass);
			item.setRect(0, pos, width, ITEM_HEIGHT);
			content.add(item);
			items.add(item);

			pos += item.height();
		}

		content.setSize(width, pos);
	}

	private static class ListItem extends Component {

		private Item item;
		private boolean identified;

		private ItemSprite sprite;
		private RenderedText label;

		public ListItem(Class<? extends Item> cl) {
			super();

			try {
				item = cl.newInstance();
				if (identified = item.isIdentified()) {
					sprite.view(item.image(), null);
					label.text(item.name());
				} else {
					sprite.view(0, null);
					label.text(item.trueName());
					label.hardlight(0xCCCCCC);
				}
			} catch (Exception e) {
				// Do nothing
			}
		}

		@Override
		protected void createChildren() {
			sprite = new ItemSprite();
			add(sprite);

			label = PixelScene.renderText(8);
			add(label);
		}

		@Override
		protected void layout() {
			sprite.y = PixelScene.align(y + (height - sprite.height) / 2);

			label.x = sprite.x + sprite.width;
			label.y = PixelScene.align(y + (height - label.baseLine()) / 2);
		}

		public boolean onClick(float x, float y) {
			if (identified && inside(x, y)) {
				GameScene.show(new WndInfoItem(item));
				return true;
			} else {
				return false;
			}
		}
	}
}
