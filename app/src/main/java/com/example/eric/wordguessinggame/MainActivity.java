package com.example.eric.wordguessinggame;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.Random;

public class MainActivity extends Activity implements View.OnTouchListener {

    WordList wordList = null;
    BlockManager blockManager = null;
    int offsetRange = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button newWordButton = (Button) findViewById(R.id.newWordButton);
        newWordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeAllBlocks();
                newWord();
            }
        });
        Button startOverButton = (Button) findViewById(R.id.startoverButton);
        startOverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeAllBlocks();
                startOver();
            }
        });
        newWord();
    }

    private void removeAllBlocks() {
        RelativeLayout l = (RelativeLayout) findViewById(R.id.activity_main);
        for (int i = 0; i < l.getChildCount(); i++) {
            View v = l.getChildAt(i);
            if (v instanceof BlockView) {
                l.removeViewAt(i);
                i = 0;
            }
        }
    }

    private void newWord() {
        wordList = new WordList();
        startOver();
    }

    private void startOver() {
        String word = wordList.getRandomWord();
        initialize();
        blockManager = new BlockManager(word, this);
        blockManager.setOnWordMatchedListener(new BlockManager.BlockManagerWordMatchedListener() {
            @Override
            public void wordMatched() {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Word Matched");
                builder.setMessage("You won!");
                builder.setPositiveButton("OK", null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    private void initialize() {
        char[] c = wordList.getRandomWordAsArray();
        for (int i = 0; i < c.length; i++) {
            Random r = new Random();
            int x = r.nextInt(300);
            int y = r.nextInt(400);

            BlockView block = new BlockView(this);
            block.setLetter(c[i]);
            ActionBar.LayoutParams p = new ActionBar.LayoutParams(75, 75, 1);
            block.setLayoutParams(p);
            block.setOnTouchListener(this);
            block.setX(x);
            block.setY(y);
            RelativeLayout l = (RelativeLayout) findViewById(R.id.activity_main);
            l.addView(block);
        }
    }

    public boolean onTouch(View view, MotionEvent event) {
        final int X = (int) event.getRawX();
        final int Y = (int) event.getRawY();

        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_MOVE:
                view.setX(X - 32);
                view.setY(Y - 75);

                ImageView imageView = (ImageView) findViewById(R.id.bucket);
                float bucketX = imageView.getX();
                float bucketY = imageView.getY();

                float blockX = view.getX();
                float blockY = view.getY();

                if (blockX >= bucketX - offsetRange && blockX <= bucketX + offsetRange &&
                        blockY >= bucketY - offsetRange && blockY <= bucketY + offsetRange) {
                    blockManager.add((BlockView) view);
                }
                break;
        }
        return true;
    }
}
