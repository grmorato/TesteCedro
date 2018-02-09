package grmorato.testecedro.Activities;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import grmorato.testecedro.Activities.Utils.RecycleAdapter;
import grmorato.testecedro.Controllers.CtrlCountry;
import grmorato.testecedro.Data.Pais;
import grmorato.testecedro.R;

public class FavoritesActivity extends Fragment implements SwipeRefreshLayout.OnRefreshListener
{

    private RecyclerView listView;
    private LinearLayoutManager mLayoutManager;
    private SwipeRefreshLayout swipeRefresh;
    private CtrlCountry ctrlPaises;

    public FavoritesActivity() {
        this.ctrlPaises = new CtrlCountry();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view   = inflater.inflate(R.layout.activity_favorites, container, false);
        Init(view);
        return view;
    }

    private void Init(View view)
    {
        listView = view.findViewById(R.id.ListViewFavoritos);
        listView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        listView.setLayoutManager(mLayoutManager);

        swipeRefresh = view.findViewById(R.id.swipeRefreshFavoritos);
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


    private void LoadValues()
    {
        ArrayList<Pais> listPaises = ctrlPaises.GetListDataBase();
        if(listPaises != null) {
            RecycleAdapter recycleAdapter = new RecycleAdapter(getContext(), listPaises);
            listView.setAdapter(recycleAdapter);
            swipeRefresh.setRefreshing(false);
        }
    }

    @Override
    public void onRefresh()
    {
        LoadValues();
    }
}
