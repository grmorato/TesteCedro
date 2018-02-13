package grmorato.testecedro.Activities;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import grmorato.testecedro.Activities.Utils.RecycleAdapter;
import grmorato.testecedro.Controllers.CtrlCountry;
import grmorato.testecedro.Controllers.CtrlFavorites;
import grmorato.testecedro.Library.LibMobile;
import grmorato.testecedro.Library.OnItemClickListener;
import grmorato.testecedro.Models.Pais;
import grmorato.testecedro.R;

//Fragment responsável por manipular os eventos e ações da tela  de listagem dos países que o mesmo visitou
public class FavoritesFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener,View.OnClickListener
{

    private RecyclerView listView;
    private LinearLayoutManager mLayoutManager;
    private SwipeRefreshLayout swipeRefresh;
    //Classe responsável pela regra de negocio da tela
    private CtrlFavorites ctrlFavorites;
    private Toolbar toolbar;
    //List que armazena os países já visitados
    private ArrayList<Pais> listPaises;
    //Classe adapter responsável por alimentar e manipular os dados e componentes no grid
    private RecycleAdapter recycleAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view  = inflater.inflate(R.layout.activity_favorites, container, false);
        Init(view);
        return view;
    }

    //Metodo responsável por iniciar os componentes e configurar os mesmos
    private void Init(View view)
    {
        //busca o toolbar criado e o informa na tela
        toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        this.ctrlFavorites = new CtrlFavorites(this.getContext());
        //busca o recyclerview e configura o mesmo
        listView = view.findViewById(R.id.ListViewFavoritos);
        //Seta como true o metodo responsável por deixar o listview mais rápido
        listView.setHasFixedSize(true);
        //Cria o LinearLayoutManager para e informa no list para organizar os dados
        mLayoutManager = new LinearLayoutManager(getContext());
        listView.setLayoutManager(mLayoutManager);
        //Cria o componente de swipe para dar a possibilidade do usuário atualizar as informações quando quiser
        swipeRefresh = view.findViewById(R.id.swipeRefreshFavoritos);
        swipeRefresh.setOnRefreshListener(this);

        //Seta o evento de click nos botões do toolbar
        view.findViewById(R.id.buttonSelectAll).setOnClickListener(this);
        view.findViewById(R.id.buttonDeselectAll).setOnClickListener(this);
        view.findViewById(R.id.buttonDelete).setOnClickListener(this);

    }

    //Criadod esse metodo para atualizar o list automáticamente quando se abre a tela
    @Override
    public void onStart()
    {
        super.onStart();
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

    //Metodo para carregar o list com os dados do banco
    private void LoadValues()
    {
        listPaises = ctrlFavorites.GetListPaises();
        if(listPaises != null) {
            //É passado uma função no construtor para chamar a tela de detalhe ao tocar nos componentes da row
            recycleAdapter = new RecycleAdapter(getContext(), listPaises, new OnItemClickListener() {
                @Override
                public void onItemClick(Pais pais) {
                    CtrlCountry.StartDetail(getContext(),pais);
                }
            });
            listView.setAdapter(recycleAdapter);
        }
        //Retira o progress da tela
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
        //Seleciona todos os itens
        if(v.getId() == R.id.buttonSelectAll)
        {
            SelectDeselectAll(true);
        }
        //Desseleciona todos os itens
        else if(v.getId() == R.id.buttonDeselectAll)
        {
            SelectDeselectAll(false);
        }
        //Realizar o delete dos itens selecionados
        else
        {
            //Verifica se tem dados antes de deletar
            if(listPaises == null || listPaises.size() ==0)
                return;
            //Chama o alertquestion com a função no yes para deletar os registros
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

    //Metodo para percorrer o list de países e seleciona-los ou desseleciona-los
    private void SelectDeselectAll(boolean select)
    {
        if(listPaises != null && recycleAdapter != null && listView != null)
        {
            for(int i =0;i < listPaises.size(); i++)
            {
               Pais pais = listPaises.get(i);
               pais.setSelect(select);
            }
            recycleAdapter.setListDados(listPaises);
        }
    }

}
