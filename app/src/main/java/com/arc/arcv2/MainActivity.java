package com.arc.arcv2;

import android.os.Bundle;

import com.arc.arcv2.Fragments.AttendanceFaculty;
import com.arc.arcv2.Utils.Helper;
import com.arc.arcv2.Utils.ProgressBarAnimation;


import androidx.core.view.GravityCompat;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.Menu;
import android.widget.ProgressBar;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ProgressBar lecture,st,it;
    Fragment current_frag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Helper.changeStatusBar(getWindow());
        lecture = findViewById(R.id.circularProgressbar);
        st = findViewById(R.id.circularProgressbar_);
        it = findViewById(R.id.circularProgressbar__);
        ProgressBarAnimation progressBarAnimation = new ProgressBarAnimation(lecture,0,20);
        ProgressBarAnimation progressBarAnimation_ = new ProgressBarAnimation(st,0,50);
        ProgressBarAnimation progressBarAnimation__ = new ProgressBarAnimation(it,0,100);
        progressBarAnimation.setDuration(1000);
        progressBarAnimation_.setDuration(1000);
        progressBarAnimation__.setDuration(1000);
        lecture.startAnimation(progressBarAnimation);
        st.startAnimation(progressBarAnimation_);
        it.startAnimation(progressBarAnimation__);
        /*Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        /*ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();*/
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        /*DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {*/
            NavigationView nav = findViewById(R.id.nav_view);
            super.onBackPressed();
            nav.setCheckedItem(R.id.nav_home);
        //}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            onBackPressed();
            // Handle the camera action
        } else if (id == R.id.nav_attendance) {
            inflateFrag(new AttendanceFaculty());
        } else if (id == R.id.nav_discuss) {

        } else if (id == R.id.nav_assignments) {

        }
        else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void inflateFrag(Fragment frag) {
        current_frag = frag;
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction =manager.beginTransaction();
        transaction.replace(R.id.content_main_faculty,frag);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
