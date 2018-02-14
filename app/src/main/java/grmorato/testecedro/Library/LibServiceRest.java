package grmorato.testecedro.Library;

import android.util.JsonReader;
import android.util.Log;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by grmorato on 09/02/2018.
 */

//Classe responsável por conectar no webservice e fazer a requisão e objer o json com os dados
public class LibServiceRest
{

    //Realiza a conexão https com a url e retorna a mesma
    public static HttpsURLConnection GetConnection(String url) throws IOException {
        URL urlConnection = new URL(url);
        HttpsURLConnection myConnection = (HttpsURLConnection) urlConnection.openConnection();
        return myConnection;
    }

    //Faz na url do webservice busca o retorno do webservice rest e cria o jsonReader para fazer o parse
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

    //Método responsável por buscar um arquivo em uma url e retornar o mesmo em byte[]
    public static byte[] GetImageUrl(String url)
    {
        try
        {

            URL urlConnection = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) urlConnection.openConnection();
            conn.setFollowRedirects(true);
            conn.setDoInput(true);
            conn.connect();
            int responseCode = conn.getResponseCode();
            if(responseCode == HttpURLConnection.HTTP_OK) {
                InputStream input = conn.getInputStream();
                byte[] bytes = LibMobile.ConvertStreamToByteArray(input);
                input.close();
                return bytes;

            }
            conn.disconnect();
            return null;
        } catch (IOException e)
        {
            e.printStackTrace();
            Log.d("Error",e.getMessage());
            return  null;
        }
    }


}