package com.dragonlfy.mmwallpaper.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.dragonlfy.mmwallpaper.R;
import com.dragonlfy.mmwallpaper.ViewPagerActivity;
import com.dragonlfy.mmwallpaper.ui.common.Common;

import java.util.List;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.ViewHolder> {

    private Context context;
    private List<String> list;
    private int position;

    public FavouriteAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.fav_item,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        this.position=position;

        Glide.with(context)
                .load(list.get(position))
                .fitCenter().diskCacheStrategy(DiskCacheStrategy.DATA)
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

     class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        private ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view_fav);
            itemView.setOnClickListener(v -> {

                Intent intent = new Intent(context, ViewPagerActivity.class);
                intent.putExtra("pos",getAdapterPosition());
                Common.PIC_LIST=list;
                context.startActivity(intent);
            });

        }
    }
}
