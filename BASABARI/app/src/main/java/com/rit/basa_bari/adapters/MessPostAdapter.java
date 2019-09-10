package com.rit.basa_bari.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rit.basa_bari.R;
import com.rit.basa_bari.details_Activity.MessDetailsActivity;
import com.rit.basa_bari.models.MessUpload;


import java.util.ArrayList;
import java.util.List;


public class MessPostAdapter extends RecyclerView.Adapter<MessPostAdapter.MessPostViewHolder>  {
    private Context context;
    private  List<MessUpload>messUploads;


    public MessPostAdapter(Context context,List<MessUpload> messUploads) {
        this.context = context;
        this.messUploads =messUploads ;

    }

    @NonNull
    @Override
    public MessPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from( context);
        View view = inflater.inflate(R.layout.show_mess_item, null);
        MessPostViewHolder messPostViewHolder=new MessPostViewHolder( view );
        return messPostViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MessPostViewHolder holder, final int position) {
        final MessUpload currentUpload=messUploads.get( position );

        holder.textRent.setText(  currentUpload.getMessRent()+" KSHS");
        holder.textPhoneNumber.setText( currentUpload.getPhone());
        holder.textLocation.setText( currentUpload.getLocation());
        holder.textGender.setText( currentUpload.getGender() );
        holder.textMonth.setText( currentUpload.getMonth() );
        //Glide.with( context ).load(currentUpload.getImage()).into( holder.imageView );
        holder.button.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent( v.getContext(), MessDetailsActivity.class );
                intent.putExtra( "rent", holder.textRent.getText().toString() );
                intent.putExtra( "location", currentUpload.getLocation() );
                intent.putExtra( "image", String.valueOf( currentUpload.getImage() ) );
                intent.putExtra( "month",currentUpload.getMonth());
                intent.putExtra( "phone",currentUpload.getPhone());
                intent.putExtra( "description",currentUpload.getDescription());
                v.getContext().startActivity( intent );

            }
        } );

    }

    @Override
    public int getItemCount() {
        return messUploads.size();
    }



    public  class MessPostViewHolder extends RecyclerView.ViewHolder{
        TextView textRent,textPhoneNumber,textLocation,textGender,textMonth;
        ImageView imageView;
        CardView button;
        public MessPostViewHolder(@NonNull View itemView) {
            super( itemView );
            textRent=itemView.findViewById( R.id.rentTV );
            textPhoneNumber=itemView.findViewById( R.id.phoneNumberTV );
            textLocation=itemView.findViewById( R.id.locationTV );
            imageView=itemView.findViewById( R.id.imageView );
            textGender=itemView.findViewById( R.id.genderTv );
            textMonth=itemView.findViewById( R.id.monthTV );
            button=itemView.findViewById( R.id.cardView );


        }

    }
    public void setFilter(ArrayList<MessUpload>messList){
        messUploads=new ArrayList <>( );
        messUploads.addAll( messList );
        notifyDataSetChanged();
    }



}
