package grmorato.testecedro.Library;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.Callable;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by grmorato on 07/02/2018.
 */

public class LibMobile {

    private final Context context;
    private ProgressDialog mProgressDialog;

    public LibMobile(Context context) {
        this.context = context;
    }

    public static <C> void StartGameActivity(Context context, Class<C> classe) {
        Intent intent = new Intent(context, classe);
        context.startActivity(intent);
    }

    public static CallbackManager FacebookConfig(LoginButton loginButton, final Callable func) {
        CallbackManager callbackManager = CallbackManager.Factory.create();

        ArrayList<String> listPermissions = new ArrayList<>();
        listPermissions.add("email");
        listPermissions.add("public_profile");
        loginButton.setReadPermissions(listPermissions);

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                try {
                    if (func != null)
                        func.call();
                } catch (Exception e) {
                    String msg = e.getMessage();
                    if (msg != null && !msg.equalsIgnoreCase(""))
                        Log.d("Error", msg);
                }
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

    public static boolean VerificarConexao(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
        return netInfo != null && netInfo.isAvailable() && netInfo.isConnected();
    }

    public void ShowProgressDialog(boolean semAnimacao) {

        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(context);
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.setCancelable(false);
        }
        if (mProgressDialog.isShowing())
            return;
        mProgressDialog.show();
    }

    public void hideProgressDialog() {

        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }

    }

    public static void AlertMessage(int resourceMessage, final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(resourceMessage);
        Dialog alerta = builder.create();
        alerta.show();
    }

    public static String GetImageUrl(String url)
    {
        String data="<html><head><title>Example</title><meta name=\"viewport\"\"content=\"width=100, initial-scale=0.65 \" /></head>";
        data=data+"<body><center><img width=100 src=\""+url+"\" /></center></body></html>";
        return data;
    }


}
