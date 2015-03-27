package com.example.walkingtour;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * Class used to send points of interest to the map activity including photo.
 * @author ww3ref
 *
 */
public class point extends Activity {

	private EditText name,desc;
	String mCurrentPhotoPath;

	static final int REQUEST_TAKE_PHOTO = 1;
	private static int RESULT_LOAD_IMAGE = 2;

/**
 * Sets on click listeners and handles calling validation.
 */
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.points);
		name = (EditText)findViewById(R.id.poiname);  //Assing buttons and edit texts their xml properties
		desc= (EditText)findViewById(R.id.poidescr);
		final Button add = (Button)findViewById(R.id.addpoi);
		final Button take = (Button)findViewById(R.id.takepic);




		add.setOnClickListener(

				new View.OnClickListener()

				{

					@Override
					public void onClick(View aView)

					{
						if(checkName(name.getText().toString())== true && checkName(desc.getText().toString())==true){
							Intent addmarker = new Intent(); //if validation pass start new intent
							addmarker.putExtra("mname", name.getText().toString());
							addmarker.putExtra("mdesc",desc.getText().toString()); //add extras
							addmarker.putExtra("photopath",mCurrentPhotoPath);	
							setResult(RESULT_OK,addmarker); //finish and move back to starting activity
							finish();	
						}
						else{

							Toast 	toast3 = Toast.makeText(getApplicationContext(), "Please fill all fields",Toast.LENGTH_SHORT);
							toast3.show();

						}
					}
				}
				);

		take.setOnClickListener(

				new View.OnClickListener()

				{

					@Override
					public void onClick(View aView)
					{
						showDialog();
					}
				}
				);

	}

/**
 * Creates an image and a string representing its path for use in photo using.
 * @return Image File
 * @throws IOException
 */
	private File createImageFile() throws IOException {
		// Create an image file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String imageFileName = "JPEG_" + timeStamp + "_";
		File storageDir = Environment.getExternalStoragePublicDirectory(
				Environment.DIRECTORY_PICTURES);
		File image = File.createTempFile(
				imageFileName,                  //creates an image file to store image in
				".jpg",         
				storageDir      
				);

		//SAVES CURRENT PATH OF FILE FOR USE IN PASSING THROUGH.	
		mCurrentPhotoPath = image.getAbsolutePath();
		return image;
	}

/**
 * Starts the camera activity, code recieved from the Google Android API.
 */
	private void dispatchTakePictureIntent() {
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		// Ensure that there's a camera activity to handle the intent
		if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
			// Create the File where the photo should go
			File photoFile = null;
			try {
				photoFile = createImageFile();  //sets file to the image taken
			} catch (IOException ex) {

			}
			// Continue only if the File was successfully created
			if (photoFile != null) {
				takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
						Uri.fromFile(photoFile));
				startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);

			}
		}
	}
/**
 * 
 * Shows the dialog related to if you want to pick an image from gallery or camera.
 * 
 */
	public void showDialog(){

		DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which){
				case DialogInterface.BUTTON_POSITIVE:
					dispatchTakePictureIntent(); //START picture intent
					break;

				case DialogInterface.BUTTON_NEGATIVE:
					Intent i = new Intent(
							Intent.ACTION_PICK,
							android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

					startActivityForResult(i, RESULT_LOAD_IMAGE);
					break;
				}
			}
		};

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Would you like to choose photo from gallery or camera?").setPositiveButton("Camera", dialogClickListener)
		.setNegativeButton("Gallery", dialogClickListener).show();
	}
/**
 * called when the actvity is resume after camera, uses cursor to get hold of image just taken and its picture path
 * is set to the currentphotpath variable.
 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
			Uri selectedImage = data.getData(); //get data of image
			String[] filePathColumn = { MediaStore.Images.Media.DATA };

			Cursor cursor = getContentResolver().query(selectedImage,
					filePathColumn, null, null, null); //
			cursor.moveToFirst();

			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			String picturePath = cursor.getString(columnIndex);
			cursor.close();
			mCurrentPhotoPath = picturePath;

		}
	}
/**
 * validation method used to check length,
 * @param s
 * @return
 */
	public boolean checkName(String s){

		if(s.length() > 0 ){
			return true;
		}
		return false;

	}

}


