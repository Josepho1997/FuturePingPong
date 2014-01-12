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

import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Input.TouchEvent;
import com.badlogic.androidgames.framework.gl.Camera2D;
import com.badlogic.androidgames.framework.gl.SpriteBatcher;
import com.badlogic.androidgames.framework.impl.GLScreen;
import com.badlogic.androidgames.framework.math.OverlapTester;
import com.badlogic.androidgames.framework.math.Rectangle;
import com.badlogic.androidgames.framework.math.Vector2;

public class StatisticScreen extends GLScreen {
	public static String userpHC, cpupHC, lwHC, rwHC, bwHC, topwHC, scoreUser, scoreCPU;
	Camera2D guiCam;
	SpriteBatcher batcher;
	Vector2 touchPoint;
	World2P world;
	Rectangle backBounds;

	public StatisticScreen(Game game) {
		super(game);
		guiCam = new Camera2D(glGraphics, 1280, 768);
		Settings.load(game.getFileIO());
		userpHC = "The ball has bounced off of your paddle a total of: " + Settings.gameStats[0] + " times!";
		cpupHC = "The ball has bounced off of the cpu's paddle a total of: " + Settings.gameStats[1] + " times!";
		lwHC = "The ball has bounced off the left wall a total of: " + Settings.gameStats[2] + " times!";
		rwHC = "The ball has bounced off the right wall a total of: " + Settings.gameStats[3] + " times!";
		bwHC = "The ball has bounced off the lower wall a total of: " + Settings.gameStats[4] + " times!";
		topwHC = "The ball has bounced of the upper wall a total of: " + Settings.gameStats[5] + " times!";
		scoreUser = "You have scored a total of: " + Settings.gameStats[6] + " points!";
		scoreCPU = "The CPU has scored a total of: " + Settings.gameStats[7] + " points!"; 
		batcher = new SpriteBatcher(glGraphics, 200);
		touchPoint = new Vector2();
		world = new World2P();
		backBounds = new Rectangle(0, 0, 78 * 2, 48 * 2);
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
		
		if(OverlapTester.pointInRectangle(backBounds, touchPoint)) {
        	game.setScreen(new MainMenuScreen(game));
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
	        gl.glEnable(GL10.GL_BLEND);
	        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
			gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT); // 5
	        gl.glClearColor(Settings.rgbBackground[0], Settings.rgbBackground[1], Settings.rgbBackground[2], 1.0f);
	        
	        batcher.beginBatch(Assets.key);
			gl.glPushMatrix();
			gl.glColor4f(1, 1, 1, 1);
			Assets.font.drawText(batcher, userpHC, 10, 748);
			Assets.font.drawText(batcher, cpupHC, 10, 658);
			Assets.font.drawText(batcher, lwHC, 10, 568);
			gl.glPopMatrix();
			batcher.endBatch();
			
			batcher.beginBatch(Assets.key);
			gl.glPushMatrix();
			gl.glColor4f(1, 1, 1, 1);
			Assets.font.drawText(batcher, rwHC, 10, 478);
			Assets.font.drawText(batcher, scoreCPU, 10, 388);
			Assets.font.drawText(batcher, scoreUser, 10, 298);
			Assets.font.drawText(batcher, topwHC, 10, 208);
			gl.glPopMatrix();
			batcher.endBatch();

			batcher.beginBatch(Assets.key);
			Assets.font.drawText(batcher, bwHC, 10, 118);
			batcher.endBatch();
			
			batcher.beginBatch(Assets.menu);
		    batcher.drawSprite(45, 36, 90, 36, Assets.back);
		    batcher.endBatch();
	        //Log.d("MainMenuLoaded", "MainMenu Loaded!!");
	        
	      //  renderer.renderPreview(); 
	        
	        gl.glDisable(GL10.GL_BLEND);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
