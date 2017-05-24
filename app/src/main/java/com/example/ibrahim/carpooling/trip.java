package com.example.ibrahim.carpooling;

/**
 * Created by Ibrahim on 3/30/2016.
 */
public class trip
{
    int id,creatorid;
    String source , destination , datee ,timee, frequant , modelcar , numbercar , capacitycar , costcar , smoke,creatorname,createdtime,creatorusername,fbid;


    public trip(int id,String source,String destination,String creatorname,String createdtime,String creatorusername,String fbid)
    {
        this.id=id;
        this.source=source;
        this.destination=destination;
        this.creatorname=creatorname;
        this.createdtime=createdtime;
        this.creatorusername=creatorusername;
        this.fbid=fbid;
    }
    public trip(String source,String destination,String creatorname,String datee,String timee,int id)
    {
        this.source=source;
        this.destination=destination;
        this.creatorname=creatorname;
        this.datee=datee;
        this.timee=timee;
        this.id=id;
    }
    //for item
    public  trip(int id,String source ,String destination ,String datee ,String timee ,String frequant ,String modelcar ,
                 String numbercar ,String capacitycar ,String smoke,String costcar ,String creatorname,int creatorid,String creatorusername,String fbid)//////////////
    {
        this.id=id;
        this.source=source;
        this.destination=destination;
        this.datee=datee;
        this.timee=timee;
        this.frequant=frequant;
        this.modelcar=modelcar;
        this.numbercar=numbercar;
        this.capacitycar=capacitycar;
        this.smoke=smoke;
        this.costcar=costcar;
        this.creatorname=creatorname;
        this.creatorid=creatorid;
        this.creatorusername=creatorusername;
        this.fbid=fbid;

    }//for tripdetails
}//class trip
