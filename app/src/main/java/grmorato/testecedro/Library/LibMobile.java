package grmorato.testecedro.Library;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.Callable;

import javax.net.ssl.HttpsURLConnection;

import grmorato.testecedro.R;

/**
 * Created by grmorato on 07/02/2018.
 */

public class LibMobile {

    private final Context context;
    private ProgressDialog mProgressDialog;

    public LibMobile(Context context) {
        this.context = context;
    }

    public static <C> void StartActivity(Context context, Class<C> classe) {
        Intent intent = new Intent(context, classe);
        context.startActivity(intent);
    }

    public static boolean VerificarConexao(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
        return netInfo != null && netInfo.isAvailable() && netInfo.isConnected();
    }

    public void ShowProgressDialog(boolean semAnimacao) {

        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(context);
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.setCancelable(false);
        }
        if (mProgressDialog.isShowing())
            return;
        mProgressDialog.show();
    }

    public void hideProgressDialog() {

        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }

    }

    public static void AlertMessage(int resourceMessage, final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(resourceMessage);
        Dialog alerta = builder.create();
        alerta.show();
    }

    public static void AlertMessageQuestion(int resourceMessage, final Context context, final Callable funcYes, final Callable funcNo)
    {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        try {
                            if (funcYes != null)
                                funcYes.call();

                        } catch (Exception ex) {
                            Log.d("Error", ex.getMessage());
                        }
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        try {
                            if (funcYes != null)
                                funcNo.call();

                        } catch (Exception ex) {
                            Log.d("Error", ex.getMessage());
                        }
                        break;
                }

            }

        };

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(resourceMessage).setPositiveButton(R.string.Yes,dialogClickListener).setNegativeButton(R.string.No,dialogClickListener);
        Dialog alerta = builder.create();
        alerta.show();

    }

    public static void DateTimePicker(Context context, boolean isDate, final Calendar calendar, final Callable func) {
        if (isDate) {

            DatePickerDialog datePickerDialog = new DatePickerDialog(context, GetDateEventListener(calendar, func), calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        } else {
            TimePickerDialog timePickerDialog = new TimePickerDialog(context, GetTimeEventListener(calendar, func), calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
            timePickerDialog.show();
        }
    }

    //Metodo para criar o evento que retorna a data selecionada
    private static DatePickerDialog.OnDateSetListener GetDateEventListener(final Calendar calendar, final Callable func) {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                try {

                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, monthOfYear);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    func.call();
                } catch (Exception ex) {
                    Log.d("Error", ex.getMessage());
                }

            }
        };
        return dateSetListener;
    }

    //Metodo que retornar a  hora selecionada
    private static TimePickerDialog.OnTimeSetListener GetTimeEventListener(final Calendar calendar, final Callable func) {
        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                try {
                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    calendar.set(Calendar.MINUTE, hourOfDay);
                    func.call();
                } catch (Exception ex) {
                    Log.d("Error", ex.getMessage());
                }
            }
        };
        return timeSetListener;
    }

    public static String GetImageUrl(String url) {
        String data = "<html><head><title>Example</title><meta name=\"viewport\"\"content=\"width=100, initial-scale=0.65 \" /></head>";
        data = data + "<body><center><img width=100 src=\"" + url + "\" /></center></body></html>";
        return data;
    }

    public static String StringDateFormat(Date date) {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", new Locale("pt", "BR"));
        return formato.format(date);
    }

}
