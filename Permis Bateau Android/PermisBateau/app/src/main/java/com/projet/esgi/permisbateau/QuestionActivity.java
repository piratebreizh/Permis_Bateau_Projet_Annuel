package com.projet.esgi.permisbateau;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.projet.esgi.myapplication.R;

import java.sql.SQLException;
import java.util.ArrayList;

import database.DataBase;
import module.Question;
import module.Reponse;

public class QuestionActivity extends ActionBarActivity {

    private DataBase db;
    private ArrayList<Question> listQuestions;
    private ArrayList<Reponse> listReponses;
    private int indexCurrentQuestion;
    private TextView enonce;
    private TextView txtRepA;
    private TextView txtRepB;
    private TextView txtRepC;
    private TextView txtRepD;
    private CheckBox repA;
    private CheckBox repB;
    private CheckBox repC;
    private CheckBox repD;
    private Button nextQuestion;
    private ProgressBar timer;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        enonce = (TextView) findViewById(R.id.enonce);
        txtRepA = (TextView) findViewById(R.id.q1);
        txtRepB = (TextView) findViewById(R.id.q2);
        txtRepC = (TextView) findViewById(R.id.q3);
        txtRepD = (TextView) findViewById(R.id.q4);
        repA = (CheckBox) findViewById(R.id.rep1);
        repB = (CheckBox) findViewById(R.id.rep2);
        repC = (CheckBox) findViewById(R.id.rep3);
        repD = (CheckBox) findViewById(R.id.rep4);
        nextQuestion = (Button) findViewById(R.id.nextQuestion);
        listQuestions = new ArrayList<Question>();
        listReponses = new ArrayList<Reponse>();
        indexCurrentQuestion =0;
        timer = (ProgressBar) findViewById(R.id.timeBar);

        //intit progressBar
        timer.setVisibility(ProgressBar.VISIBLE);
        timer.setMax(30);

        chargeQuestions();

        initListeners();

        chargeNextQuestion();
    }

    public void initListeners(){
        nextQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chargeNextQuestion();
            }
        });
    }

    /**
     * Récupère la liste des questions
     */
    public void chargeQuestions(){
        Bundle bundle = getIntent().getExtras();
        listQuestions = bundle.getParcelableArrayList("listQuestions");
    }

    /**
     * Lance le timer de 30sec
     */
    public void launchTimer(){
        countDownTimer = new CountDownTimer(30 * 1000, 1000) {
            // 500 means, onTick function will be called at every 500 milliseconds

            @Override
            public void onTick(long leftTimeInMilliseconds) {
                long seconds = leftTimeInMilliseconds / 1000;
                int barVal= (30) - ((int)(seconds/60*100)+(int)(seconds%60));
                timer.setProgress(barVal);
                //textTimer.setText(String.format("%02d", seconds/60) + ":" + String.format("%02d", seconds%60));
                // format the textview to show the easily readable format

            }
            @Override
            public void onFinish() {
                Toast.makeText(getApplicationContext(),"Fin du timer",Toast.LENGTH_LONG).show();
                chargeNextQuestion();
            }
        }.start();
    }

    /**
     * Récupère les réponses
     * Charge la prochaine question ou lance l'activity correction
     */
    public void chargeNextQuestion(){
        //récupère les réponses si ce n'est pas la première question
        if(indexCurrentQuestion>0){
            Reponse rep = new Reponse(repA.isChecked(),repB.isChecked(),repC.isChecked(),repD.isChecked());
            listReponses.add(rep);
        }
        //charge la prochaine question
        if(listQuestions.size()>indexCurrentQuestion){
            launchTimer();
            Question q = listQuestions.get(indexCurrentQuestion);
            if(q!=null) {
                enonce.setText(q.getEnoncer());
                txtRepA.setText(q.getReponse_A());
                txtRepB.setText(q.getReponse_B());
                txtRepC.setText(q.getReponse_C());
                txtRepD.setText(q.getReponse_D());
                repA.setChecked(false);
                repB.setChecked(false);
                repC.setChecked(false);
                repD.setChecked(false);
                indexCurrentQuestion++;
            }
        }
        else{
            //ouvre l'activity Correction
            //TODO
            Intent intent = new Intent(QuestionActivity.this,CorrectionActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("listRep",listReponses);
            intent.putExtras(bundle);
            startActivity(intent);

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_question, menu);
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
