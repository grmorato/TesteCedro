package grmorato.testecedro.Library;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.JsonReader;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by grmorato on 09/02/2018.
 */

public class LibServiceRest
{

    public static HttpsURLConnection GetConnection(String url) throws IOException {
      URL urlConnection = new URL(url);
      HttpsURLConnection myConnection = (HttpsURLConnection) urlConnection.openConnection();
      return myConnection;
    }

    public static JsonReader GetJsonRespose(String url)
    {
        try {
            HttpsURLConnection conn = GetConnection(url);
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK)
            {
                InputStream responseBody = conn.getInputStream();
                InputStreamReader response = new InputStreamReader(responseBody, "UTF-8");
                JsonReader jsonReader = new JsonReader(response);
                return jsonReader;
            }
            return null;
        } catch (IOException e) {
            Log.d("Error","Erro ao acessar webservice:\n"+e.getMessage());
            return null;
        }
    }

    public static Bitmap GetBitmapUrl(String url)
    {
        try
        {
            HttpsURLConnection conn = LibServiceRest.GetConnection(url);
            conn.setDoInput(true);
            conn.connect();
            InputStream input = conn.getInputStream();
            Bitmap bmp = BitmapFactory.decodeStream(input);
            return bmp;
        } catch (IOException e)
        {
            return null;
        }
    }


}
