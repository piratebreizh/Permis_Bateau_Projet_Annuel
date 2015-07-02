package webservice;

/**
 * Created by Ludwig on 08/06/2015.
 */
public class ThemeData {

    int id;
    String name;
    int num;

    public ThemeData(int _id,String _name, int _num){
        id=_id;
        name=_name;
        num = _num;
    }

    public ThemeData(int _id){
        id=_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
