package com.magnanym.magnanime.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.magnanym.magnanime.R;
import com.magnanym.magnanime.Utils;
import com.magnanym.magnanime.models.anime.AnimeCard;
import com.magnanym.magnanime.models.anime.AnimeExtended;
import com.magnanym.magnanime.ui.GridListActivity;
import com.magnanym.magnanime.ui.anime.AnimeExtendedActivity;

import java.util.List;

public class AnimeCardAdapter extends RecyclerView.Adapter<AnimeCardAdapter.ViewHolder>{

    Context context;
    List<AnimeCard> animeCards;

    public AnimeCardAdapter(Context context, List<AnimeCard> animeCards) {
        this.context = context;
        this.animeCards = animeCards;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.anime_card_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AnimeCardAdapter.ViewHolder holder, int position) {
        Glide.with(context)
                .load(animeCards.get(position).getCoverImage().getLarge())
                .into(holder.animeImage);

        holder.animeName.setText(animeCards.get(position).getTitle().getRomaji());
        holder.animeType.setText(animeCards.get(position).getType());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, AnimeExtendedActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("AnimeId", animeCards.get(position).getId());
            context.startActivity(intent);
        });
    }


    @Override
    public int getItemCount() {
        return animeCards.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView animeImage;
        TextView animeName, animeType;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            animeImage = itemView.findViewById(R.id.animeImage);
            animeName = itemView.findViewById(R.id.animeName);
            animeType = itemView.findViewById(R.id.animeType);
        }
    }
}
