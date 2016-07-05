package com.dclover.gpsutilities.taxi.Utils;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dclover.gpsutilities.R;
import com.dclover.gpsutilities.taxi.model.PhoneCenterModel;

import java.util.List;

/**
 * Created by Kinghero on 6/11/2016.
 */
public class PhoneCenterAdapter extends ArrayAdapter<PhoneCenterModel> {
    Context context;
    List<PhoneCenterModel> datas;
    LayoutInflater inflater;

    public PhoneCenterAdapter(Context context, int resource, List<PhoneCenterModel> objects) {
        super(context, resource, objects);
        this.datas = objects;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.dialog_list_driver_item, parent, false);
        TextView tvName = (TextView) view.findViewById(R.id.tv_driver_name);
        TextView tvNumber = (TextView) view.findViewById(R.id.tv_driver_phone);
        ImageView img = (ImageView) view.findViewById(R.id.imv_driver_car);
        tvName.setText(datas.get(position).getName());
        tvNumber.setText(datas.get(position).getNumber());
        img.setImageResource(R.drawable.ic_center);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + ""+datas.get(position).getNumber()));
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                context.startActivity(intent);
            }
        });
        return view;

    }

    @Override
    public int getCount() {
        return datas.size();
    }
}
