package com.example.walkingtour;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
/**
 * 
 * Simple second screen for text entry of basic walk information
 * @author ww3ref
 *
 */
public class Secondscreen extends Activity {

	private Context context;

	private EditText edit1, edit2, edit3;
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


		final Button mapm = (Button)findViewById(R.id.buttonmap);

		edit1 = (EditText)findViewById(R.id.editText1);

		edit2 = (EditText)findViewById(R.id.EditText01);

		edit3 = (EditText)findViewById(R.id.EditText02);



		mapm.setOnClickListener(

				new View.OnClickListener()

				{

					@Override
					public void onClick(View bView)
					{

						if(checklong()==false | checkshort()==false | checknotnull()==false) {  //if all of these methods return false

							//kill click
						}
						else{

							Intent toAnotherActivity = new Intent(context, map.class);
							toAnotherActivity.putExtra("walkn",edit1.getText().toString());
							toAnotherActivity.putExtra("sdesc",edit2.getText().toString());  //adding edit text to extras to pass over.
							toAnotherActivity.putExtra("ldesc",edit3.getText().toString());
							toAnotherActivity.putExtra("want","routing");
							startActivity(toAnotherActivity);

						}
					}
				}
				);  

	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.secondscreen, menu);
		return true;
	}
	/**
	 * Checks if the short description is over 100 characters, if so stops user progressing and informs them why.
	 * @return boolean based on if passed or failed.
	 */
	
	public boolean checkshort(){
		if(edit2.getText().toString().length()>100){  //if the string in edit text is 100+ chars
			Toast toast2 = Toast.makeText(context, "Short description must be under 100 characters.", Toast.LENGTH_SHORT);
			toast2.show();
			return false;
		}
		else{
			return true;
		}
			
		
	}
	
	/**
	 * 
	 * Checks the three edit texts are not null
	 * @return boolean based on if passed or failed
	 */
	public boolean checknotnull(){
		if(edit1.getText().toString().equals("") | edit2.getText().toString().equals("")| edit3.getText().toString().equals("")){// if the strings are empty
			Toast toast2 = Toast.makeText(context, "Enter values in all fields", Toast.LENGTH_SHORT);
			toast2.show();
			return false;
		}
		else{
			return true;
		}
			
		
	}
	/**
	 * Checks to make sure if the long description is over 1000, if so stops the user progressing.
	 * @return
	 */
	public boolean checklong(){
		if(edit3.getText().toString().length()>1000){ //if string is over 1000 chars
			Toast toast2 = Toast.makeText(context, "Long description must be under 1000 characters.", Toast.LENGTH_SHORT);
			toast2.show();
			return false;
		}
		else{
			return true;
		}
			
		
	}

}
