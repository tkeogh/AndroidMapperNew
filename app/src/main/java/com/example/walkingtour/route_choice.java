package com.example.walkingtour;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;


public class route_choice extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

       final  Intent choices = new Intent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_choice);

        final Button from = (Button)findViewById(R.id.from);
        final Button to = (Button)findViewById(R.id.to);
        Button back = (Button)findViewById(R.id.back);



        from.setOnClickListener(

                new View.OnClickListener()

                {

                    @Override
                    public void onClick(final View aView) {

                        //call show dialog method, asks if user is sure or not to cancel.
                        PopupMenu popup = new PopupMenu(aView.getContext(), from);
                        //Inflating the Popup using xml file
                        popup.getMenuInflater().inflate(R.menu.destination_menu, popup.getMenu());

                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            public boolean onMenuItemClick(MenuItem item) {

                                if(item.getTitle().equals("Edward Llyd")) {

                                    choices.putExtra("from","Edward");

                                }
                                if(item.getTitle().equals("Cwrt Mawr")) {

                                    choices.putExtra("from","Mawr");

                                }
                                if(item.getTitle().equals("Business Building")) {

                                    choices.putExtra("from","Business");

                                }
                                if(item.getTitle().equals("IBERS")) {

                                    choices.putExtra("from","IBERS");

                                }
                                if(item.getTitle().equals("Llandinam Building")) {

                                    choices.putExtra("from","Llandinam");

                                }
                                if(item.getTitle().equals("National Library")) {

                                    choices.putExtra("from","NL");

                                }
                                if(item.getTitle().equals("Students Union")) {

                                    choices.putExtra("from","Union");

                                }
                                if(item.getTitle().equals("IMAPS")) {

                                    choices.putExtra("from","IMAPS");

                                }
                                if(item.getTitle().equals("Hugh Owen")) {

                                    choices.putExtra("from","Hugh");

                                }

                                return true;
                            }
                        });
                        popup.show();
                    }
                }
        );

        to.setOnClickListener(

                new View.OnClickListener()

                {

                    @Override
                    public void onClick(final View aView) {

                        //call show dialog method, asks if user is sure or not to cancel.
                        PopupMenu popup = new PopupMenu(aView.getContext(), from);
                        //Inflating the Popup using xml file
                        popup.getMenuInflater().inflate(R.menu.destination_menu, popup.getMenu());

                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            public boolean onMenuItemClick(MenuItem item) {

                                if(item.getTitle().equals("Students Union")) {

                                    choices.putExtra("to","Union");

                                }
                                if(item.getTitle().equals("Cwrt Mawr")) {

                                    choices.putExtra("to","Mawr");

                                }
                                if(item.getTitle().equals("Hugh Owen")) {

                                    choices.putExtra("to","Hugh");

                                }
                                if(item.getTitle().equals("National Library")) {

                                    choices.putExtra("to","NL");

                                }
                                if(item.getTitle().equals("Edward Llyd")) {

                                    choices.putExtra("to","Edward");

                                }
                                if(item.getTitle().equals("IMAPS")) {

                                    choices.putExtra("to","IMAPS");

                                }

                                if(item.getTitle().equals("Business Building")) {

                                    choices.putExtra("to","Business");

                                }
                                if(item.getTitle().equals("IBERS")) {

                                    choices.putExtra("to","IBERS");

                                }
                                if(item.getTitle().equals("Llandinam Building")) {

                                    choices.putExtra("to","Llandinam");

                                }

                                return true;
                            }
                        });
                        popup.show();
                    }
                }
        );

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
}
