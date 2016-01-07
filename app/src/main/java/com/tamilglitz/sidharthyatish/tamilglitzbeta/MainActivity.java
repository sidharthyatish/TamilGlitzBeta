package com.tamilglitz.sidharthyatish.tamilglitzbeta;

import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity{

    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout= (DrawerLayout) findViewById(R.id.drawer);
        navigationView= (NavigationView) findViewById(R.id.navigation_view);
        setupDrawerContent(navigationView);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open, R.string.drawer_close){

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank

                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
        if(savedInstanceState==null){
            FragmentManager mFragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

            Home fragment = new Home();

            fragmentTransaction.add(R.id.frame, fragment);
            fragmentTransaction.commit();
        }


    }
    /*  @Override
      public boolean onOptionsItemSelected(MenuItem item) {
          // The action bar home/up action should open or close the drawer.
          switch (item.getItemId()) {
              case android.R.id.home:
                 drawerLayout.openDrawer(GravityCompat.START);
                  return true;
          }

          return super.onOptionsItemSelected(item);
      }
  */
    // Make sure this is the method with just `Bundle` as the signature
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }


    public void setupDrawerContent(NavigationView upDrawerContent) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }


                });

    }

    private void selectDrawerItem(MenuItem menuItem) {
        Fragment fragment = null;

        Class fragmentClass;
        switch(menuItem.getItemId()) {
            case R.id.nav_home:
                fragmentClass = Home.class;
                break;
            case R.id.nav_hot_news:
                fragmentClass = HotNews.class;
                break;
            case R.id.nav_box_office:
                fragmentClass = BoxOffice.class;
                break;
            case R.id.nav_movie_reviews:
                fragmentClass=MovieReviews.class;
                break;
            case R.id.nav_movie_trailers:
                fragmentClass=MovieTrailers.class;
                break;
            case R.id.nav_top10:
                fragmentClass=Top10.class;
                break;
            case R.id.nav_featured:
                fragmentClass=Featured.class;
                        break;
            case R.id.nav_music_reviews:
                fragmentClass=MusicReviews.class;
                break;
            case R.id.nav_tech:
                fragmentClass=Tech.class;
                break;
            case R.id.nav_video_songs:
                fragmentClass=VideoSongs.class;
                break;
            case R.id.nav_videos:
                fragmentClass=Videos.class;
                break;
            case R.id.nav_viral:
                fragmentClass=Viral.class;
                break;
            default:
                fragmentClass = Home.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame, fragment).commit();
        // Initializing Drawer Layout and ActionBarToggle

        // Highlight the selected item, update the title, and close the drawer
        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        drawerLayout.closeDrawers();

    }
}
