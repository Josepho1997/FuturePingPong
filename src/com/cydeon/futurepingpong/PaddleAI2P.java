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

import android.util.Log;

import com.badlogic.androidgames.framework.DynamicGameObject;

public class PaddleAI2P extends DynamicGameObject{
	public static float PADDLE_WIDTH = 2.74286f;
	public static float PADDLE_HEIGHT = 13.7143f;
	public static float HALF_PADDLE_HEIGHT = PADDLE_HEIGHT / 2;
	public static float PADDLE_MOVE_SPEED;
	public static boolean dontMove;
	//public static Ball2P ball;
	
	public PaddleAI2P(float x, float y, float width, float height) {
		super(x, y, PADDLE_WIDTH, PADDLE_HEIGHT);
		dontMove = false;
	}
	
	public void update(float deltaTime, float MOVE_SPEED) {
		bounds.lowerLeft.set(position).sub(PADDLE_WIDTH / 2, PADDLE_HEIGHT / 2);
		//Log.d("String", Boolean.toString(dontMove) + Integer.toString(Ball2P.directionmode));
		
		/*Alpha*/
			
			if(position.y + PADDLE_HEIGHT / 2 > World2P.WORLD_HEIGHT)
				position.y = World2P.WORLD_HEIGHT - PADDLE_HEIGHT / 2;
		
			if(position.y - PADDLE_HEIGHT / 2 < 0)
				position.y = 0 + PADDLE_HEIGHT / 2;
		
		/*1*/if(Ball2P.directionmode == Ball2P.BALL_DIRECTION_EAST) {
				dontMove = false;
		/*2*/if(World2P.ball.position.x > World2P.WORLD_HALF_LINE) {
			
		/*3a*/if(World2P.ball.position.y > position.y + 6) {
					velocity.set(0, MOVE_SPEED);
					//May have to check once it gets there and then stop it
				}/*3a*/
		/*3b*/if(World2P.ball.position.y < position.y - 6) {
					velocity.set(0, -MOVE_SPEED);
				}/*3b*/
		/*3c*/if(World2P.ball.position.y < position.y - 6
					  && World2P.ball.position.y > position.y + 6) {
				  velocity.set(0, 0);
				  //dontMove = true;
			  }/*3c*/
			}/*2*/
		//If it's moving towards the AI, but not past the half line...
		}/*1*/
		
		/*1*/if(Ball2P.directionmode == Ball2P.BALL_DIRECTION_WEST) {
			moveToCenter(MOVE_SPEED);
		}/*1*/
		
		position.add(0, velocity.y * deltaTime);
		
		
		/*Alpha*/
	}
	
	public void moveToCenter(float MOVE_SPEED) {
		//Log.d("Moving to Center", "Moving to center");
		if(position.y < World2P.WORLD_CENTER_Y & !dontMove) {
			velocity.set(0, MOVE_SPEED);
			Log.d("Moving up", "Moving up");
		}
		if(position.y > World2P.WORLD_CENTER_Y & !dontMove) {
			velocity.set(0, -MOVE_SPEED);
			Log.d("Moving down", "moving down");
		}
		if(position.y + 1 > World2P.WORLD_CENTER_Y && position.y - 1 < World2P.WORLD_CENTER_Y) {
			velocity.set(0, 0);
			dontMove = true;
		}
	}
	
}