package grmorato.testecedro.Activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Callable;

import grmorato.testecedro.Controllers.CtrlFavorites;
import grmorato.testecedro.Library.LibMobile;
import grmorato.testecedro.Models.Pais;
import grmorato.testecedro.R;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {

    private CtrlFavorites ctrlFavorites;
    private Toolbar toolbar;
    private Calendar calendar;
    private EditText textDate;
    private boolean isModificou;
    private CheckedTextView checkText;
    private Pais pais;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Init();
        LoadValues();
    }

    private void LoadValues() {
        pais = (Pais) getIntent().getSerializableExtra("Pais");
        ((TextView) findViewById(R.id.textViewDetailName)).setText(pais.getName());
        ((TextView) findViewById(R.id.textViewDetailCapital)).setText(pais.getCapital());
        ((TextView) findViewById(R.id.textViewDetailArea)).setText(pais.getArea());
        ((TextView) findViewById(R.id.textViewDetailPopulacao)).setText(pais.getPopulation());
        Pais favorite = ctrlFavorites.GetPais(pais.getAlpha2Code());
        checkText.setChecked(favorite != null);
        if(favorite != null)
            textDate.setText(favorite.getDateVisit());

    }


    private void Init() {
        toolbar = findViewById(R.id.toolbarDetail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        findViewById(R.id.buttonDate).setOnClickListener(this);
        findViewById(R.id.buttonTime).setOnClickListener(this);
        checkText = findViewById(R.id.checkedDetail);
        textDate = findViewById(R.id.editTextDateTime);
        calendar = Calendar.getInstance();
        ctrlFavorites = new CtrlFavorites(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                BackPress();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        BackPress();
    }

    @Override
    public void onClick(View v)
    {
        Callable func = new Callable(){
            @Override
            public Object call() throws Exception
            {
                UpdateTextDate();
                return null;
            }
        };

        if (v.getId() == R.id.buttonDate)
        {
            LibMobile.DateTimePicker(this, true, calendar,func);
        }
        else if (v.getId() == R.id.buttonTime)
        {
            LibMobile.DateTimePicker(this, false, calendar,func);
        }
    }

    private void UpdateTextDate()
    {
        Date date = calendar.getTime();
        String dateTime = LibMobile.StringDateFormat(date);
        textDate.setText(dateTime);
        isModificou = true;
    }

    private void BackPress()
    {
        if(isModificou)
        {
            Callable callableYes = new Callable() {
                @Override
                public Object call() throws Exception
                {
                    pais.setDateVisit(textDate.getText().toString());
                    ctrlFavorites.SalvarFavorite(pais);
                    finish();
                    return null;
                }
            };
            Callable callableNo = new Callable() {
                @Override
                public Object call() throws Exception
                {
                   finish();
                    return null;
                }
            };
            LibMobile.AlertMessageQuestion(R.string.MsgSalvar, this, callableYes,callableNo);
            return;
        }
        finish();
    }

}
