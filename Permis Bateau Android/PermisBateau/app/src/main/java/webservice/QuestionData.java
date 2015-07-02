package webservice;

/**
 * Created by Ludwig on 08/06/2015.
 */
public class QuestionData {

    int id;
    int numero;
    String pathimage;
    String enoncer;
    String reponse_A;
    String reponse_B;
    String reponse_C;
    String reponse_D;
    int correct_A;
    int correct_B;
    int correct_C;
    int correct_D;
    int idSerie;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getPathimage() {
        return pathimage;
    }

    public void setPathimage(String pathimage) {
        this.pathimage = pathimage;
    }

    public String getEnoncer() {
        return enoncer;
    }

    public void setEnoncer(String enoncer) {
        this.enoncer = enoncer;
    }

    public String getReponse_A() {
        return reponse_A;
    }

    public void setReponse_A(String reponse_A) {
        this.reponse_A = reponse_A;
    }

    public String getReponse_B() {
        return reponse_B;
    }

    public void setReponse_B(String reponse_B) {
        this.reponse_B = reponse_B;
    }

    public String getReponse_C() {
        return reponse_C;
    }

    public void setReponse_C(String reponse_C) {
        this.reponse_C = reponse_C;
    }

    public String getReponse_D() {
        return reponse_D;
    }

    public void setReponse_D(String reponse_D) {
        this.reponse_D = reponse_D;
    }

    public int getCorrect_A() {
        return correct_A;
    }

    public void setCorrect_A(int correct_A) {
        this.correct_A = correct_A;
    }

    public int getCorrect_B() {
        return correct_B;
    }

    public void setCorrect_B(int correct_B) {
        this.correct_B = correct_B;
    }

    public int getCorrect_C() {
        return correct_C;
    }

    public void setCorrect_C(int correct_C) {
        this.correct_C = correct_C;
    }

    public int getCorrect_D() {
        return correct_D;
    }

    public void setCorrect_D(int correct_D) {
        this.correct_D = correct_D;
    }

    public int getIdSerie() {
        return idSerie;
    }

    public void setIdSerie(int idSerie) {
        this.idSerie = idSerie;
    }

    public QuestionData(int _id, int num,String path, String txt, String rA,String rB,String rC,String rD,
                        int cA, int cB, int cC, int cD, int idS ){

        id=_id;
        numero=num;
        pathimage=path;
        enoncer=txt;
        reponse_A=rA;
        reponse_B=rB;
        reponse_C=rC;
        reponse_D=rD;
        correct_A=cA;
        correct_B=cB;
        correct_C=cC;
        correct_D=cD;
        idSerie=idS;

    }
    public QuestionData(int _id ){
        id=_id;
    }
}
