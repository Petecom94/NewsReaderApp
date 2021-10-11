package com.example.pscproba46;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Beallitas extends Fragment {

TextView beallit;
Switch beallitGomb;
TextView textViewBejelentkezes,felhtext,jelszoText;
EditText editTextTextPersonName,editTextTextPassword;
ImageButton imagekilep;
Button buttonBejelentkez;
    boolean logged= false;
    public static String felh;
    public  static String jelszo;
    public Boolean siker;
  public Boolean token;
  String username;
  String jwtToken;
  public int userid;
    Bundle bundleToken= new Bundle();

public static TextView rendszertext;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
MainActivity.bar.setVisibility(View.INVISIBLE);
        View view= inflater.inflate(R.layout.beallitas, container, false);

        beallit= view.findViewById(R.id.beallitText);
        beallitGomb=view.findViewById(R.id.beallitGomb);
        textViewBejelentkezes=view.findViewById(R.id.textViewBejelentkezés);
        felhtext=view.findViewById(R.id.felhtext);
        jelszoText=view.findViewById(R.id.jelszoText);
        editTextTextPersonName=view.findViewById(R.id.editTextTextPersonName);
        editTextTextPassword=view.findViewById(R.id.editTextTextPassword);
        imagekilep=view.findViewById(R.id.imagekilep);
        buttonBejelentkez=view.findViewById(R.id.buttonBejelentkez);


        if(logged!=true){
            felhtext.setVisibility(View.GONE);
            felhtext.setVisibility(View.GONE);
            jelszoText.setVisibility(View.GONE);
            editTextTextPassword.setVisibility(View.GONE);
            editTextTextPersonName.setVisibility(View.GONE);
            imagekilep.setVisibility(View.GONE);
            buttonBejelentkez.setVisibility(View.GONE);

        }


      textViewBejelentkezes.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              logged=true;
              felhtext.setVisibility(View.VISIBLE);
              felhtext.setVisibility(View.VISIBLE);
              jelszoText.setVisibility(View.VISIBLE);
              editTextTextPassword.setVisibility(View.VISIBLE);
              editTextTextPersonName.setVisibility(View.VISIBLE);
              imagekilep.setVisibility(View.VISIBLE);
              buttonBejelentkez.setVisibility(View.VISIBLE);
              buttonBejelentkez.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                    //  felh= "info.petronauta@gmail.com";
                     // jelszo="P4991PetroNautaANG";

                      getToken(felh,jelszo);



                  }
              });
          }
      });



                if(MainActivity.sharedPref.getBoolean("Sötétmód",true)){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    beallitGomb.setChecked(true);

                }else{

                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        }




        beallitGomb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                if(MainActivity.sharedPref.getBoolean("Sötétmód",true)){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    MainActivity.editor.putBoolean("Sötétmód",false);
                    MainActivity.editor.apply();
                    getActivity().recreate();

                }else{

                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    MainActivity.editor.putBoolean("Sötétmód",true);
                    MainActivity.editor.apply();
                    getActivity().recreate();
                }


            }
        });
        return view;
    }

public void getToken(String email,String jelszó) {


    Retrofit retrofit = new Retrofit.Builder().baseUrl("https://playstationcommunity.hu/").addConverterFactory(GsonConverterFactory.create()).build();
    GetToken getJwtToken = retrofit.create(GetToken.class);
    //Call<List<Post>> call = jsonPlaceHolderApi.getPost("posts/?page=1&_embed&fbclid=IwAR0VKgpA9hN38Dhajrt4Dk4ba1LOoIrb9Wn2i2sDjg4zkFEP8Kb3vsDu7IQ&include=" + returnArray(RecyclerViewAdapter.getAllSavedMyIds(getContext())));
    Call<GetJwtToken> call = getJwtToken.getToken("?rest_route=/simple-jwt-login/v1/auth&username="+editTextTextPersonName.getText()+"&password="+editTextTextPassword.getText());

call.enqueue(new Callback<GetJwtToken>() {
    @Override
    public void onResponse(Call<GetJwtToken> call, Response<GetJwtToken> response) {
        if(response.isSuccessful()){

siker= response.isSuccessful();
            Toast.makeText(getContext(),"Sikeres bejelentkezés",Toast.LENGTH_SHORT).show();
        jwtToken= response.body().getData().getJwt();

            try {
                DecodedJWT jwt = JWT.decode(jwtToken);
                Claim claimid= jwt.getClaim("id");
                userid =claimid.asInt();
                System.out.println(userid);
                Claim claimUsername= jwt.getClaim("username");
                username = claimUsername.asString();


                textViewBejelentkezes.setText("Bejelentkezve,mint:"+username);

getBearerToken();


            } catch (JWTDecodeException exception){
                //Invalid token
            }
        }else{

            Toast.makeText(getContext(),"Sikertelen bejelentkezés",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailure(Call<GetJwtToken> call, Throwable t) {
        System.out.println("dfsdkjfnsdkjnf");
    }
});


}
public void getBearerToken(){






         Retrofit retrofit = new Retrofit.Builder()

                 .baseUrl("https://playstationcommunity.hu/wp-json/jwt-auth/v1/token/")
                 .addConverterFactory(GsonConverterFactory.create())
                 .build();
         PostBearerApi postBearer= retrofit.create(PostBearerApi.class);
         Call<GetBearerToken> call =postBearer.getBearerToken("?username="+editTextTextPersonName.getText()+"&password="+editTextTextPassword.getText());
         call.enqueue(new Callback<GetBearerToken>() {
             @Override
             public void onResponse(Call<GetBearerToken> call, Response<GetBearerToken> response) {

String token= response.body().token;

                 Toast.makeText(getContext(),"A token"+token,Toast.LENGTH_SHORT).show();



WebViewFragment webViewFragment= new WebViewFragment();

       webViewFragment.UserToken=token;
        webViewFragment.userID=userid;
webViewFragment.Logged=true;
             }

             @Override
             public void onFailure(Call<GetBearerToken> call, Throwable t) {
                 System.out.println("ssss");
             }
         });



}
}
