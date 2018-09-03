package com.example.ka3ak.mybigfamily;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import data.FamilyContract;

public class MainActivity extends AppCompatActivity {

   public static ViewPager upperPager, middlePager, lowerPager, viewPager;
   TextView upperMainActivityTextView,middleMainActivityTextView,lowerMainActivityTextView;


    public static  FamilyContract familyBase;
    public static SQLiteDatabase sqLiteDatabase ;
    public static ArrayList<Person> dataBserPersons = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // first launch
        familyBase = new FamilyContract(this);
        sqLiteDatabase = familyBase.getWritableDatabase();

        Cursor cursor =sqLiteDatabase.query("Family", null,null,null, null, null, null);

        if( cursor.getCount() == 0) {
            Intent firstLaunch = new Intent(MainActivity.this, FirstLaunchActivity.class);
            startActivity(firstLaunch);
        }else {
            Log.d("log", "Table not found");
        }
        // first launch
        for (int i = 1; i <cursor.getCount() ; i++) {
            dataBserPersons.add(new Person(cursor.getInt(cursor.getColumnIndex("id"))));
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainActivity.upperPager = (ViewPager) findViewById(R.id.viewPager);
        MainActivity.middlePager = (ViewPager) findViewById(R.id.viewPager2);
        MainActivity.lowerPager = (ViewPager) findViewById(R.id.viewPager3);
        upperMainActivityTextView = findViewById(R.id.main_activity_1_swapper_textview);
        middleMainActivityTextView = findViewById(R.id.main_activity_2_swapper_textview);
        lowerMainActivityTextView = findViewById(R.id.main_activity_3_swapper_textview);


        ViewPagerAdapter topSwap = new ViewPagerAdapter(this);


    MainActivity.upperPager.setAdapter(topSwap);

    ViewPagerAdapter midSwap = new ViewPagerAdapter(this);

    MainActivity.middlePager.setAdapter(midSwap);

    ViewPagerAdapter botSwap = new ViewPagerAdapter(this);

    MainActivity.lowerPager.setAdapter(botSwap);



    }





public void onClickTree(View view){
    Intent intent =  new Intent(MainActivity.this, TreeFrame.class);
    startActivity(intent);
}
public void onClickMember(View view){
    Intent intent =  new Intent(MainActivity.this, AddNewMemberActivity.class);
    startActivity(intent);
}
public void onClickMe(View view){
Intent intent =  new Intent(MainActivity.this, MeActivity.class);
    startActivity(intent);
}
public void onClickBirthday(View view) {
        Intent intent = new Intent(MainActivity.this, BirthdayActivity.class);
        startActivity(intent);
    }
}
