package com.rit.basa_bari.show_post_activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rit.basa_bari.R;
import com.rit.basa_bari.adapters.FlatPostAdapter;
import com.rit.basa_bari.models.FlatUpload;

import java.util.ArrayList;

public class Show_Flat_Activity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private static final String TAG = Show_Flat_Activity.class.getSimpleName();

    private RecyclerView recyclerView;
    public FlatPostAdapter flatPostAdapter;


    private ArrayList<FlatUpload> flatUploadArrayList;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_show__flat_ );
        setTitle( "To_Let(Flat)" );
        recyclerView = findViewById( R.id.flatRecyclerView );
        recyclerView.setHasFixedSize( true );
        recyclerView.setLayoutManager( new LinearLayoutManager( this ) );
        flatUploadArrayList= new ArrayList <>();
        databaseReference = FirebaseDatabase.getInstance().getReference( "flat" );
        retriveData();
    }

    private void retriveData() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot setData : dataSnapshot.getChildren()) {
                    FlatUpload flatUpload = setData.getValue( FlatUpload.class );
                    flatUploadArrayList.add( flatUpload );
                }
                flatPostAdapter = new FlatPostAdapter( Show_Flat_Activity.this, flatUploadArrayList );
                recyclerView.setAdapter( flatPostAdapter );

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Show_Flat_Activity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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
        ArrayList<FlatUpload> flatList=new ArrayList <>();
        for(FlatUpload list:flatUploadArrayList){
            String descriptionName=list.getFdescription().toLowerCase();
            String flatrent=list.getFrentString().toLowerCase();
            String flatmonth=list.getMonth().toLowerCase();
            String flatgender=list.getGender().toLowerCase();
            String locationName=list.getFlocation().toLowerCase();
            String flatphone=list.getFphone().toLowerCase();
            if(flatphone.contains(newText))
                flatList.add(list);
            if(locationName.contains( newText ))
                flatList.add( list );
            if(flatgender.contains(newText))
                flatList.add(list);
            if(flatmonth.contains(newText))
                flatList.add(list);
            if(descriptionName.contains(newText))
                flatList.add(list);
            if(flatrent.contains(newText))
                flatList.add(list);

        }
        flatPostAdapter.setFilter( flatList );
        return true;
    }

}
