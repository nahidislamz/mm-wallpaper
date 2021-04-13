package com.dragonlfy.mmwallpaper.ui.favourites;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.dragonlfy.mmwallpaper.R;
import com.dragonlfy.mmwallpaper.adapter.FavouriteAdapter;
import com.dragonlfy.mmwallpaper.database.FavouriteRoomDatabase;
import com.google.android.material.appbar.MaterialToolbar;
import java.util.List;


public class FavouriteFragment extends Fragment {



    private RecyclerView recyclerView;
    private TextView noFav;
    private FavouriteRoomDatabase database;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_favoutites, container, false);
        MaterialToolbar toolbar = requireActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Favourites");

        recyclerView = root.findViewById(R.id.fav_recyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        recyclerView.setHasFixedSize(true);
        noFav = root.findViewById(R.id.nofavText);
        database = FavouriteRoomDatabase.getDatabase(getContext());
        new LoadFavourite().execute();

        return root;
    }

    @SuppressLint("StaticFieldLeak")
    class LoadFavourite extends AsyncTask<Void, List<String>,List<String>>{

        @Override
        protected List<String> doInBackground(Void... voids) {
            return database.dao().getAllFavourite();
        }

        @Override
        protected void onPostExecute(List<String> strings) {
            super.onPostExecute(strings);

            if(strings.size() > 0){
                FavouriteAdapter favouriteAdapter = new FavouriteAdapter(getContext(),strings);
                noFav.setVisibility(View.GONE);
                recyclerView.setAdapter(favouriteAdapter);

            }
        }
    }
}
