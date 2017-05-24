package com.example.ibrahim.carpooling;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.example.ibrahim.carpooling.utils.Utils;
import com.facebook.Profile;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import static com.google.android.gms.internal.zzir.runOnUiThread;

public class HomeFacebook extends Fragment {
    String connectionparameter;
    byte[] parametersbyte;
    List<user> users=new ArrayList<>();
    static String username1,iidd;
    static String profilepicurl;
    int index;
    static   boolean flag=false;
    static String username;


    static Profile profile = null;



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();

        if (bundle != null) {
            profile = (Profile) bundle.getParcelable(fragment_main.PARCEL_KEY);
        } else {
            profile = Profile.getCurrentProfile();
        }

            /*logoutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    logout();
                }
            });*/
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        final String viewprofile_url="http://carpoolingdata.esy.es/loginfb1.php";

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
                            username=currentobject.getString("username");
                            user u=new user(username);
                            users.add(u);

                        }

                        for(int i=0;i<users.size();i++)
                        {
                            if(users.get(i).username.equals(profile.getFirstName().toString()+profile.getLastName().toString()+"_fb")) {
                                index=i;
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
                                        username1=users.get(index).username;
                                        Toast.makeText(getActivity(),profile.getId()+" ",Toast.LENGTH_SHORT).show();
                                        String idk,ppk;
                                        idk=profile.getId().toString();
                                        iidd=idk;
                                        ppk=profile.getProfilePictureUri(50,50).toString();
                                        profilepicurl=ppk;
                                        flag=false;


                                        ParseUser.logInInBackground( profile.getFirstName().toString()+
                                                profile.getLastName().toString()+"_fb"
                                                ,profile.getFirstName().toString()

                                                , new LogInCallback() {

                                            @Override
                                            public void done(ParseUser pu, ParseException e) {

                                                if (pu != null) {
                                                    UserList.user = pu;

                                                } else {
                                                    Utils.showDialog(
                                                            getContext(),
                                                            getString(R.string.err_login) + " "
                                                                    + e.getMessage());
                                                    e.printStackTrace();
                                                }
                                            }
                                        });
                                    }

                                    else{
                                        flag=false;
                                        String fk,lk,gk,uk,idk,emk;

                                        fk=profile.getFirstName().toString();
                                        lk=profile.getLastName().toString();
                                        idk=profile.getId().toString();
                                        iidd=idk;
                                        Toast.makeText(getActivity(),profile.getId()+" ",Toast.LENGTH_SHORT).show();
                                        gk=fragment_main.g;
                                        uk=fk+lk+"_fb";


                                        final ParseUser pu = new ParseUser();
                                        pu.setPassword(fk);
                                        pu.setUsername(uk);


                                        pu.signUpInBackground(new SignUpCallback() {
                                            @Override
                                            public void done(ParseException e) {
                                                if (e == null)
                                                {
                                                    UserList.user = pu;
                                                }
                                                else
                                                {

                                                    Utils.showDialog(getContext(),
                                                            getString(R.string.err_singup) + " "
                                                                    + e.getMessage());
                                                    e.printStackTrace();
                                                }
                                            }
                                        });
                                        final String loginfburl = "http://carpoolingdata.esy.es/loginfb.php";
                                        String firstnamekey = "firstname=";
                                        String lastnamekey = "&lastname=";
                                        String genderkey = "&gender=";
                                        String usernamekey = "&username=";

                                        try {
                                            connectionparameter = firstnamekey + URLEncoder.encode(fk, "UTF-8")
                                                    +lastnamekey + URLEncoder.encode(lk, "UTF-8")
                                                    +genderkey + URLEncoder.encode(gk, "UTF-8")
                                                    +usernamekey + URLEncoder.encode(uk, "UTF-8")   ;

                                            parametersbyte=connectionparameter.getBytes("UTF-8");
                                        } catch (UnsupportedEncodingException e) {
                                            e.printStackTrace();
                                        }
                                        internetconnection con=new internetconnection(getContext());
                                        con.connect(loginfburl,parametersbyte);


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
        Intent intent = new Intent(getActivity(),triplist.class);
        startActivity(intent);

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    }

    /*private void logout() {
        LoginManager.getInstance().logOut();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();
        fragmentTransaction.replace(R.id.mainContainer, new fragment_main());
        fragmentTransaction.commit();
    }*/


}