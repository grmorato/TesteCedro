package grmorato.testecedro.Activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Callable;

import grmorato.testecedro.Controllers.CtrlFavorites;
import grmorato.testecedro.Library.LibMobile;
import grmorato.testecedro.Library.LibServiceRest;
import grmorato.testecedro.Models.Pais;
import grmorato.testecedro.R;

//Activity responsável por manipular os eventos e ações da tela  de detalhes do país.
//Podendo salvar a data e assim informar que visitou o mesmo
public class DetailActivity extends AppCompatActivity implements View.OnClickListener {

    //Classe de controle responsável pela regra de negócio
    private CtrlFavorites ctrlFavorites;
    private Toolbar toolbar;
    private Calendar calendar;
    private EditText textDate;
    //Varíavel para saber se foi realizado a edição da data. E assim saber se pode salvar ou não
    private boolean isModificou;
    //CheckedTextView somente para mostrar se esse país já foi visitado ou não
    private CheckedTextView checkText;
    //Classe de país com os dados do mesmo
    private Pais pais;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Init();
        LoadValues();
    }

    //Metodo para carregar os componentes com os dados do país
    private void LoadValues() {
        pais = (Pais) getIntent().getSerializableExtra("Pais");
        ((TextView) findViewById(R.id.textViewDetailName)).setText(pais.getName());
        ((TextView) findViewById(R.id.textViewDetailCapital)).setText(pais.getCapital());
        ((TextView) findViewById(R.id.textViewDetailArea)).setText(pais.getArea());
        ((TextView) findViewById(R.id.textViewDetailPopulacao)).setText(pais.getPopulation());
        //Foi utilizado o webview devido o motivo da imagem da bandeira ser no formato svg.
        // Cujo o bitmap não trabalha não podendo assim alimentar o imageview
        ((WebView) findViewById(R.id.webViewDetailFlag)).loadData(LibMobile.GetImageUrl(pais.getFlag()), "text/html", null);
        Pais favorite = ctrlFavorites.GetPais(pais.getAlpha2Code());
        checkText.setChecked(favorite != null);
        if(favorite != null)
            textDate.setText(favorite.getDateVisit());

    }


    //Metodo responsável por iniciar os componentes e configurar os mesmos
    private void Init() {
        toolbar = findViewById(R.id.toolbarDetail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        findViewById(R.id.buttonDate).setOnClickListener(this);
        findViewById(R.id.buttonTime).setOnClickListener(this);
        checkText = findViewById(R.id.checkedDetail);
        textDate = findViewById(R.id.editTextDateTime);
        //Instancica o objeto calendar para utilizar na data
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
        //Cria função para ser passada por parâmetro e ser chamada direto no eventos de data para alimentar o text
        Callable func = new Callable(){
            @Override
            public Object call() throws Exception
            {
                UpdateTextDate();
                return null;
            }
        };

        //Chama o datetimePicker para retornar a data escolhida pelo usuário
        if (v.getId() == R.id.buttonDate)
        {
            LibMobile.DateTimePicker(this, true, calendar,func);
        }
        //Chama o datetimePicker para retornar a hora escolhida pelo usuário
        else if (v.getId() == R.id.buttonTime)
        {
            LibMobile.DateTimePicker(this, false, calendar,func);
        }
    }

    //Atualiza o text do componente de data
    private void UpdateTextDate()
    {
        Date date = calendar.getTime();
        String dateTime = LibMobile.StringDateFormat(date);
        textDate.setText(dateTime);
        isModificou = true;
    }

    private void BackPress()
    {
        //Verifica se foi realizada alguma alteração para ver se pode ser realizado um save no banco
        if(isModificou)
        {
            //Cria função para ser chamado no yes do dialogQuestion para salvar o banco.
            // E também retornar o intent informando que pode atualizar a tela
            Callable callableYes = new Callable() {
                @Override
                public Object call() throws Exception
                {
                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run()
                        {
                            SetSavePais(pais);
                        }
                    });
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("result",true);
                    setResult(Activity.RESULT_OK,returnIntent);
                    finish();
                    return null;
                }
            };
            //Cria função para só fechar a tela caso o usuário não queria salvar
            Callable callableNo = new Callable() {
                @Override
                public Object call() throws Exception
                {
                   finish();
                    return null;
                }
            };
            //Chama o dialog com o questionamento se pode salvar ou não
            LibMobile.AlertMessageQuestion(R.string.MsgSalvar, this, callableYes,callableNo);
            return;
        }
        finish();
    }

    //Realiza o save no banco com o objeto de pais
    private void SetSavePais(Pais pais)
    {
        pais.setDateVisit(textDate.getText().toString());
        ctrlFavorites.SalvarFavorite(pais);
    }

}
