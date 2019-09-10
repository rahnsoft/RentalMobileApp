package com.rit.basa_bari.details_Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.rit.basa_bari.R;
import com.rit.basa_bari.models.HostelUpload;

public class HostelDetailsActivity extends AppCompatActivity {
    public static final String TAG = HostelDetailsActivity.class.getSimpleName();
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;
    public static  final String PHONE_NUMBER_KEY="phoneNumber";
    private HostelUpload hostelUpload;
    private TextView hrentView, hlocationView, hphoneNumber, hdescription, hmonth;
    private ImageView himageView1,hphone;
    private static final int REQUEST_CALL = 1;
    private EditText txtMessage;
    private Button btnSms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_hostel_details );
        hostelUpload = new HostelUpload();
        hphone=findViewById( R.id.hphoneIcon );
        initialization();
        setTitle( "Details" );

        hphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall();
            }
        });
        txtMessage=findViewById(R.id.msgTxt);
        btnSms=findViewById(R.id.btnSend);
        btnSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    sendSMSMessage();
                }
                catch (Exception e){
                    Toast.makeText(HostelDetailsActivity.this, "SMS Failed to Send, Please try again", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private  void sendSMSMessage(){
        SmsManager smgr = SmsManager.getDefault();
        smgr.sendTextMessage(hphoneNumber.getText().toString(),null,txtMessage.getText().toString(),null,null);
        Toast.makeText(HostelDetailsActivity.this, "SMS Sent Successfully", Toast.LENGTH_SHORT).show();
    }


    private void makePhoneCall() {
        String number = hphoneNumber.getText().toString();
        if (number.trim().length() > 0) {

            if (ContextCompat.checkSelfPermission(HostelDetailsActivity.this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(HostelDetailsActivity.this,
                        new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            } else {
                String dial = "tel:" + number;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall();
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }



    public void initialization() {
        himageView1 = findViewById( R.id.imageViewHostel );
        hrentView = findViewById( R.id.hrentTV );
        hlocationView = findViewById( R.id.hlocationTv );
        hmonth = findViewById( R.id.hmonthId );
        hphoneNumber = findViewById( R.id.hphoneTv );
        hdescription = findViewById( R.id.hdescriptionTv );

        String phoneNo = getIntent().getStringExtra( "phone" );
        String descrip = getIntent().getStringExtra( "description" );
        String mon = getIntent().getStringExtra( "month" );
        String rent = getIntent().getStringExtra( "rent" );
        String location = getIntent().getStringExtra( "location" );
        String image = getIntent().getStringExtra( "image" );
        Glide.with( this ).load( image ).into( himageView1 );

        hrentView.setText( rent );
        hlocationView.setText( location );
        hmonth.setText( mon );
        hdescription.setText( descrip );
        hphoneNumber.setText( phoneNo );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.share_menu, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.nav_share) {
          /*  ShareLinkContent linkContent=new ShareLinkContent.Builder()
                    .setQuote( "Share on FaceBook" )
                    .setContentUrl( Uri.parse( "https://www.facebook.com" ) )
                    .build();
              if(ShareDialog.canShow( ShareLinkContent.class )){
                  shareDialog.show( linkContent );

              }*/
        }
        return super.onOptionsItemSelected( item );
    }

}
