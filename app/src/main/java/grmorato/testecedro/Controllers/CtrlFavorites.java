package grmorato.testecedro.Controllers;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

import grmorato.testecedro.Data.RepositoryFavorites;
import grmorato.testecedro.Data.RepositoryProfile;
import grmorato.testecedro.Library.LibFacebookService;
import grmorato.testecedro.Models.Pais;
import grmorato.testecedro.Models.UserProfile;

/**
 * Created by grmorato on 11/02/2018.
 */

public class CtrlFavorites {
    private final Context context;
    private final RepositoryFavorites repository;

    public CtrlFavorites(Context context) {
        this.context = context;
        this.repository = new RepositoryFavorites(context);
    }


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

    public void DeletePais(String codPais) {
        try {
            repository.Delete(codPais);
        } catch (Exception ex) {
            Log.d("Error", ex.getMessage());
        }
    }

    public ArrayList<Pais> GetListPaises() {
        try {
            ArrayList<Pais> listPais = repository.GetListPais();
            return listPais;
        } catch (Exception ex) {
            Log.d("Error", ex.getMessage());
            return null;
        }
    }

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
