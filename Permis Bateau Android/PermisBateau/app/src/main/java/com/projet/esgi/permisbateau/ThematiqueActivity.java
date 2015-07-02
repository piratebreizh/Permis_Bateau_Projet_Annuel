package com.projet.esgi.permisbateau;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.projet.esgi.myapplication.R;

import java.sql.SQLException;
import java.util.ArrayList;

import database.DataBase;
import module.Question;

public class ThematiqueActivity extends Activity {

    private DataBase db;
    private ListView listThemes;
    private int[] listIdThemes;

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

        initListener();
    }

    public void chargeThemes() throws SQLException{
        db = new DataBase(getApplicationContext());
        db.open();
        Cursor themesDB = db.getThemes();
        String[] themes = new String[themesDB.getCount()];
        listIdThemes = new int[themesDB.getCount()];

        if (themesDB != null ) {
            if  (themesDB.moveToFirst()) {
                do {
                    listIdThemes[themesDB.getPosition()] = themesDB.getInt(themesDB.getColumnIndex("idThematique"));
                    themes[themesDB.getPosition()] = themesDB.getString(themesDB.getColumnIndex("nomThematique"));
                }while (themesDB.moveToNext());
            }
        }
        ArrayAdapter<String> themeAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, themes);

        listThemes.setAdapter(themeAdapter);
        db.close();
    }

    public void initListener(){
        listThemes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ThematiqueActivity.this, SerieActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("idThematique",listIdThemes[position]);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_thematique, menu);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_contact:
                Intent intent = new Intent(getApplicationContext(),ContactActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
