package com.example.ibrahim.carpooling;

public class user
{
    int id,indexday,indexmonth,indexyear;
    String firstname,lastname,password,email,username,birthdate,gender,job,address,type,nationalid,phonenumber;
    public  user(String firstname,String lastname,String password,String username,int id)
    {
        this.firstname=firstname;
        this.lastname=lastname;
        this.password=password;
        this.username=username;
        this.id=id;
    }
    public  user(String username)
    {
        this.username=username;
    }


    public  user(int id,String firstname,String lastname,String email,String password,String username
            ,String gender,String birthdate,String job,String address,String nationalid,String phonenumber,String type,
                 int indexday,int indexmonth,int indexyear)
    {
        this.id=id;
        this.firstname=firstname;
        this.lastname=lastname;
        this.email=email;
        this.password=password;
        this.username=username;
        this.gender=gender;
        this.birthdate=birthdate;
        this.job=job;
        this.address=address;
        this.nationalid=nationalid;
        this.phonenumber=phonenumber;
        this.type=type;
        this.indexday=indexday;
        this.indexmonth=indexmonth;
        this.indexyear=indexyear;
    }

}