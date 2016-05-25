package com.dclover.gpsutilities.maps;

import android.content.Context;

import org.json.JSONObject;

public abstract class HttpHelperRequest implements IHttpExecute {
    protected Context mContext;
    public HttpHelperRequest(Context context){
        mContext = context;
    }
    protected abstract void onResponseListener(JSONObject jsonResponse);
    public abstract String getTAG();
    public void cancel(Context context) {
        HttpHelper.getInstance(context).cancelRequest(getTAG());
    }




}
