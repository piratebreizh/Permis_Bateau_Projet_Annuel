package com.projet.esgi.permisbateau;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.projet.esgi.myapplication.R;

import java.util.ArrayList;

import module.Question;
import module.Reponse;

public class CorrectionActivity extends Activity {

    private ArrayList<Question> listQuestions;
    private ArrayList<Reponse> listReponses;
    private ArrayList<Button> listButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_correction);


        listQuestions = new ArrayList<Question>();
        listReponses = new ArrayList<Reponse>();
        listButtons = new ArrayList<Button>();

        getQuestionsAndResponses();
        createButtons();
        colorizeButton();

    }

    public void createButtons(){

        for(int i=1;i<=listQuestions.size();i++){
            int id = getResources().getIdentifier("button"+i, "id", getPackageName());
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

    /**
     * Colorise les boutons en vert ou rouge selon si la réponse est bonne ou non
     */
    public void colorizeButton(){

        for(int i=0;i<listQuestions.size();i++){
            Question q = listQuestions.get(i);
            Reponse r = listReponses.get(i);


            if(goodAnswer(q,r)){
                listButtons.get(i).setBackgroundColor(Color.GREEN);
            }else{
                listButtons.get(i).setBackgroundColor(Color.RED);
            }
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}


