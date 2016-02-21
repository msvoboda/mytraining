package com.soft.svobo.myrun.DataModel;

import java.util.Date;

/**
 * Created by misak on 3. 1. 2016.
 */
public class GpsPoint
{
    public GpsPoint()
    {
        split = 0;
    }

    public String name;
    public double lat;
    public double lon;
    public Date time;
    public String timeStr;
    public double split; //(seconds)
    public double ele;
}
