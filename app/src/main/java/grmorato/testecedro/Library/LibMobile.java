package grmorato.testecedro.Library;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.ArrayList;
import java.util.concurrent.Callable;

/**
 * Created by grmorato on 07/02/2018.
 */

public class LibMobile
{

    public static <C> void StartGameActivity(Context context, Class<C> classe)
    {
        Intent intent = new Intent(context, classe);
        context.startActivity(intent);
    }

    public static CallbackManager FacebookConfig(LoginButton loginButton, final Callable func)
    {
        CallbackManager callbackManager = CallbackManager.Factory.create();

        ArrayList<String> listPermissions = new ArrayList<>();
        listPermissions.add("email");
        listPermissions.add("public_profile");
        loginButton.setReadPermissions(listPermissions);

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult)
            {
                try {
                    if(func != null)
                        func.call();
                } catch (Exception e)
                {
                    String msg = e.getMessage();
                    if(msg != null && !msg.equalsIgnoreCase(""))
                        Log.d("Error",msg);
                }
            }

            @Override
            public void onCancel() {
                Log.w("DEBUG", "on Login Cancel");
            }

            @Override
            public void onError(FacebookException exception) {
                String msg = exception.getMessage();
                if(msg != null && !msg.equalsIgnoreCase(""))
                    Log.d("Error",msg);
            }
        });

        return callbackManager;
    }

    public static void FacebookStateLogin(final Callable funcLogout)
    {
        AccessTokenTracker tokenTracker = new AccessTokenTracker() {

            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken,
                                                       AccessToken currentAccessToken) {
                try
                {
                    AccessToken oldAccessTokenGlobal = oldAccessToken;
                    AccessToken currentAccessTokenGlobal = currentAccessToken;

                        if(funcLogout != null)
                            funcLogout.call();
                    } catch (Exception e)
                    {
                        String msg = e.getMessage();
                        if(msg != null && !msg.equalsIgnoreCase(""))
                            Log.d("Error",msg);
                    }
            }
        };
        tokenTracker.startTracking();
    }

}
