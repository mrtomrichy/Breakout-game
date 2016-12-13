package com.mrtomrichy.breakout.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.mrtomrichy.breakout.framework.Game;
import com.mrtomrichy.breakout.game.sprites.Ball;
import com.mrtomrichy.breakout.game.sprites.Brick;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by tom on 27/09/15.
 */
public class Pong implements Game {
  private enum TouchPosition {
    NONE,
    RIGHT,
    LEFT
  }

  private TouchPosition mCurrentTouchPosition = TouchPosition.NONE;
  private static final int BRICK_COUNT = 10;
  private ArrayList<Brick> mBricks;
  private Brick mPlayerBlock;
  private Ball mBall;

  private Paint mBackgroundPaint;

  private int mWidth = -1;
  private int mHeight = -1;

  public Pong() {
    mBackgroundPaint = new Paint();
    mBackgroundPaint.setColor(Color.BLACK);
  }

  @Override
  public void init(int width, int height) {
    mWidth = width;
    mHeight = height;

    float brickWidth = (float) ((mWidth / BRICK_COUNT) - ((mWidth / BRICK_COUNT) * 0.2));

    mBricks = new ArrayList<>();
    for(int rows = 0; rows < 7; rows++){
      for(int i = 0; i < BRICK_COUNT; i++) {
        float x = (float) (((mWidth / BRICK_COUNT) * i) + ((mWidth / BRICK_COUNT) * 0.1));
        float y = (float) ((mHeight / 20) + ((mHeight/30)*(rows+1)));

        mBricks.add(new Brick(x, y, brickWidth, 10));
      }
    }

    mBall = new Ball((float)(mWidth/2), (float)(mHeight*0.5), 1, 4, brickWidth/5);

    float playerBrickWidth = (float) ((mWidth / BRICK_COUNT) * 1.5);

    mPlayerBlock = new Brick((mWidth/2) - (playerBrickWidth/2), (float)(mHeight*0.9), playerBrickWidth, 15);

  }

  @Override
  public void draw(Canvas c) {
    // Draw background
    int height = c.getHeight();
    int width = c.getWidth();
    c.drawRect(0, 0, width, height, mBackgroundPaint);

    for(Brick brick : mBricks) brick.draw(c);

    mBall.draw(c);

    mPlayerBlock.draw(c);
  }

  @Override
  public void update() {
    movePlayerBlock();

    mBall.move(0.5);
    mBall.checkBounds(0, mWidth, 0, mHeight);

    detectCollisions();

    mBall.move(0.5);
    mBall.checkBounds(0, mWidth, 0, mHeight);

    detectCollisions();
  }

  public void movePlayerBlock() {
    if(mCurrentTouchPosition == TouchPosition.LEFT) {
      if(mPlayerBlock.getX() > 0) mPlayerBlock.moveLeft();
    } else if(mCurrentTouchPosition == TouchPosition.RIGHT) {
      if(mPlayerBlock.getX() < mWidth - mPlayerBlock.getWidth()) mPlayerBlock.moveRight();
    }
  }

  public void detectCollisions() {
    if(mBall.detectCollision(mPlayerBlock)) {
      mPlayerBlock.setColor(Color.RED);
    } else {
      mPlayerBlock.setColor(Color.WHITE);
    }

    Iterator<Brick> it = mBricks.iterator();
    while(it.hasNext()) {
      Brick b = it.next();
      if(mBall.detectCollision(b)) {
        b.setColor(Color.RED);
        it.remove();
      } else {
        b.setColor(Color.WHITE);
      }
    }
  }

  @Override
  public boolean handleTouch(MotionEvent e) {
    // TODO: Fix pointer count bug
    // Says there are 2 when there is only 1 finger on the screen

    if(e.getPointerCount() != 1) {
      mCurrentTouchPosition = TouchPosition.NONE;
      return true;
    }

    if(e.getAction() == MotionEvent.ACTION_UP) {
      mCurrentTouchPosition = TouchPosition.NONE;
    } else {
      float x = e.getX(0);
      if(x < mWidth/2)  mCurrentTouchPosition = TouchPosition.LEFT;
      else              mCurrentTouchPosition = TouchPosition.RIGHT;
    }


    return true;
  }

}
