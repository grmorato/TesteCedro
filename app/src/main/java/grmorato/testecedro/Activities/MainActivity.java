package grmorato.testecedro.Activities;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;

import java.util.concurrent.Callable;

import grmorato.testecedro.Activities.Utils.Pager;
import grmorato.testecedro.Library.LibFacebookService;
import grmorato.testecedro.Library.LibMobile;
import grmorato.testecedro.R;

public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Toolbar toolbar;
    private CallbackManager callbackManager;
    private ProfileActivity profileFragment;
    private CountriesActivity countriesFragment;
    private FavoritesActivity favoritesFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ConfigTab();
        ConfigFacebookButton();
    }

    private void ConfigTab()
    {
        tabLayout = findViewById(R.id.TabPageMain);
        viewPager = findViewById(R.id.PagerView);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        Pager pager = new Pager(getSupportFragmentManager(), tabLayout.getTabCount());
        profileFragment = new ProfileActivity();
        countriesFragment = new CountriesActivity();
        favoritesFragment = new FavoritesActivity();
        pager.getListFragment().add(profileFragment);
        pager.getListFragment().add(countriesFragment);
        pager.getListFragment().add(favoritesFragment);
        viewPager.setAdapter(pager);
        tabLayout.addOnTabSelectedListener(this);
    }

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

    @Override
    protected void onStart() {
        super.onStart();
        if(AccessToken.getCurrentAccessToken() == null)
        {
            finish();
        }
    }

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
