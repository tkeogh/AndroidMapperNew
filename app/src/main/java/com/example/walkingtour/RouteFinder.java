package com.example.walkingtour;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class RouteFinder extends Activity {

    private GoogleMap map;
    String start;
    String end;
    boolean routeFound;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_finder);


        Button choice = (Button) findViewById(R.id.choose);

        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map2))
                .getMap();
        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        LatLng aber = new LatLng(52.4140, -4.0810); // aberystwyth co ordinates
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(aber, 13));
        map.setMyLocationEnabled(true);

        choice.setOnClickListener(

                new View.OnClickListener()

                {

                    @Override
                    public void onClick(View aView) {

                        Intent movepoi = new Intent(aView.getContext(), route_choice.class);
                        startActivityForResult(movepoi, 1); //1 here is the request code used in onresult

                    }
                }
        );


    }


    public void setRoute(Route here, GoogleMap map) {


        Log.i("From", here.getFrom());
        Log.i("To", here.getTo());
        ArrayList<pointof> points = here.getPoints();

        for (int i = 0; i < here.getPoints().size(); i++) {


            pointof curr = points.get(i);

            LatLng Current = new LatLng(curr.getLat(), curr.getLng());

            if (i != points.size() - 1) {

                pointof next = points.get(i + 1);
                LatLng nex = new LatLng(next.getLat(), next.getLng());

                map.addPolyline(new PolylineOptions().add(Current, nex)
                        .width(8).color(Color.RED));

            }

        }

    }

    private ArrayList<Route> reading(String fileName) {
        ArrayList<Route> direct = new ArrayList<Route>();
        Log.i("Opening this file : ", fileName);
        String start = null;
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open(fileName)));
            String mLine = reader.readLine();
            while (mLine != null) {


                if (mLine.startsWith("S")) {
                    start = mLine.substring(1);
                    Log.i("begin", start);

                }

                if (mLine.startsWith("D")) {

                    ArrayList<pointof> these = new ArrayList<pointof>();
                    String end = mLine.substring(1);
                    Route route = new Route(start, end);

                    //Log.i("destination : ",end);

                    int amount = Integer.parseInt(reader.readLine());


                    for (int i = 0; i < amount; i++) {

                        String coords = reader.readLine();
                        coords = coords.substring(1);
                        //  Log.i("co ords : " ,coords);
                        String[] splits = coords.split(",");
                        double lat = Double.parseDouble(splits[0]);
                        double lon = Double.parseDouble(splits[1]);
                        pointof place = new pointof(lat, lon);
                        these.add(place);


                    }
                    route.setPoints(these);
                    direct.add(route);
                }


                mLine = reader.readLine(); //get next line
            }
        } catch (IOException e) {
            //log the exception
        }
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return direct;
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == 1) {
            if (data.getExtras() != null) {

                map.clear();


                // Make sure the request was successful
                Bundle extras = data.getExtras();
                String from = extras.getString("from");
                String end = extras.getString("to");
                routeFound = false;
                plotRoute(from, end);

            }
        }
    }

    public void plotRoute(String from, String to) {
        start = from;
        end = to;
        ArrayList<String> endings = new ArrayList<String>();
        ArrayList<Route> poss = new ArrayList<Route>();
        int tripped = 0;
        ArrayList<Route> reading = reading(from + ".txt");

        for (int i = 0; i < reading.size(); i++) {
            String destination = reading.get(i).getTo();
            endings.add(destination);
            Log.i("Added this to endings: ", destination);
            if (destination.equals(end)) {

                setRoute(reading.get(i), map);
                tripped = 1;
            }

        }

        if (tripped == 0) {


            //Toast toast3 = Toast.makeText(getApplicationContext(), "We're Sorry this Route isnt available right now",Toast.LENGTH_SHORT);
            //toast3.show();
            for (int i = 0; i < endings.size(); i++) {
                poss.clear();
                Log.i("Poss Files:", endings.get(i) + ".txt");
                poss = reading(endings.get(i) + ".txt");
                for (int j = 0; j < poss.size(); j++) {

                    Log.i("Chance:", poss.get(j).getTo());
                    if (poss.get(j).getTo().equalsIgnoreCase(end)) {
                        setRoute(poss.get(j), map);
                        String newend = poss.get(j).getFrom();
                        for (int k = 0; k < reading.size(); k++) {
                            Route m = reading.get(k);
                            if (m.getTo().equalsIgnoreCase(newend)) {
                                setRoute(m, map);
                                return;
                            }

                        }

                    }

                }

            }
            Toast toast3 = Toast.makeText(getApplicationContext(), "No Connecting within 1", Toast.LENGTH_SHORT);
            toast3.show();
        }
    }


    public void findPath(ArrayList<String> endings) {


    }
}


