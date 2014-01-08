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

import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

import com.badlogic.androidgames.framework.gl.Camera2D;
import com.badlogic.androidgames.framework.gl.SpriteBatcher;
import com.badlogic.androidgames.framework.impl.GLGraphics;

public class WorldRender {
	static final float FRUSTUM_WIDTH = 80.0f; //
	static final float FRUSTUM_HEIGHT = 48.0f; // Let goal be 3/7 of the height
												// //Let Paddle be 2/3 of goal
	public static String userpHCGO, cpupHCGO, lwHCGO, rwHCGO, bwHCGO, topwHCGO, scoreUserGO, scoreCPUGO;
	GLGraphics glGraphics;
	World2P world;
	Camera2D cam;
	SpriteBatcher batcher;
	Boolean changerbColor, changerColor, changeqColor;
	Random ran;
	float ranR, ranG, ranB;
	Ball2P ball;

	public WorldRender(GLGraphics glGraphics, SpriteBatcher batcher,
			World2P world) {
		this.glGraphics = glGraphics;
		this.world = world;
		this.cam = new Camera2D(glGraphics, FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
		this.batcher = batcher;
		changerbColor = false;
		changerColor = false;
		changeqColor = false;
		ball = new Ball2P(3, 5, 4, 1);
		ran = new Random();
		ranR = ran.nextFloat();
		ranG = ran.nextFloat();
		ranB = ran.nextFloat();
		
		userpHCGO = "The ball bounced off your paddle: ";
		cpupHCGO = "The ball bounced off the CPU's paddle: ";
		lwHCGO = "The ball bounced off the left wall: ";
		rwHCGO = "The ball bounced off the right wall: ";
		bwHCGO = "The ball bounced off the bottom wall: ";
		topwHCGO = "The ball bounced off the top wall: ";
		scoreUserGO = "You scored ";
		scoreCPUGO = "The computer scored ";
	}

	/*
	 * public void renderPreview() { cam.setViewportAndMatrices();
	 * renderObject(); }
	 */

	public void render() {
		cam.setViewportAndMatrices();
		// renderBackground();
		renderObjects();
	}

	public void renderPaused() {
		cam.setViewportAndMatrices();
		GL10 gl = glGraphics.getGL();
		gl.glEnable(GL10.GL_BLEND);
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		batcher.beginBatch(Assets.key);
		gl.glPushMatrix();
		if (!changerbColor) 
			gl.glColor4f(1, 1, 1, 1);
		else
			gl.glColor4f(ranR, ranB, ranG, 1.0f);
		Assets.font.drawText(batcher, "Reset Ball",
				World2P.WORLD_WIDTH / 2 - 18, 40, 4, 5);
		gl.glPopMatrix();
		batcher.endBatch();
		
		batcher.beginBatch(Assets.key);
		gl.glPushMatrix();
		if (!changerColor)
			gl.glColor4f(1, 1, 1, 1);
		else
			gl.glColor4f(ranR, ranB, ranG, 1.0f);
		Assets.font.drawText(batcher, "Resume", World2P.WORLD_WIDTH / 2 - 12, 30, 4, 5);
		gl.glPopMatrix();
		batcher.endBatch();
		
		batcher.beginBatch(Assets.key);
		gl.glPushMatrix();
		if (!changeqColor)
			gl.glColor4f(1, 1, 1, 1);
		else
			gl.glColor4f(ranR, ranB, ranG, 1.0f);
		Assets.font.drawText(batcher, "Quit", World2P.WORLD_WIDTH / 2 - 8, 20, 4, 5);
		gl.glPopMatrix();
		batcher.endBatch();
	}
	
	public void renderGameOver() {
		cam.setViewportAndMatrices();
		GL10 gl = glGraphics.getGL();
		gl.glEnable(GL10.GL_BLEND);
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		batcher.beginBatch(Assets.key);
		gl.glPushMatrix();
		if(Ball2P.CPU_SCORE_COUNTER == 7) {
			Assets.font.drawText(batcher, "You lose!", (World2P.WORLD_WIDTH / 2) - 18, 40, 4, 5);
			
		} else {
			Assets.font.drawText(batcher, "You win!", (World2P.WORLD_WIDTH / 2) - 16, 40, 4, 5);
		}
		Assets.font.drawText(batcher, "Score:", 2, 2, 2, 2.5f);
		Assets.font.drawText(batcher, "You:" + Integer.toString(Ball2P.USER_SCORE_COUNTER), 17 ,2, 2, 2.5f);
		Assets.font.drawText(batcher, "CPU:" + Integer.toString(Ball2P.CPU_SCORE_COUNTER), 30, 2, 2, 2.5f);
		Assets.font.drawText(batcher, "Tap anywhere to continue", (World2P.WORLD_WIDTH / 2) - 24, World2P.WORLD_HEIGHT / 2, 2, 2.5f);
		gl.glPopMatrix();
		batcher.endBatch();
	}
	
	public void renderStatsGameOver() {
		cam.setViewportAndMatrices();
		GL10 gl = glGraphics.getGL();
		gl.glEnable(GL10.GL_BLEND);
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		batcher.beginBatch(Assets.key);
		gl.glPushMatrix();
		gl.glColor4f(1, 1, 1, 1);
		Assets.font.drawText(batcher, "Game Statistics", 26, 46, 2, 2.5f);
		Assets.font.drawText(batcher, userpHCGO + Integer.toString(Ball2P.USER_PADDLE_HIT_COUNTER) + " times!", 2, 38, 1, 1.25f);
		Assets.font.drawText(batcher, cpupHCGO + Integer.toString(Ball2P.CPU_PADDLE_HIT_COUNTER) + " times!", 2, 35, 1, 1.25f);
		Assets.font.drawText(batcher, lwHCGO + Integer.toString(Ball2P.LEFT_WALL_HIT_COUNTER) + " times!", 2, 32, 1, 1.25f);
		Assets.font.drawText(batcher, rwHCGO+ Integer.toString(Ball2P.RIGHT_WALL_HIT_COUNTER) + " times!", 2, 29, 1, 1.25f);
		gl.glPopMatrix();
		batcher.endBatch();
		
		batcher.beginBatch(Assets.key);
		gl.glPushMatrix();
		gl.glColor4f(1, 1, 1, 1);
		Assets.font.drawText(batcher, scoreCPUGO + Integer.toString(Ball2P.CPU_SCORE_COUNTER) + " points!", 2, 26, 1, 1.25f);
		Assets.font.drawText(batcher, scoreUserGO +Integer.toString(Ball2P.USER_SCORE_COUNTER) + " points!", 2, 23, 1, 1.25f);
		Assets.font.drawText(batcher, topwHCGO+ Integer.toString(Ball2P.UPPER_WALL_HIT_COUNTER) + " times!", 2, 20, 1, 1.25f);
		Assets.font.drawText(batcher, bwHCGO + Integer.toString(Ball2P.LOWER_WALL_HIT_COUNTER) + " times!", 2, 17, 1, 1.25f);
		gl.glPopMatrix();
		batcher.endBatch();
	}

	/*
	 * public void renderBackground() { batcher.beginBatch(Assets.background);
	 * batcher.drawSprite(cam.position.x, cam.position.y, FRUSTUM_WIDTH,
	 * FRUSTUM_HEIGHT, Assets.backgroundRegion); batcher.endBatch(); }
	 */

	public void renderObjects() {
		GL10 gl = glGraphics.getGL();
		gl.glEnable(GL10.GL_BLEND);
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

		batcher.beginBatch(Assets.gameitems);
		gl.glPushMatrix();
		gl.glColor4f(0.3f, 0.3f, 0.6f, 0.2f);
		batcher.drawSprite(1, World2P.WORLD_HEIGHT / 2, 2, World2P.GOAL_SIZE,
				Assets.paddle);
		gl.glPopMatrix();
		batcher.endBatch();

		batcher.beginBatch(Assets.gameitems);
		gl.glPushMatrix();
		gl.glColor4f(Settings.rgbBall[0], Settings.rgbBall[1],
				Settings.rgbBall[2], 1.0f);
		batcher.drawSprite(world.ball.position.x, world.ball.position.y,
				Ball2P.BALL_WIDTH, Ball2P.BALL_HEIGHT, Assets.ball);
		gl.glPopMatrix();
		batcher.endBatch();

		batcher.beginBatch(Assets.gameitems);
		gl.glPushMatrix();
		gl.glColor4f(Settings.rgbPaddle1[0], Settings.rgbPaddle1[1],
				Settings.rgbPaddle1[2], 1.0f);
		batcher.drawSprite(world.userPaddle.position.x,
				world.userPaddle.position.y, Paddle2P.PADDLE_WIDTH,
				Paddle2P.PADDLE_HEIGHT, Assets.paddle);
		gl.glPopMatrix();
		batcher.endBatch();

		batcher.beginBatch(Assets.gameitems);
		gl.glPushMatrix();
		gl.glColor4f(Settings.rgbPaddle2[0], Settings.rgbPaddle2[1],
				Settings.rgbPaddle2[2], 1.0f);
		batcher.drawSprite(world.cpuPaddle.position.x,
				world.cpuPaddle.position.y, PaddleAI2P.PADDLE_WIDTH,
				PaddleAI2P.PADDLE_HEIGHT, Assets.paddle);
		gl.glPopMatrix(); // gl.glDisable(GL10.GL_BLEND);
		batcher.endBatch();

		batcher.beginBatch(Assets.gameitems);
		gl.glPushMatrix();
		gl.glColor4f(Settings.rgbPause[0], Settings.rgbPause[1],
				Settings.rgbPause[2], 0.7f);
		batcher.drawSprite(World2P.WORLD_WIDTH - 2, World2P.WORLD_HEIGHT - 2,
				Paddle2P.PADDLE_WIDTH / 2, Paddle2P.PADDLE_HEIGHT / 4,
				Assets.paddle);
		batcher.drawSprite(World2P.WORLD_WIDTH - 4, World2P.WORLD_HEIGHT - 2,
				Paddle2P.PADDLE_WIDTH / 2, Paddle2P.PADDLE_HEIGHT / 4,
				Assets.paddle);
		gl.glPopMatrix();
		batcher.endBatch();

	}

	/*
	 * public void renderObject() { GL10 gl = glGraphics.getGL();
	 * gl.glEnable(GL10.GL_BLEND); gl.glBlendFunc(GL10.GL_SRC_ALPHA,
	 * GL10.GL_ONE_MINUS_SRC_ALPHA);
	 * 
	 * batcher.beginBatch(Assets.items); renderBall(); renderPaddleAi();
	 * renderPaddleAi2(); batcher.endBatch(); gl.glDisable(GL10.GL_BLEND); }
	 */

	/*
	 * renderBall(gl); renderPaddle(gl); gl.glPushMatrix(); gl.glPopMatrix();
	 * renderPaddleAi(gl); gl.glPopMatrix(); gl.glPushMatrix();
	 */

	/*
	 * private void renderPaddleAi2() {
	 * batcher.drawSprite(World.paddleAi2.World2P.x, World.paddleAi2.World2P.y,
	 * 1.5f, 1.5f, Assets.paddle1); }
	 */

}
