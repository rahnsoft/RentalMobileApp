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
import com.rit.basa_bari.adapters.HostelPostAdapter;
import com.rit.basa_bari.models.HostelUpload;

import java.util.ArrayList;

public class Show_Hostel_Activity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private static final String TAG = Show_Hostel_Activity.class.getSimpleName();

    private RecyclerView recyclerView;
    public HostelPostAdapter hostelPostAdapter;


    private ArrayList<HostelUpload> hostelUploadArrayList;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_show__hostel );
        setTitle( "To_Let(Hostel)" );
        recyclerView = findViewById( R.id.hostelRecyclerView );
        recyclerView.setHasFixedSize( true );
        recyclerView.setLayoutManager( new LinearLayoutManager( this ) );
        hostelUploadArrayList= new ArrayList <>();
        databaseReference = FirebaseDatabase.getInstance().getReference( "hostel" );
        retriveData();



    }

    private void retriveData() {
        databaseReference.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot setData : dataSnapshot.getChildren()) {
                    HostelUpload hostelUpload = setData.getValue( HostelUpload.class );
                    hostelUploadArrayList.add( hostelUpload );
                }
                hostelPostAdapter = new HostelPostAdapter( Show_Hostel_Activity.this, hostelUploadArrayList );
                recyclerView.setAdapter( hostelPostAdapter );


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText( Show_Hostel_Activity.this, databaseError.getMessage(), Toast.LENGTH_SHORT ).show();

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
        TextView rent;
        newText =newText.toLowerCase();
        ArrayList<HostelUpload> hostelList=new ArrayList <>();
        for(HostelUpload list:hostelUploadArrayList){
            String locationName=list.getHlocation().toLowerCase();
            String descriptionName=list.getHdescription().toLowerCase();
            String monthName=list.getHmonth().toLowerCase();
            String phoneNumber=list.getHphone().toLowerCase();
            String genderName=list.getGender().toLowerCase();
            String rentAmount=list.getHostelR().toLowerCase();
            if(rentAmount.contains(newText))
                hostelList.add(list);
             if(locationName.contains( newText ))
                hostelList.add( list );
                   if(monthName.contains(newText))
                hostelList.add(list);
            if(genderName.contains(newText))
                hostelList.add(list);

            if(descriptionName.contains(newText))
                hostelList.add(list);
            if(phoneNumber.contains(newText))
                hostelList.add(list);

        }
        hostelPostAdapter.setFilter( hostelList );
        return true;
    }
}
