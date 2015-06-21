package com.projet.esgi.permisbateau;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.projet.esgi.myapplication.R;

import java.io.File;
import java.sql.SQLException;

import database.DataBase;


public class CoursActivity extends Activity {

    private ListView listCours;
    private DataBase db;
    private int[] listIdCours;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cours);

        listCours = (ListView) findViewById(R.id.listCours);

        try {
            chargeCours();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        initListener();
    }

    public void chargeCours() throws SQLException {

        db = new DataBase(getApplicationContext());
        db.open();
        Cursor coursDB = db.getCours();
        String[] cours = new String[coursDB.getCount()];
        listIdCours = new int[coursDB.getCount()];

        if (coursDB != null ) {
            if  (coursDB.moveToFirst()) {
                do {
                    listIdCours[coursDB.getPosition()] = coursDB.getInt(coursDB.getColumnIndex("idCours"));
                    cours[coursDB.getPosition()] = coursDB.getString(coursDB.getColumnIndex("nomCours"));
                }while (coursDB.moveToNext());
            }
        }
        ArrayAdapter<String> coursAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, cours);

        listCours.setAdapter(coursAdapter);
        db.close();
    }

    public void initListener(){

        listCours.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                File file = new File(Environment.getExternalStorageDirectory().toString() + "/permisbateaucours",
                        Integer.toString(listIdCours[position]) + ".pdf");
                Intent target = new Intent(Intent.ACTION_VIEW);
                target.setDataAndType(Uri.fromFile(file), "application/pdf");
                target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

                Intent intent = Intent.createChooser(target, "Open File");
                try {
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getApplicationContext(),"Une application de lecture de fichier PDF est " +
                            "nécessaire pour l'ouverture de ce fichier.",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_serie, menu);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
