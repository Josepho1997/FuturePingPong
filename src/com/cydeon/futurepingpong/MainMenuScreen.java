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

import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Input.TouchEvent;
import com.badlogic.androidgames.framework.gl.Camera2D;
import com.badlogic.androidgames.framework.gl.SpriteBatcher;
import com.badlogic.androidgames.framework.impl.GLScreen;
import com.badlogic.androidgames.framework.math.OverlapTester;
import com.badlogic.androidgames.framework.math.Rectangle;
import com.badlogic.androidgames.framework.math.Vector2;

public class MainMenuScreen extends GLScreen{
	Camera2D guiCam;
	SpriteBatcher batcher;
	Rectangle soundBounds;
	Rectangle playBounds;
	Rectangle settingsBounds;
	Vector2 touchPoint;
	World2P world;
	WorldRender renderer;
	
	public MainMenuScreen(Game game) {
		super(game);
		guiCam = new Camera2D(glGraphics, 1280, 768);
		batcher = new SpriteBatcher(glGraphics, 100);
		//soundBounds = new Rectangle();
		playBounds = new Rectangle((1280 / 2) -200, 500 - 114, 600, 114 * 3);
		settingsBounds = new Rectangle((1280 / 2) - (84 * 2) , 250, 256 * 2, 88);
		touchPoint = new Vector2();
		world = new World2P();
		renderer = new WorldRender(glGraphics, batcher, world);
		//World2P.mode = World2P.GAME_MODE_PREVIEW;
	}

	@Override
	public void update(float deltaTime) {
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();
        
        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);                        
            if(event.type == TouchEvent.TOUCH_UP) {
                touchPoint.set(event.x, event.y);
                guiCam.touchToWorld(touchPoint);
                Log.d("TouchPoints", "X: " + Float.toString(touchPoint.x) + " Y: " + Float.toString(touchPoint.y));
                
                if(OverlapTester.pointInRectangle(playBounds, touchPoint)) {
                	game.setScreen(new GameScreen(game));
                	World2P.mode = World2P.WORLD_STATE_RUNNING;
                	return;
                }
                
                if(OverlapTester.pointInRectangle(settingsBounds, touchPoint)) {
                	game.setScreen(new SettingsScreen(game));
                	return;
                }
            }
        }
	}

	@Override
	public void present(float deltaTime) {
		 GL10 gl = glGraphics.getGL();        
	        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	        guiCam.setViewportAndMatrices();
	        
	        gl.glEnable(GL10.GL_TEXTURE_2D);
	        
	      //  batcher.beginBatch(Assets.background);
	      //  batcher.drawSprite(160, 240, 320, 480, Assets.backgroundRegion);
	      //  batcher.endBatch();
	        
	        
	        
	        gl.glEnable(GL10.GL_BLEND);
	        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
	        
	        gl.glEnable(GL10.GL_BLEND);
	        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);               

			gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT); // 5
	        gl.glClearColor(0.2f, 0.4f, 0.6f, 1.0f);
	       // batcher.beginBatch(Assets.background);
	       // batcher.drawSprite(600 / 2, 1024 / 2, 600, 1024, Assets.backgroundRegion);
	        
	       // batcher.endBatch();
	        
	        batcher.beginBatch(Assets.menu);                 
	        
	        batcher.drawSprite((1280 / 2), 500, 200 * 2, 114 * 2, Assets.play);
	        batcher.drawSprite((1280 / 2), 250, 256 * 2, 88 * 2, Assets.settings);
	                
	        batcher.endBatch();
	        //Log.d("MainMenuLoaded", "MainMenu Loaded!!");
	        
	      //  renderer.renderPreview(); 
	        
	        gl.glDisable(GL10.GL_BLEND);
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void dispose() {
		
	}
}
