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
                start = extras.getString("from");
                 end = extras.getString("to");
                if (start.equalsIgnoreCase(end)) {
                    Toast toast4 = Toast.makeText(getApplicationContext(), "Your destination and start are the same", Toast.LENGTH_SHORT);
                    toast4.show();
                    return;
                }
                routeFound = false;
                plotRoute(start, end);

            }
        }
    }

    public void plotRoute(String from, String to) {
        String here = from;
        String dest = to;
        ArrayList<String> endings = new ArrayList<String>();

        int tripped = 0;
        ArrayList<Route> reading = reading(from + ".txt");

        for (int i = 0; i < reading.size(); i++) {
            String destination = reading.get(i).getTo();
            endings.add(destination);
            Log.i("Added this to endings: ", destination);
            if (destination.equals(dest)) {

                setRoute(reading.get(i), map);
                tripped = 1;
            }

        }

        if (tripped == 0) {

            findPath(endings, reading);


        }
    }


    public void findPath(ArrayList<String> endings, ArrayList<Route> reading) {
        boolean missingroute = true;
        String last = start;


        Log.i("going from", start);
        Log.i("going to", end);

        ArrayList<String> visited = new ArrayList<String>();
        ArrayList<Route> processed = new ArrayList<Route>();


        visited.add(start);


        while (missingroute) {

            ArrayList<Route> possRoutes = new ArrayList<Route>(); //ArrayList.clear() breaks everything.
            ArrayList<String> nextfiles = new ArrayList<String>();

            for (int i = 0; i < endings.size(); i++) {


                if (visited.contains(endings.get(i))) {

                } else {
                    visited.add(endings.get(i));
                    Log.i("Added to visited : ",endings.get(i));
                    Log.i("test ",Integer.toString(endings.size()));


                    possRoutes = reading(endings.get(i) + ".txt");
                    //visited.add(endings.get(i));
                    for (int j = 0; j < possRoutes.size(); j++) {
                        Route curr = possRoutes.get(j);

                        if (processed.contains(curr)) {

                        } else {
                            processed.add(curr);
                            Log.i("added route ",curr.getFrom() + " " +curr.getTo());
                        }

                        if (!nextfiles.contains(curr.getTo())&&!visited.contains(curr.getTo())) {
                            nextfiles.add(curr.getTo());
                            Log.i("added to next files: ", curr.getTo());

                        }


                        Log.i("getting pst logic","");


                        if (end.equalsIgnoreCase(curr.getTo())) {
                            Log.i("found route", curr.getFrom() + " " + curr.getTo());

                            missingroute = false;
                            plotPath(processed);
                            return;


                        }
                        if (!missingroute) {
                            return;
                        }
                    }
                    if (!missingroute) {
                        return;
                    }
                }
                if (!missingroute) {
                    return;
                }



            }
            endings = nextfiles;
            if (!missingroute) {
                return;
            }
        }



    }

    public void plotPath(ArrayList<Route> routes) {

        Log.i("hit plot path", "sfd");

        for (int i = 0; i < routes.size(); i++) {
            Log.i("frmo " + routes.get(i).getFrom(), " to " + routes.get(i).getTo());
        }
    }

}


