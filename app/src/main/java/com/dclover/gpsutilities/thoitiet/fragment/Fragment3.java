package com.dclover.gpsutilities.thoitiet.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dclover.gpsutilities.R;

/**
 * Created by Kinghero on 7/5/2016.
 */
public class Fragment3 extends Fragment {
    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag3, container, false);
        getActivity().setTitle("About");
        TextView feedback = (TextView) view.findViewById(R.id.gmail_tv);
        feedback.setText(Html.fromHtml("<a href=\"mailto:vishwakarmasunil68@gmail.com\">vishwakarmasunil68@gmail.com</a>"));
        feedback.setMovementMethod(LinkMovementMethod.getInstance());
        return view;
    }
}
