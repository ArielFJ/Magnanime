package com.magnanym.magnanime.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.magnanym.magnanime.R;
import com.magnanym.magnanime.models.stats.Character;

import java.util.List;

public class CharactersAdapter extends RecyclerView.Adapter<CharactersAdapter.ViewHolder> {

    Context context;
    List<Character> characterList;

    public CharactersAdapter(Context context, List<Character> characterList) {
        this.context = context;
        this.characterList = characterList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.character_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context)
                .load(characterList.get(position).getImage().getMedium())
                .into(holder.charImage);

        holder.charName.setText(characterList.get(position).getName().getFull());
    }

    @Override
    public int getItemCount() {
        return characterList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView charImage;
        TextView charName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            charImage = itemView.findViewById(R.id.charImage);
            charName = itemView.findViewById(R.id.charName);
        }
    }
}
