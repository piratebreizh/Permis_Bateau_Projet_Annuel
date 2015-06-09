package webservice;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

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

    private static String URL_WEBSERVICE_MAJ = "http://cap-horn.olympe.in/ws/getmaj";
    private static String CHARSET = "UTF-8";

    private String buildParamDate(String date) throws UnsupportedEncodingException {
        return String.format("date=%s",
                URLEncoder.encode(date, CHARSET));
    }

    private String buildParamImage(String idImage) throws UnsupportedEncodingException {
        return String.format("image=%s",
                URLEncoder.encode(idImage, CHARSET));
    }

    private String buildParamCours(String idCours) throws UnsupportedEncodingException {
        return String.format("cours=%s",
                URLEncoder.encode(idCours, CHARSET));
    }


    /**
     * Récupération des données à mettre à jour selon la date
     * @param date
     * @return
     */
    public String queryMaj(String date){
        String reponse = "";
        HttpURLConnection connection = null;
        InputStream iStream = null;
        try {
            String requete;
            if(date.length()>0) {
                String paramDate = this.buildParamDate(date);
                requete = String.format("%s&%s", URL_WEBSERVICE_MAJ, paramDate);
            }else {
                requete = URL_WEBSERVICE_MAJ;
            }

            connection = (HttpURLConnection) new URL(requete).openConnection();
            connection.setRequestProperty("Accept-Charset", CHARSET);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.setDoOutput(true);
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
        } catch (Exception e) {
            Log.e("RequeteurAPI", "Erreur lors de la récupération des données", e);
        }

        return reponse;
    }

    /**
     * Récupération et stockage interne des images
     * @param idImage
     * @param context
     */
    public void stockImage(String idImage,Context context){
        try {
            Bitmap bmp;
            String paramImage = this.buildParamImage(idImage);

            String requete = String.format("%s&%s", URL_WEBSERVICE_MAJ, paramImage);
            InputStream in = new URL(requete).openStream();
            bmp = BitmapFactory.decodeStream(in);

            FileOutputStream fos = context.openFileOutput( "images/" + idImage + ".png", Context.MODE_PRIVATE);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();

        } catch (Exception e) {
            Log.e("RequeteurAPI", "Erreur lors de la récupération et stockage des images", e);
        }
    }
}
