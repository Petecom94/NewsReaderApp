package com.example.pscproba46;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Menetrend#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Menetrend extends Fragment {
    String title;
    String kepurl;
    String Setvisible;
    View view2;
    public static CardView TwitchcardView;
    public static ConstraintLayout cardlayout;
   public static TextView cimteszt;
   public static ImageButton twitchButton;
    Boolean twitchTrue;
    Context context;
    private static final String TAG = "sddfsfsd";
    public ArrayList<EventMenetrend> mMenetrend = new ArrayList<>();
   // MenetrendAdat events = new ArrayList<>();
    RecyclerView recyclerViewMenetrend;
    RecyclerViewAdapterMenetrend adapterMenetrend;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Menetrend() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Menetrend.
     */
    // TODO: Rename and change types and number of parameters
    public static Menetrend newInstance(String param1, String param2) {
        Menetrend fragment = new Menetrend();
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

        View view = inflater.inflate(R.layout.fragment_menetrend, container, false);
        RecyclerView.LayoutManager managerMenetrend = new LinearLayoutManager(getContext());
        recyclerViewMenetrend = (RecyclerView) view.findViewById(R.id.menetrendrec);
        recyclerViewMenetrend.setLayoutManager(managerMenetrend);
        adapterMenetrend = new RecyclerViewAdapterMenetrend(getContext(), mMenetrend);

        recyclerViewMenetrend.setAdapter(adapterMenetrend);
ItemDecorationRecycler itemDecorationRecycler= new ItemDecorationRecycler(20);
        ItemDecorationRecycler itemDecorationRecycler2= new ItemDecorationRecycler(-20);
recyclerViewMenetrend.addItemDecoration(itemDecorationRecycler);
recyclerViewMenetrend.addItemDecoration(itemDecorationRecycler,0);
        recyclerViewMenetrend.setHasFixedSize(true);
        //Context context= container.getContext();
        initrecview2();
        twitchButton=view.findViewById(R.id.TwitchButton);
        twitchButton.setVisibility(View.INVISIBLE);
        TwitchcardView = view.findViewById(R.id.TwitchcardView);
        TwitchcardView.setVisibility(View.INVISIBLE);
        cardlayout= view.findViewById(R.id.Twitchlayout);
        cardlayout.setVisibility(View.INVISIBLE);
        cimteszt=view.findViewById(R.id.TwitchtextView);
        cimteszt.setVisibility(View.INVISIBLE);
MainActivity.bar.setVisibility(View.VISIBLE);


//recyclerViewMenetrend.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT));
    return view;

    }

    public void initrecview2()  {


        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://playstationcommunity.hu/wp-json/tribe/events/").addConverterFactory(GsonConverterFactory.create()).build();
        JasonPlaceHolderApiMenetrend jsonPlaceHolderApiMenetrend = retrofit.create(JasonPlaceHolderApiMenetrend.class);

        Call<MenetrendAdat> call = jsonPlaceHolderApiMenetrend.getPosts("v1/events");
        call.enqueue(new Callback<MenetrendAdat>() {


            @Override
            public void onResponse(Call<MenetrendAdat> call, Response<MenetrendAdat> response) {

                mMenetrend = (ArrayList<EventMenetrend>) response.body().getEvents();

               adapterMenetrend.setmImageNames(mMenetrend);


            }

            @Override
            public void onFailure(Call<MenetrendAdat> call, Throwable t) {

            }
        });


    }


}