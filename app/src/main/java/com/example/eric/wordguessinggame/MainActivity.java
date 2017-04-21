package com.example.eric.wordguessinggame;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.motion.MotionObserver;


public class MainActivity extends Activity {
    WordManager wordManager = new WordManager();
    BlockManager blockManager = new BlockManager(this);
    BoardManager boardManager = new BoardManager(this);
    RectF bucketRect;

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

        blockManager.setWordList(wordManager);
        boardManager.setWordList(wordManager);
        blockManager.setRuntime(boardManager.getRuntime());

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
            boardManager.initializeBoardWithNewWord();
            blockManager.initialize();
        } else {
            boardManager = savedInstanceState.getParcelable("boardManager");
            blockManager = savedInstanceState.getParcelable("blockManager");
            wordManager = savedInstanceState.getParcelable("wordManager");
            boardManager.reinitializeBoard(this);
            blockManager.reinitialize(this);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable("boardManager", boardManager);
        outState.putParcelable("blockManager", blockManager);
        outState.putParcelable("wordManager", wordManager);
    }

    @Override
    public void onWindowFocusChanged (boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        ImageView bucketImage = (ImageView) findViewById(R.id.bucket);

        int[] locations = new int[2];
        bucketImage.getLocationOnScreen(locations);
        float bucketX = locations[0];
        float bucketY = locations[1];

        bucketRect = new RectF(bucketX + 40, bucketY + 40,
                bucketX + bucketImage.getWidth(),
                bucketY + bucketImage.getHeight());
    }

    private void removeAllBlocks() {
        boardManager.removeAllBlocks();
        blockManager.removeAllBlocks();
    }

    private void newWord() {
        removeAllBlocks();
        wordManager.setNewWord();
        startOver();
    }

    private void startOver() {
        removeAllBlocks();
        boardManager.initializeBoardWithNewWord();
    }

    public abstract class BlockObserver<T> extends MotionObserver<T> {
        BlockView block;

        public void setBlock(BlockView block) {
            this.block = block;
        }
    }

    public BlockObserver<PointF> addObserver() {
        return new BlockObserver<PointF>() {
            @Override
            public void next(PointF p) {
                if(bucketRect == null)
                    return;

                boolean didIntersect = RectF.intersects(
                        new RectF(p.x, p.y,
                                p.x + block.getWidth(), p.y + block.getHeight()),
                        bucketRect);
                if (didIntersect) {
                    boardManager.removeBlock(block);
                    blockManager.add(block);
                }
            }
        };
    }
}
