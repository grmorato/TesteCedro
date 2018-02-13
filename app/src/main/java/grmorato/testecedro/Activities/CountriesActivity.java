package grmorato.testecedro.Activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import grmorato.testecedro.Controllers.CtrlCountry;
import grmorato.testecedro.Controllers.AsyncTaskLoadWsCountries;
import grmorato.testecedro.Library.LibMobile;
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
        if(LibMobile.CheckConMsg(getContext()))
        {
            AsyncTaskLoadWsCountries asyncTaskLoad = new AsyncTaskLoadWsCountries();
            asyncTaskLoad.setContext(getActivity());
            asyncTaskLoad.setGridview(gridview);
            asyncTaskLoad.setSwipeRefresh(swipeRefresh);
            asyncTaskLoad.execute();
            return;
        }
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void onRefresh()
    {
        LoadValues();
    }

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
