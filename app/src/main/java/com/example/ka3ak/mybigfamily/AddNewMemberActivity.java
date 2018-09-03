package com.example.ka3ak.mybigfamily;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.ka3ak.mybigfamily.FirstLaunchActivity.GALLERY_REQUEST;
import static com.example.ka3ak.mybigfamily.FirstLaunchActivity.REQUEST_TAKE_PHOTO;

public class AddNewMemberActivity extends AppCompatActivity {
    Integer kinship = 10;
    EditText addNewMemberName, addNewMemberSurname, addNewMemberPatronymic, addNewMemberBirthday,
            addNewMemberFather, addNewMemberFatherBirthday, addNewMemberMother,
            addNewMemberMotherBirthday, addNewMemberLikes, addNewMemberDislikes, addNewMemberPets, addNewMemberNotes;
    Button save, cancel;
    ImageView addNewMemberPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_member);
        addNewMemberPhoto = findViewById(R.id.add_new_member_image_view);
        addNewMemberName = findViewById(R.id.add_new_member_name_editText);
        addNewMemberSurname = findViewById(R.id.add_new_member_surname_editText);
        addNewMemberPatronymic = findViewById(R.id.add_new_member_patronymic_editText);
        addNewMemberBirthday = findViewById(R.id.add_new_member_birthday_editText);
        addNewMemberFather = findViewById(R.id.add_new_member_father_editText);
        addNewMemberFatherBirthday = findViewById(R.id.add_new_member_father_birthday);
        addNewMemberMother = findViewById(R.id.add_new_member_mother_editText);
        addNewMemberMotherBirthday = findViewById(R.id.add_new_member_mother_birthday);
        addNewMemberLikes = findViewById(R.id.add_new_member_like_editText);
        addNewMemberDislikes = findViewById(R.id.add_new_member_dislike_editText);
        addNewMemberPets = findViewById(R.id.add_new_member_pets_editText);
        addNewMemberNotes = findViewById(R.id.add_new_member_notes_editText);

        Spinner kinshipSpinner = (Spinner) findViewById(R.id.spinner_gender);
        // Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.family_enum, android.R.layout.simple_spinner_item);
        // Определяем разметку для использования при выборе элемента
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Применяем адаптер к элементу spinner
        kinshipSpinner.setAdapter(adapter);
        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                // Получаем выбранный объект
                String item = (String) parent.getItemAtPosition(position);
                if (position == 0 | position==1){
                    kinship = 10;
                }
                if (position == 2 | position==3){
                    kinship = 10+1;
                }if (position == 4 | position==5){
                    kinship = 10-1;
                }
//                Toast.makeText(getBaseContext(), "" + item, Toast.LENGTH_SHORT).show();
                if (position == 6) {
                    kinship = 10;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
        kinshipSpinner.setOnItemSelectedListener(itemSelectedListener);


    }

    int DIALOG_DATE = 1;
    int myYear = 2000;
    int myMonth = 00;
    int myDay = 01;

    ImageView startPhoto = null;
    Uri photoURI = null;
    String mCurrentPhotoPath;

    //photo
    public void onClickAddNewMemberImageView(View view) {
//        mCurrentNewMemberPhotoPath = mCurrentPhotoPath;
        startPhoto = findViewById(R.id.add_new_member_image_view);
        onClickPhoto(startPhoto);
    }
    public void onClickPhoto(View view){


        AlertDialog.Builder chosePhotoOrGallery = new AlertDialog.Builder(AddNewMemberActivity.this);
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

    if (requestCode == GALLERY_REQUEST) {
        Bitmap bitmap = null;
        int width = 0;
        int height = 0;

        if (resultCode == RESULT_OK) {
            Uri selectedImage = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                width = startPhoto.getWidth();
                height = startPhoto.getHeight();

            } catch (IOException e) {
                e.printStackTrace();
            }
            startPhoto.setImageBitmap(bitmap);

        }
    }



    }
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
// photo



   //buttons
    public void onClickSave(View view) {
        ContentValues newMemberContentValues =new ContentValues();
        newMemberContentValues.put("name", addNewMemberName.getText().toString());
        newMemberContentValues.put("surname", addNewMemberSurname.getText().toString());
        newMemberContentValues.put("patronymic", addNewMemberPatronymic.getText().toString());
        newMemberContentValues.put("birthday", addNewMemberBirthday.getText().toString());
        newMemberContentValues.put("photo", mCurrentPhotoPath);
        newMemberContentValues.put("kinship", kinship);
        newMemberContentValues.put("mother", addNewMemberMother.getText().toString());
        newMemberContentValues.put("motherBirthday", addNewMemberMotherBirthday.getText().toString());
        newMemberContentValues.put("father", addNewMemberFather.getText().toString());
        newMemberContentValues.put("fatherBirthday", addNewMemberFatherBirthday.getText().toString());
        newMemberContentValues.put("likes", addNewMemberLikes.getText().toString());
        newMemberContentValues.put("dislikes", addNewMemberDislikes.getText().toString());
        newMemberContentValues.put("pets", addNewMemberPets.getText().toString());
        newMemberContentValues.put("notes", addNewMemberNotes.getText().toString());


        String fatherName, fatherSurname, fatherPatronymic = "", motherName, motherSurname, motherPatronymic = "";
        String[] fatherData = addNewMemberFather.getText().toString().split(" ");
        fatherSurname = fatherData[0];
        fatherName = fatherData[1];
        if (fatherData.length>2) {
            fatherPatronymic = fatherData[2];
        }

        String[] motherData = addNewMemberMother.getText().toString().split(" ");
        motherSurname = motherData[0];
        motherName = motherData[1];
        if (motherData.length>2) {
            motherPatronymic = motherData[2];
        }
        ContentValues nemMemberFatherContentValues =new ContentValues();
        nemMemberFatherContentValues.put("name", fatherName);
        nemMemberFatherContentValues.put("surname", fatherSurname);
       nemMemberFatherContentValues.put("patronymic", fatherPatronymic);
       nemMemberFatherContentValues.put("birthday", addNewMemberFatherBirthday.getText().toString());



        ContentValues nemMemberMotherContentValues =new ContentValues();
       nemMemberMotherContentValues.put("name", motherName);
       nemMemberMotherContentValues.put("surname", motherSurname);
       nemMemberMotherContentValues.put("patronymic", motherPatronymic);
       nemMemberMotherContentValues.put("birthday", addNewMemberMotherBirthday.getText().toString());


        MainActivity.sqLiteDatabase.beginTransaction();
        try {

               long res1 = MainActivity.sqLiteDatabase.insert("Family", null, newMemberContentValues);

            Log.d("log", "Data Inserted "+ res1);
            long res2 = MainActivity.sqLiteDatabase.insert("Family", null, nemMemberFatherContentValues);
            Log.d("log", "Father`s Data Inserted " + res2);
            long res3 = MainActivity.sqLiteDatabase.insert("Family", null, nemMemberMotherContentValues);
            Log.d("log", "Mother`s Data Inserted "+ res3);
            MainActivity.sqLiteDatabase.setTransactionSuccessful();
        }catch (Exception exp){
            Toast.makeText(this,"DataBase error", Toast.LENGTH_LONG).show();
        }
        finally {
            MainActivity.sqLiteDatabase.endTransaction();
        }
        finish();
    }

    public void onClickCancel(View view) {
       finish();
    }
  //buttons



}
