package com.example.walkingtour;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class Grid extends Activity {

	
	Button help,finder,routing,facilities;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.grid);

		facilities = (Button) findViewById(R.id.facil);

		routing = (Button) findViewById(R.id.route);

		help = (Button) findViewById(R.id.help);
		
		finder = (Button) findViewById(R.id.find);

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
								RouteFinder.class);
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
