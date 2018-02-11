package grmorato.testecedro.Activities;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import grmorato.testecedro.Activities.Utils.RecycleAdapter;
import grmorato.testecedro.Controllers.CtrlCountry;
import grmorato.testecedro.Controllers.CtrlFavorites;
import grmorato.testecedro.Library.OnItemClickListener;
import grmorato.testecedro.Models.Pais;
import grmorato.testecedro.R;

public class FavoritesActivity extends Fragment implements SwipeRefreshLayout.OnRefreshListener
{

    private RecyclerView listView;
    private LinearLayoutManager mLayoutManager;
    private SwipeRefreshLayout swipeRefresh;
    private CtrlFavorites ctrlFavorites;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view   = inflater.inflate(R.layout.activity_favorites, container, false);
        Init(view);
        return view;
    }

    private void Init(View view)
    {
        this.ctrlFavorites = new CtrlFavorites(this.getContext());
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
        ArrayList<Pais> listPaises = ctrlFavorites.GetListPaises();
        if(listPaises != null) {
            RecycleAdapter recycleAdapter = new RecycleAdapter(getContext(), listPaises, new OnItemClickListener() {
                @Override
                public void onItemClick(Pais pais) {
                    CtrlCountry.StartDetail(getContext(),pais);
                }
            });
            listView.setAdapter(recycleAdapter);
        }
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void onRefresh()
    {
        LoadValues();
    }
}
