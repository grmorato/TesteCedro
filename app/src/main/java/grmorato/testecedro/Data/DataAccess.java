package grmorato.testecedro.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import grmorato.testecedro.Library.LibEnuns;

/**
 * Created by grmorato on 09/02/2018.
 */

public class DataAccess
{
    private final MobileDataBaseHelper mobDataHelper;
    private final SQLiteDatabase db;

    public DataAccess(Context context)
    {
        mobDataHelper = new MobileDataBaseHelper(context);
        db = mobDataHelper.getWritableDatabase();
    }

    public long Insert(String tableName, ArrayList<ColumnDataBase> listValues)
    {
        ContentValues values = new ContentValues();
        for ( ColumnDataBase entry : listValues)
        {
            String column = entry.getColumnName();
            if(entry.getType() == LibEnuns.DataBaseType.ByteArray)
                values.put(column,(byte[])entry.getValue());
            else
                values.put(column, (String)entry.getValue());
        }
        long newRowId = db.insert(tableName, null, values);
        return newRowId;
    }

    public Cursor GetSelect(String tableName,String[] columns,String where,String[] whereArgs)
    {
        Cursor cursor = db.query( tableName, columns, where, whereArgs,null, null,null);
        return cursor;
    }

    public int Delete(String tableName,String where,String[] whereArgs)
    {
        int count = db.delete(tableName, where, whereArgs);
        return count;
    }


    public int Update(String tableName,  ArrayList<ColumnDataBase> listValues,String where,String[] whereArgs)
    {
        ContentValues values = new ContentValues();
        for ( ColumnDataBase entry : listValues )
        {
            String column = entry.getColumnName();
            if(entry.getType() == LibEnuns.DataBaseType.ByteArray)
                values.put(column,(byte[])entry.getValue());
            else
                values.put(column, (String)entry.getValue());
        }
        int count = db.update(tableName,values,where,whereArgs);
        return count;
    }

}
