package com.magnanym.magnanime.ui.favorites;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.magnanym.magnanime.R;
import com.magnanym.magnanime.Utils;
import com.magnanym.magnanime.adapters.AnimeCardAdapter;
import com.magnanym.magnanime.api.AnimeCalls;
import com.magnanym.magnanime.models.anime.AnimeCard;

import java.util.List;

public class FavoritesFragment extends Fragment {

    RecyclerView recyclerView;
    MutableLiveData<List<AnimeCard>> animeCards;
    AnimeCardAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_favorites, container, false);

        recyclerView = root.findViewById(R.id.recyclerList);

        animeCards = new MutableLiveData<>();

        animeCards.observeForever(animeCards1 -> {
            setRecycler(animeCards1);
        });

        AnimeCalls.getInstance().getAnimesById(animeCards, Utils.favs);

        return root;
    }

    private void setRecycler(List<AnimeCard> animeCards)
    {
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity().getApplicationContext(), 3);

        adapter = new AnimeCardAdapter(getActivity().getApplicationContext(), animeCards);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);
    }

}