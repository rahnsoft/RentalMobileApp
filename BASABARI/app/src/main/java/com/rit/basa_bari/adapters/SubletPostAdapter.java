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
import com.rit.basa_bari.details_Activity.SubletDetailsActivity;
import com.rit.basa_bari.models.SubletUpload;

import java.util.ArrayList;
import java.util.List;

public class SubletPostAdapter extends RecyclerView.Adapter<SubletPostAdapter.SubletPostViewHolder> {
    private Context context;
    private List<SubletUpload> subletUploads;

    public SubletPostAdapter(Context context, List<SubletUpload> subletUploads) {
        this.context = context;
        this.subletUploads = subletUploads;
    }

    @NonNull
    @Override
    public SubletPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from( context);
        View view = inflater.inflate(R.layout.show_sublet_item, null);
        SubletPostAdapter.SubletPostViewHolder subletPostViewHolder=new SubletPostAdapter.SubletPostViewHolder( view );
        return subletPostViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull final SubletPostViewHolder holder, final int position) {
        final SubletUpload currentUpload=subletUploads.get( position );

        holder.stextRent.setText(  currentUpload.getSubletRent()+" KSHS");
        holder.stextPhoneNumber.setText( currentUpload.getSphone());
        holder.stextLocation.setText( currentUpload.getSlocation());
        holder.stextGender.setText( currentUpload.getSgender() );
        holder.stextMonth.setText( currentUpload.getSmonth() );
        //Glide.with( context ).load(currentUpload.getImage()).into( holder.imageView );
        holder.sbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent( v.getContext(), SubletDetailsActivity.class );
                intent.putExtra( "rent", holder.stextRent.getText().toString() );
                intent.putExtra( "location", currentUpload.getSlocation() );
                intent.putExtra( "image", String.valueOf( currentUpload.getSimage() ) );
                intent.putExtra( "month",currentUpload.getSmonth());
                intent.putExtra( "phone",currentUpload.getSphone());
                intent.putExtra( "description",currentUpload.getSdescription());
                v.getContext().startActivity( intent );

            }
        } );
    }

    @Override
    public int getItemCount() {
        return subletUploads.size();
    }

    public class SubletPostViewHolder extends RecyclerView.ViewHolder {
        TextView stextRent, stextPhoneNumber, stextLocation, stextGender, stextMonth;
        ImageView simageView;
        CardView sbutton;


        public SubletPostViewHolder(@NonNull View itemView) {
            super(itemView);
            stextRent =itemView.findViewById( R.id.srentTV );
            stextPhoneNumber =itemView.findViewById( R.id.sphoneNumberTV );
            stextLocation =itemView.findViewById( R.id.slocationTV );
            simageView=itemView.findViewById( R.id.imageView );
            stextGender =itemView.findViewById( R.id.sgenderTv );
            stextMonth =itemView.findViewById( R.id.smonthTV );
            sbutton =itemView.findViewById( R.id.scardView );
        }
    }

    public void setFilter(ArrayList<SubletUpload> subletList){
        subletUploads=new ArrayList <>( );
        subletUploads.addAll( subletList );
        notifyDataSetChanged();
    }
}
