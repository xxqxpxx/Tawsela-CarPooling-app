package com.example.ibrahim.carpooling;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ibrahim.carpooling.utils.Utils;
import com.facebook.FacebookSdk;
import com.parse.LogInCallback;
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

public class MainActivity extends ActionBarActivity {

    Button btsignup,btlogin;

    EditText emaillogin,passwordlogin;
    static String susernamelogin,spasswordlogin;

    List<user> users=new ArrayList<>();
    static  String password,firstname,lastname,name,username;
    static int id;
    static int nid;
    static String musername;
    static int logorfb=0;



    static boolean flag=false;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());

        setContentView(R.layout.activity_main);


        btlogin=(Button)findViewById(R.id.login);
        btlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                emaillogin=(EditText)findViewById(R.id.loginemail);
                passwordlogin=(EditText)findViewById(R.id.loginpassword);


                susernamelogin=emaillogin.getText().toString();
                spasswordlogin=passwordlogin.getText().toString();

                ParseUser.logInInBackground( susernamelogin, spasswordlogin, new LogInCallback() {

                    @Override
                    public void done(ParseUser pu, ParseException e) {

                        if (pu != null) {
                            UserList.user = pu;

                        } else {
                            Utils.showDialog(
                                    MainActivity.this,
                                    getString(R.string.err_login) + " "
                                            + e.getMessage());
                            e.printStackTrace();
                        }
                    }
                });
                final String login_url="http://carpoolingdata.esy.es/login.php";

                Runnable run = new Runnable() {
                    @Override
                    public void run() {
                        try {
                            URL viewprofileurl = new URL(login_url);
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

                                    password=currentobject.getString("password");
                                    firstname=currentobject.getString("firstname");
                                    lastname=currentobject.getString("lastname");
                                    id=currentobject.getInt("id");
                                    username=currentobject.getString("username");


                                    user u=new user(firstname,lastname,password,username,id);

                                    users.add(u);

                                }

                                for(int i=0;i<users.size();i++)
                                {
                                    if(users.get(i).username.equals(susernamelogin) && users.get(i).password.equals(spasswordlogin ))
                                    {
                                        //name=users.get(i).firstname+" "+users.get(i).lastname;
                                        //nid=users.get(i).id;
                                        flag=true;
                                        musername=users.get(i).username;
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
                                                logorfb=1;
                                                Intent intent1 = new Intent(MainActivity.this, triplist.class);
                                                startActivity(intent1);

                                                flag=false;
                                            }
                                            else{
                                                Toast.makeText(MainActivity.this,"Wrong password or Username",Toast.LENGTH_SHORT).show();
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
        });

        //////////////////////////////////////////////////////////////////////////////////////////// login button
        btsignup=(Button)findViewById(R.id.signup);
        btsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent=new Intent(MainActivity.this,SignUp.class);
                startActivity(intent);

            }
        });
        //////////////////////////////////////////////////////////////////////////////////////////// signup button
    }



    @Override
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
                Intent intent4 = new Intent(getApplicationContext(),triplist.class);
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

}