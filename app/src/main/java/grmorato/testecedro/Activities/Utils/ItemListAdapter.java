package grmorato.testecedro.Activities.Utils;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import grmorato.testecedro.R;

/**
 * Created by guilherme.morato on 11/11/2016.
 */

//Classe criada para servir para facilitar a manipulação dos componentes nas rows do listview
public class ItemListAdapter
{

    private LinearLayout sinal;
    private TextView texto;
    //Foi utilizado o webview devido o motivo da imagem da bandeira ser no formato svg.
    // Cujo o bitmap não trabalha não podendo assim alimentar o imageview
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

    public LinearLayout getSinal() {
        return sinal;
    }

    public void setSinal(LinearLayout sinal) {
        this.sinal = sinal;
    }
}
