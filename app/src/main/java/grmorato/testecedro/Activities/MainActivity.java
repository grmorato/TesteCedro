package grmorato.testecedro.Activities;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;

import java.util.concurrent.Callable;

import grmorato.testecedro.Activities.Utils.Pager;
import grmorato.testecedro.Library.LibFacebookService;
import grmorato.testecedro.R;

//Classe principal que contem o tablaout para conter as telas
public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener
{
    //Componente responsável por manipular as tabs
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Toolbar toolbar;
    //Callbackmanager responsável por manipular as requições da api do facebook
    private CallbackManager callbackManager;
    //Fragments das telas para adicionar no tablayout
    private ProfileActivity profileFragment;
    private CountriesFragment countriesFragment;
    private FavoritesFragment favoritesFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ConfigTab();
        ConfigFacebookButton();
    }

    //Configura o tablayout adicionando no mesmo os fragments
    private void ConfigTab()
    {
        tabLayout = findViewById(R.id.TabPageMain);
        viewPager = findViewById(R.id.PagerView);
        //Adiciona o tablayout no evento para realizar o movimento entre os fragments
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        //Cria o pager para retornar os fragments de acordo com cada posição do tablayout
        Pager pager = new Pager(getSupportFragmentManager(), tabLayout.getTabCount());
        profileFragment = new ProfileActivity();
        countriesFragment = new CountriesFragment();
        favoritesFragment = new FavoritesFragment();
        pager.getListFragment().add(profileFragment);
        pager.getListFragment().add(countriesFragment);
        pager.getListFragment().add(favoritesFragment);
        //adiciona o pager no viewpager
        viewPager.setAdapter(pager);
        //adiciona no tablayout o evento de tabselectedlistener
        tabLayout.addOnTabSelectedListener(this);
    }

    //Busca o botão do facebook do toolbar e passa o mesmo para o metodo que manipula o botão de login
    private void ConfigFacebookButton()
    {
        LoginButton loginButton = findViewById(R.id.login_button);
        callbackManager = LibFacebookService.FacebookConfig(loginButton ,null);
        LibFacebookService.FacebookStateLogin(new Callable() {

            @Override
            public Object call() throws Exception {
                finish();
                return null;
            }
        });
    }


   //busca a posição do tab corrente e passa para o viewpager mostrar o fragment
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    //Como o onActiviyResult dos fragments não são chamados devido a prioridade no onActiviyResult da activiy principal.
    //É chamado manualmente o onActivityResult do frament de countrys onde o mesmo busca o resultado do detailActivity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 111)
        {
            if(viewPager.getCurrentItem() == 1)
            {
                countriesFragment.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

}
