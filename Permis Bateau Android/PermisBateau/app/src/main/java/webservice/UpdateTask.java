package webservice;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import database.DataBase;

/**
 * Created by Ludwig on 08/06/2015.
 */
public class UpdateTask extends AsyncTask<String,Void,Void> {

    private Context mContext;

    public UpdateTask(Context context) {
        mContext = context;
    }

    @Override
    protected Void doInBackground(String... params) {
        DataUpdate data = null;
        String rsltRequete = "";
        try {
            RequeteurAPI requeteurAPI = new RequeteurAPI();
            rsltRequete = requeteurAPI.queryMaj(params[0]);
Log.e("mlf",rsltRequete);
            if(!rsltRequete.equals(""))
                data = ParserJSON.parseNewExamData(rsltRequete);
                databaseUpdate(data,mContext);
        } catch (Exception e) {
            Log.e("UpdateTask", "Erreur lors de la tâche de récupération et parsage des données", e);
        }
        return null;
    }

    /**
     * Mise à jour de la base de données
     * @param data
     * @param c
     */
    public static void databaseUpdate (DataUpdate data, Context c) {
        DataBase db = new DataBase(c);
        try {
            Log.e("data","databaseUpdate");
            db.open();
            db.dropDataBase();
            db.createDataBase();

            ContentValues values = new ContentValues();
            //INSERTION//
            //themes
            for (ThemeData t : data.getThemes()) {
                values.put("idThematique",t.getId());
                values.put("nomThematique",t.getName());
                values.put("numeroThematique",t.getNum());
                db.insert("Thematique", null, values);
                values.clear();
            }

            //series
            for (SerieData s : data.getSeries()) {
                values.put("idSerie",s.getId());
                values.put("nomSerie",s.getName());
                values.put("theme",s.getIdTheme());
                values.put("numeroSerie",s.getNum());
                db.insert("Serie", null, values);
                values.clear();
            }

            //questions
            for (QuestionData q : data.getQuestions()) {
                //bdd questions
                values.put("idQuestion",q.getId());
                values.put("numero",q.getNumero());
                values.put("pathimage", q.getPathimage());
                values.put("enoncer",q.getEnoncer());
                values.put("reponse_A",q.getReponse_A());
                values.put("reponse_B",q.getReponse_B());
                values.put("reponse_C",q.getReponse_C());
                values.put("reponse_D",q.getReponse_D());
                values.put("correct_A",q.getCorrect_A());
                values.put("correct_B",q.getCorrect_B());
                values.put("correct_C",q.getCorrect_C());
                values.put("correct_D",q.getCorrect_D());
                values.put("idSerie",q.getIdSerie());
                db.insert("Question", null, values);
                values.clear();

                //stockage image
                //RequeteurAPI requeteurAPI = new RequeteurAPI();
                //requeteurAPI.stockImage(q.getPathimage(),c);
            }


            //SUPPRESSION//
            //questions
            for (QuestionData q : data.getDelQuestions()) {
                //bdd questions
                db.execSql("DELETE FROM Questions WHERE idQuestion=" + q.getId());

                //suppression image
                //delImage(q.getPathimage(),c);
            }
            //themes
            for (ThemeData t : data.getDelThemes()) {
                db.execSql("DELETE FROM Thematique WHERE idThematique=" + t.getId());
            }
            //series
            for (SerieData s : data.getDelSeries()) {
                db.execSql("DELETE FROM Serie WHERE idSerie=" + s.getId());
            }

            db.close();
        }catch (Exception e){
            Log.e("UpdateTask", "Erreur lors de la copie des données dans la base de données", e);
            db.close();
        }
    }

    /**
     * Suppression d'une image dans le stockage interne de l'applications
     * @param idImage
     */
    public static void delImage(String idImage,Context c){
        if(c.deleteFile("/images/" + idImage)){
            Log.v("Suppression image","Suppression réussi");
        }else{
            Log.e("Suppression image","Erreur lors de la suppression");
        }
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Toast.makeText(mContext,"Fini",Toast.LENGTH_SHORT).show();
    }
}
