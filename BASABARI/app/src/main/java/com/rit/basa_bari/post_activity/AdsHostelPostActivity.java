package com.rit.basa_bari.post_activity;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
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
import com.rit.basa_bari.models.HostelUpload;
import com.rit.basa_bari.show_post_activity.Show_Hostel_Activity;
import com.rit.basa_bari.show_post_activity.Show_Mess_Activity;

import java.util.ArrayList;
import java.util.List;

public class AdsHostelPostActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView himageView;
    TextInputEditText hrent, hphone, hlocation, hdescription;
    Spinner hmonth,hgender;
    Button hsubmit;
    ProgressDialog progressDialog;
    private Uri himageUri;
    private static final int SELECT_PHOTO = 100;
    private DatabaseReference mDatabaseReference;
    private StorageReference mStorageReference;
    private StorageTask mUploadTask;
    private Button btn;
    int PICK_IMAGE_MULTIPLE = 1;
    String imageEncoded;
    List<String> imagesEncodedList;
    private GridView gvGallery;
    private GalleryAdapter galleryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_ads_hostel_post );
        setTitle( "Post Ads For Hostel" );
        btn = findViewById(R.id.btn);
        gvGallery = (GridView)findViewById(R.id.gv);

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
        hsubmit=findViewById(R.id.hostelsubmitBTN);
//        himageView=findViewById(R.id.hostel_camera);
        hrent= findViewById(R.id.hostelRentET);
        hphone=findViewById(R.id.hostelphoneET);
        hlocation= findViewById(R.id.hostellocationET);
        hdescription=findViewById(R.id.hosteldescriptionET);
        hmonth=findViewById(R.id.hmonthSpinnerID);
        hgender=findViewById(R.id.hgenderSpinnerID);
        mDatabaseReference= FirebaseDatabase.getInstance().getReference("hostel");
        mStorageReference= FirebaseStorage.getInstance().getReference("hostel");


        hsubmit.setOnClickListener(this);

    }
    protected  void dialog(){
        final Handler handle = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                progressDialog.incrementProgressBy(5); // Incremented By Value 2
            }
        };

        progressDialog = new ProgressDialog(AdsHostelPostActivity.this);
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
                                    startActivity(new Intent(AdsHostelPostActivity.this, Show_Hostel_Activity.class));                            }
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

                    himageUri=data.getData();

                    // Get the cursor
                    Cursor cursor = getContentResolver().query(himageUri,
                            filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    imageEncoded  = cursor.getString(columnIndex);
                    cursor.close();

                    ArrayList<Uri> mArrayUri = new ArrayList<Uri>();
                    mArrayUri.add(himageUri);
                    galleryAdapter = new GalleryAdapter(getApplicationContext(),mArrayUri);
                    gvGallery.setAdapter(galleryAdapter);
                    gvGallery.setVerticalSpacing(gvGallery.getHorizontalSpacing());
                    ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) gvGallery
                            .getLayoutParams();
                    mlp.setMargins(0, gvGallery.getHorizontalSpacing(), 0, 0);

                } else {
                    if (data.getClipData() != null) {
                        ClipData mClipData = data.getClipData();
                        ArrayList<Uri> mArrayUri = new ArrayList<Uri>();
                        for (int i = 0; i < mClipData.getItemCount(); i++) {

                            ClipData.Item item = mClipData.getItemAt(i);
                            himageUri = item.getUri();
                            mArrayUri.add(himageUri);
                            // Get the cursor
                            Cursor cursor = getContentResolver().query(himageUri, filePathColumn, null, null, null);
                            // Move to first row
                            cursor.moveToFirst();

                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                            imageEncoded  = cursor.getString(columnIndex);
                            imagesEncodedList.add(imageEncoded);
                            cursor.close();

                            galleryAdapter = new GalleryAdapter(getApplicationContext(),mArrayUri);
                            gvGallery.setAdapter(galleryAdapter);
                            gvGallery.setVerticalSpacing(gvGallery.getHorizontalSpacing());
                            ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) gvGallery
                                    .getLayoutParams();
                            mlp.setMargins(0, gvGallery.getHorizontalSpacing(), 0, 0);

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
        if (v.getId() == R.id.hostelsubmitBTN) {
            dialog();
            if(mUploadTask!=null){
                Toast.makeText(AdsHostelPostActivity.this, "Image is in  progress", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(AdsHostelPostActivity.this, "Successfully Saved", Toast.LENGTH_SHORT).show();

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
        if(himageUri !=null) {
            StorageReference storageReference = mStorageReference.child(
                    System.currentTimeMillis()
                            + "." + getFileExtension(himageUri));
            mUploadTask = storageReference.putFile(himageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    String hostelMonth=hmonth.getSelectedItem().toString().trim();
                    String hostelGender=hgender.getSelectedItem().toString().trim();
                    Integer hostelRent =Integer.valueOf(hrent.getText().toString().trim());
                    String hostelPhone=hphone.getText().toString().trim();
                    String hostelLocation =hlocation.getText().toString().trim();
                    String hostelDescription =hdescription.getText().toString().trim();
                    HostelUpload hostelUpload=new HostelUpload(taskSnapshot.getDownloadUrl().toString(),hostelMonth,hostelGender,hostelRent,hostelPhone,hostelLocation,hostelDescription);
                    String hostelId=mDatabaseReference.push().getKey();
                    mDatabaseReference.child( hostelId ).setValue( hostelUpload );



                }
            }).addOnFailureListener( new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText( AdsHostelPostActivity.this,e.getMessage(),Toast.LENGTH_SHORT ).show();
                }
            } );
        }else{
            Toast.makeText( this, "No file selected", Toast.LENGTH_SHORT ).show();
        }
    }
}
