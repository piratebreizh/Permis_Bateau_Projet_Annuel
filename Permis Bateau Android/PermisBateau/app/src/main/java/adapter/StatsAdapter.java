package adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.projet.esgi.myapplication.R;

import java.util.ArrayList;

/**
 * Created by Ludwig on 21/06/2015.
 */
public class StatsAdapter extends ArrayAdapter<ItemStats> {

    public StatsAdapter(Context context, ArrayList<ItemStats> _items){
        super(context, 0, _items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        ItemStats item = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_stats, parent, false);
        }
        // Lookup view for data ville
        final TextView titre = (TextView) convertView.findViewById(R.id.item_stats_titre);
        final TextView score = (TextView) convertView.findViewById(R.id.item_stats_score);
        final TextView date = (TextView) convertView.findViewById(R.id.item_stats_date);
        final ImageView img = (ImageView) convertView.findViewById(R.id.item_stats_image);
        // Populate the data into the template view using the data object

        titre.setText(item.getTitre());
        score.setText(item.getScore() + "/" + item.getScoreTotal());
        date.setText(item.getDate());

        //image en fonction du score et si c'est un examen blanc ou non (idtheme=0)
        Bitmap bmp_yes = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.correction_valide);
        Bitmap bmp_no = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.correction_cancel);
        Bitmap bmp_book = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.livre);

        if(item.getIdTheme()==0){
            if((Integer.parseInt(item.getScoreTotal()) - (Integer.parseInt(item.getScore())) < 5)){
                img.setImageBitmap(bmp_yes);
            }else{
                img.setImageBitmap(bmp_no);
            }
        }else{
            img.setImageBitmap(bmp_book);
        }

        // Return the completed view to render on screen
        return convertView;
    }
}
