package grmorato.testecedro.Activities;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import grmorato.testecedro.Activities.Utils.AdapterList;
import grmorato.testecedro.Controllers.CtrlCountry;
import grmorato.testecedro.Data.Pais;
import grmorato.testecedro.Controllers.AsyncTaskLoadWsCountries;
import grmorato.testecedro.R;

public class CountriesActivity extends Fragment implements SwipeRefreshLayout.OnRefreshListener {


    private final CtrlCountry ctrlPaises;
    private SwipeRefreshLayout swipeRefresh;
    private GridView gridview;

    public CountriesActivity()
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

    private void Init(View view)
    {
        gridview = view.findViewById(R.id.gridviewCouontries);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

            }
        });

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


    private void LoadValues()
    {
        AsyncTaskLoadWsCountries asyncTaskLoad = new AsyncTaskLoadWsCountries();
        asyncTaskLoad.setContext(getContext());
        asyncTaskLoad.setGridview(gridview);
        asyncTaskLoad.setSwipeRefresh(swipeRefresh);
        asyncTaskLoad.execute();
    }

    @Override
    public void onRefresh()
    {
        LoadValues();
    }
}
