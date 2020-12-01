package com.magnanym.magnanime.models.stats;

public class Character {

    Name name;
    Image image;

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public class Name {
        String full;

        public String getFull() {
            return full;
        }

        public void setFull(String full) {
            this.full = full;
        }
    }

    public class Image {
        String large;
        String medium;

        public String getLarge() {
            return large;
        }

        public void setLarge(String large) {
            this.large = large;
        }

        public String getMedium() {
            return medium;
        }

        public void setMedium(String medium) {
            this.medium = medium;
        }
    }
}
