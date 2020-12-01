package com.magnanym.magnanime.interfaces;

import com.magnanym.magnanime.models.User;
import com.magnanym.magnanime.models.UserCreateModel;
import com.magnanym.magnanime.models.anime.*;
import com.magnanym.magnanime.models.post.CategoryReadModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AnimeAPI {

    int UPDATE_LIKE = 1;
    int NO_UPDATE_LIKE = 0;

    // USERS

    @GET("users/login/{username}/{password}")
    Call<User> logUser(@Path("username") String username, @Path("password") String password);

    @POST("users/")
    Call<User> createUser(@Body UserCreateModel user);

    @PATCH("users/{userId}")
    Call<User> updateUser(@Path("userId") String userId, @Body User userUpdated);

    @PATCH("users/like/{userId}/{animeId}/{update}")
    Call<User> likeAnime(@Path("userId") String userId, @Path("animeId") int animeId,
                         @Path("update") int update);

    @DELETE("users/{id}")
    Call<Void> deleteUser(@Path("id") String id);


    // ANIMES

    @GET("animes/trending")
    Call<List<AnimeBanner>> getTrending(@Query("page")int page, @Query("perPage") int perPage);

    @GET("animes/{id}")
    Call<AnimeExtended> getAnimeById(@Path("id") int id);

    @GET("animes/popular/now")
    Call<List<AnimeCard>> getPopularAnimesThisSeason(@Query("page")int page, @Query("perPage") int perPage);

    @GET("animes/popular/next")
    Call<List<AnimeCard>> getPopularAnimesNextSeason(@Query("page")int page, @Query("perPage") int perPage);

    @GET("animes/popular/all")
    Call<List<AnimeCard>> getAllTimePopularAnimes(@Query("page")int page, @Query("perPage") int perPage);

    @GET("animes/latest")
    Call<List<AnimeCard>> getLatestAnimes(@Query("page")int page, @Query("perPage") int perPage);

    @POST("animes/animeList")
    Call<List<AnimeCard>> getAnimesById(@Body List<Integer> ids);

    @GET("animes/search")
    Call<List<AnimeCard>> search(@Query("page") int page, @Query("perPage") int perPage, @Query("search") String search);

    @POST("animes/category")
    Call<List<AnimeCard>> getByCategory(@Body CategoryReadModel catModel);


}
