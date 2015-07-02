package com.projet.esgi.permisbateau;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.projet.esgi.myapplication.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import adapter.AccueilAdapter;
import adapter.CorrectionAdapter;
import adapter.ItemAccueil;
import adapter.ItemCorrection;
import database.DataBase;
import module.Question;
import module.Reponse;

public class CorrectionActivity extends Activity {

    private ArrayList<Question> listQuestions;
    private ArrayList<Reponse> listReponses;

    private ListView listCorrection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_correction);


        listQuestions = new ArrayList<Question>();
        listReponses = new ArrayList<Reponse>();


        getQuestionsAndResponses();
        //createButtons();
        storeStats();
        createList();

    }
    public void createList(){

        ArrayList<ItemCorrection> items = new ArrayList<ItemCorrection>();

        for(int i=1;i<=listQuestions.size();i++){

            ItemCorrection iC = new ItemCorrection("Question n°" + i,listQuestions,listReponses);
            items.add(iC);

            /*int id = getResources().getIdentifier("button"+i, "id", getPackageName());
            Button b = (Button) findViewById(id);
            final int currentButton=i - 1;
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("question",listQuestions.get(currentButton));
                    bundle.putParcelable("response",listReponses.get(currentButton));
                    Intent intent = new Intent(CorrectionActivity.this,CorrectionQuestionActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
            b.setVisibility(View.VISIBLE);
            listButtons.add(b);
            */

            CorrectionAdapter adapter = new CorrectionAdapter(this,items);

            //liste des corrections
            listCorrection = (ListView) findViewById(R.id.listCorrection);
            listCorrection.setAdapter(adapter);
        }
    }


    /**
     * Récupère les questions et les réponses enregistrer dans l'activity QuestionsActivity
     */
    public void getQuestionsAndResponses(){

        Bundle bundle = getIntent().getExtras();
        listQuestions = bundle.getParcelableArrayList("listQuest");
        listReponses = bundle.getParcelableArrayList("listRep");

    }

    public void storeStats(){
        int score=0;
        for (int i=0 ; i<listQuestions.size();i++){
            if(goodAnswer(listQuestions.get(i),listReponses.get(i))){
                score++;
            }
        }

        DataBase db = new DataBase(getApplicationContext());
        int idTheme;
        int idSerie = -1;
        try{
            db.open();
            db.createDataBase();

            Cursor serie = db.getSerieFromQuestion(listQuestions.get(1).getId());
            if(serie==null) return;
            idSerie = serie.getInt(serie.getColumnIndex("idSerie"));

            Cursor theme = db.getThemeFromSerie(idSerie);
            if(theme==null) return;
            idTheme = theme.getInt(theme.getColumnIndex("theme"));

            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Calendar cal = Calendar.getInstance();

            ContentValues values = new ContentValues();
            values.put("score", score);
            values.put("scoreTotal", listQuestions.size());
            values.put("date", dateFormat.format(cal.getTime()));
            values.put("idTheme", idTheme);
            values.put("idSerie", idSerie);

            db.insert("Statistiques", null, values);
            values.clear();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * Renvoie vrai si la question à bien été répondu, faux sinon
     * @param q
     * @param r
     * @return
     */
    public static boolean goodAnswer(Question q, Reponse r){
        return (q.getCorrect_A()==((r.getRepA()) ? 1 : 0)) && (q.getCorrect_B()==((r.getRepB()) ? 1 : 0))
                && (q.getCorrect_C()==((r.getRepC()) ? 1 : 0)) && (q.getCorrect_D()==((r.getRepD()) ? 1 : 0));
    }



    @Override
    public void onBackPressed() {
        NavUtils.navigateUpFromSameTask(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_correction, menu);
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

