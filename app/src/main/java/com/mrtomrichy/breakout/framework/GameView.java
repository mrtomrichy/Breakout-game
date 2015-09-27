package com.mrtomrichy.breakout.framework;

import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.mrtomrichy.breakout.MainActivity;

/**
 * Created by tom on 26/09/15.
 */
public class GameView extends SurfaceView implements SurfaceHolder.Callback {

  private final int mScreenWidth;
  private final int mScreenHeight;
  private GameThread mGameThread;
  private float mScreenDensity;
  private MainActivity mActivity;
  private SurfaceHolder mHolder;
  private Game mGame;

  public GameView(Context context, MainActivity activity, float screenDensity, Game game) {
    super(context);

    mGame = game;

    mActivity = activity;
    mScreenDensity = screenDensity;

    mHolder = getHolder();
    mHolder.addCallback(this);

    setFocusable(true);

    Display display = mActivity.getWindowManager().getDefaultDisplay();
    Point outSize = new Point();
    display.getSize(outSize);

    mScreenWidth = outSize.x;
    mScreenHeight = outSize.y;

    Log.d("GameView", "Width: " + mScreenWidth + " - Height: " + mScreenHeight);

    mGameThread = new GameThread(mHolder, context, mScreenWidth, mScreenHeight, mGame);
  }

  @Override
  public void surfaceCreated(SurfaceHolder surfaceHolder) {
    // start the thread here so that we don't busy-wait in run()
    // waiting for the surface to be created

    if (mGameThread.getState() == Thread.State.TERMINATED)
    {
      mGameThread = new GameThread(mHolder, getContext(), mScreenWidth, mScreenHeight, mGame);
      mGameThread.start();
    }
    else
    {
      mGameThread.start();
    }
  }

  @Override
  public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {}

  @Override
  public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
    boolean retry = true;
    mGameThread.end();
    while (retry)
    {
      try
      {
        mGameThread.join();
        retry = false;
      } catch (InterruptedException e)
      {
        Log.e("GameView", e.getMessage());
      }
    }
  }

  @Override
  public boolean onTouchEvent(MotionEvent e) {
    return mGame.handleTouch(e);
  }

  public GameThread getThread() {
    return mGameThread;
  }

}
