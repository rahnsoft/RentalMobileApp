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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rit.basa_bari.R;
import com.rit.basa_bari.adapters.SubletPostAdapter;
import com.rit.basa_bari.models.SubletUpload;

import java.util.ArrayList;

public class Show_Sublet_Activity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private static final String TAG = Show_Sublet_Activity.class.getSimpleName();

    private RecyclerView recyclerView;
    public SubletPostAdapter subletPostAdapter;


    private ArrayList<SubletUpload> subletUploadArrayList;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setTitle( "To_Let(sublet)" );
        recyclerView = findViewById( R.id.subletRecyclerView );
        recyclerView.setHasFixedSize( true );
        recyclerView.setLayoutManager( new LinearLayoutManager( this ) );
        subletUploadArrayList= new ArrayList <>();
        databaseReference = FirebaseDatabase.getInstance().getReference( "sublet" );
        retriveData();


    }

    private void retriveData() {
        databaseReference.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot setData : dataSnapshot.getChildren()) {
                    SubletUpload subletUpload = setData.getValue( SubletUpload.class );
                    subletUploadArrayList.add( subletUpload );
                }
                subletPostAdapter = new SubletPostAdapter( Show_Sublet_Activity.this, subletUploadArrayList );
                recyclerView.setAdapter( subletPostAdapter );


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText( Show_Sublet_Activity.this, databaseError.getMessage(), Toast.LENGTH_SHORT ).show();

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
        ArrayList<SubletUpload> subletList=new ArrayList <>();
        for(SubletUpload list:subletUploadArrayList){
            String subrent=list.getSubrentString().toLowerCase();
            String locationName=list.getSlocation().toLowerCase();
            String descriptionsub=list.getSdescription().toLowerCase();
            if(descriptionsub.contains(newText))
                subletList.add(list);
            if(subrent.contains(newText))
                subletList.add(list);
            if(locationName.contains( newText ))
                subletList.add( list );
        }
        subletPostAdapter.setFilter( subletList );
        return true;
    }
}
