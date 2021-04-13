package com.dragonlfy.mmwallpaper.ui.terms;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.dragonlfy.mmwallpaper.R;
import com.google.android.material.appbar.MaterialToolbar;

public class TermsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_terms, container, false);
        MaterialToolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Terms & Conditions");
        WebView webView=view.findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://sites.google.com/view/mm-wallpaper-/terms");
        return view;

    }
}
