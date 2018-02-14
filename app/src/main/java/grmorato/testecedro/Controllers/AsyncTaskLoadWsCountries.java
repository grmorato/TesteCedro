package grmorato.testecedro.Controllers;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.GridView;

import java.util.ArrayList;

import grmorato.testecedro.Activities.Utils.AdapterList;
import grmorato.testecedro.Models.Pais;

/**
 * Created by grmorato on 09/02/2018.
 */


//Classe responsável para buscara os dados dos países do webservice de forma assincrona e permitir atualizar o grid facilmente
public class AsyncTaskLoadWsCountries extends AsyncTask<Void, Void,ArrayList<Pais>>
{
    private GridView gridview;
    private SwipeRefreshLayout swipeRefresh;
    private Context context;


    @Override
    protected ArrayList<Pais> doInBackground(Void... voids) {
        ArrayList arrayList = new CtrlCountry().GetListRestWebService();
        return arrayList;
    }

    @Override
    protected void onPostExecute(ArrayList<Pais> listPaises) {
        if(listPaises != null)
        {
            getGridview().setAdapter(new AdapterList(getContext(), listPaises));
            getSwipeRefresh().setRefreshing(false);
        }
    }

    public GridView getGridview() {
        return gridview;
    }

    public void setGridview(GridView gridview) {
        this.gridview = gridview;
    }

    public SwipeRefreshLayout getSwipeRefresh() {
        return swipeRefresh;
    }

    public void setSwipeRefresh(SwipeRefreshLayout swipeRefresh) {
        this.swipeRefresh = swipeRefresh;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
