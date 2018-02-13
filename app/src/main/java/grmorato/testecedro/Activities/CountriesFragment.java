package grmorato.testecedro.Activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import grmorato.testecedro.Controllers.CtrlCountry;
import grmorato.testecedro.Controllers.AsyncTaskLoadWsCountries;
import grmorato.testecedro.Library.LibMobile;
import grmorato.testecedro.R;


//Fragment responsável por manipular os eventos e ações da tela  de listagem dos países
public class CountriesFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener
{

    //Classe de controle responsável pela regra de negocio da tela de países
    private final CtrlCountry ctrlPaises;
    //Componente para fazer o efeito de atualizar ao deslizar o dedo
    private SwipeRefreshLayout swipeRefresh;
    //Componente de grid para mostrar os países lado a lado
    private GridView gridview;

    public CountriesFragment()
    {
        ctrlPaises = new CtrlCountry();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view   = inflater.inflate(R.layout.activity_countries, container, false);
        Init(view);
        return view;
    }
    //Metodo responsável por iniciar os componentes e configurar os mesmos
    private void Init(View view)
    {
        gridview = view.findViewById(R.id.gridviewCouontries);
        swipeRefresh = view.findViewById(R.id.swipeRefreshCountries);
        swipeRefresh.setOnRefreshListener(this);
        swipeRefresh.post(new Runnable()
        {
            @Override
            public void run()
            {
                swipeRefresh.setRefreshing(true);
                LoadValues();
            }
        });
    }

    //Metodo responsável por alimentar o gridview
    private void LoadValues()
    {
        //Verifica se tem conexão com a internet caso contrário não alimenta o grid
        if(LibMobile.CheckConMsg(getContext()))
        {
            //Alimenta o grid utilizando a classe responsável por controlar e fazer tudo de forma assíncrona
            AsyncTaskLoadWsCountries asyncTaskLoad = new AsyncTaskLoadWsCountries();
            asyncTaskLoad.setContext(getActivity());
            asyncTaskLoad.setGridview(gridview);
            asyncTaskLoad.setSwipeRefresh(swipeRefresh);
            asyncTaskLoad.execute();
            return;
        }
        //Desativa o progress do componente
        swipeRefresh.setRefreshing(false);
    }

    //Evento chamado ao deslizar o dedo
    @Override
    public void onRefresh()
    {
        LoadValues();
    }

    //Onactivity result que busca a informação se for realizado ao save no banco caso sim recarrega o grid
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==111 && resultCode == Activity.RESULT_OK)
        {
            Boolean result=data.getBooleanExtra("result",false);
            if(result)
            {
                swipeRefresh.post(new Runnable() {
                    @Override
                    public void run() {
                        LoadValues();
                    }
                });
            }
        }
    }
}
