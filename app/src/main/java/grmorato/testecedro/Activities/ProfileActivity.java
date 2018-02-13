package grmorato.testecedro.Activities;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import grmorato.testecedro.Controllers.CtrlProfile;
import grmorato.testecedro.Library.LibFacebookService;
import grmorato.testecedro.Library.LibMobile;
import grmorato.testecedro.Library.LibServiceRest;
import grmorato.testecedro.Models.UserProfile;
import grmorato.testecedro.R;


//Fragment responsável por mostrar os dados do facebook do usuário salvo no banco de dados
public class ProfileActivity extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.activity_profile, container, false);
        LoadValues(view);
        return view;
    }

    private void LoadValues(View view)
    {
         CtrlProfile ctrlProfile = new CtrlProfile(getContext());
         final UserProfile userProfile = ctrlProfile.GetProfile();
         if(userProfile != null) {
             ((TextView) view.findViewById(R.id.textViewName)).setText(userProfile.getName());
             ((TextView) view.findViewById(R.id.textViewEmail)).setText(userProfile.getEmail());
             final ImageView imageView = view.findViewById(R.id.imageViewProfile);
             final Bitmap bit = LibMobile.ConvertByteArrayToBitmap(userProfile.getImage());
             imageView.setImageBitmap(bit);

         }
    }
}
