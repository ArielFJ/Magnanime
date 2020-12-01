package com.magnanym.magnanime.models.anime;

import com.magnanym.magnanime.models.stats.CoverImage;
import com.magnanym.magnanime.models.stats.Title;

public class AnimeCard {
    int id;
    Title title;
    String type;
    CoverImage coverImage;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public CoverImage getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(CoverImage coverImage) {
        this.coverImage = coverImage;
    }

    public boolean isAdult() {
        return isAdult;
    }

    public void setAdult(boolean adult) {
        isAdult = adult;
    }

    @Override
    public String toString() {
        return "AnimeCard{" +
                "id=" + id +
                ", title=" + title +
                ", type='" + type + '\'' +
                ", coverImage=" + coverImage +
                ", isAdult=" + isAdult +
                '}';
    }
}
