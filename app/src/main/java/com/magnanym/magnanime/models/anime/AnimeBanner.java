package com.magnanym.magnanime.models.anime;

import com.magnanym.magnanime.models.stats.Title;

public class AnimeBanner {
    int id;
    Title title;
    String bannerImage;
    boolean isAdult;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public String getBannerImage() {
        return bannerImage;
    }

    public void setBannerImage(String bannerImage) {
        this.bannerImage = bannerImage;
    }

    public boolean isAdult() {
        return isAdult;
    }

    public void setAdult(boolean adult) {
        isAdult = adult;
    }

    @Override
    public String toString() {
        return "Anime{" +
                "id=" + id +
                ", title=" + title +
                ", bannerImage='" + bannerImage + '\'' +
                '}';
    }
}
