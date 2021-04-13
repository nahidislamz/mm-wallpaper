package com.dragonlfy.mmwallpaper.ui.about;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dragonlfy.mmwallpaper.BuildConfig;
import com.dragonlfy.mmwallpaper.R;
import com.google.android.material.appbar.MaterialToolbar;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;


public class AboutFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        MaterialToolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("About");
        Element versionElement = new Element();
        versionElement.setTitle("Version Name : "+BuildConfig.VERSION_NAME);

        View aboutPage = new AboutPage(getContext())
                .isRTL(false)
                .setImage(R.mipmap.ic_launcher_round)
                .setDescription("Moment Makers is a wallpaper app that was built based on our very own instagram photography page")
                .addItem(versionElement)
                .addItem(new Element().setTitle("Auther : Nahid Islam"))
                .addGroup("Developer Contact")
                .addEmail("nahidislamz@outlook.com","Contact")
                .addFacebook("nahidislamz","Find me on Facebook")
                .addTwitter("nahidislamzz","Find me on Twitter")
                .addInstagram("nahidislamz","Find me on Instagram")
                .create();
        aboutPage.setBackgroundColor(R.attr.backgroundcolor);

        return aboutPage;
    }
}
