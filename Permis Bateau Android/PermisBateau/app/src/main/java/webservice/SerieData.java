package webservice;

/**
 * Created by Ludwig on 08/06/2015.
 */
public class SerieData {

    int id;
    int idTheme;
    String name;
    int num;

    public SerieData(int id, int idTheme, String name, int num) {
        this.id = id;
        this.idTheme = idTheme;
        this.name = name;
        this.num = num;
    }

    public SerieData(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdTheme() {
        return idTheme;
    }

    public void setIdTheme(int idTheme) {
        this.idTheme = idTheme;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
