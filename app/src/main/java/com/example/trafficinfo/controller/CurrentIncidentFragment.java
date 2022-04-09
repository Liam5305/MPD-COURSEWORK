package com.example.trafficinfo.controller;

// Author: Liam Rutherford
// Matriculation Number: S2024208

import android.content.Context;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import com.example.trafficinfo.R;
import com.example.trafficinfo.models.RSSItem;
import com.example.trafficinfo.ui.FeedDownload;
import com.example.trafficinfo.ui.XMLParser;

import java.io.IOException;
import java.util.ArrayList;

public class CurrentIncidentFragment extends Fragment {

    private FeedDownload io;
    private XMLParser parse;
    private TextView title;
    private TextView description;
    private TextView link;
    private TextView georss;
    private TextView pubDate;
    private ArrayList<RSSItem> itemsView;
    private String URL_STRING = "https://trafficscotland.org/rss/feeds/currentincidents.aspx";
    public CurrentIncidentFragment(){}
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onSaveInstanceState(savedInstanceState);
    }
    @Override
    public void onAttach(Context context){
        super.onAttach(context);
    }
    @Override
    public void onDetach() {
        super.onDetach();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_current_incident, container, false);

        io = new FeedDownload();
        parse = new XMLParser();

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Traffic Scotland - Current Incidents");
        title = (TextView) view.findViewById(R.id.titleTextView);
        description = (TextView) view.findViewById(R.id.descriptionTextView);
        link = (TextView) view.findViewById(R.id.linkTextView);
        georss = (TextView) view.findViewById(R.id.geoTextView);
        pubDate = (TextView) view.findViewById(R.id.pubDateTextView);

        new DownloadFeed().execute(URL_STRING);

        return view;
    }
    class DownloadFeed extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String in = null;
            try {
                in = io.downloadFile(params[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return in;
        }
        @Override
        protected void onPostExecute(String result) {
            Log.d("reader", "Feed downloaded");
            new ReadFeed().execute(result);
        }
    }

    class ReadFeed extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            String s = params[0];
            parse.pullParser(s);
            itemsView = parse.getItems();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Log.d("Roadworks reader", "Feed read");

            CurrentIncidentFragment.this.updateDisplay();
        }
    }
    public void updateDisplay()
    {
        if (itemsView == null) {
            Context context = getActivity().getApplicationContext();
            CharSequence text ="Unable to get RSS feed";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return;
        }
        for (RSSItem it : itemsView) {
            title.setText( "Title: " + it.getTitle());
            description.setText( "Description: " + it.getDescription());
            link.setText( "Link: " + it.getLink());
            georss.setText( "Coordinates: " + it.getGeorss());
            pubDate.setText( "Publication Date: " + it.getPubDate());
        }
    }
}