package module;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Ludwig on 20/04/2015.
 */
public class Question implements Parcelable {

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

    public Question(int _id,int num,String path, String txt, String rA,String rB,String rC,String rD,
                    int cA,int cB,int cC,int cD ){

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

    }

    public Question(Parcel in){
        super();
        readFromParcel(in);
    }

    public String getEnoncer() {
        return enoncer;
    }

    public String getReponse_A() {
        return reponse_A;
    }

    public String getReponse_B() {
        return reponse_B;
    }

    public String getReponse_C() {
        return reponse_C;
    }

    public String getReponse_D() {
        return reponse_D;
    }

    public int getCorrect_A() {
        return correct_A;
    }

    public int getCorrect_B() {
        return correct_B;
    }

    public int getCorrect_C() {
        return correct_C;
    }

    public int getCorrect_D() {
        return correct_D;
    }

    public String getPathimage() {
        return pathimage;
    }

    public int getNumero() {
        return numero;
    }

    public int getId() {
        return id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(numero);
        dest.writeString(pathimage);
        dest.writeString(enoncer);
        dest.writeString(reponse_A);
        dest.writeString(reponse_B);
        dest.writeString(reponse_C);
        dest.writeString(reponse_D);
        dest.writeInt(correct_A);
        dest.writeInt(correct_B);
        dest.writeInt(correct_C);
        dest.writeInt(correct_D);
    }

    public void readFromParcel(Parcel in) {
        id = in.readInt();
        numero = in.readInt();
        pathimage = in.readString();
        enoncer = in.readString();
        reponse_A = in.readString();
        reponse_B = in.readString();
        reponse_C = in.readString();
        reponse_D = in.readString();
        correct_A = in.readInt();
        correct_B = in.readInt();
        correct_C = in.readInt();
        correct_D = in.readInt();
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
    @Override
    public Question createFromParcel(Parcel source) {
        return new Question(source);
    }

    @Override
    public Question[] newArray(int size) {
        return new Question[size];
    }
    };
}
