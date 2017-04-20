package com.example.eric.wordguessinggame;

import android.app.ActionBar;
import android.app.Activity;
import android.view.View;

public abstract class Common {
    protected int offsetFromDeviceButtom = 500;
    protected int offsetFromDeviceEdge = 200;
    protected Activity mainActivity;
    protected WordManager wordManager = null;

    BlockView createNewBlock(View.OnTouchListener l, char letter, float x, float y) {
        BlockView block = new BlockView(mainActivity);
        block.setLetter(letter);
        ActionBar.LayoutParams p = new ActionBar.LayoutParams(75, 75, 1);
        block.setLayoutParams(p);
        block.setOnTouchListener(l);
        block.setX(x);
        block.setY(y);
        return block;
    }

    abstract void removeAllBlocks();
    void setWordList(WordManager w) {
        wordManager = w;
    }
}
