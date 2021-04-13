package com.dragonlfy.mmwallpaper.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.dragonlfy.mmwallpaper.R;
import com.dragonlfy.mmwallpaper.ViewPagerActivity;
import com.dragonlfy.mmwallpaper.models.Wallpaper;
import com.dragonlfy.mmwallpaper.ui.common.Common;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter;
import com.mikepenz.fastadapter.commons.utils.FastAdapterDiffUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ExploreFragment extends Fragment {

    private List<Wallpaper> wallpaperList;
    private List<String> picturesList;
    private ProgressBar progressBar;
    private FastItemAdapter<Wallpaper> itemAdapter;
    private RecyclerView recyclerView;
    private int uid;
    private String url;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_explore, container, false);
        FirebaseApp.initializeApp(view.getContext());
        progressBar = view.findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);
        recyclerView = view.findViewById(R.id.wall_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        itemAdapter = new FastItemAdapter<>();
        wallpaperList = new ArrayList<>();
        picturesList = new ArrayList<>();
        recyclerView.setAdapter(itemAdapter);

        FirebaseDatabase.getInstance().getReference("wallpaper")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            progressBar.setVisibility(View.GONE);
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                url = ds.child("url").getValue(String.class);
                                Wallpaper wallpaper = new Wallpaper(uid, url);uid++;
                                wallpaperList.add(wallpaper);
                                picturesList.add(url);
                                Collections.reverse(wallpaperList);
                                Collections.reverse(picturesList);
                            }

                        }

                        DiffUtil.DiffResult result = FastAdapterDiffUtil.calculateDiff(itemAdapter, wallpaperList);
                        FastAdapterDiffUtil.set(itemAdapter,result);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getContext(), (CharSequence) databaseError.toException(),Toast.LENGTH_LONG).show();

                    }
                });
        itemAdapter.withSelectable(true);
        itemAdapter.withOnClickListener((v, adapter, item, position) ->{

            Intent intent = new Intent(getContext(), ViewPagerActivity.class);
            intent.putExtra("pos",position);
            Common.PIC_LIST=picturesList;
            startActivity(intent);
            return true;
        });

        return view;
    }


}