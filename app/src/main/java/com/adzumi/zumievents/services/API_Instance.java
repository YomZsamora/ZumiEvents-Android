package com.adzumi.zumievents.services;

import com.adzumi.zumievents.models.Events;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface API_Instance {
    @GET("/events/search/")
    Call<Events> getEvents(@Query("location.address") String eventLocation, @Query("token") String myToken);
}
