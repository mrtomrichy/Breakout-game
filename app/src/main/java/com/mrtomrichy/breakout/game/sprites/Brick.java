package com.mrtomrichy.breakout.game.sprites;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

/**
 * Created by tom on 26/09/15.
 */
public class Brick implements Sprite {
  private float x;
  private float y;

  private int color = Color.WHITE;
  private float width;
  private float height;

  private Paint paint;

  public Brick(float x, float y, float width, float height) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;

    this.paint = new Paint();
    this.paint.setColor(color);
  }

  public void setColor(int color) {
    this.color = color;
    this.paint.setColor(color);
  }

  @Override
  public float getX() {
    return x;
  }

  @Override
  public float getY() {
    return y;
  }

  public void moveRight() {
    x += 4;
  }

  public void moveLeft() {
    x -= 4;
  }

  public float getWidth() {
    return width;
  }

  @Override
  public void draw(Canvas c) {
    c.drawRect(x, y, x + width, y + height, paint);
  }

  public Point[] getPoints() {
    return new Point[]{new Point((int) x, (int) y),
        new Point((int) (x + width), (int) y),
        new Point((int) x, (int) (y + height)),
        new Point((int) (x + width), (int) (y + height))};
  }

  public boolean ballHit(Ball ball) {
    Point ballCenter = new Point((int) ball.getX(), (int) ball.getY());
    Point[] brickPoints = getPoints();

    return isPointInRect(brickPoints, ballCenter) || isLineInCircle(brickPoints, ballCenter, ball.getRadius());
  }

  // Method for rectangle collision from
  // http://stackoverflow.com/questions/8721406/how-to-determine-if-a-point-is-inside-a-2d-convex-polygon
  private boolean isPointInRect(Point[] points, Point ballCenter) {
    int i;
    int j;
    boolean result = false;
    for (i = 0, j = points.length - 1; i < points.length; j = i++) {
      if ((points[i].y > ballCenter.y) != (points[j].y > ballCenter.y) &&
          (ballCenter.x < (points[j].x - points[i].x) * (ballCenter.y - points[i].y) / (points[j].y - points[i].y) + points[i].x)) {
        result = !result;
      }
    }

    return result;
  }

  private boolean isLineInCircle(Point[] points, Point ballCenter, float radius) {
    for (int i = 0; i < points.length; i++) {
      if (distanceFromLineToCircle(points[i], points[(i + 1) % points.length], ballCenter) <= radius) {
        return true;
      }
    }

    return false;
  }

  private float distanceFromLineToCircle(Point a, Point b, Point circleCenter) {
    Point d = new Point();

    double CF = ((b.x - a.x) * (circleCenter.x - a.x) + (b.y - a.y) * (circleCenter.y - a.y)) / (Math.pow(b.x - a.x, 2) + Math.pow(b.y - a.y, 2));
    d.x = (int) (a.x + (b.x - a.x) * CF);
    d.y = (int) (a.y + (b.y - a.y) * CF);

    if (distanceBetween(a, d) + distanceBetween(d, b) == distanceBetween(a, b)) {
      return 0f;
    } else {
      if (distanceBetween(a, d) < distanceBetween(b, d)) {
        return distanceBetween(a, circleCenter);
      } else {
        return distanceBetween(b, circleCenter);
      }
    }
  }

  private float distanceBetween(Point a, Point b) {
    return (float) Math.sqrt(Math.pow(b.x - a.x, 2) + Math.pow(b.y - a.y, 2));

  }

}
