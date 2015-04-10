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

import database.DatabaseHelper;


public class Accueil extends ActionBarActivity {

    private DatabaseHelper dbHelper;

    private Button createDbButton;
    private Button launchQuestionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);
        initListeners();
    }

/**temporaire*/////
    public void createDb(){

        dbHelper = new DatabaseHelper(getApplicationContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("numero",1);
        values.put("pathimage","");
        values.put("enoncer","un enoncer test qui est super long pour voir comment ça fait");
        values.put("reponse_A","reponse A");
        values.put("reponse_B","reponse B");
        values.put("reponse_C","reponse C");
        values.put("reponse_D","reponse D");
        values.put("correct_A","true");
        values.put("correct_B","false");
        values.put("correct_C","false");
        values.put("correct_D","false");

        db.insertOrThrow("Question",null,values);
        db.close();
    }

    public void initListeners(){

        createDbButton = (Button) findViewById(R.id.createdb);
        launchQuestionButton = (Button) findViewById(R.id.launchquestion);

        createDbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDb();
            }
        });

        launchQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Bundle bundle = new Bundle();
                //bundle.putSerializable("DB",dbHelper);
                Intent intent = new Intent(Accueil.this,Question.class);
                //intent.putExtras(bundle);
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
}
