package com.example.githubnotification;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface GitHubUserEndPoints {

    @GET("/repos/RvSingh001/AlarmMangerTest")
    Call<Model> getUser();
}




