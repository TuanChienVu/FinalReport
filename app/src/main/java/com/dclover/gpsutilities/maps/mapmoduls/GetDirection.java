package com.dclover.gpsutilities.maps.mapmoduls;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;
import com.dclover.gpsutilities.khoihanh.moduls.Route;
import com.dclover.gpsutilities.khoihanh.moduls.Steps;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GetDirection extends HttpHelperRequest {


    public OnCompleteGetDirection mOnCompleteGetDirection;
    String address1 = "";
    String address2 = "";

    public GetDirection(Context context, String address1, String address2, OnCompleteGetDirection onCompleteGetDirection){
        super(context);
        mOnCompleteGetDirection = onCompleteGetDirection;
        this.address1 = address1;
        this.address2 = address2;

    }
    //phuong thuc duoc goi khi nhan duoc ket qua tra ve tu server
    //truong hop khong bi loi
    @Override
    protected void onResponseListener(JSONObject jsonResponse) {
        //parse JSON
        //LUU API KEY vao SharedPref

        Route direction = null;
        Steps steps = null;
        List<Steps> stepsList = null;

        try {

            direction = new Route();
            stepsList = new ArrayList<Steps>();

            JSONArray jsonArrayRoutes = jsonResponse.getJSONArray("routes");
            JSONObject jsonObjectLegs = jsonArrayRoutes.getJSONObject(0);
            JSONArray jsonArrayLegs = jsonObjectLegs.getJSONArray("legs");
            JSONObject jsonObjectProperty = jsonArrayLegs.getJSONObject(0);

            JSONObject jsonObjectDistance = jsonObjectProperty.getJSONObject("distance");
            JSONObject jsonObjectDuration = jsonObjectProperty.getJSONObject("duration");

            //distance
            //jsonObjectDistance.getString("text");
//            direction.setKcdistance(jsonObjectDistance.getString("text"));

            //duration
            //jsonObjectDuration.getString("text");
//            direction.setTgduration(jsonObjectDuration.getString("text"));

            JSONArray jsonArraySteps = jsonObjectProperty.getJSONArray("steps");

            for (int i = 0; i < jsonArraySteps.length(); i++) {

                steps = new Steps();

                JSONObject jsonObjectSteps = jsonArraySteps.getJSONObject(i);

                JSONObject jsonObjectStepsDistance = jsonObjectSteps.getJSONObject("distance");
                JSONObject jsonObjectStepsDuration = jsonObjectSteps.getJSONObject("duration");
                JSONObject jsonObjectPolyline = jsonObjectSteps.getJSONObject("polyline");

                //distance
                //jsonObjectStepsDistance.getString("text");
                steps.setStep_distance(jsonObjectStepsDistance.getString("text"));

                //duration
                //jsonObjectStepsDuration.getString("text");
                steps.setStep_duration(jsonObjectStepsDuration.getString("text"));

                //html_instructions
                //jsonObjectSteps.getString("html_instructions");
                steps.setHtml_instructions(jsonObjectSteps.getString("html_instructions"));

                //points
                //jsonObjectPolyline.getString("points");
                steps.setPoints(jsonObjectPolyline.getString("points"));

                stepsList.add(steps);

                //mOnCompleteGetDirection.onCompleteGetDirection(true, jsonObjectPolyline.getString("points"));

            }

            direction.setSteps(stepsList);

            //SharedPrefUtils.putPreference(mContext, "APIKey",APIKey);

            //thong bao getAPIKey thanh cong
            mOnCompleteGetDirection.onCompleteGetDirection(true, direction, direction.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //Dung de quan ly khi goi API tu server
    @Override
    public String getTAG() {
        return "GetString Direction";
    }

    @Override
    public void post() {

        Log.d("xxxxxxxxxxxxxxxxx = ", "https://maps.googleapis.com/maps/api/directions/json?origin="+address1+"&destination="+address2);

        HttpHelper httpHelper = HttpHelper.getInstance(mContext);
        httpHelper.makeJsonRequest(HttpHelper.Method.GET,

                /*"http://maps.googleapis.com/maps/api/directions/xml?\"\n" +
                        "                + \"origin=\" + start.latitude + \",\" + start.longitude\n" +
                        "                + \"&destination=\" + end.latitude + \",\" + end.longitude\n" +
                        "                + \"&sensor=false&units=metric&mode=driving\n",*/

                //"https://maps.googleapis.com/maps/api/directions/json?origin=10.765097,%20106.681586&destination=10.787441,%20106.664334",

                "https://maps.googleapis.com/maps/api/directions/json?origin="+address1+"&destination="+address2,

                null,
                new HttpHelper.ResponseHandler() {
                    @Override
                    public void handleJsonResponse(JSONObject jsonObject) {
                        //call API thanh cong
                        onResponseListener(jsonObject);
                    }
                },
                new HttpHelper.ResponseErrorHandler() {
                    @Override
                    public void handleError(VolleyError error, String msg) {
                        mOnCompleteGetDirection.onCompleteGetDirection(false, null, msg);
                    }
                },
                getTAG(),
                HttpHelper.TIMEOUT,
                HttpHelper.RETRY_TIMES
        );


    }

    public interface OnCompleteGetDirection{
        void onCompleteGetDirection(boolean isOK, Route direction, String... msg);
    }
}
