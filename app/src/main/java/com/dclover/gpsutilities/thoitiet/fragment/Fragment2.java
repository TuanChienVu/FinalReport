package com.dclover.gpsutilities.thoitiet.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dclover.gpsutilities.BuildConfig;
import com.dclover.gpsutilities.R;
import com.dclover.gpsutilities.thoitiet.datas.Channel;
import com.dclover.gpsutilities.thoitiet.datas.ForeCastData;
import com.dclover.gpsutilities.thoitiet.services.YahooWeatherService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Kinghero on 7/5/2016.
 */
public class Fragment2  extends Fragment {
    Channel channel;
    List<DayForeCast> infolst;
    YahooWeatherService mservice;
    RecyclerView recyclerView;
    TextView tv;

    class DayForeCast {
        String condition;
        String date;
        int imgID;
        String temp;

        DayForeCast(String date, String condition, int imgID, String temp) {
            this.date = date;
            this.condition = condition;
            this.imgID = imgID;
            this.temp = temp;
        }
    }

    class RVAdapterOverView extends RecyclerView.Adapter<RVAdapterOverView.DayForeCastHolder> {
        List<DayForeCast> infolst;

        /* renamed from: com.sundroid.myapplication.Fragments.Fragment2.RVAdapterOverView.1 */
        class C02021 implements View.OnClickListener {
            C02021() {
            }

            public void onClick(View view) {
                DayForeCastHolder holder = (DayForeCastHolder) view.getTag();
                Toast.makeText(Fragment2.this.getActivity(), ((DayForeCast) RVAdapterOverView.this.infolst.get(holder.getPosition())).date + "\nTemp :- " + holder.temp.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        }

        public class DayForeCastHolder extends RecyclerView.ViewHolder {
            TextView condition;
            CardView cv;
            TextView day;
            ImageView imgID;
            TextView temp;

            DayForeCastHolder(View itemView) {
                super(itemView);
                this.cv = (CardView) itemView.findViewById(R.id.frag2_cv);
                this.day = (TextView) itemView.findViewById(R.id.frag2_card_day);
                this.condition = (TextView) itemView.findViewById(R.id.frag2_card_condition);
                this.imgID = (ImageView) itemView.findViewById(R.id.frag2_card_iv);
                this.temp = (TextView) itemView.findViewById(R.id.frag2_card_temp);
            }
        }

        RVAdapterOverView(List<DayForeCast> infolst) {
            this.infolst = infolst;
        }

        public DayForeCastHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new DayForeCastHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.frag2_card_row, parent, false));
        }

        public void onBindViewHolder(DayForeCastHolder holder, int i) {
            holder.day.setText(((DayForeCast) this.infolst.get(i)).date);
            holder.imgID.setImageResource(((DayForeCast) this.infolst.get(i)).imgID);
            holder.temp.setText(((DayForeCast) this.infolst.get(i)).temp);
            holder.condition.setText(((DayForeCast) this.infolst.get(i)).condition);
            View.OnClickListener clickListener = new C02021();
            holder.day.setOnClickListener(clickListener);
            holder.condition.setOnClickListener(clickListener);
            holder.cv.setOnClickListener(clickListener);
            holder.day.setTag(holder);
            holder.condition.setTag(holder);
            holder.cv.setTag(holder);
        }

        public int getItemCount() {
            return this.infolst.size();
        }

        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
        }
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag2, container, false);
        getActivity().setTitle("Daily Forecast");
        this.recyclerView = (RecyclerView) view.findViewById(R.id.frag2_rv);
        this.tv = (TextView) view.findViewById(R.id.frag2_tv);
        this.channel = Fragment1.schannel;
        this.mservice = Fragment1.service;
        this.tv.setText(this.mservice.getLocation());
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        initializeData();
        this.recyclerView.setAdapter(new RVAdapterOverView(this.infolst));
        return view;
    }

    private List<DayForeCast> initializeData() {
        this.infolst = new ArrayList();
        Iterator it = this.channel.getItem().getForeCast().getForeCastDatas().iterator();
        while (it.hasNext()) {
            ForeCastData fd = (ForeCastData) it.next();
            String date = fd.getDate();
            String high = fd.getHigh();
            String low = fd.getLow();
            String cond = fd.getText();
            int resourceId = getResources().getIdentifier("drawable/img_" + fd.getCode(), null, getActivity().getPackageName());
            this.infolst.add(new DayForeCast(date, cond, resourceId, tempconversion(high) + "\u00b0/" + tempconversion(low) + "\u00b0"));
        }
        return this.infolst;
    }

    public String tempconversion(String temp) {
        return (((Integer.parseInt(temp) - 32) * 5) / 9) + BuildConfig.FLAVOR;
    }
}