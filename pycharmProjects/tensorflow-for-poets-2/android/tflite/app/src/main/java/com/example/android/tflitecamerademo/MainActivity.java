package com.example.android.tflitecamerademo;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void pickImage(View view) {
        startActivity(new Intent(this,Gallery.class));
    }

    public void autoRecog(View view) {
        startActivity(new Intent(this,CameraActivity.class));
    }
}
