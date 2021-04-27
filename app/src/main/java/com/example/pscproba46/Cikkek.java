package com.example.pscproba46;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

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
 * Use the {@link Cikkek#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Cikkek extends Fragment {

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

    public Cikkek() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Cikkek.
     */
    // TODO: Rename and change types and number of parameters
    public static Cikkek newInstance(String param1, String param2) {
        Cikkek fragment = new Cikkek();
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
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_cikkek, container, false);
        RecyclerView.LayoutManager managercikkek = new LinearLayoutManager(getContext());
        recyclerViewCikkek = (RecyclerView) view.findViewById(R.id.recyclecikkek);
        recyclerViewCikkek.setLayoutManager(managercikkek);
        adapter= new RecyclerViewAdapterCikkek(getContext(), mNames);
        recyclerViewCikkek.setAdapter(adapter);
        recyclerViewCikkek.setHasFixedSize(true);
        initrecview3();
        MainActivity.bar.setVisibility(View.GONE);
        recyclerViewCikkek.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) { //check for scroll down
                    visibleItemCount = managercikkek.getChildCount();
                    totalItemCount = managercikkek.getItemCount();
                    pastVisiblesItems = ((LinearLayoutManager) managercikkek).findFirstVisibleItemPosition();
                    // pastVisiblesItems = ((LinearLayoutManager) manager).findLastVisibleItemPosition();
                    if (loading) {


                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount-5 ) {
                            loading = false;
                            Log.v("...", "Last Item Wow !");
                            Retrofit retrofit = new Retrofit.Builder().baseUrl("https://playstationcommunity.hu/wp-json/wp/v2/").addConverterFactory(GsonConverterFactory.create()).build();
                            JasonPlaceHolderApiCikkek jsonPlaceHolderApiCikkek = retrofit.create(JasonPlaceHolderApiCikkek.class);
                            Call<List<CikkekAdat>> call = jsonPlaceHolderApiCikkek.getPostCikkek("pages/?page=" + pagenumber);
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
    public void initrecview3() {

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://playstationcommunity.hu/wp-json/wp/v2/").addConverterFactory(GsonConverterFactory.create()).build();
        JasonPlaceHolderApiCikkek jsonPlaceHolderApiCikkek = retrofit.create(JasonPlaceHolderApiCikkek.class);
        Call<List<CikkekAdat>> call = jsonPlaceHolderApiCikkek.getPostCikkek("pages/?page=" +pagenumber );
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
}