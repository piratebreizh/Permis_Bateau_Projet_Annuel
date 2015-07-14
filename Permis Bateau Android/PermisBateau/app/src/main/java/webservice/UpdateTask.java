package webservice;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.projet.esgi.permisbateau.AccueilActivity;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

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

        //si erreur dans la récupération des données
        if (data==null){
            Toast.makeText(mContext,"Erreur lors de la récupération de la mise à jour",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(mContext,AccueilActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        }else{
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
                            UpdateOnlineTask uot = new UpdateOnlineTask(mContext,stateNetwork);
                            uot.execute(data);
                        }
                    });
                    builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(mContext,AccueilActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            mContext.startActivity(intent);
                        }
                    });
                    builder.setCancelable(false);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }

                if(stateNetwork.equals("local")){
                    loadLocalDatas();
                    UpdateOnlineTask uot = new UpdateOnlineTask(mContext,stateNetwork);
                    uot.execute(data);
                }

            }
        }
    }

    /**
     * Charge les données locales
     */
    private void loadLocalDatas(){
        Bitmap bmp;
        try  {
            ZipInputStream zin = new ZipInputStream(mContext.getResources().openRawResource(
                    mContext.getResources().getIdentifier("localimages", "raw", mContext.getPackageName())));
            ZipEntry ze = null;
            while ((ze = zin.getNextEntry()) != null) {
                //copie des images
                if(ze.getName().split("\\.")[1].equals("png")) {
                    FileOutputStream fout = mContext.openFileOutput(ze.getName(), Context.MODE_PRIVATE);
                    bmp = BitmapFactory.decodeStream(zin);
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, fout);

                    zin.closeEntry();
                    fout.close();
                }
                //copie des cours
                if(ze.getName().split("\\.")[1].equals("pdf")) {
                    //On crée le dossier de destination si besoin
                    File folder = new File(Environment.getExternalStorageDirectory().toString(), "permisbateaucours");
                    if (!folder.exists())
                        folder.mkdir();
                    int size;
                    byte[] buffer = new byte[2048];

                    FileOutputStream fos = new FileOutputStream(Environment.getExternalStorageDirectory() + "/permisbateaucours/" + ze.getName());
                    BufferedOutputStream bos = new BufferedOutputStream(fos, buffer.length);

                    while ((size = zin.read(buffer, 0, buffer.length)) != -1) {
                        bos.write(buffer, 0, size);
                    }
                    bos.flush();
                    bos.close();

                    zin.closeEntry();
                    fos.close();
                }
            }
            zin.close();
        } catch(Exception e) {
            Log.e("Decompress", "unzip", e);
        }
    }
}

