package adapter;

import android.graphics.Bitmap;

/**
 * Created by Ludwig on 21/06/2015.
 */
public class ItemAccueil {
    private Bitmap image;
    private String text;
    private String desc;

    public ItemAccueil(Bitmap bmp,String txt,String d){
        image = bmp;
        text=txt;
        desc=d;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
