package com.example.walkingtour;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;

import com.example.walkingtour.Data.Distance;
import com.example.walkingtour.Data.locations;

public class Help extends Activity {

	private static double EARTH_CIRC_METERS = 40030218;
	
	ArrayList<locations> distances = new ArrayList<locations>();
	
	String help;

    Distance d = new Distance();


	private Location location;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help);
		TextView text = (TextView) findViewById(R.id.viewer);

		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		location = locationManager
				.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

		double lat = location.getLatitude();
		double lon = location.getLongitude();


        locations PS = new locations("Physical Sciences",d.distanceTo(lat, lon, 52.4159519,-4.0654581));
		distances.add(PS);

        locations HO = new locations("Hugh Owen",d.distanceTo(lat, lon, 52.4157893,-4.063691));
		distances.add(HO);

        locations TP = new locations("Thomas Parry",d.distanceTo(lat, lon, 52.4096535,-4.0521135));
		distances.add(TP);

        locations shortest = sortDist(distances);
		
		if (shortest.getName().equals("Physical Sciences")){
			
			
			help = "Your closest point of help is the Physical Sciences Library. \n "
					+ "Person of Contact : Amelia Lees \n "
					+ "Number : (01970)62 2407 ";
			
		}
		
		if (shortest.getName().equals("Hugh Owen")){
			
			help = "Your closest point of help is the Hugh Owen Building \n"
					+ "Person of Contact : Rhiannon Owen \n"
					+ "Number : (01970) 62 2400 ";
			
		}
		
		if (shortest.getName().equals("Thomas Parry")){
			
			help = "Your closest point of help is the Thomas Parry building \n"
					+ "Person of Contact : Amelia Lees \n"
					+ "Number : (01970) 62 2411 ";
			
		}
		
		
		
		
		text.setText(help);

	}

	private locations sortDist(ArrayList<locations> newdist) {
		int index = 0;

        locations small = newdist.get(0);
		
		for (int i =0;i<newdist.size();i++){
			
			if(newdist.get(i).getDist() < small.getDist()){
				
				index = i;
			}
			
			
		}
		
		
		return newdist.get(index);
		
		
		
	}
	
}
