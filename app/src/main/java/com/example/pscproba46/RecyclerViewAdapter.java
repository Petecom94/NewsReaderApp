package com.example.pscproba46;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
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
import com.google.gson.Gson;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";
    private ArrayList<Post> mImageNames;
public  ArrayList<String> multiarray= new ArrayList<>();
    private Context mContext;
MainActivity asd;

// az interface get metódusa


    public RecyclerViewAdapter(Context mContext, ArrayList<Post> mImagesNames ) {
        this.mContext = mContext;
        this.mImageNames = mImagesNames;
       //notifyDataSetChanged();
        setHasStableIds(true);
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
        holder.setIsRecyclable(false);

          // holder.textfocim.setText(mImageNames.get(position).getTitle().getTitle().replace("&#8211;",""));
        holder.textfocim.setText(Html.fromHtml(mImageNames.get(position).getTitle().getTitle()));
        holder.textdatum.setText(mImageNames.get(position).getFormatted_date());
        holder.textszerzo.setText(mImageNames.get(position).getAuthor_meta().getAuthor());
        holder.alcim.setText(Html.fromHtml(mImageNames.get(position).getExcerpt().getRendered()));
        RequestOptions reqOpt = RequestOptions
                .fitCenterTransform()
                //.transform(new RoundedCorners(5))
                .diskCacheStrategy(DiskCacheStrategy.ALL) // It will cache your image after loaded for first time
                .override(holder.kepview.getWidth(),holder.kepview.getWidth()); // Overrides size of downloaded image and converts it's bitmaps to your desired image size;
        Glide.with(mContext).load(mImageNames.get(position).getimgLink()).apply(reqOpt).placeholder(R.drawable.defaultpsc).into(holder.kepview);




       if(multiarray.contains(mImageNames.get(position).getId().toString())){
            holder.kedvencButton.setBackgroundColor(Color.GREEN);


        }


            holder.kedvencButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!multiarray.contains(mImageNames.get(position).getId().toString())) {
                        holder.kedvencButton.setBackgroundColor(Color.GREEN);

                        multiarray.add(mImageNames.get(position).getId());
                        Home.textKedvencek.setText("Kedvenceim(" + multiarray.size() + ")");


                    }else if(multiarray.contains(mImageNames.get(position).getId().toString())){
                        holder.kedvencButton.setBackgroundColor(Color.RED);
                        multiarray.remove(mImageNames.get(position).getId());
                        Home.textKedvencek.setText("Kedvenceim(" + multiarray.size() + ")");
                    }
                }

            });







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

   @Override
    public long getItemId(int position)
    {
        return position;
    }


    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageButton kedvencButton;
        ImageView kepview;
        TextView textfocim;
        TextView textalcim;
        TextView textszerzo;
        TextView textdatum;
        TextView textkategoria;
        TextView alcim;
        ConstraintLayout parentlayout;
        LinearLayout linTextParent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            kedvencButton=itemView.findViewById(R.id.imageButton);
            alcim=itemView.findViewById(R.id.textAlcim);
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
