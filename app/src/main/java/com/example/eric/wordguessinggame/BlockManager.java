package com.example.eric.wordguessinggame;

import android.app.Activity;
import android.graphics.Point;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class BlockManager extends Common implements Parcelable {
    private StringBuilder currentWord = new StringBuilder();
    private BlockManagerWordMatchedListener listener;
    private ArrayList<BlockView> arrayBlocks = new ArrayList<>();
    private ArrayList<Character> arrayBlocksChar = new ArrayList<>();

    public BlockManager(Activity a) {
        this.mainActivity = a;
    }

    protected BlockManager(Parcel in) {
        arrayBlocksChar = new ArrayList<>();
        in.readList(arrayBlocksChar, Character.class.getClassLoader());
    }

    public static final Creator<BlockManager> CREATOR = new Creator<BlockManager>() {
        @Override
        public BlockManager createFromParcel(Parcel in) {
            return new BlockManager(in);
        }

        @Override
        public BlockManager[] newArray(int size) {
            return new BlockManager[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeList(arrayBlocksChar);
    }

    public interface BlockManagerWordMatchedListener {
        void wordMatched();
    }

    public void setOnWordMatchedListener(BlockManagerWordMatchedListener listener) {
        this.listener = listener;
    }

    public void initialize() {
        draw();
    }

    public void reinitialize(Activity a) {
        this.mainActivity = a;
        arrayBlocks.clear();
        convertToBlock();
        initialize();
    }

    public void draw() {
        ConstraintLayout c = (ConstraintLayout) mainActivity.findViewById(R.id.block2);
        int width = 0;
        for (int i = 0; i < arrayBlocks.size(); i++) {
            BlockView b = arrayBlocks.get(i);
            b.setX(width);
            b.setY(0);
            width += 85;
        }
        ViewGroup.LayoutParams l = c.getLayoutParams();
        l.width = width;
        c.setLayoutParams(l);
    }

    public void convertToBlock() {
        ConstraintLayout c = (ConstraintLayout) mainActivity.findViewById(R.id.block2);
        for(int i = 0; i < arrayBlocksChar.size(); i++) {
            BlockView block = createNewBlock(arrayBlocksChar.get(i), 0, 0);
            arrayBlocks.add(block);
            c.addView(block);
        }
    }


    public void add(BlockView blockView) {
        arrayBlocks.add(blockView);
        arrayBlocksChar.add(blockView.getLetter());

        //blockView.setOnTouchListener(null);
        currentWord.append(blockView.getLetter());
        ConstraintLayout c = (ConstraintLayout) mainActivity.findViewById(R.id.block2);
        c.addView(blockView);

        draw();

        String word = wordManager.getRandomWord();
        if(currentWord.toString().equals(word)) {
            if(listener != null)
                listener.wordMatched();
        }
    }

    public void removeAllBlocks() {
        arrayBlocks.clear();
        arrayBlocksChar.clear();
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
