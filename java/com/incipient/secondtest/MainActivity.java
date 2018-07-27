package com.incipient.secondtest;

import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.incipient.secondtest.fragments.HomeFragment;
import com.incipient.secondtest.fragments.SplashScreenFragment;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            goToFragment();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void goToFragment() throws Exception{
        Fragment fragment = new SplashScreenFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frame, fragment)
                .commit();



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Fragment fragment = new HomeFragment();
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.frame, fragment)
                        .commit();


            }
        }, 1000);
    }
}
