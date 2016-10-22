package com.example.eric.wordguessinggame;

import android.app.Activity;
import android.graphics.Point;

import java.util.ArrayList;

public class BlockManager {
    public interface BlockManagerWordMatchedListener {
        void wordMatched();
    }
    private double startingX = 100;
    private String word = null;
    private StringBuilder currentWord = new StringBuilder();
    private BlockManagerWordMatchedListener listener;
    private ArrayList<BlockView> listofblocks = new ArrayList<>();
    private Activity mainActivity;

    public BlockManager(String word, Activity mainActivity) {
        this.word = word;
        this.mainActivity = mainActivity;
    }

    public void setOnWordMatchedListener(BlockManagerWordMatchedListener listener) {
        this.listener = listener;
    }

    public void draw() {
        Point size = System.getDisplaySize(mainActivity);
        int deviceHeight = size.y;
        int deviceWidth = size.x;
        startingX = (deviceWidth / 2.0) - ((listofblocks.size() / 2.0) * 90.0);
        for (int i = 0; i < listofblocks.size(); i++) {
            BlockView blockView = listofblocks.get(i);
            blockView.setX((float)startingX + (i * 90));
            blockView.setY(deviceHeight - 200);
        }
    }

    public void add(BlockView blockView) {
        listofblocks.add(blockView);
        draw();

        blockView.setOnTouchListener(null);
        currentWord.append(blockView.getLetter());

        if(currentWord.toString().equals(word)) {
            if(listener != null)
                listener.wordMatched();
        }
    }
}
