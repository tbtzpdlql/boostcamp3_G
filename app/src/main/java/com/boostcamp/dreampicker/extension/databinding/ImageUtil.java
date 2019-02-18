package com.boostcamp.dreampicker.extension.databinding;

import android.net.Uri;
import android.widget.ImageView;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.utils.GlideApp;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import androidx.databinding.BindingAdapter;

public class ImageUtil {

    @BindingAdapter({"image"})
    public static void loadImage(ImageView imageView, Uri uri) {

        if (uri == null) {
            GlideApp.with(imageView)
                    .load(R.drawable.ic_add_to_photos)
                    .into(imageView);
        } else {
            GlideApp.with(imageView)
                    .load(uri)
                    .transform(new RoundedCorners(20))
                    .error(R.drawable.ic_broken_image_black)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(imageView);
        }
    }

    @BindingAdapter({"image"})
    public static void loadImage(ImageView imageView, String url) {
        if (url == null) {
            GlideApp.with(imageView)
                    .load(R.drawable.ic_add_to_photos)
                    .into(imageView);
        } else {
            GlideApp.with(imageView)
                    .load(url)
                    .transform(new RoundedCorners(20))
                    .placeholder(R.drawable.ic_photo)
                    .error(R.drawable.ic_broken_image_black)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(imageView);
        }

    }

    @BindingAdapter({"recImage"})
    public static void rectangleLoadImage(ImageView imageView, String url) {
        if (url == null) {
            GlideApp.with(imageView)
                    .load(R.drawable.ic_photo)
                    .into(imageView);
        } else {
            GlideApp.with(imageView)
                    .load(url)
                    .placeholder(R.drawable.ic_photo)
                    .error(R.drawable.ic_photo)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(imageView);
        }
    }

    @BindingAdapter({"image"})
    public static void loadImage(ImageView imageView, int resourceId) {

        GlideApp.with(imageView).load(resourceId).into(imageView);
    }

    @BindingAdapter({"circleImage"})
    public static void loadCircleImage(ImageView imageView, int resourceId) {

        GlideApp.with(imageView).load(resourceId).circleCrop().into(imageView);
    }

    @BindingAdapter({"circleImage"})
    public static void loadCircleImage(ImageView imageView, String url) {
        if (url == null) {
            return;
        }

        GlideApp.with(imageView).load(url).circleCrop().into(imageView);
    }
}