package com.example.ka3ak.mybigfamily;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.os.UserHandle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.sql.SQLData;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import data.FamilyContract;

public class FirstLaunchActivity extends AppCompatActivity {

    static final int REQUEST_TAKE_PHOTO = 1;
    static final int GALLERY_REQUEST = 2;
    int DIALOG_DATE = 1;
    int myYear = 2000;
    ;
    int myMonth = 00;
    int myDay = 01;

    DatePickerDialog.OnDateSetListener myCallBack = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myYear = year;
            myMonth = monthOfYear;
            myDay = dayOfMonth;
            TextView birthdayProfileTextView = (TextView) findViewById(R.id.first_launch_profile_birthday_editText);
            birthdayProfileTextView.setText(myDay + "/" + (myMonth + 1) + "/" + myYear);

//       +1 because first month start from zero
        }
    };

    ImageView startPhoto;

    //   photo
    String mCurrentPhotoPath;
    String[] buttons = {"Photo", "Gallery"};
    EditText firstLaunchName, firstLaunchSurname, firstLaunchBirthday;
    Button save, cancel;
    private Uri photoURI;

    private void birthday_onclick(View view) {
        showDialog(DIALOG_DATE);
    }

    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_DATE) {
            DatePickerDialog tpd = new DatePickerDialog(this, myCallBack, myYear, myMonth, myDay);
            return tpd;
        }
        return super.onCreateDialog(id);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_launch);
        startPhoto= findViewById(R.id.first_launch_profile_photo);

        firstLaunchName = findViewById(R.id.first_launch_profile_name_editText);
        firstLaunchSurname = findViewById(R.id.first_launch_profile_surname_editText);
        firstLaunchBirthday = findViewById(R.id.first_launch_profile_birthday_editText);
        save = findViewById(R.id.profile_save_btn);
        cancel = findViewById(R.id.profile_cancel_btn);



        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                ContentValues contentValues =new ContentValues();
                contentValues.put("name", firstLaunchName.getText().toString());
                contentValues.put("surname", firstLaunchSurname.getText().toString());
                contentValues.put("birthday", firstLaunchBirthday.getText().toString());
                contentValues.put("photo", mCurrentPhotoPath);
                contentValues.put("kinship", 10);

                MainActivity.sqLiteDatabase.insert("Family",null, contentValues);
                Log.d("log", "Data Inserted");




                Intent backToMain = new Intent(FirstLaunchActivity.this, MainActivity.class);
                startActivity(backToMain);


            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Cursor cursor =MainActivity.sqLiteDatabase.query("Family", null,null,null,null,null,null);
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

            }


        });


    }

    public void onClickPhoto(View view){


        AlertDialog.Builder chosePhotoOrGallery = new AlertDialog.Builder(FirstLaunchActivity.this);
        chosePhotoOrGallery.setTitle("Photo or Gallery").setItems(R.array.photo_or_gallery, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0){
                    dispatchTakePictureIntent();
                }
                if (i==1){
                    dispatchLoadFromGalleryIntent();
                }
            }
        });
        chosePhotoOrGallery.show();

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            startPhoto.setImageURI(photoURI);

        }

        if(requestCode == GALLERY_REQUEST){
            Bitmap bitmap = null;
            int width =0;
            int height =0;

            if(resultCode == RESULT_OK){
                Uri selectedImage = data.getData();




                mCurrentPhotoPath = selectedImage.getAuthority();

                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                    width = startPhoto.getWidth();
                    height = startPhoto.getHeight();
                    File galleryFile = createImageFile();
                    Bitmap bitmapToFile = bitmap;
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    bitmapToFile.compress(Bitmap.CompressFormat.JPEG,0,bos);
                    byte[] bitmapToFileData = bos.toByteArray();
                    FileOutputStream fos = new FileOutputStream(galleryFile);
                    fos.write(bitmapToFileData);
                    bos.close();
                    fos.close();
                    


                } catch (IOException e) {
                    e.printStackTrace();
                }
                startPhoto.setImageBitmap(bitmap);


            }
        }

    }


//    photo



//    text

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp+".jpg";
//        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES),imageFileName);

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
//        mCurrentPhotoPath = imageFileName;
        return image;
    }

//    /text

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();

            } catch (IOException ex) {
                // Error occurred while creating the File
                Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private void dispatchLoadFromGalleryIntent() {
        Intent loadFromGalleryIntent = new Intent(Intent.ACTION_PICK);
        loadFromGalleryIntent.setType("image/*");
        startActivityForResult(loadFromGalleryIntent, GALLERY_REQUEST);


    }

}

