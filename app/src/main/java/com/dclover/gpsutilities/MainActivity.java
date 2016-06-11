package com.dclover.gpsutilities;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dclover.gpsutilities.caidat.ActivityCaiDat;
import com.dclover.gpsutilities.compass.ComPass;
import com.dclover.gpsutilities.khoihanh.ActivityKhoiHanh;
import com.dclover.gpsutilities.maps.MapActivity;
import com.dclover.gpsutilities.taxi.Activity.LoginActivity;
import com.dclover.gpsutilities.taxi.Activity.MainActivityTaxi;
import com.dclover.gpsutilities.taxi.Utils.Env;
import com.dclover.gpsutilities.taxi.model.User;

import org.xmlpull.v1.XmlPullParser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    LinearLayout llcompas,llcaidat,llhuongdan,llketnoi,lltaxi,llkhoihanh,llbando;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        llcompas=(LinearLayout) findViewById(R.id.ll_compass);
        llcaidat=(LinearLayout) findViewById(R.id.ll_caidat);
        llhuongdan=(LinearLayout) findViewById(R.id.ll_huongdan);
        llketnoi=(LinearLayout) findViewById(R.id.ll_connect);
        lltaxi=(LinearLayout) findViewById(R.id.ll_taxi);
        llkhoihanh=(LinearLayout) findViewById(R.id.ll_khoihanh);
        llbando=(LinearLayout) findViewById(R.id.ll_map);


        llcompas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, ComPass.class);
                startActivity(intent);
            }
        });
        llcaidat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customToast("Chức năng này đang phát triển");
            }
        });
        llhuongdan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customToast("Chức năng này đang phát triển");
            }
        });
        llketnoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customToast("Chức năng này đang phát triển");
            }
        });
        llkhoihanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getBaseContext(),ActivityKhoiHanh.class);
                startActivity(intent);
            }
        });
        llbando.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });
        lltaxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User objUser = Env.readUser(MainActivity.this);
                Intent intent;
                if(objUser==null)
                 intent = new Intent(MainActivity.this, LoginActivity.class);
                else
                    intent = new Intent(MainActivity.this, MainActivityTaxi.class);

                startActivity(intent);
            }
        });
        llcaidat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getBaseContext(),ActivityCaiDat.class);
                startActivity(intent);
            }
        });

    }


    private void customToast(String msg){
        LayoutInflater layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View inflatedView = layoutInflater.inflate(R.layout.fb_comments_list_item, null,false);
        TextView tvTitle = (TextView) inflatedView.findViewById(R.id.tv_text_popup);
        tvTitle.setText(msg);
        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setGravity(Gravity.BOTTOM, 0, 0);
        toast.setView(inflatedView);
        toast.show();
    }
    public static String getListCar(Location mLocation) {
        Map<String, Object> mapParams = new HashMap();
        mapParams.put("longitude", Double.valueOf(mLocation.getLongitude()));
        mapParams.put("latitude", Double.valueOf(mLocation.getLatitude()));
        mapParams.put("distance", Integer.valueOf(500));
        mapParams.put("amount", Integer.valueOf(5));
        mapParams.put( Notification.CATEGORY_STATUS, Integer.valueOf(4));
        mapParams.put("userid", "admin");
        String resultContent = doGet("http://tms.giamsatxeviet.vn:8080/TaxiOperation/rest/MobileWS/postTaxiPosition", mapParams);

        return resultContent;
    }


    public static String doGet(String strUrl, Map<String, Object> params) {
        String result = XmlPullParser.NO_NAMESPACE;
        InputStream inputStream = null;
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) new URL(strUrl + "?" + getQuery(params)).openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(1000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();
            int response = conn.getResponseCode();
            inputStream = conn.getInputStream();
            result = readInputStream(inputStream);
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e) {
                }
            }
            if (conn != null) {
                conn.disconnect();
            }
            return result;
        } catch (Exception ex) {
            result = ex.getMessage();
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e2) {
                    return result;
                }
            }
            if (conn != null) {
                conn.disconnect();
            }
            return result;
        } catch (Throwable th) {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e3) {
                }
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        return result;
    }

    public static String getQuery(Map<String, Object> mapParams) throws UnsupportedEncodingException {
        if (mapParams == null) {
            return XmlPullParser.NO_NAMESPACE;
        }
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, Object> param : mapParams.entrySet()) {
            if (first) {
                first = false;
            } else {
                result.append("&");
            }
            result.append(URLEncoder.encode((String) param.getKey(), "UTF-8"));
            result.append('=');
            result.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }
        return result.toString();
    }

    public static String readInputStream(InputStream stream) throws Throwable {
        Throwable th;
        BufferedReader reader = null;
        StringBuffer result = new StringBuffer();
        try {
            BufferedReader reader2 = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
            while (true) {
                try {
                    String inputLine = reader2.readLine();
                    if (inputLine == null) {
                        break;
                    }
                    result.append(inputLine);
                } catch (Exception e) {
                    reader = reader2;
                } catch (Throwable th2) {
                    th = th2;
                    reader = reader2;
                }
            }
            if (reader2 != null) {
                try {
                    reader2.close();
                } catch (Exception e2) {
                    reader = reader2;
                }
            }
            reader = reader2;
        } catch (Exception e3) {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e4) {
                }
            }
            return result.toString();
        } catch (Throwable th3) {
            th = th3;
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e5) {
                }
            }
            throw th;
        }
        return result.toString();
    }


}
