package com.magnanym.magnanime.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {
    String _id;
    String username;
    String password;
    boolean allowPlus18;
    List<Integer> favs;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        allowPlus18 = false;
        favs = new ArrayList<>();
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAllowPlus18() {
        return allowPlus18;
    }

    public void setAllowPlus18(boolean allowPlus18) {
        this.allowPlus18 = allowPlus18;
    }

    public List<Integer> getFavs() {
        return favs;
    }

    public void setFavs(List<Integer> favs) {
        this.favs = favs;
    }

    @Override
    public String toString() {
        return "User{" +
                "_id='" + _id + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", allowPlus18=" + allowPlus18 +
                '}';
    }

    public static User clone(User user) {
        User u = new User(user.getUsername(), user.getPassword());
        u.setAllowPlus18(user.isAllowPlus18());
        u.setFavs(user.getFavs());
        u.set_id(user.get_id());
        return u;
    }
}
