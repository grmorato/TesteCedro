package grmorato.testecedro.Activities.Utils;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import grmorato.testecedro.R;

/**
 * Created by guilherme.morato on 11/11/2016.
 */

public class ItemListAdapter
{
    private TextView texto;
    private WebView webView;

    public TextView getTexto() {
        return texto;
    }

    public void setTexto(TextView texto) {
        this.texto = texto;
    }

    public WebView getWebView() {
        return webView;
    }

    public void setWebView(WebView webView) {
        this.webView = webView;
    }
}
