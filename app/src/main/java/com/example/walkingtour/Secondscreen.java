package com.example.walkingtour;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 
 * Simple second screen for text entry of basic walk information
 * @author ww3ref
 *
 */
public class Secondscreen extends Activity {

	private Context context;
    String to;

	private EditText edit1, edit2, edit3;

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
/**
 * 
 * Sets on click listeners and helps with validation with calls to methods that check the edit text box's.
 * 
 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {






		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_secondscreen);
		context = this;
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);


		final Button mapm = (Button)findViewById(R.id.buttonmap);

		edit1 = (EditText)findViewById(R.id.editText1);



        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {



                     to = listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition);
                    String[] split = to.split(" ");
                    to = split[0];
                Log.i("Added thi nas to: ", to);




                return false;
            }
        });



		mapm.setOnClickListener(

				new View.OnClickListener()

				{

					@Override
					public void onClick(View bView)
					{



							Intent toAnotherActivity = new Intent(context, map.class);
							toAnotherActivity.putExtra("start",edit1.getText().toString());

                            toAnotherActivity.putExtra("end",to);

							toAnotherActivity.putExtra("want","routing");
							startActivity(toAnotherActivity);

						}

				}
				);  

	}




	
	/**
	 *
	 * Checks the three edit texts are not null
	 * @return boolean based on if passed or failed
	 */
	public boolean checknotnull(){
		if(edit1.getText().toString().equals("")){// if the strings are empty
			Toast toast2 = Toast.makeText(context, "Enter values in all fields", Toast.LENGTH_SHORT);
			toast2.show();
			return false;
		}
		else{
			return true;
		}


	}


    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding header titles

        listDataHeader.add("To");


        // Adding child data from reading a file
        List<String> locations = getData();



        listDataChild.put(listDataHeader.get(0), locations); // Header, Child data


    }

    /**
     * Used to load in file locations, means users just need to add one line into a text file to change the menus
     * They also need to add the places text file but thats not too hard.
     * @return - ArrayList of the locations.
     */

    private ArrayList<String> getData(){

        ArrayList<String> places = new ArrayList<String>();



        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open("Places")));
            String mLine = reader.readLine();
            while (mLine != null) {
                places.add(mLine);
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
