package com.mrtomrichy.breakout.framework;

import android.graphics.Canvas;
import android.view.MotionEvent;

/**
 * Created by tom on 27/09/15.
 */
public interface Game {
  void init(int width, int height);
  void draw(Canvas c);
  void update();
  boolean handleTouch(MotionEvent e);
}
