package com.projet.esgi.permisbateau;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.projet.esgi.myapplication.R;

import java.io.File;

import module.Question;
import module.Reponse;


public class CorrectionQuestionActivity extends Activity {

    private TextView enonce;
    private ImageView image;
    private TextView txtRepA;
    private TextView txtRepB;
    private TextView txtRepC;
    private TextView txtRepD;
    private CheckBox repA;
    private CheckBox repB;
    private CheckBox repC;
    private CheckBox repD;
    private Button quit;

    private Question theQuestion;
    private Reponse theResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_correction_question);

        enonce = (TextView) findViewById(R.id.enonce);
        image = (ImageView) findViewById(R.id.imageQuestion);
        txtRepA = (TextView) findViewById(R.id.q1);
        txtRepB = (TextView) findViewById(R.id.q2);
        txtRepC = (TextView) findViewById(R.id.q3);
        txtRepD = (TextView) findViewById(R.id.q4);
        repA = (CheckBox) findViewById(R.id.rep1);
        repB = (CheckBox) findViewById(R.id.rep2);
        repC = (CheckBox) findViewById(R.id.rep3);
        repD = (CheckBox) findViewById(R.id.rep4);
        quit = (Button) findViewById(R.id.quit);

        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Bundle bundle = getIntent().getExtras();
        theQuestion = (Question) bundle.get("question");
        theResponse = (Reponse) bundle.get("response");

        displayCorrection(theQuestion,theResponse);
    }


    /**
     * Affiche la correction de la question
     * @param q
     */
    public void displayCorrection(Question q, Reponse r){

        //question
        enonce.setText(q.getEnoncer());
        File filePath = getFileStreamPath(q.getPathimage());
        if(filePath.exists()){
            image.setImageDrawable(Drawable.createFromPath(filePath.toString()));
        }else{
            image.setImageDrawable(getResources().getDrawable(R.drawable.noimage));
        }
        txtRepA.setText(q.getReponse_A());
        txtRepB.setText(q.getReponse_B());
        txtRepC.setText(q.getReponse_C());
        txtRepD.setText(q.getReponse_D());

        //réponse de l'utilisateur
        repA.setChecked(r.getRepA());
        repB.setChecked(r.getRepB());
        repC.setChecked(r.getRepC());
        repD.setChecked(r.getRepD());

        //correction des réponses
        if(q.getCorrect_A()==((r.getRepA()) ? 1 : 0)) {
            txtRepA.setTextColor(Color.GREEN);
        }else{
            txtRepA.setTextColor(Color.RED);
        }
        if(q.getCorrect_B()==((r.getRepB()) ? 1 : 0)) {
            txtRepB.setTextColor(Color.GREEN);
        }else{
            txtRepB.setTextColor(Color.RED);
        }
        if(q.getCorrect_C()==((r.getRepC()) ? 1 : 0)) {
            txtRepC.setTextColor(Color.GREEN);
        }else{
            txtRepC.setTextColor(Color.RED);
        }
        if(q.getCorrect_D()==((r.getRepD()) ? 1 : 0)) {
            txtRepD.setTextColor(Color.GREEN);
        }else{
            txtRepD.setTextColor(Color.RED);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_correction_question, menu);
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
