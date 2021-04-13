package com.dragonlfy.mmwallpaper.ui.privacy;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.dragonlfy.mmwallpaper.R;
import com.google.android.material.appbar.MaterialToolbar;


public class PrivacyFragment extends Fragment {

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_privacy, container, false);
        MaterialToolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Privacy Policy");
        WebView webView=view.findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://sites.google.com/view/mm-wallpaper/privacy");
        return view;
    }
}
