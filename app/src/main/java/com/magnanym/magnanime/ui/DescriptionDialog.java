package com.magnanym.magnanime.ui;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.google.android.flexbox.FlexboxLayout;
import com.google.android.material.button.MaterialButton;
import com.magnanym.magnanime.R;
import com.magnanym.magnanime.models.anime.AnimeExtended;

public class DescriptionDialog extends DialogFragment {

    TextView dialogTitle, status, description;
    MaterialButton closeBtn;
    FlexboxLayout genresLayout;

    AnimeExtended animeExtended;

    public DescriptionDialog(AnimeExtended animeExtended) {
        this.animeExtended = animeExtended;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.DialogTheme);
        View root = getLayoutInflater().inflate(R.layout.description_layout, getActivity().findViewById(R.id.dialogContainer));
        builder.setView(root);

        dialogTitle = root.findViewById(R.id.title);
        status = root.findViewById(R.id.status);
        description = root.findViewById(R.id.description);

        closeBtn = root.findViewById(R.id.close);

        genresLayout = root.findViewById(R.id.flexbox);

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
        dialogTitle.setText("Description");
        status.setText("STATUS: " + animeExtended.getStatus());
        description.setText(Html.fromHtml(animeExtended.getDescription()));

//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
//        params.setMargins(10,5,10,5);

        for(String genre : animeExtended.getGenres()){
            TextView tvGenre;
            tvGenre = new TextView(getContext());
//            tvGenre.setLayoutParams(params);
            tvGenre.setBackground(getResources().getDrawable(R.drawable.tag_warning));
            tvGenre.setTextColor(getResources().getColor(R.color.white));
            tvGenre.setPadding((int) getResources().getDimension(R.dimen.common_padding_default),
                (int) getResources().getDimension(R.dimen.common_padding_micro),
                (int) getResources().getDimension(R.dimen.common_padding_default),
                (int) getResources().getDimension(R.dimen.common_padding_micro));
            tvGenre.setTextSize(14);
            tvGenre.setGravity(View.TEXT_ALIGNMENT_CENTER);
            tvGenre.setMaxLines(1);

            tvGenre.setText(genre);
            genresLayout.addView(tvGenre);

            ViewGroup.MarginLayoutParams margins = (ViewGroup.MarginLayoutParams) tvGenre.getLayoutParams();
            margins.rightMargin = getContext().getResources().getDimensionPixelSize(R.dimen.common_padding_small);
            margins.leftMargin = getContext().getResources().getDimensionPixelSize(R.dimen.common_padding_small);
            margins.bottomMargin = getContext().getResources().getDimensionPixelSize(R.dimen.common_padding_micro);
        }
    }

}
