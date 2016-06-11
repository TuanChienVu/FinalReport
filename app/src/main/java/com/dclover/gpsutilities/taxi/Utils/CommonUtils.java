package com.dclover.gpsutilities.taxi.Utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import com.dclover.gpsutilities.taxi.model.Driver;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Kinghero on 6/6/2016.
 */
public class CommonUtils {
    public static void showAlertDialog(Activity activity, String title, String message) {
        try {
            CommonAlertDialog newFragment = new CommonAlertDialog();
            newFragment.setmTitle(title);
            newFragment.setmMessage(message);
            newFragment.show(activity.getFragmentManager(), "DlgTag");
        } catch (Exception e) {
        }
    }

    public static class CommonAlertDialog extends DialogFragment {
        private String mMessage;
        private String mTitle;

        public String getmTitle() {
            return this.mTitle;
        }

        public void setmTitle(String mTitle) {
            this.mTitle = mTitle;
        }

        public String getmMessage() {
            return this.mMessage;
        }

        public void setmMessage(String mMessage) {
            this.mMessage = mMessage;
        }

        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder dlgBuilder = new AlertDialog.Builder(getActivity());
            dlgBuilder.setTitle(this.mTitle);
            dlgBuilder.setMessage(this.mMessage);
            dlgBuilder.setPositiveButton("Đồng ý", null);
            return dlgBuilder.create();
        }

    }

    public static boolean isConnectInternet() {
        NetworkInfo networkInfo = ((ConnectivityManager) Env.getApplication().getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected()) {
            return false;
        }
        return true;
    }
    public static Object[] convertJson2MapDriver(String jsonContent) {
        Object[] objArr = new Object[2];
        List<Driver> lstDriver = new ArrayList();
        double time_estimate = 0.0d;
        try {
            JsonObject jObj = new JsonParser().parse(jsonContent).getAsJsonObject();
            Iterator i$ = jObj.getAsJsonArray("drivers").iterator();
            while (i$.hasNext()) {
                lstDriver.add((Driver) new Gson().fromJson((JsonElement) i$.next(), Driver.class));
            }
            time_estimate = jObj.get("time_estimate").getAsDouble();
        } catch (Exception e) {
        }
        objArr[0] = lstDriver;
        objArr[1] = Double.valueOf(time_estimate);
        return objArr;
    }

    public static Bitmap resizeImage(Resources resources, Bitmap bitmap, int bounding_x, int bounding_y) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        DisplayMetrics dm = resources.getDisplayMetrics();
        if (bounding_x <= 0 || bounding_y <= 0) {
            bounding_x = (int) (((double) dm.widthPixels) * 0.7d);
            bounding_y = (int) (((double) dm.heightPixels) * 0.7d);
        }
        float xScale = ((float) bounding_x) / ((float) width);
        float yScale = ((float) bounding_y) / ((float) height);
        Matrix matrix = new Matrix();
        matrix.postScale(xScale, yScale);
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
    }
    public static int dpToPx(float dp, Resources resources) {
        return (int) TypedValue.applyDimension(1, dp, resources.getDisplayMetrics());
    }
    public static boolean isEmpty(String s) {
        if (s == null || XmlPullParser.NO_NAMESPACE.equals(s.trim())) {
            return true;
        }
        return false;
    }
    public static Bitmap convertString2Bitmap(String base64Img) {
        if (base64Img == null) {
            return null;
        }
        byte[] decodedString = Base64.decode(base64Img, 0);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }

}
