package com.projet.esgi.permisbateau;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.projet.esgi.myapplication.R;

import java.util.ArrayList;

import adapter.ItemStats;
import adapter.StatsAdapter;
import database.DataBase;

public class StatistiquesActivity extends Activity {

    private ListView listStats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistiques);

        ArrayList<ItemStats> items = new ArrayList<ItemStats>();

        //récupere les stats
        try{
            DataBase db = new DataBase(getApplicationContext());
            db.open();
            db.createDataBase();
            Cursor stats = db.getStats();

            if (stats != null ) {
                if  (stats.moveToFirst()) {
                    do {

                        ItemStats iS = new ItemStats(stats.getString(stats.getColumnIndex("nomThematique")) + " - " +
                                stats.getString(stats.getColumnIndex("nomSerie")),
                                stats.getString(stats.getColumnIndex("score")),
                                stats.getString(stats.getColumnIndex("scoreTotal")),
                                stats.getString(stats.getColumnIndex("date")),
                                stats.getInt(stats.getColumnIndex("idTheme")));
                        items.add(iS);
                    }while (stats.moveToNext());
                }
            }
            db.close();

            StatsAdapter adapter = new StatsAdapter(this, items);

            //liste des stats
            listStats = (ListView) findViewById(R.id.listStats);
            listStats.setAdapter(adapter);

        }catch (Exception e){
            e.printStackTrace();
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_statistiques, menu);
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
