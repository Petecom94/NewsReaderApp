package com.example.pscproba46;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.MenuPopupWindow;
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

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";
    private ArrayList<Post> mImageNames;
public  ArrayList<String> multiarray= new ArrayList<>();
    private Context mContext;
MainActivity asd;


// az interface get metódusa


    public RecyclerViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

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




       if(getAllSavedMyIds(mContext).contains(mImageNames.get(position).getId())){
           holder.kedvencButton.setImageResource(R.drawable.ic_baseline_favorite_24);


        }


            holder.kedvencButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (!getAllSavedMyIds(mContext).contains(mImageNames.get(position).getId())) {
                       holder.kedvencButton.setImageResource(R.drawable.ic_baseline_favorite_24);

multiarray =getAllSavedMyIds(mContext);
                     multiarray.add(mImageNames.get(position).getId());
                        saveMyIDs(mContext,multiarray);

                        Home.textKedvencek.setText("Kedvenceim(" +  getAllSavedMyIds(mContext).size()+ ")");


                    }else if(getAllSavedMyIds(mContext).contains(mImageNames.get(position).getId())){
                        holder.kedvencButton.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                        getAllSavedMyIds(mContext).remove(mImageNames.get(position).getId());
                        multiarray =getAllSavedMyIds(mContext);
                        multiarray.remove(mImageNames.get(position).getId());


                        saveMyIDs(mContext,multiarray);

                        Home.textKedvencek.setText("Kedvenceim(" + getAllSavedMyIds(mContext).size()+ ")");

                    }
                }

            });







           holder.parentlayout.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                  // Fragment web= new WebViewFragment();
                   MainActivity main= new MainActivity();


                   Bundle bundle = new Bundle();
                   if(getAllSavedMyIds(mContext).contains(mImageNames.get(position).getId())){

                      bundle.putBoolean("boolean",true);
                   }
                   bundle.putString("image", mImageNames.get(position).getLink());
                   bundle.putString("title",mImageNames.get(position).getTitle().getTitle());
                   bundle.putString("imageLink",mImageNames.get(position).getimgLink());


                   main.fragment4.setArguments(bundle);//Here pass your data

                   FragmentManager manager = ((AppCompatActivity)mContext).getSupportFragmentManager();
                          manager.beginTransaction().replace(R.id.fragment_container,main.fragment4)
                                  .addToBackStack(null)

                                  .commit();




                /*Intent intent =new Intent(Intent.ACTION_VIEW);
               intent.setData(Uri.parse(mImageNames.get(position).getLink()));
                
                mContext.startActivity(intent);*/


               }


           });
      
        MainActivity.bar.setVisibility(View.GONE);
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



    public static ArrayList<String> getAllSavedMyIds(Context context) {
        ArrayList<String> savedCollage = new ArrayList<String>();
        SharedPreferences mPrefs = context.getSharedPreferences("PhotoCollage", context.MODE_PRIVATE);
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
        SharedPreferences mPrefs = context.getSharedPreferences("PhotoCollage", context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(collageList);
        prefsEditor.putString("myJson", json);
        prefsEditor.commit();
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
