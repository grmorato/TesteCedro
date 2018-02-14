package grmorato.testecedro.Controllers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.JsonReader;
import java.io.IOException;
import java.util.ArrayList;
import grmorato.testecedro.Activities.DetailActivity;
import grmorato.testecedro.Models.Pais;
import grmorato.testecedro.Library.LibServiceRest;

/**
 * Created by grmorato on 09/02/2018.
 */
//Classe de controle responsável por fazer a regra de negócio da tab de países
public class CtrlCountry
{
    //Metodo responsável por buscar o json do webservice com as informações, realizar o parse para o objeto país
    // e retornar um arraylist das informações
    public ArrayList<Pais> GetListRestWebService() {
        try
        {
            ArrayList<Pais> listPais = new ArrayList<>();
            String url = "https://restcountries.eu/rest/v2/all?fields=name;capital;alpha2Code;flag;population;area";
            JsonReader jsonReader = LibServiceRest.GetJsonRespose(url);
            jsonReader.beginArray();

            while (jsonReader.hasNext()) {
                listPais.add(GetPais(jsonReader));
            }
            jsonReader.endArray();

            return listPais;
        } catch (IOException e) {
            return null;
        }
    }

    //Método para fazer o parse do json para o objeto país
    private Pais GetPais(JsonReader reader) throws IOException
    {
        Pais pais = new Pais();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if(name.equals("flag"))
            {
                String urlFlag =reader.nextString();
                pais.setFlag(urlFlag);
            }
            else if(name.equals("name"))
                pais.setName(reader.nextString());
            else if(name.equals("alpha2Code"))
                pais.setAlpha2Code(reader.nextString());
            else if(name.equals("population"))
                pais.setPopulation(reader.nextString());
            else if(name.equals("area"))
                pais.setArea(reader.nextString());
            else if(name.equals("capital"))
                pais.setCapital(reader.nextString());
            else
                reader.skipValue();
        }
        reader.endObject();
        return pais;
    }

    //Método para chamar a tela de detalhe passando o objeto país como parâmetro
    //A tela é chamada com startActivityForResult para objeter o resultado da mesma na tela pai
    //Foi feito dessa forma para ao fechar a tela ter como saber se foi realizado um save no banco caso sim recarrega o gridview
    public static void StartDetail(Context context,Pais pais)
    {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra("Pais", pais);
        ((Activity) context).startActivityForResult(intent,111);
    }


}
