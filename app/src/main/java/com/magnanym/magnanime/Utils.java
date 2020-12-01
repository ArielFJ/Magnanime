package com.magnanym.magnanime;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.magnanym.magnanime.models.User;
import com.magnanym.magnanime.ui.GridListActivity;

import java.util.ArrayList;
import java.util.List;

public abstract class Utils {

    public static int[] tagResourcesId = new int[] {
            R.drawable.tag_success,
    };

    public static boolean allowPlus18 = false;
    public static int searchListSize = 0;
    public static List<Integer> favs = new ArrayList<>();
    public static String userId = "";
    public static final String BASE_URL="https://magnanime-api.herokuapp.com/";
    //public static MutableLiveData<User> user = new M;
}
