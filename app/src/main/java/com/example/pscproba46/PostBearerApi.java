package com.example.pscproba46;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface PostBearerApi {

    @POST
    Call<GetBearerToken> getBearerToken (@Url String url);

}
