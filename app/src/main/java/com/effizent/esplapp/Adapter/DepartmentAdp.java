package com.effizent.esplapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.effizent.esplapp.Model.DepartmentDTO;
import com.effizent.esplapp.R;

import java.util.ArrayList;

public class DepartmentAdp extends RecyclerView.Adapter<DepartmentAdp.CustomViewHodler> {
    private Context mContext;
    ArrayList<String>  departmentlistnamerray;

    TextView textView;


    public DepartmentAdp(Context context, TextView textView, ArrayList<String> departmentlistnamerray) {
        this.mContext = context;
        this.departmentlistnamerray = departmentlistnamerray;
        this.textView = textView;
    }

    @Override
    public CustomViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_listing, parent, false);
        return new CustomViewHodler(itemView);
    }

    @Override
    public void onBindViewHolder(final CustomViewHodler holder, final int position) {


        holder.text.setText(departmentlistnamerray.get(position));
        holder.imageView.setVisibility(View.GONE);


    }

    @Override
    public int getItemCount() {
        return departmentlistnamerray == null ? 0 : departmentlistnamerray.size();
    }

    public class CustomViewHodler extends RecyclerView.ViewHolder {
        //            ImageView imageView;
        ImageView imageView;
        TextView text;

        public CustomViewHodler(View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.text);
            imageView = (ImageView) itemView.findViewById(R.id.cross);
        }
    }
}

