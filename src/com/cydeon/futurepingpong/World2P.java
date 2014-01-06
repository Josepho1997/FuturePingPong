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

import com.badlogic.androidgames.framework.math.Vector2;


public class World2P {
	public static float WORLD_WIDTH = 80.0f;
	public static float WORLD_HEIGHT = 48.0f;
	public static float WORLD_HALF_LINE = WORLD_WIDTH / 2;
	public static float WORLD_CENTER_Y = WORLD_HEIGHT / 2;
	public static float GOAL_SIZE = 20.5713f;
	public static float HALF_GOAL_SIZE = GOAL_SIZE / 2;
	public static float BALL_MOVE_SPEED;
	public static float PADDLE_MOVE_SPEED;
	public static final int WORLD_STATE_RUNNING = 1;
	public static final int WORLD_STATE_GAME_OVER = 2;

	public static int mode;
	public static int state;
	Vector2 touchPos;

	public static Paddle2P userPaddle;
	public static PaddleAI2P cpuPaddle;
	public static Ball2P ball;

	public World2P() {
		userPaddle = new Paddle2P(1.3888f, WORLD_HEIGHT / 2,
				Paddle2P.PADDLE_WIDTH, Paddle2P.PADDLE_HEIGHT);
		cpuPaddle = new PaddleAI2P(WORLD_WIDTH - 1.3888f, WORLD_HEIGHT / 2,
				PaddleAI2P.PADDLE_WIDTH, PaddleAI2P.PADDLE_HEIGHT);
		ball = new Ball2P(WORLD_WIDTH / 2, WORLD_HEIGHT / 2, Ball2P.BALL_WIDTH,
				Ball2P.BALL_HEIGHT);
		BALL_MOVE_SPEED = 8;
		PADDLE_MOVE_SPEED = 50;
	}
	
	public void update(float deltaTime, Vector2 touchPos) {
		updateBall(deltaTime);
		updateUserPaddle(deltaTime, touchPos);
		updateCPUPaddle(deltaTime);
	}
	
	public void updateBall(float deltaTime) {
		ball.update(deltaTime);
	}
	
	public void updateUserPaddle(float deltaTime, Vector2 touchPos) {
		userPaddle.updatePaddle(deltaTime, touchPos);
	}
	
	public void updateCPUPaddle(float deltaTime) {
		cpuPaddle.update(deltaTime, 50);
	}
	
}
