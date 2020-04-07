package kchaou.uha.fr.test.fragments;

import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

public class DataBindingAdapters {

    @BindingAdapter("image")
    public static void setImageBitmap(ImageView view, Bitmap bitmap) {
        view.setImageBitmap(bitmap);
    }

    @BindingAdapter("image")
    public static void setImageDrawable(ImageView view, Drawable drawable) {
        view.setImageDrawable(drawable);
    }

    @BindingAdapter("image")
    public static void setImageResource(ImageView view, int resource){
        view.setImageResource(resource);
    }

    @BindingAdapter(value={"bitmap", "empty"})
    public static void setBitmapOrDefault(ImageView view, Bitmap bitmap, Drawable empty) {
        if (bitmap == null) {
            setImageDrawable(view, empty);
        } else {
            setImageBitmap (view, bitmap);
        }
    }
}

