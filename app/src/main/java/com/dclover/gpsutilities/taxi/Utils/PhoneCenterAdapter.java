package com.dclover.gpsutilities.taxi.Utils;

import android.content.Context;
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
public class PhoneCenterAdapter extends ArrayAdapter<PhoneCenterModel>{
    Context context;
    List<PhoneCenterModel> datas;
    LayoutInflater inflater;
    public PhoneCenterAdapter(Context context, int resource, List<PhoneCenterModel> objects) {
        super(context, resource, objects);
        this.datas=objects;
        this.context=context;
        inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=inflater.inflate(R.layout.dialog_list_driver_item,parent,false);
        TextView tvName= (TextView) view.findViewById(R.id.tv_driver_name);
        TextView tvNumber=(TextView) view.findViewById(R.id.tv_driver_phone);
        ImageView img=(ImageView) view.findViewById(R.id.imv_driver_car);
        tvName.setText(datas.get(position).getName());
        tvNumber.setText(datas.get(position).getNumber());
        img.setImageResource(R.drawable.ic_center);
        return view;

    }

    @Override
    public int getCount() {
        return datas.size();
    }
}
