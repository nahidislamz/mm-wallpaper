package com.dragonlfy.mmwallpaper.models;



import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.dragonlfy.mmwallpaper.R;
import com.github.ybq.android.spinkit.style.Wave;
import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.items.AbstractItem;
import java.util.List;


public class Category extends AbstractItem<Category,Category.CategoryViewHolder> {
    private int uid;
    private String name, thumb;

    public Category(int uid,String name, String thumb) {
        this.uid=uid;
        this.name = name;
        this.thumb = thumb;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }


    @Override
    public long getIdentifier() {
        return uid;
    }

    @Override
    public int getType() {
        return R.id.card_item_cat;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.recyclerview_categories;
    }

    @NonNull
    @Override
    public CategoryViewHolder getViewHolder(@NonNull View v) {
        return new CategoryViewHolder(v);
    }

    public static class CategoryViewHolder extends FastAdapter.ViewHolder<Category> {

        TextView textView;
        ImageView imageView;
        ProgressBar progressBar;
        CategoryViewHolder(final View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.text_view_cat_name);
            imageView = itemView.findViewById(R.id.image_view);
            progressBar = itemView.findViewById(R.id.progressbar);

        }

        @Override
        public void bindView(Category item, @NonNull List<Object> payloads) {
            Wave wave = new Wave();
            progressBar.setIndeterminateDrawable(wave);
            progressBar.setVisibility(View.VISIBLE);
            textView.setText(item.name);
            Glide.with(itemView.getContext())
                    .load(item.thumb)
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            progressBar.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(imageView)
            ;
      }

        @Override
        public void unbindView(@NonNull Category item) {
            textView.setText(null);

        }
    }
}
