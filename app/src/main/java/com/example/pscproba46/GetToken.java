package com.example.pscproba46;

import java.util.List;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface GetToken {


    @POST
    Call <GetJwtToken> getToken (@Url String url);


    @GET
    Call<GetJwtToken> getJwtTokenDetails(@Url String url);
}
