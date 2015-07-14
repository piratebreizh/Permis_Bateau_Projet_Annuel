package com.projet.esgi.permisbateau;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.projet.esgi.myapplication.R;
import webservice.UpdateTask;


public class MajActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maj);
        launchMaj();
    }

    public void launchMaj(){
        try{
            SharedPreferences pref = getSharedPreferences("date", MODE_PRIVATE);
            String lastdate = pref.getString("DATE_VERSION_LOCALE", null);
            UpdateTask update = new UpdateTask(MajActivity.this);
            update.execute(lastdate,"online");
        }catch (Exception e){
            Log.e("erreur", e.getMessage());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_maj, menu);
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
}
