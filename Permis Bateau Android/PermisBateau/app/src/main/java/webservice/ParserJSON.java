package webservice;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ludwig on 08/06/2015.
 */
public class ParserJSON {

    public static DataUpdate parseNewExamData (String json) throws JSONException {
        if (json != null) {
            DataUpdate data = new DataUpdate();

            JSONObject jsonObject = new JSONObject(json);
            //themes
            JSONArray arrayThemes = jsonObject.getJSONArray("themes_nouveaux");
            for (int i = 0; i < arrayThemes.length(); ++i) {
                JSONObject theme = arrayThemes.getJSONObject(i);
                data.addTheme(new ThemeData(theme.getInt("id_theme"), theme.getString("nom"), theme.getInt("numero")));
            }
            //examens
            JSONArray arrayExamens = jsonObject.getJSONArray("examens_nouveaux");
            for (int i = 0; i < arrayExamens.length(); ++i) {
                JSONObject serie = arrayExamens.getJSONObject(i);
                data.addSerie(new SerieData(serie.getInt("id_examen"),serie.getInt("id_theme"),serie.getString("nom"),
                        serie.getInt("numero")));
            }
            //questions
            JSONArray arrayQuestions = jsonObject.getJSONArray("questions_nouvelles");
            for (int i = 0; i < arrayQuestions.length(); ++i) {
                JSONObject quest = arrayQuestions.getJSONObject(i);
                data.addQuestion(new QuestionData(quest.getInt("id_question"),quest.getInt("numero_question"),
                        quest.getString("id_question"),
                        quest.getString("enonce_question"),quest.getString("enonce_A"),quest.getString("enonce_B"),
                        quest.getString("enonce_C"),quest.getString("enonce_D"),
                        quest.getInt("is_correct_A"),quest.getInt("is_correct_B"),quest.getInt("is_correct_C"),
                        quest.getInt("is_correct_D"), quest.getInt("id_examen")));
            }
            //cours
            JSONArray arrayCours = jsonObject.getJSONArray("cours_nouveaux");
            for (int i = 0; i < arrayCours.length(); ++i) {
                JSONObject cours = arrayCours.getJSONObject(i);
                data.addCours(new CoursData(cours.getInt("id_cours"),
                        cours.getString("nom"), cours.getInt("id_theme")));
            }
            //themes_supprimes
            JSONArray arrayThemes_supprimes = jsonObject.getJSONArray("themes_supprimes");
            for (int i = 0; i < arrayThemes_supprimes.length(); ++i) {
                data.deleteTheme(new ThemeData(arrayThemes_supprimes.getInt(i)));
            }
            //examens_supprimes
            JSONArray arrayExamens_supprimes = jsonObject.getJSONArray("examens_supprimes");
            for (int i = 0; i < arrayExamens_supprimes.length(); ++i) {
                data.deleteSerie(new SerieData(arrayExamens_supprimes.getInt(i)));
            }
            //questions_supprimees
            JSONArray arrayQuestions_supprimees = jsonObject.getJSONArray("questions_supprimees");
            for (int i = 0; i < arrayQuestions_supprimees.length(); ++i) {
                data.deleteQuestion(new QuestionData(arrayQuestions_supprimees.getInt(i)));
            }
            //cours_supprimes
            JSONArray arrayCours_supprimees = jsonObject.getJSONArray("cours_supprimes");
            for (int i = 0; i < arrayCours_supprimees.length(); ++i) {
                data.addCours(new CoursData(arrayCours_supprimees.getInt(i)));
            }

            //date de maj
            data.setDateUpdate(jsonObject.getString("date_update"));
            //est vide
            data.setIsEmpty(jsonObject.getString("is_empty"));

            return data;
        }else{
            return null;
        }
    }

}
