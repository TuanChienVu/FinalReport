package com.dclover.gpsutilities.maps;

/**
 * Created by Do Huu Thien on 11/8/2015.
 */
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MJsonObjRequest extends JsonObjectRequest{
    public MJsonObjRequest(int method, String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        String responseData = new String(response.data);
        Log.d("volley.debugging", responseData);
        return super.parseNetworkResponse(response);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> params = new HashMap<>();
        params.put("Content-Type", "application/json");
        return params;
    }
}
