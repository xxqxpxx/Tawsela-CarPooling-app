package com.example.ibrahim.carpooling;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;


public class searchtrip extends ActionBarActivity implements View.OnClickListener {

    EditText source,destination;

    Button searchbtn;

   static String search_source,search_destination;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchtrip);

        source=(EditText)findViewById(R.id.search_source);
        destination=(EditText)findViewById(R.id.search_destination);

        source.setOnClickListener(this);
        destination.setOnClickListener(this);

        searchbtn=(Button)findViewById(R.id.btn_search);


    }

    public  void searchbutton(View view)
    {
        search_source=source.getText().toString();
        search_destination=destination.getText().toString();
        Intent intent = new Intent(searchtrip.this,  triplistsearch.class);
        startActivity(intent);
        Toast.makeText(getApplicationContext(),search_destination+" "+search_source,Toast.LENGTH_LONG).show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_searchtrip, menu);
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
    EditText location_tf;
    String location ,location2 ;
    static String place1,place2;
    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);

                Log.i("", "Place: " + place.getName());

                place1=(String)place.getName();
                location_tf.setText(place1);

                //place2=(String)place.getName();
                // location_tf2.setText(place2);

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                Log.i("", status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }


        }
    }

    @Override
    public void onClick(View view) {


        switch (view.getId()) {

            case R.id.search_source:
                location_tf = (EditText)findViewById(R.id.search_source);
                location = source.getText().toString();
                if(location.length() == 0)
                {


                    try {
                        Intent intent =
                                new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                                        .build(this);
                        startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
                    } catch (GooglePlayServicesRepairableException e) {
                        // TODO: Handle the error.
                    } catch (GooglePlayServicesNotAvailableException e) {
                        // TODO: Handle the error.
                    }



                }
                else return;
                break;

            //     ---------------------------------------------------------------------------------------
            case R.id.search_destination:
                location_tf = (EditText)findViewById(R.id.search_destination);
                location = destination.getText().toString();
                if(location.length() == 0)
                {


                    try {
                        Intent intent2 =
                                new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                                        .build(this);
                        startActivityForResult(intent2, PLACE_AUTOCOMPLETE_REQUEST_CODE);
                    } catch (GooglePlayServicesRepairableException e) {
                        // TODO: Handle the error.
                    } catch (GooglePlayServicesNotAvailableException e) {
                        // TODO: Handle the error.
                    }



                }
                else return;
                break;
        }//switch



    }
}
