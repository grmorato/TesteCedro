package grmorato.testecedro.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import grmorato.testecedro.Data.Pais;
import grmorato.testecedro.R;

public class DetailActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Init();
        LoadValues();
    }

    private void LoadValues()
    {
        Pais pais = (Pais) getIntent().getSerializableExtra("Pais");
        ((TextView)findViewById(R.id.textViewDetailName)).setText(pais.getName());
        ((TextView)findViewById(R.id.textViewDetailCapital)).setText(pais.getCapital());
        ((TextView)findViewById(R.id.textViewDetailArea)).setText(pais.getArea());
        ((TextView)findViewById(R.id.textViewDetailPopulacao)).setText(pais.getPopulation());
    }


    private void Init()
    {
        toolbar = findViewById(R.id.toolbarDetail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
               finish();
               return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
