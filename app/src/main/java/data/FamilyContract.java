package data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FamilyContract extends SQLiteOpenHelper {



    /**
     * Имя файла базы данных
     */
    private static final String DATABASE_NAME = "Relatives.db";

    /**
     * Версия базы данных. При изменении схемы увеличить на единицу
     */
    private static final int DATABASE_VERSION = 1;



    public FamilyContract(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
       String SQL_CREATE_MAIN;

       SQL_CREATE_MAIN= "create table Family( id INTEGER primary key autoincrement, " +
               "name text not null, " +
               "surname text not null, " +
               "patronymic text, " +
               "birthday text, " +
               "photo text, " +
               "kinship INTEGER, " +
               "father text, " +
               "fatherBirthday text," +
               "mother text," +
               "motherBirthday text," +
               "likes text," +
               "dislikes text," +
               "pets text," +
               "notes text)";




        sqLiteDatabase.execSQL(SQL_CREATE_MAIN);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }




}
