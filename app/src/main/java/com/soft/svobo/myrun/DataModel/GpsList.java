package com.soft.svobo.myrun.DataModel;

import com.soft.svobo.myrun.Common.TimeTool;
import com.soft.svobo.myrun.LocationTools.GpsTools;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

/**
 * Created by misak on 3. 1. 2016.
 */
public class GpsList extends ArrayList<GpsPoint>  {
    public void calculateSplits(){
        GpsPoint lastPoint =null;
        for(int i = 0; i < size();i++) {
            GpsPoint pt = get(i);
            if (lastPoint != null) {
                if (pt.time!=null)
                    pt.split = pt.time.getTime() - lastPoint.time.getTime();
                pt.splitlen = GpsTools.distFrom(pt.lat,pt.lon, lastPoint.lat, lastPoint.lon );
            }
            lastPoint = pt;
        }
    }

    public long getTotalTime()
    {
        if (size() == 0)
            return 0;

        GpsPoint pt1 = get(0);
        GpsPoint pt2 = get(size()-1);

        return (pt2.time.getTime()-pt1.time.getTime());
    }

    public String getTotalTimeStr()
    {
        return TimeTool.convertTime(getTotalTime());
    }

    public double getTotalLen()
    {
        double total = 0;
        for(int i = 0; i < size();i++) {
            GpsPoint pt = get(i);

            total+=pt.splitlen;
        }
        return total;
    }

    public String getTotalLenStr()
    {
        DecimalFormat ft = new DecimalFormat("###.##");
        return ft.format(getTotalLen());
    }

    public void Sort()
    {
        Collections.sort(this, new GpsComparer());
    }
}
