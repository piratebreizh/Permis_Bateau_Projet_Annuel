package adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import com.projet.esgi.myapplication.R;
import com.projet.esgi.permisbateau.ContactActivity;
import com.projet.esgi.permisbateau.CoursActivity;
import com.projet.esgi.permisbateau.MajActivity;
import com.projet.esgi.permisbateau.SerieActivity;
import com.projet.esgi.permisbateau.StatistiquesActivity;
import com.projet.esgi.permisbateau.ThematiqueActivity;

/**
 * Created by Ludwig on 21/06/2015.
 */
public class AccueilAdapter extends ArrayAdapter<ItemAccueil> {

    public AccueilAdapter(Context context,ArrayList<ItemAccueil> _items){
        super(context, 0, _items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        ItemAccueil item = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_accueil, parent, false);
        }
        // Lookup view for data ville
        final TextView text = (TextView) convertView.findViewById(R.id.item_accueil_text);
        final TextView desc = (TextView) convertView.findViewById(R.id.item_accueil_desc);
        final ImageView img = (ImageView) convertView.findViewById(R.id.item_accueil_image);
        // Populate the data into the template view using the data object
        text.setText(item.getText());
        desc.setText(item.getDesc());

        switch (position){
            case 0:
            text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(),ThematiqueActivity.class);
                    getContext().startActivity(intent);
                }
            });
            img.setImageBitmap(item.getImage());
            break;
            case 1:
                text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), SerieActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("idThematique",0);
                        intent.putExtras(bundle);
                        getContext().startActivity(intent);
                    }
                });
                img.setImageBitmap(item.getImage());
                break;
            case 2:
                text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(),ContactActivity.class);
                        getContext().startActivity(intent);
                    }
                });
                img.setImageBitmap(item.getImage());
                break;
            case 3:
                text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(),StatistiquesActivity.class);
                        getContext().startActivity(intent);
                    }
                });
                img.setImageBitmap(item.getImage());
                break;
            case 4:
                text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    Intent intent = new Intent(getContext(),CoursActivity.class);
                    getContext().startActivity(intent);
                    }
                });
                img.setImageBitmap(item.getImage());
                break;
            case 5:
                text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(),MajActivity.class);
                        getContext().startActivity(intent);
                    }
                });
                img.setImageBitmap(item.getImage());
                break;

        }

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text.callOnClick();
            }
        });
        desc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text.callOnClick();
            }
        });

        // Return the completed view to render on screen
        return convertView;
    }
}
