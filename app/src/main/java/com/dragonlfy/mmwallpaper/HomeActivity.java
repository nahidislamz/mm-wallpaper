package com.dragonlfy.mmwallpaper;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import com.dragonlfy.mmwallpaper.ui.about.AboutFragment;
import com.dragonlfy.mmwallpaper.ui.favourites.FavouriteFragment;
import com.dragonlfy.mmwallpaper.ui.privacy.PrivacyFragment;
import com.dragonlfy.mmwallpaper.ui.settings.SettingsFragment;
import com.dragonlfy.mmwallpaper.ui.settings.SharedPref;
import com.dragonlfy.mmwallpaper.ui.terms.TermsFragment;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;
import com.tbuonomo.morphbottomnavigation.MorphBottomNavigationView;
import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Objects;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    MaterialToolbar toolbar;DrawerLayout drawer;
    SharedPref sharedpref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        sharedpref = new SharedPref(getApplicationContext());
        if(sharedpref.loadNightModeState()) {

            setTheme(R.style.darktheme);
        }
        else  {
            setTheme(R.style.AppTheme);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setTitle("MM Wallpaper");
        drawer= findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        FirebaseApp.initializeApp(this);

        MorphBottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(morphBottomNavigationView);
        displayFragment(new HomeFragment());
        navigationView.setNavigationItemSelectedListener(this);
        setUpNavDrawer();

    }


    private void displayFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_area, fragment)
                .commit();
    }

    private void setUpNavDrawer() {
        if (toolbar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationIcon(R.drawable.ic_menu);
            toolbar.setNavigationOnClickListener(v -> drawer.openDrawer(GravityCompat.START));
        }

    }

    MorphBottomNavigationView.OnNavigationItemSelectedListener morphBottomNavigationView
            = item -> {
                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.nav_fav:
                        fragment = new FavouriteFragment();
                        break;
                    case R.id.nav_set:
                        fragment = new SettingsFragment();
                        break;
                    case R.id.nav_home:
                        fragment = new HomeFragment();
                        break;
                    default:
                        fragment =  null;

                        break;
                }
                displayFragment(fragment);
                return true;
            };

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment ;
        switch (item.getItemId()) {
            case R.id.nav_about:
                fragment = new AboutFragment();
                break;
            case R.id.nav_term:
                fragment = new TermsFragment();
                break;
            case R.id.nav_privacy:
                fragment = new PrivacyFragment();
                break;
            case R.id.nav_share:
                fragment = new HomeFragment();
                Intent myIntent = new Intent(Intent.ACTION_SEND);
                myIntent.setType("text/plain");
                String shareBody = "Download MM-Wallpaper\nhttps://play.google.com/store/apps/details?id=com.dragonlfy.mmwallpaper";
                String shareSub = "MM-Wallpaper Download Link";
                myIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
                myIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(myIntent, "Share using"));
                break;
            case R.id.nav_insta:
                Uri uri = Uri.parse("http://www.instagram.com/moment_makerzz");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            default:
                fragment = new HomeFragment();
                break;
        }
        displayFragment(fragment);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



}
