package com.example.pscproba46;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

public class Beallitas extends Fragment {

TextView beallit;
Switch beallitGomb;
public static TextView rendszertext;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
MainActivity.bar.setVisibility(View.INVISIBLE);
        View view= inflater.inflate(R.layout.beallitas, container, false);

        beallit= view.findViewById(R.id.beallitText);
        beallitGomb=view.findViewById(R.id.beallitGomb);




                if(MainActivity.sharedPref.getBoolean("Sötétmód",true)){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    beallitGomb.setChecked(true);

                }else{

                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        }




        beallitGomb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                if(MainActivity.sharedPref.getBoolean("Sötétmód",true)){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    MainActivity.editor.putBoolean("Sötétmód",false);
                    MainActivity.editor.apply();
                    getActivity().recreate();

                }else{

                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    MainActivity.editor.putBoolean("Sötétmód",true);
                    MainActivity.editor.apply();
                    getActivity().recreate();
                }


            }
        });
        return view;
    }
}
