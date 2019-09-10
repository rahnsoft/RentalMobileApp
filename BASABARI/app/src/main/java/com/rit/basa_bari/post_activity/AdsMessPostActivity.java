package com.rit.basa_bari.post_activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.rit.basa_bari.R;
import com.rit.basa_bari.adapters.GalleryAdapter;
import com.rit.basa_bari.adapters.MessGalleryAdapter;
import com.rit.basa_bari.models.MessUpload;
import com.rit.basa_bari.show_post_activity.Show_Mess_Activity;

import java.util.ArrayList;
import java.util.List;

public class AdsMessPostActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView imageView;
    TextInputEditText rent, phone, location, description;
    Spinner month,gender;
    Button submit;
    ProgressDialog progressDialog;
    private Uri imageUri;
    private static final int SELECT_PHOTO = 100;
    private DatabaseReference mDatabaseReference;
    private StorageReference mStorageReference;
    private StorageTask mUploadTask;
    private Button btn;
    int PICK_IMAGE_MULTIPLE = 1;
    String imageEncoded;
    List<String> imagesEncodedList;
    private GridView mgvGallery;
    private MessGalleryAdapter mgalleryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_ads_mess_post );
        setTitle( "Post Ads For Mess" );

        //imageView = findViewById( R.id.camera );
        rent = findViewById( R.id.messRentET );
        mgvGallery = (GridView)findViewById(R.id.mgv);
        btn = findViewById(R.id.mbtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"), PICK_IMAGE_MULTIPLE);
            }
        });
        phone = findViewById( R.id.phoneET );
        location = findViewById( R.id.locationET );
        description = findViewById( R.id.descriptionET );
        month=findViewById(R.id.monthSpinnerID);
        gender=findViewById(R.id.genderSpinnerID);
        submit = findViewById( R.id.submitBTN );

        mDatabaseReference = FirebaseDatabase.getInstance().getReference( "mess" );
        mStorageReference = FirebaseStorage.getInstance().getReference( "mess" );

//
//        imageView.setOnClickListener(
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        openGallery();
//                    }
//                } );
        submit.setOnClickListener( this );
    }


    protected  void dialog(){
        final Handler handle = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                progressDialog.incrementProgressBy(5); // Incremented By Value 2
            }
        };

        progressDialog = new ProgressDialog(AdsMessPostActivity.this);
        progressDialog.setMax(100); // Progress Dialog Max Value
        progressDialog.setMessage("UpLoading Your Post..."); // Setting Message
        progressDialog.setTitle("Please Wait"); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL); // Progress Dialog Style Horizontal
        progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (progressDialog.getProgress() <= progressDialog.getMax()) {
                        Thread.sleep(200);
                        handle.sendMessage(handle.obtainMessage());
                        if (progressDialog.getProgress() == progressDialog.getMax()) {
                            progressDialog.dismiss();
                            progressDialog.setOnDismissListener(new DialogInterface.OnDismissListener(){
                                @Override
                                public void onDismiss(DialogInterface dialog) {
                                    startActivity(new Intent(AdsMessPostActivity.this,Show_Mess_Activity.class));                            }
                            });
                        }}
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            // When an Image is picked
            if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                imagesEncodedList = new ArrayList<String>();
                if(data.getData()!=null){

                    imageUri=data.getData();

                    // Get the cursor
                    Cursor cursor = getContentResolver().query(imageUri,
                            filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    imageEncoded  = cursor.getString(columnIndex);
                    cursor.close();

                    ArrayList<Uri> mArrayUri = new ArrayList<Uri>();
                    mArrayUri.add(imageUri);
                    mgalleryAdapter = new MessGalleryAdapter(getApplicationContext(),mArrayUri);
                    mgvGallery.setAdapter(mgalleryAdapter);
                    mgvGallery.setVerticalSpacing(mgvGallery.getHorizontalSpacing());
                    ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) mgvGallery
                            .getLayoutParams();
                    mlp.setMargins(0, mgvGallery.getHorizontalSpacing(), 0, 0);

                } else {
                    if (data.getClipData() != null) {
                        ClipData mClipData = data.getClipData();
                        ArrayList<Uri> mArrayUri = new ArrayList<Uri>();
                        for (int i = 0; i < mClipData.getItemCount(); i++) {

                            ClipData.Item item = mClipData.getItemAt(i);
                            imageUri = item.getUri();
                            mArrayUri.add(imageUri);
                            // Get the cursor
                            Cursor cursor = getContentResolver().query(imageUri, filePathColumn, null, null, null);
                            // Move to first row
                            cursor.moveToFirst();

                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                            imageEncoded  = cursor.getString(columnIndex);
                            imagesEncodedList.add(imageEncoded);
                            cursor.close();

                            mgalleryAdapter = new MessGalleryAdapter(getApplicationContext(),mArrayUri);
                            mgvGallery.setAdapter(mgalleryAdapter);
                            mgvGallery.setVerticalSpacing(mgvGallery.getHorizontalSpacing());
                            ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) mgvGallery
                                    .getLayoutParams();
                            mlp.setMargins(0, mgvGallery.getHorizontalSpacing(), 0, 0);

                        }
                        Log.v("LOG_TAG", "Selected Images" + mArrayUri.size());
                    }
                }
            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.submitBTN) {
            dialog();
            if(mUploadTask!=null){
                Toast.makeText(AdsMessPostActivity.this, "Image is progess", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(AdsMessPostActivity.this, "Successfully Saved", Toast.LENGTH_SHORT).show();
                submitPost();

            }
        }

    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType( cR.getType( uri ) );
    }

    private void submitPost() {

        if (imageUri != null) {
            StorageReference storageReference = mStorageReference.child(
                    System.currentTimeMillis()
                            + "." + getFileExtension( imageUri ) );
            mUploadTask=  storageReference.putFile( imageUri ).addOnSuccessListener( new OnSuccessListener <UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    String messMonth=month.getSelectedItem().toString().trim();
                    String messGender=gender.getSelectedItem().toString().trim();
                    Integer messRent= Integer.valueOf( rent.getText().toString().trim() );
                    String messPhone=phone.getText().toString().trim();
                    String messLocation=location.getText().toString().trim();
                    String messDescription=description.getText().toString().trim();

                    MessUpload messUpload=new MessUpload(taskSnapshot.getDownloadUrl().toString(),messMonth,messGender,messRent,messPhone,messLocation,messDescription);
                    String messId=mDatabaseReference.push().getKey();
                    mDatabaseReference.child( messId ).setValue( messUpload );

                }
            } ).addOnFailureListener( new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText( AdsMessPostActivity.this,e.getMessage(),Toast.LENGTH_SHORT ).show();
                }
            } );
        } else {
            Toast.makeText( this, "No file selected", Toast.LENGTH_SHORT ).show();
        }
    }
}


