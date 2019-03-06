package com.ge.music;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.ge.music.base.BaseActivity;
import com.ge.music.media.MVActivity;


public class MainActivity extends BaseActivity {

    private Button button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        button.setOnClickListener((v) -> {
            Intent intent = new Intent(this, MVActivity.class);
            startActivity(intent);
        });
    }


}
