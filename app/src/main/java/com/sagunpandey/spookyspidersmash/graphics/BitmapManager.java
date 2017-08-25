package com.sagunpandey.spookyspidersmash.graphics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.RectF;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by sagun on 8/10/2017.
 */

public class BitmapManager {

    private Context context;

    private BitmapFactory.Options options;

    public BitmapManager(Context context) {
        this.context = context;

        options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        options.inSampleSize = 1;
        options.inScaled = false;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
    }

    public Bitmap newBitmap(int resourceId) {
        return BitmapFactory.decodeResource(
                context.getResources(),
                resourceId,
                options);
    }

    public Bitmap newBitmap(String filename, int dstWidth, int dstHeight) {
        try
        {
            int inWidth;
            int inHeight;

            InputStream in = context.getAssets().open(filename);

            // Decode image size (Decode metadata only, not the whole image)
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(in, null, options);
            in.close();

            // Save width and height
            inWidth = options.outWidth;
            inHeight = options.outHeight;

            // Decode full image pre-resized
            in = context.getAssets().open(filename);
            options = new BitmapFactory.Options();
            // Calculate rough size
            options.inSampleSize = Math.max(inWidth/dstWidth, inHeight/dstHeight);
            // Decode full image
            Bitmap roughBitmap = BitmapFactory.decodeStream(in, null, options);

            // Calculate exact destination size
            Matrix m = new Matrix();
            RectF inRect = new RectF(0, 0, roughBitmap.getWidth(), roughBitmap.getHeight());
            RectF outRect = new RectF(0, 0, dstWidth, dstHeight);
            m.setRectToRect(inRect, outRect, Matrix.ScaleToFit.CENTER);
            float[] values = new float[9];
            m.getValues(values);

            // Resize Bitmap
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(
                    roughBitmap,
                    (int) (roughBitmap.getWidth() * values[0]),
                    (int) (roughBitmap.getHeight() * values[4]),
                    true
            );

            if (roughBitmap != scaledBitmap )
                roughBitmap.recycle();

            return scaledBitmap;
        }
        catch (IOException e) {
            throw new RuntimeException("Error loading bitmap '" + filename + "'");
        }
    }

    public static Bitmap scaleBitmap(Bitmap bitmap, float scaleWidth, float scaleHeight, boolean recycle) {
        int newWidth = (int) (bitmap.getWidth() * scaleWidth);
        int newHeight = (int) (bitmap.getHeight() * scaleHeight);

        return scaleBitmap(bitmap, newWidth, newHeight, scaleWidth, scaleHeight, recycle);
    }

    public static Bitmap scaleBitmap(Bitmap bitmap, float scaleFactor, boolean recycle) {
        return scaleBitmap(bitmap, scaleFactor, scaleFactor, recycle);
    }

    public static Bitmap scaleBitmap(Bitmap bitmap, int newWidth, int newHeight, boolean recycle) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        return scaleBitmap(bitmap, newWidth, newHeight, scaleWidth, scaleHeight, recycle);
    }

    private static Bitmap scaleBitmap(Bitmap bitmap,
                                      int newWidth,
                                      int newHeight,
                                      float scaleWidth,
                                      float scaleHeight,
                                      boolean recycle) {
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);

        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap,
                newWidth, newHeight, true);

        if (recycle && bitmap!=scaledBitmap )
            bitmap.recycle();

        return scaledBitmap;
    }
}
