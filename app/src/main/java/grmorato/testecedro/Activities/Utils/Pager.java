package grmorato.testecedro.Activities.Utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * Created by grmorato on 07/02/2018.
 */

//Classe de pager utilizada para manipular os fragments do tabpage
public class Pager extends FragmentStatePagerAdapter
{
    //Variável para armazenar o total de tabs
    private final int countTabs;
    //Criado o arraylist para poder manipular as tabs deixando a utilização do mesmo mais genérico
    private ArrayList<Fragment> listFragment;

    public Pager(FragmentManager fm, int countTabs) {
        super(fm);
        this.countTabs = countTabs;
    }

    @Override
    public Fragment getItem(int position)
    {
        if(getListFragment().size() >0)
            return getListFragment().get(position);
        else
            return null;

    }

    @Override
    public int getCount() {
        return countTabs;
    }

    public ArrayList<Fragment> getListFragment()
    {
        if(listFragment == null)
            listFragment = new ArrayList<>();
        return listFragment;
    }
}
