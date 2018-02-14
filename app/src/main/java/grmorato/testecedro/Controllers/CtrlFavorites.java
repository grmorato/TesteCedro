package grmorato.testecedro.Controllers;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

import grmorato.testecedro.Data.Repositories.RepositoryFavorites;
import grmorato.testecedro.Models.Pais;

/**
 * Created by grmorato on 11/02/2018.
 */

//Classe de controle responsável por fazer a regra de negócio da tab de países já visitados
public class CtrlFavorites {
    private final Context context;
    private final RepositoryFavorites repository;

    public CtrlFavorites(Context context) {
        this.context = context;
        this.repository = new RepositoryFavorites(context);
    }


    //Chama os métodos do repositório para fazer o insert ou update do país no banco de dados
    public void SalvarFavorite(Pais pais) {
        try {
            if (pais != null) {
                Pais paisAux = repository.GetPais(pais.getAlpha2Code());
                if (paisAux == null) {
                    repository.InsetFavorite(pais);
                }
                else
                {
                    repository.Update(pais);
                }
            }
        } catch (Exception ex) {
            Log.d("Error", ex.getMessage());
        }
    }

    //Chama o método do repositório para fazer o delete de um arraylist de vários paises no banco de dados
    public void DeleteVariosPais(ArrayList<Pais> listPais)
    {
        try
        {
            for(Pais pais : listPais)
            {
                if(pais.isSelect())
                    DeletePais(pais.getAlpha2Code());
            }
        } catch (Exception ex) {
            Log.d("Error", ex.getMessage());
        }
    }
    //Chama o método do repositório para fazer o delete do país no banco de dados
    public void DeletePais(String codPais) {
        try {
            repository.Delete(codPais);
        } catch (Exception ex) {
            Log.d("Error", ex.getMessage());
        }
    }

    //Faz um select no banco de dados e retona todos os países em um arraylist
    public ArrayList<Pais> GetListPaises() {
        try {
            ArrayList<Pais> listPais = repository.GetListPais();
            return listPais;
        } catch (Exception ex) {
            Log.d("Error", ex.getMessage());
            return null;
        }
    }

    //Faz um select no banco de dados e traz somente um país de acordo o codigo do país
    public Pais GetPais(String codPais) {
        try {
            Pais pais = repository.GetPais(codPais);
            return pais;
        } catch (Exception ex) {
            Log.d("Error", ex.getMessage());
            return null;
        }
    }
}
