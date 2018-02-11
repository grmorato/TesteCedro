package grmorato.testecedro.Data;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import grmorato.testecedro.Library.LibEnuns;
import grmorato.testecedro.Library.LibFacebookService;
import grmorato.testecedro.Models.Pais;

/**
 * Created by grmorato on 11/02/2018.
 */

public class RepositoryFavorites
{
    private final DataAccess dataAccess;
    final String tableName = "histcountry";
    public RepositoryFavorites(Context context)
    {
        dataAccess = new DataAccess(context);
    }

    public void InsetFavorite(Pais pais)
    {
        if(LibFacebookService.userProfile != null)
        {
            ArrayList<ColumnDataBase> listColumn = new ArrayList<>();
            listColumn.add(new ColumnDataBase("name", LibEnuns.DataBaseType.String, pais.getName()));
            listColumn.add(new ColumnDataBase("capital", LibEnuns.DataBaseType.String, pais.getCapital()));
            listColumn.add(new ColumnDataBase("emailprofile", LibEnuns.DataBaseType.String, LibFacebookService.userProfile.getEmail()));
            listColumn.add(new ColumnDataBase("flag", LibEnuns.DataBaseType.String, pais.getFlag()));
            listColumn.add(new ColumnDataBase("population", LibEnuns.DataBaseType.String, pais.getPopulation()));
            listColumn.add(new ColumnDataBase("area", LibEnuns.DataBaseType.String, pais.getArea()));
            listColumn.add(new ColumnDataBase("datetravel", LibEnuns.DataBaseType.String, pais.getDateVisit()));
            listColumn.add(new ColumnDataBase("code", LibEnuns.DataBaseType.String, pais.getAlpha2Code()));
            long result = dataAccess.Insert(tableName, listColumn);
        }
    }

    public void Delete(String codePais)
    {
        if(LibFacebookService.userProfile != null)
        {
            String email = LibFacebookService.userProfile.getEmail();
            int result = dataAccess.Delete(tableName, "emailprofile = ? and code = ?", new String[]{email, codePais});
        }
    }

    public void Update(Pais pais)
    {
        if(LibFacebookService.userProfile != null)
        {
            String email = LibFacebookService.userProfile.getEmail();
            ArrayList<ColumnDataBase> listColumn = new ArrayList<>();
            listColumn.add(new ColumnDataBase("name", LibEnuns.DataBaseType.String, pais.getName()));
            listColumn.add(new ColumnDataBase("flag", LibEnuns.DataBaseType.String, pais.getFlag()));
            listColumn.add(new ColumnDataBase("population", LibEnuns.DataBaseType.String, pais.getPopulation()));
            listColumn.add(new ColumnDataBase("area", LibEnuns.DataBaseType.String, pais.getArea()));
            listColumn.add(new ColumnDataBase("datetravel", LibEnuns.DataBaseType.String, pais.getDateVisit()));
            listColumn.add(new ColumnDataBase("capital", LibEnuns.DataBaseType.String, pais.getCapital()));
            int result = dataAccess.Update(tableName, listColumn, "emailprofile = ? and code = ?", new String[]{email, pais.getAlpha2Code()});
        }
    }

    public Pais GetPais(String code)
    {
        Pais pais = null;
        if(LibFacebookService.userProfile != null)
        {
            String email = LibFacebookService.userProfile.getEmail();
            Cursor cursor = dataAccess.GetSelect(tableName, new String[]{"name", "flag", "population", "area","capital", "datetravel","code"}, "emailprofile = ? and code = ?", new String[]{email,code});

            if (cursor.moveToNext())
            {
                pais = GetPaisBase(cursor);
            }
        }
        return  pais;
    }


    public ArrayList<Pais> GetListPais()
    {
        ArrayList<Pais> listPais = new ArrayList<>();
        if(LibFacebookService.userProfile != null)
        {
            String email = LibFacebookService.userProfile.getEmail();
            Cursor cursor = dataAccess.GetSelect(tableName, new String[]{"name", "flag", "population", "area","capital", "datetravel","code"}, "emailprofile = ? ", new String[]{email});

            while (cursor.moveToNext())
            {
                Pais pais = GetPaisBase(cursor);
                listPais.add(pais);
            }
        }
        return  listPais;
    }

    private Pais GetPaisBase(Cursor cursor)
    {
        Pais pais = new Pais();
        String name = cursor.getString(cursor.getColumnIndex("name"));
        String image = cursor.getString(cursor.getColumnIndex("flag"));
        String population = cursor.getString(cursor.getColumnIndex("population"));
        String area = cursor.getString(cursor.getColumnIndex("area"));
        String date = cursor.getString(cursor.getColumnIndex("datetravel"));
        String capital = cursor.getString(cursor.getColumnIndex("capital"));
        String code = cursor.getString(cursor.getColumnIndex("code"));

        pais.setName(name);
        pais.setFlag(image);
        pais.setPopulation(population);
        pais.setArea(area);
        pais.setAlpha2Code(code);
        pais.setDateVisit(date);
        pais.setCapital(capital);

        return pais;
    }
}
