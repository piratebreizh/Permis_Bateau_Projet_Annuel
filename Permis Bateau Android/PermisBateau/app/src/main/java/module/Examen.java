package module;

import android.content.Context;
import android.database.Cursor;

import java.sql.SQLException;
import java.util.ArrayList;

import database.DataBase;

/**
 * Created by Ludwig on 15/04/2015.
 */
public class Examen {

    private int numThem = 0;
    private DataBase db;
    private ArrayList<Question> listQuestions;

    public Examen(Context context, int _numThem){

        db = new DataBase(context);
        numThem=_numThem;
        listQuestions = new ArrayList<Question>();
    }

    public Examen(Context context){

        db = new DataBase(context);
        listQuestions = new ArrayList<Question>();
    }

    public ArrayList<Question> getlistQuestions(){
        return listQuestions;
    }

    /**
     * Retourne les questions d'une serie
     * @param idSerie
     * @throws SQLException
     */
    public void getQuestionsFromSerie(int idSerie) throws SQLException {
        db.open();
        Cursor list = db.getQuestionsFromSeries(idSerie);
        if (list != null ) {
            if  (list.moveToFirst()) {
                do {
                    Question q = new Question(list.getInt(list.getColumnIndex("idQuestion")),
                            list.getInt(list.getColumnIndex("numero")),
                            list.getString(list.getColumnIndex("pathimage")),
                            list.getString(list.getColumnIndex("enoncer")),
                            list.getString(list.getColumnIndex("reponse_A")),
                            list.getString(list.getColumnIndex("reponse_B")),
                            list.getString(list.getColumnIndex("reponse_C")),
                            list.getString(list.getColumnIndex("reponse_D")),
                            list.getInt(list.getColumnIndex("correct_A")),
                            list.getInt(list.getColumnIndex("correct_B")),
                            list.getInt(list.getColumnIndex("correct_C")),
                            list.getInt(list.getColumnIndex("correct_D")));
                    listQuestions.add(q);
                }while (list.moveToNext());
            }
        }
        list.close();
        db.close();
    }
}
