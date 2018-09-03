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

import com.example.ka3ak.mybigfamily.utlity.Orthography;

import java.io.File;

public class MeActivity extends AppCompatActivity {
    Cursor cursor;
    EditText name, surname, birthday, father, fatherBirthday, mother, motherBirthday;
    ImageView myPhotoImageView;


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
        String SQlfatherTextView = "1", SQlfatherBirthdayTextView = "2", SQlmotherTextView="3", SQlmotherBirthdayTextView="4";

        SQlfatherTextView = cursor.getString(cursor.getColumnIndex("father"));
        SQlfatherBirthdayTextView = cursor.getString(cursor.getColumnIndex("fatherBirthday"));
        SQlmotherTextView = cursor.getString(cursor.getColumnIndex("mother"));
        SQlmotherBirthdayTextView = cursor.getString(cursor.getColumnIndex("motherBirthday"));


        name = findViewById(R.id.profile_name_editText);
        name.setText(SQLname);
        surname = findViewById(R.id.profile_surname_editText);
        surname.setText(SQLsurname);
        birthday = findViewById(R.id.profile_birthday_editText);
        birthday.setText(SQLbirthday);
        father = findViewById(R.id.profile_father_editText);
        father.setText(SQlfatherTextView);
        fatherBirthday = findViewById(R.id.profile_father_birthday);
        fatherBirthday.setText(SQlfatherBirthdayTextView);
        mother = findViewById(R.id.profile_mother_editText);
        mother.setText(SQlmotherTextView);
        motherBirthday = findViewById(R.id.profile_mother_birthday);
        motherBirthday.setText(SQlmotherBirthdayTextView);
        myPhotoImageView = findViewById(R.id.profile_my_photo);


//        myPhotoImageView = findViewById(R.id.profile_my_photo);
//        myPhotoImageView.setImageURI(photoImage);
//        myPhotoImageView.setImageDrawable(Drawable.createFromPath(photoSQLName));

        try {
            File photoFile = new File(SQLphoto);
            Bitmap bmp = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
            myPhotoImageView.setImageBitmap(bmp);
        }  catch (Exception e){
            Log.d("log", "Profile photo not found");
            myPhotoImageView.setImageResource(R.drawable.not_avalible);
        }

    }

    public void myProfileSave(View view){
      if  (Orthography.nameRegex(name.getText().toString()) && Orthography.nameRegex(surname.getText().toString()) ||
                (Orthography.dataRegex(birthday.getText().toString()) && Orthography.nameRegex(name.getText().toString())) ||
                (Orthography.dataRegex(birthday.getText().toString()) && Orthography.nameRegex(surname.getText().toString()))||
              Orthography.dataRegex(fatherBirthday.getText().toString()) && Orthography.dataRegex(motherBirthday.getText().toString())) {

          ContentValues meValues = new ContentValues();
          meValues.put("name", name.getText().toString());
          meValues.put("surname", surname.getText().toString());
          meValues.put("birthday", birthday.getText().toString());
          meValues.put("father", father.getText().toString());
          meValues.put("mother", mother.getText().toString());
          meValues.put("fatherBirthday", fatherBirthday.getText().toString());
          meValues.put("motherBirthday", motherBirthday.getText().toString());

          try {

              String[] fatherData = father.getText().toString().split(" ");
              ContentValues fatherValues = new ContentValues();
              fatherValues.put("name", fatherData[1]);
              fatherValues.put("surname", fatherData[0]);
              if (fatherData.length > 2) {
                  fatherValues.put("patronymic", fatherData[2]);
              }
              fatherValues.put("birthday", fatherBirthday.getText().toString());
              MainActivity.sqLiteDatabase.update("Family", fatherValues,"id = ?", new String[]{"2"});
          }
          catch (Exception ext) {
              Toast.makeText(MeActivity.this, "Need to insert father data", Toast.LENGTH_LONG).show();
          }
          try {
              String[] motherData = mother.getText().toString().split(" ");
              ContentValues motherValues = new ContentValues();
              motherValues.put("name", motherData[1]);
              motherValues.put("surname", motherData[0]);
              if (motherData.length > 2) {
                  motherValues.put("patronymic", motherData[2]);
              }
              motherValues.put("birthday", motherBirthday.getText().toString());
              MainActivity.sqLiteDatabase.update("Family", motherValues,"id = ?", new String[]{"3"});
          } catch (Exception exp) {
              Toast.makeText(MeActivity.this, "Need to insert mother data", Toast.LENGTH_LONG).show();
          }

          MainActivity.sqLiteDatabase.update("Family", meValues, "id = ?", new String[]{"1"});
          Toast toast =  Toast.makeText(MeActivity.this, "Data inserted ", Toast.LENGTH_LONG);
          toast.show();
          finish();

      }
        else {
          Toast.makeText(this, R.string.input_correct_data, Toast.LENGTH_SHORT).show();
      }


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



