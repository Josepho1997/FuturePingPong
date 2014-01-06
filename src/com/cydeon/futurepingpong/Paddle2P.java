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
import com.badlogic.androidgames.framework.math.Vector2;

public class Paddle2P extends DynamicGameObject{
	public static float PADDLE_WIDTH = 2.74286f;
	public static float PADDLE_HEIGHT = 13.7143f;
	public static float HALF_PADDLE_HEIGHT = PADDLE_HEIGHT / 2;
	public static float MOVE_SPEED = 50;
	public Vector2 touchPos;
	
	public Paddle2P(float x, float y, float width, float height) {
		super(x, y, PADDLE_WIDTH, PADDLE_HEIGHT);
		touchPos = new Vector2();
	}
	
	public void update(float deltaTime) {
			//updatePaddle(deltaTime);
	}
	
	public void updatePaddle(float deltaTime, Vector2 touchPos) {		
		if(GameScreen.screenIsTouched) {
			bounds.lowerLeft.set(position).sub(PADDLE_WIDTH / 2, PADDLE_HEIGHT / 2);
			this.touchPos = touchPos;

			
			if(touchPos.y > position.y + 0.5f)
				velocity.set(0, MOVE_SPEED);
			else if(touchPos.y < position.y - 0.5f)
				velocity.set(0, -MOVE_SPEED);
			else velocity.set(0, 0);
			
			
			
			position.add(0, velocity.y * deltaTime);
			
			if(position.y + PADDLE_HEIGHT / 2 > World2P.WORLD_HEIGHT)
				position.y = World2P.WORLD_HEIGHT - PADDLE_HEIGHT / 2;
		
			if(position.y - PADDLE_HEIGHT / 2 < 0)
				position.y = 0 + PADDLE_HEIGHT / 2;
			
			
		}
		
	}

}
