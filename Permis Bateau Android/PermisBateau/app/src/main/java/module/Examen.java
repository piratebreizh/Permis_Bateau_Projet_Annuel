package module;

import android.content.Context;
import database.DataBase;

/**
 * Created by Ludwig on 15/04/2015.
 */
public class Examen {
    private int nbQuestions;
    private DataBase db;


    public Examen(int idExam, Context context, int numThem){
        db = new DataBase(context);
    }

    public void GenerateRandomQuestions(int numThem){
        if(numThem == 0){

        }else
        {

        }
    }
}
