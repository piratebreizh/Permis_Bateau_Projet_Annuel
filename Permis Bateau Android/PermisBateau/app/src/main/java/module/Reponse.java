package module;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Ludwig on 26/04/2015.
 */
public class Reponse implements Parcelable{
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

    public Reponse(Parcel in){
        super();
        readFromParcel(in);
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte)(repA ? 1 : 0));
        dest.writeByte((byte)(repB ? 1 : 0));
        dest.writeByte((byte)(repC ? 1 : 0));
        dest.writeByte((byte)(repD ? 1 : 0));
    }

    public void readFromParcel(Parcel in) {
        repA= in.readByte() != 0;
        repB= in.readByte() != 0;
        repC= in.readByte() != 0;
        repD= in.readByte() != 0;
    }

    public static final Creator<Reponse> CREATOR = new Creator<Reponse>() {
        @Override
        public Reponse createFromParcel(Parcel source) {
            return new Reponse(source);
        }

        @Override
        public Reponse[] newArray(int size) {
            return new Reponse[size];
        }
    };
}
