package com.example.walkingtour;


import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.*;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.PopupMenu;

import com.example.walkingtour.Data.Distance;
import com.example.walkingtour.Data.locations;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;


/**
 * Handles retrieving, adding to and sending of the map information.
 *
 * @author ww3ref
 */

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class map extends Activity implements LocationListener {
    Double totalDist = 0.0;
    Distance d = new Distance();

    private ArrayList<locations> info = new ArrayList<locations>();


    //positions


    private final static int POI_CHANGE = 1; //constant used for returning from other activity.


    private Location location; //current user location
    private GoogleMap map; //map to use

    String start;
    String end; //variables from previous screen

    String desire;

    ArrayList<locations> points = new ArrayList<locations>(); //array list for all POI

    /**
     * Sets map and gets current user location. Then sets the methods for the buttons.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);


        final Button opt = (Button) findViewById(R.id.cancel);
        final Button see = (Button) findViewById(R.id.see);
        NumberPicker np = (NumberPicker) findViewById(R.id.number_picker);


        map = ((MapFragment) getFragmentManager()
                .findFragmentById(R.id.map)).getMap();  //retrieve the map fragment to be used.

        LatLng aber = new LatLng(52.4140, -4.0810); //aberystwyth co ordinates
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(aber, 13));
        map.setMyLocationEnabled(true); //Show users current location, blue dot on map.
        final LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER); //GET USER LOCATION HERE.

        Intent intent = getIntent(); // can now reference last screen

        start = intent.getStringExtra("start");
        end = intent.getStringExtra("end"); //add in the information from previous screen using name/value pairs

        desire = intent.getStringExtra("want");

        if (desire.equals("help")) {
            map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
            locations l = new locations();
            opt.setVisibility(View.GONE);
            see.setVisibility(View.VISIBLE);
            np.setVisibility(View.GONE);

            info = l.populate();


            see.setOnClickListener(

                    new View.OnClickListener()

                    {

                        @Override
                        public void onClick(final View aView) {

                            //call show dialog method, asks if user is sure or not to cancel.
                            PopupMenu popup = new PopupMenu(aView.getContext(), see);
                            //Inflating the Popup using xml file
                            popup.getMenuInflater().inflate(R.menu.view_menu, popup.getMenu());

                            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                public boolean onMenuItemClick(MenuItem item) {

                                    if (item.getTitle().equals("Lecture Buildings")) {
                                        map.clear();
                                        showByType("lecture");


                                    }

                                    if (item.getTitle().equals("Facilities")) {
                                        map.clear();
                                        showByType("facilities");


                                    }
                                    if (item.getTitle().equals("Accomodation")) {
                                        map.clear();
                                        showByType("accomodation");
                                    }

                                    return true;
                                }
                            });
                            popup.show();
                        }
                    }
            );


        }

        if (desire.equals("routing")) {



            opt.setVisibility(View.VISIBLE);
            np.setVisibility(View.VISIBLE);
            see.setVisibility(View.GONE);

            np.setMinValue(1);// restricted number to minimum value i.e 1
            np.setMaxValue(50);

            np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener()
            {

                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal)
                {

                    // TODO Auto-generated method stub

                   Log.i("value is",Integer.toString(newVal));

                }
            });

            opt.setOnClickListener(

                    new View.OnClickListener()

                    {

                        @Override
                        public void onClick(final View aView) {

                            //call show dialog method, asks if user is sure or not to cancel.
                            PopupMenu popup = new PopupMenu(aView.getContext(), opt);
                            //Inflating the Popup using xml file
                            popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());

                            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                public boolean onMenuItemClick(MenuItem item) {

                                    if (item.getTitle().equals("Log Point")) {

                                        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                                        locations point = new locations(location.getLatitude(), location.getLongitude());
                                        points.add(point);
                                        if (points.size() > 1) {
                                            locations lastPoint = points.get(points.size()-2);
                                            paintRoute(lastPoint, point);
                                            totalDist = totalDist + d.distanceTo(lastPoint.getLat(),lastPoint.getLon(),
                                                    point.getLat(),point.getLon());
                                            Log.i("distance",Double.toString(totalDist));

                                        }


                                    }

                                    if (item.getTitle().equals("Cancel")) {
                                        showDialog();
                                    }
                                    if (item.getTitle().equals("Create Route")) {

                                        createFile(points);


                                    }

                                    return true;
                                }
                            });
                            popup.show();
                        }
                    }
            );
        }

        //Log.i("walk", "name:"+walkname+ shortdes+ longdes); TEST METHOD


    }


    /**
     * On the return to screen from adding a poi runs the code inside the method. Adds poi to map and data storage.
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    /**
     * Shows a dialog that either clears your walk and map or does nothing.
     */
    private void showDialog() {

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() { //new dialog
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        map.clear();    //clear walk
                        points.clear();//clear data structure
                        totalDist = 0.0;
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to cancel your walk?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();  //setting variables within the dialog
    }

    /**
     * All of these have to be implemented : /
     */

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    private void paintRoute(locations a, locations b) {

        LatLng first = new LatLng(a.getLat(), a.getLon());
        LatLng second = new LatLng(b.getLat(), b.getLon());

        map.addPolyline(new PolylineOptions().add(first, second)
                .width(8).color(Color.RED));

    }

    private void createFile(ArrayList<locations> points) {
        String fileName = start + ".txt";
        Log.i("filen ", fileName);

        String begin = "S" + start+"\n";
        String dest = "D" + end+"\n";
        Log.i("filen ", begin);
        Log.i("filen ", dest);
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/saved_routes");
        if(myDir.exists()){

        }
        else{
            myDir.mkdirs();
        }
        File file = new File (myDir, fileName);
        Log.i("dir",myDir.toString());



        try {
            FileOutputStream outputStream = new FileOutputStream(file);


            outputStream.write(begin.getBytes());
            outputStream.write(dest.getBytes());
            outputStream.write("YELLOW".getBytes());
            outputStream.write("\n".getBytes());
            outputStream.write(Integer.toString(points.size()).getBytes());
            outputStream.write("\n".getBytes());
            for(int i=0;i<points.size();i++){
                String line = "P"+points.get(i).getLat()+","+points.get(i).getLon()+"\n";
                Log.i("line ,",line);

                outputStream.write(line.getBytes());
            }

            outputStream.close();
            Log.i("close", "");
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("error","");
        }


    }

    public void showByType(String type){

        int size = info.size();

        for (int i = 0; i < size; i++) {

            locations a = info.get(i);

            if (a.getType().equals(type)) {

                LatLng pos = new LatLng(a.getLat(), a.getLon());
                String resource ="R.drawable."+type;


                Marker j = map.addMarker(new MarkerOptions()
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.accomodation))
                                .anchor(0.0f, 1.0f)
                                .position(pos)    //add a marker to map using passed in data
                                .title(a.getName())
                                .snippet(a.getDes())

                );


            }

        }

    }

}