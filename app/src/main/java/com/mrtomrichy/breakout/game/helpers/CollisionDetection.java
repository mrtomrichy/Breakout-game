package com.mrtomrichy.breakout.game.helpers;

import android.graphics.Point;

/**
 * Created by tom on 29/09/15.
 */
public class CollisionDetection {

  // Detect if center of ball is inside rectangle
  // http://stackoverflow.com/questions/8721406/how-to-determine-if-a-point-is-inside-a-2d-convex-polygon
  public static boolean isPointInRect(Point[] points, Point ballCenter) {
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

  // Detect if an edge of the rectangle is within the circle
  // http://stackoverflow.com/questions/8721406/how-to-determine-if-a-point-is-inside-a-2d-convex-polygon
  public static boolean isLineInCircle(Point[] points, Point ballCenter, float radius) {
    for (int i = 0; i < points.length; i++) {
      if (distanceFromLineToCircle(points[i], points[(i + 1) % points.length], ballCenter) <= radius) {
        return true;
      }
    }

    return false;
  }

  private static float distanceFromLineToCircle(Point a, Point b, Point circleCenter) {
    Point d = new Point();

    double CF = ((b.x - a.x) * (circleCenter.x - a.x) + (b.y - a.y) * (circleCenter.y - a.y)) / (Math.pow(b.x - a.x, 2) + Math.pow(b.y - a.y, 2));
    d.x = (int) (a.x + (b.x - a.x) * CF);
    d.y = (int) (a.y + (b.y - a.y) * CF);

    float AD = distanceBetween(a, d);
    float BD = distanceBetween(b, d);

    if (AD + BD == distanceBetween(a, b)) {
      return 0f;
    } else {
      if (AD < BD) {
        return distanceBetween(a, circleCenter);
      } else {
        return distanceBetween(b, circleCenter);
      }
    }
  }

  public static float distanceBetween(Point a, Point b) {
    return (float) Math.sqrt(Math.pow(b.x - a.x, 2) + Math.pow(b.y - a.y, 2));
  }
}
