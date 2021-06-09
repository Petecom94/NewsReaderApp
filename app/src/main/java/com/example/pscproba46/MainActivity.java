package com.example.pscproba46;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
TextView kedvencek;
    Fragment fragment;
    Fragment fragment4=new WebViewFragment();
    Fragment fragment5= new WebviewFragmentCikkek();
    Fragment fragment6= new Beallitas();
   public static ProgressBar bar;
    public static SharedPreferences sharedPref;
   public static SharedPreferences.Editor editor;

  FragmentManager fragmentManager = getSupportFragmentManager();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        // define your fragments here
        final Fragment fragment1 = new Cikkek();
        final Fragment fragment2 = new Home();
        final Fragment fragment3 = new Menetrend();

        RecyclerViewAdapter recicle= new RecyclerViewAdapter(this);
 kedvencek = findViewById(R.id.textKedvencek);

         bar= findViewById(R.id.progressBar);
        bar.setVisibility(View.VISIBLE);

        Context context = this.getApplicationContext();
        sharedPref = context.getSharedPreferences(
                "SötétMód", context.MODE_PRIVATE);

         editor = sharedPref.edit();
        editor.putBoolean("Sötétmód",true);

        if(sharedPref.getBoolean("Sötétmód",true)){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        }else{

            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        }



        fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment1).commit();

        // handle navigation selection
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                       // Fragment fragment;
                        switch (item.getItemId()) {
                            case R.id.like:
                                fragment = fragment1;
                               // getSupportFragmentManager().popBackStack();

                                break;
                            case R.id.share:
                            default:
                                fragment = fragment2;


                               // getSupportFragmentManager().popBackStack();
                                break;
                            case R.id.about:

                               // getSupportFragmentManager().popBackStack();
                                fragment = fragment3;
                                break;

                            case R.id.beallitas:
                                fragment=fragment6;
                                break;
                        }
                        fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
                        return true;
                    }
                });
        // Set default selection
        bottomNavigationView.setSelectedItemId(R.id.menetrend);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

    }
/*  @Override
    public void onBackPressed(){
        fragmentManager= getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() == 0) {

            fragmentManager.popBackStack();
        } else {

            super.onBackPressed();
        }
    }*/
    }
