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
import com.badlogic.androidgames.framework.impl.GLGraphics;

public class WorldRender {
	static final float FRUSTUM_WIDTH = 80.0f; //
	static final float FRUSTUM_HEIGHT = 48.0f; //Let goal be 3/7 of the height //Let Paddle be 2/3 of goal
	GLGraphics glGraphics;
	World2P world;
	Camera2D cam;
	SpriteBatcher batcher;

	public WorldRender(GLGraphics glGraphics, SpriteBatcher batcher, World2P world) {
		this.glGraphics = glGraphics;
		this.world = world;
		this.cam = new Camera2D(glGraphics, FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
		this.batcher = batcher;

	}
	
	/*public void renderPreview() {
		cam.setViewportAndMatrices();
		renderObject();
	}*/

	public void render() {
		cam.setViewportAndMatrices();
		//renderBackground();
		renderObjects();
	}

	/*public void renderBackground() {
		batcher.beginBatch(Assets.background);
		batcher.drawSprite(cam.position.x, cam.position.y, FRUSTUM_WIDTH,
				FRUSTUM_HEIGHT, Assets.backgroundRegion);
		batcher.endBatch();
	}*/

	public void renderObjects() {
		GL10 gl = glGraphics.getGL();
		gl.glEnable(GL10.GL_BLEND);
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

		batcher.beginBatch(Assets.gameitems);
		gl.glPushMatrix();
		gl.glColor4f(0.3f, 0.3f, 0.6f, 0.2f);
		batcher.drawSprite(1, World2P.WORLD_HEIGHT / 2, 2, World2P.GOAL_SIZE, Assets.paddle);
		gl.glPopMatrix();
		batcher.endBatch();
		
		batcher.beginBatch(Assets.gameitems);
			gl.glPushMatrix();
			gl.glColor4f(Settings.rgbBall[0], Settings.rgbBall[1], Settings.rgbBall[2], 1.0f);
			batcher.drawSprite(world.ball.position.x, world.ball.position.y,
					Ball2P.BALL_WIDTH, Ball2P.BALL_HEIGHT, Assets.ball);
			gl.glPopMatrix();
			batcher.endBatch();

			batcher.beginBatch(Assets.gameitems);
			gl.glPushMatrix();
			gl.glColor4f(Settings.rgbPaddle1[0], Settings.rgbPaddle1[1], Settings.rgbPaddle1[2], 1.0f);
			batcher.drawSprite(world.userPaddle.position.x, world.userPaddle.position.y, 
					Paddle2P.PADDLE_WIDTH, Paddle2P.PADDLE_HEIGHT, Assets.paddle);
			gl.glPopMatrix();
			batcher.endBatch();
		
			batcher.beginBatch(Assets.gameitems);
			gl.glPushMatrix();
			gl.glColor4f(Settings.rgbPaddle2[0], Settings.rgbPaddle2[1], Settings.rgbPaddle2[2], 1.0f);
			batcher.drawSprite(world.cpuPaddle.position.x, world.cpuPaddle.position.y,
					PaddleAI2P.PADDLE_WIDTH, PaddleAI2P.PADDLE_HEIGHT, Assets.paddle);
			gl.glPopMatrix();		//gl.glDisable(GL10.GL_BLEND);
			batcher.endBatch();
			
			batcher.beginBatch(Assets.gameitems);
			gl.glPushMatrix();
			gl.glColor4f(Settings.rgbPause[0], Settings.rgbPause[1], Settings.rgbPause[2], 0.7f);
			batcher.drawSprite(World2P.WORLD_WIDTH - 2, World2P.WORLD_HEIGHT - 2, Paddle2P.PADDLE_WIDTH / 2, Paddle2P.PADDLE_HEIGHT / 4, Assets.paddle);
			batcher.drawSprite(World2P.WORLD_WIDTH - 4, World2P.WORLD_HEIGHT - 2, Paddle2P.PADDLE_WIDTH / 2, Paddle2P.PADDLE_HEIGHT / 4, Assets.paddle);
			gl.glPopMatrix();
			batcher.endBatch();
			
			
	}
	
	/*public void renderObject() {
		GL10 gl = glGraphics.getGL();
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

		batcher.beginBatch(Assets.items);
		renderBall();
		renderPaddleAi();
		renderPaddleAi2();
		batcher.endBatch();
		gl.glDisable(GL10.GL_BLEND);
	}*/
	
	/*renderBall(gl);
	renderPaddle(gl);
	gl.glPushMatrix();
	gl.glPopMatrix();
	renderPaddleAi(gl);
	gl.glPopMatrix();
	gl.glPushMatrix();*/

	
	
	/*private void renderPaddleAi2() {
		batcher.drawSprite(World.paddleAi2.World2P.x, World.paddleAi2.World2P.y,
				1.5f, 1.5f, Assets.paddle1);
	}*/



}