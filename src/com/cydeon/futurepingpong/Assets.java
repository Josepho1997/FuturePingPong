/* FurturePingPong Android application
 *   Copyright (C) 2013 Joseph O'Neill
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.cydeon.futurepingpong;

import com.badlogic.androidgames.framework.gl.Font;
import com.badlogic.androidgames.framework.gl.Texture;
import com.badlogic.androidgames.framework.gl.TextureRegion;
import com.badlogic.androidgames.framework.impl.GLGame;

public class Assets {
/*	public static Texture background;
	public static TextureRegion backgroundRegion; */
	
	public static Texture gameitems;
	public static TextureRegion paddle;
	public static TextureRegion back;
	//public static TextureRegion soundOn;
	//public static TextureRegion soundOff;
	
	public static TextureRegion ball;
	public static Texture menu;
	public static TextureRegion play;
	public static TextureRegion settings;
	
	public static Texture textItems;
	public static TextureRegion touchToStart;
	public static TextureRegion touchToResume;
	public static TextureRegion paddle1Color;
	public static TextureRegion paddle2Color;
	public static TextureRegion ballColor;
	public static TextureRegion pauseColor;
	public static TextureRegion backgroundColor;
	public static TextureRegion generateRandomColor;
	public static TextureRegion generateRandomTransperancy;
	
	public static Texture key;
	public static Font font;
	
	
	public static void load(GLGame game) {
		/*background = new Texture(game, "bg.png");
		backgroundRegion = new TextureRegion(background, 0, 0, 600, 1024); */
		
		gameitems = new Texture(game, "PongStuff.png");
		menu = new Texture(game, "pongmenu.png");
		textItems = new Texture(game, "textitems.png");
		back = new TextureRegion(menu, 0, 120 * 2, 120 * 2, 48 * 2);
		//items = new Texture(game, "items.png");
		ball = new TextureRegion(gameitems, 0, 0, 220, 220);
		paddle = new TextureRegion(gameitems, 0, 252, 54, 260);
		//paddle2 = new TextureRegion(items, 140, 0, 144, 144);
		//soundOn = new TextureRegion(items, 288, 0, 72, 72);
		//soundOff = new TextureRegion(items, 360, 0, 72, 72);
		play = new TextureRegion(menu, 0, 0, 200 * 2, 114 * 2);
		settings = new TextureRegion(menu, 0, 168 * 2, 256 * 2, 88 * 2);
		touchToStart = new TextureRegion(textItems, 0, 0, 684, 108);
		touchToResume = new TextureRegion(textItems, 0, 144, 684, 84);
		paddle1Color = new TextureRegion(textItems, 0, 240, 576, 84);
		paddle2Color = new TextureRegion(textItems, 0, 360, 576, 72);
		ballColor = new TextureRegion(textItems, 0, 468, 384, 96);
		pauseColor = new TextureRegion(textItems, 0, 588, 468, 84);
		backgroundColor = new TextureRegion(textItems, 0, 696, 720, 96);
		generateRandomColor = new TextureRegion(textItems, 0, 795, 936, 72);
		generateRandomTransperancy = new TextureRegion(textItems, 0, 888, 948, 84);
		key = new Texture(game, "items.png");
		font = new Font(key, 224, 0, 16, 16, 20);
	}
	
	public static void reload() {
       // background.reload();
        gameitems.reload();
        menu.reload();
        textItems.reload();
        key.reload();
        
        //menu.reload();
       // if(Settings.soundEnabled)
       //     music.play();
    }
	
}
