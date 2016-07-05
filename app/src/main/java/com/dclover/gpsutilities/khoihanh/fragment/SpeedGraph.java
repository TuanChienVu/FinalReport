package com.dclover.gpsutilities.khoihanh.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dclover.gpsutilities.R;
import com.dclover.gpsutilities.khoihanh.ActivityKhoiHanh;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.PointsGraphSeries;
import com.jjoe64.graphview.series.Series;


/**
 * Created by Kinghero on 7/5/2016.
 */
public class SpeedGraph extends Fragment {
    GraphView graph;
    private double graphLastXValue;
    PointsGraphSeries<com.jjoe64.graphview.series.DataPoint> pointSeries;
    LineGraphSeries<DataPoint> series;
    /* renamed from: com.virtualmaze.gpstriptracker.SpeedGraph.1 */
    class C08951 implements OnDataPointTapListener {
        C08951() {
        }

        public void onTap(Series series, DataPointInterface dataPoint) {
            ((ActivityKhoiHanh) SpeedGraph.this.getActivity()).PointClicked(dataPoint.getX());
        }
    }

    /* renamed from: com.virtualmaze.gpstriptracker.SpeedGraph.2 */
    class C08962 implements OnDataPointTapListener {
        C08962() {
        }

        public void onTap(Series series, DataPointInterface dataPoint) {
            ((ActivityKhoiHanh) SpeedGraph.this.getActivity()).PointClicked(dataPoint.getX());
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.graph_fragment, container, false);
        this.graph = (GraphView) rootView.findViewById(R.id.graph);
        InitializeGraph();
        return rootView;
    }

    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public void InitializeGraph() {
        this.graph.getViewport().setXAxisBoundsManual(true);
        this.graph.getViewport().setMinX(0.0d);
        this.graph.getViewport().setMaxX(20.0d);
        this.graph.onDataChanged(false, false);
        this.graph.getViewport().setScrollable(true);
        this.graph.setTitle(getResources().getString(R.string.speedAnalysis));
        this.graph.getGridLabelRenderer().setVerticalAxisTitle("");
        this.graph.getGridLabelRenderer().setHorizontalAxisTitle(getResources().getString(R.string.Time) + " (s)");
        this.graph.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.HORIZONTAL);
        this.graph.getViewport().setScalable(true);
    }

    public void InitializeRealTimeGraph() {
        this.series = new LineGraphSeries();
        this.graph.addSeries(this.series);
        this.series.setOnDataPointTapListener(new C08951());
    }

    public void AssignGraphData(float point, float xAxisValue) {
        this.graphLastXValue += 1.0d;
        this.series.appendData(new DataPoint((double) xAxisValue, (double) point), true, (int) this.graphLastXValue);
        this.graph.getViewport().scrollToEnd();
    }

    public void AssignGraph(DataPoint[] points) {
        if (points.length != 0) {
            this.series = new LineGraphSeries(points);
            this.series.setOnDataPointTapListener(new C08962());
            this.graph.addSeries(this.series);
        }
    }

   public void ResetGraph() {
        this.graph.removeAllSeries();
    }

   public void ResetPoint() {
        this.graph.removeSeries(this.pointSeries);
    }

    public void assign() {
        DataPoint[] points = new DataPoint[50];
        for (int i = 0; i < 50; i++) {
            points[i] = new DataPoint((double) i, (Math.sin(((double) i) * 0.5d) * 20.0d) * ((Math.random() * 10.0d) + 1.0d));
        }
        this.graph.addSeries(new LineGraphSeries(points));
    }
}
