package com.example.eric.wordguessinggame;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.ImageView;

public class HelpActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        ImageView closeView = (ImageView) findViewById(R.id.closeButtonId);
        closeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HelpActivity.this.finish();
            }
        });
    }

}
