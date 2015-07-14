package com.projet.esgi.permisbateau;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import com.projet.esgi.myapplication.R;

import android.os.Handler;

import webservice.UpdateTask;


public class LoadActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);

    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            checkUpdates();
        }catch (Exception e){
            Log.e("erreur", e.getMessage());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_load, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_contact) {
            Intent intent = new Intent(getApplicationContext(),ContactActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        return;
    }

    /**
     * Vérifie si une mise à jour est disponible et la propose à l'utilisateur
     * Fait également la première mise à jour avec le JSON local
     */
    public void checkUpdates() throws Exception{
        SharedPreferences pref = getSharedPreferences("date", MODE_PRIVATE);
        String lastdate = pref.getString("DATE_VERSION_LOCALE", null);
        //première utilisation de l'application
        if(lastdate==null){
            //copie les questions stockées en local
            UpdateTask update = new UpdateTask(getApplicationContext());
            update.execute("","local");
        }
        else{
            //test la connexion
            ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
            if(!isConnected) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Intent intent = new Intent(getApplicationContext(),AccueilActivity.class);
                        startActivity(intent);
                    }
                }, 2000);

            }else{
                //si il y a une connexion
                //lance la maj
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Intent intent = new Intent(getApplicationContext(),MajActivity.class);
                        startActivity(intent);
                    }
                }, 2000);
            }


        }

    }


}
