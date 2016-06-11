package com.dclover.gpsutilities.taxi.Utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.dclover.gpsutilities.R;
import com.dclover.gpsutilities.taxi.model.Driver;

/**
 * Created by Kinghero on 6/8/2016.
 */
public class ListDriverDialogFragment extends DialogFragment {
    private Driver[] drivers;

    /* renamed from: com.vietek.taximailinh.activity.ListDriverDialogFragment.1 */
    class C06181 implements DialogInterface.OnClickListener {
        C06181() {
        }

        public void onClick(DialogInterface dialog, int which) {
        }
    }

    private class DriverAdapter extends ArrayAdapter<Driver> {
        private Driver[] arrDriver;

        /* renamed from: com.vietek.taximailinh.activity.ListDriverDialogFragment.DriverAdapter.1 */
        class C06191 implements View.OnClickListener {
            final /* synthetic */ int val$position;

            C06191(int i) {
                this.val$position = i;
            }

            public void onClick(View v) {
                Driver driver = DriverAdapter.this.arrDriver[this.val$position];
                if (driver != null && driver.getPhoneNumber() != null && driver.getPhoneNumber().trim().length() > 0) {
                    ListDriverDialogFragment.this.startActivity(new Intent("android.intent.action.CALL", Uri.parse("tel:" + driver.getPhoneNumber().trim())));
                   // DriverAdapter.this.startLogPhoneCallTask(driver);
                }
            }
        }

        /* renamed from: com.vietek.taximailinh.activity.ListDriverDialogFragment.DriverAdapter.2 */
//        class C06202 implements ITaxiEventListener {
//            C06202() {
//            }
//
//            public void onChangeListCar(ArrayList<TaxiResult> arrayList) {
//            }
//
//            public void onPostOrderResult(String postOrderResult) {
//            }
//
//            public void onGetDriverInfo(String infoResult) {
//            }
//        }

        public DriverAdapter(Context context, Driver[] objects) {
            super(context, -1, objects);
            this.arrDriver = objects;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View rowView = ListDriverDialogFragment.this.getActivity().getLayoutInflater().inflate(R.layout.dialog_list_driver_item, parent, false);
            if (this.arrDriver != null) {
                TextView tvDriverPhone = (TextView) rowView.findViewById(R.id.tv_driver_phone);
                ImageView imvCar = (ImageView) rowView.findViewById(R.id.imv_driver_car);
                ImageView imvPhone = (ImageView) rowView.findViewById(R.id.imv_phone);
                ((TextView) rowView.findViewById(R.id.tv_driver_name)).setText(this.arrDriver[position].getDriverName());
                tvDriverPhone.setText(this.arrDriver[position].getPhoneNumber());
                if (2 == this.arrDriver[position].getCarType()) {
                    imvCar.setImageResource(R.drawable.taxi7_72);
                } else {
                    imvCar.setImageResource(R.drawable.taxi4_72);
                }
                imvPhone.setOnClickListener(new C06191(position));
            }
            return rowView;
        }

        public void onItemClick(int position) {
        }


    }

    public void setDrivers(Driver[] p_driver) {
        this.drivers = p_driver;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_list_driver, null);
        ((ListView) view.findViewById(R.id.lvw_driver)).setAdapter(new DriverAdapter(getActivity(), this.drivers));
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Danh sách lái xe");
        builder.setNegativeButton(R.string.close, new C06181());
        builder.setView(view);
        return builder.create();
    }
}
