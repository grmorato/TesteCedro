package grmorato.testecedro.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;
import java.util.concurrent.Callable;

import grmorato.testecedro.Controllers.CtrlProfile;
import grmorato.testecedro.Library.LibFacebookService;
import grmorato.testecedro.Library.LibMobile;
import grmorato.testecedro.R;

public class Splash extends AppCompatActivity
{

    public CallbackManager callbackManager;
    private LoginButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        final Context context = this;
        loginButton = findViewById(R.id.login_button);
        callbackManager = LibFacebookService.FacebookConfig(loginButton, new Callable() {
            @Override
            public Object call() throws Exception
            {
                new CtrlProfile(context).InsertProfile(LibFacebookService.userProfile);
                LibMobile.StartActivity(context,MainActivity.class);
                return null;
            }
        });

    }

    @Override
    protected void onStart()
    {
        super.onStart();
        CheckStartApp();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void StartApp()
    {
        final Context context = this;
        loginButton.setVisibility(AccessToken.getCurrentAccessToken() != null ? View.GONE: View.VISIBLE);
        if(AccessToken.getCurrentAccessToken() != null)
        {
            LibFacebookService.GetEmailProfile(null);
            CountDownTimer timer = new CountDownTimer(2000,1000)
            {
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    LibMobile.StartActivity(context, MainActivity.class);
                }
            }.start();
        }
    }

    private void CheckStartApp()
    {
        if(!LibMobile.VerificarConexao(this))
        {
            LibMobile.AlertMessage(R.string.semConexao, this, new Callable() {
                @Override
                public Object call() throws Exception
                {
                    CheckStartApp();
                    return null;
                }
            });
            return;
        }
        StartApp();
    }

}
