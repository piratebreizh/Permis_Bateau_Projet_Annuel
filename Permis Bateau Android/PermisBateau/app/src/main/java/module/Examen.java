package module;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

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
                            list.getString(list.getColumnIndex("correct_A")),
                            list.getString(list.getColumnIndex("correct_B")),
                            list.getString(list.getColumnIndex("correct_C")),
                            list.getString(list.getColumnIndex("correct_D")));
                    listQuestions.add(q);
                }while (list.moveToNext());
            }
        }
        list.close();
        db.close();
    }

    /**
     * Génère un nombre nbQuestions de questions aléatoires sur un thème précis ou non
     */
    public void GenerateRandomQuestions() throws SQLException {
        db.open();
        if(numThem == 0){
            Cursor list = db.getRandomNbQuestions(2);
            if (list != null ) {
                if  (list.moveToFirst()) {
                    do {
                        Question q = new Question(list.getInt(list.getColumnIndex("id")),
                                list.getInt(list.getColumnIndex("numero")),
                                list.getString(list.getColumnIndex("pathimage")),
                                list.getString(list.getColumnIndex("enoncer")),
                                list.getString(list.getColumnIndex("reponse_A")),
                                list.getString(list.getColumnIndex("reponse_B")),
                                list.getString(list.getColumnIndex("reponse_C")),
                                list.getString(list.getColumnIndex("reponse_D")),
                                list.getString(list.getColumnIndex("correct_A")),
                                list.getString(list.getColumnIndex("correct_B")),
                                list.getString(list.getColumnIndex("correct_C")),
                                list.getString(list.getColumnIndex("correct_D")));
                        listQuestions.add(q);
                    }while (list.moveToNext());
                }
            }
            list.close();
        }else
        {
            Cursor list = db.getRandomNbQuestionsByTheme(2,numThem);
            if (list != null ) {
                if (list.moveToFirst()) {
                    do {
                        Question q = new Question(list.getInt(list.getColumnIndex("id")),
                                list.getInt(list.getColumnIndex("numero")),
                                list.getString(list.getColumnIndex("pathimage")),
                                list.getString(list.getColumnIndex("enoncer")),
                                list.getString(list.getColumnIndex("reponse_A")),
                                list.getString(list.getColumnIndex("reponse_B")),
                                list.getString(list.getColumnIndex("reponse_C")),
                                list.getString(list.getColumnIndex("reponse_D")),
                                list.getString(list.getColumnIndex("correct_A")),
                                list.getString(list.getColumnIndex("correct_B")),
                                list.getString(list.getColumnIndex("correct_C")),
                                list.getString(list.getColumnIndex("correct_D")));
                        listQuestions.add(q);
                    } while (list.moveToNext());
                }
            }
        }
        db.close();
    }
}
