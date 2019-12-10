package com.example.newsappjava;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;


public class DetailsActivity extends AppCompatActivity {
    ProgressBar loader;
    TextView author;
    TextView content;
    TextView title;
    TextView publishedAt;
    ImageView image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Intent intent = getIntent();
        author = findViewById(R.id.detailAuthor);
        content = findViewById(R.id.detailContent);
        title = findViewById(R.id.detailTitle);
        publishedAt = findViewById(R.id.detailPublishedAt);
        image = findViewById(R.id.detailImage);

        author.setText(intent.getStringExtra("author"));
        content.setText(intent.getStringExtra("content"));
        title.setText(intent.getStringExtra("title"));
        publishedAt.setText(intent.getStringExtra("publishedAt"));
        if(intent.getStringExtra("image").length() < 5) {

            image.setVisibility(View.GONE);

        } else {
            Picasso.get()
                    .load(intent.getStringExtra("image"))
                    .resize(400, 400)
                    .centerCrop()
                    .into(image);
        }
        System.out.println("DEBUGGING" + author + title);
    }
}