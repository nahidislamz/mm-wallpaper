package com.dragonlfy.mmwallpaper.ui.main;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dragonlfy.mmwallpaper.R;
import com.dragonlfy.mmwallpaper.ViewPagerActivity;
import com.dragonlfy.mmwallpaper.models.Wallpaper;
import com.dragonlfy.mmwallpaper.ui.common.Common;
import com.dragonlfy.mmwallpaper.ui.settings.SharedPref;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter;
import com.mikepenz.fastadapter.commons.utils.FastAdapterDiffUtil;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CategoryItemActivity extends AppCompatActivity {

    private List<Wallpaper> wallpaperList;
    private List<String> pictureList;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private FastItemAdapter<Wallpaper> itemAdapter;
    private int uid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        SharedPref sharedpref = new SharedPref(getApplicationContext());
        if(sharedpref.loadNightModeState()) {

            setTheme(R.style.darktheme);
        }
        else  {
            setTheme(R.style.AppTheme);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_category_item);
        FirebaseApp.initializeApp(this);

        MaterialToolbar toolbar2 = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar2);
        TextView textView = findViewById(R.id.titleText);

        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);

        itemAdapter = new FastItemAdapter<>();

        recyclerView = findViewById(R.id.wall_recycler_view_item);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        wallpaperList = new ArrayList<>();
        pictureList=new ArrayList<>();
        recyclerView.setAdapter(itemAdapter);
        String name=getIntent().getStringExtra("name");

        textView.setText(name);

        FirebaseDatabase.getInstance().getReference("images").child(name)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            progressBar.setVisibility(View.GONE);
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                String url = ds.child("url").getValue(String.class);
                                Wallpaper wallpaper = new Wallpaper(uid, url); uid++;
                                wallpaperList.add(wallpaper);
                                pictureList.add(url);
                                Collections.reverse(wallpaperList);
                                Collections.reverse(pictureList);

                            }

                            DiffUtil.DiffResult result = FastAdapterDiffUtil.calculateDiff(itemAdapter, wallpaperList);
                            FastAdapterDiffUtil.set(itemAdapter,result);


                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getApplicationContext(), (CharSequence) databaseError.toException(),Toast.LENGTH_LONG).show();

                    }
                });

        itemAdapter.withSelectable(true);
        itemAdapter.withOnClickListener((v, adapter, item, position) ->{

            Intent intent = new Intent(getApplicationContext(), ViewPagerActivity.class);
            intent.putExtra("pos",position);
            Common.PIC_LIST = pictureList;
            startActivity(intent);
            return true;
        });
    }


}
