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

    public boolean checkDbExist(){
        return mDbHelper.checkDataBaseExist();
    }

    public void createDataBase(){
        mDb.execSQL("CREATE TABLE IF NOT EXISTS Thematique (" +
                "idThematique INTEGER PRIMARY KEY," +
                "nomThematique TEXT," +
                "numeroThematique INTEGER" +
                ");");
        mDb.execSQL("CREATE TABLE IF NOT EXISTS Serie (" +
                "idSerie INTEGER PRIMARY KEY," +
                "nomSerie TEXT," +
                "theme INTEGER," +
                "numeroSerie INTEGER" +
                ");");
        mDb.execSQL("CREATE TABLE IF NOT EXISTS Question (" +
                "idQuestion INTEGER PRIMARY KEY," +
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
                "correct_D TEXT," +
                "idSerie INTEGER," +
                "FOREIGN KEY(idSerie) REFERENCES Serie(idSerie)" +
                ");");
        mDb.execSQL("CREATE TABLE IF NOT EXISTS Cours (" +
                "idCours INTEGER PRIMARY KEY," +
                "nomCours TEXT," +
                "idTheme INTEGER," +
                "FOREIGN KEY(idTheme) REFERENCES Thematique(idThematique)" +
                ");");
        mDb.execSQL("CREATE TABLE IF NOT EXISTS Statistiques (" +
                "idStats INTEGER PRIMARY KEY AUTOINCREMENT," +
                "score INTEGER," +
                "scoreTotal INTEGER," +
                "date TEXT," +
                "idTheme INTEGER," +
                "idSerie INTEGER," +
                "FOREIGN KEY(idSerie) REFERENCES Serie(idSerie)," +
                "FOREIGN KEY(idTheme) REFERENCES Thematique(idThematique)" +
                ");");
    }

    public void dropDataBase(){
        mDb.execSQL("DROP TABLE IF EXISTS Thematique");
        mDb.execSQL("DROP TABLE IF EXISTS Serie");
        mDb.execSQL("DROP TABLE IF EXISTS Question");
        mDb.execSQL("DROP TABLE IF EXISTS Cours");
        mDb.execSQL("DROP TABLE IF EXISTS Statistiques");
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

    public void execSql(String sql){
        mDb.execSQL(sql);
    }

    /**
     * retourne tous les thèmes
     * @return cursor
     */
    public Cursor getThemes(){
        try
        {
            Cursor cursor = mDb.query("Thematique",  // TABLE
                    new String[] { "idThematique","nomThematique" }, // SELECT
                    null, null,  // WHERE, ARGS
                    null, null, "nomThematique ASC", "100"); // GROUP BY, HAVING, ORDER BY, LIMIT
            if (cursor != null)
                cursor.moveToFirst();
            return cursor;
        }
        catch (Exception mSQLException)
        {
            Log.e("Error", "getThemes >>"+ mSQLException.toString());
            throw mSQLException;
        }

    }

    /**
     * Retourne les séries d'un thèmes
     * @param idThematique
     * @return
     */
    public Cursor getSeries(int idThematique){
        try
        {
            Cursor cursor = mDb.query("Serie",  // TABLE
                    new String[] { "idSerie","nomSerie" }, // SELECT
                    "theme" + "= ?", new String[] { String.valueOf(idThematique) },  // WHERE, ARGS
                    null, null, "nomSerie ASC", "100"); // GROUP BY, HAVING, ORDER BY, LIMIT
            if (cursor != null)
                cursor.moveToFirst();
            return cursor;
        }
        catch (Exception mSQLException)
        {
            Log.e("Error", "getSeries >>"+ mSQLException.toString());
            throw mSQLException;
        }
    }


    /**
     * Renvoie les questions d'une série
     * @param idSerie
     * @return
     */
    public Cursor getQuestionsFromSeries(int idSerie){
        try
        {
            String MY_QUERY = "SELECT Question.idQuestion,numero, pathimage,enoncer,reponse_A,reponse_B,reponse_C,reponse_D," +
                    "                    correct_A,correct_B,correct_C,correct_D FROM Question " +
                    "WHERE idSerie=? ORDER BY numero";
            Cursor cursor = mDb.rawQuery(MY_QUERY, new String[]{ Integer.toString(idSerie)});
            if (cursor != null)
                cursor.moveToFirst();
            return cursor;
        }
        catch (Exception mSQLException)
        {
            Log.e("Error", "getQuestionsFromSeries >>"+ mSQLException.toString());
            throw mSQLException;
        }
    }

    /**
     * Retourne l'ensemble des cours
     * @return
     */
    public Cursor getCours(){
        try
        {
            String MY_QUERY = "SELECT idCours,nomCours,idTheme FROM Cours ORDER BY nomCours";
            Cursor cursor = mDb.rawQuery(MY_QUERY, new String[]{ });
            if (cursor != null)
                cursor.moveToFirst();
            return cursor;
        }
        catch (Exception mSQLException)
        {
            Log.e("Error", "getCours >>"+ mSQLException.toString());
            throw mSQLException;
        }
    }

    /**
     * Retourne l'ensemble des statistiques
     * @return
     */
    public Cursor getStats(){
        try
        {
            String MY_QUERY = "SELECT idStats,date,score,scoreTotal,nomThematique,nomSerie,Statistiques.idTheme " +
                    "FROM Statistiques LEFT JOIN Thematique ON Statistiques.idTheme=Thematique.idThematique " +
                    "INNER JOIN Serie ON Statistiques.idSerie=Serie.idSerie ORDER BY Statistiques.idTheme ASC, idStats DESC";
            Cursor cursor = mDb.rawQuery(MY_QUERY, new String[]{ });
            if (cursor != null)
                cursor.moveToFirst();
            return cursor;
        }
        catch (Exception mSQLException)
        {
            Log.e("Error", "getStats >>"+ mSQLException.toString());
            throw mSQLException;
        }
    }

    /**
     * Retourne l'id du theme d'une série
     * @param idSerie
     * @return
     */
    public Cursor getThemeFromSerie(int idSerie){
        try
        {
            Cursor cursor = mDb.query("Serie",  // TABLE
                    new String[] { "theme" }, // SELECT
                    "idSerie" + "= ?", new String[] { String.valueOf(idSerie) },  // WHERE, ARGS
                    null, null, null, "100"); // GROUP BY, HAVING, ORDER BY, LIMIT
            if (cursor != null)
                cursor.moveToFirst();
            return cursor;
        }
        catch (Exception mSQLException)
        {
            Log.e("Error", "getThemeFromSerie >>"+ mSQLException.toString());
            throw mSQLException;
        }
    }

    /**
     * Retourne l'id de la série d'une question
     * @param idQuestion
     * @return
     */
    public Cursor getSerieFromQuestion(int idQuestion){
        try
        {
            Cursor cursor = mDb.query("Question",  // TABLE
                    new String[] { "idSerie" }, // SELECT
                    "idQuestion" + "= ?", new String[] { String.valueOf(idQuestion) },  // WHERE, ARGS
                    null, null, null, "100"); // GROUP BY, HAVING, ORDER BY, LIMIT
            if (cursor != null)
                cursor.moveToFirst();
            return cursor;
        }
        catch (Exception mSQLException)
        {
            Log.e("Error", "getSeries >>"+ mSQLException.toString());
            throw mSQLException;
        }
    }
}

