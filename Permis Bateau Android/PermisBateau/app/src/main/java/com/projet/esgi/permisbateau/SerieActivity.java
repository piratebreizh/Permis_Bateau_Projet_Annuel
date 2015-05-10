package com.projet.esgi.permisbateau;

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


public class SerieActivity extends ActionBarActivity {

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
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_serie, menu);
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
