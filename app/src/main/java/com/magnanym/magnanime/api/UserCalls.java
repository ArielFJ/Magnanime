package com.magnanym.magnanime.api;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.magnanym.magnanime.Utils;
import com.magnanym.magnanime.interfaces.AnimeAPI;
import com.magnanym.magnanime.models.User;
import com.magnanym.magnanime.models.UserCreateModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class UserCalls {

    Context context;
    Retrofit retrofit;
    AnimeAPI animeAPI;


    private static UserCalls instance;

    private UserCalls(Context context) {
        retrofit = new Retrofit.Builder()
                .baseUrl(Utils.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        animeAPI = retrofit.create(AnimeAPI.class);
        this.context = context;
    }

    public static UserCalls getInstance(Context context) {
        if(instance == null)
            instance = new UserCalls(context);

        return instance;
    }

    public void createNewUser(UserCreateModel user, MutableLiveData<User> userData) {

        Call<User> call = animeAPI.createUser(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                try{
                    if(response.isSuccessful()){
                        userData.setValue(response.body());
                        Toast.makeText(context, "New user created.", Toast.LENGTH_SHORT).show();
                    }else{
                        Log.e("RESPONSE", response.message());
                        Toast.makeText(context, "An user with that name already exists.", Toast.LENGTH_SHORT).show();
                    }
                }catch(Exception e){
                    Log.e("ERROR", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("FAIL", t.getMessage());
            }
        });
    }

    public void logUser(UserCreateModel userModel, MutableLiveData<User> userData) {
        Call<User> call = animeAPI.logUser(userModel.getUsername(), userModel.getPassword());
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                try{
                    if(response.isSuccessful()){
                        userData.setValue( response.body());
                    }
                    else {
                        createNewUser(userModel, userData);
                    }
                }
                catch(Exception e){
                    Log.e("ERROR", e.getMessage());
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("FAIL", t.getMessage());
            }
        });
    }

    public void  likeAnime(String userId, int animeId, MutableLiveData<User> userData, int stayFav){
        Call<User> call = animeAPI.likeAnime(userId, animeId, stayFav);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                try{
                    if(response.isSuccessful()){
                        userData.setValue(response.body());
                    }
                    else{
                        Log.d("LOG", "Algo pasó" + response.message());
                    }
                }
                catch(Exception e){
                    Log.e("ERROR", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("FAIL", t.getMessage());
            }
        });
    }

    public void updateUser(MutableLiveData<User> user, User newUserInfo) {
        Call<User> call = animeAPI.updateUser(user.getValue().get_id(), newUserInfo);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                try{
                    if(response.isSuccessful()){
                        user.setValue(response.body());
                    }
                }
                catch(Exception e){
                    Log.e("ERROR", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("FAIL", t.getMessage());
            }
        });
    }

    public void deleteUser(MutableLiveData<User> user) {
        Call<Void> call = animeAPI.deleteUser(user.getValue().get_id());

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                try{
                    if(response.isSuccessful()){
                        Log.d("DELETED", "User" + user.getValue().getUsername() + "deleted.");
                        user.setValue(null);
                    }
                }
                catch(Exception e){
                    Log.e("ERROR", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("FAIL", t.getMessage());
            }
        });
    }

}
