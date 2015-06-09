package com.projet.esgi.permisbateau;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.projet.esgi.myapplication.R;

import java.io.File;


public class CoursActivity extends Activity {

    private ListView listCours;
    //chemin des pdfs
    private static String PDF_PATH = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cours);

        //récupère le chemin des cours en fonction de la version d'Android
        if(android.os.Build.VERSION.SDK_INT >= 17){
            PDF_PATH = getApplicationContext().getApplicationInfo().dataDir + "/cours/";
        }
        else
        {
            PDF_PATH = "/data/data/" + getApplicationContext().getPackageName() + "/cours/";
        }

        listCours = (ListView) findViewById(R.id.listCours);


    }

    public void chargeCours(){
        File f = new File(PDF_PATH);
        File file[] = f.listFiles();;
        String[] listFiles = new String[file.length];

        ArrayAdapter<String> themeAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listFiles);
        //http://developer.android.com/guide/topics/data/data-storage.html#filesInternal
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cours, menu);
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
