package adapter;

import android.graphics.Bitmap;

import java.util.ArrayList;

import module.Question;
import module.Reponse;

/**
 * Created by Ludwig on 21/06/2015.
 */
public class ItemCorrection {
    private Bitmap image;
    private String text;
    private ArrayList<Question> listQuestions;
    private ArrayList<Reponse> listReponses;

    public ItemCorrection(String txt,ArrayList<Question> q ,ArrayList<Reponse> r){
        text=txt;
        listQuestions = q;
        listReponses = r;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ArrayList<Question> getListQuestions() {
        return listQuestions;
    }

    public void setListQuestions(ArrayList<Question> listQuestions) {
        this.listQuestions = listQuestions;
    }

    public ArrayList<Reponse> getListReponses() {
        return listReponses;
    }

    public void setListReponses(ArrayList<Reponse> listReponses) {
        this.listReponses = listReponses;
    }

    public Question getQuestionAtIndex(int i){
        return listQuestions.get(i);
    }

    public Reponse getReponseAtIndex(int i){
        return listReponses.get(i);
    }
}
