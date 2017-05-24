package com.example.ibrahim.carpooling;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ibrahim.carpooling.utils.Const;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Abdulrhman elsokkary on 5/3/2016.
 */
public class othersprofile extends ActionBarActivity implements View.OnClickListener {
    TextView f,l,e,p,c,j,t,b;
    ImageView g,i;
    List<user> users=new ArrayList<>();
    String firstname,lastname,email,gender,username,birthdate,job,address,password,type,nationalid,phonenumber;
    int id;
    int index;


    ImageView chatbt;
    tripdetails td=new tripdetails();
    static   boolean flag=false;
    @Override
    protected void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.othersprofile);
        f=(TextView)findViewById(R.id.f);
        l=(TextView)findViewById(R.id.l);
        e=(TextView)findViewById(R.id.e);
        p=(TextView)findViewById(R.id.p);
        c=(TextView)findViewById(R.id.c);
        j=(TextView)findViewById(R.id.j);
        b=(TextView)findViewById(R.id.b);
        t=(TextView)findViewById(R.id.t);
        g=(ImageView)findViewById(R.id.g);
        i=(ImageView)findViewById(R.id.i);

        chatbt=(ImageView)findViewById(R.id.chatbutton);
        chatbt.setOnClickListener(this);
        final String viewprofile_url="http://carpoolingdata.esy.es/viewprofile.php";

        Runnable run = new Runnable() {
            @Override
            public void run() {
                try {
                    URL viewprofileurl = new URL(viewprofile_url);
                    HttpURLConnection con = (HttpURLConnection) viewprofileurl.openConnection();
                    InputStreamReader resultStreamReader = new InputStreamReader(con.getInputStream());
                    BufferedReader resultReader = new BufferedReader(resultStreamReader);
                    final StringBuilder textbuilder= new StringBuilder();
                    String line;
                    while ((line=resultReader.readLine()) != null)
                    {
                        textbuilder.append(line);
                    }
                    String result=textbuilder.toString();

                    try {
                        JSONObject object=new JSONObject(result);
                        JSONArray usersarray=object.getJSONArray("users");

                        users.clear();
                        for(int i=0;i<usersarray.length();i++)
                        {

                            JSONObject currentobject=usersarray.getJSONObject(i);
                            id=currentobject.getInt("id");
                            firstname=currentobject.getString("firstname");
                            lastname=currentobject.getString("lastname");
                            email=currentobject.getString("email");
                            password=currentobject.getString("password");
                            username=currentobject.getString("username");
                            gender=currentobject.getString("gender");
                            birthdate=currentobject.getString("birthdate");
                            job=currentobject.getString("job");
                            address=currentobject.getString("address");
                            nationalid=currentobject.getString("nationalid");
                            phonenumber=currentobject.getString("phonenumber");
                            type=currentobject.getString("type");

                            user u=new user(id,firstname,lastname,email,password,username,gender,birthdate,job,address,nationalid,phonenumber,type,0,0,0);
                            users.add(u);

                        }

                        for(int i=0;i<users.size();i++)
                        {
                            if(users.get(i).id==td.ID) {
                                index = i;
                                flag=true;
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();

                    }

                    runOnUiThread(
                            new Runnable() {
                                @Override
                                public void run() {
                                    if(flag == true)
                                    {
                                        f.setText(users.get(index).firstname);
                                        l.setText(" "+ users.get(index).lastname);
                                        e.setText(users.get(index).email);
                                        p.setText(users.get(index).phonenumber);
                                        c.setText(users.get(index).address);
                                        j.setText(users.get(index).job);
                                        t.setText(users.get(index).type);
                                        b.setText(users.get(index).birthdate);
                                        if (users.get(index).gender.equals("male"))
                                        {
                                            g.setImageResource(R.drawable.male);
                                        }
                                        if(users.get(index).gender.equals("female"))
                                        {
                                            g.setImageResource(R.drawable.female);
                                        }


                                        if(td.fi.equals("nofbid")) {
                                            Glide.with(othersprofile.this).load("http://carpoolingdata.esy.es/pictures/" + users.get(index).username + ".PNG").into(i);
                                        }
                                        else
                                        {
                                            Glide.with(othersprofile.this)
                                                    .load("https://graph.facebook.com/" + td.fi + "/picture?type=large")
                                                    .transform(new CircleTransform(othersprofile.this))
                                                    .into(i);
                                        }
                                    }

                                }
                            });

                } catch (IOException e) {
                    e.printStackTrace();

                }

            }

        };


        Thread th = new Thread(run);
        th.start();






    }

    public static ParseUser user;

    @Override
    public void onClick(View view) {

        Toast.makeText(getApplication(), "ok", Toast.LENGTH_SHORT).show();
        ParseUser.getQuery().whereNotEqualTo("username",users.get(index).username)
                .findInBackground(new FindCallback<ParseUser>() {

                    @Override
                    public void done(List<ParseUser> li, ParseException e)
                    {

                        startActivity(new Intent(othersprofile.this,
                                Chat.class).putExtra(
                                Const.EXTRA_DATA,users.get(index).username ));

                    }
                });

    }//onClick

    @TargetApi(Build.VERSION_CODES.DONUT)
    public void openWhatsapp(View view ) {
        PackageManager pm=getPackageManager();
        try {

            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Type your message here.");
            sendIntent.setType("text/plain");
            sendIntent.setPackage("com.whatsapp");
            startActivity(sendIntent);

            // Perform action on click
            PackageInfo info=pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
            //Check if package exists or not. If not then code
            //in catch block will be called


        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(this, "WhatsApp is not Installed", Toast.LENGTH_SHORT)
                    .show();
        }

    }
}
