package com.magnanym.magnanime.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.magnanym.magnanime.R;
import com.magnanym.magnanime.models.stats.Studio;

import java.util.List;

public class StudiosAdapter extends RecyclerView.Adapter<StudiosAdapter.ViewHolder> {

    Context context;
    List<Studio> studios;

    public StudiosAdapter(Context context, List<Studio> studios) {
        this.context = context;
        this.studios = studios;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.studio_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvStudio.setText(studios.get(position).getName());

        holder.tvStudio.setOnClickListener(v -> {
            Uri uri = Uri.parse(studios.get(position).getSiteUrl());
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return studios.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvStudio;

        @RequiresApi(api = Build.VERSION_CODES.O)
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvStudio = itemView.findViewById(R.id.tvStudio);
            tvStudio.setTooltipText("Studio");

        }
    }
}
