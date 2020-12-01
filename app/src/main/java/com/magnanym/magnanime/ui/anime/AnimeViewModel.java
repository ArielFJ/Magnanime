package com.magnanym.magnanime.ui.anime;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.magnanym.magnanime.models.anime.AnimeBanner;
import com.magnanym.magnanime.models.anime.AnimeCard;
import com.magnanym.magnanime.models.anime.AnimeExtended;

import java.util.List;

public class AnimeViewModel extends ViewModel {

    private MutableLiveData<List<AnimeBanner>> animeBanner;
    private MutableLiveData<AnimeExtended> animeExtended;
    private MutableLiveData<List<AnimeCard>> thisSeasonPop, nextSeasonPop, allTimePop, lastUpdated, animeList;

    public AnimeViewModel() {
        animeBanner = new MutableLiveData<>();
        animeList = new MutableLiveData<>();
        animeExtended = new MutableLiveData<>();
        thisSeasonPop = new MutableLiveData<>();
        nextSeasonPop = new MutableLiveData<>();
        allTimePop = new MutableLiveData<>();
        lastUpdated = new MutableLiveData<>();
    }

    public MutableLiveData<List<AnimeBanner>> getAnimeBanner() {
        return animeBanner;
    }

    public MutableLiveData<List<AnimeCard>> getAnimeList() {
        return animeList;
    }

    public MutableLiveData<AnimeExtended> getAnimeExtended() {
        return animeExtended;
    }

    public MutableLiveData<List<AnimeCard>> getThisSeasonPop() {
        return thisSeasonPop;
    }

    public MutableLiveData<List<AnimeCard>> getNextSeasonPop() {
        return nextSeasonPop;
    }

    public MutableLiveData<List<AnimeCard>> getAllTimePop() {
        return allTimePop;
    }

    public MutableLiveData<List<AnimeCard>> getLastUpdated() {
        return lastUpdated;
    }
}