package com.magnanym.magnanime;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationView;
import com.magnanym.magnanime.api.AnimeCalls;
import com.magnanym.magnanime.api.UserCalls;
import com.magnanym.magnanime.models.User;
import com.magnanym.magnanime.models.UserCreateModel;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ShareCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    public static MutableLiveData<User> user;
    MenuItem adultItem, favsItem; // TODO
    TextView userNameHeader, log;

    UserCalls userCalls;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sharedPreferences = getSharedPreferences("User", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_favorites, R.id.nav_search)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        userCalls = UserCalls.getInstance(getApplicationContext());
        userNameHeader = navigationView.getHeaderView(0).findViewById(R.id.navHeaderUsername);

        // Set log button depending user null or not
        user = new MutableLiveData<>();
        //logItem = navigationView.getMenu().findItem(R.id.log);
        favsItem = navigationView.getMenu().findItem(R.id.nav_favorites);
        favsItem.setVisible(false);

        log = findViewById(R.id.log);

        log.setOnClickListener(v -> {
            if(user.getValue() == null){
                LoginDialog loginDialog = new LoginDialog(user);
                loginDialog.show(getSupportFragmentManager(), "LOGIN");
            } else{
                user.setValue(null);
            }
        });

        user.observe(this ,u -> {
            onUserChanged(u);

        });

    }

    private void onUserChanged(User u) {
        if(user.getValue() == null) {
            adultItem.setVisible(false);
            Utils.allowPlus18 = false;
            Utils.favs = new ArrayList<>();
            Utils.userId = null;

            editor.putString("username", null);
            editor.putString("password", null);

            log.setText(getString(R.string.login));
            //logItem.setIcon(R.drawable.ic_login);
            favsItem.setVisible(false);

            userNameHeader.setText(getString(R.string.no_user));

        } else{
            adultItem.setVisible(true);
            adultItem.setChecked(u.isAllowPlus18());
            Utils.allowPlus18 = u.isAllowPlus18();
            Utils.favs = u.getFavs();
            Utils.userId = u.get_id();

            editor.putString("username", u.getUsername());
            editor.putString("password", u.getPassword());

            //logItem.setTitle(getString(R.string.logout));
            log.setText(R.string.logout);
            //logItem.setIcon(R.drawable.ic_logout);
            favsItem.setVisible(true);

            userNameHeader.setText(user.getValue().getUsername());
            Log.d("FAVS", Utils.favs.size() + "");

        }

        editor.apply();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        adultItem = menu.getItem(1);
        adultItem.setOnMenuItemClickListener(v -> {

            v.setChecked(!v.isChecked());
            User u = User.clone(user.getValue());
            u.setAllowPlus18(v.isChecked());
            userCalls.updateUser(user, u);
            return false;
        });

        menu.getItem(0).setOnMenuItemClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"));
            intent.putExtra(Intent.EXTRA_EMAIL, new String[] { "20186362@itla.edu.do" });
            intent.putExtra(Intent.EXTRA_SUBJECT, "Rating anime app");
            startActivity(Intent.createChooser(intent, "Choose an email client:"));

            return false;
        });

        if(user.getValue() == null) {
            adultItem.setVisible(false);
        } else{
            adultItem.setVisible(true);
        }

        UserCreateModel model = new UserCreateModel(new User(sharedPreferences.getString("username", null),
                sharedPreferences.getString("password", null)));

        Log.d("USER", model.toString());

        if(model.getUsername() != null){
            userCalls.logUser(model, user);
        }

        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    @Override
    public void onBackPressed() {
        new MaterialAlertDialogBuilder(this)
                .setTitle(R.string.bottom_sheet_title)
                .setPositiveButton(R.string.exit, (dialog, which) -> finish())
                .setNegativeButton("Cancel", null)
                .show();
    }
}