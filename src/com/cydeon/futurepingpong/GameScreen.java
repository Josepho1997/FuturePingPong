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

import android.R.color;
import android.util.Log;

import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Input.TouchEvent;
import com.badlogic.androidgames.framework.gl.Camera2D;
import com.badlogic.androidgames.framework.gl.SpriteBatcher;
import com.badlogic.androidgames.framework.impl.GLScreen;
import com.badlogic.androidgames.framework.math.OverlapTester;
import com.badlogic.androidgames.framework.math.Rectangle;
import com.badlogic.androidgames.framework.math.Vector2;

public class GameScreen extends GLScreen {
	Camera2D guiCam;
	Vector2 touchPoint;
	SpriteBatcher batcher;
	World2P world;
	WorldRender renderer;
	Vector2 oldV;
	Vector2 newV;
	Ball2P ball;
	Paddle2P paddle;
	Rectangle pauseBounds, resetBallBounds, resumeBounds, quitBounds;
	public static Boolean screenIsTouched, irbb, irb, iqb;
	static final int GAME_READY = 0;
	static final int GAME_RUNNING = 1;
	static final int GAME_PAUSED = 2;
	static final int GAME_LEVEL_END = 3;
	static final int GAME_OVER = 4;
	int state;
	int userScoreint, cpuScoreInt;

	public GameScreen(Game game) {
		super(game);
		guiCam = new Camera2D(glGraphics, World2P.WORLD_WIDTH,
				World2P.WORLD_HEIGHT);
		touchPoint = new Vector2();
		batcher = new SpriteBatcher(glGraphics, 1000);
		world = new World2P();
		renderer = new WorldRender(glGraphics, batcher, world);
		oldV = new Vector2();
		newV = new Vector2();
		ball = world.ball;
		paddle = world.userPaddle;
		screenIsTouched = false;
		irbb = false;
		irb = false;
		iqb = false;
		pauseBounds = new Rectangle(World2P.WORLD_WIDTH - 4,
				World2P.WORLD_HEIGHT - 5, 4, 4);
		resetBallBounds = new Rectangle(World2P.WORLD_WIDTH / 2 - 18, 35, 40, 10);
		resumeBounds = new Rectangle(World2P.WORLD_WIDTH / 2 - 12, 25, 24, 10);
		quitBounds = new Rectangle(World2P.WORLD_WIDTH / 2 - 8, 15, 16, 10);
		state = GAME_READY;
		userScoreint = Ball2P.USER_SCORE_COUNTER;
		cpuScoreInt = Ball2P.CPU_SCORE_COUNTER;
	}

	public void update(float deltaTime) {
		  switch(state) {
	        case GAME_READY:
	            updateReady();
	            break;
	        case GAME_RUNNING:
	            updateRunning(deltaTime);
	            break;
	        case GAME_PAUSED:
	            updatePaused();
	            break;
	        case GAME_OVER:
	            updateGameOver();
	            break;
	        }
	}
	
	public void updateReady() {
		 if(game.getInput().getTouchEvents().size() > 0) {
	            state = GAME_RUNNING;
	        }
	}
	
