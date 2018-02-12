package grmorato.testecedro.Activities;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import grmorato.testecedro.Activities.Utils.ItemHolderAdapter;
import grmorato.testecedro.Activities.Utils.RecycleAdapter;
import grmorato.testecedro.Controllers.CtrlCountry;
import grmorato.testecedro.Controllers.CtrlFavorites;
import grmorato.testecedro.Library.LibMobile;
import grmorato.testecedro.Library.OnItemClickListener;
import grmorato.testecedro.Models.Pais;
import grmorato.testecedro.R;

public class FavoritesActivity extends Fragment implements SwipeRefreshLayout.OnRefreshListener,View.OnClickListener
{

    private RecyclerView listView;
    private LinearLayoutManager mLayoutManager;
    private SwipeRefreshLayout swipeRefresh;
    private CtrlFavorites ctrlFavorites;
    private Toolbar toolbar;
    private ArrayList<Pais> listPaises;
    private RecycleAdapter recycleAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view  = inflater.inflate(R.layout.activity_favorites, container, false);
        Init(view);
        return view;
    }

    private void Init(View view)
    {
        toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
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

        view.findViewById(R.id.buttonSelectAll).setOnClickListener(this);
        view.findViewById(R.id.buttonDeselectAll).setOnClickListener(this);
        view.findViewById(R.id.buttonDelete).setOnClickListener(this);

    }


    private void LoadValues()
    {
        listPaises = ctrlFavorites.GetListPaises();
        if(listPaises != null) {
            recycleAdapter = new RecycleAdapter(getContext(), listPaises, new OnItemClickListener() {
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


    @Override
    public void onClick(View v)
    {
        if(v.getId() == R.id.buttonSelectAll)
        {
            SelectDeselectAll(true);
        }
        else if(v.getId() == R.id.buttonDeselectAll)
        {
            SelectDeselectAll(false);
        }
        else
        {
            if(listPaises == null || listPaises.size() ==0)
                return;
            LibMobile.AlertMessageQuestion(R.string.msgDelete, getContext(), new Callable() {
                @Override
                public Object call() throws Exception
                {
                    ctrlFavorites.DeleteVariosPais(listPaises);
                    LoadValues();
                    return null;
                }
            },null);
        }

    }

    private void SelectDeselectAll(boolean select)
    {
        if(listPaises != null && recycleAdapter != null && listView != null)
        {
            for(int i =0;i < listPaises.size(); i++)
            {
               Pais pais = listPaises.get(i);
               pais.setSelect(select);
               ItemHolderAdapter holder =(ItemHolderAdapter) listView.findViewHolderForAdapterPosition(i);
               holder.getCheck().setChecked(select);
            }
        }
    }

}
