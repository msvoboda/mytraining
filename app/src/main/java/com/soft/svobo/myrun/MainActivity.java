package com.soft.svobo.myrun;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.soft.svobo.myrun.Common.OpenFileDialog;
import com.soft.svobo.myrun.DataModel.GpsList;
import com.soft.svobo.myrun.DataModel.GpsListAdapter;
import com.soft.svobo.myrun.LocationTools.GpxImporter;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OpenFileDialog.OpenDialogListener {

    com.soft.svobo.myrun.Common.OpenFileDialog _dlg = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Start training", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (findViewById(R.id.fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            com.soft.svobo.myrun.TrainingFragment firstFragment = new TrainingFragment();
            firstFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, firstFragment).commit();
        }
    }

    public void setFragment(int id)
    {
        Fragment fragment = null;

        if (id == R.id.nav_routes) {
            fragment = new RoutesFragment();
        } else if (id == R.id.nav_training) {
            fragment = new TrainingFragment();
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_send) {

        }

        if (fragment != null) {

            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.fragment_container, fragment);
            transaction.commit();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Snackbar.make(this.getCurrentFocus(), "Select options:"+item.getTitle(), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        else if (id == R.id.action_import)
        {
            try {
                com.soft.svobo.myrun.Common.OpenFileDialog dlg = new OpenFileDialog(MainActivity.this);
                dlg.setFilter(".*");
                dlg.setTitle(R.string.string_import_title);
                dlg.setOpenDialogListener(this);
                dlg.show();
            }
            catch(Exception e)
            {
                Log.d("ERR", e.toString());
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        setFragment(id);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void OnSelectedFile(String fileName) {

        GpxImporter importer = new GpxImporter(fileName);
        try {
            importer.ImportTraining();
            GpsList points = importer.getList();

            GpsListAdapter adapter = new GpsListAdapter(this, R.layout.point_layout, points);
            //setFragment(R.id.nav_training);
            ListView lv = (ListView)findViewById(R.id.listPoints);
            lv.setAdapter(adapter);
            //lv.a
            Snackbar.make(this.getCurrentFocus(),"Points:"+adapter.getCount(), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
        catch(Exception ex)
        {
            Snackbar.make(this.getCurrentFocus(), ex.toString(), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            System.out.print("Import error");
        }
    }
}
