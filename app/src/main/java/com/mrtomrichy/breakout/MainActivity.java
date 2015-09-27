package com.mrtomrichy.breakout;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;

import com.mrtomrichy.breakout.framework.Game;
import com.mrtomrichy.breakout.framework.GameThread;
import com.mrtomrichy.breakout.framework.GameView;
import com.mrtomrichy.breakout.game.Pong;

public class MainActivity extends Activity {

  private GameView mGameView;

  private DisplayMetrics mMetrics = new DisplayMetrics();
  private float mScreenDensity;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    getWindowManager().getDefaultDisplay().getMetrics(mMetrics);
    mScreenDensity = mMetrics.density;

    Game game = new Pong();

    mGameView = new GameView(getApplicationContext(), this, mScreenDensity, game);

    setContentView(mGameView);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  @Override
  public void onPause() {
    super.onPause();
    mGameView.getThread().setState(GameThread.State.PAUSED);
  }

}
