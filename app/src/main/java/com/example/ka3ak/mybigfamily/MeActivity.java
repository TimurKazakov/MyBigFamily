package com.example.ka3ak.mybigfamily;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MeActivity extends AppCompatActivity {
    Cursor cursor;
    EditText name, surname, birthday, father, fatherBirthday, mother, motherBirthday;
    ImageView myPhotoImageView;
    ImageButton fatherPhoto, motherPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me);
        name = findViewById(R.id.profile_name_editText);
        surname = findViewById(R.id.profile_surname_editText);
        birthday = findViewById(R.id.profile_birthday_editText);
        father = findViewById(R.id.profile_father_editText);
        fatherBirthday = findViewById(R.id.profile_father_birthday);
        mother = findViewById(R.id.profile_mother_editText);
        motherBirthday = findViewById(R.id.profile_mother_birthday);
        myPhotoImageView = findViewById(R.id.profile_my_photo);
        fatherPhoto = findViewById(R.id.father_photo_btn);
        motherPhoto = findViewById(R.id.mother_photo_btn);



    }

    public void myProfileSave(View view){
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name.getText().toString());
        contentValues.put("surname", surname.getText().toString());
        contentValues.put("birthday", birthday.getText().toString());

        MainActivity.sqLiteDatabase.update("Family", contentValues,"id = ?", new String[] {"1"});

    }

    public void myProfileCancel(View view){

        if(cursor.moveToFirst()) {
            do {
                Log.d("log", ""+ "ID " + cursor.getInt(cursor.getColumnIndex("id")) +" Name " + cursor.getString(cursor.getColumnIndex("name")));


            } while (cursor.moveToNext());
        }else {
            Log.d("log", "Table not found");
        }
        MainActivity.sqLiteDatabase.execSQL("DELETE from "+"Family");
        String TABLE_NAME = "Family";
        MainActivity.sqLiteDatabase.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + TABLE_NAME + "'");
        cursor.close();

    }





}



