package com.example.ka3ak.mybigfamily;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class ViewPagerAdapter extends PagerAdapter {

    private static Person currentPerson = null;
    private Context context;
    private LayoutInflater layoutInflater;
    private Integer[] images = {R.drawable.mother, R.drawable.father, R.drawable.father};
    private Person[] personToShow = {};

    public ViewPagerAdapter(Context context) {
        this.context = context;
    }

    public static Person getCurrentPerson() {
        return currentPerson;
    }

    public static void setCurrentPerson(Person currentPerson) {
        ViewPagerAdapter.currentPerson = currentPerson;
    }

    public Person[] getPersonToShow() {
        return personToShow;
    }

    public void setPersonToShow(Person[] personToShow) {
        this.personToShow = personToShow;
    }

    @Override
    public int getCount() {
        return personToShow.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    public Integer[] getImages() {
        return images;
    }

    public void setImages(Integer[] images) {
        this.images = images;
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {


        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.activity_custom_swip, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);


//        imageView.setImageResource(images[position]);

//try {
//    Cursor cursor;
//    cursor = MainActivity.sqLiteDatabase.query("Family", null, null, null, null, null, null);
//    cursor.moveToFirst();
//    String photoPath = cursor.getString(cursor.getColumnIndex("photo"));
//    cursor.close();
//    File photoFile = new File(photoPath);
//    Bitmap bitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
//    bitmap = Bitmap.createScaledBitmap(bitmap, 150, 200, true);
//
//    imageView.setImageBitmap(bitmap);
//}
//catch (Exception exp){
//
//}

        try {
            if (personToShow[position] != null) {
                if (personToShow[position].getPhoto() != null) {

                    imageView.setImageBitmap(personToShow[position].getReSizePhotoBitmap());
                } else {
                    imageView.setImageResource(R.drawable.not_avalible);
                }
            }
        } catch (Exception e) {

        }


        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);
    }
}
