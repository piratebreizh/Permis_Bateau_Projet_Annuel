package webservice;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.projet.esgi.permisbateau.AccueilActivity;
import com.projet.esgi.permisbateau.LoadActivity;
import com.projet.esgi.permisbateau.MajActivity;

import java.io.File;

import database.DataBase;

/**
 * Created by Ludwig on 08/06/2015.
 * Récupération des données
 */
public class UpdateTask extends AsyncTask<String,Void,Void> {

    private DataBase db;
    private Context mContext;
    public boolean goodEnding;
    public DataUpdate data;
    private String stateNetwork;

    public UpdateTask(Context context) {
        mContext = context;
        db = new DataBase(context);
        goodEnding = true;
    }

    /**
     * Récupération des données
     * @param params
     * @return
     */
    @Override
    protected Void doInBackground(String... params) {
        stateNetwork=params[1];
        //test la connexion
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(mContext.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if(!isConnected) return null;

        data = null;
        String rsltRequete = "";
        try {
            RequeteurAPI requeteurAPI = new RequeteurAPI();
            if(stateNetwork.equals("online"))
                rsltRequete = requeteurAPI.queryMaj(params[0]);
            if(stateNetwork.equals("local"))
                rsltRequete = requeteurAPI.queryLocalMaj(mContext);
            if(!rsltRequete.equals(""))
                data = ParserJSON.parseNewExamData(rsltRequete);

        } catch (Exception e) {
            db.close();
            goodEnding = false;
            Log.e("UpdateTask", "Erreur lors de la tâche de récupération et parsage des données", e);
        }
        return null;
    }


    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        //si le JSON est vide
        if (data.getIsEmpty().equals("true")){
            Toast.makeText(mContext,"Aucune mise à jour disponible",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(mContext,AccueilActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        }
        //si le JSON n'est pas vide
        if (data.getIsEmpty().equals("false")){

            if(stateNetwork.equals("online")){
                //Proposition de la mise à jour
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setMessage("Une nouvelle mise à jour est disponible, voulez-vous la télécharger ?").setTitle("Téléchargement mise à jour");
                builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //mise à jour des données
                        UpdateOnlineTask uot = new UpdateOnlineTask(mContext);
                        uot.execute(data);
                    }
                });
                builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.setCancelable(false);
                AlertDialog dialog = builder.create();
                dialog.show();
            }

            if(stateNetwork.equals("local")){
                UpdateOnlineTask uot = new UpdateOnlineTask(mContext);
                uot.execute(data);
            }

        }
    }
}
