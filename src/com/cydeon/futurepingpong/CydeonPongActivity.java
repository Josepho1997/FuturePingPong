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

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.util.Log;

import com.badlogic.androidgames.framework.Screen;
import com.badlogic.androidgames.framework.impl.GLGame;

public class CydeonPongActivity extends GLGame {
	boolean firstTimeCreate = true;
	
	@Override
	public Screen getStartScreen() {
        Log.d("MainMenuLoaded", "MainMenu Loaded!!");
		return new MainMenuScreen(this);
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		super.onSurfaceCreated(gl, config);
		if(firstTimeCreate) {
			Settings.load(getFileIO());
	        Log.d("MainMenuLoaded", "MainMenu Loaded!!");
			Assets.load(this);
			firstTimeCreate = false;
		} else {
			Assets.reload();
	        Log.d("MainMenuLoaded", "MainMenu Loaded!!");
		}
	}

}