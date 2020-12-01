package com.magnanym.magnanime.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.magnanym.magnanime.R;
import com.magnanym.magnanime.models.anime.AnimeBanner;
import com.magnanym.magnanime.ui.anime.AnimeExtendedActivity;


import java.util.List;

public class BannerPagerAdapter extends PagerAdapter {

    Context context;
    List<AnimeBanner> animeBannerList;

    public BannerPagerAdapter(Context context, List<AnimeBanner> animeBannerList) {
        this.context = context;
        this.animeBannerList = animeBannerList;
    }

    @Override
    public int getCount() {
        return animeBannerList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        View view = LayoutInflater.from(context).inflate(R.layout.banner_layout, null);
        ImageView imageView = view.findViewById(R.id.bannerImage);
        TextView tv = view.findViewById(R.id.bannerText);

        String title = animeBannerList.get(position).getTitle().getRomaji();
        if(title.length() > 30)
            tv.setText(title.substring(0, 30) + "...");
        else
            tv.setText(title);

        Glide.with(context)
                .load(animeBannerList.get(position).getBannerImage())
                .into(imageView);

        container.addView(view);

        view.setOnClickListener(v -> {
            startAnimeExtendedFragment(animeBannerList.get(position).getId());
        });

        return view;
    }

    private void startAnimeExtendedFragment(int id)
    {
        Intent intent = new Intent(context, AnimeExtendedActivity.class);
        intent.putExtra("AnimeId", id);
        context.startActivity(intent);
    }
}
