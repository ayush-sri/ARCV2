package com.arc.arcv2;

import android.os.Bundle;

import com.arc.arcv2.Adapters.SubjectAdapter;
import com.arc.arcv2.Adapters.SubjectAdapterStudent;
import com.arc.arcv2.Fragments.AttendanceFaculty;
import com.arc.arcv2.Fragments.AttendanceStudent;
import com.arc.arcv2.Model.SubjectModel;
import com.arc.arcv2.Utils.Helper;
import com.arc.arcv2.ui.home.HomeFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;

import java.util.ArrayList;

public class main_student extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    private RecyclerView subjects;
    private ArrayList<SubjectModel> subject_list;
    private SubjectAdapterStudent adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_student);
        Helper.changeStatusBar(getWindow());
        initUi();
        addSampleData();
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
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
       /* mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_attendance, R.id.nav_discuss,
                R.id.nav_assignments,R.id.nav_notes,R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();*/
        /*NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);*/
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void initUi() {
        subjects = findViewById(R.id.subject_recycler_student_main);
        subject_list = new ArrayList<>();
        adapter = new SubjectAdapterStudent(this,subject_list);
        subjects.setLayoutManager(new LinearLayoutManager(this));
        subjects.setAdapter(adapter);
    }
    private void addSampleData() {
        subject_list.add(new SubjectModel("Digital Image Processing","BCS7002",false));
        subject_list.add(new SubjectModel("Digital Image Processing","BCS7002",false));
        subject_list.add(new SubjectModel("Digital Image Processing","BCS7002",false));
        subject_list.add(new SubjectModel("Digital Image Processing","BCS7002",false));
        subject_list.add(new SubjectModel("Digital Image Processing","BCS7002",false));
        subject_list.add(new SubjectModel("Digital Image Processing","BCS7002",false));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        NavigationView nav = findViewById(R.id.nav_view);
        nav.setCheckedItem(R.id.nav_home);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_student, menu);
        return true;
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        if (id == R.id.nav_home) {
            onBackPressed();
        } else if (id == R.id.nav_attendance) {
            inflateFrag(new AttendanceStudent());
        } else if (id == R.id.nav_discuss) {

        } else if (id == R.id.nav_assignments) {

        }
        else if (id == R.id.nav_notes) {

        }
        else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void inflateFrag(Fragment frag) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction =manager.beginTransaction();
        transaction.replace(R.id.content_main_student,frag);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    /*@Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }*/
}
