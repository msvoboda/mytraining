package com.soft.svobo.mytraining.DataModel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.soft.svobo.mytraining.R;

import java.util.List;

/**
 * Created by misak on 4. 1. 2016.
 */
public class GpsListAdapter extends ArrayAdapter<GpsPoint>
{
    public GpsListAdapter(Context context, int resource, List<GpsPoint> objects) {
        super(context, resource, objects);
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

        return convertView;
    }
}
