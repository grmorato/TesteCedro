package grmorato.testecedro.Activities.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import grmorato.testecedro.Controllers.CtrlCountry;
import grmorato.testecedro.Controllers.CtrlFavorites;
import grmorato.testecedro.Models.Pais;
import grmorato.testecedro.Library.LibMobile;
import grmorato.testecedro.R;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by guilherme.morato on 11/11/2016.
 */

public class AdapterList extends ArrayAdapter {

    private long touchDownMiliseconds;

    public AdapterList(Context context, ArrayList<Pais> listDados) {
        super(context, 0, listDados);
    }


    @SuppressLint("ClickableViewAccessibility")
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try {
            //Cria uma classe adapter só para armazenar os objetos de text para não precisar ficar buscando eles toda hora
            ItemListAdapter itemAdapter = new ItemListAdapter();

            //se o convertView estiver nulo cria o mesmo e cria os objetos de text no itemAdapter e set no convertView
            if (convertView == null)
            {
                convertView = LayoutInflater.from(this.getContext()).inflate(R.layout.itemlist_countries, parent, false);
                itemAdapter.setTexto((TextView) convertView.findViewById(R.id.textViewCountryName));
                itemAdapter.setWebView((WebView) convertView.findViewById(R.id.webViewCountryFlag));
                itemAdapter.setSinal((LinearLayout) convertView.findViewById(R.id.sinalCountry));
                convertView.setTag(itemAdapter);
            }
            //Caso contrario somente busca o itemAdapter que está no tag do convertView
            else {
                itemAdapter = (ItemListAdapter) convertView.getTag();
            }

            final Pais pais = (Pais) getItem(position);
            if(pais != null)
            {
                String msgCode = pais.getAlpha2Code() == null ? " " : pais.getAlpha2Code();
                String msgName = pais.getName() == null ? " " : pais.getName();
                itemAdapter.getTexto().setText(msgCode + " - " + msgName);

                // Configura o webview para chamar a tela de detalhe
                itemAdapter.getWebView().loadData(LibMobile.GetImageUrl(pais.getFlag()), "text/html", null);
                itemAdapter.getWebView().setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        WebViewTouch(v, event, pais);
                        return false;
                    }
                });


                //Eventos de click direto no text  chamar a tela de detalhes
                itemAdapter.getTexto().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        StartDetail(pais);
                    }
                });

                Pais paisAux = new CtrlFavorites(getContext()).GetPais(pais.getAlpha2Code());
                if (paisAux != null)
                    itemAdapter.getSinal().setBackgroundColor(Color.GREEN);
            }
            return convertView;
        } catch (Exception ex) {
            Log.d("Error", ex.getMessage());
            return null;
        }
    }

    public void WebViewTouch(View v, MotionEvent event, final Pais pais) {
        //Tratamento realizado para o touch no webview só chame a tela de detalhe se for um touch rápido
        if (v.getId() == R.id.webViewCountryFlag) {
            long currentTime = new Date().getTime();
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                touchDownMiliseconds = currentTime;
            } else if (event.getAction() == MotionEvent.ACTION_UP && (currentTime - touchDownMiliseconds) < 200) {
                StartDetail(pais);
            }
        }
    }

    private void StartDetail(Pais pais) {
        if (pais != null) {
            CtrlCountry.StartDetail(getContext(), pais);
        }
    }


}
