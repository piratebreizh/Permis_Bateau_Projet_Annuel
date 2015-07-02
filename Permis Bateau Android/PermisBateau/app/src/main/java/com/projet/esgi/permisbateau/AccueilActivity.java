package com.projet.esgi.permisbateau;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.projet.esgi.myapplication.R;

import java.sql.SQLException;
import java.util.ArrayList;

import adapter.AccueilAdapter;
import adapter.ItemAccueil;


public class AccueilActivity extends Activity {

    private ListView listAccueil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        ArrayList<ItemAccueil> items = new ArrayList<ItemAccueil>();

        //examem blanc
        Bitmap bmp_exblanc = BitmapFactory.decodeResource(getResources(), R.drawable.d_examblanc);
        ItemAccueil item_exblanc = new ItemAccueil(bmp_exblanc,"Examen blanc","Conditions réelles d'examen");

        //examen thématique
        Bitmap bmp_exth = BitmapFactory.decodeResource(getResources(), R.drawable.d_examthem);
        ItemAccueil item_exth = new ItemAccueil(bmp_exth,"Examen thématique","Des questions par thèmes");

        //liste des cours
        Bitmap bmp_cours = BitmapFactory.decodeResource(getResources(), R.drawable.d_cours);
        ItemAccueil item_cours = new ItemAccueil(bmp_cours,"Cours","Révisez vos leçons");

        //stats
        Bitmap bmp_stats = BitmapFactory.decodeResource(getResources(), R.drawable.d_historique);
        ItemAccueil item_stats = new ItemAccueil(bmp_stats,"Historiques","Suivez votre évolution");

        //mise à jour
        Bitmap bmp_maj = BitmapFactory.decodeResource(getResources(), R.drawable.d_maj);
        ItemAccueil item_maj = new ItemAccueil(bmp_maj,"Mettre à jour","Mise à jour manuelle des données");

        items.add(item_exth);
        items.add(item_exblanc);
        items.add(item_stats);
        items.add(item_cours);
        items.add(item_maj);

        AccueilAdapter adapter = new AccueilAdapter(this,items);

        //liste des villes
        listAccueil = (ListView) findViewById(R.id.listAccueil);
        listAccueil.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_accueil, menu);
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
