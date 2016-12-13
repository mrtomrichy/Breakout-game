package com.mrtomrichy.breakout.game.sprites;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;

import com.mrtomrichy.breakout.game.helpers.CollisionDetection;
import com.mrtomrichy.breakout.game.helpers.Vector;

import java.util.ArrayList;

/**
 * Created by tom on 27/09/15.
 */
public class Ball implements Sprite {
  private float x;
  private float y;
  private float dx;
  private float dy;

  private float radius;

  private ArrayList<Vector> pendingCollisions;

  private static final int color = Color.WHITE;
  private static Paint paint;

  public Ball(float x, float y, float dx, float dy, float radius) {
    this.x = x;
    this.y = y;
    this.radius = radius;

    this.dx = dx;
    this.dy = dy;

    paint = new Paint();
    paint.setColor(this.color);

    this.pendingCollisions = new ArrayList<>();
  }

  public void move(double modifier) {
    applyCollisions();

    x += dx*modifier;
    y += dy*modifier;
  }

  public void applyCollisions() {
    if(pendingCollisions.size() == 0) return;

    Log.d("COLLISIONS", pendingCollisions.size() + " collisions");

    Vector normal = new Vector(0,0);

    for(int i = 0; i < pendingCollisions.size(); i++) {
      normal.x += pendingCollisions.get(i).x;
      normal.y += pendingCollisions.get(i).y;
    }

    normal.x /= pendingCollisions.size();
    normal.y /= pendingCollisions.size();

    pendingCollisions.clear();

    float newDx = dx - ((2*normal.x) * ((dx*normal.x) + (dy * normal.y)));
    float newDy = dy - ((2*normal.y) * ((dx*normal.x) + (dy * normal.y)));

    dx = newDx;
    dy = newDy;
  }

  public void checkBounds(float minX, float maxX, float minY, float maxY) {
    if(x+radius >= maxX || x-radius <= minX) {
      dx = -dx;
    }

    if(y+radius >= maxY || y-radius <= minY) {
      dy = -dy;
    }
  }

  @Override
  public float getX() {
    return x;
  }

  @Override
  public float getY() {
    return y;
  }

  public float getRadius() {
    return radius;
  }

  @Override
  public void draw(Canvas c) {
    c.drawCircle(x, y, radius, paint);
  }

  public boolean detectCollision(Brick b) {
    Point ballCenter = new Point((int) getX(), (int) getY());
    Point[] brickPoints = b.getPoints();

    Vector normal = CollisionDetection.isLineInCircle(brickPoints, ballCenter, getRadius());

    if(normal != null) {
      pendingCollisions.add(normal);
      Log.d("NORMAL", "dx:"+normal.x+" dy:"+normal.y);

      return true;
    }

    return false;
  }
}
