package webservice;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.app.DownloadManager;
import android.app.DownloadManager.Query;
import android.app.DownloadManager.Request;
import android.os.Environment;

import com.projet.esgi.myapplication.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Connexion au web service
 * Created by Ludwig on 08/06/2015.
 */
public class RequeteurAPI {

    private static String URL_BASE = "http://cap-horn.osmose-hebergement.com";
    //    private static String URL_BASE = "http://cap-horn.olympe.in";
    private static String URL_WEBSERVICE_MAJ = URL_BASE + "/ws/getmaj";
    private static String URL_WEBSERVICE_IMAGE = URL_BASE + "/ws/getImage";
    private static String URL_WEBSERVICE_COURS = URL_BASE + "/ws/getCours";
    private static String CHARSET = "UTF-8";

    private long downloadID;
    private String downloadCompleteIntentName = DownloadManager.ACTION_DOWNLOAD_COMPLETE;
    private IntentFilter downloadCompleteIntentFilter = new IntentFilter(downloadCompleteIntentName);
    private BroadcastReceiver downloadCompleteReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0L);
            //On ignore les autres DL
            if (id != downloadID) {
                return;
            }
            DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            DownloadManager.Query query = new DownloadManager.Query();
            query.setFilterById(id);
            Cursor cursor = downloadManager.query(query);

            // Si vide
            if (!cursor.moveToFirst()) {
                return;
            }
            int statusIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
            //Si pas encore fini
            if (DownloadManager.STATUS_SUCCESSFUL != cursor.getInt(statusIndex)) {
                return;
            }

            int uriIndex = cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI);
            String downloadedPackageUriString = cursor.getString(uriIndex);

            //Le fichier dans /.../Android/data/....
            File fileOriginal = new File(downloadedPackageUriString.replaceFirst("file://", ""));

            //On crée le dossier de destination si besoin
            File folder = new File(Environment.getExternalStorageDirectory().toString(), "permisbateaucours");
            if (!folder.exists())
                folder.mkdir();

            //On y déplace le fichier téléchargé
            fileOriginal.renameTo(new File(folder, fileOriginal.getName()));
        }
    };

    private String buildParamDate(String date) throws UnsupportedEncodingException {
        return String.format("date=%s",
                URLEncoder.encode(date, CHARSET));
    }

    private String buildParamImage(String idImage) throws UnsupportedEncodingException {
        return String.format("id=%s",
                URLEncoder.encode(idImage, CHARSET));
    }

    private String buildParamCours(String idCours) throws UnsupportedEncodingException {
        return String.format("id=%s",
                URLEncoder.encode(idCours, CHARSET));
    }


    /**
     * Récupération des données à mettre à jour selon la date
     *
     * @param date
     * @return
     */
    public String queryMaj(String date) throws Exception {
        String reponse = "";
        HttpURLConnection connection = null;
        InputStream iStream = null;

        if (date=="") return "";

        String requete;
        String paramDate = this.buildParamDate(date);
        requete = String.format("%s?%s", URL_WEBSERVICE_MAJ, paramDate);

        connection = (HttpURLConnection) new URL(requete).openConnection();
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
        connection.setRequestProperty("Accept-Charset", CHARSET);
        connection.setRequestMethod("GET");
        connection.setDoInput(true);
        connection.connect();


        iStream = connection.getInputStream();
        BufferedReader bReader = new BufferedReader(new InputStreamReader(iStream));

        String line;
        StringBuffer sBuffer = new StringBuffer();

        while ((line = bReader.readLine()) != null) {
            sBuffer.append(line);
        }
        iStream.close();
        connection.disconnect();
        reponse = sBuffer.toString();
        return reponse;
    }

    /**
     * Récupère les données à partir du fichier d'initialisation local
     */
    public String queryLocalMaj(Context c){
        String json = null;
        try {

            InputStream is = c.getResources().openRawResource(
                    c.getResources().getIdentifier("raw/json_init",
                            "raw", c.getPackageName()));

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    /**
     * Récupération et stockage interne des images
     *
     * @param idImage
     * @param context
     */
    public void stockImage(String idImage, Context context) throws Exception {
        Bitmap bmp;
        String paramImage = this.buildParamImage(idImage);

        String requete = String.format("%s?%s", URL_WEBSERVICE_IMAGE, paramImage);
        InputStream in = new URL(requete).openStream();
        bmp = BitmapFactory.decodeStream(in);

        if (bmp != null) {
            FileOutputStream fos = context.openFileOutput(idImage + ".jpeg", Context.MODE_PRIVATE);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();
        }
    }

    /**
     * Récupération et stockage interne des images
     *
     * @param idCours
     * @param context
     */
    public void stockCours(String idCours, Context context) throws Exception {
        context.registerReceiver(downloadCompleteReceiver, downloadCompleteIntentFilter);

        String paramCours = this.buildParamCours(idCours);
        String requete = String.format("%s?%s", URL_WEBSERVICE_COURS, paramCours);

        // Create the download request
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(requete));
        request.setTitle("Téléchargement des cours");
        request.setDescription("Cours n°" + idCours);
        request.setDestinationInExternalFilesDir(context, null, idCours + ".pdf");

        // enqueue this request
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        downloadID = downloadManager.enqueue(request);
    }
}
