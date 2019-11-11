package com.bolla.myapplication.webservice;


import com.bolla.myapplication.models.MovieModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIClient {
    @GET("list")
    Call<List<MovieModel>> getPosts();
}
