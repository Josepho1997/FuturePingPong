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

import android.util.Log;

import com.badlogic.androidgames.framework.DynamicGameObject;
import com.badlogic.androidgames.framework.math.Circle;
import com.badlogic.androidgames.framework.math.OverlapTester;
import com.badlogic.androidgames.framework.math.Vector2;

public class Ball2P extends DynamicGameObject {
	public static float BALL_HEIGHT = 2.75f;
	public static float BALL_WIDTH = 2.75f;
	public static float HALF_BALL_MEASUREMENT = BALL_WIDTH / 2;
	public static float BALL_MIN_VELOCITY_ = 0.1f;
	public static float BALL_MAX_VELOCITY = 8f;
	public static final int BALL_ROAMING = 1;
	public static final int BALL_HIT_UPPER_WALL = 2;
	public static final int BALL_HIT_LOWER_WALL = 3;
	public static final int BALL_HIT_LEFT_WALL = 4;
	public static final int BALL_WENT_IN_USER_GOAL = 5;
	public static final int BALL_HIT_RIGHT_WALL = 6;
	public static final int BALL_WENT_IN_CPU_GOAL = 7;
	public static final int BALL_HIT_PADDLE_USER = 8;
	public static final int BALL_HIT_PADDLE_CPU = 9;
	public static final int BALL_GAME_START = 10;
	public static final int BALL_GOAL_SCORED = 11;
	public static final int BALL_PHYSIC_MODE_NULLED = 12;
	public static final int BALL_DIRECTION_EAST = 13;
	public static final int BALL_DIRECTION_WEST = 14;
	
	public static int ballmode;
	public static int physicmode;
	public static int directionmode;
	public static float angle;
	public static float eastTime;
	public static float westTime;
	public static Circle bbCircle;
	public static Vector2 origin;
	public static float ransplx;
	public static float ranslpy;
	public static int intmult;
	public static int LEFT_WALL_HIT_COUNTER = 0;
	public static int RIGHT_WALL_HIT_COUNTER = 0;
	public static int UPPER_WALL_HIT_COUNTER = 0;
	public static int LOWER_WALL_HIT_COUNTER = 0;
	public static int USER_SCORE_COUNTER = 0;
	public static int CPU_SCORE_COUNTER = 0;
	public static int USER_PADDLE_HIT_COUNTER = 0;
	public static int CPU_PADDLE_HIT_COUNTER = 0;
	Random random = new Random();
	Random random2 = new Random();
	Paddle2P userPaddle;
	PaddleAI2P cpuPaddle;

	
	public Ball2P(float x, float y, float width, float height) {
		super(x, y, BALL_WIDTH, BALL_HEIGHT);
		bbCircle = new Circle(position.x, position.y, HALF_BALL_MEASUREMENT);
		ballmode = BALL_GAME_START;
		userPaddle = World2P.userPaddle;
		cpuPaddle = World2P.cpuPaddle;
		origin = new Vector2();
		velocity.set(0, 0);
	}
	
