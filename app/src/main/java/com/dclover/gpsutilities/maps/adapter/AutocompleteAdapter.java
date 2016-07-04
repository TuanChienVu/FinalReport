package com.dclover.gpsutilities.maps.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.dclover.gpsutilities.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kinghero on 7/4/2016.
 */
public class AutocompleteAdapter extends ArrayAdapter {
    Context context;
    List<com.dclover.gpsutilities.maps.mapmoduls.autocomplete.Prediction> data=new ArrayList<>();
    public AutocompleteAdapter(Context context, int resource,  List<com.dclover.gpsutilities.maps.mapmoduls.autocomplete.Prediction> data) {
        super(context, resource, data);
        this.context = context;
        this.data = data;
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.item_diadiem_autocomplete_adapter,parent,false);

        TextView txtAuto = (TextView) view.findViewById(R.id.edt_item_diadiem_autocomple);
        txtAuto.setText(data.get(position).getDescription());
        return view;
    }
}
