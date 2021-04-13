package com.dragonlfy.mmwallpaper.ui.main;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dragonlfy.mmwallpaper.R;
import com.dragonlfy.mmwallpaper.models.Category;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.adapters.ItemAdapter;
import com.mikepenz.fastadapter.commons.utils.FastAdapterDiffUtil;

import java.util.ArrayList;
import java.util.List;


public class CategoryFragment extends Fragment {

    private List<Category> categoryList;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private ItemAdapter<Category> itemAdapter;
    private FastAdapter<Category> fastAdapter;
    private int uid;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_category, container, false);
        FirebaseApp.initializeApp(root.getContext());
        progressBar = root.findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);
        itemAdapter = new ItemAdapter<>();
        fastAdapter = FastAdapter.with(itemAdapter);
        recyclerView = root.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        categoryList = new ArrayList<>();

        FirebaseDatabase.getInstance().getReference().child("categories")
        .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    progressBar.setVisibility(View.GONE);
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        String name = ds.getKey();
                        String thumbnail = ds.child("thumbnail").getValue(String.class);
                        Category category = new Category(uid,name, thumbnail);
                        categoryList.add(category);
                        uid++;
                    }
                    DiffUtil.DiffResult result = FastAdapterDiffUtil.calculateDiff(itemAdapter, categoryList);
                    FastAdapterDiffUtil.set(itemAdapter,result);
                    recyclerView.setAdapter(fastAdapter);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), (CharSequence) databaseError.toException(),Toast.LENGTH_LONG).show();
            }
        });



        fastAdapter.withSelectable(true);
        fastAdapter.withOnClickListener((v, adapter, item, position) ->{
            Intent intent = new Intent(getContext(), CategoryItemActivity.class);
            intent.putExtra("name",item.getName());
            startActivity(intent);
            return true;
        });

        return root;
    }


}
