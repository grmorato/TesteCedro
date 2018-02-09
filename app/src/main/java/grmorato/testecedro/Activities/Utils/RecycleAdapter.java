package grmorato.testecedro.Activities.Utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import grmorato.testecedro.Data.Pais;
import grmorato.testecedro.Library.LibMobile;
import grmorato.testecedro.R;

import java.util.ArrayList;

/**
 * Created by guilherme.morato on 11/11/2016.
 */

public class RecycleAdapter extends RecyclerView.Adapter<ItemHolderAdapter>
{

    private final ArrayList<Pais> listDados;
    private final Context context;

    public RecycleAdapter(Context context, ArrayList<Pais> listDados)
    {
        this.context = context;
        this.listDados = listDados;
    }

    @Override
    public ItemHolderAdapter onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlist_countries, parent, false);
        return new ItemHolderAdapter(view);
    }

    @Override
    public void onBindViewHolder(ItemHolderAdapter holder, int position) {
        try {
            Pais item = listDados.get(position);
            String msgCode = item.getAlpha2Code() == null ? " " : item.getAlpha2Code();
            String msgName = item.getName() == null ? " " : item.getName();
            if (item.getFlag() != null)
            {
                holder.getWebView().loadData(LibMobile.GetImageUrl(item.getFlag()), "text/html", null);
            }
            holder.getTexto().setText(msgCode + " - " + msgName);
        } catch (Exception ex) {
            Log.d("Error", ex.getMessage());
        }
    }


    @Override
    public int getItemCount() {
        return listDados.size();
    }
}
