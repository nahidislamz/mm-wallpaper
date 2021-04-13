package com.dragonlfy.mmwallpaper.ui.main;
import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private final Context mContext;


    public SectionsPagerAdapter(Context context,FragmentManager fm) {
        super(fm);
        mContext = context;

    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).

        switch (position)
        {
            case 0 :
                return new ExploreFragment();
            case 1:
                return new CategoryFragment();
            default:
                return null;
        }
    }


    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }
}