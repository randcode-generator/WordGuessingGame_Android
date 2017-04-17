package com.example.eric.wordguessinggame;

import android.app.Activity;
import android.graphics.Point;
import android.support.constraint.ConstraintLayout;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

public class BoardManager extends Common {
    ArrayList<BlockView> arrayBlocks = new ArrayList<>();

    public void initialize() {
        Point size = System.getDisplaySize(mainActivity);
        int deviceWidth = size.x;
        int deviceHeight = size.y;
        arrayBlocks.clear();
        char[] c = wordList.getRandomWordAsArray();
        for (int i = 0; i < c.length; i++) {
            Random r = new Random();
            int x = r.nextInt(deviceWidth - offsetFromDeviceEdge);
            int y = r.nextInt(deviceHeight - offsetFromDeviceButtom);

            BlockView block = createNewBlock((View.OnTouchListener)mainActivity, c[i], x, y);

            ConstraintLayout l = (ConstraintLayout) mainActivity.findViewById(R.id.board);
            l.addView(block);
            arrayBlocks.add(block);
        }
    }

    public void setMainActivity(Activity mainActivity) {
        this.mainActivity = mainActivity;

        Point size = System.getDisplaySize(mainActivity);
        int deviceWidth = size.x;
        int deviceHeight = size.y;
        ArrayList<BlockView> tempBlocks = new ArrayList<>();
        ConstraintLayout l = (ConstraintLayout) mainActivity.findViewById(R.id.board);
        for (int i = 0; i < arrayBlocks.size(); i++) {
            BlockView v2 = arrayBlocks.get(i);
            Random r = new Random();
            int x = r.nextInt(deviceWidth - offsetFromDeviceEdge);
            int y = r.nextInt(deviceHeight - offsetFromDeviceButtom);
            BlockView block = createNewBlock((View.OnTouchListener)mainActivity,
                    v2.getLetter(), x, y);
            tempBlocks.add(block);
            l.addView(block);
        }
        arrayBlocks = tempBlocks;
    }

    public void removeBlock(View v) {
        arrayBlocks.remove(v);
        ConstraintLayout l = (ConstraintLayout) mainActivity.findViewById(R.id.board);
        for (int i = 0; i < l.getChildCount(); i++) {
            View v2 = l.getChildAt(i);
            if (v2 instanceof BlockView && v == v2) {
                l.removeViewAt(i);
                break;
            }
        }
    }

    public void removeAllBlocks() {
        arrayBlocks.clear();
        ConstraintLayout l = (ConstraintLayout) mainActivity.findViewById(R.id.board);
        for (int i = 0; i < l.getChildCount(); i++) {
            View v = l.getChildAt(i);
            if (v instanceof BlockView) {
                l.removeViewAt(i);
                i = 0;
            }
        }
    }
}
