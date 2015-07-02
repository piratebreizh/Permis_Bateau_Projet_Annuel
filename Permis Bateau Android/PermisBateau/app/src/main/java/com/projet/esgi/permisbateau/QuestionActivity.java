package com.projet.esgi.permisbateau;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.SystemClock;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.projet.esgi.myapplication.R;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;

import database.DataBase;
import module.Question;
import module.Reponse;

public class QuestionActivity extends Activity {

    private ArrayList<Question> listQuestions;
    private ArrayList<Reponse> listReponses;
    private int indexCurrentQuestion;
    private ImageView image;
    private TextView enonce;
    private TextView txtRepA;
    private TextView txtRepB;
    private TextView txtRepC;
    private TextView txtRepD;
    private ToggleButton repA;
    private ToggleButton repB;
    private ToggleButton repC;
    private ToggleButton repD;
    private Button nextQuestion;
    private ProgressBar timer;
    private CountDownTimer countDownTimer;
    private boolean delaiDepasse;
    private String parent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        enonce = (TextView) findViewById(R.id.enonce);
        image = (ImageView) findViewById(R.id.imageQuestion);
        txtRepA = (TextView) findViewById(R.id.q1);
        txtRepB = (TextView) findViewById(R.id.q2);
        txtRepC = (TextView) findViewById(R.id.q3);
        txtRepD = (TextView) findViewById(R.id.q4);
        repA = (ToggleButton) findViewById(R.id.rep1);
        repB = (ToggleButton) findViewById(R.id.rep2);
        repC = (ToggleButton) findViewById(R.id.rep3);
        repD = (ToggleButton) findViewById(R.id.rep4);
        nextQuestion = (Button) findViewById(R.id.nextQuestion);
        listQuestions = new ArrayList<Question>();
        listReponses = new ArrayList<Reponse>();
        indexCurrentQuestion =0;
        timer = (ProgressBar) findViewById(R.id.timeBar);
        delaiDepasse = false;

        chargeQuestions();

        //intit progressBar
        if(parent.equals("Serie")){
            timer.setVisibility(ProgressBar.VISIBLE);
            timer.setMax(30);
        }

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
        parent=bundle.getString("parent");
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
                Toast.makeText(getApplicationContext(),"Délai dépassé",Toast.LENGTH_LONG).show();
                delaiDepasse=true;
                chargeNextQuestion();
            }
        }.start();
    }

    /**
     * Récupère les réponses
     * Charge la prochaine question ou lance l'activity correction
     */
    public void chargeNextQuestion(){
        //récupère les réponses si ce n'est pas la première questionx
        Reponse rep;
        if(indexCurrentQuestion>0 ){
            if(delaiDepasse){
                rep = new Reponse(false,false,false,false);
            }else{
                rep = new Reponse(repA.isChecked(),repB.isChecked(),repC.isChecked(),repD.isChecked());
            }
            listReponses.add(rep);
            delaiDepasse = false;
        }
        //charge la prochaine question
        if(listQuestions.size()>indexCurrentQuestion){
            if(countDownTimer != null) {
                countDownTimer.cancel();
                timer.setProgress(0);
            }
            //timer que si examen blanc
            if(parent.equals("Serie"))launchTimer();

            Question q = listQuestions.get(indexCurrentQuestion);
            if(q!=null) {
                enonce.setText(q.getEnoncer());
                File filePath = getFileStreamPath(q.getPathimage() + ".png");
                if(filePath.exists()){
                    image.setImageDrawable(Drawable.createFromPath(filePath.toString()));
                }else{
                    image.setImageDrawable(getResources().getDrawable(R.drawable.noimage));
                }
                txtRepA.setText(q.getReponse_A());
                txtRepB.setText(q.getReponse_B());
                txtRepC.setText(q.getReponse_C());
                txtRepD.setText(q.getReponse_D());
                repA.setChecked(false);
                repB.setChecked(false);
                repC.setChecked(false);
                repD.setChecked(false);
                indexCurrentQuestion++;

                //si moins de réponse
                if (q.getReponse_C().equals("")){
                    txtRepC.setVisibility(View.INVISIBLE);
                    repC.setVisibility(View.GONE);
                }
                if(q.getReponse_D().equals("")){
                    txtRepD.setVisibility(View.INVISIBLE);
                    repD.setVisibility(View.GONE);
                }

            }
        }
        else{
            //ouvre l'activity Correction
            if (countDownTimer!=null) countDownTimer.cancel();
            Intent intent = new Intent(QuestionActivity.this,CorrectionActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("listQuest",listQuestions);
            bundle.putParcelableArrayList("listRep",listReponses);
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        if (countDownTimer!=null) countDownTimer.cancel();
        NavUtils.navigateUpFromSameTask(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_question, menu);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //Validation du retour à l'accueil
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Cette action annulera l'examen, êtes-vous sûr(e) de vouloir retourner à l'écran d'accueil ?").setTitle("Annuler l'examen");
                builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (countDownTimer!=null) countDownTimer.cancel();
                        onBackPressed();
                    }
                });
                builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.setCancelable(false);
                AlertDialog dialog = builder.create();
                dialog.show();
                return true;
            case R.id.action_contact:
                //Validation du retour à l'accueil
                AlertDialog.Builder builderContact = new AlertDialog.Builder(this);
                builderContact.setMessage("Cette action annulera l'examen, êtes-vous sûr(e) de vouloir retourner à l'écran d'accueil ?").setTitle("Annuler l'examen");
                builderContact.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (countDownTimer!=null) countDownTimer.cancel();
                        Intent intent = new Intent(getApplicationContext(),ContactActivity.class);
                        startActivity(intent);
                    }
                });
                builderContact.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builderContact.setCancelable(false);
                AlertDialog dialogContact = builderContact.create();
                dialogContact.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

