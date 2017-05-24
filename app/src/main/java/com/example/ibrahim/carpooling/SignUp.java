package com.example.ibrahim.carpooling;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.ActionBarActivity;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ibrahim.carpooling.utils.Utils;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
/**
 * Created by Ibrahim on 3/19/2016.
 */
public class SignUp extends ActionBarActivity implements View.OnClickListener {
    EditText efirstname, elastname, epassword, eemail, eusername, enationalid,ejob,eaddress,ephonenumber;
    Spinner sday, smonth, syear;
    RadioGroup rg_gender, rg_type;
    RadioButton rgender, rtype;
    String connectionparameter;
    String connectionparameter2;
    byte[] parametersbyte;
    byte[] parametersbyte2;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.sign_up_screen);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        eaddress=(EditText)findViewById(R.id.address);

        eaddress.setOnClickListener((View.OnClickListener) this);
    }
    public void signUp(View view)
    {
        efirstname=(EditText)findViewById(R.id.firstname);
        elastname=(EditText)findViewById(R.id.lastname);
        epassword=(EditText)findViewById(R.id.password);
        eemail=(EditText)findViewById(R.id.email);
        eusername=(EditText)findViewById(R.id.username);
        enationalid=(EditText)findViewById(R.id.nationalid);
        ejob=(EditText)findViewById(R.id.job);

        ephonenumber=(EditText)findViewById(R.id.phonenumber);
        //spinner
        sday=(Spinner)findViewById(R.id.sp_day);
        smonth=(Spinner)findViewById(R.id.sp_month);
        syear=(Spinner)findViewById(R.id.sp_year);
        //Radiogroup
        rg_gender=(RadioGroup)findViewById(R.id.radiogroupgender);
        rg_type=(RadioGroup)findViewById(R.id.radiogrouptype);
        final int selectedIdgender = rg_gender.getCheckedRadioButtonId();
        rgender= (RadioButton)findViewById(selectedIdgender);
        final int selectedIdtype = rg_type.getCheckedRadioButtonId();
        rtype= (RadioButton)findViewById(selectedIdtype);

        String fk,lk,ek,pk,uk,gk,bk,nk,tk ,jk,addk,phk;

        int dbk,mbk,ybk;

        fk=efirstname.getText().toString();
        lk=elastname.getText().toString();
        ek=eemail.getText().toString();
        pk=epassword.getText().toString();
        uk=eusername.getText().toString();
        jk=ejob.getText().toString();
        addk=eaddress.getText().toString();
        phk=ephonenumber.getText().toString();
        gk = ((RadioButton) findViewById(rg_gender.getCheckedRadioButtonId())).getText().toString();
        bk=sday.getSelectedItem().toString()+"/"+smonth.getSelectedItem().toString()+"/"+syear.getSelectedItem().toString();
        nk=enationalid.getText().toString();
        tk = ((RadioButton) findViewById(rg_type.getCheckedRadioButtonId())).getText().toString();
        dbk = sday.getSelectedItemPosition();
        mbk = smonth.getSelectedItemPosition();
        ybk=  syear.getSelectedItemPosition();

///////////////////////////////////////////////////////
        final ParseUser pu = new ParseUser();
        pu.setEmail(ek);
        pu.setPassword(pk);
        pu.setUsername(uk);

        pu.signUpInBackground(new SignUpCallback() {

            @Override
            public void done(ParseException e)
            {
                if (e == null)
                {
                    UserList.user = pu;


                }
                else
                {
                    Utils.showDialog(
                            SignUp.this,
                            getString(R.string.err_singup) + " "
                                    + e.getMessage());
                    e.printStackTrace();
                }

            }
        });
////////////////////////////////////////////////////////
        final String signurl = "http://carpoolingdata.esy.es/signup.php";
        String firstnamenamekey = "firstname=";
        String lastnamekey = "&lastname=";
        String emailkey = "&email=";
        String passwordkey = "&password=";
        String usernamekey = "&username=";
        String genderkey = "&gender=";
        String birthdatekey = "&birthdate=";
        String jobkey = "&job=";
        String addresskey = "&address=";
        String natioalidkey = "&nationalid=";
        String typekey = "&type=";
        String phonenumberkey = "&phonenumber=";
        String indexdaykey="&indexday=";
        String indexmonthkey="&indexmonth=";
        String indexyearkey="&indexyear=";
        try {
            connectionparameter = firstnamenamekey + URLEncoder.encode(fk, "UTF-8") +
                    lastnamekey + URLEncoder.encode(lk, "UTF-8")+
                    emailkey + URLEncoder.encode(ek, "UTF-8")+passwordkey + URLEncoder.encode(pk, "UTF-8")
                    +usernamekey + URLEncoder.encode(uk, "UTF-8")+genderkey + URLEncoder.encode(gk, "UTF-8")
                    +birthdatekey + URLEncoder.encode(bk, "UTF-8")  +jobkey + URLEncoder.encode(jk, "UTF-8")
                    +addresskey + URLEncoder.encode(addk, "UTF-8")
                    +natioalidkey + URLEncoder.encode(nk, "UTF-8")  +phonenumberkey + URLEncoder.encode(phk, "UTF-8")
                    +typekey + URLEncoder.encode(tk, "UTF-8")+indexdaykey+dbk+indexmonthkey+mbk+indexyearkey+ybk;
            parametersbyte=connectionparameter.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        internetconnection con=new internetconnection(SignUp.this);
        con.connect(signurl,parametersbyte);
        //-----Default Profile Pic----------
        Drawable defaultimage = ResourcesCompat.getDrawable(getResources(), R.drawable.imageview2, null);
        Bitmap image = ((BitmapDrawable) defaultimage).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
        final String savepicurl="http://carpoolingdata.esy.es/savepicture%20.php";
        String namekey ="name=";
        String imagekey ="&image=";
        try {
            connectionparameter2 =
                    namekey + URLEncoder.encode(uk, "UTF-8") +
                            imagekey + URLEncoder.encode(encodedImage, "UTF-8");

            parametersbyte2=connectionparameter2.getBytes("UTF-8");

            con.connect(savepicurl, parametersbyte2);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
///////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    //////////////////////////////////////////////////////////////////////////////////////////////
    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    String location;
    @Override
    public void onClick(View view) {
        location = eaddress.getText().toString();
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
    }
    static String place1;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);

                Log.i("", "Place: " + place.getName());

                place1=(String)place.getName();
                eaddress.setText(place1);
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                Toast.makeText(getApplication(),"Error",
                        Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }


        }

    }

///////////////////////////////////////////////////////////////////////////////////////////////
}
