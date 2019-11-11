package com.bolla.myapplication.models;

import android.widget.ImageView;

import com.google.gson.annotations.SerializedName;

public class MovieModel {
    @SerializedName("url")
    private String movieName;

    public MovieModel(String movieName, String movieImage, String movieDirector, String  year) {
        this.movieName = movieName;
        this.movieImage = movieImage;
        this.movieDirector = movieDirector;
        this.year = year;
    }

    public String getMovieImage() {
        return movieImage;
    }

    public void setMovieImage(String movieImage) {
        this.movieImage = movieImage;
    }

    @SerializedName("download_url")
    private String movieImage;
    @SerializedName("author")
    private String movieDirector;
    @SerializedName("id")
    private String year;

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }



    public String getMovieDirector() {
        return movieDirector;
    }

    public void setMovieDirector(String movieDirector) {
        this.movieDirector = movieDirector;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }


}
