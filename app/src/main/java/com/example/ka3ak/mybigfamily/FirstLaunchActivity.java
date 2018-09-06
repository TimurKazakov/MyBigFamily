package com.example.ka3ak.mybigfamily;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ka3ak.mybigfamily.utlity.Orthography;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FirstLaunchActivity extends AppCompatActivity {

    static final int REQUEST_TAKE_PHOTO = 1;
    static final int GALLERY_REQUEST = 2;
    int DIALOG_DATE = 1;
    int myYear = 2000;
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





        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Cursor cursor =MainActivity.sqLiteDatabase.query("Family", null,null,null,null,null,null);
                if(cursor.moveToFirst()) {
                    do {
                        Log.d("myLog", ""+ "ID " + cursor.getInt(cursor.getColumnIndex("id")) +" Name " + cursor.getString(cursor.getColumnIndex("name")));


                    } while (cursor.moveToNext());
                }else {
                    Log.d("myLog", "Table not found");
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

    public void firstLaunchSaveBtn(View view) {
       if (    (Orthography.nameRegex(firstLaunchName.getText().toString()) && Orthography.nameRegex(firstLaunchSurname.getText().toString())) ||
               (Orthography.dataRegex(firstLaunchBirthday.getText().toString()) && Orthography.nameRegex(firstLaunchName.getText().toString())) ||
               (Orthography.dataRegex(firstLaunchBirthday.getText().toString()) && Orthography.nameRegex(firstLaunchSurname.getText().toString()))){
           ContentValues ownerValues =new ContentValues();
           ownerValues.put("name", firstLaunchName.getText().toString());
           ownerValues.put("surname", firstLaunchSurname.getText().toString());
           ownerValues.put("birthday", firstLaunchBirthday.getText().toString());

           ownerValues.put("photo", mCurrentPhotoPath);
           ownerValues.put("kinship", 10);

           ContentValues fatherValues = new ContentValues();
           fatherValues.put("name", " ");
           fatherValues.put("surname", " ");
           fatherValues.put("birthday", " ");
           fatherValues.put("kinship", 11);


           ContentValues motherValues = new ContentValues();
           motherValues.put("name", " ");
           motherValues.put("surname", " ");
           motherValues.put("birthday", " ");
           motherValues.put("kinship", 11);

           MainActivity.sqLiteDatabase.insert("Family",null, ownerValues);
           MainActivity.sqLiteDatabase.insert("Family",null, fatherValues);
           MainActivity.sqLiteDatabase.insert("Family",null, motherValues);
           Log.d("log", "Data Inserted");




           Intent backToMain = new Intent(FirstLaunchActivity.this, MainActivity.class);
           startActivity(backToMain);


       }
        else {
           Toast.makeText(this, R.string.input_correct_data, Toast.LENGTH_SHORT).show();
       }

    }
    }


