package adapter;

/**
 * Created by Ludwig on 21/06/2015.
 */
public class ItemStats {
    private String titre;
    private String score;
    private String scoreTotal;
    private String date;
    private int idTheme;

    public ItemStats(String _titre,String _score,String _scoreTotal,String _date, int _idTheme){
        titre=_titre;
        score=_score;
        scoreTotal = _scoreTotal;
        date=_date;
        idTheme=_idTheme;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getIdTheme() {
        return idTheme;
    }

    public void setIdTheme(int idTheme) {
        this.idTheme = idTheme;
    }

    public String getScoreTotal() {
        return scoreTotal;
    }

    public void setScoreTotal(String scoreTotal) {
        this.scoreTotal = scoreTotal;
    }
}
