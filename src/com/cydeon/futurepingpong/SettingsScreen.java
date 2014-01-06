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
import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

import android.util.Log;

import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Input.TouchEvent;
import com.badlogic.androidgames.framework.gl.Camera2D;
import com.badlogic.androidgames.framework.gl.SpriteBatcher;
import com.badlogic.androidgames.framework.impl.GLScreen;
import com.badlogic.androidgames.framework.math.OverlapTester;
import com.badlogic.androidgames.framework.math.Rectangle;
import com.badlogic.androidgames.framework.math.Vector2;

public class SettingsScreen extends GLScreen {
	float etBVF;
	float etCPUPVF;
	int etWPI;
	SpriteBatcher batcher;
	Camera2D guiCam;
	Random ran1, ran2, ran3;
	Float ranr, rang, ranb;
	Rectangle p1Bounds, p2Bounds, ballBounds, backgroundBounds, pauseBounds, backBounds;
	Vector2 touchPoint;
	
	public SettingsScreen(Game game) {
		super(game);
		batcher = new SpriteBatcher(glGraphics, 100);
		guiCam = new Camera2D(glGraphics, 1280, 768);
		ran1 = new Random();
		ran2 = new Random();
		ran3 = new Random();
		touchPoint = new Vector2();
		ranr = ran1.nextFloat();
		rang = ran2.nextFloat();
		ranb = ran3.nextFloat();
		p1Bounds = new Rectangle((1280 / 2) - (576 / 2), 700 - (84 / 2), 576, 84);
		p2Bounds = new Rectangle((1280 / 2) - (576 / 2), 575, 576, 72);
		ballBounds = new Rectangle((1280 / 2) - (384 / 2), 450, 384, 96);
		pauseBounds = new Rectangle((1280 / 2) - (468 / 2), 325, 468, 84);
		backgroundBounds = new Rectangle((1280 / 2) - (720 / 2), 200, 720, 96);
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
                
                if(OverlapTester.pointInRectangle(p1Bounds, touchPoint)) {
                	game.setScreen(new P1Screen(game));
                	return;
                }
                
                if(OverlapTester.pointInRectangle(p2Bounds, touchPoint)) {
                	game.setScreen(new P2Screen(game));
                	return;
                }
                
                if(OverlapTester.pointInRectangle(ballBounds, touchPoint)) {
                	game.setScreen(new BallScreen(game));
                	return;
                }
                
                if(OverlapTester.pointInRectangle(pauseBounds, touchPoint)) {
                	game.setScreen(new PauseScreen(game));
                	return;
                }
                
                if(OverlapTester.pointInRectangle(backgroundBounds, touchPoint)) {
                	game.setScreen(new BackgroundScreen(game));
                	return;
                }
                
                if(OverlapTester.pointInRectangle(backBounds, touchPoint)) {
                	game.setScreen(new MainMenuScreen(game));
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
	        gl.glClearColor(ranr, rang, ranb, 1.0f);
	        batcher.beginBatch(Assets.textItems);
	        batcher.drawSprite((1280 / 2), 700, 576, 84, Assets.paddle1Color);
	        batcher.drawSprite((1280 / 2), 575, 576, 72, Assets.paddle2Color);
	        batcher.drawSprite((1280 / 2), 450, 384, 96, Assets.ballColor);
	        batcher.drawSprite((1280 / 2), 325, 468, 84, Assets.pauseColor);
	        batcher.drawSprite((1280 / 2), 200, 720, 96, Assets.backgroundColor);
	        batcher.endBatch();
	        batcher.beginBatch(Assets.menu);
	        batcher.drawSprite(45, 36, 90, 36, Assets.back);
	        batcher.endBatch();
	       // Log.d("Settings", "Settings");
	                
	       // batcher.endBatch();
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

	/*
	 EditText etBV = new EditText(this);
		etBV = (EditText) findViewById(R.id.etBallVelocity);
		EditText etCPUPV = (EditText) findViewById(R.id.etCPUPaddleVelocity);
		EditText etWP = (EditText) findViewById(R.id.etWinningPoint);
		
		//EditText for Ball Velocity
		if(etBV.getText() != null)
			etBVF = Float.parseFloat(etBV.getText().toString());
		else
			etBV.setText(Float.toString(3.0f));
		if(etBVF > 8.0f) {
			etBVF = 8.0f;
			etBV.setText(Float.toString(etBVF));
		}
		if(etBVF < 0.1f) {
			etBVF = 0.1f;
			etBV.setText(Float.toString(etBVF));
		}
		Settings.BALL_MOVE_SPEED = etBVF;
		//end
		
		//EditText for CPU Paddle Velocity
		if(etCPUPV.getText() != null)
			etCPUPVF = Float.parseFloat(etCPUPV.getText().toString());
		else
			etCPUPV.setText(Float.toString(5.0f));
		if(etCPUPVF > 10.0f) {
			etCPUPVF = 10.0f;
			etCPUPV.setText(Float.toString(etCPUPVF));
		}
		if(etCPUPVF < 0.1f) {
			etCPUPVF = 0.1f;
			etCPUPV.setText(Float.toString(etCPUPVF));
		}
		Settings.PADDLE_MOVE_SPEED = etCPUPVF;
		//end
		
		//EditText for Winning Point
		if(etWP.getText() != null)
			etWPI = Integer.parseInt(etWP.getText().toString());
		else
			etWP.setText(Integer.toString(7));
		if(etWPI < 1) {
			etWPI = 1;
			etWP.setText(Integer.toString(etWPI));
		}
		Settings.WINNING_POINT = etWPI;
	}
	  */