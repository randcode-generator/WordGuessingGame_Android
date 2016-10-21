package com.example.eric.wordguessinggame;

import android.app.Activity;
import android.widget.ImageView;

import java.util.ArrayList;

public class BlockManager {
    public interface BlockManagerWordMatchedListener {
        void wordMatched();
    }
    private int startingX = 100;
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

    public void add(BlockView blockView) {
        listofblocks.add(blockView);
        ImageView bucket = (ImageView) mainActivity.findViewById(R.id.bucket);
        float bucketY = bucket.getY();
        for(int i = 0; i < listofblocks.size(); i++) {
            blockView.setX(startingX + (i * 90));
            blockView.setY(bucketY + 132);
        }

        blockView.setOnTouchListener(null);
        currentWord.append(blockView.getLetter());

        if(currentWord.toString().equals(word)) {
            if(listener != null)
                listener.wordMatched();
        }
    }
}
