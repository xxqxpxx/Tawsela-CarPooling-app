package com.example.ibrahim.carpooling;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Abdulrhman elsokkary on 4/25/2016.
 */
public class tripdetails extends ActionBarActivity {

    Button mapfragment;
    ImageView delete;
    MainActivity m = new MainActivity();
    triplistsearch t1=new triplistsearch();
    profile f = new profile();

    String viewtripdetails_url;
    static   String from , to ;
    String connectionparameter;
    byte[] parametersbyte;
    TextView tsource,tdestination,tfrequant,tdate,ttime,tmodel,tnumber,tcapacity,tsmoke,tcost,tcreatorname;
    ImageView timage;
    List<trip> trips=new ArrayList<>();
    String source,destination,datee,timee,frequant,modelcar,numbercar,capacitycar,smoke,costcar,creatorname,creatorusername,fbid;
    static String sharestring;
    int id,creatorid;
    int index;
    static   boolean flag=false;
    static String un,fi;
    static int ID;
    triplist t=new triplist();

    @Override
    protected void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tripdetails);


        mapfragment=(Button)findViewById(R.id.mapfragment);

        tsource=(TextView)findViewById(R.id.sourcel);
        tdestination=(TextView)findViewById(R.id.destinationl);
        tfrequant=(TextView)findViewById(R.id.frequent1);
        tdate=(TextView)findViewById(R.id.date);
        ttime=(TextView)findViewById(R.id.time);
        tmodel=(TextView)findViewById(R.id.model);
        tnumber=(TextView)findViewById(R.id.number);
        tcapacity=(TextView)findViewById(R.id.capacity);
        tsmoke=(TextView)findViewById(R.id.smoke);
        tcost=(TextView)findViewById(R.id.cost);
        tcreatorname=(TextView)findViewById(R.id.namel);
        timage=(ImageView)findViewById(R.id.img);


        delete=(ImageView)findViewById(R.id.delete);



        viewtripdetails_url="http://carpoolingdata.esy.es/viewtripdetails.php";

        Runnable run = new Runnable() {
            @Override
            public void run() {
                try {
                    URL viewtripdetailsurl = new URL(viewtripdetails_url);
                    HttpURLConnection con = (HttpURLConnection) viewtripdetailsurl.openConnection();
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
                        JSONArray usersarray=object.getJSONArray("trips");

                        trips.clear();
                        for(int i=0;i<usersarray.length();i++)
                        {

                            JSONObject currentobject=usersarray.getJSONObject(i);
                            id=currentobject.getInt("id");
                            source=currentobject.getString("source");
                            destination=currentobject.getString("destination");
                            datee=currentobject.getString("datee");
                            timee=currentobject.getString("timee");
                            frequant=currentobject.getString("frequant");
                            modelcar=currentobject.getString("modelcar");
                            numbercar=currentobject.getString("numbercar");
                            capacitycar=currentobject.getString("capacitycar");
                            smoke=currentobject.getString("smoke");
                            costcar=currentobject.getString("costcar");
                            creatorname=currentobject.getString("creatorname");
                            creatorid=currentobject.getInt("creatorid");
                            creatorusername=currentobject.getString("creatorusername");
                            fbid=currentobject.getString("fbid");

                            trip t=new trip(id,source,destination,datee,timee,frequant,modelcar,numbercar,
                                    capacitycar,smoke,costcar,creatorname,creatorid,creatorusername,fbid);
                            trips.add(t);
                        }
                        for(int i=0;i<trips.size();i++)
                        {

                            if((trips.get(i).id==t.ID ||trips.get(i).id==t1.ID))
                            {
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
                                    if(flag == true){

                                        tcreatorname.setText(trips.get(index).creatorname);
                                        tsource.setText("From  "+trips.get(index).source);
                                        tdestination.setText("To  "+trips.get(index).destination);
                                        tfrequant.setText(trips.get(index).frequant);
                                        tdate.setText(trips.get(index).datee);
                                        ttime.setText(trips.get(index).timee);
                                        tmodel.setText(trips.get(index).modelcar);
                                        tnumber.setText(trips.get(index).numbercar);
                                        tcapacity.setText(trips.get(index).capacitycar);
                                        tsmoke.setText(trips.get(index).smoke);
                                        tcost.setText(trips.get(index).costcar);
                                        ID=trips.get(index).creatorid;////////////////////////////
                                        un=trips.get(index).creatorusername;
                                        fi=trips.get(index).fbid;
                                        if (un.equals(t.uusername))
                                        {
                                            delete.setVisibility(View.VISIBLE);
                                        }
                                        if(fi.equals("nofbid")) {
                                            Glide.with(tripdetails.this).load("http://carpoolingdata.esy.es/pictures/" + trips.get(index).creatorusername + ".PNG").into(timage);
                                        }
                                        else
                                        {
                                            Glide.with(tripdetails.this)
                                                    .load("https://graph.facebook.com/" + fi + "/picture?type=large")
                                                    .transform(new CircleTransform(tripdetails.this))
                                                    .into(timage);
                                        }




                                        tcreatorname.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view)
                                            {
                                                if (un.equals(t.uusername))
                                                {
                                                    Intent intent1 = new Intent(tripdetails.this, profile.class);
                                                    startActivity(intent1);
                                                }
                                                else
                                                {
                                                    Intent intent1 = new Intent(tripdetails.this, othersprofile.class);
                                                    startActivity(intent1);
                                                }
                                            }
                                        });
                                        timage.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                if (un.equals(t.uusername))
                                                {
                                                    Intent intent1 = new Intent(tripdetails.this, profile.class);
                                                    startActivity(intent1);
                                                }
                                                else
                                                {
                                                    Intent intent1 = new Intent(tripdetails.this, othersprofile.class);
                                                    startActivity(intent1);
                                                }
                                            }
                                        });

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
    //////////////////////////////////////////////////////////////////////////////////////////////////
    public void  MapFragment(View view)
    {
        try {
            from = trips.get(index).source;
            to = trips.get(index).destination;
        }
        catch (Exception e)
        {
            Toast.makeText(tripdetails.this, e.toString() + " TripDetails", Toast.LENGTH_LONG).show();
        }
        Intent i=new Intent(tripdetails.this,MapsActivity.class);
        startActivity(i);
    }//map button
    //////////////////////////////////////////////////////////////////////////////////////////////////
    public void delete(View view)
    {


        int ik=trips.get(index).id;



        final String deletetrip_url = "http://carpoolingdata.esy.es/deletetrip.php";
        String idkey = "id=";

        try {
            connectionparameter =idkey+ik;

            parametersbyte=connectionparameter.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        internetconnection con=new internetconnection(tripdetails.this);
        con.connect(deletetrip_url,parametersbyte);
        Intent intent=new Intent(tripdetails.this,triplist.class);
        startActivity(intent);
        finish();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.share_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.action_share);

        ShareActionProvider mShareActionProvider =
                (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);


        if (mShareActionProvider != null ) {

            mShareActionProvider.setShareIntent(createShareTripIntent());

        }
        return true;
    }

    public Intent createShareTripIntent() {
        final Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");

        Runnable run = new Runnable() {
            @Override
            public void run() {
                try {
                    URL viewtripdetailsurl = new URL(viewtripdetails_url);
                    HttpURLConnection con = (HttpURLConnection) viewtripdetailsurl.openConnection();
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
                        JSONArray usersarray=object.getJSONArray("trips");

                        trips.clear();
                        for(int i=0;i<usersarray.length();i++)
                        {

                            JSONObject currentobject=usersarray.getJSONObject(i);

                            id=currentobject.getInt("id");
                            source=currentobject.getString("source");
                            destination=currentobject.getString("destination");
                            creatorname=currentobject.getString("creatorname");
                            datee=currentobject.getString("datee");
                            timee=currentobject.getString("timee");

                            trip t=new trip(source,destination,creatorname,datee,timee,id);
                            trips.add(t);
                        }
                        for(int i=0;i<trips.size();i++)
                        {

                            if((trips.get(i).id==t.ID))
                            {
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
                                    if(flag == true){

                                        sharestring="Trip from "+trips.get(index).source + " to "+trips.get(index).destination+
                                                " on "+trips.get(index).datee+" at " +trips.get(index).timee+"" +
                                                " created by "+trips.get(index).creatorname+" join now !\n#TWSELA";

                                        shareIntent.putExtra(Intent.EXTRA_TEXT,sharestring);

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
        return shareIntent;
    }
}