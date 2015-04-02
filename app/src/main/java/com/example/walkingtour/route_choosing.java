package com.example.walkingtour;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.Toast;

public class route_choosing extends Activity {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final Intent choices = new Intent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_choosing);

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);
        Button back = (Button)findViewById(R.id.back2);

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        expListView.setOnChildClickListener(new OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

                if(listDataHeader.get(groupPosition).equalsIgnoreCase("From")){

                    String from = listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition);
                    String[] split = from.split(" ");
                    from = split[0];

                        choices.putExtra("from",from);
                        Log.i("added from", from);
                }
                if(listDataHeader.get(groupPosition).equalsIgnoreCase("To")){

                    String to = listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition);
                    String[] split = to.split(" ");
                    to = split[0];


                        choices.putExtra("to",to);
                        Log.i("added to",to);


                }
                return false;
            }
        });


        back.setOnClickListener(

                new View.OnClickListener()

                {

                    @Override
                    public void onClick(View aView)

                    {

                        setResult(RESULT_OK,choices); //finish and move back to starting activity
                        finish();
                    }


                }
        );


    }



    /*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("From");
        listDataHeader.add("To");


        // Adding child data
        List<String> locations = getData();



        listDataChild.put(listDataHeader.get(0), locations); // Header, Child data
        listDataChild.put(listDataHeader.get(1), locations);

    }

    private ArrayList<String> getData(){

        ArrayList<String> places = new ArrayList<String>();



        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open("Places")));
            String mLine = reader.readLine();
            while (mLine != null) {
                places.add(mLine);
                Log.i("Loade in:"," "+mLine);
                mLine = reader.readLine();


            }
        } catch (IOException e) {
            //log the exception
        }
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }




        return places;
    }
}



