package com.example.pscproba46;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.sql.SQLOutput;

public class WebViewFragment extends Fragment {
    public static  WebView webview;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.webview_layout,container,false);

       webview = view.findViewById(R.id.webview);

        String fileName = this.getArguments().getString("image");

       webview.loadUrl(fileName);

        // Enable Javascript
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Force links and redirects to open in the WebView instead of in a browser
        webview.setWebViewClient(new WebViewClient());
        return view;
    }
}
