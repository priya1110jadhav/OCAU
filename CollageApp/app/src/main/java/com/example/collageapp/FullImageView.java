package com.example.collageapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;

public class FullImageView extends AppCompatActivity {
    private PhotoView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image_view);

        imageView = findViewById(R.id.imageView);

        String imageURL = getIntent().getStringExtra("imageURL");
        String image = getIntent().getStringExtra("image");



        if (imageURL == null) {
            Glide.with(this).load(image).into(imageView);

        }
        else {
            Glide.with(this).load(imageURL).into(imageView);
        }
    }
}