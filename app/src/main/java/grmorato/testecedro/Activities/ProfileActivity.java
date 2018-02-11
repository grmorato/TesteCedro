package grmorato.testecedro.Activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import grmorato.testecedro.Controllers.CtrlProfile;
import grmorato.testecedro.Models.UserProfile;
import grmorato.testecedro.R;


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
         UserProfile userProfile = ctrlProfile.GetProfile();
         if(userProfile != null) {
             ((TextView) view.findViewById(R.id.textViewName)).setText(userProfile.getName());
             ((TextView) view.findViewById(R.id.textViewEmail)).setText(userProfile.getEmail());
             WebView webView = view.findViewById(R.id.webViewProfile);
             webView.setWebViewClient(new WebViewClient());
             webView.loadUrl(userProfile.getImage());
         }
    }
}
