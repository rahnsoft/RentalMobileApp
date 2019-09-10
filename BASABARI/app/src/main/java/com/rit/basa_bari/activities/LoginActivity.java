package com.rit.basa_bari.activities;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rit.basa_bari.MainActivity;
import com.rit.basa_bari.R;
import com.rit.basa_bari.models.OwnerProfile;
import com.rit.basa_bari.models.RenterProfile;
import com.rit.basa_bari.show_post_activity.Show_Flat_Activity;
import com.rit.basa_bari.show_post_activity.Show_Hostel_Activity;
import com.rit.basa_bari.show_post_activity.Show_Mess_Activity;
import com.rit.basa_bari.show_post_activity.Show_Sublet_Activity;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    TextInputEditText email,password;
    public static boolean LOGCHECKER=false;
    Button login;
    List<OwnerProfile> ownerProfileList;
    List<RenterProfile> renterProfileList;
    DatabaseReference oDatabaseReference,rDatabaseReference;
    OwnerProfile ownerProfile;
    RenterProfile renterProfile;
    RadioGroup loginrb;
    String userType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Sign In");
        initializationView();


        ownerProfileList=new ArrayList<>();
        renterProfileList=new ArrayList<>();
        oDatabaseReference= FirebaseDatabase.getInstance().getReference("OwnerProfile");
        rDatabaseReference=FirebaseDatabase.getInstance().getReference("RenterProfile");


        oDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot setData:dataSnapshot.getChildren()){
                    OwnerProfile ownerProfile=setData.getValue(OwnerProfile.class);
                    ownerProfileList.add(ownerProfile);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText( LoginActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        rDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot setData:dataSnapshot.getChildren()){
                    RenterProfile renterProfile=setData.getValue(RenterProfile.class);
                    renterProfileList.add(renterProfile);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText( LoginActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        clickButton();

    }
    public void initializationView(){
        email=findViewById(R.id.emailEt);
        password=findViewById(R.id.passwordEt);
        login=findViewById(R.id.loginBtn);
        loginrb=findViewById(R.id.userTypelog);
    }
    public void clickButton(){
        login.setOnClickListener(this);
           }
    private void addNotification() {
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Intent notificationIntent = new Intent(this, Show_Flat_Activity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.flat_icon)
                        .setContentTitle("Hello there From NaniApp!")
                        .setSound(soundUri)
                        .setAutoCancel(true)
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText("We found a house matching your Flat Preference.Click Here to View It"))
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);



        builder.setContentIntent(contentIntent);
        Intent notificationIntent1 = new Intent(this, Show_Mess_Activity.class);
        PendingIntent contentIntent1 = PendingIntent.getActivity(this, 1, notificationIntent1,
                PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder1 =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.mess_icon)
                        .setContentTitle("Hello there From NaniApp!")
                        .setAutoCancel(true)
                        .setSound(soundUri)
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText("We found a house matching your Mess Preference.Click Here to View It"))
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);



        builder1.setContentIntent(contentIntent1);
        Intent notificationIntent2 = new Intent(this, Show_Sublet_Activity.class);
        PendingIntent contentIntent2 = PendingIntent.getActivity(this, 2, notificationIntent2,
                PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder2 =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.sublet_icon)
                        .setContentTitle("Hello there From NaniApp!")
                        .setAutoCancel(true)
                        .setSound(soundUri)
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText("We found a house matching your Mess Preference.Click Here to View It"))
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);



        builder2.setContentIntent(contentIntent2);
        Intent notificationIntent3 = new Intent(this, Show_Hostel_Activity.class);
        PendingIntent contentIntent3 = PendingIntent.getActivity(this, 3, notificationIntent3,
                PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder3 =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.hostel_icon)
                        .setContentTitle("Hello there From NaniApp!")
                        .setAutoCancel(true)
                        .setSound(soundUri)
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText("We found a house matching your Mess Preference.Click Here to View It"))
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);



        builder3.setContentIntent(contentIntent3);

        // Add as notification

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
        manager.notify(1,builder1.build());
        manager.notify(2,builder2.build());
        manager.notify(3,builder3.build());
    }

    public boolean checkValidity() {
        View focusView = null;
        loginrb=findViewById(R.id.userTypelog);
        boolean cancel = false;
        String uEmail = email.getText().toString();
        String uPassword = password.getText().toString();

        if (TextUtils.isEmpty(uEmail)) {
            // focusView=userName;
            cancel = true;
            email.setError("Enter a valid email");
        }else if(!Patterns.EMAIL_ADDRESS.matcher(uEmail).matches()){
            cancel=true;
            email.setError("Invalid Email Address");
            email.requestFocus();
        } else if (TextUtils.isEmpty(uPassword)) {
            // focusView = pass;
            cancel = true;
            password.setError("Enter a valid password");
        }else if(loginrb.getCheckedRadioButtonId()==-1){
            cancel =true;
            Toast.makeText(this, "Radio Button not Selected", Toast.LENGTH_SHORT).show();

        }

        return cancel;
    }

    @Override
    public void onClick(View view) {
        String uEmail=email.getText().toString().trim();
        String uPassword=password.getText().toString().trim();

        if(view.getId()==R.id.loginBtn){

            if(checkValidity()){
                Toast.makeText( this,"Ooopss!! you might have left something",Toast.LENGTH_LONG ).show();

            }else {

//                intent1.putExtra("Intent1_EMAIL",uEmail);
//                intent1.putExtra("Intent1_PASSWORD",uPassword);
//                startActivity(intent1);


                int i,j;
                for(i=0;i<ownerProfileList.size();i++){
                    ownerProfile=ownerProfileList.get(i);
                }
                String oEmail=ownerProfile.getEmail();
                String oPass=ownerProfile.getPassword();
                String oName=ownerProfile.getName();
                String imageName="Owner's Profile";
                RadioButton logOwner=findViewById(R.id.logOwnerRb);

                if(oEmail.equals(uEmail)&& oPass.equals(uPassword) && logOwner.isChecked()){
                    LOGCHECKER=true;
                    //Toast.makeText( LoginActivity.this,"Owner Matched!!!",Toast.LENGTH_LONG ).show();
                    Intent intent=new Intent(LoginActivity.this,ProfileActivity.class);
                    intent.putExtra("IMAGE_NAME",imageName);
                    intent.putExtra("NAME",oName);
                    intent.putExtra("PASSWORD",oPass);
                    intent.putExtra("EMAIL",oEmail);
                    startActivity(intent);

                }else {
                    for(j=0;j<renterProfileList.size();j++){
                        renterProfile=renterProfileList.get(j);
                    }
                    String rEmail=renterProfile.getEmail();
                    String rPass=renterProfile.getPassword();
                    String rName=renterProfile.getName();
                    String imagName="Renter's Profile";
                    RadioButton logRenter=findViewById(R.id.logRenterRb);

                    if(rEmail.equals(uEmail)&& rPass.equals(uPassword) && logRenter.isChecked()){
                        LOGCHECKER=true;
                        Intent intent=new Intent(LoginActivity.this,ProfileActivity.class);
                        intent.putExtra("IMAGE_NAME",imagName);
                        intent.putExtra("NAME",rName);
                        intent.putExtra("PASSWORD",rPass);
                        intent.putExtra("EMAIL",rEmail);
                        startActivity(intent);

                        addNotification();
                        // Toast.makeText( LoginActivity.this,"Renter Matched!!!",Toast.LENGTH_LONG ).show();

                    }else{
                        Toast.makeText( this,"UnAuthorised Access!! Check Given Credentials" +
                                "",Toast.LENGTH_LONG ).show();
                    }

                }


            }


        }

    }
}
