package com.example.ibrahim.carpooling;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class triplistadapter extends RecyclerView.Adapter<triplistadapter.triplistholder>
{
    List<trip> tripList;
    Context context;
    public  triplistadapter(Context context,List<trip> tripList)
    {
        this.tripList=tripList;
        this.context=context;
        int s=tripList.size();

        String ss=""+s;
        Log.d("mo",ss);
    }
    @Override
    public triplistholder onCreateViewHolder(ViewGroup parent, int viewtype) {

        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.tripitem,parent,false);
        triplistholder holder =new triplistholder(row);
        return holder;
    }

    @Override
    public void onBindViewHolder(triplistholder holder, int position) {

        trip t=tripList.get(position);
        holder.name.setText(t.creatorname);
        holder.from.setText(t.source);
        holder.to.setText(t.destination);
        holder.createdtime.setText("Created AT : \n" + t.createdtime);
        if(t.fbid.equals("nofbid")) {
            Glide.with(context).load("http://carpoolingdata.esy.es/pictures/" + t.creatorusername + ".PNG").into(holder.pic);
        }
        else
        {
            Glide.with(context)
                    .load("https://graph.facebook.com/" + t.fbid + "/picture?type=large")
                    .transform(new CircleTransform(context))
                    .into(holder.pic);
        }
    }


    @Override
    public int getItemCount() {
        return tripList.size();

    }

    class triplistholder extends  RecyclerView.ViewHolder
    {

        TextView name , from , to , createdtime;
        ImageView pic;
        public triplistholder(View itemView) {
            super(itemView);

            name=(TextView)itemView.findViewById(R.id.name_triplist);
            from=(TextView)itemView.findViewById(R.id.source_triplist);
            to=(TextView)itemView.findViewById(R.id.destination_triplist);
            createdtime=(TextView)itemView.findViewById(R.id.date_triplist);

            pic=(ImageView)itemView.findViewById(R.id.profile_pic_triplist);
        }
    }//class triplistholder
}//class triplistadapter
