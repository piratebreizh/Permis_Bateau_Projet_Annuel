package database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.sql.SQLException;


/**
 * Created by Ludwig on 02/04/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    // Nom de notre base de données
    public static final String DB_NAME = "permisbateau.db";
    // Chemin de la base de données
    private static String DB_PATH = "";
    // Version de notre base de données
    public static final int DB_VERSION = 1;
    // La base
    private SQLiteDatabase mDataBase;


    public DatabaseHelper(Context context){

        super(context,DB_NAME,null,DB_VERSION);
        if(android.os.Build.VERSION.SDK_INT >= 17){
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        }
        else
        {
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        }
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    //Vérifie que la base de données existe ici: /data/data/your package/databases/Da Name
    public boolean checkDataBaseExist()
    {
        File dbFile = new File(DB_PATH + DB_NAME);
        return dbFile.exists();
    }

    //Ouverture de la base de données
    public boolean openDataBase() throws SQLException
    {
        String mPath = DB_PATH + DB_NAME;
        File file = new File(mPath);
        if (file.exists() && !file.isDirectory())
        mDataBase = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        return mDataBase != null;
    }

    //Fermeture de la base de données
    public synchronized void close()
    {
        if(mDataBase != null)
            mDataBase.close();
        super.close();
    }
}
