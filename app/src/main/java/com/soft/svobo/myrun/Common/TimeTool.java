package com.soft.svobo.myrun.Common;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by misak on 21. 2. 2016.
 */
public class TimeTool {
    public static String convertTime(long time){
        Date date = new Date(time);
        Format format = new SimpleDateFormat("mm:ss");
        return format.format(date);
    }

    public static String convertTime(long time, String fmt){
        Date date = new Date(time);
        Format format = new SimpleDateFormat(fmt);
        return format.format(date);
    }
}
