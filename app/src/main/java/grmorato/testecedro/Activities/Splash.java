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

//Activity de splash onde tem duas funções de splash depois que o usuário já logou com a conta do facebook.
//E de login esperando a confirmação do usuário para logar com sua conta do facebook
public class Splash extends AppCompatActivity
{

    //Callbackmanager responsável por manipular as requições da api do facebook
    public CallbackManager callbackManager;
    //Botão de login da api do facebook onde já possuí funções para facilitar a requisição
    // E busca pelo token do usuário ao logar
    private LoginButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        final Context context = this;
        //Busca o botão de login do facebook
        loginButton = findViewById(R.id.login_button);
        //Passa o button no metodo que manipula os resultados do login do facebook
        // E também passa a função como parâmetro responsável por fazer o save dos dados do usuário e start na activity main
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

    //Metodo responsável por controlar a visibilidade do botão do facebook caso já esteja logado o deixa invisivel
    //E também verifica se o mesmo já está logado caso sim faz a função de splash
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

    //Metodo responsável por verificar se tem conexão com a internet caso não exibe uma mensagem e mantém na mesma até que tenha conexão
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
