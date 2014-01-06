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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.util.Log;

import com.badlogic.androidgames.framework.FileIO;

public class Settings {
	public static boolean soundEnabled = true;
	public static float[] rgbPaddle1 = {1.0f, 1.0f, 1.0f, 1.0f};
	public static float[] rgbPaddle2 = {1.0f, 1.0f, 1.0f, 1.0f};
	public static float[] rgbBall = {1.0f, 1.0f, 1.0f, 1.0f};
	public static float[] rgbBackground = {1.0f, 1.0f, 1.0f, 1.0f};
	public static float[] rgbPause = {1.0f, 1.0f, 1.0f, 1.0f};

	public final static String file = ".fpp";

	public static void load(FileIO files) {
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(files.readFile(file)));
			for(int i = 0; i < rgbPaddle1.length; i++) {
				rgbPaddle1[i] = Float.parseFloat(in.readLine());
			}
			for(int x = 0; x < rgbPaddle2.length; x++) {
				rgbPaddle2[x] = Float.parseFloat(in.readLine());
			}
			for(int a = 0; a < rgbBall.length; a++) {
				rgbBall[a] = Float.parseFloat(in.readLine());
			}
			for(int b = 0; b < rgbBackground.length; b++) {
				rgbBackground[b] = Float.parseFloat(in.readLine());
			}
			for(int c = 0; c < rgbPause.length; c++) {
				rgbPause[c] = Float.parseFloat(in.readLine());
			}
			Log.d("Log", Float.toString(rgbPaddle1[0]));
			
		} catch (IOException e) {
			// :( It's ok we have defaults
		} catch (NumberFormatException e) {
			// :/ It's ok, defaults save our day
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (IOException e) {
			}
		}
	}

	public static void save(FileIO files) {
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(
					files.writeFile(file)));
			Log.d("Saving...", "saving");
			for(int i = 0; i < rgbPaddle1.length; i++) {
				out.write(Float.toString(rgbPaddle1[i]));
				out.write("\n");
			}
			for(int x = 0; x < rgbPaddle2.length; x++) {
				out.write(Float.toString(rgbPaddle2[x]));
				out.write("\n");
			}
			for(int a = 0; a < rgbBall.length; a++) {
				out.write(Float.toString(rgbBall[a]));
				out.write("\n");
			}
			for(int b = 0; b < rgbBackground.length; b++) {
				out.write(Float.toString(rgbBackground[b]));
				out.write("\n");
			}
			for(int c = 0; c < rgbPause.length; c++) {
				out.write(Float.toString(rgbPause[c]));
				out.write("\n");
			}

		} catch (IOException e) {
			
		} finally {
			try {
				if (out != null)
					out.close();
			} catch (IOException e) {
			}
		}
	}
}