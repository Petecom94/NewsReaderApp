package com.example.pscproba46;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import static com.example.pscproba46.RecyclerViewAdapter.getAllSavedMyIds;

public class WebviewFragmentCikkek extends Fragment {
WebView webviewcikkek;
    Toolbar toolbarcikkek;
ImageView imageViewCikkek;
ArrayList kedveltCikkek;
String imageLinkCikkek;
String CikkekUrl;
Boolean Cikkekigaz;
String cikkekCim;
String cikkekId;

    private static final int FULL_SCREEN_SETTING = View.SYSTEM_UI_FLAG_FULLSCREEN |
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
            View.SYSTEM_UI_FLAG_IMMERSIVE;
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
        cikkekId =this.getArguments().getString("cikkekId");
        ProgressBar webviewProgressBar2 =view.findViewById(R.id.webviewProgressBar2);
        webviewProgressBar2.setVisibility(View.VISIBLE);
        webviewcikkek.saveState(savedInstanceState);

kedveltCikkek= new ArrayList();

        webviewProgressBar2.setProgress( webviewcikkek.getProgress());

        WebSettings webSettings = webviewcikkek.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webviewcikkek.setWebViewClient(new WebViewClient());
        webSettings.setAllowFileAccess(true);
        webSettings.setAppCacheEnabled(true);

        WebChromeClientCustom mWebChromeClient = new WebChromeClientCustom();
        webviewcikkek.setWebChromeClient(mWebChromeClient);

        webviewcikkek.setWebViewClient(new WebViewClient(){
            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
                webviewProgressBar2.setProgress( webviewcikkek.getProgress());

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

            @Override
            public void onPageFinished(WebView view, String url) {

                webviewProgressBar2.setProgress( webviewcikkek.getProgress());






            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                webviewProgressBar2.setProgress( webviewcikkek.getProgress());

                super.onPageStarted(view, url, favicon);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {

                webviewProgressBar2.setProgress( webviewcikkek.getProgress());

                return super.shouldOverrideUrlLoading(view, request);
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
                        if(RecyclerViewAdapterCikkek.getAllSavedMyIds(getContext()).contains(cikkekId)){
                            Toast.makeText(getContext(),"Törölve a kedvencekből",Toast.LENGTH_SHORT).show();
                            kedveltCikkek= RecyclerViewAdapterCikkek.getAllSavedMyIds(getContext());
                            kedveltCikkek.remove(cikkekId);
                            RecyclerViewAdapterCikkek.saveMyIDs(getContext(),kedveltCikkek);
                            Cikkek.adapter.notifyDataSetChanged();
                            item2.setIcon(R.drawable.ic_baseline_favorite_border_24);

                        }else {
                            Toast.makeText(getContext(),"Hozzáadva a kedvencekhez",Toast.LENGTH_SHORT).show();
                            kedveltCikkek= RecyclerViewAdapterCikkek.getAllSavedMyIds(getContext());
                            kedveltCikkek.add(cikkekId);

                            RecyclerViewAdapterCikkek.saveMyIDs(getContext(),kedveltCikkek);
                            Cikkek.adapter.notifyDataSetChanged();
                            item2.setIcon(R.drawable.ic_baseline_favorite_24);
                        }


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
    //    Uri bmpUri = getLocalBitmapUri(imageViewCikkek);




            Intent sendIntent = new Intent(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Hello!");

// (Optional) Here we're setting the title of the content

            sendIntent.putExtra(Intent.EXTRA_TEXT, cikkekCim.concat(" "+CikkekUrl));
            sendIntent.setType("text/plain");

            sendIntent.putExtra(Intent.EXTRA_TITLE, cikkekCim);
            sendIntent.setType("text/plain");

// (Optional) Here we're passing a content URI to an image to be displayed
           /* sendIntent.setData(bmpUri);
            sendIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);*/


// Show the Sharesheet
            startActivity(Intent.createChooser(sendIntent, cikkekCim));

            // Launch sharing dialog for image

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
          //  bmpUri = FileProvider.getUriForFile(getContext(), "com.codepath.fileprovider", file2);  // use this version for API >= 24
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }

    private class WebChromeClientCustom extends WebChromeClient {
        private View mCustomView;
        private WebChromeClient.CustomViewCallback mCustomViewCallback;
        private int mOriginalOrientation;
        private int mOriginalSystemUiVisibility;


        @Override
        public void onHideCustomView() {
            super.onHideCustomView();

            ((FrameLayout) getActivity().getWindow().getDecorView()).removeView(this.mCustomView);
            this.mCustomView = null;
            getActivity().getWindow().getDecorView().setSystemUiVisibility(this.mOriginalSystemUiVisibility);
            getActivity().setRequestedOrientation(this.mOriginalOrientation);
            this.mCustomViewCallback.onCustomViewHidden();
            this.mCustomViewCallback = null;
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        }

        @Override
        public void onShowCustomView(View paramView, WebChromeClient.CustomViewCallback paramCustomViewCallback) {
            if (this.mCustomView != null) {
                onHideCustomView();
                return;
            }
            this.mCustomView = paramView;
            this.mOriginalSystemUiVisibility = getActivity().getWindow().getDecorView().getSystemUiVisibility();
            this.mOriginalOrientation = getActivity().getRequestedOrientation();
            this.mCustomViewCallback = paramCustomViewCallback;
            ((FrameLayout) getActivity().getWindow()
                    .getDecorView())
                    .addView(this.mCustomView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            getActivity().getWindow().getDecorView().setSystemUiVisibility(FULL_SCREEN_SETTING);
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER);
            this.mCustomView.setOnSystemUiVisibilityChangeListener(visibility -> updateControls());
        }
        @Override
        public Bitmap getDefaultVideoPoster() {
            if (mCustomView == null) {
                return null;
            }
            return BitmapFactory.decodeResource(getActivity().getResources(), 2130837573);

        }
        void updateControls() {
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) this.mCustomView.getLayoutParams();
            params.bottomMargin = 0;
            params.topMargin = 0;
            params.leftMargin = 0;
            params.rightMargin = 0;
            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            this.mCustomView.setLayoutParams(params);
            getActivity().getWindow().getDecorView().setSystemUiVisibility(FULL_SCREEN_SETTING);



        }



        @Override
        public void onCloseWindow(WebView window) {
            super.onCloseWindow(window);
        }
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

}

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        webviewcikkek.saveState(outState);

    }

    @Override
    public void onPause() {
        super.onPause();
        //getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
}