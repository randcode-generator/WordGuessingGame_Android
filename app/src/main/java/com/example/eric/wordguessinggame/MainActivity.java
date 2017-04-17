package com.example.eric.wordguessinggame;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


public class MainActivity extends Activity implements View.OnTouchListener {
    static WordList wordList = new WordList();
    static BlockManager blockManager = new BlockManager();
    static BoardManager boardManager = new BoardManager();
    int offsetRange = 20;
    PointF bucketPosition;

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
        ImageView im = (ImageView) findViewById(R.id.helpButton);
        im.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HelpActivity.class);
                startActivity(intent);
            }
        });

        blockManager.setWordList(wordList);
        boardManager.setWordList(wordList);

        boardManager.setMainActivity(this);
        blockManager.setMainActivity(this);
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

        if (savedInstanceState == null) {
            boardManager.initialize();
        }
    }

    @Override
    public void onWindowFocusChanged (boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        ImageView bucketImage = (ImageView) findViewById(R.id.bucket);

        int[] locations = new int[2];
        bucketImage.getLocationOnScreen(locations);
        float bucketX = locations[0];
        float bucketY = locations[1];

        bucketPosition = new PointF(bucketX, bucketY);
    }

    private void removeAllBlocks() {
        boardManager.removeAllBlocks();
        blockManager.removeAllBlocks();
    }

    private void newWord() {
        removeAllBlocks();
        wordList.setNewWord();
        startOver();
    }

    private void startOver() {
        removeAllBlocks();
        boardManager.initialize();
    }

    float dX, dY;
    public boolean onTouch(View view, MotionEvent event) {
        final int X = (int) event.getRawX();
        final int Y = (int) event.getRawY();

        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                dX = view.getX() - X;
                dY = view.getY() - Y;
                break;
            case MotionEvent.ACTION_MOVE:
                float blockX = X + dX;
                float blockY = Y + dY;

                view.setX(blockX);
                view.setY(blockY);
                if (blockX >= bucketPosition.x - offsetRange &&
                        blockX <= bucketPosition.x + offsetRange &&
                        blockY >= bucketPosition.y - offsetRange &&
                        blockY <= bucketPosition.y + offsetRange) {
                    boardManager.removeBlock(view);
                    blockManager.add((BlockView) view);
                }
                break;
        }
        return true;
    }
}
