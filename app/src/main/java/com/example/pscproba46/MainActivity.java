package com.example.pscproba46;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
TextView kedvencek;
    Fragment fragment;
   public static ProgressBar bar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final FragmentManager fragmentManager = getSupportFragmentManager();
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        // define your fragments here
        final Fragment fragment1 = new Cikkek();
        final Fragment fragment2 = new Home();
        final Fragment fragment3 = new Menetrend();
        RecyclerViewAdapter recicle= new RecyclerViewAdapter(this);
 kedvencek = findViewById(R.id.textKedvencek);

         bar= findViewById(R.id.progressBar);
        bar.setVisibility(View.VISIBLE);




        fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment1).commit();

        // handle navigation selection
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                       // Fragment fragment;
                        switch (item.getItemId()) {
                            case R.id.dashboard:
                                fragment = fragment1;

                                break;
                            case R.id.home:
                            default:
                                fragment = fragment2;

                                break;
                            case R.id.about:

                                fragment = fragment3;
                                break;
                        }
                        fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
                        return true;
                    }
                });
        // Set default selection
        bottomNavigationView.setSelectedItemId(R.id.menetrend);
    }
    }
