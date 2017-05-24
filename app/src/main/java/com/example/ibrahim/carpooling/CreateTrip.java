package com.example.ibrahim.carpooling;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class CreateTrip extends ActionBarActivity implements View.OnClickListener {
    triplist t =  new triplist();
    EditText efromm, eto, edatee,etimee, emodelcar, enumbercar,ecapacitycar,ecostcar,ecreatorname;
    RadioGroup rg_frequant, rg_smoke;
    RadioButton rfrequant, rsmoke;

    Button searchfr , searcht;


    String connectionparameter;
    byte[] parametersbyte;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.createtrip);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        efromm=(EditText)findViewById(R.id.source);
        eto=(EditText)findViewById(R.id.destination);

        efromm.setOnClickListener(this);
        eto.setOnClickListener(this);
        Toast.makeText(CreateTrip.this,t.uid+" ",Toast.LENGTH_SHORT).show();

    }

    public void createTrip(View view)
    {

        edatee=(EditText)findViewById(R.id.datee);
        etimee=(EditText)findViewById(R.id.timee);
        emodelcar=(EditText)findViewById(R.id.modelcar);
        enumbercar=(EditText)findViewById(R.id.numbercar);
        ecapacitycar=(EditText)findViewById(R.id.capacitycar);
        ecostcar=(EditText)findViewById(R.id.costcar);
        //Radiogroup
        rg_frequant=(RadioGroup)findViewById(R.id.radiogroupfrequant);
        rg_smoke=(RadioGroup)findViewById(R.id.radiogroupsmoke);

        final int selectedIdfrequant = rg_frequant.getCheckedRadioButtonId();
        rfrequant= (RadioButton)findViewById(selectedIdfrequant);

        final int selectedIdsmoke = rg_smoke.getCheckedRadioButtonId();
        rsmoke= (RadioButton)findViewById(selectedIdsmoke);

        String fk,tk,dk,tik,mk,nk,cak,cok ,frk,smk,cn,cun,fbidk;
        int cii;
        fk=efromm.getText().toString();
        tk=eto.getText().toString();
        dk=edatee.getText().toString();
        tik=etimee.getText().toString();
        mk=emodelcar.getText().toString();
        nk=enumbercar.getText().toString();
        cak=ecapacitycar.getText().toString();
        cok=ecostcar.getText().toString();
        cn=t.uname;
        cii=t.uid;
        cun=t.uusername;
        if(MainActivity.logorfb==1)
        {
            fbidk="nofbid";
        }
        else
        {
            fbidk=HomeFacebook.iidd;
        }

        frk = ((RadioButton) findViewById(rg_frequant.getCheckedRadioButtonId())).getText().toString();

        smk = ((RadioButton) findViewById(rg_smoke.getCheckedRadioButtonId())).getText().toString();
        final String createtrip_url = "http://carpoolingdata.esy.es/createtrip.php";
        String frommkey = "source=";
        String tokey = "&destination=";
        String dateekey = "&datee=";
        String timeekey = "&timee=";
        String frequantkey = "&frequant=";
        String modelcarkey = "&modelcar=";
        String numbercarkey = "&numbercar=";
        String capacitycarkey = "&capacitycar=";
        String costcarkey = "&costcar=";
        String smokekey = "&smoke=";
        String creatorname = "&creatorname=";
        String creatorid = "&creatorid=";
        String creatorusername = "&creatorusername=";
        String fbidkey="&fbid=";


        try {
            connectionparameter = frommkey + URLEncoder.encode(fk, "UTF-8") +
                    tokey + URLEncoder.encode(tk, "UTF-8")+
                    dateekey + URLEncoder.encode(dk, "UTF-8")+timeekey + URLEncoder.encode(tik, "UTF-8")
                    +frequantkey + URLEncoder.encode(frk, "UTF-8")
                    +modelcarkey + URLEncoder.encode(mk, "UTF-8")+numbercarkey + URLEncoder.encode(nk, "UTF-8")
                    +capacitycarkey + URLEncoder.encode(cak, "UTF-8")  +costcarkey + URLEncoder.encode(cok, "UTF-8")
                    +smokekey + URLEncoder.encode(smk, "UTF-8")+creatorname + URLEncoder.encode(cn, "UTF-8")
                    +creatorid + URLEncoder.encode(String.valueOf(cii), "UTF-8")
                    +creatorusername + URLEncoder.encode(cun, "UTF-8")+fbidkey + URLEncoder.encode(fbidk, "UTF-8");

            parametersbyte=connectionparameter.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        internetconnection con=new internetconnection(CreateTrip.this);
        con.connect(createtrip_url,parametersbyte);
        Intent intent1 = new Intent(CreateTrip.this, triplist.class);
        startActivity(intent1);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {

            case android.R.id.home:
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
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

            case R.id.source:
                location_tf = (EditText)findViewById(R.id.source);
                location = efromm.getText().toString();
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
            case R.id.destination:
                location_tf = (EditText)findViewById(R.id.destination);
                location = eto.getText().toString();
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
