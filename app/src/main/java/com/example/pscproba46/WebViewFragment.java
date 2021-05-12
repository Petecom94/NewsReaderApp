package com.example.pscproba46;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Html;
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
import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

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


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;

import java.sql.SQLOutput;

public class WebViewFragment extends Fragment {
    public static WebView webview;
    public static Toolbar toolbar;
    String fileName;
    Bitmap Bitmap;
    ImageView Imageviews;
    String imageLink;
    String link;
    String title;
    // String savedImagePath = null;
    String finalsource;
    private Intent shareIntent;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.webview_layout, container, false);
        toolbar = view.findViewById(R.id.toolbar);
        Menu menu = toolbar.getMenu();
        MenuItem item2 = menu.findItem(R.id.likeweb);
        webview = view.findViewById(R.id.webview);

        Html.fromHtml(this.getArguments().getString("title"));
        fileName = this.getArguments().getString("image");
        String id = this.getArguments().getString("id");
       title = Html.fromHtml(this.getArguments().getString("title")).toString();
        imageLink = this.getArguments().getString("imageLink");
        webview.loadUrl(fileName);
        Imageviews = view.findViewById(R.id.imageView);
        Imageviews.setVisibility(View.VISIBLE);

        Boolean likeOrNot = this.getArguments().getBoolean("boolean");
        if (likeOrNot) {

            item2.setIcon(R.drawable.ic_baseline_favorite_24);
        }

        // Enable Javascript
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Force links and redirects to open in the WebView instead of in a browser
        webview.setWebViewClient(new WebViewClient());

        Glide.with(this).asBitmap().load(imageLink).into(Imageviews);



        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
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
}





