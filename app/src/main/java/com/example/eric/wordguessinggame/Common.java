package com.example.eric.wordguessinggame;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.PointF;

import com.google.android.indefinite.observable.IndefiniteObservable;
import com.google.android.material.motion.MotionRuntime;
import com.google.android.material.motion.interactions.Draggable;

import java.util.ArrayList;

public abstract class Common {
    protected int offsetFromDeviceButtom = 500;
    protected int offsetFromDeviceEdge = 200;
    protected Activity mainActivity;
    protected WordManager wordManager = null;

    ArrayList<IndefiniteObservable.Subscription> subscriptions = new ArrayList<>();
    MotionRuntime runtime = new MotionRuntime();

    BlockView createNewBlock(char letter, float x, float y) {
        BlockView block = new BlockView(mainActivity);
        block.setLetter(letter);
        ActionBar.LayoutParams p = new ActionBar.LayoutParams(75, 75, 1);
        block.setLayoutParams(p);
        block.setX(x);
        block.setY(y);
        Draggable draggable = new Draggable();
        runtime.addInteraction(draggable, block);
        MainActivity.BlockObserver<PointF> r =
                ((MainActivity)mainActivity).addObserver();
        r.setBlock(block);
        subscriptions.add(runtime.get(block).translation().subscribe(r));

        return block;
    }

    abstract void removeAllBlocks();
    void setWordList(WordManager w) {
        wordManager = w;
    }
}
