package com.magnanym.magnanime.api;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.magnanym.magnanime.Utils;
import com.magnanym.magnanime.interfaces.AnimeAPI;
import com.magnanym.magnanime.models.anime.*;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AnimeCalls {

    Retrofit retrofit;
    AnimeAPI animeAPI;

    private static AnimeCalls instance;

    private AnimeCalls() {
        retrofit = new Retrofit.Builder()
                .baseUrl(Utils.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        animeAPI = retrofit.create(AnimeAPI.class);
    }

    public static AnimeCalls getInstance() {
        if(instance == null)
            instance = new AnimeCalls();
        return instance;
    }


    public void getTrendingAnime(MutableLiveData<List<AnimeBanner>> animeList, int page, int perPage){

        Call<List<AnimeBanner>> call = animeAPI.getTrending(page, perPage);
        call.enqueue(new Callback<List<AnimeBanner>>() {
            @Override
            public void onResponse(Call<List<AnimeBanner>> call, Response<List<AnimeBanner>> response) {
                try{
                    if(response.isSuccessful()){
                        //animeList.setValue(response.body());
                        checkBannerAllow18(response.body(), animeList);
                    }

                }catch(Exception e){
                    Log.e("ERROR", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<List<AnimeBanner>> call, Throwable t) {
                Log.e("FAIL",t.getMessage());

            }
        });
    }

    public void getAnimeById(int id, MutableLiveData<AnimeExtended> animeExtended) {
        Call<AnimeExtended> call = animeAPI.getAnimeById(id);
        call.enqueue(new Callback<AnimeExtended>() {
            @Override
            public void onResponse(Call<AnimeExtended> call, Response<AnimeExtended> response) {
                try{
                    if(response.isSuccessful()) {
                        animeExtended.setValue(response.body());
                    }
                } catch(Exception e) {
                    Log.e("ERROR", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<AnimeExtended> call, Throwable t) {
                Log.e("FAIL", t.getMessage());
            }
        });
    }

    public void getPopularAnimeThisSeason(MutableLiveData<List<AnimeCard>> animeCards, int page, int perPage, boolean append) {
        Call<List<AnimeCard>> call = animeAPI.getPopularAnimesThisSeason(page, perPage);

        call.enqueue(new Callback<List<AnimeCard>>() {
            @Override
            public void onResponse(Call<List<AnimeCard>> call, Response<List<AnimeCard>> response) {
                try{
                    if(response.isSuccessful())
                        checkCardsAllow18(response.body(), animeCards, append);
                }
                catch (Exception e){
                    Log.e("ERROR", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<List<AnimeCard>> call, Throwable t) {
                Log.e("FAIL", t.getMessage());
            }
        });
    }

    public void getPopularAnimeNextSeason(MutableLiveData<List<AnimeCard>> animeCards, int page, int perPage, boolean append) {
        Call<List<AnimeCard>> call = animeAPI.getPopularAnimesNextSeason(page, perPage);

        call.enqueue(new Callback<List<AnimeCard>>() {
            @Override
            public void onResponse(Call<List<AnimeCard>> call, Response<List<AnimeCard>> response) {
                try{
                    if(response.isSuccessful())
                        checkCardsAllow18(response.body(), animeCards, append);
                }
                catch (Exception e){
                    Log.e("ERROR", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<List<AnimeCard>> call, Throwable t) {
                Log.e("FAIL", t.getMessage());
            }
        });
    }

    public void getPopularAnimeAllTime(MutableLiveData<List<AnimeCard>> animeCards, int page, int perPage, boolean append) {
        Call<List<AnimeCard>> call = animeAPI.getAllTimePopularAnimes(page, perPage);

        call.enqueue(new Callback<List<AnimeCard>>() {
            @Override
            public void onResponse(Call<List<AnimeCard>> call, Response<List<AnimeCard>> response) {
                try{
                    if(response.isSuccessful())
                        checkCardsAllow18(response.body(), animeCards, append);
                }
                catch (Exception e){
                    Log.e("ERROR", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<List<AnimeCard>> call, Throwable t) {
                Log.e("FAIL", t.getMessage());
            }
        });
    }

    public void getLatestAnime(MutableLiveData<List<AnimeCard>> animeCards, int page, int perPage, boolean append) {
        Call<List<AnimeCard>> call = animeAPI.getLatestAnimes(page, perPage);

        call.enqueue(new Callback<List<AnimeCard>>() {
            @Override
            public void onResponse(Call<List<AnimeCard>> call, Response<List<AnimeCard>> response) {
                try{
                    if(response.isSuccessful())
                        checkCardsAllow18(response.body(), animeCards, append);
                }
                catch (Exception e){
                    Log.e("ERROR", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<List<AnimeCard>> call, Throwable t) {
                Log.e("FAIL", t.getMessage());
            }
        });
    }

    public void getAnimesById(MutableLiveData<List<AnimeCard>> animeCards, List<Integer> ids) {
        Call<List<AnimeCard>> call = animeAPI.getAnimesById(ids);
        Log.d("ANIME", "DENTRO");
        call.enqueue(new Callback<List<AnimeCard>>() {
            @Override
            public void onResponse(Call<List<AnimeCard>> call, Response<List<AnimeCard>> response) {
                try{
                    if(response.isSuccessful()){
                        animeCards.setValue(response.body());
                    }
                    else{
                        Log.e("NOT SUCCESS", "Hubo un error");
                    }
                }catch(Exception e) {
                    Log.e("ERROR", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<List<AnimeCard>> call, Throwable t) {
                Log.e("FAIL", t.getMessage());
            }
        });
    }

    public void searchAnimeByName(MutableLiveData<List<AnimeCard>> animeCards, int page, int perPage, String search) {
        Call<List<AnimeCard>> call = animeAPI.search(page, perPage, search);

        call.enqueue(new Callback<List<AnimeCard>>() {
            @Override
            public void onResponse(Call<List<AnimeCard>> call, Response<List<AnimeCard>> response) {
                try{
                    if(response.isSuccessful()){
                        checkCardsAllow18(response.body(), animeCards, false);
                    }
                    else
                        Log.e("Not Success", response.message());
                }
                catch (Exception e){
                    Log.e("ERROR", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<List<AnimeCard>> call, Throwable t) {
                Log.e("FAIL", t.getMessage());
            }
        });
    }

    private void checkCardsAllow18(List<AnimeCard> response, MutableLiveData<List<AnimeCard>> animeCards, boolean append) {
        if(append){
            List<AnimeCard> newAnimeCards = animeCards.getValue();
            newAnimeCards.addAll(response);
            if(Utils.allowPlus18){
                animeCards.setValue(newAnimeCards);
            }
            else{
                List<AnimeCard> newList = new ArrayList<>();

                for(AnimeCard a : newAnimeCards){
                    if(!a.isAdult())
                        newList.add(a);
                }

                animeCards.setValue(newList);
            }
        }else{
            Utils.searchListSize = response.size();

            if(Utils.allowPlus18)
                animeCards.setValue(response);
            else{
                List<AnimeCard> newList = new ArrayList<>();

                for(AnimeCard a : response){
                    if(!a.isAdult())
                        newList.add(a);
                }
                animeCards.setValue(newList);
            }
        }
    }

    private void checkBannerAllow18(List<AnimeBanner> response, MutableLiveData<List<AnimeBanner>> animeBanner) {
        if(Utils.allowPlus18)
            animeBanner.setValue(response);
        else{
            List<AnimeBanner> newList = new ArrayList<>();

            for(AnimeBanner a : response){
                if(!a.isAdult())
                    newList.add(a);
            }
            animeBanner.setValue(newList);
        }
    }

}
