package com.example.ka3ak.mybigfamily;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BirthdayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birthday);
        LinearLayout birthdayLayout = new LinearLayout(this);
        birthdayLayout.setOrientation(LinearLayout.VERTICAL);

        MainActivity.sqLiteDatabase = MainActivity.familyBase.getWritableDatabase();
        Cursor cursor =MainActivity.sqLiteDatabase.query("Family", null,null,null, null, null, null);
        cursor.moveToFirst();
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        setContentView(birthdayLayout,params);
        ViewGroup.LayoutParams standartParamsForSQLQuery = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        TextView textView = new TextView(this);
        textView.setText( "ID: " + cursor.getInt(cursor.getColumnIndex("id"))
                 +" "+ cursor.getString(cursor.getColumnIndex("name"))
                +" "+  cursor.getString(cursor.getColumnIndex("surname"))
                 +" "+ cursor.getString(cursor.getColumnIndex("birthday"))
        +" "+ cursor.getString(cursor.getColumnIndex("photo")));
        textView.setTextSize(15);
        textView.setLayoutParams(standartParamsForSQLQuery);
        birthdayLayout.addView(textView);

//        while (!cursor.isLast()){
//            TextView dtb
//        }

    }
}
