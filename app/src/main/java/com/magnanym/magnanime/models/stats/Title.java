package com.magnanym.magnanime.models.stats;

import com.google.gson.annotations.SerializedName;

public class Title {
    String romaji;
    @SerializedName("native")
    String nativeName;
    String english;

    public String getRomaji() {
        return romaji;
    }

    public void setRomaji(String romaji) {
        this.romaji = romaji;
    }

    public String getNativeName() {
        return nativeName;
    }

    public void setNativeName(String nativeName) {
        this.nativeName = nativeName;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }
}
