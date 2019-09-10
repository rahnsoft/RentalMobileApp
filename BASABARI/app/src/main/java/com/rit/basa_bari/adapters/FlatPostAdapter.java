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
import com.rit.basa_bari.details_Activity.FlatDetailsActivity;
import com.rit.basa_bari.models.FlatUpload;

import java.util.ArrayList;
import java.util.List;

public class FlatPostAdapter extends RecyclerView.Adapter<FlatPostAdapter.FlatPostViewHolder> {
    private Context context;
    private List<FlatUpload> flatUploads;


    public FlatPostAdapter(Context context, List<FlatUpload> flatUploads) {
        this.context = context;
        this.flatUploads = flatUploads;
    }




    @NonNull
    @Override
    public FlatPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from( context);
        View view = inflater.inflate(R.layout.show_flat_item, null);
        FlatPostViewHolder flatPostViewHolder =new FlatPostViewHolder( view );
        return flatPostViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final FlatPostViewHolder holder, final int position) {
        final FlatUpload currentUpload= flatUploads.get(position);
        holder.ftextRent.setText(  currentUpload.getFlatRent()+" KSHS");
        holder.ftextPhoneNumber.setText( currentUpload.getFphone());
        holder.ftextLocation.setText( currentUpload.getFlocation());
        holder.ftextGender.setText( currentUpload.getGender() );
        holder.ftextMonth.setText( currentUpload.getMonth() );
        holder.fbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( v.getContext(), FlatDetailsActivity.class );
                intent.putExtra( "rent", holder.ftextRent.getText().toString() );
                intent.putExtra( "location", currentUpload.getFlocation() );
                intent.putExtra( "image", String.valueOf( currentUpload.getFimage() ) );
                intent.putExtra( "month",currentUpload.getMonth());
                intent.putExtra( "phone",currentUpload.getFphone());
                intent.putExtra( "description",currentUpload.getFdescription());
                v.getContext().startActivity( intent );
            }
        });
    }

    @Override
    public int getItemCount() {
        return flatUploads.size();
    }

    public class FlatPostViewHolder extends RecyclerView.ViewHolder {
        TextView ftextRent,ftextPhoneNumber,ftextLocation,ftextGender,ftextMonth;
        ImageView imageView;
        CardView fbutton;

        public FlatPostViewHolder(@NonNull View itemView) {
            super(itemView);
            ftextRent=itemView.findViewById( R.id.frentTV );
            ftextPhoneNumber=itemView.findViewById( R.id.fphoneNumberTV );
            ftextLocation=itemView.findViewById( R.id.flocationTV );
            imageView=itemView.findViewById( R.id.imageView );
            ftextGender=itemView.findViewById( R.id.fgenderTv );
            ftextMonth=itemView.findViewById( R.id.fmonthTV );
            fbutton=itemView.findViewById( R.id.fcardView );
        }
    }
    public void setFilter(ArrayList<FlatUpload> flatList){
        flatUploads=new ArrayList <>( );
        flatUploads.addAll( flatList );
        notifyDataSetChanged();
    }
}
