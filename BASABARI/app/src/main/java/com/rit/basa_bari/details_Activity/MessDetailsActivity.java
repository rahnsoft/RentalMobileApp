package com.rit.basa_bari.details_Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
/*import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;*/

import com.rit.basa_bari.R;
import com.rit.basa_bari.models.MessUpload;

public class MessDetailsActivity extends AppCompatActivity {
    public static final String TAG = MessDetailsActivity.class.getSimpleName();
        private MessUpload messUpload;
    private TextView rentView, locationView, phoneNumber, description, month,emailView;
    private ImageView imageView1,phone;
    private static final int REQUEST_CALL = 1;
    private EditText txtMessage;
    private Button btnSms;
    String phoneNo;
    String message;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;

   /* CallbackManager callbackManager;
    ShareDialog shareDialog;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        /* FacebookSdk.sdkInitialize( this );*/
        setContentView( R.layout.activity_details );
        messUpload = new MessUpload();
        phone=findViewById( R.id.phoneIcon );
        initialization();
        setTitle( "Details" );
        txtMessage=findViewById(R.id.msgTxt);
        btnSms=findViewById(R.id.btnSend);
        btnSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                  checkForSmsPermission();
                }
                catch (Exception e){
                    Toast.makeText(MessDetailsActivity.this, "SMS Failed to Send, Please try again", Toast.LENGTH_SHORT).show();
                }
            }
        });

        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall();
            }
        });
    }



    private void makePhoneCall() {
        String number = phoneNumber.getText().toString();
        if (number.trim().length() > 0) {

            if (ContextCompat.checkSelfPermission(MessDetailsActivity.this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MessDetailsActivity.this,
                        new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            } else {
                String dial = "tel:" + number;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
        }
    }
    private void checkForSmsPermission() {
        phoneNo = phoneNumber.getText().toString();
        message = txtMessage.getText().toString();
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS) !=
                PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Permission Not Granted");
            // Permission not yet granted. Use requestPermissions().
            // MY_PERMISSIONS_REQUEST_SEND_SMS is an
            // app-defined int constant. The callback method gets the
            // result of the request.
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS},
                    MY_PERMISSIONS_REQUEST_SEND_SMS);
        } else {
            // Permission already granted. Enable the message button.
            enableSmsButton();
        }
    }

    private void enableSmsButton() {

        SmsManager smgr = SmsManager.getDefault();
        smgr.sendTextMessage(phoneNo,null,message,null,null);
        Toast.makeText(MessDetailsActivity.this, "SMS Sent Successfully", Toast.LENGTH_SHORT).show();
    }

//    protected void sendSMSMessage() {
//        phoneNo = phoneNumber.getText().toString();
//        message = txtMessage.getText().toString();
//
//        if (ContextCompat.checkSelfPermission(this,
//                Manifest.permission.SEND_SMS)
//                != PackageManager.PERMISSION_GRANTED) {
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
//                    Manifest.permission.SEND_SMS)) {
//            } else {
//                ActivityCompat.requestPermissions(this,
//                        new String[]{Manifest.permission.SEND_SMS},
//                        MY_PERMISSIONS_REQUEST_SEND_SMS);
//            }
//        }
//    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall();
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (permissions[0].equalsIgnoreCase(Manifest.permission.SEND_SMS)
                        && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED) {
                    enableSmsButton();
                    // Permission was granted.
                } else {
                    // Permission denied.
                    Log.d(TAG,"failure_permission");
                    Toast.makeText(MessDetailsActivity.this,
                            "failure_permission",
                            Toast.LENGTH_SHORT).show();
                    // Disable the message button.
                    disableSmsButton();
                }
            }
        }
    }
    private void disableSmsButton() {
        Toast.makeText(this, "SMS usage disabled", Toast.LENGTH_LONG).show();
        Button smsButton = (Button) findViewById(R.id.btnSend);
        smsButton.setVisibility(View.INVISIBLE);
        Button retryButton = (Button) findViewById(R.id.btnSend);
        retryButton.setVisibility(View.VISIBLE);
    }


    public void initialization() {
        imageView1 = findViewById( R.id.imageViewMess );
        rentView = findViewById( R.id.rentTV );
        locationView = findViewById( R.id.locationTv );
        month = findViewById( R.id.monthId );
        phoneNumber = findViewById( R.id.phoneTv );
        description = findViewById( R.id.descriptionTv );

        String phoneNo = getIntent().getStringExtra( "phone" );
        String descrip = getIntent().getStringExtra( "description" );
        String mon = getIntent().getStringExtra( "month" );
        String rent = getIntent().getStringExtra( "rent" );
        String location = getIntent().getStringExtra( "location" );
        String image = getIntent().getStringExtra( "image" );
        Glide.with( this ).load( image ).into( imageView1 );

        rentView.setText( rent );
        locationView.setText( location );
        month.setText( mon );
        description.setText( descrip );
        phoneNumber.setText( phoneNo );
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


