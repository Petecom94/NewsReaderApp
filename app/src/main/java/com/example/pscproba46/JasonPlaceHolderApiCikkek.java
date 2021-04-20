package com.example.pscproba46;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface JasonPlaceHolderApiCikkek {
//@GET("posts/")




    //@GET("posts/?page=")
    @GET
    Call<List<CikkekAdat>> getPostCikkek (@Url String url);

}
