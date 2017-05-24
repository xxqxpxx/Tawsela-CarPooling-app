package com.example.ibrahim.carpooling;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by SeifEl-Deen on 08/05/2016.
 */
public class Changepass extends Activity {
    MainActivity m = new MainActivity();
    EditText currentpass,newpass,renewpass;
    String connectionparameter;
    byte[] parametersbyte;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.changepass);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width*.8),(int)(height*.8));



    }
    public void changePass(View view)
    {
        currentpass=(EditText)findViewById(R.id.currentpass);
        newpass=(EditText)findViewById(R.id.newpass);
        renewpass=(EditText)findViewById(R.id.renewpass);

        String cp,np,cnp;
        int ik;
        ik=m.nid;
        cp=currentpass.getText().toString();
        np=newpass.getText().toString();
        cnp=renewpass.getText().toString();
        if (cp.equals(m.spasswordlogin)) {
            final String changepassurl = "http://carpoolingdata.esy.es/changepass.php";
            String currentpasskey = "currentpass=";
            String newpasskey = "&newpass=";
            String renewpasskey = "&renewpass=";
            String idkey = "&id=";
            try {
                connectionparameter =
                        currentpasskey + URLEncoder.encode(cp, "UTF-8") +
                                newpasskey + URLEncoder.encode(np, "UTF-8") +
                                renewpasskey + URLEncoder.encode(cnp, "UTF-8")+idkey + ik;

                parametersbyte = connectionparameter.getBytes("UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            internetconnection con = new internetconnection(Changepass.this);
            con.connect(changepassurl, parametersbyte);

        }
        else
        {
            Toast.makeText(Changepass.this, "Password is uncorrect", Toast.LENGTH_SHORT).show();
        }

    }
}
