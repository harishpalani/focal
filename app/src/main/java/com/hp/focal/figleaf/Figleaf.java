package com.hp.focal.figleaf;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

/**
 * @author Harish Palani
 * @version 6/21/2016
 */
public class Figleaf {

    private static final String TAG = Figleaf.class.getSimpleName();
    private static final int NEG_MASK = 0x00FFFFFF;

    public static byte[] invertJPEG(byte[] jpegData) { // HP: Makes image negative before it's saved, "encrypting" it [add ENCRYPTION here]
        Bitmap b = BitmapFactory.decodeByteArray(jpegData, 0, jpegData.length);
        Bitmap invertedBitmap = invert(b);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        invertedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        return stream.toByteArray();
    }

    private static Bitmap invert(Bitmap original) { // HP: insert encryption HERE; right now, picture inversion happens here
        // Create mutable Bitmap to invert, argument true makes it mutable
        Bitmap inversion = original.copy(Bitmap.Config.ARGB_8888, true);

        // Get info about Bitmap
        int width = inversion.getWidth();
        int height = inversion.getHeight();
        int pixels = width * height;

        // Get original pixels
        int[] pixel = new int[pixels];
        inversion.getPixels(pixel, 0, width, 0, 0, width, height);

        // Modify pixels
        for (int i = 0; i < pixels; i++)
            pixel[i] ^= NEG_MASK;
        inversion.setPixels(pixel, 0, width, 0, 0, width, height);

        // Return inverted Bitmap
        return inversion;
    }
}