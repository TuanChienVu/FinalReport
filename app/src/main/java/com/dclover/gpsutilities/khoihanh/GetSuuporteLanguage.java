package com.dclover.gpsutilities.khoihanh;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by Kinghero on 7/5/2016.
 */
public class GetSuuporteLanguage {
    private static final int HEIGHT_PX = 80;
    private static final int WIDTH_PX = 200;

    public static boolean isSupported(Context context, String text) {
        float scale = context.getResources().getDisplayMetrics().density;
        Bitmap.Config conf = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = Bitmap.createBitmap(WIDTH_PX, HEIGHT_PX, conf);
        Bitmap orig = bitmap.copy(conf, false);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(1);
        paint.setColor(Color.rgb(0, 0, 0));
        paint.setTextSize((float) ((int) (14.0f * scale)));
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        canvas.drawText(text, (float) ((bitmap.getWidth() - bounds.width()) / 2), (float) ((bitmap.getHeight() + bounds.height()) / 2), paint);
        boolean res = !orig.sameAs(bitmap);
        orig.recycle();
        bitmap.recycle();
        return res;
    }
}
