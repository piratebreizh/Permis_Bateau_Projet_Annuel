package webservice;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.projet.esgi.permisbateau.AccueilActivity;

import database.DataBase;

/**
 * Created by Ludwig on 21/06/2015.
 * Mise à jour des données dans la base
 */
public class UpdateOnlineTask extends AsyncTask<DataUpdate,Void,Void> {

    private DataBase db;
    private Context mContext;

    public UpdateOnlineTask(Context context) {
        mContext = context;
        db = new DataBase(context);
    }

    @Override
    protected Void doInBackground(DataUpdate... params) {
        try{
            databaseUpdate(params[0],mContext);
            //mise à jour de la date de dernière mise à jour
            SharedPreferences pref = mContext.getSharedPreferences("date", mContext.MODE_PRIVATE);
            pref.edit().putString("DATE_VERSION_LOCALE", params[0].getDateUpdate()).commit();

        }catch (Exception e){
            db.close();
            Log.e("UpdateTask", "Erreur lors du stockage des données", e);
        }

        return null;
    }

    /**
     * Mise à jour de la base de données
     * @param data
     * @param c
     */
    public void databaseUpdate (DataUpdate data, Context c) throws Exception{
        db.open();
        db.createDataBase();

        if(data != null) {

            ContentValues values = new ContentValues();
            //SUPPRESSION//
            //themes
            for (ThemeData t : data.getDelThemes()) {
                db.execSql("DELETE FROM Thematique WHERE idThematique=" + t.getId());
            }
            //series
            for (SerieData s : data.getDelSeries()) {
                db.execSql("DELETE FROM Serie WHERE idSerie=" + s.getId());
            }
            //questions
            for (QuestionData q : data.getDelQuestions()) {
                //bdd questions
                db.execSql("DELETE FROM Question WHERE idQuestion=" + q.getId());

                //suppression image
                delImage(q.getPathimage(), c);
            }
            //cours
            for (CoursData co : data.getDelCours()) {
                db.execSql("DELETE FROM Cours WHERE idCours=" + co.getIdCours());
            }

            //INSERTION//
            //themes
            for (ThemeData t : data.getThemes()) {
                values.put("idThematique", t.getId());
                values.put("nomThematique", t.getName());
                values.put("numeroThematique", t.getNum());
                db.insert("Thematique", null, values);
                values.clear();
            }

            //series
            for (SerieData s : data.getSeries()) {
                values.put("idSerie", s.getId());
                values.put("nomSerie", s.getName());
                values.put("theme", s.getIdTheme());
                values.put("numeroSerie", s.getNum());
                db.insert("Serie", null, values);
                values.clear();
            }

            //questions
            for (QuestionData q : data.getQuestions()) {
                //bdd questions
                values.put("idQuestion", q.getId());
                values.put("numero", q.getNumero());
                values.put("pathimage", q.getPathimage());
                values.put("enoncer", q.getEnoncer());
                values.put("reponse_A", q.getReponse_A());
                values.put("reponse_B", q.getReponse_B());
                values.put("reponse_C", q.getReponse_C());
                values.put("reponse_D", q.getReponse_D());
                values.put("correct_A", q.getCorrect_A());
                values.put("correct_B", q.getCorrect_B());
                values.put("correct_C", q.getCorrect_C());
                values.put("correct_D", q.getCorrect_D());
                values.put("idSerie", q.getIdSerie());
                db.insert("Question", null, values);
                values.clear();

                //stockage image
                RequeteurAPI requeteurAPI = new RequeteurAPI();
                requeteurAPI.stockImage(q.getPathimage(), c);
            }

            //cours
            for (CoursData co : data.getCours()) {
                values.put("idCours", co.getIdCours());
                values.put("nomCours", co.getName());
                values.put("idTheme", co.getIdTheme());
                db.insert("Cours", null, values);
                values.clear();

                //stockage cours
                RequeteurAPI requeteurAPI = new RequeteurAPI();
                requeteurAPI.stockCours(Integer.toString(co.getIdCours()),c);
            }

            db.close();
        }
    }

    /**
     * Suppression d'une image dans le stockage interne de l'applications
     * @param idImage
     */
    public static void delImage(String idImage,Context c){
        if(c.deleteFile(idImage + "png")){
            Log.v("Suppression image", "Suppression réussi");
        }else{
            Log.e("Suppression image","Erreur lors de la suppression");
        }
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Toast.makeText(mContext, "Mise à jour terminée", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(mContext,AccueilActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }
}
