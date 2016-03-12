package com.soft.svobo.myrun.DataModel;

import java.util.Comparator;

/**
 * Created by misak on 25. 2. 2016.
 */
public class GpsComparer implements Comparator<GpsPoint> {
    @Override
    public int compare(GpsPoint gpsPoint, GpsPoint t1) {
        return gpsPoint.time.compareTo(t1.time);
    }
}
