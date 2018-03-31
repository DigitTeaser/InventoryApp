package com.example.android.inventoryapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper method related to Image setting.
 */
public final class ImageUtils {

    private static final String LOG_TAG = ImageUtils.class.getSimpleName();

    /**
     * Create a private constructor because no one should ever create a {@link ImageUtils} object.
     */
    private ImageUtils() {
    }

    /**
     * Set {@link Bitmap} from the passed-in URI to ImageView.
     *
     * @param uri pointed to the image file.
     */
    public static Bitmap decodeScaledBitmap(Context context, Uri uri, List<Integer> imageViewDimension) {
        // If URI is null, bail early.
        if (uri == null) {
            return null;
        }

        // Get the dimensions of the View.
        int targetW = imageViewDimension.get(0);
        int targetH = imageViewDimension.get(1);

        try {
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            // Set inJustDecodeBounds to true, so the decoder will return null,
            // but the out... fields will still be set.
            bmOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri),
                    null, bmOptions);
            // Get the dimensions of image that need to decode.
            int photoW = bmOptions.outWidth;
            int photoH = bmOptions.outHeight;

            // Determine how much to scale down the image.
            int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

            // Decode the image file into a Bitmap sized to fill the View.
            bmOptions.inJustDecodeBounds = false;
            bmOptions.inSampleSize = scaleFactor;
            bmOptions.inPurgeable = true;

            return BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri),
                    null, bmOptions);
        } catch (FileNotFoundException e) {
            Log.e(LOG_TAG, "Error finding image file " + e);
            // Prompt to user that the item image hasn't been found.
            Toast.makeText(context, "Image Not Found.", Toast.LENGTH_SHORT).show();
        }

        return null;
    }

    /**
     * Get the dimension (width, height) of {@link ImageView}.
     *
     * @param imageView that need to get its dimension
     * @return a {@link List<Integer>} which item 0 is width and item 1 is height.
     */
    public static List<Integer> getImageViewDimension(ImageView imageView) {
        List<Integer> imageViewDimension = new ArrayList<>();
        imageViewDimension.add(imageView.getWidth());
        imageViewDimension.add(imageView.getHeight());
        return imageViewDimension;
    }
}
