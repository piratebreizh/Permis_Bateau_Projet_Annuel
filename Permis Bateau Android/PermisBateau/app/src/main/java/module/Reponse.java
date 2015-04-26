package module;

/**
 * Created by Ludwig on 26/04/2015.
 */
public class Reponse {
    private boolean repA;
    private boolean repB;
    private boolean repC;
    private boolean repD;

    public Reponse(boolean _repA, boolean _repB, boolean _repC, boolean _repD){
        repA= _repA;
        repB= _repB;
        repC= _repC;
        repD= _repD;
    }

    public boolean getRepA() {
        return repA;
    }

    public boolean getRepB() {
        return repB;
    }

    public boolean getRepC() {
        return repC;
    }

    public boolean getRepD() {
        return repD;
    }
}
