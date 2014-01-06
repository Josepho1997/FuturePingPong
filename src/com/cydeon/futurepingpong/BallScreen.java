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

import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Input.TouchEvent;
import com.badlogic.androidgames.framework.gl.Camera2D;
import com.badlogic.androidgames.framework.gl.SpriteBatcher;
import com.badlogic.androidgames.framework.impl.GLScreen;
import com.badlogic.androidgames.framework.math.OverlapTester;
import com.badlogic.androidgames.framework.math.Rectangle;
import com.badlogic.androidgames.framework.math.Vector2;

public class BallScreen extends GLScreen{
	SpriteBatcher batcher;
	Camera2D guiCam;
	Random ran;
	Float ranR, ranG, ranB, ranA, bRanR, bRanG, bRanB;
	String bgC, bC;
	Vector2 touchPoint;
    int x = 0;
    Rectangle grcBounds, backBounds;
    
	public BallScreen(Game game) {
		super(game);
		batcher = new SpriteBatcher(glGraphics, 100);
		guiCam = new Camera2D(glGraphics, 1280, 768);
		ran = new Random();
		bRanR = ran.nextFloat();
		bRanG = ran.nextFloat();
		bRanB = ran.nextFloat();
		bgC = "Background's Current Color: ";
		bC = "Ball's Current Color: ";
		touchPoint = new Vector2();
		grcBounds = new Rectangle((1280 / 2) - ((936 / 2.3f) / 2), 0, 936 / 2.3f, 50);
		backBounds = new Rectangle(0, 0, 78 * 2, 48 * 2);
		Settings.load(game.getFileIO());
		//Log.d("Randoms", Float.toString(ranR) + ", " + Float.toString(ranG) + ", " + Float.toString(ranB) + ", " + Float.toString(ranA));
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
	            
	            if(OverlapTester.pointInRectangle(grcBounds, touchPoint)) {
	            	ranR = ran.nextFloat();
	        		ranG = ran.nextFloat();
	        		ranB = ran.nextFloat();
	        		ranA = ran.nextFloat();
	            	Settings.rgbBall[0] = ranR;
	  		        Settings.rgbBall[1] = ranG;
	  		        Settings.rgbBall[2] = ranB;
	  		        Settings.save(game.getFileIO());
	            	return;
	            }
	            
	            if(OverlapTester.pointInRectangle(backBounds, touchPoint)) {
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
	        gl.glEnable(GL10.GL_BLEND);
	        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
			gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT); // 5
	        gl.glClearColor(bRanR, bRanG, bRanB, 1.0f);
	        batcher.beginBatch(Assets.textItems);
	        gl.glPushMatrix();
	        batcher.drawSprite((384 / 3) / 2, 768 - ((96 / 3) / 2), 384 / 3, 96 / 3, Assets.ballColor);
	        batcher.drawSprite(1280 / 2, 50, 936 / 2.3f, 72 / 2.3f, Assets.generateRandomColor);
	        gl.glPopMatrix();
	        batcher.endBatch();
	        
	        batcher.beginBatch(Assets.gameitems);
	        gl.glPushMatrix();
	        gl.glColor4f(Settings.rgbBall[0], Settings.rgbBall[1], Settings.rgbBall[2], 1.0f);
//	        /Log.d("Red", Float.toString(Settings.rgbPaddle1[0]));
	        batcher.drawSprite(1280 / 2, 768 / 2, 220, 220, Assets.ball);
	        gl.glPopMatrix();
	        batcher.endBatch();
	        
	        batcher.beginBatch(Assets.key);
	        gl.glPushMatrix();
	        gl.glColor4f(1, 1, 1, 1);
	        Assets.font.drawText(batcher, bgC, 1280 - (16 * (bgC.length() - 1)), 768 - 20);
	        //Red RGB Background
	        String bRanRP = Integer.toString(Math.round(bRanR * 100));
	        String bRanRFRGB = Integer.toString(Math.round(bRanR * 256));
	        Assets.font.drawText(batcher,  "R: " + bRanRP + "%" + "(" + bRanRFRGB + ")", 1280 - /*((bRanRP.length() + bRanRFRGB.length() + 6) * 16)*/ 192, 748 - 40);
	        
	        //Green RGB Backgorund
	        String bRanGP = Integer.toString(Math.round(bRanG * 100));
	        String bRanGFRGB = Integer.toString(Math.round(bRanG * 256));
	        Assets.font.drawText(batcher,  "G: " + bRanGP + "%" + "(" + bRanGFRGB + ")", 1280 - /*((bRanGP.length() + bRanGFRGB.length() + 6) * 16)*/ 192, 708 - 20);
	        
	        //Blue RGB Backgorund
	        String bRanBP = Integer.toString(Math.round(bRanB * 100));
	        String bRanBFRGB = Integer.toString(Math.round(bRanB * 256));
	        Assets.font.drawText(batcher,  "B: " + bRanBP + "%" + "(" + bRanBFRGB + ")", 1280 - /*((bRanBP.length() + bRanBFRGB.length() + 6) * 16)*/ 192, 688 - 20);
	        
	        Assets.font.drawText(batcher, bC, 1280 - (16 * (bC.length() - 1)), 600);
	        batcher.endBatch();

	        batcher.beginBatch(Assets.key);
	        //Red RGB Paddle
	        String ranRgbR = Integer.toString(Math.round(Settings.rgbBall[0] * 100));
	        String ranBRRGB = Integer.toString(Math.round(Settings.rgbBall[0] * 256));
	        Assets.font.drawText(batcher,  "R: " + ranRgbR + "%" + "(" + ranBRRGB + ")", 1280 - /*((ranRgbR.length() + ranBRRGB.length() + 6) * 16)*/ 192, 600 - 40);

	        gl.glColor4f(1, 1, 1, 1);
	        //Green RGB Paddle
	        String ranRgbG = Integer.toString(Math.round(Settings.rgbBall[1] * 100));
	        String ranBGRGB = Integer.toString(Math.round(Settings.rgbBall[1] * 256));
	       // Log.d("Test", ranRgbG + " " + ranBGRGB);
	        Assets.font.drawText(batcher,  "G: " + ranRgbG + "%" + "(" + ranBGRGB + ")", 1280 - /*((ranRgbG.length() + ranBGRGB.length() + 6) * 16)*/ 192, 540);
	        
	        //Blue RGB Paddle
	        String ranRgbB = Integer.toString(Math.round(Settings.rgbBall[2] * 100));
	        String ranBBRGB = Integer.toString(Math.round(Settings.rgbBall[2] * 256));
	        Assets.font.drawText(batcher,  "B: " + ranRgbB + "%" + "(" + ranBBRGB + ")", 1280 - /*((ranRgbB.length() + ranBBRGB.length() + 6) * 16)*/ 192, 520);
	        gl.glPopMatrix();
	        batcher.endBatch();
	        
	        batcher.beginBatch(Assets.menu);
	        batcher.drawSprite(45, 36, 90, 36, Assets.back);
	        batcher.endBatch();
	       // Log.d("Saved", "saved");
	                
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

