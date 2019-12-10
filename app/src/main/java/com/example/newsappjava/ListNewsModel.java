package com.example.newsappjava;

import android.widget.ImageView;
import android.widget.TextView;

public class ListNewsModel {
    ImageView galleryImage;
    TextView author, title, details, time;

    public ListNewsModel(TextView author, TextView title, TextView details, TextView time, ImageView galleryImage) {
        this.galleryImage = galleryImage;
        this.author = author;
        this.title = title;
        this.details = details;
        this.time = time;
        this.galleryImage = galleryImage;
    }

    public ImageView getGalleryImage() {
        return galleryImage;
    }

    public TextView getAuthor() {
        return author;
    }

    public TextView getTitle() {
        return title;
    }

    public TextView getDetails() {
        return details;
    }

    public TextView getTime() {
        return time;
    }
}
