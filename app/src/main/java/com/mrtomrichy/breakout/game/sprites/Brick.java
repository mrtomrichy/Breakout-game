package com.mrtomrichy.breakout.game.sprites;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by tom on 26/09/15.
 */
public class Brick implements Sprite{
  private float x;
  private float y;

  private static final int color = Color.WHITE;
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
    c.drawRect(x, y, x+width, y+height, paint);
  }

}
