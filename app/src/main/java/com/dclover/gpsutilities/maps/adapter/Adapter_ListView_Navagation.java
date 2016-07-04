package com.dclover.gpsutilities.maps.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dclover.gpsutilities.R;
import com.dclover.gpsutilities.maps.mapmoduls.item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kinghero on 7/4/2016.
 */
public class Adapter_ListView_Navagation extends BaseAdapter {

    Context context;
    List<item> dulieu=new ArrayList<item>();

    LayoutInflater inflater;
    public int po=0;
    public Adapter_ListView_Navagation(Context context, int resource,List<item> i) {
        this.context=context;
        dulieu=i;
        inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return dulieu.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=inflater.inflate(R.layout.item_menu_rowlist,null);
        ImageView imageView=(ImageView) view.findViewById(R.id._0_row_imgIcon);
        TextView textView=(TextView) view.findViewById(R.id._0_row_txtTenMenu);
        FrameLayout ke=(FrameLayout) view.findViewById(R.id._0_row_frameDongke);
        if(po==position)
            imageView.setColorFilter(ContextCompat.getColor(context,R.color.colorPrimaryDark));

        item i=dulieu.get(position);
        if(i.isKe())
        {
            imageView.setVisibility(View.GONE);
            textView.setVisibility(View.GONE);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 50);
            view.setLayoutParams(layoutParams);
            ke.setVisibility(View.VISIBLE);
        }
        else
        {
            Typeface roboto = Typeface.createFromAsset(context.getAssets(),
                    "font/Roboto-Medium.ttf");

            imageView.setImageResource(i.getImg());
            textView.setTypeface(roboto);
            textView.setText(i.getText());
        }
        return view;


    }
}
