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
    String correct_A;
    String correct_B;
    String correct_C;
    String correct_D;

    public Question(int _id,int num,String path, String txt, String rA,String rB,String rC,String rD,
                    String cA,String cB,String cC,String cD ){

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

    public String getCorrect_A() {
        return correct_A;
    }

    public String getCorrect_B() {
        return correct_B;
    }

    public String getCorrect_C() {
        return correct_C;
    }

    public String getCorrect_D() {
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
        dest.writeString(correct_A);
        dest.writeString(correct_B);
        dest.writeString(correct_C);
        dest.writeString(correct_D);
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
        correct_A = in.readString();
        correct_B = in.readString();
        correct_C = in.readString();
        correct_D = in.readString();
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
