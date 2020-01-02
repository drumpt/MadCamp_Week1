package com.example.recyclerview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageActivity extends AppCompatActivity {
    private TextView imgTitle;
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        imgTitle = (TextView)findViewById(R.id.img_title);
        img = (ImageView)findViewById(R.id.img_thumbnail);

        // Receive Data
        Intent intent = getIntent();
        String title = intent.getExtras().getString("Title");
        String image = intent.getExtras().getString("Thumbnail");

        // Setting values
        imgTitle.setText(title);
        img.setImageURI(Uri.parse(image));
    }
}
