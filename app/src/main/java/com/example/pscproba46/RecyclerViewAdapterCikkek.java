package com.example.pscproba46;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class RecyclerViewAdapterCikkek extends RecyclerView.Adapter<RecyclerViewAdapterCikkek.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";
    private ArrayList<CikkekAdat> mImageNames;

    private Context mContext;
MainActivity asd;

// az interface get metódusa


    public RecyclerViewAdapterCikkek(Context mContext, ArrayList<CikkekAdat> mImagesNames ) {
        this.mContext = mContext;
        this.mImageNames = mImagesNames;
        notifyDataSetChanged();

    }


    public void setmImageNames(ArrayList<CikkekAdat> mImageNames) {
        this.mImageNames = mImageNames;
        notifyDataSetChanged();
    }

    public ArrayList<CikkekAdat> getmImageNames() {
        return mImageNames;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.viewcikkek,parent,false);
       ViewHolder holder=new ViewHolder(view)       ;

        return holder;
    }



    @Override                         
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
           Log.d(TAG, "onCreateViewHolder: ");

          // holder.textfocim.setText(mImageNames.get(position).getTitle().getTitle().replace("&#8211;",""));
        holder.textfocimcikkek.setText(Html.fromHtml(mImageNames.get(position).getTitle().getRendered()));
           holder.textdatumcikkek.setText(mImageNames.get(position).getFormatted_date());
        holder.textszerzocikkek.setText(mImageNames.get(position).getAuthor_meta().getDisplay_name());

        RequestOptions reqOpt = RequestOptions
                .fitCenterTransform()
                //.transform(new RoundedCorners(5))
                .diskCacheStrategy(DiskCacheStrategy.ALL)   // It will cache your image after loaded for first time
                .override(holder.kepviewcikkek.getWidth(),holder.kepviewcikkek.getWidth()); // Overrides size of downloaded image and converts it's bitmaps to your desired image size;
        Glide.with(mContext).load(mImageNames.get(position).getFimg_url()).apply(reqOpt).placeholder(R.drawable.defaultpsc)
                .into(holder.kepviewcikkek);


Log.e("ID cikkek:",mImageNames.get(position).getId());
           holder.parentlayoutcikkek.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {


                Intent intent =new Intent(Intent.ACTION_VIEW);
               intent.setData(Uri.parse(mImageNames.get(position).getLink()));
                
                mContext.startActivity(intent);


               }


           });


    }

    @Override
    public int getItemCount() {
       
        return mImageNames.size();
    }




    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView kepviewcikkek;
        TextView textfocimcikkek;
        TextView textalcimcikkek;
        TextView textszerzocikkek;
        TextView textdatumcikkek;
        TextView textkategoriacikkek;
        ConstraintLayout parentlayoutcikkek;
        LinearLayout linTextParentcikkek;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            kepviewcikkek=itemView.findViewById(R.id.imagekepcikkek);
            textfocimcikkek=itemView.findViewById(R.id.textkezdescikkek);
            parentlayoutcikkek=itemView.findViewById(R.id.parentcikkek_layout)   ;
            textszerzocikkek=itemView.findViewById(R.id.textszerzocikkek);
            textdatumcikkek=itemView.findViewById(R.id.textdatumcikkek);
            textkategoriacikkek=itemView.findViewById(R.id.texthirekcikkek);
            linTextParentcikkek=itemView.findViewById(R.id.textleirascikkek);
            //Intent intent =new Intent(itemView.getContext(),MyWebview.class);

        }
    }
}
