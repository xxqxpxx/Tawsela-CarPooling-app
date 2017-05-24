package com.example.ibrahim.carpooling;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

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

public class triplist extends ActionBarActivity
{


    String TITLES[] = {"Home","Profile","Create Trip","Contact Us","Log Out"};

    int ICONS[] = {R.drawable.home,R.drawable.profile,R.drawable.create,R.drawable.contact,R.drawable.logout};

    //Similarly we Create a String Resource for the name and email in the header view
    //And we also create a int resource for profile picture in the header view

    String NAME = "Ibrahim Sayed";
    String EMAIL = "IbrahimSayed94@outlook.com";
    int PROFILE = R.drawable.imageview;

    private Toolbar toolbar;                              // Declaring the Toolbar Object

    RecyclerView mRecyclerView;                           // Declaring RecyclerView
    RecyclerView.Adapter mAdapter;                        // Declaring Adapter For Recycler View
    RecyclerView.LayoutManager mLayoutManager;            // Declaring Layout Manager as a linear layout manager
    DrawerLayout Drawer;                                  // Declaring DrawerLayout

    ActionBarDrawerToggle mDrawerToggle;                  // Declaring Action Bar Drawer Toggle



    ImageView imagesearch;

    //
    String firstname,lastname,email,gender,username,birthdate,job,address,password,type,nationalid,phonenumber;
    static String ufirstname,ulastname,uemail,uphonenumber,ubirthdate,ujob,uaddress,utype,uusername,ugender,uname;
    static int uindexday,uindexmonth,uindexyear,uid;
    int indexday,indexmonth,indexyear;
    List<user> users=new ArrayList<>();
    HomeFacebook hf=new HomeFacebook();
    MainActivity m = new MainActivity();
    static   boolean flag=false;
    int indext1;
    //
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





        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView); // Assigning the RecyclerView Object to the xml View

        mRecyclerView.setHasFixedSize(true);                            // Letting the system know that the list objects are of fixed size

        mAdapter = new MyAdapter(TITLES,ICONS,NAME,EMAIL,PROFILE,this);       // Creating the Adapter of MyAdapter class(which we are going to see in a bit)
        // And passing the titles,icons,header view name, header view email,
        // and header view profile picture

        mRecyclerView.setAdapter(mAdapter);                              // Setting the adapter to RecyclerView

        mLayoutManager = new LinearLayoutManager(this);                 // Creating a layout Manager

        mRecyclerView.setLayoutManager(mLayoutManager);                 // Setting the layout Manager


        Drawer = (DrawerLayout) findViewById(R.id.DrawerLayout);        // Drawer object Assigned to the view
        mDrawerToggle = new ActionBarDrawerToggle(this,Drawer,toolbar,R.string.openDrawer,R.string.closeDrawer){

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // code here will execute once the drawer is opened( As I dont want anything happened whe drawer is
                // open I am not going to put anything here)
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                // Code here will execute once drawer is closed
            }



        }; // Drawer Toggle Object Made
        Drawer.setDrawerListener(mDrawerToggle); // Drawer Listener set to the Drawer toggle
        mDrawerToggle.syncState();               // Finally we set the drawer toggle sync State

        creattrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(triplist.this, CreateTrip.class);
                startActivity(intent1);
            }
        });

///////////////////////////////////////////////////////////////////////////////////////////

        imagesearch=(ImageView)findViewById(R.id.logosearchtrip);


/////////////////////////////////////////////////////////////////////////////////////////
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        ////////////////////////////////////////////////////////////////////////////////////////////
        final String viewprofile_url="http://carpoolingdata.esy.es/viewprofile.php";

        Runnable run1 = new Runnable() {
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
                        JSONArray  usersarray=object.getJSONArray("users");
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
                            indexday=currentobject.getInt("indexday");
                            indexmonth=currentobject.getInt("indexmonth");
                            indexyear=currentobject.getInt("indexyear");

                            user u=new user(id,firstname,lastname,email,password,username,gender,birthdate,job,address,nationalid,phonenumber,type,indexday,indexmonth,indexyear);
                            users.add(u);

                        }

                        for(int i=0;i<users.size();i++)
                        {
                            if(users.get(i).username.equals(m.susernamelogin) ||users.get(i).username.equals(hf.username1))
                            {
                                indext1 = i;
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
                                        uid=users.get(indext1).id;
                                        uusername=users.get(indext1).username;
                                        ufirstname=users.get(indext1).firstname;
                                        ulastname=users.get(indext1).lastname;
                                        ugender=users.get(indext1).gender;
                                        uemail=users.get(indext1).email;
                                        uphonenumber=users.get(indext1).phonenumber;
                                        uaddress=users.get(indext1).address;
                                        ujob=users.get(indext1).job;
                                        utype=users.get(indext1).type;
                                        ubirthdate=users.get(indext1).birthdate;
                                        uindexday=users.get(indext1).indexday;
                                        uindexmonth=users.get(indext1).indexmonth;
                                        uindexyear=users.get(indext1).indexyear;
                                        uname=ufirstname+" "+ulastname;
                                    }

                                }
                            });

                } catch (IOException e) {
                    e.printStackTrace();

                }

            }

        };


        Thread thread = new Thread(run1);
        thread.start();

        ///////////////////////////////////////////////////////////////////////////////////////////

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


                            trip t1=new trip(id,source,destination,creatorname,createdtime,creatorusername,fbid);
                            tripList.add(t1);
                            index=i;
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();

                    }

                    runOnUiThread(
                            new Runnable() {
                                @Override
                                public void run() {


                                    adapter = new triplistadapter(triplist.this,tripList);
                                    recyclerView.setAdapter(adapter);

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
                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {


                        ID=adapter.tripList.get(position).id;
                        Intent intent = new Intent(triplist.this, tripdetails.class);
                        startActivity(intent);

                    }
                })
        );






    }

    public  void SearchTrip(View view)
    {
        Intent intent = new Intent(triplist.this, searchtrip.class);
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

class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {

    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }
    GestureDetector mGestureDetector;

    public RecyclerItemClickListener(Context context, OnItemClickListener listener) {
        mListener = listener;
        mGestureDetector = new GestureDetector(context,     new GestureDetector.SimpleOnGestureListener() {
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
