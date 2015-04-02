package com.example.walkingtour;


import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.*;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.*;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


/**
 * Handles retrieving, adding to and sending of the map information.
 *
 * @author ww3ref
 */

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class map extends Activity implements LocationListener {

    private ArrayList<locations> info = new ArrayList<locations>();


    //positions


    private final static int POI_CHANGE = 1; //constant used for returning from other activity.


    private static final String URL_UPLOAD = "http://users.aber.ac.uk/elk10/group3/create_tour.php";
    private static final String POI_UPLOAD = "http://users.aber.ac.uk/elk10/group3/location.php";
    private static final String DES_UPLOAD = "http://users.aber.ac.uk/elk10/group3/description.php"; //constant strings used for uploads.
    private static final String IMG_UPLOAD = "http://users.aber.ac.uk/jmj12/groupproject/photo.php";

    private Location location; //current user location
    private GoogleMap map; //map to use

    String walkname;
    String shortdes; //variables from previous screen
    String longdes;
    String desire;

    ArrayList<pointof> markers = new ArrayList<pointof>(); //array list for all POI

    /**
     * Sets map and gets current user location. Then sets the methods for the buttons.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);


        final Button opt = (Button) findViewById(R.id.cancel);
        final Button see = (Button) findViewById(R.id.see);


        map = ((MapFragment) getFragmentManager()
                .findFragmentById(R.id.map)).getMap();  //retrieve the map fragment to be used.

        LatLng aber = new LatLng(52.4140, -4.0810); //aberystwyth co ordinates
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(aber, 13));
        map.setMyLocationEnabled(true); //Show users current location, blue dot on map.
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER); //GET USER LOCATION HERE.

        Intent intent = getIntent(); // can now reference last screen

        walkname = intent.getStringExtra("walkn");
        shortdes = intent.getStringExtra("sdesc"); //add in the information from previous screen using name/value pairs
        longdes = intent.getStringExtra("ldesc");

        desire = intent.getStringExtra("want");

        if (desire.equals("help")) {
            map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
            locations l = new locations();
            opt.setVisibility(View.GONE);
            see.setVisibility(View.VISIBLE);

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
                                        int size = info.size();

                                        for (int i = 0; i < size; i++) {

                                            locations a = info.get(i);

                                            if (a.getType().equals("Lecture")) {

                                                LatLng pos = new LatLng(a.getLat(), a.getLon());

                                                Marker j = map.addMarker(new MarkerOptions()
                                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.lecture))
                                                        .anchor(0.0f, 1.0f)
                                                        .position(pos)    //add a marker to map using passed in data
                                                        .title(a.getName())
                                                        .snippet(a.getDes()));

                                            }

                                        }


                                    }

                                    if (item.getTitle().equals("Facilities")) {
                                        map.clear();
                                        int size = info.size();

                                        for (int i = 0; i < size; i++) {

                                            locations a = info.get(i);

                                            if (a.getType().equals("Facilities")) {

                                                LatLng pos = new LatLng(a.getLat(), a.getLon());

                                                Marker j = map.addMarker(new MarkerOptions()
                                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.facil))
                                                        .anchor(0.0f, 1.0f)
                                                        .position(pos)    //add a marker to map using passed in data
                                                        .title(a.getName())
                                                        .snippet(a.getDes()));

                                            }

                                        }


                                    }
                                    if (item.getTitle().equals("Accomodation")) {
                                        map.clear();
                                        int size = info.size();

                                        for (int i = 0; i < size; i++) {

                                            locations a = info.get(i);

                                            if (a.getType().equals("Accomodation")) {

                                                LatLng pos = new LatLng(a.getLat(), a.getLon());

                                                Marker j = map.addMarker(new MarkerOptions()
                                                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.house))
                                                                .anchor(0.0f, 1.0f)
                                                                .position(pos)    //add a marker to map using passed in data
                                                                .title(a.getName())
                                                                .snippet(a.getDes())

                                                );


                                            }

                                        }


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
            see.setVisibility(View.GONE);

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

                                    if (item.getTitle().equals("Add Info")) {

                                        Intent movepoi = new Intent(aView.getContext(), point.class);
                                        startActivityForResult(movepoi, POI_CHANGE);

                                    }

                                    if (item.getTitle().equals("Cancel")) {
                                        showDialog();
                                    }
                                    if (item.getTitle().equals("Upload")) {

                                        new Upload().execute();
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


    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onProviderDisabled(String arg0) {
        // TODO Auto-generated method stub

    }                                                          //Unused as such, could be used for telling user about network issues.

    @Override
    public void onProviderEnabled(String arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
        // TODO Auto-generated method stub

    }

    /**
     * On the return to screen from adding a poi runs the code inside the method. Adds poi to map and data storage.
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == POI_CHANGE) {
            // Make sure the request was successful
            Bundle extras = data.getExtras();
            if (extras != null) { //if we have extras
                LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER); //get new location

                pointof add = new pointof(); //new pointof to be used to store

                String name = extras.getString("mname");
                String description = extras.getString("mdesc");
                double lat = location.getLatitude();    //get data needed from extras and locaion manager
                double lng = location.getLongitude();
                LatLng pos = new LatLng(lat, lng); //create new latlng object for use

                Marker j = map.addMarker(new MarkerOptions()
                        .position(pos)    //add a marker to map using passed in data
                        .title(name)
                        .snippet(description));

                if (extras.getString("photopath") != null) {  //IF AN IMAGE HAS BEEN CHOSEN
                    String path = extras.getString("photopath");
                    Bitmap bmp = BitmapFactory.decodeFile(path);
                    Bitmap scaled = Bitmap.createScaledBitmap(bmp, 120, 120, true); //turn it into a scaled bitmap
                    j.setIcon(BitmapDescriptorFactory.fromBitmap(scaled));  //set it as icon and add it to the data structure.
                    add.setImg(scaled);
                }


                add.setDescription(description);
                add.setName(name);
                add.setLat(lat);
                add.setLng(lng);  //add all data to object to be put in data structure and add to data structure.
                markers.add(add);
            }
        }
    }

    /**
     * Shows a dialog that either clears your walk and map or does nothing.
     */
    public void showDialog() {

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() { //new dialog
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        map.clear();    //clear walk
                        markers.clear(); //clear data structure
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

    class Upload extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... arg0) {
            // TODO Auto-generated method stub
            //List <NameValuePair> params = new ArrayList<NameValuePair>();
            JSONObject params = new JSONObject();
            JSONObject des = new JSONObject();   //4 new JSON objects
            JSONObject poi = new JSONObject();
            JSONObject img = new JSONObject();

            try {

                params.put/*(new BasicNameValuePair*/("walkname", walkname);
                params.put("shortdes", shortdes);
                params.put("longdes", longdes);   //add in variables that the PHP script needs
                poi.put("sizet", markers.size());
                des.put("sizer", markers.size());
                img.put("size", markers.size());


                for (int i = 0; i < markers.size(); i++) {  //for every marker

                    pointof curr = markers.get(i);  //get reference to current marker

                    Bitmap d = curr.getImg();
                    ByteArrayOutputStream output = new ByteArrayOutputStream();
                    d.compress(Bitmap.CompressFormat.JPEG, 100, output); //bm is the bitmap object
                    byte[] bytes = output.toByteArray();                //decode to bytes
                    String base64Image = Base64.encodeToString(bytes, Base64.DEFAULT);    //encode in base64
                    img.put("image" + i, base64Image);   //send string
                    poi.put("description" + i, curr.getDescription());
                    des.put("description" + i, curr.getDescription());
                    poi.put("lat" + i, curr.getLat());
                    //String longitudeu = String.valueOf((double) curr.getLng());
                    poi.put("lng" + i, curr.getLng());

                }
            } catch (JSONException e) {

            }
            JSONObject json = JSONParser.SendHttpPost(URL_UPLOAD, params);
            JSONObject sendloc = JSONParser.SendHttpPost(POI_UPLOAD, poi);
            JSONObject senddes = JSONParser.SendHttpPost(DES_UPLOAD, des);   //sending of data using the JSONParser class
            JSONObject sendp = JSONParser.SendHttpPost(IMG_UPLOAD, img);
            Log.i("test", params.toString());
            Log.i("testpoi", poi.toString());
            Log.i("testdes", des.toString());  //Just test methods
            Log.i("img", img.toString());
            return null;
        }


    }


}