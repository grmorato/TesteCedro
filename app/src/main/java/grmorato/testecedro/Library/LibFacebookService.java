package grmorato.testecedro.Library;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import grmorato.testecedro.Models.UserProfile;

/**
 * Created by grmorato on 10/02/2018.
 */

//Classe responsável por manipular a api do facebook para realizar o login e retornar os dados do mesmo
public class LibFacebookService {

    //Classe responsável por encapsular os dados do usuário
    public static UserProfile userProfile;

    //Metodo responsável por centralizar a configuração do butão de login do facebook em um só lugar
    // e também o evento do mesmo. É passado por parâmetro uma função para ser chamada caso tenha algo a fazer após o login.
    public static CallbackManager FacebookConfig(LoginButton loginButton, final Callable func) {
        CallbackManager callbackManager = CallbackManager.Factory.create();

        ArrayList<String> listPermissions = new ArrayList<>();
        listPermissions.add("email");
        listPermissions.add("public_profile");
        loginButton.setReadPermissions(listPermissions);

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GetEmailProfile(func);
            }

            @Override
            public void onCancel() {
                Log.w("DEBUG", "on Login Cancel");
            }

            @Override
            public void onError(FacebookException exception) {
                String msg = exception.getMessage();
                if (msg != null && !msg.equalsIgnoreCase(""))
                    Log.d("Error", msg);
            }
        });

        return callbackManager;
    }

    //Método responsável por conter os eventos de mudança AcessToken.
    //Sendo assim  é utilizado para verificar se o usuário está logado ou não
    public static void FacebookStateLogin(final Callable funcLogout)
    {
        AccessTokenTracker tokenTracker = new AccessTokenTracker() {

            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken,
                                                       AccessToken currentAccessToken) {
                try {
                    AccessToken oldAccessTokenGlobal = oldAccessToken;
                    AccessToken currentAccessTokenGlobal = currentAccessToken;
                    if (funcLogout != null)
                        funcLogout.call();
                } catch (Exception e) {
                    String msg = e.getMessage();
                    if (msg != null && !msg.equalsIgnoreCase(""))
                        Log.d("Error", msg);
                }
            }
        };
        tokenTracker.startTracking();
    }


    //Método responsável por fazer o request utilizando a api do facebook para retornar os dados do usuário
    public static void GetEmailProfile(final Callable func) {
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject json, GraphResponse response) {
                try {
                    if (response.getError() == null) {
                        String id = json.optString("id");
                        String name = json.optString("name");
                        String userEmail = json.optString("email");
                        final String urlImage = "https://graph.facebook.com/" + id + "/picture?type=large";
                        userProfile = new UserProfile();
                        userProfile.setName(name);
                        userProfile.setEmail(userEmail);
                        AsyncTask.execute(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    byte[] bytes = LibServiceRest.GetImageUrl(urlImage);
                                    userProfile.setImage(bytes);
                                    if (func != null)
                                        func.call();
                                } catch (Exception e) {
                                    String msg = e.getMessage();
                                    if (msg != null && !msg.equalsIgnoreCase(""))
                                        Log.d("Error", msg);
                                }
                            }
                        });


                    }

                } catch (Exception e) {
                    String msg = e.getMessage();
                    if (msg != null && !msg.equalsIgnoreCase(""))
                        Log.d("Error", msg);
                }
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,gender, birthday");
        request.setParameters(parameters);
        request.executeAsync();
    }

}
