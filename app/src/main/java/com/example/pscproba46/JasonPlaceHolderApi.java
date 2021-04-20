package com.example.pscproba46;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface JasonPlaceHolderApi {
//@GET("posts/")




    //@GET("posts/?page=")
    @GET
    Call<List<Post>> getPost (@Url String url);

}
