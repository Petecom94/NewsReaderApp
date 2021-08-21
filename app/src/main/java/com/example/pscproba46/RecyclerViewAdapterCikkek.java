package com.example.pscproba46;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapterCikkek extends RecyclerView.Adapter<RecyclerViewAdapterCikkek.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";
    private ArrayList<CikkekAdat> mImageNames;
    public  ArrayList<String> multiarrayKedvencek= new ArrayList<>();
    private Context mContext;
MainActivity asd;

// az interface get metódusa


    public RecyclerViewAdapterCikkek(Context mContext, ArrayList<CikkekAdat> mImagesNames ) {
        this.mContext = mContext;
        this.mImageNames = mImagesNames;
        notifyDataSetChanged();
        setHasStableIds(true);
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
        holder.setIsRecyclable(false);
          // holder.textfocim.setText(mImageNames.get(position).getTitle().getTitle().replace("&#8211;",""));
        holder.textfocimcikkek.setText(Html.fromHtml(mImageNames.get(position).getTitle().getRendered()));
           holder.textdatumcikkek.setText(mImageNames.get(position).getFormatted_date());
        holder.textszerzocikkek.setText(mImageNames.get(position).getAuthor_meta().getDisplay_name());
        holder.textalcimcikkek.setText(Html.fromHtml(mImageNames.get(position).getExcerpt().getRendered()));

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

                   MainActivity main= new MainActivity();
                   Bundle bundle = new Bundle();
                   if(getAllSavedMyIds(mContext).contains(mImageNames.get(position).getId())){

                       bundle.putBoolean("booleanCikkek",true);
                   }
                   bundle.putString("imageLinkCikkek", mImageNames.get(position).getFimg_url());
                   bundle.putString("titleCikkek",mImageNames.get(position).getTitle().getRendered());
                   bundle.putString("imageCikkek",mImageNames.get(position).getLink());
                   bundle.putString("cikkekId",mImageNames.get(position).getId());

                   main.fragment5.setArguments(bundle);//Here pass your data

                   FragmentManager manager = ((AppCompatActivity)mContext).getSupportFragmentManager();
                   manager.beginTransaction().replace(R.id.fragment_container,main.fragment5)
                           .addToBackStack(null)

                           .commit();



               /* Intent intent =new Intent(Intent.ACTION_VIEW);
               intent.setData(Uri.parse(mImageNames.get(position).getLink()));
                
                mContext.startActivity(intent);*/


               }


           });

        if(getAllSavedMyIds(mContext).contains(mImageNames.get(position).getId())){
            holder.imageKedvencek.setImageResource(R.drawable.ic_baseline_favorite_24);


        }


        holder.imageKedvencek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!getAllSavedMyIds(mContext).contains(mImageNames.get(position).getId())) {
                    holder.imageKedvencek.setImageResource(R.drawable.ic_baseline_favorite_24);
                    multiarrayKedvencek =getAllSavedMyIds(mContext);
                    multiarrayKedvencek.add(mImageNames.get(position).getId());
                    saveMyIDs(mContext,multiarrayKedvencek);
                    Toast.makeText(mContext,"Hozzáadva a kedvencekhez",Toast.LENGTH_SHORT).show();
                    Cikkek.textkedvencikkek.setText("Kedvenceim(" +  getAllSavedMyIds(mContext).size()+ ")");


                }else if(getAllSavedMyIds(mContext).contains(mImageNames.get(position).getId())){
                    holder.imageKedvencek.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                    getAllSavedMyIds(mContext).remove(mImageNames.get(position).getId());
                    multiarrayKedvencek =getAllSavedMyIds(mContext);
                    multiarrayKedvencek.remove(mImageNames.get(position).getId());
                    Toast.makeText(mContext,"Törölve a kedvencekből",Toast.LENGTH_SHORT).show();

                    saveMyIDs(mContext,multiarrayKedvencek);

                    Cikkek.textkedvencikkek.setText("Kedvenceim(" + getAllSavedMyIds(mContext).size()+ ")");

                }
            }

        });
        MainActivity.bar.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
       
        return mImageNames.size();
    }


    public static ArrayList<String> getAllSavedMyIds(Context context) {
        ArrayList<String> savedCollage = new ArrayList<String>();
        SharedPreferences mPrefs = context.getSharedPreferences("Kedvencek", context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString("myJson", "");
        if (json.isEmpty()) {
            savedCollage = new ArrayList<String>();
        } else {
            Type type = new TypeToken<ArrayList<String>>() {
            }.getType();
            savedCollage = gson.fromJson(json, type);
        }

        return savedCollage;
    }

    public static void saveMyIDs(Context context, List<String> collageList) {
        SharedPreferences mPrefs = context.getSharedPreferences("Kedvencek", context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(collageList);
        prefsEditor.putString("myJson", json);
        prefsEditor.commit();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView getTextalcimcikkek;
        ImageView imageKedvencek;
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
imageKedvencek=itemView.findViewById(R.id.imageKedvencek);
            kepviewcikkek=itemView.findViewById(R.id.imagekepcikkek);
            textfocimcikkek=itemView.findViewById(R.id.textkezdescikkek);
            parentlayoutcikkek=itemView.findViewById(R.id.parentcikkek_layout)   ;
            textszerzocikkek=itemView.findViewById(R.id.textszerzocikkek);
            textdatumcikkek=itemView.findViewById(R.id.textdatumcikkek);
            textkategoriacikkek=itemView.findViewById(R.id.texthirekcikkek);
            linTextParentcikkek=itemView.findViewById(R.id.textleirascikkek);
            textalcimcikkek=itemView.findViewById(R.id.textalcimcikkek);
            //Intent intent =new Intent(itemView.getContext(),MyWebview.class);

        }
    }
}
