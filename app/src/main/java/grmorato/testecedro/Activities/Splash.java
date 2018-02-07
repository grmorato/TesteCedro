package grmorato.testecedro.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;
import java.util.concurrent.Callable;
import grmorato.testecedro.Library.LibMobile;
import grmorato.testecedro.R;

public class Splash extends AppCompatActivity
{

    public CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        final Context context = this;
        callbackManager = LibMobile.FacebookConfig((LoginButton) findViewById(R.id.login_button), new Callable() {
            @Override
            public Object call() throws Exception
            {
                LibMobile.StartGameActivity(context,MainActivity.class);
                return null;
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(AccessToken.getCurrentAccessToken() != null)
            LibMobile.StartGameActivity(this,MainActivity.class);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
