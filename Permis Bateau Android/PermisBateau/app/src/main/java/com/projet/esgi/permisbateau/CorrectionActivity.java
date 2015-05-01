package com.projet.esgi.permisbateau;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.projet.esgi.myapplication.R;

public class CorrectionActivity extends ActionBarActivity {

    //temporaire
    private TextView txtCorrection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_correction);

        /*txtCorrection = (TextView) findViewById(R.id.pageCorrect);

        Bundle bundle = getIntent().getExtras();
        ArrayList<Reponse> list = bundle.getParcelableArrayList("listRep");
        for(Reponse r : list){
            txtCorrection.append(String.valueOf(r.getRepA()) + "\n");
            txtCorrection.append(String.valueOf(r.getRepB()) + "\n");
            txtCorrection.append(String.valueOf(r.getRepC()) + "\n");
            txtCorrection.append(String.valueOf(r.getRepD()) + "\n");
            txtCorrection.append("\n");
        }*/
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_correction, menu);
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
