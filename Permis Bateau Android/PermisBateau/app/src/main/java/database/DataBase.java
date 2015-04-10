package database;

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
}


/*        Bundle extras = getIntent().getExtras();
        dbHelper = (DatabaseHelper)extras.getSerializable("DB");

        // Open database for reading
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // Construct and execute query
        Cursor cursor = db.query("Question",  // TABLE
                new String[] { "enoncer","reponse_A","reponse_B","reponse_C","reponse_D" }, // SELECT
                "id" + "= ?", new String[] { "1" },  // WHERE, ARGS
                null, null, "id ASC", "100"); // GROUP BY, HAVING, ORDER BY, LIMIT
        if (cursor != null)
            cursor.moveToFirst();
        // Load result into model object
        enonce.setText(cursor.getString(1));
        repA.setText(cursor.getString(2));
        repB.setText(cursor.getString(3));
        repC.setText(cursor.getString(4));
        repD.setText(cursor.getString(5));*/