package grmorato.testecedro.Controllers;

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

public class CtrlCountry
{

    public ArrayList<Pais> GetListRestWebService() {
        try
        {
            ArrayList<Pais> listPais = new ArrayList<>();
            String url = "https://restcountries.eu/rest/v2/all?fields=name;capital;alpha2Code;flag;population;area";
            JsonReader jsonReader = LibServiceRest.GetJsonRespose(url);
            jsonReader.beginArray();

            while (jsonReader.hasNext())
            {
                listPais.add(GetPais(jsonReader));
            }
            jsonReader.endArray();
            return listPais;
        } catch (IOException e) {
            return null;
        }
    }

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

    public static void StartDetail(Context context,Pais pais)
    {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra("Pais", pais);
        context.startActivity(intent);
    }


}
