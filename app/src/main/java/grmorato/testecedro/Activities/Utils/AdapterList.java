package grmorato.testecedro.Activities.Utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import grmorato.testecedro.Data.Pais;
import grmorato.testecedro.Library.LibMobile;
import grmorato.testecedro.R;

import java.util.ArrayList;

/**
 * Created by guilherme.morato on 11/11/2016.
 */

public class AdapterList extends ArrayAdapter
{

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

            Pais item = (Pais) getItem(position);
            if (item != null && itemAdapter != null) {
                String msgCode = item.getAlpha2Code() == null ? " " : item.getAlpha2Code();
                String msgName = item.getName() == null ? " " : item.getName();
                itemAdapter.getTexto().setText(msgCode + " - " + msgName);
                if (item.getFlag() != null)
                {
                    itemAdapter.getWebView().loadData(LibMobile.GetImageUrl(item.getFlag()), "text/html", null);
                }
            }
            return convertView;
        }catch (Exception ex)
        {
            Log.d("Error",ex.getMessage());
            return null;
        }
    }

}
