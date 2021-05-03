package com.example.pscproba46;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class KedvencHirek extends Fragment{
   public static TextView textKedvencek;
    RecyclerView.LayoutManager manager;
    Fragment fragment= null;
    ArrayList<List> items;
    private final String KEY_RECYCLER_STATE = "recycler_state";
    private Parcelable mListState;
Boolean loader= false;
    RecyclerView recyclerView;
    JasonPlaceHolderApi jsonPlaceHolderApi;
    public ArrayList<Post> mNames = new ArrayList<>();
Button buttonUp;
    public ArrayList<HashMap> hirek;
    ArrayList<HashMap<String, String>> contactList;
    public int listaelem = 1;
    RecyclerViewAdapter adapter;
    public boolean clicked= true;
    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;

    private String TAG;
    private Object view;
    ProgressBar progress;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home,container,false);



       // buttonUp.setVisibility(view.GONE);
        manager = new LinearLayoutManager(getContext());
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerv_view);
        recyclerView.setLayoutManager(manager);
        adapter = new RecyclerViewAdapter(getContext(), mNames);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        initrecview2();
//buttonUp.setVisibility(View.GONE);
        MainActivity.bar.setVisibility(View.GONE);

        Home.textKedvencek.setText("Kedvenceim"+ "("+RecyclerViewAdapter.getAllSavedMyIds(getContext()).size()+")" );



        return  view;
    }


    public void initrecview2() {


        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://playstationcommunity.hu/wp-json/wp/v2/").addConverterFactory(GsonConverterFactory.create()).build();
        JasonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JasonPlaceHolderApi.class);
        Call<List<Post>> call = jsonPlaceHolderApi.getPost("posts/?page=1&_embed&fbclid=IwAR0VKgpA9hN38Dhajrt4Dk4ba1LOoIrb9Wn2i2sDjg4zkFEP8Kb3vsDu7IQ&include=" + returnArray()+"&per_page=100");
        //Call<List<Post>> call = jsonPlaceHolderApi.getPost("posts/?page=" + pagenumber);

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {

                ArrayList<Post> posts = (ArrayList<Post>) response.body();

                for (Post post : posts) {
                    mNames.add(post);


                }
                adapter.setmImageNames(mNames);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Log.e("valami", "valami nem jó");
            }

        });




    }

    public String returnArray(){
        String t="";
        for(int i=0;i<RecyclerViewAdapter.getAllSavedMyIds(getContext()).size();i++){

            t+= RecyclerViewAdapter.getAllSavedMyIds(getContext()).get(i)+",";
        }
        return t;
    }
}
