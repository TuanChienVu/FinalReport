package com.dclover.gpsutilities.taxi.Utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dclover.gpsutilities.R;
import com.dclover.gpsutilities.taxi.Activity.LoginActivity;
import com.dclover.gpsutilities.taxi.Activity.MainActivityTaxi;
import com.dclover.gpsutilities.taxi.Activity.PhoneCenter;

/**
 * Created by Kinghero on 6/7/2016.
 */
public class DrawerListAdapter extends ArrayAdapter<String> {
    Context context;
    String[] section;
    DrawerLayout drawer;
    public DrawerListAdapter(DrawerLayout drawer,Context context, int layout_id, String[] section) {
        super(context, layout_id, section);
        this.context=context;
        this.section=section;
        this.drawer=drawer;
    }
    public View getView(final int position, View convertView, ViewGroup parent) {
        View rowView = ((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.drawer_list_item, parent, false);
        ImageView imvItem = (ImageView) rowView.findViewById(R.id.imv_drawer_item);
        TextView tvItem = (TextView) rowView.findViewById(R.id.tvw_drawer_item);
        LinearLayout lii=(LinearLayout) rowView.findViewById(R.id.itemViewNavigation);
        imvItem.setColorFilter(-1, PorterDuff.Mode.SRC_IN);
        if (position == 0) {
            tvItem.setTextColor(context.getResources().getColor(R.color.yellowgreen));
        } else if (position == 1) {
            imvItem.setImageResource(R.drawable.user_48);
        } else if (position == 2) {
            imvItem.setImageResource(R.drawable.telephone_48);

        } else if (position == 3) {
            imvItem.setImageResource(R.drawable.history_48);
        } else if (position == 4) {
            imvItem.setImageResource(R.drawable.question_48);
        } else if (position == 5) {
            imvItem.setImageResource(R.drawable.about);
        } else if (position == 6) {
            imvItem.setImageResource(R.drawable.world_48);
        } else if (position == 7) {
            imvItem.setImageResource(R.drawable.user_48);
        }
        tvItem.setText(section[position]);
        lii.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                switch (position)
                {
                    case 2:
                         intent=new Intent(context, PhoneCenter.class);
                        ((MainActivityTaxi)context).finish();
                        context.startActivity(intent);
                        break;
                    case 7:
                        Env.removeUser(context);
                        intent=new Intent(context, LoginActivity.class);
                        ((MainActivityTaxi)context).finish();
                        context.startActivity(intent);
                        break;
                }
                drawer.closeDrawers();
            }
        });
        return rowView;
    }

}
