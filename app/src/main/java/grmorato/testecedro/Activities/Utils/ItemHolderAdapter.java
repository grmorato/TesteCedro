package grmorato.testecedro.Activities.Utils;

import android.content.ClipData;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import grmorato.testecedro.R;

/**
 * Created by guilherme.morato on 11/11/2016.
 */

public class ItemHolderAdapter extends RecyclerView.ViewHolder
{
    private TextView texto;
    private WebView webView;

    public ItemHolderAdapter(View view)
    {
        super(view);
        if(view != null)
        {
            setTexto((TextView) view.findViewById(R.id.textViewCountryName));
            setWebView((WebView) view.findViewById(R.id.webViewCountryFlag));
        }
    }


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