package com.example.eric.wordguessinggame;

import android.app.Activity;
import android.graphics.Point;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class BlockManager extends Common {
    public interface BlockManagerWordMatchedListener {
        void wordMatched();
    }

    private StringBuilder currentWord = new StringBuilder();
    private BlockManagerWordMatchedListener listener;
    private ArrayList<BlockView> listofblocks = new ArrayList<>();

    public void setMainActivity(Activity mainActivity) {
        this.mainActivity = mainActivity;

        ArrayList<BlockView> tempBlocks = new ArrayList<>();
        ConstraintLayout l = (ConstraintLayout) mainActivity.findViewById(R.id.block2);
        for (int i = 0; i < listofblocks.size(); i++) {
            BlockView v2 = listofblocks.get(i);
            BlockView block = createNewBlock((View.OnTouchListener)mainActivity,
                    v2.getLetter(), v2.getX(), v2.getY());

            tempBlocks.add(block);
            l.addView(block);
        }
        listofblocks = tempBlocks;
        draw();
    }

    public void setOnWordMatchedListener(BlockManagerWordMatchedListener listener) {
        this.listener = listener;
    }

    public void draw() {
        ConstraintLayout c = (ConstraintLayout) mainActivity.findViewById(R.id.block2);
        int width = 0;
        for (int i = 0; i < listofblocks.size(); i++) {
            BlockView b = listofblocks.get(i);
            b.setX(width);
            b.setY(0);
            width += 85;
        }
        ViewGroup.LayoutParams l = c.getLayoutParams();
        l.width = width;
        c.setLayoutParams(l);
    }

    public void add(BlockView blockView) {
        listofblocks.add(blockView);

        blockView.setOnTouchListener(null);
        currentWord.append(blockView.getLetter());
        ConstraintLayout c = (ConstraintLayout) mainActivity.findViewById(R.id.block2);
        c.addView(blockView);

        draw();

        String word = wordList.getRandomWord();
        if(currentWord.toString().equals(word)) {
            if(listener != null)
                listener.wordMatched();
        }
    }

    public void removeAllBlocks() {
        listofblocks.clear();
        currentWord = new StringBuilder();
        ConstraintLayout l = (ConstraintLayout) mainActivity.findViewById(R.id.block2);
        for(int i = 0; i < l.getChildCount(); i++) {
            View v = l.getChildAt(i);
            if (v instanceof BlockView) {
                l.removeViewAt(i);
                i = 0;
            }
        }
    }
}
