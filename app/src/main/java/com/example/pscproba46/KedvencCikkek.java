package com.example.pscproba46;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class KedvencCikkek extends Fragment {
    Fragment fragment= null;
    ArrayList<List> items;
    private final String KEY_RECYCLER_STATE = "recycler_state";
    private Parcelable mListState;

    RecyclerView recyclerViewCikkek;
    JasonPlaceHolderApi jsonPlaceHolderApi;
    public ArrayList<CikkekAdat> mNames = new ArrayList<>();

    public ArrayList<HashMap> hirek;
    ArrayList<HashMap<String, String>> contactList;
    public int pagenumber = 1;
    RecyclerViewAdapterCikkek adapter;
    public static TextView textkedvencikkek;
    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    Button buttonCikkekUp;
    private String TAG;
    private Object view;
    ProgressBar progress;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_cikkek,container,false);

        RecyclerView.LayoutManager managercikkek = new LinearLayoutManager(getContext());
        recyclerViewCikkek = (RecyclerView) view.findViewById(R.id.recyclecikkek);
        recyclerViewCikkek.setLayoutManager(managercikkek);
        adapter= new RecyclerViewAdapterCikkek(getContext(), mNames);
        recyclerViewCikkek.setAdapter(adapter);
        recyclerViewCikkek.setHasFixedSize(true);

        textkedvencikkek=view.findViewById(R.id.textViewKedvencek);
        MainActivity.bar.setVisibility(View.GONE);
        buttonCikkekUp=view.findViewById(R.id.buttonUpKedvencek);
        textkedvencikkek.setText("Kedvenceim");
        if(RecyclerViewAdapterCikkek.getAllSavedMyIds(getContext()).size()!=managercikkek.getItemCount()){
        initrecview3();
        }
        return view;
    }


    public void initrecview3() {

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://playstationcommunity.hu/wp-json/wp/v2/").addConverterFactory(GsonConverterFactory.create()).build();
        JasonPlaceHolderApiCikkek jsonPlaceHolderApiCikkek = retrofit.create(JasonPlaceHolderApiCikkek.class);
        Call<List<CikkekAdat>> call = jsonPlaceHolderApiCikkek.getPostCikkek("pages/?page=1&_embed&author_exclude=1&fbclid=IwAR2yxt4rGrIzq09fCabao8lUcrxbRGZ2-use7ewiEPNggYlC1B9JJ0QJ_Ws&include="+returnArray()+"&per_page=100"  );
        call.enqueue(new Callback<List<CikkekAdat>>() {
            @Override
            public void onResponse(Call<List<CikkekAdat>> call, Response<List<CikkekAdat>> response) {

                ArrayList<CikkekAdat> posts = (ArrayList<CikkekAdat>) response.body();

                for (CikkekAdat post : posts) {
                    mNames.add(post);
                }

                adapter.setmImageNames(mNames);
                adapter.notifyDataSetChanged();

            }



            @Override
            public void onFailure(Call<List<CikkekAdat>> call, Throwable t) {
                Log.d("Json",t.getMessage());
            }

        });


        pagenumber++;

    }

    public String returnArray(){
        String t="";
        for(int i=0;i<=RecyclerViewAdapterCikkek.getAllSavedMyIds(getContext()).size()-1;i++){

            t+= RecyclerViewAdapterCikkek.getAllSavedMyIds(getContext()).get(i)+",";
        }
        return t;
    }
}

