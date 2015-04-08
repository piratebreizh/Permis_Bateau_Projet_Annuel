package database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Ludwig on 02/04/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    // Nom de notre base de données
    public static final String DB_NAME = "permisbateau.db";
    // Version de notre base de données
    public static final int DB_VERSION = 1;


    public DatabaseHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    /*
    Création de la base de donnée
     */
    public void onCreate(SQLiteDatabase db) {
        //table question
        db.execSQL("CREATE TABLE Question (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "numero INTEGER," +
                "pathimage TEXT," +
                "enoncer TEXT," +
                "reponse_A TEXT," +
                "reponse_B TEXT," +
                "reponse_C TEXT," +
                "reponse_D TEXT," +
                "correct_A TEXT," +
                "correct_B TEXT," +
                "correct_C TEXT," +
                "correct_D TEXT" +
                ");");

    }

    /*
    Suppression et re-création des tables
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //table question
        db.execSQL("DROP TABLE IF EXISTS Question");
        onCreate(db);
    }
}
