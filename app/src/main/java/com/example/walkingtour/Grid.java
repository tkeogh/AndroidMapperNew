package com.example.walkingtour;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class Grid extends Activity {

	


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.grid);
        Button help,finder,routing,facilities;

		facilities = (Button) findViewById(R.id.facil);

		routing = (Button) findViewById(R.id.route);

		help = (Button) findViewById(R.id.help);
		
		finder = (Button) findViewById(R.id.find);

		/**
		 * setting text using HTML, gives better design
		 */
        facilities.setText(Html.fromHtml("<b><big>" + "Facilities" + "</big></b>" + "<br /><br />" +
                "<small>" + "Map of Buildings on Campus" + "</small>" + "<br />"));


        routing.setText(Html.fromHtml("<b><big>" + "Route Plotter" + "</big></b>" + "<br /><br />" +
                "<small>" + "Plot new routes and upload them" + "</small>" + "<br />"));

        help.setText(Html.fromHtml("<b><big>" + "Find Help" + "</big></b>" + "<br /><br />" +
                "<small>" + "Find the closest person who can help you" + "</small>" + "<br />"));

        finder.setText(Html.fromHtml("<b><big>" + "Route Finder" + "</big></b>" + "<br /><br />" +
                "<small>" + "Find your way around Campus" + "</small>" + "<br />"));



		facilities.setOnClickListener(

		new View.OnClickListener()

		{

			@Override
			public void onClick(View aView) {
				Intent toAnotherActivity = new Intent(aView.getContext(),
						map.class);
				toAnotherActivity.putExtra("want", "help");
				startActivity(toAnotherActivity); // move to another activity

			}
		});
		
		finder.setOnClickListener(
				
				new View.OnClickListener()

				{

					@Override
					public void onClick(View aView) {
						Intent toAnotherActivity = new Intent(aView.getContext(),
								routeType.class);
						startActivity(toAnotherActivity); // move to another activity

					}
				}
				
				);

		routing.setOnClickListener(

		new View.OnClickListener()

		{

			@Override
			public void onClick(View aView) {
				Intent toAnotherActivity = new Intent(aView.getContext(),
						Secondscreen.class);
				startActivity(toAnotherActivity); // move to another activity

			}
		});

		help.setOnClickListener(

		new View.OnClickListener()

		{

			@Override
			public void onClick(View aView) {
				Intent toAnotherActivity = new Intent(aView.getContext(),
						Help.class);
				startActivity(toAnotherActivity); // move to another activity

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.grid, menu);
		return true;
	}
}
