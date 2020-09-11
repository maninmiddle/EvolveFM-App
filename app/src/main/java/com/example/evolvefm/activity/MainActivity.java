package com.example.evolvefm.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.evolvefm.R;
import com.example.evolvefm.fragment.FragmentNews;
import com.example.evolvefm.fragment.FragmentPlay;
import com.example.evolvefm.fragment.FragmentSettings;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ChipNavigationBar mainBottomNav = findViewById(R.id.mainBottomNav);
        mainBottomNav.setMenuResource(R.menu.main_menu);

        if (getSupportFragmentManager().getFragments().isEmpty())
            mainBottomNav.setItemSelected(R.id.bottomNavPlay, true);
        else
            mainBottomNav.setItemSelected(R.id.bottomNavSettings, true);

        mainBottomNav.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                switch (i) {
                    case R.id.bottomNavNews: {
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.mainFrame, new FragmentNews())
                                .commit();

                        break;
                    }

                    case R.id.bottomNavPlay: {
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.mainFrame, new FragmentPlay())
                                .commit();

                        break;
                    }

                    case R.id.bottomNavSettings: {
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.mainFrame, new FragmentSettings())
                                .commit();

                        break;
                    }
                }
            }
        });

        if (getSupportFragmentManager().getFragments().isEmpty())
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.mainFrame, new FragmentPlay())
                    .commit();
        else
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.mainFrame, new FragmentSettings())
                    .commit();
    }
}