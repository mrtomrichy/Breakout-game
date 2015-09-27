package com.mrtomrichy.breakout.game.sprites;

import android.graphics.Canvas;

/**
 * Created by tom on 27/09/15.
 */
public interface Sprite {
  float getX();
  float getY();
  void draw(Canvas c);
}
