package com.rit.basa_bari;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.rit.basa_bari.activities.AccountActivity;
import com.rit.basa_bari.activities.LoginActivity;
import com.rit.basa_bari.activities.ProfileActivity;
import com.rit.basa_bari.fragments.AddPostFragment;
import com.rit.basa_bari.show_post_activity.Show_Flat_Activity;
import com.rit.basa_bari.show_post_activity.Show_Hostel_Activity;
import com.rit.basa_bari.show_post_activity.Show_Mess_Activity;

import static com.rit.basa_bari.activities.LoginActivity.LOGCHECKER;

public class MainActivityRenter extends AppCompatActivity
        implements View.OnClickListener{
    ImageView imageView,simageView,himageView,fimageView;
    CardView mlayout,hlayout,slayout,flayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_mainrenter );

        initialization();

          }

    @Override
    public void onBackPressed() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate( R.menu.mainrenter, menu );
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
            alertDialogBuilder.setMessage("Are you sure " +
                    "You want to Logout?");
                    alertDialogBuilder.setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {

                                    Toast.makeText(MainActivityRenter.this,"You have Successfully Logged Out ",Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(MainActivityRenter.this,LoginActivity.class));
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
        }else if(id==R.id.profileRenter){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this,R.style.AlertDialog);
            alertDialogBuilder.setIcon(R.drawable.tolet);
            alertDialogBuilder.setTitle("Help Us Know It is You By Logging In Again");
            alertDialogBuilder.setMessage("Are You Sure?");
            alertDialogBuilder.setPositiveButton("Yes",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {

                            Toast.makeText(MainActivityRenter.this,"Login to Confirm Renter's Profile by Selecting Renter At The Top ",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(MainActivityRenter.this, LoginActivity.class));
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

      @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.messCardViewId:
                startActivity(new Intent(MainActivityRenter.this, Show_Mess_Activity.class));
                break;
            case R.id.messImage:
                    startActivity(new Intent(MainActivityRenter.this, Show_Mess_Activity.class));
                    break;
            case R.id.flatCardViewId:
                startActivity(new Intent(MainActivityRenter.this, Show_Flat_Activity.class));
                break;
            case R.id.flatImage:
                startActivity(new Intent(MainActivityRenter.this, Show_Flat_Activity.class));
                break;
            case R.id.subletCardViewId:
                startActivity(new Intent(MainActivityRenter.this, Show_Hostel_Activity.class));
                break;
            case R.id.subletImage:
                startActivity(new Intent(MainActivityRenter.this, Show_Hostel_Activity.class));
                break;
            case R.id.hostelCardViewId:
                startActivity(new Intent(MainActivityRenter.this, Show_Hostel_Activity.class));
                break;
            case R.id.hostelImage:
                startActivity(new Intent(MainActivityRenter.this, Show_Hostel_Activity.class));




        }

    }
}
