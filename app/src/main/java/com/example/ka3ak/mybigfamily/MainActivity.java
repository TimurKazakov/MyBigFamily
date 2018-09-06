package com.example.ka3ak.mybigfamily;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;

import data.FamilyContract;

public class MainActivity extends AppCompatActivity {

    public static ViewPager upperPager, middlePager, lowerPager, viewPager;
    public static FamilyContract familyBase;
    public static SQLiteDatabase sqLiteDatabase;
    public static Person[] dataBasePersons = null;
    TextView upperMainActivityTextView, middleMainActivityTextView, lowerMainActivityTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        familyBase = new FamilyContract(this);
        sqLiteDatabase = familyBase.getWritableDatabase();
        collectingPrimaryData();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainActivity.upperPager = (ViewPager) findViewById(R.id.viewPager);
        MainActivity.middlePager = (ViewPager) findViewById(R.id.viewPager2);
        MainActivity.lowerPager = (ViewPager) findViewById(R.id.viewPager3);
        upperMainActivityTextView = findViewById(R.id.main_activity_1_swapper_textview);
        middleMainActivityTextView = findViewById(R.id.main_activity_2_swapper_textview);
        lowerMainActivityTextView = findViewById(R.id.main_activity_3_swapper_textview);

        ViewPagerAdapter topSwap = new ViewPagerAdapter(this);
        ViewPagerAdapter midSwap = new ViewPagerAdapter(this);
        ViewPagerAdapter botSwap = new ViewPagerAdapter(this);
//        MainActivity.upperPager.setAdapter(topSwap);
//        MainActivity.middlePager.setAdapter(midSwap);
//        MainActivity.lowerPager.setAdapter(botSwap);

