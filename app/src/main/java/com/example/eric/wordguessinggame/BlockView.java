package com.example.eric.wordguessinggame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class BlockView extends View {
    public BlockView(Context context) {
        super(context);
    }

    public BlockView(Context context, AttributeSet attr) {
        super(context, attr);
    }

    public BlockView(Context context, AttributeSet attr, int defaultStyles) {
        super(context, attr, defaultStyles);
    }

    private char letter = 'A';
    public void setLetter(char letter) {
        this.letter = letter;
    }

    public char getLetter() {
        return letter;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Bitmap fingerprint = BitmapFactory.decodeResource(getResources(), R.drawable.wood);
        canvas.drawBitmap(fingerprint, null, new Rect(0, 0, 75, 75), null);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        paint.setTextSize(75);
        canvas.drawText(new Character(letter).toString(), 11, 65, paint);
    }
}