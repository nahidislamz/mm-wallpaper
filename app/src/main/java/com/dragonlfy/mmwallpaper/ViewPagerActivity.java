package com.dragonlfy.mmwallpaper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.viewpager.widget.ViewPager;
import android.Manifest;
import android.app.AlertDialog;
import android.app.WallpaperManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.dragonlfy.mmwallpaper.database.Favourite;
import com.dragonlfy.mmwallpaper.database.FavouriteRoomDatabase;
import com.dragonlfy.mmwallpaper.ui.common.Common;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ViewPagerActivity extends AppCompatActivity {

    static  final int PERMISSION_REQUEST_CODE =1000;
    ViewPager viewPager2;
    ViewPagerAdapter viewPagerAdapter;
    AlertDialog alertDialog;
    ConstraintLayout root;
    static final int NUM_OF_THREAD = 4;
    ExecutorService executorService = Executors.newFixedThreadPool(NUM_OF_THREAD);
    FavouriteRoomDatabase roomDatabase;

    FloatingActionButton favouriteFb,setWallpaper,downloads;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_view_pager);
        favouriteFb=findViewById(R.id.favourites);
        setWallpaper=findViewById(R.id.setwallpaper);
        downloads=findViewById(R.id.download);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        Common.WIDTH = display.getWidth();
        Common.HIEGHT=display.getHeight();

        roomDatabase = FavouriteRoomDatabase.getDatabase(this);
        favouriteFb.setOnClickListener(v -> updateFavourite());
        downloads.setOnClickListener(v -> {
            if(ActivityCompat.checkSelfPermission(ViewPagerActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]
                        {Manifest.permission.WRITE_EXTERNAL_STORAGE},PERMISSION_REQUEST_CODE);
            }
            else {
                downloadBitmap();
            }
        });
        setWallpaper.setOnClickListener(v -> setWallpaperScreen());

        addViewPager();
        addFavourite();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == PERMISSION_REQUEST_CODE && grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            downloadBitmap();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    private void addFavourite() {
        executorService.execute(() ->
                {
                    String currentImage = Common.PIC_LIST.get(viewPager2.getCurrentItem());
                    Favourite favourite = roomDatabase.dao().isExist(currentImage);
                    if(favourite!= null){
                        favouriteFb.setImageResource(R.drawable.ic_star_yes);
                       // Toast.makeText(getApplicationContext(),"Added to favourites",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        favouriteFb.setImageResource(R.drawable.ic_star_no);
                       // Toast.makeText(getApplicationContext(),"Removed from favourites",Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void updateFavourite() {
        executorService.execute((() -> {
            String currentImage = Common.PIC_LIST.get(viewPager2.getCurrentItem());
            Favourite favourite = roomDatabase.dao().isExist(currentImage);
            if(favourite==null){
                favouriteFb.setImageResource(R.drawable.ic_star_yes);
                roomDatabase.dao().insertFavourite(new Favourite(currentImage,""+System.currentTimeMillis()));
            }
            else {
                favouriteFb.setImageResource(R.drawable.ic_star_no);
                roomDatabase.dao().deleteFavourite(currentImage);
            }

        }));
    }
    private void setWallpaperScreen() {
        alertDialog = new AlertDialog.Builder(ViewPagerActivity.this).create();
        alertDialog.setMessage("Setting Wallpaper... Please Wait");
        alertDialog.show();

        Glide.with(ViewPagerActivity.this)
                .asBitmap().load(Common.PIC_LIST.get(viewPager2.getCurrentItem()))
                .override(Common.WIDTH,Common.HIEGHT)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());

                        try {
                            wallpaperManager.setBitmap(resource);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        alertDialog.dismiss();
                        //Snackbar.make(root,"Wallpaper Set",Snackbar.LENGTH_LONG).show();
                        Toast.makeText(getApplicationContext(),"Wallpaper Set",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });


    }
    public void addViewPager(){

        if(getIntent()!=null){
            viewPager2=findViewById(R.id.view_pager);
            int pos=getIntent().getIntExtra("pos",0);
            viewPagerAdapter = new ViewPagerAdapter(this, Common.PIC_LIST);
            viewPager2.setAdapter(viewPagerAdapter);
            viewPager2.setCurrentItem(pos);
            viewPagerAdapter.notifyDataSetChanged();

            viewPager2.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {

                    addFavourite();

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

        }

    }


    private void downloadBitmap() {
        alertDialog = new AlertDialog.Builder(ViewPagerActivity.this).create();
        alertDialog.setMessage("Downloading... Please Wait");
        alertDialog.show();
        Glide.with(ViewPagerActivity.this)
                .asBitmap().load(Common.PIC_LIST.get(viewPager2.getCurrentItem()))
                .override(Common.WIDTH,Common.HIEGHT)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {

                        try {
                            saveBitmap(resource);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
    }

   /* private void saveBitmap(Bitmap bitmap) throws IOException {

        String fileName = UUID.randomUUID()+".jpg";
        OutputStream outputStream;
        boolean isSaved;

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            ContentResolver resolver =getContentResolver();
            ContentValues contentValues=new ContentValues();
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME,fileName);
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE,"image/jpg");
            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH,Environment.DIRECTORY_PICTURES);

            Uri uri =resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,contentValues);
            outputStream = resolver.openOutputStream(uri);

        }
        else {
            String path = Environment.getExternalStorageState();

            File folder = new File(path, "/MM-Wallpaper");
            if(!folder.exists()){
                folder.mkdirs();
            }
            File file = new File(folder, fileName);
            if (file.exists()) {
                file.delete();
            }
            outputStream = new FileOutputStream(file);
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
        }

        isSaved = bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream);
        if(isSaved){
            Snackbar.make(root,"Download Successful",Snackbar.LENGTH_LONG).show();
            alertDialog.dismiss();

        }else {
            Snackbar.make(root,"Download failed",Snackbar.LENGTH_LONG).show();
        }
        outputStream.flush();
        outputStream.close();



    }*/

    public File saveBitmap(Bitmap bmp) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        File f = new File(Environment.getExternalStorageDirectory()
                + File.separator + UUID.randomUUID() + ".jpg");
        f.createNewFile();
        FileOutputStream fo = new FileOutputStream(f);
        fo.write(bytes.toByteArray());
        fo.close();
        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(f)));
        Toast.makeText(this,"Download Successful",Toast.LENGTH_LONG).show();
        alertDialog.dismiss();

        return f;
    }



}