	public void update(float deltaTime) {
		bbCircle.center.set(position.x, position.y);
		if(position.y + HALF_BALL_MEASUREMENT > World2P.WORLD_HEIGHT) {
			velocity.set(velocity.x, -velocity.y);
			UPPER_WALL_HIT_COUNTER++;
		}
		if(position.y - HALF_BALL_MEASUREMENT < 0) {
			velocity.set(velocity.x, -velocity.y);
			LOWER_WALL_HIT_COUNTER++;
		}
		if(position.x + HALF_BALL_MEASUREMENT > World2P.WORLD_WIDTH &&
			position.y - HALF_BALL_MEASUREMENT < (World2P.WORLD_HEIGHT / 2) - World2P.HALF_GOAL_SIZE ||
			position.x + HALF_BALL_MEASUREMENT > World2P.WORLD_WIDTH && position.y + HALF_BALL_MEASUREMENT > (World2P.WORLD_HEIGHT / 2) + World2P.HALF_GOAL_SIZE) {
				velocity.set(-velocity.x, velocity.y);
				RIGHT_WALL_HIT_COUNTER++;
		}
		if(position.x - HALF_BALL_MEASUREMENT < 0 &&
				position.y - HALF_BALL_MEASUREMENT < (World2P.WORLD_HEIGHT / 2) - World2P.HALF_GOAL_SIZE ||
				position.x - HALF_BALL_MEASUREMENT < 0 && position.y + HALF_BALL_MEASUREMENT > (World2P.WORLD_HEIGHT / 2) + World2P.HALF_GOAL_SIZE) {
			velocity.set(-velocity.x, velocity.y);
			LEFT_WALL_HIT_COUNTER++;
		}
		
		if(position.x + HALF_BALL_MEASUREMENT > World2P.WORLD_WIDTH &&
				position.y - HALF_BALL_MEASUREMENT > (World2P.WORLD_HEIGHT / 2) - World2P.HALF_GOAL_SIZE &&
				 position.y + HALF_BALL_MEASUREMENT < (World2P.WORLD_HEIGHT / 2) + World2P.HALF_GOAL_SIZE) {
					ballmode = BALL_WENT_IN_CPU_GOAL;
					USER_SCORE_COUNTER++;
			}
		if(position.x - HALF_BALL_MEASUREMENT < 0 &&
				position.y - HALF_BALL_MEASUREMENT > (World2P.WORLD_HEIGHT / 2) - World2P.HALF_GOAL_SIZE &&
				 position.y + HALF_BALL_MEASUREMENT < (World2P.WORLD_HEIGHT / 2) + World2P.HALF_GOAL_SIZE) {
					ballmode = BALL_WENT_IN_USER_GOAL;
					CPU_SCORE_COUNTER++;
			}
		
		if(OverlapTester.overlapCircleRectangle(bbCircle, userPaddle.bounds)) {
			physicmode = BALL_HIT_PADDLE_USER;
			USER_PADDLE_HIT_COUNTER++;
		}
		if(OverlapTester.overlapCircleRectangle(bbCircle, cpuPaddle.bounds)) {
			physicmode = BALL_HIT_PADDLE_CPU;
			CPU_PADDLE_HIT_COUNTER++;
		}
		if(velocity.x < 0) {
			directionmode = BALL_DIRECTION_WEST;
		}
		if(velocity.x > 0) {
			directionmode = BALL_DIRECTION_EAST;
		}
		if(ballmode == BALL_GAME_START) {
			ransplx = random.nextFloat();
			ranslpy = random.nextFloat();
			velocity.set(ransplx > 0.5f? ransplx * 50.0f:-ransplx * 50.0f, ranslpy < 0.5f? ranslpy * 50.0f:-ranslpy * 50.0f);
			position.add(velocity.x * deltaTime, velocity.y * deltaTime);
			if(velocity.x != 0 && velocity.y != 0)
			ballmode = BALL_ROAMING;
		}
		if(ballmode == BALL_ROAMING) {
			position.add(velocity.x * deltaTime, velocity.y * deltaTime);
		}
		if(physicmode == BALL_HIT_UPPER_WALL || physicmode == BALL_HIT_LOWER_WALL
				|| physicmode == BALL_HIT_LEFT_WALL || physicmode == BALL_HIT_RIGHT_WALL) {
			if(physicmode != 12)
			velocity.set(-velocity.x, -velocity.y);
			nullifyPhysics();
		}
		if(ballmode == BALL_WENT_IN_USER_GOAL || ballmode == BALL_WENT_IN_CPU_GOAL) {
			ballmode = BALL_GOAL_SCORED;
		}
		if(ballmode == BALL_GOAL_SCORED) {
			stopBall();
			position.set(World2P.WORLD_WIDTH / 2, World2P.WORLD_HEIGHT / 2);
			//Check in gamescreen if this mode is active. If so, display
			//message letting the user know to touch the screen to continue.
			//Then change the mode to BALL_GAME_START.
		}
		if(physicmode == BALL_HIT_PADDLE_CPU) {
			origin.set(position);
			float mag = (float) (0.9 * Math.hypot(position.x, position.y));
			angle = position.sub(cpuPaddle.position).angle();
			position.set(origin);
			float radians = angle * Vector2.TO_RADIANS;
			//Log.d("CPU Mag", Float.toString(mag));

			
			velocity.x = (float) (Math.cos(radians)) * mag;
			velocity.y = (float) (Math.sin(radians)) * mag;
			nullifyPhysics();
			//Log.d("CPU Paddle", Float.toString(velocity.x) + ", " + Float.toString(velocity.y));
		}
		if(physicmode == BALL_HIT_PADDLE_USER) {
			origin.set(position);
			float mag = (float) (3.5 * Math.hypot(position.x, position.y));
			angle = position.sub(userPaddle.position).angle();
			position.set(origin);
			float radians = angle * Vector2.TO_RADIANS;
			//Log.d("User Mag", Float.toString(mag));
			
			velocity.x = (float) (Math.cos(radians)) * mag;
			velocity.y = (float) (Math.sin(radians)) * mag;
			if(velocity.x > 100 && velocity.x < 130| velocity.y > 100 && velocity.y < 130) {
				velocity.x -= 40;
				velocity.y -= 40;
			}
			if(velocity.x > 130 | velocity.y > 130) {
				velocity.x -=70;
				velocity.y -= 70;
			}
			nullifyPhysics();
		//	Log.d("User Paddle", Float.toString(velocity.x) + ", " + Float.toString(velocity.y));
		}
		
		if(CPU_SCORE_COUNTER == 7|| USER_SCORE_COUNTER == 7) {
			World2P.state = World2P.WORLD_STATE_GAME_OVER;
		}
		//Log.d("Sure", Integer.toString(Ball2P.directionmode));
		
		//Log.d("Modes", "Physic Mode: " + Integer.toString(physicmode) + " Ball Mode: " + Integer.toString(ballmode) + " Direction Mode: " + Integer.toString(directionmode));
		
		
	}
	
	public void stopBall() {
		velocity.set(0, 0);
	}
	
	
	public void nullifyPhysics() {
		physicmode = BALL_PHYSIC_MODE_NULLED;
		//Physics nullified *puts on sunglasses*
	}
}