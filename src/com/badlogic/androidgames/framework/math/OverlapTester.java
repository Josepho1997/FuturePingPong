package com.badlogic.androidgames.framework.math;

public class OverlapTester {

	public static boolean overlapCircles(Circle c1, Circle c2) {
		float distance = c1.center.distSquared(c2.center);
		float radiusSum = c1.radius + c2.radius;
		return distance <= radiusSum * radiusSum;
	}
	
	public static boolean overlapRectangles(Rectangle r1, Rectangle r2) {
		//the left edge of the first rectangle must be to the left of the right edge of the second rectangle
		if(r1.lowerLeft.x < r2.lowerLeft.x + r2.width &&
		//the right edge of the first rectangle must be to the right of the left edge of the second rectangle
		   r1.lowerLeft.x + r1.width > r2.lowerLeft.x &&
		//the bottom edge of the first rectangle must be below the top edge of the second rectangle
		   r1.lowerLeft.y < r2.lowerLeft.y + r2.height &&
		//the top edge of the first rectangle must be above the bottom edge of the second rectangle
		   r1.lowerLeft.y + r1.height > r2.lowerLeft.y)
			return true;
		else
			return false;
	}
	
	public static boolean overlapCircleRectangle(Circle c, Rectangle r) {
		float closestX = c.center.x;
		float closestY = c.center.y;
		
		//If our circle's center x coordinate is less than(to the left)
		//of our lower left x coordinate of our rectangle...
		if(c.center.x < r.lowerLeft.x) {
			closestX = r.lowerLeft.x;
		}
		
		//If our circle's center x coordinate is greater than(to the right)
		//of lower right x coordinate of our rectangle...
		else if(c.center.x > r.lowerLeft.x + r.width) {
			closestX = r.lowerLeft.x + r.width;
		}
		
		//If our circle's center y coordinate is more than(below)
		//our upper left y coordinate of our rectangle...
		if(c.center.y < r.lowerLeft.y) {
			closestY = r.lowerLeft.y;
		}
		
		//If our circle's center y coordinate is less than(above)
		//our lower left y coordinate of our rectangle...
		else if(c.center.y > r.lowerLeft.y + r.height) {
			closestY = r.lowerLeft.y + r.height;
		}
		return c.center.distSquared(closestX, closestY) < c.radius * c.radius;
	}
	
	public static boolean pointInCircle(Circle c, Vector2 p) {
		return c.center.distSquared(p) < c.radius * c.radius;
	}
	
	public static boolean pointInCircle(Circle c, float x, float y) {
		return c.center.distSquared(x, y) < c.radius * c.radius;
	}
	
	public static boolean pointInRectangle(Rectangle r, Vector2 p) {
		return r.lowerLeft.x <= p.x && r.lowerLeft.x + r.width >= p.x &&
			   r.lowerLeft.y <= p.y && r.lowerLeft.y + r.height >= p.y;
	}
	
	public static boolean pointInRectangle(Rectangle r, float x, float y) {
		return r.lowerLeft.x <= x && r.lowerLeft.x + r.width >= x &&
			   r.lowerLeft.y <= y && r.lowerLeft.y + r.height >= y;
	}
	
}
