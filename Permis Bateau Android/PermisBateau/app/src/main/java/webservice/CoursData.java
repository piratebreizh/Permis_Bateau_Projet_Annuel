package webservice;

/**
 * Created by Ludwig on 16/06/2015.
 */
public class CoursData {
    private int idCours;
    private String name;
    private int idTheme;

    public CoursData(int idCours, String name, int idTheme) {
        this.idCours = idCours;
        this.name = name;
        this.idTheme = idTheme;
    }

    public CoursData(int idCours) {
        this.idCours = idCours;
    }

    public int getIdCours() {
        return idCours;
    }

    public void setIdCours(int idCours) {
        this.idCours = idCours;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIdTheme() {
        return idTheme;
    }

    public void setIdTheme(int idTheme) {
        this.idTheme = idTheme;
    }
}
