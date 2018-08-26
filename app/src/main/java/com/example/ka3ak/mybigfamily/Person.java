package com.example.ka3ak.mybigfamily;

import android.app.Activity;
import android.database.Cursor;
import android.support.v4.view.ViewPager;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Person extends Activity  {



public static Person returnOwner(){
    Cursor cursor = MainActivity.sqLiteDatabase.query("Family", null,null,null, null, null, null);
    cursor.moveToFirst();
    String name = cursor.getColumnName(cursor.getColumnIndex("name"));
    String surname = cursor.getColumnName(cursor.getColumnIndex("surname"));
    String birthday = cursor.getColumnName(cursor.getColumnIndex("birthday"));
    Log.d("log", "Data insert in main");

    Person me = new Person(name, surname, birthday);
    return (me);
}
    private int id;
    private String name;
    private  String surname;
    private   String patronymic;
    private   String birthday;
    private   String photoUri;
    private   String kinship;
    private   String mother;
    private   String father;
    private   String likes;
    private   String dislikes;
    private   String pets;
    private   String notes;


    public Person(String name, String surname, String birthday) {
        this.name = name;
        this.surname = surname;
        this.birthday = birthday;
    }

    public Person(int id, String name, String surname, String patronymic, String birthday, String photoUri, String kinship, String mother, String father) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.birthday = birthday;
        this.photoUri = photoUri;
        this.kinship = kinship;
        this.mother = mother;
        this.father = father;
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

    public String getPhotoUri() {
        return photoUri;
    }

    public void setPhotoUri(String photoUri) {
        this.photoUri = photoUri;
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

    public String getKinship() {
        return kinship;
    }

    public void setKinship(String kinship) {
        this.kinship = kinship;
    }



}
