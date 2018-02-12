package grmorato.testecedro.Activities.Utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import grmorato.testecedro.Controllers.CtrlCountry;
import grmorato.testecedro.Library.OnItemClickListener;
import grmorato.testecedro.Models.Pais;
import grmorato.testecedro.Library.LibMobile;
import grmorato.testecedro.R;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by guilherme.morato on 11/11/2016.
 */

public class RecycleAdapter extends RecyclerView.Adapter<ItemHolderAdapter> {

    private final ArrayList<Pais> listDados;
    private final Context context;
    private final OnItemClickListener onItemClickListener;
    private long touchDownMiliseconds;

    public RecycleAdapter(Context context, ArrayList<Pais> listDados, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.listDados = listDados;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ItemHolderAdapter onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlist_favorites, parent, false);
        return new ItemHolderAdapter(view);
    }

    @Override
    public void onBindViewHolder(ItemHolderAdapter holder, int position) {
        try
        {
            //busca e trata os dados
            final Pais item = listDados.get(position);
            String msgCode = item.getAlpha2Code() == null ? " " : item.getAlpha2Code();
            String msgName = item.getName() == null ? " " : item.getName();

            //Seta os dados nos componentes
            holder.getWebView().loadData(LibMobile.GetImageUrl(item.getFlag()), "text/html", null);
            holder.getTexto().setText(msgCode + " - " + msgName);

            //Seta os eventos específicos dos componentes
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(item);
                }
            });

            holder.getCheck().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    item.setSelect(isChecked);
                }
            });

            holder.getWebView().setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    WebViewTouch(v, event, item);
                    return false;
                }
            });
        } catch (Exception ex) {
            Log.d("Error", ex.getMessage());
        }
    }

    public void WebViewTouch(View v, MotionEvent event, final Pais pais)
    {
        //Tratamento realizado para o touch no webview só chame a tela de detalhe se for um touch rápido
        if (v.getId() == R.id.webViewFavFlag) {
            long currentTime = new Date().getTime();
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                touchDownMiliseconds = currentTime;
            } else if (event.getAction() == MotionEvent.ACTION_UP && (currentTime - touchDownMiliseconds) < 200) {
                CtrlCountry.StartDetail(v.getContext(), pais);
            }
        }
    }

    @Override
    public int getItemCount() {
        return listDados.size();
    }
}
