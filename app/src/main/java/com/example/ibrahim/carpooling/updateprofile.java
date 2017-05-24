package com.example.ibrahim.carpooling;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class    updateprofile extends ActionBarActivity
{
    static final int RESULT_LOAD_IMAGE=1;

    triplist t = new triplist();
    profile f = new profile();
    EditText efirstname, elastname, eemail,ejob,eaddress,ephonenumber;
    Spinner sday, smonth, syear;
    RadioGroup  rg_type;
    RadioButton  rtype,rpassenger,rdriver;
    ImageView profilepic;
    Button uploadimg;



    String connectionparameter;
    String connectionparameter2;
    byte[] parametersbyte;
    byte[] parametersbyte2;

    public static Bitmap getRoundedRectBitmap(Bitmap bitmap, int pixels) {
        Bitmap result = null;
        try {
            result = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(result);

            int color = 0xff424242;
            Paint paint = new Paint();
            Rect rect = new Rect(0, 0, 100, 100);

            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(color);
            canvas.drawCircle(50, 50, 50, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(bitmap, rect, rect, paint);

        } catch (NullPointerException e) {
        } catch (OutOfMemoryError o) {
        }
        return result;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==RESULT_LOAD_IMAGE && resultCode==RESULT_OK&&data!=null)
        {
            Uri selectedImage = data.getData();
            Bitmap mBitmap = null;
            try {
                mBitmap= MediaStore.Images.Media.getBitmap(this.getContentResolver(),selectedImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Bitmap resized = Bitmap.createScaledBitmap(mBitmap, 100, 100, true);
            Bitmap conv_bm = getRoundedRectBitmap(resized,100);
            profilepic.setImageBitmap(conv_bm);
        }
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.updateprofile);
        profilepic=(ImageView)findViewById(R.id.profilepic);
        efirstname=(EditText)findViewById(R.id.firstname);
        elastname=(EditText)findViewById(R.id.lastname);
        eemail=(EditText)findViewById(R.id.email);
        ejob=(EditText)findViewById(R.id.job);
        eaddress=(EditText)findViewById(R.id.address);
        ephonenumber=(EditText)findViewById(R.id.phonenumber);
        //spinner
        sday=(Spinner)findViewById(R.id.sp_day);
        smonth=(Spinner)findViewById(R.id.sp_month);
        syear=(Spinner)findViewById(R.id.sp_year);
        //Radiogroup
        rg_type=(RadioGroup)findViewById(R.id.radiogrouptype);
        rdriver=(RadioButton) findViewById(R.id.driver);
        rpassenger=(RadioButton) findViewById(R.id.passenger);

        uploadimg=(Button)findViewById(R.id.uploadimagebutton);


        uploadimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent,RESULT_LOAD_IMAGE);
            }
        });
        if (MainActivity.logorfb==0)
        {
            uploadimg.setVisibility(View.INVISIBLE);
        }

        profilepic.setImageDrawable(f.pp.getDrawable());
        efirstname.setText(t.ufirstname);
        elastname.setText(t.ulastname);
        eemail.setText(t.uemail);
        ephonenumber.setText(t.uphonenumber);
        eaddress.setText(t.uaddress);
        ejob.setText(t.ujob);
        if (t.utype.equals("driver"))
        {
            rdriver.setChecked(true);
        }
        if (t.utype.equals("passenger"))
        {
            rpassenger.setChecked(true);
        }
        sday.setSelection(t.uindexday);
        smonth.setSelection(t.uindexmonth);
        syear.setSelection(t.uindexyear);
        if(MainActivity.logorfb==1)
        {
            Glide.with(updateprofile.this).load("http://carpoolingdata.esy.es/pictures/" + t.uusername + ".PNG").into(profilepic);
        }
        else
        {
            Glide.with(updateprofile.this)
                    .load("https://graph.facebook.com/" + HomeFacebook.iidd + "/picture?type=large")
                    .transform(new CircleTransform(updateprofile.this))
                    .into(profilepic);
        }

    }

    public void updateProfile( View view)
    {
        internetconnection con=new internetconnection(updateprofile.this);
        if(MainActivity.logorfb==1) {
            Bitmap image = ((BitmapDrawable) profilepic.getDrawable()).getBitmap();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
            String nk = t.uusername;
            final String savepicurl = "http://carpoolingdata.esy.es/savepicture%20.php";
            String namekey = "name=";
            String imagekey = "&image=";
            try {
                connectionparameter2 =
                        namekey + URLEncoder.encode(nk, "UTF-8") +
                                imagekey + URLEncoder.encode(encodedImage, "UTF-8");

                parametersbyte2 = connectionparameter2.getBytes("UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            con.connect(savepicurl, parametersbyte2);
        }


        efirstname=(EditText)findViewById(R.id.firstname);
        elastname=(EditText)findViewById(R.id.lastname);
        eemail=(EditText)findViewById(R.id.email);
        ejob=(EditText)findViewById(R.id.job);
        eaddress=(EditText)findViewById(R.id.address);
        ephonenumber=(EditText)findViewById(R.id.phonenumber);
        //spinner
        sday=(Spinner)findViewById(R.id.sp_day);
        smonth=(Spinner)findViewById(R.id.sp_month);
        syear=(Spinner)findViewById(R.id.sp_year);
        //Radiogroup
        rg_type=(RadioGroup)findViewById(R.id.radiogrouptype);


        final int selectedIdtype = rg_type.getCheckedRadioButtonId();
        rtype= (RadioButton)findViewById(selectedIdtype);

        String unk,fk,lk,ek,bk,tk,jk,addk,phk;
        int dbk,mbk,ybk;
        unk= t.uusername;
        fk=efirstname.getText().toString();
        lk=elastname.getText().toString();
        ek=eemail.getText().toString();
        jk=ejob.getText().toString();
        addk=eaddress.getText().toString();
        phk=ephonenumber.getText().toString();
        bk=sday.getSelectedItem().toString()+"/"+smonth.getSelectedItem().toString()+"/"+syear.getSelectedItem().toString();
        tk = ((RadioButton) findViewById(rg_type.getCheckedRadioButtonId())).getText().toString();
        dbk = sday.getSelectedItemPosition();
        mbk = smonth.getSelectedItemPosition();
        ybk=  syear.getSelectedItemPosition();



        final String updateprofileurl = "http://carpoolingdata.esy.es/updateprofile.php";
        String unkey="username=";
        String firstnamenamekey = "&firstname=";
        String lastnamekey = "&lastname=";
        String emailkey = "&email=";
        String birthdatekey = "&birthdate=";
        String jobkey = "&job=";
        String addresskey = "&address=";
        String phonenumberkey = "&phonenumber=";
        String typekey = "&type=";
        String indexdaykey="&indexday=";
        String indexmonthkey="&indexmonth=";
        String indexyearkey="&indexyear=";

        try {
            connectionparameter = unkey + unk
                    +firstnamenamekey + URLEncoder.encode(fk, "UTF-8") +
                    lastnamekey + URLEncoder.encode(lk, "UTF-8")+
                    emailkey + URLEncoder.encode(ek, "UTF-8")+
                    birthdatekey + URLEncoder.encode(bk, "UTF-8")
                    +jobkey + URLEncoder.encode(jk, "UTF-8")
                    +addresskey + URLEncoder.encode(addk, "UTF-8")
                    +phonenumberkey + URLEncoder.encode(phk, "UTF-8")
                    +typekey + URLEncoder.encode(tk, "UTF-8")+indexdaykey+dbk+indexmonthkey+mbk+indexyearkey+ybk;

            parametersbyte=connectionparameter.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        con.connect(updateprofileurl,parametersbyte);


        Intent intent1 = new Intent(updateprofile.this, triplist.class);
        startActivity(intent1);

    }

}