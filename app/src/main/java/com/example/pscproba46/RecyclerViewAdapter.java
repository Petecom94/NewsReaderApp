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

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";
    private ArrayList<Post> mImageNames;

    private Context mContext;
MainActivity asd;

// az interface get metódusa


    public RecyclerViewAdapter(Context mContext, ArrayList<Post> mImagesNames ) {
        this.mContext = mContext;
        this.mImageNames = mImagesNames;
        notifyDataSetChanged();

    }


    public void setmImageNames(ArrayList<Post> mImageNames) {
        this.mImageNames = mImageNames;
        notifyDataSetChanged();
    }

    public ArrayList<Post> getmImageNames() {
        return mImageNames;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.view,parent,false);
       ViewHolder holder=new ViewHolder(view)       ;

        return holder;
    }



    @Override                         
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
           Log.d(TAG, "onCreateViewHolder: ");

          // holder.textfocim.setText(mImageNames.get(position).getTitle().getTitle().replace("&#8211;",""));
        holder.textfocim.setText(Html.fromHtml(mImageNames.get(position).getTitle().getTitle()));
           holder.textdatum.setText(mImageNames.get(position).getFormatted_date());
        holder.textszerzo.setText(mImageNames.get(position).getAuthor_meta().getAuthor());

        RequestOptions reqOpt = RequestOptions
                .fitCenterTransform()
                //.transform(new RoundedCorners(5))
                .diskCacheStrategy(DiskCacheStrategy.ALL) // It will cache your image after loaded for first time
                .override(holder.kepview.getWidth(),holder.kepview.getWidth()); // Overrides size of downloaded image and converts it's bitmaps to your desired image size;
        Glide.with(mContext).load(mImageNames.get(position).getimgLink()).apply(reqOpt).placeholder(R.drawable.defaultpsc).into(holder.kepview);



           holder.parentlayout.setOnClickListener(new View.OnClickListener() {
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
        ImageView kepview;
        TextView textfocim;
        TextView textalcim;
        TextView textszerzo;
        TextView textdatum;
        TextView textkategoria;
        ConstraintLayout parentlayout;
        LinearLayout linTextParent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            kepview=itemView.findViewById(R.id.imagekep);
            textfocim=itemView.findViewById(R.id.textkezdes);
            parentlayout=itemView.findViewById(R.id.parent_layout)   ;
            textszerzo=itemView.findViewById(R.id.textszerzo);
            textdatum=itemView.findViewById(R.id.textdatum);
            textkategoria=itemView.findViewById(R.id.texthirek);
            linTextParent=itemView.findViewById(R.id.textleiras);
            //Intent intent =new Intent(itemView.getContext(),MyWebview.class);

        }
    }
}
