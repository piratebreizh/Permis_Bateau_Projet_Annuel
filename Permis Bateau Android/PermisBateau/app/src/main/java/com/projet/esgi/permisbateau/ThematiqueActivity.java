package com.projet.esgi.permisbateau;

import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.projet.esgi.myapplication.R;

import java.sql.SQLException;
import java.util.List;

import database.DataBase;

public class ThematiqueActivity extends ActionBarActivity {

    private DataBase db;
    private ListView listThemes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thematique);

        listThemes = (ListView) findViewById(R.id.listTheme);

        try {
            chargeThemes();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void chargeThemes() throws SQLException{
        db = new DataBase(getApplicationContext());
        db.open();
        Cursor themesDB = db.getThemes();
        String[] themes = new String[themesDB.getCount()];

        if (themesDB != null ) {
            if  (themesDB.moveToFirst()) {
                do {
                    themes[themesDB.getPosition()] = themesDB.getString(themesDB.getColumnIndex("nomThematique"));
                }while (themesDB.moveToNext());
            }
        }
        ArrayAdapter<String> themeAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, themes);

        listThemes.setAdapter(themeAdapter);
        db.close();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_thematique, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
