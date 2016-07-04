package com.dclover.gpsutilities.maps.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.dclover.gpsutilities.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kinghero on 7/3/2016.
 */
public class ItemDiaDiemAdapter extends RecyclerView.Adapter<ItemDiaDiemAdapter.MyViewHolder>{
    private List<String> data=new ArrayList<>();
    private List<String> type=new ArrayList<>();

    int po=-1;
    OnItemDiaDiemClick click;
    public void setClick(OnItemDiaDiemClick click)
    {
        this.click=click;
    }
    public interface OnItemDiaDiemClick{
        void onClickDiadiem(String type);
    }

    public ItemDiaDiemAdapter() {

        data.clear(); type.clear();
        data.add("ATM"); type.add("atm");
        data.add("Bến BUS");type.add("bus_station");
        data.add("Bệnh viện");type.add("hospital");
        data.add("Quán Cafe");type.add("cafe");
        data.add("Cây xăng");type.add("gas_station");
        data.add("Công viên");type.add("park");
        data.add("Tập Gym");type.add("gym");
        data.add("Ngân hàng");type.add("bank");
        data.add("Nhà hàng");type.add("restaurant");
        data.add("Nhà nghĩ");type.add("hotel");
        data.add("Nhà thờ");type.add("church");
        data.add("Quán cắt tóc");type.add("haircuts");
        data.add("Sân bay");type.add("airport");
        data.add("Sở thú");type.add("zoo");
        data.add("Trường đại học");type.add("university");
        data.add("Đồn công an");type.add("police");
    }


    @Override
    public int getItemCount() {
        return data.size();
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        if(position==po)
            holder.fm.setBackgroundResource(R.drawable.shape_item_diadiem_click);
        else
            holder.fm.setBackgroundResource(R.drawable.shape_item_diadiem);
        holder.tv.setText(data.get(position));
        holder.fm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(click!=null) {
                    po=position;
                    click.onClickDiadiem(type.get(position));
                    notifyDataSetChanged();
                }
            }
        });


    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_diadiem, parent, false);

        return new MyViewHolder(itemView);
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv;
        public FrameLayout fm;
        public MyViewHolder(View view) {
            super(view);
            tv = (TextView) view.findViewById(R.id.tv_item_diadiem);
            fm=(FrameLayout) view.findViewById(R.id.frm_item_diadiem);
        }
    }
}
