package com.mrtomrichy.breakout.game.sprites;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by tom on 27/09/15.
 */
public class Ball implements Sprite {
  private float x;
  private float y;

  private float radius;

  private static final int color = Color.WHITE;
  private static Paint paint;

  public Ball(float x, float y, float radius) {
    this.x = x;
    this.y = y;
    this.radius = radius;

    paint = new Paint();
    paint.setColor(this.color);
  }

  @Override
  public float getX() {
    return x;
  }

  @Override
  public float getY() {
    return y;
  }

  @Override
  public void draw(Canvas c) {
    c.drawCircle(x, y, radius, paint);
  }
}
