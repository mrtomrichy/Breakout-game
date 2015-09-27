package com.mrtomrichy.breakout.framework;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Created by tom on 26/09/15.
 */
public class GameThread extends Thread {
  public enum State {
    RUNNING,
    PAUSED
  }

  private State mCurrentState;
  private boolean mRunning = false;
  private Context mContext;
  private SurfaceHolder mHolder;
  private Game mGame;
  private int mWidth;
  private int mHeight;

  public GameThread(SurfaceHolder holder, Context context, int width, int height, Game game) {
    mHolder = holder;
    mContext = context;

    mWidth = width;
    mHeight = height;

    mGame = game;
  }

  @Override
  public void start() {
    mRunning = true;
    mCurrentState = State.RUNNING;

    mGame.init(mWidth, mHeight);

    super.start();
  }

  public void end() {
    mRunning = false;
  }

  public boolean isRunning() {
    return mRunning;
  }

  public void setState(State state) {
    mCurrentState = state;
  }

  @Override
  public void run() {
    super.run();

    while (mRunning) {

      Canvas c = null;
      try {
        c = mHolder.lockCanvas(null);
        synchronized (mHolder) {
          if (mCurrentState == State.RUNNING) {
            update();
          }
          draw(c);
        }
      } finally {
        if (c != null) {
          mHolder.unlockCanvasAndPost(c);
        }
      }

    }
  }

  private void update() {
    mGame.update();
  }

  private void draw(Canvas c) {
    if (c != null) {
      mGame.draw(c);
    }
  }
}
