package com.example.android.tflitecamerademo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Gallery extends Activity {
    final int PICK_IMAGE_REQ = 13290;
    ImageView inputImage;
    TextView result;
    TextView treatment;
    ImageClassifier imageClassifier;
    private Uri filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gellary);

        inputImage = (ImageView) findViewById(R.id.input_image);
        result = (TextView) findViewById(R.id.result);
        treatment =(TextView)findViewById(R.id.treatment);
        try {
            imageClassifier = new ImageClassifier(this);
        } catch (Exception e) {
        }
    }

    public void startClasify(View view) {
        if (filePath != null) {
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                runRecog(bitmap);

            } catch (Exception e) {
            }
        } else {
            Toast.makeText(this, "choose image", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQ && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                inputImage.setImageBitmap(bitmap);
            } catch (Exception e) {
            }
        }

    }

    void runRecog(final Bitmap croppedBitmap) {
        String text = imageClassifier.classifyFrame(Bitmap.createScaledBitmap(croppedBitmap, ImageClassifier.DIM_IMG_SIZE_X, ImageClassifier.DIM_IMG_SIZE_Y, false));
        result.setText(text);
        int r=imageClassifier.printTop();
        switch (r){
            case 1:
                treatment.setText(R.string.bacterial_spot);
                break;
            case 2:
                treatment.setText(R.string.healthy);
                break;
            case 3:
                treatment.setText(R.string.lateblight);
                break;
            case 4:
                treatment.setText(R.string.leaf_mold);
                break;
            case 5:
                treatment.setText(R.string.septoria);
                break;
            case 6:
                treatment.setText(R.string.mosaic);
                break;
            case 7:
                treatment.setText(R.string.yellow);
                break;
            case -1:
                treatment.setText("unknown");
                break;
        }

    }

    public void chooseImage(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQ);
    }
}
