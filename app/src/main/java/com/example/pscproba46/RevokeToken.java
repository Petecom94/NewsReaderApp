package com.example.pscproba46;

import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface RevokeToken {

    @POST
    Call<GetRevoke> getRevoke(@Url String url);
}
