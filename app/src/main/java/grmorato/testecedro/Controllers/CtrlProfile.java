package grmorato.testecedro.Controllers;

import android.content.Context;

import grmorato.testecedro.Data.RepositoryProfile;
import grmorato.testecedro.Models.UserProfile;
import grmorato.testecedro.Library.LibFacebookService;

/**
 * Created by grmorato on 09/02/2018.
 */

public class CtrlProfile
{
    private final Context context;
    private final RepositoryProfile repository;

    public CtrlProfile(Context context)
    {
        this.context = context;
        this.repository = new RepositoryProfile(context);
    }


    public void InsertProfile(UserProfile userProfile)
    {
        if(userProfile != null && !userProfile.getEmail().equalsIgnoreCase("") && userProfile.getEmail() != null)
        {
            UserProfile userProfileAux = repository.GetProfile(LibFacebookService.userProfile.getEmail());
            if(userProfileAux == null)
            {
                repository.InsetProfile(userProfile.getName(),userProfile.getEmail(),userProfile.getImage());
            }
        }
    }

    public UserProfile GetProfile()
    {
        UserProfile userProfile = repository.GetProfile(LibFacebookService.userProfile.getEmail());
        return userProfile;
    }

}
