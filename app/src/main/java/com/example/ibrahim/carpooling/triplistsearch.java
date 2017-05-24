package com.example.ibrahim.carpooling;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

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
 * Created by Ibrahim on 6/11/2016.
 */
public class triplistsearch extends ActionBarActivity
{
    searchtrip s=new searchtrip();
    ImageView imagesearch;
    boolean x=false;

    Context context;
    triplistadapter adapter;
    List<trip> tripList=new ArrayList<>();
    String source , destination ,creatorname ,createdtime,creatorusername,fbid;
    int id;
    int index;
    Button creattrip;
    RecyclerView recyclerView;
    static  int ID;

    @Override
    protected void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.triplist);
        creattrip=(Button)findViewById(R.id.creattrip);
        creattrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent( triplistsearch.this, CreateTrip.class);
                startActivity(intent1);
            }
        });

///////////////////////////////////////////////////////////////////////////////////////////

        imagesearch=(ImageView)findViewById(R.id.logosearchtrip);


/////////////////////////////////////////////////////////////////////////////////////////
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
      //  linearLayoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(linearLayoutManager);


        final String viewtriplist_url="http://carpoolingdata.esy.es/viewtrip.php";

        Runnable run = new Runnable() {
            @Override
            public void run() {
                try {
                    URL viewprofileurl = new URL(viewtriplist_url);
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
                        JSONArray tripssarray=object.getJSONArray("trips");

                        tripList.clear();
                        for(int i=0;i<tripssarray.length();i++)
                        {

                            JSONObject currentobject=tripssarray.getJSONObject(i);

                            id=currentobject.getInt("id");
                            source=currentobject.getString("source");
                            destination=currentobject.getString("destination");
                            creatorname=currentobject.getString("creatorname");
                            createdtime=currentobject.getString("createdtime");
                            creatorusername=currentobject.getString("creatorusername");
                            fbid=currentobject.getString("fbid");



                              if(source.equals(s.search_source) && destination.equals(s.search_destination)) {

                                  trip t1 = new trip(id, source, destination, creatorname, createdtime, creatorusername,fbid);
                                  tripList.add(t1);
                                  index = i;
                                  x=true;
                              }


                        }


                    } catch (JSONException e) {
                        e.printStackTrace();

                    }

                    runOnUiThread(
                            new Runnable() {
                                @Override
                                public void run() {


                                    adapter = new triplistadapter(triplistsearch.this,tripList);
                                    recyclerView.setAdapter(adapter);

                                    if(x==false)
                                    {    new AlertDialog.Builder(triplistsearch.this)
                                            .setTitle("Searching On Trip")
                                            .setMessage("Trip Not Found\nBack To Previous Trips List ")
                                            .setCancelable(false)
                                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    // Whatever...
                                                    Intent intent = new Intent(triplistsearch.this, triplist.class);
                                                    startActivity(intent);
                                                }
                                            }).create().show();
                                    }
                                    Toast.makeText(getApplicationContext(),s.search_source+" And "+s.search_destination+" And ",Toast.LENGTH_LONG).show();

                                }
                            });

                } catch (IOException e) {
                    e.printStackTrace();

                }

            }

        };


        Thread th = new Thread(run);
        th.start();


        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListenersearch(this, new RecyclerItemClickListenersearch.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {


                        ID=adapter.tripList.get(position).id;
                        Intent intent = new Intent( triplistsearch.this, tripdetails.class);
                        startActivity(intent);

                    }
                })
        );

    }

    public  void SearchTrip(View view)
    {
        Intent intent = new Intent(triplistsearch.this, searchtrip.class);
        startActivity(intent);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {

            case R.id.item3:
                Intent intent2 = new Intent(getApplicationContext(), CreateTrip.class);
                startActivity(intent2);
                return true;
            case R.id.item4:
                Intent intent1 = new Intent(getApplicationContext(), profile.class);
                startActivity(intent1);
                return true;
            case R.id.item5:
                setContentView(R.layout.tripdetails);
                return true;
            case R.id.item6:
                Intent intent3 = new Intent(getApplicationContext(), triplist.class);
                startActivity(intent3);
                return true;
            case R.id.item7:
                setContentView(R.layout.tripitem);
                return true;
            case R.id.item8:
                Intent intent4 = new Intent(getApplicationContext(),UserList.class);
                startActivity(intent4);
                return true;
            case android.R.id.home:
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }





}//class triplist

class RecyclerItemClickListenersearch implements RecyclerView.OnItemTouchListener {

    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }
    GestureDetector mGestureDetector;

    public RecyclerItemClickListenersearch(Context context, OnItemClickListener listener) {
        mListener = listener;
        mGestureDetector = new GestureDetector(context,new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
        View childView = view.findChildViewUnder(e.getX(), e.getY());
        if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
            mListener.onItemClick(childView, view.getChildAdapterPosition(childView));
        }
        return false;
    }


    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }


    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}//RecyclerItemClickListener class

