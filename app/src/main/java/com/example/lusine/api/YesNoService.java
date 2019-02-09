package com.example.lusine.api;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface YesNoService {

    @GET(".")
    Single<YesOrNO> getAnswer();

}
