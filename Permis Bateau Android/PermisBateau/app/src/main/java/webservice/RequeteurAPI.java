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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
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

    private static String URL_WEBSERVICE_MAJ = "http://cap-horn.osmose-hebergement.com/ws/getmaj";
    private static String URL_WEBSERVICE_IMAGE = "http://cap-horn.osmose-hebergement.com/ws/getImage";
    //private static String URL_WEBSERVICE_IMAGE = "http://cap-horn.olympe.in/ws/getImage";
    private static String URL_WEBSERVICE_COURS= "http://cap-horn.osmose-hebergement.com/ws/getCours";
    private static String CHARSET = "UTF-8";

    private boolean dlImageEnd;

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
     * @param date
     * @return
     */
    public String queryMaj(String date) throws Exception{
        String reponse = "";
        HttpURLConnection connection = null;
        InputStream iStream = null;

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

        while ((line = bReader.readLine()) != null){
            sBuffer.append(line);
        }
        iStream.close();
        connection.disconnect();
        reponse = sBuffer.toString();
        return reponse;
    }

    /**
     * Récupération et stockage interne des images
     * @param idImage
     * @param context
     */
    public void stockImage(String idImage,Context context) throws Exception{

        Bitmap bmp;
        String paramImage = this.buildParamImage(idImage);

        String requete = String.format("%s?%s", URL_WEBSERVICE_IMAGE, paramImage);
        InputStream in = new URL(requete).openStream();
        bmp = BitmapFactory.decodeStream(in);

        if(bmp!=null){
            FileOutputStream fos = context.openFileOutput(idImage + ".jpeg", Context.MODE_PRIVATE);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();
        }



    }

    /**
     * Récupération et stockage externe des cours
     * @param idCours
     * @param context
     */
    public boolean stockCours(String idCours,Context context) throws Exception{

        dlImageEnd = false;
       String paramCours = this.buildParamCours(idCours);

        String requete = String.format("%s?%s", URL_WEBSERVICE_COURS, paramCours);
/*
        File folder = new File(Environment.getExternalStorageDirectory().toString(), "permisbateaucours");
        folder.mkdir();

        File pdfFile = new File(folder, idCours + ".pdf");
/*
        // Create the download request
        DownloadManager.Request r = new DownloadManager.Request( Uri.parse( requete ) );
        r.setDestinationInExternalFilesDir( context, folder.getAbsolutePath(),pdfFile.getName() );
        final DownloadManager dm = (DownloadManager) context.getSystemService( Context.DOWNLOAD_SERVICE );

        BroadcastReceiver onComplete = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                context.unregisterReceiver( this );

                long downloadId = intent.getLongExtra( DownloadManager.EXTRA_DOWNLOAD_ID, -1 );
                Cursor c = dm.query( new DownloadManager.Query().setFilterById( downloadId ) );

                if ( c.moveToFirst() ) {
                    int status = c.getInt( c.getColumnIndex( DownloadManager.COLUMN_STATUS ) );
                    if ( status == DownloadManager.STATUS_SUCCESSFUL ) {
                        //fin du dl
                        dlImageEnd = true;
                    }
                }
                c.close();
            }
        };

        context.registerReceiver( onComplete, new IntentFilter( DownloadManager.ACTION_DOWNLOAD_COMPLETE ) );

        // Enqueue the request
        dm.enqueue( r );

        return dlImageEnd;
        */
/*
        DownloadManager downloadManager = (DownloadManager)context.getSystemService(context.DOWNLOAD_SERVICE);
        Uri Download_Uri = Uri.parse(requete);
        DownloadManager.Request request = new DownloadManager.Request(Download_Uri);

        //Restrict the types of networks over which this download may proceed.
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        //Set whether this download may proceed over a roaming connection.
        request.setAllowedOverRoaming(false);
        //Set the title of this download, to be displayed in notifications (if enabled).
        request.setTitle("Téléchargement cours");
        //Set a description of this download, to be displayed in notifications (if enabled)
        request.setDescription("Téléchargement du cours n° " + idCours);
        //Set the local destination for the downloaded file to a path within the application's external files directory
        request.setDestinationInExternalFilesDir(context, folder.getAbsolutePath(),pdfFile.getName());


        //Enqueue a new download and same the referenceId
        long downloadReference = downloadManager.enqueue(request);
return true;*/
        /*Query myDownloadQuery = new Query();
        //set the query filter to our previously Enqueued download
        myDownloadQuery.setFilterById(downloadReference);

        //Query the download manager about downloads that have been requested.
        Cursor cursor = downloadManager.query(myDownloadQuery);
        if(cursor.moveToFirst()){
            checkStatus(cursor);
        }*/

        URL url = new URL(requete);
        HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.setDoOutput(true);
        urlConnection.connect();

        File folder = new File(Environment.getExternalStorageDirectory().toString(), "permisbateaucours");
        folder.mkdir();

        File pdfFile = new File(folder, idCours + ".pdf");

        FileOutputStream fos = new FileOutputStream(pdfFile);
        InputStream is = urlConnection.getInputStream();

        byte[] buffer = new byte[1024];
        int len1 = 0;
        while ((len1 = is.read(buffer)) != -1) {
            fos.write(buffer, 0, len1);
        }
        fos.close();
        is.close();
return  true;
    }
}