	public void updateRunning(float deltaTime) {
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (Ball2P.ballmode == Ball2P.BALL_GOAL_SCORED) {
				if (event.type == TouchEvent.TOUCH_UP) {
					World2P.ball.ballmode = Ball2P.BALL_GAME_START;
				}
			}
			if (event.type == TouchEvent.TOUCH_DOWN
					| event.type == TouchEvent.TOUCH_DRAGGED) {
				screenIsTouched = true;
				touchPoint.set(event.x, event.y);
				guiCam.touchToWorld(touchPoint);
				if (OverlapTester.pointInRectangle(pauseBounds, touchPoint)) {
					Log.d("Pause Bounds", "Pause Bounds");
					state = GAME_PAUSED;
					return;
				}
			}
			if (event.type == TouchEvent.TOUCH_UP) {
				screenIsTouched = false;
			}
			
			if (World2P.state == World2P.WORLD_STATE_GAME_OVER) {
				state = GAME_OVER;
			}

		}
		world.update(deltaTime, touchPoint);
		/*if(userScoreint != Ball2P.USER_SCORE_COUNTER) {
			userScoreint = Ball2P.USER_SCORE_COUNTER;
		}
		if(cpuScoreInt != Ball2P.CPU_SCORE_COUNTER) {
			cpuScoreInt = Ball2P.USER_SCORE_COUNTER;
		}*/
	}
	
	public void updatePaused() {
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_UP) {
				touchPoint.set(event.x, event.y);
				guiCam.touchToWorld(touchPoint);
				if (OverlapTester.pointInRectangle(resetBallBounds, touchPoint)) {
					renderer.changerbColor = false;
					Ball2P.ballmode = Ball2P.BALL_GOAL_SCORED;
					return;
				}
				if (OverlapTester.pointInRectangle(resumeBounds, touchPoint)) {
					renderer.changerColor = false;
					state = GAME_RUNNING;
					return;
				}
				if (OverlapTester.pointInRectangle(quitBounds, touchPoint)) {
					renderer.changeqColor = false;
					World2P.ball.reset();
					game.setScreen(new MainMenuScreen(game));
					return;
				}
			}
			if (event.type == TouchEvent.TOUCH_DOWN | event.type == TouchEvent.TOUCH_DRAGGED) {
				touchPoint.set(event.x, event.y);
				guiCam.touchToWorld(touchPoint);
				if (OverlapTester.pointInRectangle(resetBallBounds, touchPoint)) {
					renderer.changerbColor = true;
					irbb = true;
					return;
				}
				if (irbb && !OverlapTester.pointInRectangle(resetBallBounds, touchPoint)) {
					irbb = false;
					renderer.changerbColor = false;
				}
				if (OverlapTester.pointInRectangle(resumeBounds, touchPoint)) {
					renderer.changerColor = true;
					irb = true;
					//Log.d("Pressed resume", "pressed resume");
					return;
				}
				if (irb && !OverlapTester.pointInRectangle(resumeBounds, touchPoint)) {
					irb = false;
					renderer.changerColor = false;
				}
				if (OverlapTester.pointInRectangle(quitBounds, touchPoint)) {
					renderer.changeqColor = true;
					iqb = true;
					return;
				}
				if (iqb && !OverlapTester.pointInRectangle(quitBounds, touchPoint)) {
					iqb = false;
					renderer.changeqColor = false;
				}
			}
			

		}
	}
	
	public void updateGameOver() {
		
	}

	public void present(float deltaTime) {
		 switch(state) {
	        case GAME_READY:
	            presentReady();
	            break;
	        case GAME_RUNNING:
	            presentRunning(deltaTime);
	            break;
	        case GAME_PAUSED:
	            presentPaused();
	            break;
	        case GAME_OVER:
	            presentGameOver();
	            break;
	        }
	}
	
	public void presentReady() {
		
	}
	
	public void presentPaused() {
		GL10 gl = glGraphics.getGL();
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		gl.glEnable(GL10.GL_TEXTURE_2D);
		
		guiCam.setViewportAndMatrices();
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT); // 5
		gl.glClearColor(Settings.rgbBackground[0], Settings.rgbBackground[1], Settings.rgbBackground[2], 1.0f);
		renderer.renderPaused();
	}
	
	public void presentGameOver() {
		
	}

	public void presentRunning(float deltaTime) {
		GL10 gl = glGraphics.getGL();
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		gl.glEnable(GL10.GL_TEXTURE_2D);
		
		guiCam.setViewportAndMatrices();
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT); // 5
		gl.glClearColor(Settings.rgbBackground[0], Settings.rgbBackground[1], Settings.rgbBackground[2], 1.0f);
		renderer.render();
		if (Ball2P.ballmode == Ball2P.BALL_GOAL_SCORED) {
			batcher.beginBatch(Assets.textItems);
			batcher.drawSprite(World2P.WORLD_WIDTH / 2, 3, 684 / 20, 84 / 20, Assets.touchToResume);
			batcher.endBatch();
		}
		batcher.beginBatch(Assets.key);
		gl.glPushMatrix();
		gl.glColor4f(1, 1, 1, 1);
		Assets.font.drawText(batcher, "You:" + Integer.toString(Ball2P.USER_SCORE_COUNTER), (World2P.WORLD_WIDTH / 2) - 12.5f , World2P.WORLD_HEIGHT - 2.5f, 2, 2.5f);
		Assets.font.drawText(batcher, "CPU:" + Integer.toString(Ball2P.CPU_SCORE_COUNTER), (World2P.WORLD_WIDTH / 2), World2P.WORLD_HEIGHT - 2.5f, 2, 2.5f);
		gl.glPopMatrix();
		batcher.endBatch();
		
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