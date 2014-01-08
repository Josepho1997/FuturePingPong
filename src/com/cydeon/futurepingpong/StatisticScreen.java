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

import javax.microedition.khronos.opengles.GL10;

import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.gl.Camera2D;
import com.badlogic.androidgames.framework.gl.SpriteBatcher;
import com.badlogic.androidgames.framework.impl.GLScreen;
import com.badlogic.androidgames.framework.math.Vector2;

public class StatisticScreen extends GLScreen {
	public static String userpHC, cpupHC, lwHC, rwHC, bwHC, topwHC, scoreUser, scoreCPU;
	Camera2D guiCam;
	SpriteBatcher batcher;
	Vector2 touchPoint;
	World2P world;

	public StatisticScreen(Game game) {
		super(game);
		guiCam = new Camera2D(glGraphics, 1280, 768);
		userpHC = "The ball bounced off your paddle: " + Integer.toString(Ball2P.USER_PADDLE_HIT_COUNTER) + " times!";
		cpupHC = "The ball bounced off the CPU's paddle: " + Integer.toString(Ball2P.CPU_PADDLE_HIT_COUNTER) + " times!";
		lwHC = "The ball bounced off the left wall: " + Integer.toString(Ball2P.LEFT_WALL_HIT_COUNTER) + " times!";
		rwHC = "The ball bounced off the right wall: " + Integer.toString(Ball2P.RIGHT_WALL_HIT_COUNTER) + " times!";
		bwHC = "The ball bounced off the bottom wall: " + Integer.toString(Ball2P.LOWER_WALL_HIT_COUNTER) + " times!";
		topwHC = "The ball bounced off the top wall: " + Integer.toString(Ball2P.UPPER_WALL_HIT_COUNTER) + "times!";
		scoreUser = "You scored " + Integer.toString(Ball2P.USER_SCORE_COUNTER) + " points!";
		scoreCPU = "The computer scored " + Integer.toString(Ball2P.CPU_SCORE_COUNTER) + " points!";
		batcher = new SpriteBatcher(glGraphics, 200);
		touchPoint = new Vector2();
		world = new World2P();
	}

	@Override
	public void update(float deltaTime) {
		
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
			Assets.font.drawText(batcher, cpupHC, 10, 718);
			Assets.font.drawText(batcher, lwHC, 10, 688);
			Assets.font.drawText(batcher, rwHC, 10, 658);
			gl.glPopMatrix();
			batcher.endBatch();
			
			batcher.beginBatch(Assets.key);
			gl.glPushMatrix();
			gl.glColor4f(1, 1, 1, 1);
			Assets.font.drawText(batcher, scoreCPU, 10, 628);
			Assets.font.drawText(batcher, scoreUser, 10, 598);
			Assets.font.drawText(batcher, topwHC, 10, 568);
			Assets.font.drawText(batcher, userpHC, 10, 538);
			gl.glPopMatrix();
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
