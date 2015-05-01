package com.projet.esgi.permisbateau;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.projet.esgi.myapplication.R;

import java.sql.SQLException;
import java.util.ArrayList;

import database.DataBase;
import database.DatabaseHelper;
import module.Examen;
import module.Question;


public class AccueilActivity extends ActionBarActivity {

    private DatabaseHelper dbHelper;

    private Button createDbButton;
    private Button launchExamButton;
    private Button launchThemeExam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);
        initListeners();
    }

    public void initListeners(){

        createDbButton = (Button) findViewById(R.id.createdb);
        launchExamButton = (Button) findViewById(R.id.launchexam);
        launchThemeExam = (Button) findViewById(R.id.launchthemexam);

        createDbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    createDb();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        launchExamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Examen exam = new Examen(getApplicationContext(),5);
                try {
                    exam.GenerateRandomQuestions();
                    ArrayList<Question> listQuestions = exam.getlistQuestions();
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("listQuestions",listQuestions);
                    Intent intent = new Intent(AccueilActivity.this,QuestionActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        launchThemeExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccueilActivity.this,ThematiqueActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_accueil, menu);
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


    /**temporaire*/////
    public void createDb() throws SQLException {

        DataBase db = new DataBase(getApplicationContext());

        db.open();
        db.dropDataBase();
        db.createDataBase();
        //dbHelper = new DatabaseHelper(getApplicationContext());
        //SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("numero",1);
        values.put("pathimage","");
        values.put("enoncer","11111un enoncer test qui est super long pour voir comment ça fait");
        values.put("reponse_A","111reponse A");
        values.put("reponse_B","111reponse B");
        values.put("reponse_C","11reponse C");
        values.put("reponse_D","11reponse D");
        values.put("correct_A","11true");
        values.put("correct_B","11false");
        values.put("correct_C","11false");
        values.put("correct_D","11false");

        db.insert("Question", null, values);



        values.clear();
        values.put("numero",2);
        values.put("pathimage","");
        values.put("enoncer","un enoncer2222222222 test qui est super long pour voir comment ça fait");
        values.put("reponse_A","reponse A2");
        values.put("reponse_B","reponse B2");
        values.put("reponse_C","reponse C2");
        values.put("reponse_D","reponse D2");
        values.put("correct_A","true");
        values.put("correct_B","false");
        values.put("correct_C","false");
        values.put("correct_D","false");

        db.insert("Question", null, values);

        values.clear();
        values.put("numero",3);
        values.put("pathimage","");
        values.put("enoncer","333333un enoncer test qui est super long pour voir comment ça fait");
        values.put("reponse_A","reponse A3");
        values.put("reponse_B","reponse B3");
        values.put("reponse_C","reponse C3");
        values.put("reponse_D","reponse D3");
        values.put("correct_A","true");
        values.put("correct_B","false");
        values.put("correct_C","false");
        values.put("correct_D","false");

        db.insert("Question", null, values);

        values.clear();
        values.put("numero",4);
        values.put("pathimage","");
        values.put("enoncer","4444un enoncer test qui est super long pour voir comment ça fait");
        values.put("reponse_A","reponse A4");
        values.put("reponse_B","reponse B4");
        values.put("reponse_C","reponse C4");
        values.put("reponse_D","reponse D4");
        values.put("correct_A","true");
        values.put("correct_B","false");
        values.put("correct_C","false");
        values.put("correct_D","false");

        db.insert("Question", null, values);

        values.clear();
        values.put("numero",5);
        values.put("pathimage","");
        values.put("enoncer","5555555un enoncer test qui est super long pour voir comment ça fait");
        values.put("reponse_A","reponse A5");
        values.put("reponse_B","reponse B5");
        values.put("reponse_C","reponse C5");
        values.put("reponse_D","reponse D5");
        values.put("correct_A","true");
        values.put("correct_B","false");
        values.put("correct_C","false");
        values.put("correct_D","false");

        db.insert("Question", null, values);

        values.clear();
        values.put("numero",6);
        values.put("pathimage","");
        values.put("enoncer","6666 enoncer test qui est super long pour voir comment ça fait");
        values.put("reponse_A","reponse A6");
        values.put("reponse_B","reponse B6");
        values.put("reponse_C","reponse C6");
        values.put("reponse_D","reponse D6");
        values.put("correct_A","true");
        values.put("correct_B","false");
        values.put("correct_C","false");
        values.put("correct_D","false");

        db.insert("Question", null, values);

        values.clear();
        values.put("numero",7);
        values.put("pathimage","");
        values.put("enoncer","77777un enoncer test qui est super long pour voir comment ça fait");
        values.put("reponse_A","reponse A7");
        values.put("reponse_B","reponse B7");
        values.put("reponse_C","reponse C7");
        values.put("reponse_D","reponse D7");
        values.put("correct_A","true");
        values.put("correct_B","false");
        values.put("correct_C","false");
        values.put("correct_D","false");

        db.insert("Question", null, values);

        values.clear();
        values.put("numero",8);
        values.put("pathimage","");
        values.put("enoncer","8888un enoncer test qui est super long pour voir comment ça fait");
        values.put("reponse_A","reponse A8");
        values.put("reponse_B","reponse B8");
        values.put("reponse_C","reponse C8");
        values.put("reponse_D","reponse D8");
        values.put("correct_A","true");
        values.put("correct_B","false");
        values.put("correct_C","false");
        values.put("correct_D","false");

        db.insert("Question", null, values);

        values.clear();
        values.put("numero",9);
        values.put("pathimage","");
        values.put("enoncer","99999un enoncer test qui est super long pour voir comment ça fait");
        values.put("reponse_A","reponse A9");
        values.put("reponse_B","reponse B9");
        values.put("reponse_C","reponse C9");
        values.put("reponse_D","reponse D9");
        values.put("correct_A","true");
        values.put("correct_B","false");
        values.put("correct_C","false");
        values.put("correct_D","false");

        db.insert("Question", null, values);

        values.clear();
        values.put("numero",10);
        values.put("pathimage","");
        values.put("enoncer","10101010un enoncer test qui est super long pour voir comment ça fait");
        values.put("reponse_A","reponse A10");
        values.put("reponse_B","reponse B10");
        values.put("reponse_C","reponse C10");
        values.put("reponse_D","reponse D10");
        values.put("correct_A","true");
        values.put("correct_B","false");
        values.put("correct_C","false");
        values.put("correct_D","false");

        db.insert("Question", null, values);

        values.clear();
        values.put("idThematique",1);
        values.put("nomThematique","Signalisation");
        values.put("themeValide",0);

        db.insert("Thematique", null, values);

        values.clear();
        values.put("idThematique",2);
        values.put("nomThematique","Direction");
        values.put("themeValide",0);

        db.insert("Thematique", null, values);

        values.clear();
        values.put("idSerie",1);
        values.put("nomSerie","Serie 1 Feux");
        values.put("theme",1);

        db.insert("Serie", null, values);

        values.clear();
        values.put("idSerie",2);
        values.put("nomSerie","Serie 2 Feux");
        values.put("theme",1);

        db.insert("Serie", null, values);

        values.clear();
        values.put("idSerie",2);
        values.put("nomSerie","Serie 1 Dir");
        values.put("theme",1);

        db.insert("Serie", null, values);

        values.clear();

        values.put("idQuestion",1);
        values.put("idSerie",1);

        db.insert("SerieQuestion", null, values);

        values.put("idQuestion",2);
        values.put("idSerie",1);

        db.insert("SerieQuestion", null, values);

        values.put("idQuestion",3);
        values.put("idSerie",1);

        db.insert("SerieQuestion", null, values);

        values.put("idQuestion",1);
        values.put("idSerie",2);

        db.insert("SerieQuestion", null, values);

        values.put("idQuestion",4);
        values.put("idSerie",2);

        db.insert("SerieQuestion", null, values);

        values.put("idQuestion",5);
        values.put("idSerie",2);

        db.insert("SerieQuestion", null, values);

        db.close();
    }
}
