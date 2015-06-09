package webservice;

import java.util.ArrayList;

import module.Question;

/**
 * Created by Ludwig on 08/06/2015.
 */
public class DataUpdate {
    ArrayList<QuestionData> questions;
    ArrayList<ThemeData> themes;
    ArrayList<SerieData> series;

    ArrayList<QuestionData> delQuestions;
    ArrayList<ThemeData> delThemes;
    ArrayList<SerieData> delSeries;

    public DataUpdate(){
        questions = new ArrayList<QuestionData>();
        themes = new ArrayList<ThemeData>();
        series = new ArrayList<SerieData>();

        delQuestions = new ArrayList<QuestionData>();
        delThemes = new ArrayList<ThemeData>();
        delSeries = new ArrayList<SerieData>();
    }

    public void addQuestion(QuestionData q){
        questions.add(q);
    }

    public void addTheme(ThemeData t){
        themes.add(t);
    }

    public void addSerie(SerieData s){
        series.add(s);
    }

    public void deleteQuestion(QuestionData q){
        delQuestions.add(q);
    }

    public void deleteTheme(ThemeData t){
        delThemes.add(t);
    }

    public void deleteSerie(SerieData s){
        delSeries.add(s);
    }

    public ArrayList<QuestionData> getQuestions(){
        return questions;
    }

    public ArrayList<ThemeData> getThemes() {
        return themes;
    }

    public ArrayList<SerieData> getSeries() {
        return series;
    }

    public ArrayList<QuestionData> getDelQuestions() {
        return delQuestions;
    }

    public ArrayList<ThemeData> getDelThemes() {
        return delThemes;
    }

    public ArrayList<SerieData> getDelSeries() {
        return delSeries;
    }
}
