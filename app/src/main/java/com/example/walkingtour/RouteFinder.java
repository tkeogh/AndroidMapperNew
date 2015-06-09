package com.example.walkingtour;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

import com.example.walkingtour.Data.Node;
import com.example.walkingtour.Data.Route;
import com.example.walkingtour.Data.locations;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class RouteFinder extends Activity {

    private GoogleMap map;
    String start;
    String end;
    boolean routeFound;
    String type;

    public RouteFinder() {

    }


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
        Intent intent = getIntent();
        type = intent.getStringExtra("type");

        choice.setOnClickListener(

                new View.OnClickListener()

                {

                    @Override
                    public void onClick(View aView) {

                        Intent getchoice = new Intent(aView.getContext(), route_choosing.class);
                        getchoice.putExtra("type", type);
                        startActivityForResult(getchoice, 1); //1 here is the request code used in onresult

                    }
                }
        );


    }


    /**
     * Takes a rotue object and paints the path dictated by it
     *
     * @param here the route to be set
     * @param map map object
     */
    public void setRoute(Route here, GoogleMap map) {


        Log.i("From", here.getFrom());
        Log.i("To", here.getTo());
        ArrayList<locations> points = here.getPoints();

        for (int i = 0; i < here.getPoints().size(); i++) {


            locations curr = points.get(i);

            LatLng Current = new LatLng(curr.getLat(), curr.getLon());

            if (i != points.size() - 1) {// if not last point

                locations next = points.get(i + 1);//get next location
                LatLng nex = new LatLng(next.getLat(), next.getLon());


                map.addPolyline(new PolylineOptions().add(Current, nex)
                        .width(8).color(Color.parseColor(here.getDifficulty())));//paint the line

            }

        }

    }

    /**
     * read in the routes from a file
     * @param fileName the file name of the represented node
     * @return return the routes held in the file
     */
    public ArrayList<Route> reading(String fileName) {
        ArrayList<Route> direct = new ArrayList<Route>();
        //Log.i("Opening this file : ", fileName);
        String start = null;
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open(type + "/" + fileName)));//open frm right folder
            String mLine = reader.readLine();
            while (mLine != null) {


                if (mLine.startsWith("S")) {//if start
                    start = mLine.substring(1);


                }

                if (mLine.startsWith("D")) {//if a destination


                    ArrayList<locations> these = new ArrayList<locations>();
                    String end = mLine.substring(1);//remove first letter "D"
                    Route route = new Route(start, end);

                    //Log.i("destination : ",end);
                    String rating = reader.readLine();
                    route.setDifficulty(rating);


                    int amount = Integer.parseInt(reader.readLine());//amount of nodes


                    //load in files co ordinates along the route
                    for (int i = 0; i < amount; i++) {

                        String coords = reader.readLine();
                        coords = coords.substring(1);
                        //  Log.i("co ords : " ,coords);
                        String[] splits = coords.split(",");
                        double lat = Double.parseDouble(splits[0]);
                        double lon = Double.parseDouble(splits[1]);
                        locations place = new locations(lat, lon);
                        these.add(place);


                    }
                    route.setPoints(these);
                    direct.add(route);//add the finished route
                }


                mLine = reader.readLine(); //get next line
            }
        } catch (IOException e) {
            // Toast toast5 = Toast.makeText(getApplicationContext(), "Could Not Find File", Toast.LENGTH_SHORT);
            //toast5.show();
        }
        try {
            reader.close();
        } catch (IOException e) {
            // Toast toast5 = Toast.makeText(getApplicationContext(), "Could Not Find File", Toast.LENGTH_SHORT);
            // toast5.show();
        }

        return direct;
    }


    /**Handles the starting of route finding based on user request
     *
     * @param requestCode what went out as a request
     * @param resultCode how did the process finish
     * @param data the data returned
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        Log.i("requiest code is " + Integer.toString(requestCode), " result is " + resultCode);
        if (requestCode == 1) {//requestCode was set when starting the other activity. In this case 1 was route finding.
            if (resultCode == RESULT_OK) {//Equates to -1
                if (data.getExtras() != null) {

                    map.clear();


                    // Make sure the request was successful
                    Bundle extras = data.getExtras();
                    start = extras.getString("from");
                    end = extras.getString("to");
                    if (start.equalsIgnoreCase(end)) {//error check
                        Toast toast4 = Toast.makeText(getApplicationContext(), "Your destination and start are the same", Toast.LENGTH_SHORT);
                        toast4.show();
                        return;
                    }
                    routeFound = false;
                    plotRoute(start, end); //Start plotting the route requested

                }
            }
            if (resultCode == RESULT_CANCELED) {//Equates to 0
                //Stops the app crashing if the user just wants to go back a screen.

            }
        }
    }

    /** handles finding connceting start end, if not deaults into the BFS
     *
     * @param from START location
     * @param to end location
     */

    public void plotRoute(String from, String to) {
        String here = from;
        String dest = to;
        ArrayList<String> endings = new ArrayList<String>();

        int tripped = 0; //Keep track of if the routes found
        ArrayList<Route> reading = reading(here + ".txt"); //Reads in routes from a text file, all follow naming convention

        for (int i = 0; i < reading.size(); i++) {
            String destination = reading.get(i).getTo();
            endings.add(destination);//Add all possible endings from this point for possible later use.
            Log.i("Added this to endings: ", destination);
            if (destination.equals(dest)) { //found a route in first file.

                setRoute(reading.get(i), map);//Pass in  a route and the map object
                tripped = 1;
            }

        }

        if (tripped == 0) {

            ArrayList<Node> nodes = findPath(endings);//Route isnt in the first file, find a path.
            plotPath(nodes); //plot that path


        }
    }


    /**
     * BFS for finding the route
     * @param endings the starting nodes connecting ndoes
     * @return the path as an array of nodes
     */
    public ArrayList<Node> findPath(ArrayList<String> endings) {
        boolean missingroute = true;

        Log.i("going from", start);
        Log.i("going to", end);

        ArrayList<String> visited = new ArrayList<String>();
        ArrayList<Route> processed = new ArrayList<Route>();
        ArrayList<Node> fileInfo = new ArrayList<Node>();
        ArrayList<String> nextfiles = new ArrayList<String>();

        visited.add(start);


        while (missingroute) {

            ArrayList<Route> possRoutes = new ArrayList<Route>(); //ArrayList.clear() breaks everything.


            for (int i = 0; i < endings.size(); i++) {


                if (visited.contains(endings.get(i))) {
                    //If we've already scanned this file we dont need to check it again.
                } else {
                    visited.add(endings.get(i));
                    //Node n = new Node(last,endings.get(i));
                    Log.i("Added to visited : ", endings.get(i));
                    Log.i("test ", Integer.toString(endings.size()));


                    possRoutes = reading(endings.get(i) + ".txt");
                    //visited.add(endings.get(i));
                    for (int j = 0; j < possRoutes.size(); j++) {
                        Route curr = possRoutes.get(j);
                        //Stops weird errors with analysis the same route a lot. Shouldnt be needed, scared to take out.
                        if (processed.contains(curr)) {

                        } else {
                            processed.add(curr);
                            Log.i("added route ", curr.getFrom() + " " + curr.getTo());
                        }

                        if (!nextfiles.contains(curr.getTo()) && !visited.contains(curr.getTo())) {
                            Node b = new Node(curr.getFrom(), curr.getTo());
                            fileInfo.add(b);//Logging which file is opened from where, track back.
                            nextfiles.add(curr.getTo());
                            Log.i("added to next files: ", curr.getTo());

                        }


                        Log.i("getting pst logic", "");


                        if (end.equalsIgnoreCase(curr.getTo())) { //weve found the last file
                            Log.i("found route", curr.getFrom() + " " + curr.getTo());

                            missingroute = false;


                            return fileInfo;//return the final analysed nodes


                        }

                    }

                }


            }
            endings = nextfiles;//new set of ndoes to analyse

        }

        return fileInfo;
    }


    /** takes the nodes and plots a path by searching backwards
     *
     * @param nodes the analysed nodes in the graph
     */
    public void plotPath(ArrayList<Node> nodes) {

        Log.i("hit plot path", "sfd");
        int counter = 0;
        String search = end;

        Collections.reverse(nodes); //so we can start at the end easily
        for (int i = 0; i < nodes.size(); i++) {
            for (int j = 0; j < nodes.size(); j++) {
                if (search.equalsIgnoreCase(nodes.get(j).getName())) {
                    plotRoute(nodes.get(j).getFrom(), nodes.get(j).getName());//makes it right colour
                    search = nodes.get(j).getFrom();//now searching the node which opened the current
                }

            }

        }
        //plot from start to the last analysed
        plotRoute(start, search);
    }

}


