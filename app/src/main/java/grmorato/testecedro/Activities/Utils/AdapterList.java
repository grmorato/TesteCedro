package grmorato.testecedro.Activities.Utils;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import grmorato.testecedro.Activities.DetailActivity;
import grmorato.testecedro.Controllers.CtrlCountry;
import grmorato.testecedro.Data.Pais;
import grmorato.testecedro.Library.LibMobile;
import grmorato.testecedro.R;

import java.util.ArrayList;

/**
 * Created by guilherme.morato on 11/11/2016.
 */

public class AdapterList extends ArrayAdapter implements View.OnTouchListener
{

    private Pais pais;

    public AdapterList(Context context, ArrayList<Pais> listDados)
    {
        super(context, 0,listDados);
    }



    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        try {
            //Cria uma classe adapter só para armazenar os objetos de text para não precisar ficar buscando eles toda hora
            ItemListAdapter itemAdapter = new ItemListAdapter();

            //se o convertView estiver nulo cria o mesmo e cria os objetos de text no itemAdapter e set no convertView
            if (convertView == null) {
                convertView = LayoutInflater.from(this.getContext()).inflate(R.layout.itemlist_countries, parent, false);
                itemAdapter.setTexto((TextView) convertView.findViewById(R.id.textViewCountryName));
                itemAdapter.setWebView((WebView) convertView.findViewById(R.id.webViewCountryFlag));
            }
            //Caso contrario somente busca o itemAdapter que está no tag do convertView
            else {
                itemAdapter = (ItemListAdapter) convertView.getTag();
            }

            pais = (Pais) getItem(position);
            if (pais != null && itemAdapter != null)
            {
                String msgCode = pais.getAlpha2Code() == null ? " " : pais.getAlpha2Code();
                String msgName = pais.getName() == null ? " " : pais.getName();
                itemAdapter.getTexto().setText(msgCode + " - " + msgName);

                // Configura o webview para chamar a tela de detalhe
                ConfigWebView( itemAdapter.getWebView());
                if (pais.getFlag() != null)
                {
                    itemAdapter.getWebView().loadData(LibMobile.GetImageUrl(pais.getFlag()), "text/html", null);
                }

                //Eventos de click direto no text  chamar a tela de detalhes
                itemAdapter.getTexto().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        StartDetail();
                    }
                });
            }

            return convertView;
        }catch (Exception ex)
        {
            Log.d("Error",ex.getMessage());
            return null;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        if (v.getId() == R.id.webViewCountryFlag && event.getAction() == MotionEvent.ACTION_DOWN)
        {
            StartDetail();
        }
        return false;
    }

    private void ConfigWebView(WebView webView)
    {
        webView.setOnTouchListener(this);
    }

    private void StartDetail()
    {
        if(pais != null) {
            CtrlCountry.StartDetail(getContext(),pais);
        }
    }
}
