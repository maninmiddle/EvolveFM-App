package com.example.evolvefm.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.example.evolvefm.R;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class FragmentSettings extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        SwitchMaterial settingsDarkTheme = view.findViewById(R.id.settingsDarkTheme);
        settingsDarkTheme.setChecked(getContext().getSharedPreferences("data", Context.MODE_PRIVATE).getBoolean("darkThemeEnabled", false));
        settingsDarkTheme.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                getContext().getSharedPreferences("data", Context.MODE_PRIVATE).edit().putBoolean("darkThemeEnabled", b).apply();

                AppCompatDelegate.setDefaultNightMode(b ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
            }
        });

        return view;
    }
}
