package com.projet.esgi.permisbateau;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.projet.esgi.myapplication.R;

import java.sql.SQLException;
import java.util.ArrayList;

import database.DataBase;
import module.Question;

public class QuestionActivity extends ActionBarActivity {

    private DataBase db;
    private ArrayList<Question> listQuestions;
    private int indexCurrentQuestion;
    private TextView enonce;
    private TextView repA;
    private TextView repB;
    private TextView repC;
    private TextView repD;
    private Button nextQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        enonce = (TextView) findViewById(R.id.enonce);
        repA = (TextView) findViewById(R.id.q1);
        repB = (TextView) findViewById(R.id.q2);
        repC = (TextView) findViewById(R.id.q3);
        repD = (TextView) findViewById(R.id.q4);
        nextQuestion = (Button) findViewById(R.id.nextQuestion);
        listQuestions = new ArrayList<Question>();
        indexCurrentQuestion =0;

        chargeQuestions();

        initListeners();

        chargeNextQuestion();

        /*try {
            chargeAQuestion();
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
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
     * Récupère les réponses
     * Charge la prochaine question ou lance l'activity correction
     */
    public void chargeNextQuestion(){
        //
        if(listQuestions.size()>=indexCurrentQuestion){
            Question q = listQuestions.get(indexCurrentQuestion);
            if(q!=null) {
                enonce.setText(q.getEnoncer());
                repA.setText(q.getReponse_A());
                repB.setText(q.getReponse_B());
                repC.setText(q.getReponse_C());
                repD.setText(q.getReponse_D());
                indexCurrentQuestion++;
            }
        }
        else{
            //ouvre l'activity Correction
            //TODO
        }
    }

    public void chargeAQuestion() throws SQLException {
        db = new DataBase(getApplicationContext());
        db.open();
        Cursor cursor = db.getAQuestion();
        if(cursor!=null){
            enonce.setText(cursor.getString(0));
            repA.setText(cursor.getString(1));
            repB.setText(cursor.getString(2));
            repC.setText(cursor.getString(3));
            repD.setText(cursor.getString(4));
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
