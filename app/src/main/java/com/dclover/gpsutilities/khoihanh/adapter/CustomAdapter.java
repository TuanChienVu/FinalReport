package com.dclover.gpsutilities.khoihanh.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dclover.gpsutilities.R;

import java.util.ArrayList;

/**
 * Created by Kinghero on 7/5/2016.
 */
public class CustomAdapter extends ArrayAdapter<String> {
    ArrayList<String> EndPlace;
    ArrayList<String> StartPlace;
    ArrayList<String> TripName;
    Context context;
    public customButtonListener customListner;

    /* renamed from: com.virtualmaze.gpstriptracker.CustomAdapter.1 */
    class C05731 implements View.OnClickListener {
        final /* synthetic */ int val$position;

        C05731(int i) {
            this.val$position = i;
        }

        public void onClick(View v) {
            CustomAdapter.this.customListner.onDeleteClickListner((String) CustomAdapter.this.TripName.get(this.val$position), this.val$position);
        }
    }

    public interface customButtonListener {
        void onDeleteClickListner(String str, int i);
    }

    public CustomAdapter(Context context, ArrayList<String> TripName, ArrayList<String> StartPlace, ArrayList<String> EndPlace, customButtonListener listener) {
        super(context, R.layout.custom_list_layout, TripName);
        this.context = context;
        this.TripName = TripName;
        this.StartPlace = StartPlace;
        this.EndPlace = EndPlace;
        this.customListner = listener;
    }

    public View getView(int position, View view, ViewGroup parent) {
        View customView = LayoutInflater.from(this.context).inflate(R.layout.custom_list_layout, parent, false);
        TextView tripTitle = (TextView) customView.findViewById(R.id.tripNameTextview);
        TextView startPlace = (TextView) customView.findViewById(R.id.startPlaceTextview);
        TextView endPlace = (TextView) customView.findViewById(R.id.endPlaceTextview);
        ((ImageView) customView.findViewById(R.id.deleteButton)).setOnClickListener(new C05731(position));
        tripTitle.setText((CharSequence) this.TripName.get(position));
        startPlace.setText((CharSequence) this.StartPlace.get(position));
        endPlace.setText((CharSequence) this.EndPlace.get(position));
        return customView;
    }
}
