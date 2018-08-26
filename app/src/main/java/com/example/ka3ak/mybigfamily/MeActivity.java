package com.example.ka3ak.mybigfamily;

import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

public class MeActivity extends AppCompatActivity {
    Cursor cursor;
    EditText name, surname, birthday, father, fatherBirthday, mother, motherBirthday;
    ImageView myPhotoImageView;
    ImageButton fatherPhoto, motherPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me);
        cursor = MainActivity.sqLiteDatabase.query("Family",null,null,null,null,null,null);
        cursor.moveToFirst();
        String SQLphoto = cursor.getString(cursor.getColumnIndex("photo"));
        String SQLname = cursor.getString(cursor.getColumnIndex("name"));
        String SQLsurname = cursor.getString(cursor.getColumnIndex("surname"));
        String SQLbirthday = cursor.getString(cursor.getColumnIndex("birthday"));


        name = findViewById(R.id.profile_name_editText);
        name.setText(SQLname);
        surname = findViewById(R.id.profile_surname_editText);
        surname.setText(SQLsurname);
        birthday = findViewById(R.id.profile_birthday_editText);
        birthday.setText(SQLbirthday);
        father = findViewById(R.id.profile_father_editText);
        fatherBirthday = findViewById(R.id.profile_father_birthday);
        mother = findViewById(R.id.profile_mother_editText);
        motherBirthday = findViewById(R.id.profile_mother_birthday);
        myPhotoImageView = findViewById(R.id.profile_my_photo);


//        myPhotoImageView = findViewById(R.id.profile_my_photo);
//        myPhotoImageView.setImageURI(photoImage);
//        myPhotoImageView.setImageDrawable(Drawable.createFromPath(photoSQLName));


        File photoFile = new File(SQLphoto);
        fatherPhoto = findViewById(R.id.father_photo_btn);
        motherPhoto = findViewById(R.id.mother_photo_btn);
        Bitmap bmp = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
        myPhotoImageView.setImageBitmap(bmp);


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



