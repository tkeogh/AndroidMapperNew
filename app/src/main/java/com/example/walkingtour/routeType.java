package com.example.walkingtour;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class routeType extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_type);
        Button standard = (Button) findViewById(R.id.standard);
        Button nostep = (Button) findViewById(R.id.nostep);

        standard.setText(Html.fromHtml("<b><big>" + "Standard" + "</big></b>" + "<br /><br />" +
                "<small>" + "Route Finding including stairs." + "</small>" + "<br />"));

        nostep.setText(Html.fromHtml("<b><big>" + "No Step" + "</big></b>" + "<br /><br />" +
                "<small>" + "Route Finding with no stairs." + "</small>" + "<br />"));

        standard.setOnClickListener(

                new View.OnClickListener()

                {

                    @Override
                    public void onClick(View aView) {
                        Intent toAnotherActivity = new Intent(aView.getContext(),
                               RouteFinder.class);
                        toAnotherActivity.putExtra("type", "standard");
                        startActivity(toAnotherActivity); // move to another activity

                    }
                });

        nostep.setOnClickListener(

                new View.OnClickListener()

                {

                    @Override
                    public void onClick(View aView) {
                        Intent toAnotherActivity = new Intent(aView.getContext(),
                                RouteFinder.class);
                        toAnotherActivity.putExtra("type", "nostep");
                        startActivity(toAnotherActivity); // move to another activity

                    }
                });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_route_type, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
