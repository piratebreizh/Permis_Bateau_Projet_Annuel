package com.projet.esgi.permisbateau;

import android.app.Activity;
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        return;
    }

    /**temporaire*/////
    public void createDb() throws SQLException {
        SharedPreferences pref = getSharedPreferences("date", MODE_PRIVATE);
        Editor editor = pref.edit();
        editor.clear();
        editor.commit();
        /*File dirFiles = getFilesDir();
        for (String strFile : dirFiles.list())
        {
            Toast.makeText(getApplicationContext(),strFile.toString(),Toast.LENGTH_SHORT).show();
        }*/
        /*
        db.dropDataBase();
        db.createDataBase();
        //dbHelper = new DatabaseHelper(getApplicationContext());
        //SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();


        values.clear();
        values.put("idThematique",1);
        values.put("nomThematique","Signalisation");
        values.put("numeroThematique",0);

        db.insert("Thematique", null, values);

        values.clear();
        values.put("idThematique",2);
        values.put("nomThematique","Direction");
        values.put("numeroThematique",0);

        db.insert("Thematique", null, values);

        values.clear();
        values.put("idSerie",1);
        values.put("nomSerie","Serie 1 Feux");
        values.put("theme",1);

        db.insert("Serie", null, values);

        values.clear();
        values.put("idSerie",2);
        values.put("nomSerie","Serie 2 Feux");
        values.put("theme",1);

        db.insert("Serie", null, values);

        values.clear();
        values.put("idSerie",3);
        values.put("nomSerie","Serie 1 Dir");
        values.put("theme",2);

        db.insert("Serie", null, values);

        values.clear();
        values.put("idSerie",4);
        values.put("nomSerie","Serie pour exam blanc");
        values.put("theme",0);

        db.insert("Serie", null, values);

        values.clear();


        values.put("numero",1);
        values.put("pathimage", Environment.DIRECTORY_DCIM + "/aaa.jpg");
        values.put("enoncer","11111un enoncer test qui est super long pour voir comment ça fait");
        values.put("reponse_A","111reponse A");
        values.put("reponse_B","111reponse B");
        values.put("reponse_C","11reponse C");
        values.put("reponse_D","11reponse D");
        values.put("correct_A",1);
        values.put("correct_B",0);
        values.put("correct_C",0);
        values.put("correct_D",0);
        values.put("idSerie",1);

        db.insert("Question", null, values);


        values.clear();
        values.put("numero",2);
        values.put("pathimage","");
        values.put("enoncer","un enoncer2222222222 test qui est super long pour voir comment ça fait");
        values.put("reponse_A","reponse A2");
        values.put("reponse_B","reponse B2");
        values.put("reponse_C","reponse C2");
        values.put("reponse_D","reponse D2");
        values.put("correct_A",1);
        values.put("correct_B",0);
        values.put("correct_C",0);
        values.put("correct_D",0);
        values.put("idSerie",1);

        db.insert("Question", null, values);

        values.clear();
        values.put("numero",3);
        values.put("pathimage","");
        values.put("enoncer","333333un enoncer test qui est super long pour voir comment ça fait");
        values.put("reponse_A","reponse A3");
        values.put("reponse_B","reponse B3");
        values.put("reponse_C","reponse C3");
        values.put("reponse_D","reponse D3");
        values.put("correct_A",1);
        values.put("correct_B",0);
        values.put("correct_C",0);
        values.put("correct_D",0);
        values.put("idSerie",1);

        db.insert("Question", null, values);

        values.clear();
        values.put("numero",4);
        values.put("pathimage","");
        values.put("enoncer","4444un enoncer test qui est super long pour voir comment ça fait");
        values.put("reponse_A","reponse A4");
        values.put("reponse_B","reponse B4");
        values.put("reponse_C","reponse C4");
        values.put("reponse_D","reponse D4");
        values.put("correct_A",1);
        values.put("correct_B",0);
        values.put("correct_C",0);
        values.put("correct_D",0);
        values.put("idSerie",4);

        db.insert("Question", null, values);

        values.clear();
        values.put("numero",5);
        values.put("pathimage","");
        values.put("enoncer","5555555un enoncer test qui est super long pour voir comment ça fait");
        values.put("reponse_A","reponse A5");
        values.put("reponse_B","reponse B5");
        values.put("reponse_C","reponse C5");
        values.put("reponse_D","reponse D5");
        values.put("correct_A",1);
        values.put("correct_B",0);
        values.put("correct_C",0);
        values.put("correct_D",0);
        values.put("idSerie",4);

        db.insert("Question", null, values);

        values.clear();
        values.put("numero",6);
        values.put("pathimage","");
        values.put("enoncer","6666 enoncer test qui est super long pour voir comment ça fait");
        values.put("reponse_A","reponse A6");
        values.put("reponse_B","reponse B6");
        values.put("reponse_C","reponse C6");
        values.put("reponse_D","reponse D6");
        values.put("correct_A",1);
        values.put("correct_B",0);
        values.put("correct_C",0);
        values.put("correct_D",0);
        values.put("idSerie",2);

        db.insert("Question", null, values);

        values.clear();
        values.put("numero",7);
        values.put("pathimage","");
        values.put("enoncer","77777un enoncer test qui est super long pour voir comment ça fait");
        values.put("reponse_A","reponse A7");
        values.put("reponse_B","reponse B7");
        values.put("reponse_C","reponse C7");
        values.put("reponse_D","reponse D7");
        values.put("correct_A",1);
        values.put("correct_B",0);
        values.put("correct_C",0);
        values.put("correct_D",0);
        values.put("idSerie",2);

        db.insert("Question", null, values);

        values.clear();
        values.put("numero",8);
        values.put("pathimage","");
        values.put("enoncer","8888un enoncer test qui est super long pour voir comment ça fait");
        values.put("reponse_A","reponse A8");
        values.put("reponse_B","reponse B8");
        values.put("reponse_C","reponse C8");
        values.put("reponse_D","reponse D8");
        values.put("correct_A",1);
        values.put("correct_B",0);
        values.put("correct_C",0);
        values.put("correct_D",0);
        values.put("idSerie",3);

        db.insert("Question", null, values);

        values.clear();
        values.put("numero",9);
        values.put("pathimage","");
        values.put("enoncer","99999un enoncer test qui est super long pour voir comment ça fait");
        values.put("reponse_A","reponse A9");
        values.put("reponse_B","reponse B9");
        values.put("reponse_C","reponse C9");
        values.put("reponse_D","reponse D9");
        values.put("correct_A",1);
        values.put("correct_B",0);
        values.put("correct_C",0);
        values.put("correct_D",0);
        values.put("idSerie",3);

        db.insert("Question", null, values);

        values.clear();
        values.put("numero",10);
        values.put("pathimage","");
        values.put("enoncer","10101010un enoncer test qui est super long pour voir comment ça fait");
        values.put("reponse_A","reponse A10");
        values.put("reponse_B","reponse B10");
        values.put("reponse_C","reponse C10");
        values.put("reponse_D","reponse D10");
        values.put("correct_A",1);
        values.put("correct_B",0);
        values.put("correct_C",0);
        values.put("correct_D",0);
        values.put("idSerie",3);

        db.insert("Question", null, values);


        db.close();
        */
    }
}
