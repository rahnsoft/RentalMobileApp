package com.rit.basa_bari;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.rit.basa_bari.activities.AccountActivity;
import com.rit.basa_bari.activities.LoginActivity;
import com.rit.basa_bari.fragments.AddPostFragment;
import com.rit.basa_bari.show_post_activity.Show_Flat_Activity;
import com.rit.basa_bari.show_post_activity.Show_Hostel_Activity;
import com.rit.basa_bari.show_post_activity.Show_Mess_Activity;
import com.rit.basa_bari.show_post_activity.Show_Sublet_Activity;

import static com.rit.basa_bari.activities.LoginActivity.LOGCHECKER;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener{
    ImageView imageView,simageView,himageView,fimageView;
    CardView mlayout,hlayout,slayout,flayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );

        initialization();

        DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout );
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close );
        drawer.addDrawerListener( toggle );
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById( R.id.nav_view );
        navigationView.setNavigationItemSelectedListener( this );
    }

    @Override
    public void onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout );
//        if (drawer.isDrawerOpen( GravityCompat.START )) {
//            drawer.closeDrawer( GravityCompat.START );
//        } else {
//            super.onBackPressed();
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate( R.menu.main, menu );
        return true;
    }

    //main activity page click button initialization
    public  void initialization(){
        imageView = findViewById(R.id.messImage);
        himageView=findViewById(R.id.hostelImage);
        simageView=findViewById(R.id.subletImage);
        fimageView=findViewById(R.id.flatImage);
        slayout=findViewById(R.id.subletCardViewId);
        mlayout=findViewById(R.id.messCardViewId);
        hlayout=findViewById(R.id.hostelCardViewId);
        flayout=findViewById(R.id.flatCardViewId);

        hlayout.setOnClickListener(this);
        slayout.setOnClickListener(this);
        flayout.setOnClickListener(this);
        mlayout.setOnClickListener(this);



        imageView.setOnClickListener(this);
        himageView.setOnClickListener(this);
        simageView.setOnClickListener(this);
        fimageView.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this,R.style.AlertDialog);
            alertDialogBuilder.setIcon(R.drawable.tolet);
            alertDialogBuilder.setMessage("Are you sure You want to Logout");



            alertDialogBuilder.setPositiveButton("Yes",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {

                            Toast.makeText(MainActivity.this,"You have Successfully Logged Out ",Toast.LENGTH_LONG).show();
                            FirebaseAuth.getInstance().signOut();
                            startActivity(new Intent(MainActivity.this,LoginActivity.class));
                        }

                    });
            alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
//                    startActivity(new );

                }
            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }

        return super.onOptionsItemSelected( item );
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.profileOwner ){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this,R.style.AlertDialog);
            alertDialogBuilder.setIcon(R.drawable.tolet);
            alertDialogBuilder.setTitle("Help Us Know It is You By Logging In Again");
            alertDialogBuilder.setMessage("Are You Sure?");
            alertDialogBuilder.setPositiveButton("Yes",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {

                            Toast.makeText(MainActivity.this,"Login to Confirm Owner's Profile by Selecting Owner At the Top",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        }

                    });
            alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
//                    startActivity(new );

                }
            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

                } else if (id == R.id.addPost) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.framlayout, new AddPostFragment());
            fragmentTransaction.commit();
            getSupportActionBar().setTitle("Add Post");

              } else if (id == R.id.nav_logout) {
            Intent intent=getIntent();
            String uEmail=intent.getStringExtra("Intent1_EMAIL");
            String uPassword=intent.getStringExtra("Intent1_PASSWORD");
            if(LOGCHECKER==false){
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }


        }

        DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout );
        drawer.closeDrawer( GravityCompat.START );
        return true;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.messCardViewId:
                startActivity(new Intent(MainActivity.this, Show_Mess_Activity.class));
                break;
            case R.id.messImage:
                    startActivity(new Intent(MainActivity.this, Show_Mess_Activity.class));
                    break;
            case R.id.flatCardViewId:
                startActivity(new Intent(MainActivity.this, Show_Flat_Activity.class));
                break;
            case R.id.flatImage:
                startActivity(new Intent(MainActivity.this, Show_Flat_Activity.class));
                break;
            case R.id.subletCardViewId:
                startActivity(new Intent(MainActivity.this, Show_Hostel_Activity.class));
                break;
            case R.id.subletImage:
                startActivity(new Intent(MainActivity.this, Show_Hostel_Activity.class));
                break;
            case R.id.hostelCardViewId:
                startActivity(new Intent(MainActivity.this, Show_Hostel_Activity.class));
                break;
            case R.id.hostelImage:
                startActivity(new Intent(MainActivity.this, Show_Hostel_Activity.class));




        }

    }
}
