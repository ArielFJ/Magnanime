package com.magnanym.magnanime.ui.anime;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.material.button.MaterialButton;
import com.magnanym.magnanime.MainActivity;
import com.magnanym.magnanime.R;
import com.magnanym.magnanime.Utils;
import com.magnanym.magnanime.adapters.CharactersAdapter;
import com.magnanym.magnanime.adapters.StudiosAdapter;
import com.magnanym.magnanime.api.AnimeCalls;
import com.magnanym.magnanime.api.UserCalls;
import com.magnanym.magnanime.interfaces.AnimeAPI;
import com.magnanym.magnanime.models.User;
import com.magnanym.magnanime.models.anime.AnimeExtended;
import com.magnanym.magnanime.models.stats.Review;
import com.magnanym.magnanime.ui.DescriptionDialog;
import com.magnanym.magnanime.ui.LinksDialog;

import java.util.Random;

public class AnimeExtendedActivity extends AppCompatActivity {

    private AnimeCalls animeCalls;
    private UserCalls userCalls;

    AnimeViewModel viewModel;

    ImageView bannerImage, coverImage;
    TextView title, type, season, episodes, duration, origin, adultTv, scoreAvg, reviewsLabel;
    FlexboxLayout reviewLayout;

    MaterialButton descriptionBtn, likeBtn, linksBtn;

    RecyclerView studiosRecycler, charsRecycler;

    ConstraintLayout constraintLayout;

    AnimeExtended animeExtended;

