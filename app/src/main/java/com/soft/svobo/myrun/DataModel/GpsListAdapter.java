package com.soft.svobo.myrun.DataModel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.soft.svobo.myrun.R;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by misak on 4. 1. 2016.
 */
public class GpsListAdapter extends ArrayAdapter<GpsPoint>
{
    List<GpsPoint> _points;

    public GpsListAdapter(Context context, int resource, List<GpsPoint> objects) {
        super(context, R.layout.point_layout, objects);
        _points = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        GpsPoint point = getItem(position);
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.point_layout, parent, false);

        TextView ptName = (TextView) convertView.findViewById(R.id.pointName);
        if (ptName != null) {
            ptName.setText(point.name);
        }
        /*
        TextView ptTime = (TextView) convertView.findViewById(R.id.pointTime);
        if (ptTime != null && point.timeStr!=null) {
            ptTime.setText(point.timeStr);
        }*/

        TextView ptSplit = (TextView) convertView.findViewById(R.id.pointSplit);
        if (ptSplit!=null) {
            double val = (point.split/(double)60000);
            DecimalFormat f = new DecimalFormat("##.00");
            ptSplit.setText(f.format(val));
        }
        return convertView;
   }
}
