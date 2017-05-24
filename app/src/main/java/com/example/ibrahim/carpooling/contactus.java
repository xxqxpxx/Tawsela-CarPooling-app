package com.example.ibrahim.carpooling;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


public class contactus extends ActionBarActivity {

    String connectionparameter;
    byte[] parametersbyte;
    Button send;
    EditText firstname,lastname,contact_email,message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactus);

        send=(Button)findViewById(R.id.contact_send);
        firstname=(EditText)findViewById(R.id.contact_firstname);
        lastname=(EditText)findViewById(R.id.contact_lastname);
        contact_email=(EditText)findViewById(R.id.contact_email);
        message=(EditText)findViewById(R.id.contact_message);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fk,lk,ek,mk;
                fk=firstname.getText().toString();
                lk=lastname.getText().toString();
                ek=contact_email.getText().toString();
                mk=message.getText().toString();

                final String contactus_url = "http://carpoolingdata.esy.es/contactus.php";
                String firstnamenamekey = "firstname=";
                String lastnamekey = "&lastname=";
                String emailkey = "&email=";
                String messagekey = "&message=";

                try {
                    connectionparameter = firstnamenamekey + URLEncoder.encode(fk, "UTF-8") +
                            lastnamekey + URLEncoder.encode(lk, "UTF-8")+
                            emailkey + URLEncoder.encode(ek, "UTF-8")+messagekey + URLEncoder.encode(mk, "UTF-8");
                    parametersbyte=connectionparameter.getBytes("UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                internetconnection con=new internetconnection(contactus.this);
                con.connect(contactus_url,parametersbyte);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contactus, menu);
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
