package com.example.genjeh.mycataloguemovieuiux.Activity;


import android.content.Intent;
import android.graphics.Color;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;


import com.example.genjeh.mycataloguemovieuiux.Fragment.FavoriteFragment;
import com.example.genjeh.mycataloguemovieuiux.Fragment.NowPlayingFragment;
import com.example.genjeh.mycataloguemovieuiux.Fragment.ResultMovieFragment;
import com.example.genjeh.mycataloguemovieuiux.Fragment.UpcomingFragment;
import com.example.genjeh.mycataloguemovieuiux.R;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {
    private Fragment currentFragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private String fragment_name;

    private static final String EXTRA_FRAGMENT_NAME = "extra_fragment_name";
    private static final String EXTRA_SUBMIT_STATE = "extra_submit_state";

    private boolean submitState = false;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.collapse_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigation;

    @BindView(R.id.search_view_material)
    MaterialSearchView materialSearchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        collapsingToolbarLayout.setTitleEnabled(false);
        getSupportActionBar().setTitle(getResources().getString(R.string.app_name));

        bottomNavigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
        fragmentManager = getSupportFragmentManager();

        if (savedInstanceState == null) {
            Fragment upcomingFragment = new UpcomingFragment();
            fragment_name = "upcoming";
            currentFragment = upcomingFragment;
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, upcomingFragment).commit();
        } else {
            submitState = savedInstanceState.getBoolean(EXTRA_SUBMIT_STATE);
            fragment_name = savedInstanceState.getString(EXTRA_FRAGMENT_NAME);
            if (fragment_name != null) {
                switch (fragment_name) {
                    case "upcoming":
                        currentFragment = new UpcomingFragment();
                        break;
                    case "nowplaying":
                        currentFragment = new NowPlayingFragment();
                        break;
                    case "favorite":
                        currentFragment = new FavoriteFragment();
                        break;
                }
            }

        }
    }



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(EXTRA_FRAGMENT_NAME, fragment_name);
        outState.putBoolean(EXTRA_SUBMIT_STATE,submitState);
        super.onSaveInstanceState(outState);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            if (materialSearchView.isSearchOpen()) {
                submitState=false;
                materialSearchView.closeSearch();
            }
            switch (item.getItemId()) {
                case R.id.navigation_upcoming:
                    fragment_name = "upcoming";
                    currentFragment = new UpcomingFragment();
                    break;
                case R.id.navigation_nowplaying:
                    fragment_name = "nowplaying";
                    currentFragment = new NowPlayingFragment();

                    break;
                case R.id.navigation_favorite:
                    fragment_name = "favorite";
                    currentFragment = new FavoriteFragment();
                    break;
            }

            if (currentFragment != null) {
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, currentFragment).commit();
                return true;
            }
            return false;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.appbar_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.search_view);
        materialSearchView.setMenuItem(searchItem);
        materialSearchView.setHint(getResources().getString(R.string.query_hint));
        materialSearchView.setHintTextColor(Color.GRAY);
        materialSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ResultMovieFragment resultMovieFragment = new ResultMovieFragment();
                resultMovieFragment.setMovieName(query);
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, resultMovieFragment).commit();
                materialSearchView.clearFocus();
                submitState = true;
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        materialSearchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {
                if (submitState) {
                    submitState=false;
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, currentFragment).commit();
                }
            }
        });


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.changeLanguage) {
            Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(intent);
        }else if(item.getItemId() == R.id.settingReminder){
            Intent intent = new Intent(this,NotificationSettingActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (materialSearchView.isSearchOpen()) {
            if (submitState) {
                submitState=false;
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, currentFragment).commit();
                materialSearchView.closeSearch();
            } else {
                materialSearchView.closeSearch();
            }

        } else {
            super.onBackPressed();
        }

    }
}
