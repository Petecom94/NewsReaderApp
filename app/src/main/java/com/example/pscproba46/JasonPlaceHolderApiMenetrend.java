package com.example.pscproba46;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface JasonPlaceHolderApiMenetrend {
//@GET("posts/")




    //@GET("posts/?page=")
    @GET
    Call<MenetrendAdat> getPosts (@Url String url);

}
