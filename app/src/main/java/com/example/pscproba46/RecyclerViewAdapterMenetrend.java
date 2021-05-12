package com.example.pscproba46;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class RecyclerViewAdapterMenetrend extends RecyclerView.Adapter<RecyclerViewAdapterMenetrend.ViewHolderMenetrend> {
    SimpleDateFormat sdf;
    private static final String TAG = "RecyclerViewAdapter";
    private ArrayList<EventMenetrend> mMenetrend;
    Date date;
    Date dkezdes;
    Date dvege;
    Menetrend menetrend;
    private Context mContext2;
    Button twitcButton;


// az interface get metódusa


    public RecyclerViewAdapterMenetrend(Context mContext, ArrayList<EventMenetrend> mImagesNames ) {
        this.mContext2 = mContext;
        this.mMenetrend = mImagesNames;
        notifyDataSetChanged();

    }


    public void setmImageNames(ArrayList<EventMenetrend> mImageNames) {
        this.mMenetrend = mImageNames;
        notifyDataSetChanged();
    }

    public ArrayList<EventMenetrend> getmIMenetrendadat() {
        return mMenetrend;
    }

    @NonNull
    @Override
    public ViewHolderMenetrend onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.viewmenetrend,parent,false);
       ViewHolderMenetrend holder=new ViewHolderMenetrend(view)       ;

        return holder;
    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ViewHolderMenetrend holder, int position) {
//holder.texteloado.setText(mMenetrend.get(position).getTitle());
//holder.textkezdes.setText(mMenetrend.get(position).getEventss());
Date currentime = Calendar.getInstance().getTime();

        //currentime.compareTo(sdf.parse(mMenetrend.get(position).getEnd_date()))>0
         sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");



        try {
           if(currentime.compareTo(sdf.parse(mMenetrend.get(position).getEnd_date()))>0){
               //holder.textkezdes.setText(mMenetrend.get(position).getStart_date());
               holder.textvege.setText("Vége a streamnek");
               holder.textvege.setTextColor(Color.RED);
               holder.ButtonCalendar.setVisibility(View.GONE);
               Date date= sdf.parse(mMenetrend.get(position).getEnd_date());
               date.getTime();

               menetrend = new Menetrend();






               //  Menetrend.TwitchcardView.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, 250));
               // menetrend.recyclerViewMenetrend.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.));

           }  if(currentime.compareTo(sdf.parse(mMenetrend.get(position).getStart_date()))>0 && currentime.compareTo(sdf.parse(mMenetrend.get(position).getEnd_date()))<0){

                    holder.textkezdes.setText(mMenetrend.get(position).getStart_date());


                    holder.textvege.setTextColor(Color.GREEN);
               holder.textvege.setText("Éppen zajlik");
               Menetrend.twitchButton.setVisibility(View.VISIBLE);
               Menetrend.cimteszt.setVisibility(View.VISIBLE);

               Menetrend.cimteszt.setText(mMenetrend.get(position).getTitle());
               Menetrend.cardlayout.setVisibility(View.VISIBLE);
               Menetrend.TwitchcardView.setVisibility(View.VISIBLE);

               Menetrend.twitchButton.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       Uri uri = Uri.parse("https://www.twitch.tv/pschungary");

                       Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

                       likeIng.setPackage("tv.twitch.android.viewer");

                       try {
                           mContext2.startActivity(likeIng);
                       } catch (ActivityNotFoundException e) {
                           mContext2.startActivity(new Intent(Intent.ACTION_VIEW,
                                   Uri.parse("https://www.twitch.tv/pschungary"  )));
                       }
                   }
               });

               RequestOptions reqOpt = RequestOptions
                       .fitCenterTransform()
                       .transform(new RoundedCorners(5))
                       .diskCacheStrategy(DiskCacheStrategy.ALL) // It will cache your image after loaded for first time
                       .override(holder.imagekepmenetrend.getWidth(),holder.imagekepmenetrend.getWidth());

               Glide.with(Menetrend.TwitchcardView.getContext())
                       .load(mMenetrend.get(position).getImage().getUrl())

                       .into(new CustomTarget<Drawable>() {
                           @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                           @Override
                           public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                               Menetrend.TwitchcardView.setBackground(resource);
                           }

                           @Override
                           public void onLoadCleared(@Nullable Drawable placeholder) {

                           }
                       });
holder.ButtonCalendar.setVisibility(View.INVISIBLE);
           }


           else

               holder.textvege.setText("Vége: "+mMenetrend.get(position).getEnd_date());
            holder.textkezdes.setText("Kezdés: "+mMenetrend.get(position).getStart_date());
            holder.texteloado.setText(Html.fromHtml(mMenetrend.get(position).getTitle()));

            RequestOptions reqOpt = RequestOptions
                    .fitCenterTransform()
                    .transform(new RoundedCorners(5))
                    .diskCacheStrategy(DiskCacheStrategy.ALL) // It will cache your image after loaded for first time
                    .override(holder.imagekepmenetrend.getWidth(),holder.imagekepmenetrend.getHeight()); // Overrides size of downloaded image and converts it's bitmaps to your desired image size;
            Glide.with(mContext2).asBitmap().load(mMenetrend.get(position).getImage().getUrl()).apply(reqOpt).into(holder.imagekepmenetrend);



           holder.ButtonCalendar.setOnClickListener(new View.OnClickListener() {
                @Override
               public void onClick(View v) {

                    sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    try {
                         dkezdes = sdf.parse(mMenetrend.get(position).getStart_date());
                       dvege=sdf.parse(mMenetrend.get(position).getEnd_date());
                        Calendar beginTime = Calendar.getInstance();
                        beginTime.setTime(dkezdes);
                        //beginTime.set(2021,4,11,15,20);
                        Calendar endTime = Calendar.getInstance();
                        endTime.setTime(dvege);
                        // endTime.set(2021, 4, 11, 16, 30);
                        Intent intent = new Intent(Intent.ACTION_INSERT)
                                .setData(CalendarContract.Events.CONTENT_URI)
                                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
                                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis())
                                .putExtra(CalendarContract.Events.TITLE, mMenetrend.get(position).getTitle())
                                .putExtra(CalendarContract.Events.DESCRIPTION, mMenetrend.get(position).getTitle())
                                .putExtra(CalendarContract.Events.EVENT_LOCATION, "Twitch")
                                .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY);

                        mContext2.startActivity(intent);



                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                }
            });

        } catch (ParseException e) {
            e.printStackTrace();
        }



    }

    @Override
    public int getItemCount() {
       
        return mMenetrend.size();
    }




    public class ViewHolderMenetrend extends RecyclerView.ViewHolder {
        ImageView imagekepmenetrend;
        TextView textkezdes;
        TextView textvege;
        TextView texteloado;
        Button ButtonCalendar;
        ConstraintLayout parentmenetrend_layout;


        public ViewHolderMenetrend(@NonNull View itemView) {
            super(itemView);
            imagekepmenetrend= itemView.findViewById(R.id.imagekepmenetrend);
            textkezdes=itemView.findViewById(R.id.textkezdes);
            textvege=itemView.findViewById(R.id.textvege);
            texteloado=itemView.findViewById(R.id.texteloado);
            ButtonCalendar=itemView.findViewById(R.id.ButtonCalendar);
            parentmenetrend_layout=itemView.findViewById(R.id.parentmenetrend_layout);

        }
    }

}
