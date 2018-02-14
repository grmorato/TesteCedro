package grmorato.testecedro.Data.Repositories;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import grmorato.testecedro.Data.ColumnDataBase;
import grmorato.testecedro.Data.DataAccess;
import grmorato.testecedro.Library.LibEnuns;
import grmorato.testecedro.Models.UserProfile;

/**
 * Created by grmorato on 09/02/2018.
 */

//Classe responsável por montar a estrutura para realizar as operações no banco de acordo com a tabela de profile
//Foi criado a classe ColumnDataBase para mapear o campo que será usado no sql contendo o nome do mesmo, tipo e valor
public class RepositoryProfile
{
    //Classe reponsável realizar os sql no banco sqlite
    private final DataAccess dataAccess;
    //Váriavel contendo o nome da tabela
    final String tableName = "profile";
    public RepositoryProfile(Context context)
    {
        dataAccess = new DataAccess(context);
    }

    public void InsetProfile(String name, String email, byte[] imagem)
    {
        ArrayList<ColumnDataBase> listColumn = new ArrayList<>();
        listColumn.add(new ColumnDataBase("name", LibEnuns.DataBaseType.String,name));
        listColumn.add(new ColumnDataBase("email", LibEnuns.DataBaseType.String,email));
        listColumn.add(new ColumnDataBase("image", LibEnuns.DataBaseType.ByteArray,imagem));
        long result =dataAccess.Insert(tableName,listColumn);
    }

    public void Delete(String email)
    {
        int result = dataAccess.Delete(tableName,"email = ?",new String[]{email});
    }

    public void Update(String name, String email, String imagem)
    {
        ArrayList<ColumnDataBase> listColumn = new ArrayList<>();
        listColumn.add(new ColumnDataBase("name", LibEnuns.DataBaseType.String,name));
        listColumn.add(new ColumnDataBase("email", LibEnuns.DataBaseType.String,email));
        listColumn.add(new ColumnDataBase("image", LibEnuns.DataBaseType.ByteArray,imagem));
        int result = dataAccess.Update(tableName,listColumn,"email = ?",new String[]{email});
    }

    public UserProfile GetProfile(String email)
    {
        Cursor cursor = dataAccess.GetSelect(tableName,new String[]{"name","email","image"},"email = ?",new String[]{email});
        UserProfile profile = null;
        if(cursor.moveToNext())
        {
            profile = new UserProfile();
            String name = cursor.getString(cursor.getColumnIndex("name"));
            byte[] image = cursor.getBlob(cursor.getColumnIndex("image"));
            profile.setEmail(email);
            profile.setName(name);
            profile.setImage(image);
        }
        return  profile;
    }
}
