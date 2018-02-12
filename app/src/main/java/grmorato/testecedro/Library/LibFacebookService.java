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

public class LibFacebookService {

    public static UserProfile userProfile;

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

    public static void FacebookStateLogin(final Callable funcLogout) {
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
