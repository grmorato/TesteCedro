package grmorato.testecedro.Controllers;

import android.content.Context;

import grmorato.testecedro.Data.Repositories.RepositoryProfile;
import grmorato.testecedro.Models.UserProfile;
import grmorato.testecedro.Library.LibFacebookService;

/**
 * Created by grmorato on 09/02/2018.
 */

//Controler responsável por fazer a regra de negócios da tela do usuário
public class CtrlProfile
{
    private final Context context;
    //Repositório responsável por fazer os sql's re da tabela de profile
    private final RepositoryProfile repository;

    public CtrlProfile(Context context) {
        this.context = context;
        this.repository = new RepositoryProfile(context);
    }


    //Realiza um insert no banco de dados com as informações do usuário
    public void InsertProfile(UserProfile userProfile)
    {
        if (userProfile != null && !userProfile.getEmail().equalsIgnoreCase("") && userProfile.getEmail() != null)
        {
            UserProfile userProfileAux = repository.GetProfile(LibFacebookService.userProfile.getEmail());
            if (userProfileAux == null) {
                repository.InsetProfile(userProfile.getName(), userProfile.getEmail(), userProfile.getImage());
            }
        }
    }

    //Retorna o usuário de acordo com o email logado
    public UserProfile GetProfile()
    {
        UserProfile userProfile = null;

        if (LibFacebookService.userProfile != null)

        {
            userProfile = repository.GetProfile(LibFacebookService.userProfile.getEmail());
        }
        return userProfile;
    }

}
