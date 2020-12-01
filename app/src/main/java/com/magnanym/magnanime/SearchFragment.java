package com.magnanym.magnanime;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.magnanym.magnanime.adapters.AnimeCardAdapter;
import com.magnanym.magnanime.api.AnimeCalls;
import com.magnanym.magnanime.models.anime.AnimeCard;

import java.util.List;

public class SearchFragment extends Fragment {

    EditText searchBox;
    MaterialButton btnSearch;
    RecyclerView recyclerView;

    MutableLiveData<List<AnimeCard>> animeCards;
    AnimeCardAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_search, container, false);

        searchBox = root.findViewById(R.id.searchBox);
        btnSearch = root.findViewById(R.id.btnSearch);
        recyclerView = root.findViewById(R.id.recyclerList);

        animeCards = new MutableLiveData<>();

        btnSearch.setOnClickListener(v -> {
            if(searchBox.getText().toString().trim().length() > 0)
                search();
            else
                Toast.makeText(getContext(), "Type in the search box.", Toast.LENGTH_SHORT).show();
        });

        animeCards.observeForever(animeCards1 -> {
            if(animeCards1.size() > 0){
                GridLayoutManager layoutManager = new GridLayoutManager(getActivity().getApplicationContext(), 3);

                adapter = new AnimeCardAdapter(getActivity().getApplicationContext(), animeCards1);
                recyclerView.setLayoutManager(layoutManager);

                recyclerView.setAdapter(adapter);
            } else {
                Snackbar snackbar;
                if(Utils.allowPlus18){
                    snackbar = Snackbar
                            .make(root.findViewById(R.id.constraint),
                                    "There's no results with that name.", Snackbar.LENGTH_LONG);

                } else{
                    if(Utils.searchListSize > 0){
                        snackbar = Snackbar
                                .make(root.findViewById(R.id.constraint),
                                        "Results for this name just available to adults.", Snackbar.LENGTH_LONG);
                    } else {
                        snackbar = Snackbar
                                .make(root.findViewById(R.id.constraint),
                                        "There's no results with that name.", Snackbar.LENGTH_LONG);
                    }

                }
                snackbar.show();
            }
        });

        return root;
    }

    private void search() {
        AnimeCalls.getInstance().searchAnimeByName(animeCards, 1, 50, searchBox.getText().toString());
    }


}