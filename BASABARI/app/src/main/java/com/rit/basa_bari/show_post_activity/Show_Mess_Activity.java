package com.rit.basa_bari.show_post_activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.support.v7.widget.SearchView;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Filter;

import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rit.basa_bari.MainActivity;
import com.rit.basa_bari.R;
import com.rit.basa_bari.adapters.MessPostAdapter;
import com.rit.basa_bari.models.MessUpload;

import java.util.ArrayList;
import java.util.List;

public class Show_Mess_Activity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private static final String TAG = Show_Mess_Activity.class.getSimpleName();

    private RecyclerView recyclerView;
    public MessPostAdapter messPostAdapter;


    private ArrayList <MessUpload> messUploadArrayList;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_show__mess );
        setTitle( "To_Let(Mess)" );
        recyclerView = findViewById( R.id.messRecyclerView );
        recyclerView.setHasFixedSize( true );
        recyclerView.setLayoutManager( new LinearLayoutManager( this ) );
        messUploadArrayList= new ArrayList <>();
        databaseReference = FirebaseDatabase.getInstance().getReference( "mess" );
        retriveData();
    }


    public void retriveData() {
        databaseReference.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot setData : dataSnapshot.getChildren()) {
                    MessUpload messUpload = setData.getValue( MessUpload.class );
                    messUploadArrayList.add( messUpload );
                }
                messPostAdapter = new MessPostAdapter( Show_Mess_Activity.this, messUploadArrayList );
                recyclerView.setAdapter( messPostAdapter );


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText( Show_Mess_Activity.this, databaseError.getMessage(), Toast.LENGTH_SHORT ).show();

            }
        } );
    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Discard changes?");
        builder.setMessage("If you go back, click yes");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {


                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.menu_item,menu );
        MenuItem menuItem=menu.findItem( R.id.action_search );
        SearchView searchView= (SearchView) MenuItemCompat.getActionView( menuItem );
        searchView.setOnQueryTextListener( this );
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        newText =newText.toLowerCase();
        ArrayList<MessUpload> messList=new ArrayList <>();
        for(MessUpload list:messUploadArrayList){
            String locationName=list.getLocation().toLowerCase();
            String descriptionflat=list.getDescription().toLowerCase();
            String messrent=list.getMessrentString().toLowerCase();
            String messPhone=list.getPhone().toLowerCase();
            String messmonth=list.getMonth().toLowerCase();
            String messgender=list.getGender().toLowerCase();
            if(messgender.contains(newText))
                messList.add(list);
            if(messmonth.contains(newText))
                messList.add(list);
            if(messPhone.contains(newText))
                messList.add(list);
            if(messrent.contains(newText))
                messList.add(list);

            if(descriptionflat.contains(newText))
                messList.add(list);
                        if(locationName.contains( newText ))
                messList.add( list );
        }
        messPostAdapter.setFilter( messList );
        return true;
    }

}
