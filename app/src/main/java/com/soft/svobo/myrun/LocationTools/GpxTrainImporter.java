package com.soft.svobo.myrun.LocationTools;

import com.soft.svobo.myrun.DataModel.GpsList;
import com.soft.svobo.myrun.DataModel.GpsPoint;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.xml.xpath.XPathExpressionException;

/**
 * Created by misak on 3. 3. 2016.
 */
public class GpxTrainImporter {
    String _fname;
    XmlPullParser xmlParser;
    String currEl;
    String currText;
    GpsList pointList = null;
    GpsPoint actPoint=null;
    GpsPoint firstPoint=null;
    GpsPoint lastPoint=null;

    public GpxTrainImporter(String fname)
    {
        super();
        _fname = fname;
        pointList = new GpsList();
    }

    public void ImportRoute() throws IOException, XPathExpressionException, XmlPullParserException
    {

        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser xpp = factory.newPullParser();
        xmlParser = xpp;

        xpp.setInput(new FileReader(_fname));
        int eventType = xpp.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_DOCUMENT) {
                //System.out.println("Start document");
            }
            else if (eventType == XmlPullParser.START_TAG) {
                currEl = xmlParser.getName();
                onStartElement();
            } else if (eventType == XmlPullParser.END_TAG) {
                currEl = xmlParser.getName();
                if (!onEndElement())
                    return;
            } else if (eventType == XmlPullParser.TEXT) {
                currText = xpp.getText();
                if (!xmlParser.isWhitespace()) {
                    onText(xpp.getText());
                }
                System.out.println("Text " + xpp.getText());
            }
            eventType = xpp.next();
        }

        if (lastPoint !=null)
            pointList.add(lastPoint);

        pointList.Sort();
    }

    private  void onText(String text)
    {
        try {
            if (actPoint==null)
                return;

            if ("ele".equals(currEl) && actPoint != null) {
                actPoint.ele = Double.valueOf(text);
            } else if ("name".equals(currEl) && actPoint != null) {
                actPoint.name = text;
            } else if ("time".equals(currEl) && actPoint != null) {
                actPoint.timeStr = text;
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                actPoint.time = format.parse(text);
            }
        }
        catch(Exception e) {

        }
    }

    public GpsList getList()
    {
        return pointList;
    }

    protected void onStartElement() {
        if ("trk".equals(currEl))
        {
            actPoint = null;
        }
        else if ("trkpt".equals(currEl))
        {
            String lat = "";
            String lon = "";

            for(int i = 0, n = this.xmlParser.getAttributeCount(); i < n; ++i) {
                String att_name = this.xmlParser.getAttributeName(i);

                if (att_name.equals("lat")) {
                    lat = this.xmlParser.getAttributeValue(i);
                }
                if (att_name.equals("lon")) {
                    lon = this.xmlParser.getAttributeValue(i);
                }
            }

            GpsPoint new_point = new GpsPoint();
            new_point.lat = Double.valueOf(lat);
            new_point.lon = Double.valueOf(lon);
            new_point.name = String.valueOf(pointList.size());
            pointList.add(new_point);
            firstPoint = new_point;
            actPoint = new_point;

        }
    }

    protected boolean onEndElement()
    {

        return true;
    }
}
