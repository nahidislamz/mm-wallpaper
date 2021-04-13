package com.dragonlfy.mmwallpaper.ui.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.dragonlfy.mmwallpaper.HomeActivity;
import com.dragonlfy.mmwallpaper.R;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.Objects;

public class SettingsFragment extends Fragment {

    private SharedPref sharedpref;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        sharedpref = new SharedPref(requireContext());
        if(sharedpref.loadNightModeState()) {

            requireActivity().setTheme(R.style.darktheme);
        }
        else  {
            requireActivity().setTheme(R.style.AppTheme);
        }
        View root = inflater.inflate(R.layout.fragment_settings, container, false);
        MaterialToolbar toolbar = requireActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Settings");
        Switch myswitch = root.findViewById(R.id.myswitch);
        if (sharedpref.loadNightModeState()) {
            myswitch.setChecked(true);
        }
        myswitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                sharedpref.setNightModeState(true);
                restartApp();
            }
            else {
                sharedpref.setNightModeState(false);
                restartApp();
            }
        });


        return root;
    }

    private void restartApp() {
        Intent i = new Intent(requireActivity().getApplicationContext(), HomeActivity.class);
        startActivity(i);
        requireActivity().finish();
    }
}
