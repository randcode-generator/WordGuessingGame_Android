package com.example.eric.wordguessinggame;

import android.app.Activity;
import android.graphics.Point;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.constraint.ConstraintLayout;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

public class BoardManager extends Common implements Parcelable {
    ArrayList<BlockView> arrayBlocks = new ArrayList<>();
    ArrayList<Character> arrayBlocksChar = new ArrayList<>();
    char[] c = null;

    public BoardManager(Activity a) {
        this.mainActivity = a;
    }

    protected BoardManager(Parcel in) {
        arrayBlocksChar = new ArrayList<>();
        c = new char[20];
        in.readList(arrayBlocksChar, Character.class.getClassLoader());
        in.readCharArray(c);
    }

    public static final Creator<BoardManager> CREATOR = new Creator<BoardManager>() {
        @Override
        public BoardManager createFromParcel(Parcel in) {
            return new BoardManager(in);
        }

        @Override
        public BoardManager[] newArray(int size) {
            return new BoardManager[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeList(arrayBlocksChar);
        parcel.writeCharArray(c);
    }

    public void initializeBoard() {
        Point size = System.getDisplaySize(mainActivity);
        int deviceWidth = size.x;
        int deviceHeight = size.y;
        for (int i = 0; i < c.length; i++) {
            Random r = new Random();
            int x = r.nextInt(deviceWidth - offsetFromDeviceEdge);
            int y = r.nextInt(deviceHeight - offsetFromDeviceButtom);

            BlockView block = createNewBlock((View.OnTouchListener)mainActivity, c[i], x, y);
            arrayBlocksChar.add(c[i]);

            ConstraintLayout l = (ConstraintLayout) mainActivity.findViewById(R.id.board);
            l.addView(block);
            arrayBlocks.add(block);
        }
    }

    public void reinitializeBoard(Activity a) {
        this.mainActivity = a;
        Point size = System.getDisplaySize(mainActivity);
        int deviceWidth = size.x;
        int deviceHeight = size.y;
        for (int i = 0; i < arrayBlocksChar.size(); i++) {
            Random r = new Random();
            int x = r.nextInt(deviceWidth - offsetFromDeviceEdge);
            int y = r.nextInt(deviceHeight - offsetFromDeviceButtom);

            BlockView block = createNewBlock((View.OnTouchListener)mainActivity,
                    arrayBlocksChar.get(i), x, y);

            ConstraintLayout l = (ConstraintLayout) mainActivity.findViewById(R.id.board);
            l.addView(block);
            arrayBlocks.add(block);
        }
    }

    public void initializeBoardWithNewWord() {
        c = wordManager.getRandomWordAsArray();
        initializeBoard();
    }

    public void removeBlock(BlockView v) {
        arrayBlocks.remove(v);
        arrayBlocksChar.remove((Character) v.getLetter());
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
        arrayBlocksChar.clear();
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
