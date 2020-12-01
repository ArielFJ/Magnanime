package com.magnanym.magnanime.ui.anime;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.tabs.TabLayout;
import com.magnanym.magnanime.R;
import com.magnanym.magnanime.Utils;
import com.magnanym.magnanime.adapters.AnimeCardAdapter;
import com.magnanym.magnanime.adapters.BannerPagerAdapter;
import com.magnanym.magnanime.api.AnimeCalls;
import com.magnanym.magnanime.interfaces.GetAnime;
import com.magnanym.magnanime.models.anime.AnimeBanner;
import com.magnanym.magnanime.models.anime.AnimeCard;
import com.magnanym.magnanime.ui.GridListActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class AnimeFragment extends Fragment {

    private AnimeCalls animeCalls;

    private ViewPager viewPager;
    private BannerPagerAdapter pagerAdapter;

    private Context context;
    private TabLayout tabLayout;
    private TextView arielfj;
    private MaterialButton morePopThis, morePopNext, moreAllTime, moreLastUpdated;

    private RecyclerView thisSeasonRecycler, nextSeasonRecycler, allTimeRecycler, latestRecycler;
    private AnimeCardAdapter animeCardAdapter;

    private AnimeViewModel viewModel;
    int maxItemsNumber = 10;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_anime, container, false);

        context = container.getContext();
        arielfj = root.findViewById(R.id.arielfj);
        arielfj.setText(Html.fromHtml("<a href=\"https://github.com/ArielFJ/\">@ArielFJ</a>"));
        arielfj.setLinksClickable(true);
        arielfj.setMovementMethod(LinkMovementMethod.getInstance());



        // Layouts
        viewPager = root.findViewById(R.id.viewPager);
        tabLayout = root.findViewById(R.id.tab_indicator);

        thisSeasonRecycler = root.findViewById(R.id.thisSeasonRecycler);
        nextSeasonRecycler = root.findViewById(R.id.nextSeasonRecycler);
        allTimeRecycler = root.findViewById(R.id.allTimeRecycler);
        latestRecycler = root.findViewById(R.id.latestRecycler);

        // Buttons
        morePopThis = root.findViewById(R.id.morePopThis);
        morePopNext = root.findViewById(R.id.morePopNext);
        moreAllTime = root.findViewById(R.id.moreAllTime);
        moreLastUpdated = root.findViewById(R.id.moreLatest);
        setButtonsMoreOptions();

        // API Calls
        animeCalls = AnimeCalls.getInstance();

        // View Model
        viewModel = new ViewModelProvider(this).get(AnimeViewModel.class);

        // View Model Listener
        viewModel.getAnimeBanner().observe(getViewLifecycleOwner(), animeList ->
            setBannerPagerAdapter(root, animeList)
        );

        viewModel.getThisSeasonPop().observe(getViewLifecycleOwner(), animeCards ->
                setAnimeAdapters(thisSeasonRecycler ,animeCards)
        );

        viewModel.getNextSeasonPop().observe(getViewLifecycleOwner(), animeCards ->
            setAnimeAdapters(nextSeasonRecycler, animeCards)
        );

        viewModel.getAllTimePop().observe(getViewLifecycleOwner(), animeCards ->
                setAnimeAdapters(allTimeRecycler, animeCards)
        );

        viewModel.getLastUpdated().observe(getViewLifecycleOwner(), animeCards ->
                setAnimeAdapters(latestRecycler, animeCards)
        );

        animeCalls.getTrendingAnime(viewModel.getAnimeBanner(), 1, 15);
        animeCalls.getPopularAnimeThisSeason(viewModel.getThisSeasonPop(), 1, 10, false);
        animeCalls.getPopularAnimeNextSeason(viewModel.getNextSeasonPop(), 1, 10, false);
        animeCalls.getPopularAnimeAllTime(viewModel.getAllTimePop(), 1, 10, false);
        animeCalls.getLatestAnime(viewModel.getLastUpdated(), 1, 10, false);



        return root;
    }


    private void setBannerPagerAdapter(View root, List<AnimeBanner> animeBannerList){
        viewPager = root.findViewById(R.id.viewPager);

        List<AnimeBanner> animes = new ArrayList<>();
        int index = 0;
        for(AnimeBanner ab : animeBannerList){
            if(ab.getBannerImage() != null){
                animes.add(ab);
                index++;
            }
            if(index >= maxItemsNumber)
                break;
        }

        pagerAdapter = new BannerPagerAdapter(context, animes);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        Timer sliderTimer = new Timer();
        sliderTimer.scheduleAtFixedRate(new AutoSlider(), 4000, 6000);
        tabLayout.setupWithViewPager(viewPager, true);
    }

    private void setAnimeAdapters(RecyclerView recycler, List<AnimeCard> animeCards) {
        animeCardAdapter = new AnimeCardAdapter(context, animeCards);
        recycler.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
        recycler.setAdapter(animeCardAdapter);
    }

    private void setButtonsMoreOptions() {

        morePopThis.setOnClickListener(v -> {
            Intent intent = new Intent(context, GridListActivity.class);
            intent.putExtra("Title", getString(R.string.popularThis));
            intent.putExtra("GetType", GetAnime.popularThisSeason);
            startActivity(intent);
        });

        morePopNext.setOnClickListener(v -> {
            Intent intent = new Intent(context, GridListActivity.class);
            intent.putExtra("Title", getString(R.string.popularNext));
            intent.putExtra("GetType", GetAnime.popularNextSeason);
            startActivity(intent);
        });

        moreAllTime.setOnClickListener(v -> {
            Intent intent = new Intent(context, GridListActivity.class);
            intent.putExtra("Title", getString(R.string.allTime));
            intent.putExtra("GetType", GetAnime.allTime);
            startActivity(intent);
        });

        moreLastUpdated.setOnClickListener(v -> {
            Intent intent = new Intent(context, GridListActivity.class);
            intent.putExtra("Title", getString(R.string.latest));
            intent.putExtra("GetType", GetAnime.latest);
            startActivity(intent);
        });
    }

    class AutoSlider extends TimerTask {

        @Override
        public void run() {
            if(getActivity() != null){
                getActivity().runOnUiThread(() -> {
                    if(viewPager.getCurrentItem() < maxItemsNumber - 1 ){
                        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                    } else {
                        viewPager.setCurrentItem(0);
                    }
                });
            }
        }
    }

}