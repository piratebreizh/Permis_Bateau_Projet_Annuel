package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLException;

/**
 * Created by Ludwig on 10/04/2015.
 */
public class DataBase {

    private final Context mContext;
    private SQLiteDatabase mDb;
    private DatabaseHelper mDbHelper;

    public DataBase(Context context){
        this.mContext = context;
        mDbHelper = new DatabaseHelper(mContext);
    }

    public void createDataBase(){
        //mDbHelper.onCreate(mDb);
        mDb.execSQL("CREATE TABLE Question (" +
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
    public void dropDataBase(){
        //mDbHelper.dropDataBase(mDb);
        mDb.execSQL("DROP TABLE IF EXISTS Question");
    }

    public DataBase open() throws SQLException
    {
        try
        {
            mDbHelper.openDataBase();
            mDbHelper.close();
            mDb = mDbHelper.getReadableDatabase();
        }
        catch (SQLException mSQLException)
        {
            Log.e("Error", "open >>" + mSQLException.toString());
            throw mSQLException;
        }
        return this;
    }

    public void close()
    {
        mDbHelper.close();
    }

    public void insert(String table,String column,ContentValues cv){
        mDb.insert(table,column,cv);
    }

    //fonction temporaire
    public Cursor getAQuestion()
    {
        try
        {
            Cursor cursor = mDb.query("Question",  // TABLE
                    new String[] { "enoncer","reponse_A","reponse_B","reponse_C","reponse_D" }, // SELECT
                    "id" + "= ?", new String[] { "1" },  // WHERE, ARGS
                    null, null, "id ASC", "100"); // GROUP BY, HAVING, ORDER BY, LIMIT
            if (cursor != null)
                cursor.moveToFirst();
            return cursor;
        }
        catch (Exception mSQLException)
        {
            Log.e("Error", "getTestData >>"+ mSQLException.toString());
            throw mSQLException;
        }
    }

    public Cursor getQuestionById(int id){

        try
        {
            Cursor cursor = mDb.query("Question",  // TABLE
                    new String[] { "enoncer","reponse_A","reponse_B","reponse_C","reponse_D" }, // SELECT
                    "id" + "= ?", new String[] { Integer.toString(id) },  // WHERE, ARGS
                    null, null, "id ASC", "100"); // GROUP BY, HAVING, ORDER BY, LIMIT
            if (cursor != null)
                cursor.moveToFirst();
            return cursor;
        }
        catch (Exception mSQLException)
        {
            Log.e("Error", "getQuestionByID >>"+ mSQLException.toString());
            throw mSQLException;
        }

    }

    public int getNbTotalQuestions(){
        return 0;
    }

    public Cursor getQuestionByIdAndThem(int id,int theme){
        return null;
    }

    public Cursor getRandomNbQuestions(int nb){
        try
        {
            Cursor cursor = mDb.query("Question",  // TABLE
                    new String[] { "id","numero", "pathimage","enoncer","reponse_A","reponse_B","reponse_C","reponse_D",
                    "correct_A","correct_B","correct_C","correct_D"}, // SELECT
                    null,  // WHERE, ARGS
                    null, null, null, "id", Integer.toString(nb)); // GROUP BY, HAVING, ORDER BY, LIMIT
            if (cursor != null)
                cursor.moveToFirst();
            return cursor;
        }
        catch (Exception mSQLException)
        {
            Log.e("Error", "getRandomNbQuestions >>"+ mSQLException.toString());
            throw mSQLException;
        }
    }

    public Cursor getRandomNbQuestionsByTheme(int nb,int theme){
        try
        {
            Cursor cursor = mDb.query("Question",  // TABLE
                    new String[] { "id","numero", "pathimage","enoncer","reponse_A","reponse_B","reponse_C","reponse_D",
                            "correct_A","correct_B","correct_C","correct_D"}, // SELECT
                    "theme" + "= ?", new String[] { Integer.toString(theme) },  // WHERE, ARGS
                    null, null, "id RANDOM()", Integer.toString(nb)); // GROUP BY, HAVING, ORDER BY, LIMIT
            if (cursor != null)
                cursor.moveToFirst();
            return cursor;
        }
        catch (Exception mSQLException)
        {
            Log.e("Error", "getRandomNbQuestionsByTheme >>"+ mSQLException.toString());
            throw mSQLException;
        }
    }
}

