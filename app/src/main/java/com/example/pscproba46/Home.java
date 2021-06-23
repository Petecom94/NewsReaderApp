package com.example.pscproba46;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Home extends Fragment {
    public static TextView textKedvencek;
    RecyclerView.LayoutManager manager;
    Fragment fragment= null;
    ArrayList<List> items;
    private final String KEY_RECYCLER_STATE = "recycler_state";
    private Parcelable mListState;

    RecyclerView recyclerView;
    JasonPlaceHolderApi jsonPlaceHolderApi;
    public ArrayList<Post> mNames = new ArrayList<>();

    public ArrayList<HashMap> hirek;
    ArrayList<HashMap<String, String>> contactList;
    public int pagenumber = 1;
    public static  RecyclerViewAdapter adapter;
    public boolean clicked= true;
    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;

    private String TAG;
    private Object view;
    ProgressBar progress;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Home() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Home.
     */
    // TODO: Rename and change types and number of parameters
    public static Home newInstance(String param1, String param2) {
        Home fragment = new Home();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);
        Button buttonUp= view.findViewById(R.id.buttonUp);
        buttonUp.setVisibility(view.GONE);
         manager = new LinearLayoutManager(getContext());
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerv_view);
        recyclerView.setLayoutManager(manager);
        adapter = new RecyclerViewAdapter(getContext(), mNames);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        initrecview();
        MainActivity.bar.setVisibility(View.VISIBLE);

textKedvencek= view.findViewById(R.id.textKedvencek);

        textKedvencek.setText("Kedvenceim"+ "("+RecyclerViewAdapter.getAllSavedMyIds(getContext()).size()+")" );

textKedvencek.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        KedvencHirek nextFrag= new KedvencHirek();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, nextFrag,"HometoWeb")

                .addToBackStack(null)
                .commit();

//.addToBackStack("null")
    }
});





  recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                returnArray(RecyclerViewAdapter.getAllSavedMyIds(getContext()));

              adapter.notifyDataSetChanged();
               if(recyclerView.getScrollState()==0){
                buttonUp.setVisibility(View.VISIBLE);
                   buttonUp.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {

                           buttonUp.setVisibility(view.VISIBLE);
                           recyclerView.smoothScrollToPosition(0);



                       }
                   });

               }else{
                   buttonUp.setVisibility(View.GONE);}
                if (dy > 0) { //check for scroll down
                    visibleItemCount = manager.getChildCount();
                    totalItemCount = manager.getItemCount();
                    pastVisiblesItems = ((LinearLayoutManager) manager).findFirstVisibleItemPosition();
                    // pastVisiblesItems = ((LinearLayoutManager) manager).findLastVisibleItemPosition();
                    if (loading) {


                        if ((visibleItemCount + pastVisiblesItems)>= totalItemCount-5 ) {
                            loading = false;
                            Log.v("...", "Last Item Wow !");
                            Retrofit retrofit = new Retrofit.Builder().baseUrl("https://playstationcommunity.hu/wp-json/wp/v2/").addConverterFactory(GsonConverterFactory.create()).build();
                            JasonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JasonPlaceHolderApi.class);
                            Call<List<Post>> call = jsonPlaceHolderApi.getPost("posts/?page=" + pagenumber);
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

                            loading = true;
                            pagenumber++;

                        }
                    }
                }
            }
        });
return view;
    }

    public void initrecview() {


        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://playstationcommunity.hu/wp-json/wp/v2/").addConverterFactory(GsonConverterFactory.create()).build();
        JasonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JasonPlaceHolderApi.class);
        //Call<List<Post>> call = jsonPlaceHolderApi.getPost("posts/?page=1&_embed&fbclid=IwAR0VKgpA9hN38Dhajrt4Dk4ba1LOoIrb9Wn2i2sDjg4zkFEP8Kb3vsDu7IQ&include=" + returnArray(RecyclerViewAdapter.getAllSavedMyIds(getContext())));
        Call<List<Post>> call = jsonPlaceHolderApi.getPost("posts/?page=" + pagenumber);

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
        pagenumber++;



    }

    public void initrecview2() {


        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://playstationcommunity.hu/wp-json/wp/v2/").addConverterFactory(GsonConverterFactory.create()).build();
        JasonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JasonPlaceHolderApi.class);
        Call<List<Post>> call = jsonPlaceHolderApi.getPost("posts/?page=1&_embed&fbclid=IwAR0VKgpA9hN38Dhajrt4Dk4ba1LOoIrb9Wn2i2sDjg4zkFEP8Kb3vsDu7IQ&include=" + returnArray(RecyclerViewAdapter.getAllSavedMyIds(getContext())));
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






    public String returnArray(ArrayList array){
        String t="";
     for(int i=0;i<array.size();i++){

        t+= array.get(i)+",";
     }
        return t;
    }
}