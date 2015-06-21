package adapter;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * Created by Ludwig on 21/06/2015.
 */
public class ItemAccueil {
    private Bitmap image;
    private String text;

    public ItemAccueil(Bitmap bmp,String txt){
        image = bmp;
        text=txt;
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

}
