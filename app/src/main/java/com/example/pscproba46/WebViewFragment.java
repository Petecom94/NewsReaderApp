package com.example.pscproba46;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.webkit.WebSettingsCompat;
import androidx.webkit.WebViewFeature;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.SimpleTarget;

import com.bumptech.glide.request.target.SizeReadyCallback;

import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;


import org.jsoup.Jsoup;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;

import java.sql.SQLOutput;
import java.util.ArrayList;

import static com.example.pscproba46.RecyclerViewAdapter.getAllSavedMyIds;
import static com.example.pscproba46.RecyclerViewAdapter.multiarray;

public class WebViewFragment extends Fragment {
    public static WebView webview;
    public static Toolbar toolbar;
    String fileName;
    Bitmap Bitmap;
    ImageView Imageviews;
    String id;
    String imageLink;
    String link;
    String title;
    ProgressBar webviewProgressBar;
    ArrayList kedvelthirek;
    // String savedImagePath = null;
    String finalsource;
    private Intent shareIntent;
    public int userID;
    public  String UserToken;
    public Boolean Logged;


    private static final int FULL_SCREEN_SETTING = View.SYSTEM_UI_FLAG_FULLSCREEN |
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
            View.SYSTEM_UI_FLAG_IMMERSIVE;
    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.webview_layout, container, false);
        toolbar = view.findViewById(R.id.toolbar);
        Menu menu = toolbar.getMenu();
        MenuItem item2 = menu.findItem(R.id.likeweb);
        kedvelthirek= new ArrayList();

        webviewProgressBar= view.findViewById(R.id.webviewProgressBar);

        webviewProgressBar.setVisibility(View.VISIBLE);




                webview = view.findViewById(R.id.webview);
        webviewProgressBar.setProgress( webview.getProgress());
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            CookieManager.getInstance().setAcceptThirdPartyCookies(webview, true);
        } else {
            CookieManager.getInstance().setAcceptCookie(true);
        }

        Html.fromHtml(this.getArguments().getString("title"));
        fileName = this.getArguments().getString("image");
        id = this.getArguments().getString("id");
       title = Html.fromHtml(this.getArguments().getString("title")).toString();
        imageLink = this.getArguments().getString("imageLink");




        UserToken= this.getArguments().getString("bearer");
                userID=this.getArguments().getInt("UserID");
                Logged=this.getArguments().getBoolean("logged");

                if(Logged=true){
                    Toast.makeText(getContext(),"Sikertelen bejelentkezés"+UserToken,Toast.LENGTH_SHORT).show();

                }

        Imageviews = view.findViewById(R.id.imageView);
        Imageviews.setVisibility(View.VISIBLE);

        Boolean likeOrNot = this.getArguments().getBoolean("boolean");
        if (likeOrNot) {

            item2.setIcon(R.drawable.ic_baseline_favorite_24);
        }

        // Enable Javascript
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);

       WebChromeClientCustom mWebChromeClient = new WebChromeClientCustom();
        webview.setWebChromeClient(mWebChromeClient);

        // Force links and redirects to open in the WebView instead of in a browser
        webview.setWebViewClient(new WebViewClient(){

            @Nullable
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                return super.shouldInterceptRequest(view, request);
            }

            @Override
            public void onPageStarted(WebView view, String url, android.graphics.Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                webviewProgressBar.setProgress( webview.getProgress());


            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                webviewProgressBar.setProgress( webview.getProgress());
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onLoadResource(WebView view, String url) {

                webviewProgressBar.setProgress( webview.getProgress());

                try{

                    webview.loadUrl("javascript:(function() { " +
                            "var head = document.getElementById('gp-main-header').style.display='none'; " +
                            "})()");
                    webview.loadUrl("javascript:(function() { " +
                            "var head = document.getElementById('gp-fixed-header-padding').style.display='none'; " +
                            "})()");
                    webview.loadUrl("javascript:(function() { " +
                            "var head = document.getElementById('gp-sidebar').style.display='none'; " +
                            "})()");
                    webview.loadUrl("javascript:(function() { " +
                            "var head = document.getElementById('gp-footer-widgets').style.display='none'; " +
                            "})()");
                    webview.loadUrl("javascript:(function() { " +
                            "var lofasz = document.getElementById('comments').style.display = 'none';"
                            +"})()");


                }catch(Exception e){
e.printStackTrace();

                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                webviewProgressBar.setProgress( webview.getProgress());

              webviewProgressBar.setVisibility(View.GONE);



            }
        });


webview.loadUrl(fileName);





        Glide.with(this).asBitmap().load(imageLink).into(Imageviews);



        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {


            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.likeweb:
                 if(RecyclerViewAdapter.getAllSavedMyIds(getContext()).contains(id)){
                     Toast.makeText(getContext(),"Törölve a kedvencekből",Toast.LENGTH_SHORT).show();
                     kedvelthirek= getAllSavedMyIds(getContext());
                     kedvelthirek.remove(id);
                     RecyclerViewAdapter.saveMyIDs(getContext(),kedvelthirek);
                     Home.adapter.notifyDataSetChanged();
                     item2.setIcon(R.drawable.ic_baseline_favorite_border_24);

                 }else {
                     Toast.makeText(getContext(),"Hozzáadva a kedvencekhez",Toast.LENGTH_SHORT).show();
                     kedvelthirek= getAllSavedMyIds(getContext());
                     kedvelthirek.add(id);

                     RecyclerViewAdapter.saveMyIDs(getContext(),kedvelthirek);
                     Home.adapter.notifyDataSetChanged();
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
        Uri bmpUri = getLocalBitmapUri(Imageviews);
        if (bmpUri != null) {



            Intent sendIntent = new Intent(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Hello!");

// (Optional) Here we're setting the title of the content

            sendIntent.putExtra(Intent.EXTRA_TEXT, title.concat(" "+fileName));
            sendIntent.setType("text/plain");

            sendIntent.putExtra(Intent.EXTRA_TITLE, title);
            sendIntent.setType("text/plain");

// (Optional) Here we're passing a content URI to an image to be displayed
            sendIntent.setData(bmpUri);
            sendIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);


// Show the Sharesheet
            startActivity(Intent.createChooser(sendIntent, title));

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
            File file =  new File(getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), title + System.currentTimeMillis() + ".png");
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(android.graphics.Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            // **Warning:** This will fail for API >= 24, use a FileProvider as shown below instead.
            bmpUri = FileProvider.getUriForFile(getContext(), "com.codepath.fileprovider", file);  // use this version for API >= 24
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }


    //Video full screen mod

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
}}





