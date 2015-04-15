package com.example.walkingtour;

import android.os.Bundle;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
/**
 * 
 * Starting screen uses very little.
 * @author ww3ref
 *
 */
public class MainActivity extends Activity {
	

/**
 * 
 * Sets button and text on page for view, simple entry screen.
 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main); 
		
		final Button change = (Button)findViewById(R.id.button1);  //attach button to xml
		
		change.setOnClickListener(
				 
        		new View.OnClickListener()
 
        		{
 
                        @Override
						public void onClick(View aView)
                        {
                            Intent toAnotherActivity = new Intent(aView.getContext(),
                                    Grid.class);
                            startActivity(toAnotherActivity);//move to another activity
                        }
        		}
        		);      
		
		

		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	

}
