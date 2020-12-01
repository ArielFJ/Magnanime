package com.magnanym.magnanime.models.anime;

import com.magnanym.magnanime.models.stats.*;

import java.util.Arrays;
import java.util.List;

public class AnimeExtended {

    int id;
    String type;
    Title title;
    Trailer trailer;
    CoverImage coverImage;
    String bannerImage;
    String description;         // desc alert
    String format;              // desc alert
    String status;              // desc alert
    String season;              // tag 1
    int seasonYear;             // tag 1
    int episodes;               // tag 1
    int duration;               // tag 1
    String countryOfOrigin;     // tag 1
    String source;
    String[] genres;            // desc alert
    String siteUrl;
    Stats stats;
    ReviewsContainer reviews;
    List<ExternalLink> externalLinks;
    boolean isAdult;
    StudiosContainer studios;
    CharactersContainer characters;
    boolean isFav = false;

    public boolean isFav() {
        return isFav;
    }

    public void setFav(boolean fav) {
        isFav = fav;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public Trailer getTrailer() {
        return trailer;
    }

    public void setTrailer(Trailer trailer) {
        this.trailer = trailer;
    }

    public CoverImage getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(CoverImage coverImage) {
        this.coverImage = coverImage;
    }

    public String getBannerImage() {
        return bannerImage;
    }

    public void setBannerImage(String bannerImage) {
        this.bannerImage = bannerImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public int getSeasonYear() {
        return seasonYear;
    }

    public void setSeasonYear(int seasonYear) {
        this.seasonYear = seasonYear;
    }

    public int getEpisodes() {
        return episodes;
    }

    public void setEpisodes(int episodes) {
        this.episodes = episodes;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }

    public void setCountryOfOrigin(String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String[] getGenres() {
        return genres;
    }

    public void setGenres(String[] genres) {
        this.genres = genres;
    }

    public String getSiteUrl() {
        return siteUrl;
    }

    public void setSiteUrl(String siteUrl) {
        this.siteUrl = siteUrl;
    }

    public Stats getStats() {
        return stats;
    }

    public int getAverageScoreStats() {
        float scoreSum = 0, scoreCount = 0;
        for(Score s : stats.getScoreDistribution()){
            scoreSum += s.getScore() * s.getAmount();
            scoreCount += s.getAmount();
        }
        return (int)(scoreSum / scoreCount);
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }

    public ReviewsContainer getReviews() {
        return reviews;
    }

    public void setReviews(ReviewsContainer reviews) {
        this.reviews = reviews;
    }

    public List<ExternalLink> getExternalLinks() {
        return externalLinks;
    }

    public void setExternalLinks(List<ExternalLink> externalLinks) {
        this.externalLinks = externalLinks;
    }

    public boolean isAdult() {
        return isAdult;
    }

    public void setAdult(boolean adult) {
        isAdult = adult;
    }

    public StudiosContainer getStudios() {
        return studios;
    }

    public void setStudios(StudiosContainer studios) {
        this.studios = studios;
    }

    public CharactersContainer getCharacters() {
        return characters;
    }

    public void setCharacters(CharactersContainer characters) {
        this.characters = characters;
    }

    @Override
    public String toString() {
        return "AnimeExtended{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", title=" + title +
                ", coverImage=" + coverImage +
                ", bannerImage='" + bannerImage + '\'' +
                ", description='" + description + '\'' +
                ", format='" + format + '\'' +
                ", status='" + status + '\'' +
                ", season='" + season + '\'' +
                ", seasonYear=" + seasonYear +
                ", episodes=" + episodes +
                ", duration=" + duration +
                ", countryOfOrigin='" + countryOfOrigin + '\'' +
                ", source='" + source + '\'' +
                ", genres=" + Arrays.toString(genres) +
                ", siteUrl='" + siteUrl + '\'' +
                ", stats=" + stats +
                ", reviews=" + reviews +
                ", externalLinks=" + externalLinks +
                ", isAdult=" + isAdult +
                ", studios=" + studios +
                ", characters=" + characters +
                '}';
    }
}
