package com.example.trafficinfo.ui;
// Author: Liam Rutherford
// Matriculation Number: S2024208
import android.util.Log;

import com.example.trafficinfo.models.RSSItem;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

public class XMLParser {
    RSSItem item;
    private ArrayList<RSSItem> items;

    public ArrayList<RSSItem> getItems() {
        return items;
    }

    public void pullParser(String reader)
    {
        items = new ArrayList<RSSItem>();
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new StringReader(reader));
            int eventType = xpp.getEventType();
            DataFormat help = new DataFormat();
            while(eventType != XmlPullParser.END_DOCUMENT) {
                if(eventType == XmlPullParser.START_DOCUMENT) {
                    System.out.println("Start Document");
                }
                else if(eventType == XmlPullParser.START_TAG){
                    if(xpp.getName().equals("item")) {
                        item = new RSSItem();
                        eventType = xpp.nextTag();
                        if(xpp.getName().equals("title")) {
                            eventType = xpp.next();
                            item.setTitle(xpp.getText());
                            eventType = xpp.nextTag();
                            eventType = xpp.nextTag();
                        }
                        if(xpp.getName().equals("description")){
                            eventType = xpp.next();
                            item.setDescription(help.getDescription(xpp.getText()));
                            String[] dates = help.getDates(xpp.getText());

                            if(dates != null) {
                                item.setStartDate(help.convertLongDateToShort(dates[0]));
                                item.setEndDate(help.convertLongDateToShort(dates[1]));
                            }
                            eventType = xpp.nextTag();
                            eventType = xpp.nextTag();
                        }
                        if(xpp.getName().equals("link")) {
                            eventType = xpp.next();
                            item.setLink(xpp.getText());
                            eventType = xpp.nextTag();
                            eventType = xpp.nextTag();
                        }
                        if(xpp.getName().equals("point")) {
                            eventType = xpp.next();
                            item.setGeorss(xpp.getText());
                            eventType = xpp.nextTag();
                            eventType = xpp.nextTag();
                        }
                        eventType = xpp.nextTag();
                        eventType = xpp.nextTag();
                        eventType = xpp.nextTag();
                        eventType = xpp.nextTag();

                        if(xpp.getName().equals("pubDate")) {
                            eventType = xpp.next();
                            item.setPubDate(xpp.getText());
                            Log.v("check data", xpp.getText());
                            eventType = xpp.nextTag();
                            eventType = xpp.nextTag();
                        }
                        items.add(item);
                    }
                }
                eventType = xpp.next();
            }
        } catch (XmlPullParserException e) {
            Log.e("MyTag", "Parsing error" + e);
        } catch (IOException ae1) {
            Log.e("MyTag", "IO error during parsing");
        }
        Log.e("XMLParser","End of document");
    }

}