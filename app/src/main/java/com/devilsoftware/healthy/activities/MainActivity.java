package com.devilsoftware.healthy.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.devilsoftware.healthy.R;
import com.devilsoftware.healthy.views.ApplyFragmentLevel;
import com.devilsoftware.healthy.views.FindFragment;
import com.devilsoftware.healthy.views.MainFragment;
import com.devilsoftware.healthy.views.RegistrationFragment;
import com.devilsoftware.healthy.views.SearchOrgFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Toast.makeText(this, "Подключитесь к сети Innopolis, пароль: Innopolis",Toast.LENGTH_LONG).show();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, new MainFragment());
        fragmentTransaction.commit();

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



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        if (id == R.id.nav_reg) {
            fragmentTransaction.replace(R.id.container, new RegistrationFragment());
            fragmentTransaction.commit();
        } else if(id == R.id.nav_apply) {
            fragmentTransaction.replace(R.id.container, new ApplyFragmentLevel());
            fragmentTransaction.commit();
        } else if(id == R.id.nav_first_help) {
            fragmentTransaction.replace(R.id.container, new MainFragment());
            fragmentTransaction.commit();
        } else if(id == R.id.nav_info) {
            fragmentTransaction.replace(R.id.container, new FindFragment());
            fragmentTransaction.commit();
        } else if (id == R.id.nav_map) {
            fragmentTransaction.replace(R.id.container, new SearchOrgFragment());
            fragmentTransaction.commit();
        } else if (id == R.id.nav_call){
            String phone = "112";
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
            startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