        MyTask me = new MyTask();
        Log.d("myLog", "before execute");
        me.execute(topSwap, midSwap, botSwap, upperMainActivityTextView, middleMainActivityTextView, lowerMainActivityTextView );
        Log.d("myLog", "after execute");


    }

    class MyTask extends AsyncTask<Object,Void,Void> {


        public Person getTaskPerson() {
            return taskPerson;
        }

        public void setTaskPerson(Person taskPerson) {
            this.taskPerson = taskPerson;
        }

        private  Person taskPerson = null;

        public Person[] returnOlderPeople(){
            ArrayList<Person> olderPeopleArray = new ArrayList<>();

            Person father = findFather();
            Person mother = findMother();
            olderPeopleArray.add(father);
            olderPeopleArray.add(mother);



            for (int i = 1; i <MainActivity.dataBasePersons.length ; i++) {
                if ( MainActivity.dataBasePersons[i].getKinship()> taskPerson.getKinship()){
                    olderPeopleArray.add(MainActivity.dataBasePersons[i]);

                }
            }
            HashSet<Person> personHashSet= new HashSet<>(olderPeopleArray);
            Person[] olderPeople = personHashSet.toArray(new Person[0]);
            return olderPeople;
        }
        public Person[] returnSameAgePeople(){
            ArrayList<Person> someAgePeopleArray = new ArrayList<>();

            for (int i = 0; i <MainActivity.dataBasePersons.length ; i++) {
                if ( MainActivity.dataBasePersons[i].getKinship()== taskPerson.getKinship()){
                    someAgePeopleArray.add(MainActivity.dataBasePersons[i]);

                }
            }
            Person[] SameAgePeople = someAgePeopleArray.toArray(new Person[0]);
            return SameAgePeople;
        }
        public Person[] returnYoungerPeople(){
            ArrayList<Person> youngerPeopleArray = new ArrayList<>();

            for (int i = 1; i <MainActivity.dataBasePersons.length ; i++) {
                if (MainActivity.dataBasePersons[i].getKinship()< taskPerson.getKinship()){
                    youngerPeopleArray.add(MainActivity.dataBasePersons[i]);

                }
            }
            Person[] youngerPeople = youngerPeopleArray.toArray(new Person[0]);
            return youngerPeople;
        }



        private Person findMother() {
            if (taskPerson.getMother() !=null) {
                String[] motherName = taskPerson.getMother().split(" ");
                for (int i = 0; i < MainActivity.dataBasePersons.length; i++) {
                    if (MainActivity.dataBasePersons[i].getName().equals(motherName[1]) && MainActivity.dataBasePersons[i].getSurname().equals(motherName[0])) {
                        return MainActivity.dataBasePersons[i];
                    }
                }
            }
            return null;
        }
        private Person findFather() {
            if (taskPerson.getFather() !=null) {
                String[] fatherName = taskPerson.getFather().split(" ");
                for (int i = 0; i < MainActivity.dataBasePersons.length; i++) {
                    if (MainActivity.dataBasePersons[i].getName().equals(fatherName[1]) && MainActivity.dataBasePersons[i].getSurname().equals(fatherName[0])) {
                        return MainActivity.dataBasePersons[i];
                    }
                }
            }
            return null;
        }

        public MyTask() {
            super();

        }



        @Override
        protected Void doInBackground(Object... objects) {

            try {

                Person owner = dataBasePersons[0];
                setTaskPerson(owner);

                Person[] older = returnOlderPeople();
                Person[] someAge = returnSameAgePeople();
                Person[] younger = returnYoungerPeople();



                ViewPagerAdapter top = (ViewPagerAdapter) objects[0];
                ViewPagerAdapter mid = (ViewPagerAdapter) objects[1];
                ViewPagerAdapter bot = (ViewPagerAdapter) objects[2];

                TextView upperMainActivityTextView = (TextView) objects[3];
                TextView middleMainActivityTextView = (TextView) objects[4];
                TextView lowerMainActivityTextView = (TextView) objects[5];

                top.setPersonToShow(older);
                mid.setPersonToShow(someAge);
                bot.setPersonToShow(younger);
                top.setMainActivityPersonDataTextView(upperMainActivityTextView);
                mid.setMainActivityPersonDataTextView(middleMainActivityTextView);
                bot.setMainActivityPersonDataTextView(lowerMainActivityTextView);
                MainActivity.upperPager.setAdapter(top);
                MainActivity.middlePager.setAdapter(mid);
                MainActivity.lowerPager.setAdapter(bot);

            } catch (Exception e){
                Log.d("myLog", "error");
            }
            return null;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }


    }

    private void collectingPrimaryData() {
        Cursor cursor = sqLiteDatabase.query("Family", null, null, null, null, null, null);
        // first launch
        if (cursor.getCount() == 0) {

            Intent firstLaunch = new Intent(MainActivity.this, FirstLaunchActivity.class);
            startActivity(firstLaunch);
        } else {
            Log.d("log", "Table not found");
        }
        // first launch

        cursor.moveToFirst();
        Log.d("myLog", "start in table " + cursor.getCount());
        if (cursor.getCount() > 0) {
//
            dataBasePersons = new Person[cursor.getCount()];

            for (int i = 0; i < cursor.getCount(); i++) {

                Log.d("myLog", "start");
//                +1 for save position like in sql and for correct working person constructor - it takes data from sql by the db.id and can`t be 0
                dataBasePersons[i] = new Person(i + 1);
//                dataBasePersons.add(new Person(cursor.getInt(cursor.getColumnIndex("id"))));
                Log.d("myLog", "finish");


                if (!cursor.isLast()) {
                    cursor.moveToNext();
                } else break;
            }

            for (int i = 0; i < dataBasePersons.length; i++) {

                Log.d("myLog", dataBasePersons[i].getName() + " " + dataBasePersons[i].getSurname());
                Log.d("myLog", "" + dataBasePersons.length);
            }
        }
    }


    public void onClickTree(View view) {
        Intent intent = new Intent(MainActivity.this, TreeFrame.class);
        startActivity(intent);
    }

    public void onClickMember(View view) {
        Intent intent = new Intent(MainActivity.this, AddNewMemberActivity.class);
        startActivity(intent);
    }

    public void onClickMe(View view) {
        Intent intent = new Intent(MainActivity.this, MeActivity.class);
        startActivity(intent);
    }

    public void onClickBirthday(View view) {
        Intent intent = new Intent(MainActivity.this, BirthdayActivity.class);
        startActivity(intent);
    }
}
