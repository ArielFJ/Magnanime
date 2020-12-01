package com.magnanym.magnanime.models.post;

import java.util.List;

public class CategoryReadModel {
    List<String> categories;
    List<String> tags;

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
