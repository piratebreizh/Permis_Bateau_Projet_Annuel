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
    private int nbQuestions;
    private int numThem = 0;
    private DataBase db;
    private ArrayList<Question> listQuestions;

    public Examen(Context context,int nb){

        db = new DataBase(context);
        numThem=0;
        nbQuestions=nb;
        listQuestions = new ArrayList<Question>();
    }

    public Examen(Context context, int _numThem,int nb){

        db = new DataBase(context);
        numThem=_numThem;
        nbQuestions=nb;
        listQuestions = new ArrayList<Question>();
    }

    public ArrayList<Question> getlistQuestions(){
        return listQuestions;
    }

    /**
     * Génère un nombre nbQuestions de questions aléatoire sur un thème précis ou non
     */
    public void GenerateRandomQuestions() throws SQLException {
        db.open();
        if(numThem == 0){
            Cursor list = db.getRandomNbQuestions(nbQuestions);
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
            Cursor list = db.getRandomNbQuestionsByTheme(nbQuestions,numThem);
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
