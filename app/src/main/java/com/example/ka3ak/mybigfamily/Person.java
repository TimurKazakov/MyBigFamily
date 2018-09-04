package com.example.ka3ak.mybigfamily;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.File;

public class Person extends Activity {


    private int id;
    private String name;
    private String surname;
    private String patronymic;
    private String birthday;
    private String photo;
    private int kinship;
    private String mother;
    private String motherBirthday;
    private String father;
    private String fatherBirthday;
    private String likes;
    private String dislikes;
    private String pets;
    private String notes;

    public Person(int id) {
        this.id = id;
        String sqlQuery = "select * from Family where id=" + id;
        Cursor cursor = MainActivity.sqLiteDatabase.rawQuery(sqlQuery, null);
        cursor.moveToFirst();
        this.name = cursor.getString(cursor.getColumnIndex("name"));
        this.surname = cursor.getString(cursor.getColumnIndex("surname"));
        this.patronymic = cursor.getString(cursor.getColumnIndex("patronymic"));
        this.birthday = cursor.getString(cursor.getColumnIndex("birthday"));
        this.photo = cursor.getString(cursor.getColumnIndex("photo"));
        this.kinship = cursor.getInt(cursor.getColumnIndex("kinship"));
        this.mother = cursor.getString(cursor.getColumnIndex("mother"));
        this.motherBirthday = cursor.getString(cursor.getColumnIndex("motherBirthday"));
        this.father = cursor.getString(cursor.getColumnIndex("father"));
        this.fatherBirthday = cursor.getString(cursor.getColumnIndex("fatherBirthday"));
        this.likes = cursor.getString(cursor.getColumnIndex("likes"));
        this.dislikes = cursor.getString(cursor.getColumnIndex("dislikes"));
        this.pets = cursor.getString(cursor.getColumnIndex("pets"));
        this.notes = cursor.getString(cursor.getColumnIndex("notes"));
        cursor.close();
        Log.d("myLog", "Person creating over ");
    }

    public Person(String name, String surname, String birthday) {
        this.name = name;
        this.surname = surname;
        this.birthday = birthday;


    }



    public static Person returnOwner() {

        Person me = new Person(1);
        return (me);
    }

    public Bitmap getReSizePhotoBitmap() {
        File photoFile = new File(this.photo);
        Bitmap bitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
        bitmap = Bitmap.createScaledBitmap(bitmap, 150, 200, true);
        return bitmap;

    }

    public Bitmap getFullSizePhotoBitmap() {
        File photoFile = new File(this.photo);
        Bitmap bitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
        return bitmap;
    }

    public String getMotherBirthday() {
        return motherBirthday;
    }

    public void setMotherBirthday(String motherBirthday) {
        this.motherBirthday = motherBirthday;
    }

    public String getFatherBirthday() {
        return fatherBirthday;
    }

    public void setFatherBirthday(String fatherBirthday) {
        this.fatherBirthday = fatherBirthday;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getDislikes() {
        return dislikes;
    }

    public void setDislikes(String dislikes) {
        this.dislikes = dislikes;
    }

    public String getPets() {
        return pets;
    }

    public void setPets(String pets) {
        this.pets = pets;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getMother() {
        return mother;
    }

    public void setMother(String mother) {
        this.mother = mother;
    }

    public String getFather() {
        return father;
    }

    public void setFather(String father) {
        this.father = father;
    }

    public int getKinship() {
        return kinship;
    }

    public void setKinship(int kinship) {
        this.kinship = kinship;
    }


}
