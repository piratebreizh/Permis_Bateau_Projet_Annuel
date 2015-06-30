package adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.projet.esgi.myapplication.R;
import com.projet.esgi.permisbateau.CorrectionActivity;
import com.projet.esgi.permisbateau.CorrectionQuestionActivity;
import com.projet.esgi.permisbateau.CoursActivity;
import com.projet.esgi.permisbateau.MajActivity;
import com.projet.esgi.permisbateau.SerieActivity;
import com.projet.esgi.permisbateau.ThematiqueActivity;

import java.util.ArrayList;

import module.Question;
import module.Reponse;

/**
 * Created by Ludwig on 21/06/2015.
 */
public class CorrectionAdapter extends ArrayAdapter<ItemCorrection> {

    public CorrectionAdapter(Context context,ArrayList<ItemCorrection> _items){
        super(context, 0, _items);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final ItemCorrection item = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_correction, parent, false);
        }
        // Lookup view for data ville
        final TextView text = (TextView) convertView.findViewById(R.id.item_correction_text);
        final ImageView img = (ImageView) convertView.findViewById(R.id.item_correction_image);
        // Populate the data into the template view using the data object
        text.setText(item.getText());

        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("question",item.getQuestionAtIndex(position));
                bundle.putParcelable("response",item.getReponseAtIndex(position));
                Intent intent = new Intent(getContext(),CorrectionQuestionActivity.class);
                intent.putExtras(bundle);
                getContext().startActivity(intent);
            }
        });


        Bitmap bmp_yes = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.yes);
        Bitmap bmp_no = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.no);

        if(goodAnswer(item.getQuestionAtIndex(position),item.getReponseAtIndex(position))){
            img.setImageBitmap(bmp_yes);
        }else{
            img.setImageBitmap(bmp_no);
        }


        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text.callOnClick();
            }
        });

        // Return the completed view to render on screen
        return convertView;
    }


    /**
     * Renvoie vrai si la question à bien été répondu, faux sinon
     * @param q
     * @param r
     * @return
     */
    public static boolean goodAnswer(Question q, Reponse r){
        return (q.getCorrect_A()==((r.getRepA()) ? 1 : 0)) && (q.getCorrect_B()==((r.getRepB()) ? 1 : 0))
                && (q.getCorrect_C()==((r.getRepC()) ? 1 : 0)) && (q.getCorrect_D()==((r.getRepD()) ? 1 : 0));
    }
}
