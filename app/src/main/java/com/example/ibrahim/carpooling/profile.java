package com.example.ibrahim.carpooling;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

/**
 * Created by Ibrahim on 3/23/2016.
 */
public class profile extends ActionBarActivity
{
    Context context;

    Button  update_btn,changepass_btn;
    TextView tfirstname,tlastname,temail,tphone,taddress,tjob,ttype,tbirthdate;
    triplist t=new triplist();
    static ImageView pp;
    ImageView genderpic;
    int id;
    int index;

    @Override
    protected void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        tfirstname=(TextView)findViewById(R.id.tfirstname);
        tlastname=(TextView)findViewById(R.id.tlastname);
        temail=(TextView)findViewById(R.id.temail);
        tphone=(TextView)findViewById(R.id.tphonenumber);
        taddress=(TextView)findViewById(R.id.tcity);
        tjob=(TextView)findViewById(R.id.tjob);
        tbirthdate=(TextView)findViewById(R.id.tbirthdate);
        ttype=(TextView)findViewById(R.id.ttype);
        pp=(ImageView) findViewById(R.id.profilepicforprofile);
        genderpic=(ImageView) findViewById(R.id.genderpic);


        update_btn=(Button)findViewById(R.id.update);
        changepass_btn=(Button)findViewById(R.id.changepass);


        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), updateprofile.class);
                startActivity(intent);
                finish();
            }
        });

        changepass_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getApplicationContext(), Changepass.class);
                startActivity(intent2);
            }
        });


        if (t.ugender.equals("male"))
        {
            genderpic.setImageResource(R.drawable.male);
        }
        if(t.ugender.equals("female"))
        {
            genderpic.setImageResource(R.drawable.female);
        }



        tfirstname.setText(t.ufirstname);
        tlastname.setText(" "+t.ulastname);
        temail.setText(t.uemail);
        tphone.setText(t.uphonenumber);
        taddress.setText(t.uaddress);
        tjob.setText(t.ujob);
        ttype.setText(t.utype);
        tbirthdate.setText(t.ubirthdate);

        if(MainActivity.logorfb==1)
        {
            Glide.with(profile.this).load("http://carpoolingdata.esy.es/pictures/" + t.uusername + ".PNG").into(pp);
        }
        else
        {
            Glide.with(profile.this)
                    .load("https://graph.facebook.com/" + HomeFacebook.iidd + "/picture?type=large")
                    .transform(new CircleTransform(profile.this))
                    .into(pp);
        }

    }



}