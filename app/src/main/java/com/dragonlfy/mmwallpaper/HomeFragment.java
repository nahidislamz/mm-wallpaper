package com.dragonlfy.mmwallpaper;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.dragonlfy.mmwallpaper.ui.main.ExploreFragment;
import com.dragonlfy.mmwallpaper.ui.main.SectionsPagerAdapter;
import com.google.android.material.appbar.MaterialToolbar;
import com.legendmohe.slidingdrawabletablayout.SlidingDrawableTabLayout;


import java.util.Objects;

public class HomeFragment extends Fragment {

    private int[] tabIcons = {
            R.drawable.ic_explore,
            R.drawable.ic_category
    };
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        MaterialToolbar toolbar = requireActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("MM Wallpaper");
        final ViewPager viewPager = root.findViewById(R.id.view_pager);
        final SlidingDrawableTabLayout tabs = root.findViewById(R.id.tabs);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getContext(), requireActivity().getSupportFragmentManager());
        viewPager.setAdapter(sectionsPagerAdapter);
        tabs.setupWithViewPager(viewPager);
        Objects.requireNonNull(tabs.getTabAt(0)).setIcon(tabIcons[0]);
        Objects.requireNonNull(tabs.getTabAt(1)).setIcon(tabIcons[1]);


        tabs.setOnTabSelectedListener(new SlidingDrawableTabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(SlidingDrawableTabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(SlidingDrawableTabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(SlidingDrawableTabLayout.Tab tab) {

            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Objects.requireNonNull(tabs.getTabAt(position)).select();

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        displayFragment(new ExploreFragment());
        return root;

    }
    private void displayFragment(Fragment fragment) {
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.view_pager, fragment)
                .commit();
    }

}