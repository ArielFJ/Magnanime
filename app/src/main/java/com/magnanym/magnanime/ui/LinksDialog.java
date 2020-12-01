package com.magnanym.magnanime.ui;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.google.android.flexbox.FlexboxLayout;
import com.google.android.material.button.MaterialButton;
import com.magnanym.magnanime.R;
import com.magnanym.magnanime.Utils;
import com.magnanym.magnanime.models.anime.AnimeExtended;
import com.magnanym.magnanime.models.stats.ExternalLink;

import java.util.Random;

public class LinksDialog extends DialogFragment {

    MaterialButton closeBtn;
    FlexboxLayout linksLayout;

    AnimeExtended animeExtended;

    public LinksDialog(AnimeExtended animeExtended) {
        this.animeExtended = animeExtended;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.DialogTheme);
        View root = getLayoutInflater().inflate(R.layout.links_layout, getActivity().findViewById(R.id.dialogContainer));
        builder.setView(root);

        closeBtn = root.findViewById(R.id.close);

        linksLayout = root.findViewById(R.id.flexbox);

        setData();

        AlertDialog alert = builder.create();

        closeBtn.setOnClickListener(v -> {
            alert.dismiss();
        });

        if(alert.getWindow() != null){
            alert.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }

        return alert;
    }

    private void setData(){

        for(ExternalLink link : animeExtended.getExternalLinks()){
            TextView tvGenre;
            tvGenre = new TextView(getContext());
            tvGenre.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.tag_warning))  ;
            tvGenre.setTextColor(getResources().getColor(R.color.white));
            tvGenre.setPadding((int) getResources().getDimension(R.dimen.common_padding_default),
                    (int) getResources().getDimension(R.dimen.common_padding_micro),
                    (int) getResources().getDimension(R.dimen.common_padding_default),
                    (int) getResources().getDimension(R.dimen.common_padding_micro));
            tvGenre.setTextSize(16);
            tvGenre.setGravity(View.TEXT_ALIGNMENT_CENTER);
            tvGenre.setMaxLines(1);

            tvGenre.setOnClickListener(v -> {
                Uri uri = Uri.parse(link.getUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            });

            tvGenre.setText(link.getSite());
            linksLayout.addView(tvGenre);

            ViewGroup.MarginLayoutParams margins = (ViewGroup.MarginLayoutParams) tvGenre.getLayoutParams();
            margins.rightMargin = getContext().getResources().getDimensionPixelSize(R.dimen.common_padding_small);
            margins.leftMargin = getContext().getResources().getDimensionPixelSize(R.dimen.common_padding_small);
            margins.bottomMargin = getContext().getResources().getDimensionPixelSize(R.dimen.common_padding_micro);
        }
    }

}
