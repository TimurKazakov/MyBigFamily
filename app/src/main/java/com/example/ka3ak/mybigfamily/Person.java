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
    String name = cursor.getString(cursor.getColumnIndex("name"));
    String surname = cursor.getString(cursor.getColumnIndex("surname"));
    String birthday = cursor.getString(cursor.getColumnIndex("birthday"));
    Log.d("log", "Data insert in main");

    Person me = new Person(name, surname, birthday);
    return (me);
}

    public Person(int id) {
        this.id = id;
        Cursor cursor = MainActivity.sqLiteDatabase.query("Family", null, "id ="+id, null,null,null,null);
        this.name = cursor.getString(cursor.getColumnIndex("name"));
        this.surname = cursor.getString(cursor.getColumnIndex("surname"));
        this.patronymic = cursor.getString(cursor.getColumnIndex("patronymic"));
        this.birthday = cursor.getString(cursor.getColumnIndex("birthday"));
        this.photoUri = cursor.getString(cursor.getColumnIndex("photoUri"));
        this.kinship = cursor.getString(cursor.getColumnIndex("kinship"));
        this.mother = cursor.getString(cursor.getColumnIndex("mother"));
        this.motherBirthday = cursor.getString(cursor.getColumnIndex("motherBirthday"));
        this.father = cursor.getString(cursor.getColumnIndex("father"));
        this.fatherBirthday = cursor.getString(cursor.getColumnIndex("fatherBirthday"));
        this.likes = cursor.getString(cursor.getColumnIndex("likes"));
        this.dislikes = cursor.getString(cursor.getColumnIndex("dislikes"));
        this.pets = cursor.getString(cursor.getColumnIndex("pets"));
        this.notes = cursor.getString(cursor.getColumnIndex("notes"));
        cursor.close();
    }

    private int id;
    private String name;
    private  String surname;
    private   String patronymic;
    private   String birthday;
    private   String photoUri;
    private   String kinship;
    private   String mother;
    private   String motherBirthday;
    private   String father;
    private   String fatherBirthday;
    private   String likes;
    private   String dislikes;
    private   String pets;
    private   String notes;


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

    public Person(String name, String surname, String birthday) {
        this.name = name;
        this.surname = surname;
        this.birthday = birthday;

    }

    public Person(int id, String name, String surname, String patronymic, String birthday, String photoUri, String kinship, String mother, String motherBirthday, String father, String fatherBirthday) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.birthday = birthday;
        this.photoUri = photoUri;
        this.kinship = kinship;
        this.mother = mother;
        this.motherBirthday = motherBirthday;
        this.father = father;
        this.fatherBirthday = fatherBirthday;
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