    boolean stayFav;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime_extended);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView toolbarTitle = findViewById(R.id.toolbarTitle);

        viewModel = new ViewModelProvider(this).get(AnimeViewModel.class);

        // Anime extended images
        bannerImage = findViewById(R.id.bannerImage);
        coverImage = findViewById(R.id.coverImage);

        // Anime extended text views
        title = findViewById(R.id.animeTitle);
        type = findViewById(R.id.animeType);
        season = findViewById(R.id.season);
        episodes = findViewById(R.id.episodes);
        duration = findViewById(R.id.duration);
        origin = findViewById(R.id.origin);
        adultTv = findViewById(R.id.adultTv);
        scoreAvg = findViewById(R.id.scoreAvg);
        reviewsLabel = findViewById(R.id.reviewsLabel);

        // Anime extended buttons
        descriptionBtn = findViewById(R.id.btnDescription);
        likeBtn = findViewById(R.id.likeBtn);
        linksBtn = findViewById(R.id.btnLinks);

        // Anime extended layouts
        constraintLayout = findViewById(R.id.constraint);
        reviewLayout = findViewById(R.id.flexbox);
        studiosRecycler = findViewById(R.id.studiosRecycler);
        charsRecycler = findViewById(R.id.charsRecycler);

        animeCalls = AnimeCalls.getInstance();
        userCalls = UserCalls.getInstance(this);

        viewModel.getAnimeExtended().observe(this, animeExtended -> {
            this.animeExtended = animeExtended;
            setAnimeExtendedView();
            toolbarTitle.setText(animeExtended.getTitle().getEnglish());
            toolbarTitle.setSelected(true);
        });

        animeCalls.getAnimeById(getIntent().getIntExtra("AnimeId", 1), viewModel.getAnimeExtended());
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setAnimeExtendedView() {
        title.setText(animeExtended.getTitle().getRomaji());
        type.setText(animeExtended.getType());
        season.setText(animeExtended.getSeason() + ", " + animeExtended.getSeasonYear());
        episodes.setText(animeExtended.getEpisodes() + "");
        duration.setText(animeExtended.getDuration() + " min");
        origin.setText(animeExtended.getCountryOfOrigin());
        adultTv.setVisibility(animeExtended.isAdult() ? View.VISIBLE : View.INVISIBLE);
        scoreAvg.setText("SCORE AVERAGE: " + animeExtended.getAverageScoreStats() + "/100");

        if(animeExtended.getBannerImage() != null){
            Glide.with(getApplicationContext())
                .load(animeExtended.getBannerImage())
                .into(bannerImage);
        }

        bannerImage.setOnClickListener(v -> {
            if(animeExtended.getTrailer() != null && animeExtended.getTrailer().getSite().equals("youtube")){
                Uri uri = Uri.parse("https://www.youtube.com/watch?v=" + animeExtended.getTrailer().getId());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            } else{
                Toast.makeText(getApplicationContext(), "Trailer unavailable.", Toast.LENGTH_SHORT).show();
            }
        });

        Glide.with(getApplicationContext())
                .load(animeExtended.getCoverImage().getMedium())
                .into(coverImage);

        likeBtn.setIcon(Utils.favs.contains(animeExtended.getId()) &&
                Utils.userId != null ?
                getDrawable(R.drawable.ic_fav) :
                getDrawable(R.drawable.ic_no_fav));

        likeBtn.setOnClickListener(v -> {
            if(Utils.userId != null && !Utils.userId.equals("")){
                if(Utils.favs.contains(animeExtended.getId())) {
                    likeBtn.setIcon(getDrawable(R.drawable.ic_no_fav));
                    stayFav = false;
                    Utils.favs.remove(Integer.valueOf(animeExtended.getId()));
                }
                else {
                    likeBtn.setIcon(getDrawable(R.drawable.ic_fav));
                    stayFav = true;
                    Utils.favs.add(animeExtended.getId());
                }
            }
            else{
                Toast.makeText(this, "You have to be logged to like an anime", Toast.LENGTH_LONG).show();
            }
        });

        descriptionBtn.setOnClickListener(v -> {
            DescriptionDialog dialog = new DescriptionDialog(animeExtended);
            dialog.show(getSupportFragmentManager(), "DESCRIPTION");
        });

        linksBtn.setOnClickListener(v -> {
            LinksDialog linksDialog = new LinksDialog(animeExtended);
            linksDialog.show(getSupportFragmentManager(), "LINKS");
        });

        setReviewsLayout(animeExtended);

        if(animeExtended.getStudios().getNodes().size() == 0){
            findViewById(R.id.studioLabel).setVisibility(View.INVISIBLE);
        }

        if(animeExtended.getCharacters().getNodes().size() == 0) {
            findViewById(R.id.charsLabel).setVisibility(View.INVISIBLE);
        }

        setRecyclerViews(animeExtended);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setReviewsLayout(AnimeExtended animeExtended) {
        if(animeExtended.getReviews().getNodes().size() == 0){
            reviewsLabel.setVisibility(View.INVISIBLE);
            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(constraintLayout);
            constraintSet.connect(R.id.btnLinks, ConstraintSet.TOP, R.id.scoreAvg, ConstraintSet.BOTTOM);
            constraintSet.applyTo(constraintLayout);
        }

        for(Review review : animeExtended.getReviews().getNodes()){
            TextView tvReview;
            tvReview = new TextView(this);
//            tvReview.setLayoutParams(params);
            tvReview.setBackground(ContextCompat.getDrawable(this, Utils.tagResourcesId[new Random()
                    .nextInt(Utils.tagResourcesId.length)]));
            tvReview.setTextColor(getResources().getColor(R.color.white));
            tvReview.setPadding((int) getResources().getDimension(R.dimen.common_padding_default),
                    (int) getResources().getDimension(R.dimen.common_padding_micro),
                    (int) getResources().getDimension(R.dimen.common_padding_default),
                    (int) getResources().getDimension(R.dimen.common_padding_micro));
            tvReview.setTextSize(12);
            tvReview.setGravity(View.TEXT_ALIGNMENT_CENTER);

            // Show item type when long click on it
            tvReview.setTooltipText("Review");

            // Open full review when clicked on item
            tvReview.setOnClickListener(v -> {
                Uri uri = Uri.parse(review.getSiteUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

            });

            tvReview.setText(review.getSummary());
            reviewLayout.addView(tvReview);

            ViewGroup.MarginLayoutParams margins = (ViewGroup.MarginLayoutParams) tvReview.getLayoutParams();
            margins.rightMargin = getResources().getDimensionPixelSize(R.dimen.common_padding_small);
            margins.leftMargin = getResources().getDimensionPixelSize(R.dimen.common_padding_small);
            margins.bottomMargin = getResources().getDimensionPixelSize(R.dimen.common_padding_micro);
        }
    }

    private void setRecyclerViews(AnimeExtended animeExtended) {
        StudiosAdapter studiosAdapter = new StudiosAdapter(getApplicationContext(), animeExtended.getStudios().getNodes());
        studiosRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false));
        studiosRecycler.setAdapter(studiosAdapter);

        CharactersAdapter charactersAdapter = new CharactersAdapter(getApplicationContext(), animeExtended.getCharacters().getNodes());
        charsRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false));
        charsRecycler.setAdapter(charactersAdapter);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        userCalls.likeAnime(Utils.userId,
                animeExtended.getId(),
                MainActivity.user,
                stayFav ? AnimeAPI.UPDATE_LIKE : AnimeAPI.NO_UPDATE_LIKE);
    }
}