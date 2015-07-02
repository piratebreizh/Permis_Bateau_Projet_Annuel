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

import database.DataBase;
import module.Examen;
import module.Question;


public class SerieActivity extends Activity {

    private DataBase db;
    private ListView listSeries;
    private int[] listIdSeries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serie);

        listSeries = (ListView) findViewById(R.id.listSerie);

        try {
            chargeSeries();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        initListener();

    }

    /**
     * Charge la liste de toutes les séries selon le thème choisi (0 si pas de thème=Examen Blanc)
     * @throws SQLException
     */
    public void chargeSeries() throws SQLException {
        db = new DataBase(getApplicationContext());
        Bundle bundle = getIntent().getExtras();
        db.open();
        Cursor seriesDB = db.getSeries(bundle.getInt("idThematique"));
        String[] series = new String[seriesDB.getCount()];
        listIdSeries = new int[seriesDB.getCount()];

        if (seriesDB != null ) {
            if  (seriesDB.moveToFirst()) {
                do {
                    listIdSeries[seriesDB.getPosition()] = seriesDB.getInt(seriesDB.getColumnIndex("idSerie"));
                    series[seriesDB.getPosition()] = seriesDB.getString(seriesDB.getColumnIndex("nomSerie"));
                }while (seriesDB.moveToNext());
            }
        }
        ArrayAdapter<String> themeAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, series);

        listSeries.setAdapter(themeAdapter);
        db.close();
    }

    public void initListener(){
        listSeries.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SerieActivity.this, QuestionActivity.class);
                Bundle bundle = new Bundle();
                Examen exam = new Examen(getApplicationContext());
                try {
                    exam.getQuestionsFromSerie(listIdSeries[position]);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                bundle.putParcelableArrayList("listQuestions",exam.getlistQuestions());

                Bundle inputBundle = getIntent().getExtras();

                //gestion de l'affichage de la progressbar dans l'activity question
                if(0 == inputBundle.getInt("idThematique")){
                    bundle.putString("parent","Blanc");
                }else {
                    bundle.putString("parent","Theme");
                }

                intent.putExtras(bundle);
                startActivity(intent);
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
            case R.id.action_contact:
                Intent intent = new Intent(getApplicationContext(),ContactActivity.class);
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

