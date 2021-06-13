package com.example.pscproba46;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class WebviewFragmentCikkek extends Fragment {
WebView webviewcikkek;
    Toolbar toolbarcikkek;
ImageView imageViewCikkek;

String imageLinkCikkek;
String CikkekUrl;
Boolean Cikkekigaz;
String cikkekCim;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.webview_layoutcikkek, container, false);

        webviewcikkek= view.findViewById(R.id.webviewCikkek);
        toolbarcikkek=view.findViewById(R.id.toolbarCikkek)   ;
        imageViewCikkek=view.findViewById(R.id.imageViewCikkek);
        Menu menu2 = toolbarcikkek.getMenu();
        MenuItem item2 = menu2.findItem(R.id.likeweb);
        imageLinkCikkek=this.getArguments().getString("imageLinkCikkek");
        CikkekUrl=this.getArguments().getString("imageCikkek");
        Cikkekigaz =this.getArguments().getBoolean("booleanCikkek");
        cikkekCim=this.getArguments().getString("titleCikkek");






        WebSettings webSettings = webviewcikkek.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webviewcikkek.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {


                webviewcikkek.loadUrl("javascript:(function() { " +
                        "var head = document.getElementById('gp-main-header').style.display='none'; " +
                        "})()");
                webviewcikkek.loadUrl("javascript:(function() { " +
                        "var head = document.getElementById('gp-fixed-header-padding').style.display='none'; " +
                        "})()");
                webviewcikkek.loadUrl("javascript:(function() { " +
                        "var head = document.getElementById('gp-sidebar').style.display='none'; " +
                        "})()");
                webviewcikkek.loadUrl("javascript:(function() { " +
                        "var head = document.getElementById('gp-footer-widgets').style.display='none'; " +
                        "})()");
                webviewcikkek.loadUrl("javascript:(function() { " +
                        "var lofasz = document.getElementById('comments').style.display = 'none';"
                        +"})()");




            }
        });

        webviewcikkek.loadUrl(CikkekUrl);


        Glide.with(this).asBitmap().load(imageLinkCikkek).into(imageViewCikkek);

        if (Cikkekigaz) {

            item2.setIcon(R.drawable.ic_baseline_favorite_24);
        }

        toolbarcikkek.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.likeweb:


                        break;
                    case R.id.shareweb:
                    default:
                        onShareItem();

                        break;

                }


                return false;
            }


        });
        return view;
    }

    public void onShareItem() {
        // Get access to bitmap image from view

        // Get access to the URI for the bitmap
        Uri bmpUri = getLocalBitmapUri(imageViewCikkek);
        if (bmpUri != null) {



            Intent sendIntent = new Intent(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Hello!");

// (Optional) Here we're setting the title of the content

            sendIntent.putExtra(Intent.EXTRA_TEXT, cikkekCim.concat(" "+CikkekUrl));
            sendIntent.setType("text/plain");

            sendIntent.putExtra(Intent.EXTRA_TITLE, cikkekCim);
            sendIntent.setType("text/plain");

// (Optional) Here we're passing a content URI to an image to be displayed
            sendIntent.setData(bmpUri);
            sendIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);


// Show the Sharesheet
            startActivity(Intent.createChooser(sendIntent, cikkekCim));

            // Launch sharing dialog for image

        } else {
            // ...sharing failed, handle error
        }
    }


    public Uri getLocalBitmapUri(ImageView imageView) {
        // Extract Bitmap from ImageView drawable
        Drawable drawable = imageView.getDrawable();
        Bitmap bmp = null;
        if (drawable instanceof BitmapDrawable){
            bmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        } else {
            return null;
        }
        // Store image to default external storage directory
        Uri bmpUri = null;
        try {
            // Use methods on Context to access package-specific directories on external storage.
            // This way, you don't need to request external read/write permission.
            // See https://youtu.be/5xVh-7ywKpE?t=25m25s
            File file2 =  new File(getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), cikkekCim + System.currentTimeMillis() + ".png");
            FileOutputStream out = new FileOutputStream(file2);
            bmp.compress(android.graphics.Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            // **Warning:** This will fail for API >= 24, use a FileProvider as shown below instead.
            bmpUri = FileProvider.getUriForFile(getContext(), "com.codepath.fileprovider", file2);  // use this version for API >= 24
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }
}