package grmorato.testecedro.Activities.Utils;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebView;
import android.widget.CheckBox;
import android.widget.TextView;

import grmorato.testecedro.R;

/**
 * Created by guilherme.morato on 11/11/2016.
 */

// Classe específica para fazer o binding das rows do listview com os componentes e podemar manipular os mesmos.
public class ItemHolderAdapter extends RecyclerView.ViewHolder
{
    private TextView texto;
    private WebView webView;
    private CheckBox check;

    public ItemHolderAdapter(View view)
    {
        super(view);
        if(view != null)
        {
            setTexto((TextView) view.findViewById(R.id.textViewFavName));
            //Foi utilizado o webview devido o motivo da imagem da bandeira ser no formato svg.
            // Cujo o bitmap não trabalha não podendo assim alimentar o imageview
            setWebView((WebView) view.findViewById(R.id.webViewFavFlag));
            setCheck((CheckBox) view.findViewById(R.id.CheckCountry));
        }
    }


    public TextView getTexto() {
        return texto;
    }

    public void setTexto(TextView texto) {
        this.texto = texto;
    }

    public CheckBox getCheck() {
        return check;
    }

    public void setCheck(CheckBox check) {
        this.check = check;
    }

    public WebView getWebView() {
        return webView;
    }

    public void setWebView(WebView webView) {
        this.webView = webView;
    }
}
