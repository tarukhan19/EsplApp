package com.effizent.esplapp.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;


import com.effizent.esplapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class BitmapImageAdapter extends RecyclerView.Adapter<BitmapImageAdapter.CustomViewHodler>
{

    private Context mContext;
    ArrayList<Bitmap> bmList;

    public BitmapImageAdapter(Context context, ArrayList<Bitmap> photoBMList)
    {
        this.mContext = context;
        this.bmList = photoBMList;
    }

    @Override
    public CustomViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_uploadimage, parent, false);
        return new CustomViewHodler(itemView);    }

    @Override
    public void onBindViewHolder(CustomViewHodler holder, final int position) {
        final Bitmap bm = bmList.get(position);
//        Picasso.with(mContext)
//                .load(String.valueOf(bm))
//                .fit()
//                .into(holder.imageView);
        holder.imageView.setImageBitmap(bm);
        holder.crossIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bmList.remove(position);
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return bmList.size();
    }

    public class CustomViewHodler extends RecyclerView.ViewHolder {
        ImageView imageView,crossIV;
        public CustomViewHodler(View itemView) {
            super(itemView);
            imageView=(ImageView)itemView.findViewById(R.id.picIV);
            crossIV=itemView.findViewById(R.id.crossIV);
        }
    }
}

