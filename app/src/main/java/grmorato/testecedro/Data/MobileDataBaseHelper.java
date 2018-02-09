package grmorato.testecedro.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import grmorato.testecedro.R;

/**
 * Created by grmorato on 15/01/2016.
 */
public class MobileDataBaseHelper<T> extends SQLiteOpenHelper {

    private static final String dataBaseName = "testecedro.db3";
    private static final String dataBasePath = "/data/data/grmorato.testecedro/databases/";
    private final Context context;

    public MobileDataBaseHelper(Context context) {
        super(context, dataBaseName, null, 1);
        this.context = context;
        try {
            CriarDbResource();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //Metodo pode ser utilizado para criar as tabelas no banco diretamete
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }

    //Cria o banco com o arquivo do banco adicionado no resource
    private void CriarDbResource()
    {
        boolean dbexist = Checkdatabase();
        if (!dbexist) {
            try {
                File dir = new File(dataBasePath);
                dir.mkdirs();
                InputStream myinput = context.getResources().openRawResource(R.raw.testecedro);
                String outfilename = dataBasePath + dataBaseName;
                OutputStream myoutput = new FileOutputStream(outfilename);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = myinput.read(buffer)) > 0) {
                    myoutput.write(buffer, 0, length);
                }
                myoutput.flush();
                myoutput.close();
                myinput.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
       }
    }

    //Checa se o banco já existe
    private boolean Checkdatabase() {
        //Cria um objeto  do SQLiteDatabase
        SQLiteDatabase dbToBeVerified = null;

        try {
            // get database path
            String dbPath = dataBasePath + dataBaseName;
            //abre o arquivo do banco e carrega o objeto
            dbToBeVerified = SQLiteDatabase.openDatabase(dbPath, null,
                    SQLiteDatabase.OPEN_READONLY);

        } catch (SQLiteException e) {
            e.printStackTrace();
        }

        //para caso ele existir fecha o banco
        if (dbToBeVerified != null) {
            dbToBeVerified.close();
        }
        //verifica se o banco carregou caso sim ele existe
        return dbToBeVerified != null ? true : false;
    }
}
