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
import com.rit.basa_bari.details_Activity.HostelDetailsActivity;
import com.rit.basa_bari.models.HostelUpload;

import java.util.ArrayList;
import java.util.List;

public class HostelPostAdapter extends RecyclerView.Adapter<HostelPostAdapter.HostelPostViewHolder>{
    private Context context;
    private List<HostelUpload> hostelUploads;

    public HostelPostAdapter(Context context, List<HostelUpload> hostelUploads) {
        this.context = context;
        this.hostelUploads = hostelUploads;
    }

    @NonNull
    @Override
    public HostelPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from( context);
        View view = inflater.inflate(R.layout.show_hostel_item, null);
        HostelPostViewHolder hostelPostViewHolder =new HostelPostViewHolder( view );
        return hostelPostViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final HostelPostViewHolder holder, final int position) {
        final HostelUpload currentUpload= hostelUploads.get(position);
        holder.htextRent.setText(  currentUpload.getHostelRent()+" KSHS");
        holder.htextPhoneNumber.setText( currentUpload.getHphone());
        holder.htextLocation.setText( currentUpload.getHlocation());
        holder.htextGender.setText( currentUpload.getGender() );
        holder.htextMonth.setText( currentUpload.getHmonth() );
        holder.hbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( v.getContext(), HostelDetailsActivity.class );
                intent.putExtra( "rent", holder.htextRent.getText().toString() );
                intent.putExtra( "location", currentUpload.getHlocation() );
                intent.putExtra( "image", String.valueOf( currentUpload.getHimage() ) );
                intent.putExtra( "month",currentUpload.getHmonth());
                intent.putExtra( "phone",currentUpload.getHphone());
                intent.putExtra( "description",currentUpload.getHdescription());
                v.getContext().startActivity( intent );
            }
        });
    }

    @Override
    public int getItemCount() {
        return hostelUploads.size();
    }




    public class HostelPostViewHolder extends RecyclerView.ViewHolder {
        TextView htextRent,htextPhoneNumber,htextLocation,htextGender,htextMonth;
        ImageView imageView;
        CardView hbutton;

        public HostelPostViewHolder(@NonNull View itemView) {
            super(itemView);
            htextRent=itemView.findViewById( R.id.hrentTV );
            htextPhoneNumber=itemView.findViewById( R.id.hphoneNumberTV );
            htextLocation=itemView.findViewById( R.id.hlocationTV );
            imageView=itemView.findViewById( R.id.imageView );
            htextGender=itemView.findViewById( R.id.hgenderTv );
            htextMonth=itemView.findViewById( R.id.hmonthTV );
            hbutton=itemView.findViewById( R.id.hcardView );


        }
    }
    public void setFilter(ArrayList<HostelUpload> hostelList) {
        hostelUploads=new ArrayList <>( );
        hostelUploads.addAll( hostelList );
        notifyDataSetChanged();
    }
}
