package com.magnanym.magnanime.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.magnanym.magnanime.R;
import com.magnanym.magnanime.Utils;
import com.magnanym.magnanime.adapters.AnimeCardAdapter;
import com.magnanym.magnanime.api.AnimeCalls;
import com.magnanym.magnanime.interfaces.GetAnime;
import com.magnanym.magnanime.models.anime.AnimeCard;
import com.magnanym.magnanime.ui.anime.AnimeViewModel;

import org.w3c.dom.Text;

import java.util.List;

public class GridListActivity extends AppCompatActivity {

    AnimeViewModel viewModel = new AnimeViewModel();

    RecyclerView recyclerView;
    AnimeCardAdapter adapter;

    AnimeCalls animeCalls;

    MaterialButton loadMoreBtn;

    private int perPage = 30;
    private int actualPage = 1;
    private int actualLastElement = 0;

    private boolean firstLoad = true, firstFinalLoad = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        findViewById(R.id.btnBack).setOnClickListener( v -> finish());

        TextView toolbarTitle = findViewById(R.id.toolbarTitle);
        toolbarTitle.setText(getIntent().getStringExtra("Title"));

        viewModel.getAnimeList().observe(this, animeCards -> observeChanges(animeCards));
        recyclerView = findViewById(R.id.recyclerList);
        loadMoreBtn = findViewById(R.id.btnLoadMore);

        recyclerView.setOnScrollChangeListener((v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if(!recyclerView.canScrollVertically(1)){
                loadMoreBtn.setVisibility(View.VISIBLE);
            } else{
                loadMoreBtn.setVisibility(View.INVISIBLE);
            }
        });

        loadMoreBtn.setOnClickListener(v -> updateData());

        animeCalls = AnimeCalls.getInstance();

        makeCall();

    }

    private void setRecycler(List<AnimeCard> animeCards)
    {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);

        adapter = new AnimeCardAdapter(getApplicationContext(), animeCards);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);
    }

    private void observeChanges(List<AnimeCard> animeCards) {
        setRecycler(animeCards);
        if(firstLoad) {
            firstLoad = false;
            actualLastElement = adapter.getItemCount() - 1;
        } else{
            int lastElement = adapter.getItemCount() - 1;
            if(lastElement - actualLastElement == perPage){
                recyclerView.scrollToPosition(lastElement - perPage - 6);
                recyclerView.smoothScrollToPosition(lastElement - perPage - 3);
            } else{
                if(firstFinalLoad)
                {
                    recyclerView.scrollToPosition(actualLastElement - 3);
                    recyclerView.smoothScrollToPosition(actualLastElement);
                    firstFinalLoad = false;
                } else{
                    recyclerView.scrollToPosition(lastElement);
                    Toast.makeText(this, "End of items.", Toast.LENGTH_SHORT).show();
                }
            }
            actualLastElement = lastElement;
        }
    }

    private void makeCall() {
        switch((GetAnime) getIntent().getSerializableExtra("GetType")){
            case popularThisSeason:
                animeCalls.getPopularAnimeThisSeason(viewModel.getAnimeList(), actualPage, perPage,
                        firstLoad ? false : true);
                break;
            case popularNextSeason:
                animeCalls.getPopularAnimeNextSeason(viewModel.getAnimeList(), actualPage, perPage,
                        firstLoad ? false : true);
                break;
            case allTime:
                animeCalls.getPopularAnimeAllTime(viewModel.getAnimeList(), actualPage, perPage,
                        firstLoad ? false : true);
                break;
            case latest:
                animeCalls.getLatestAnime(viewModel.getAnimeList(), actualPage, perPage,
                        firstLoad ? false : true);
                break;
            default:
                break;
        }
    }

    public void updateData() {
        actualPage++;
        makeCall();
    }
}