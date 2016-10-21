package com.example.eric.wordguessinggame;

import android.app.Activity;
import android.graphics.Point;
import android.view.Display;

public class System {
    static public Point getDisplaySize(Activity mainActivity) {
        Display display = mainActivity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size;
    }
}
