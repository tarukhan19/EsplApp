package com.effizent.esplapp.Adapter;

import android.content.Context;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.effizent.esplapp.R;
import com.effizent.esplapp.RetroApiResponses.Timeline_Post_List;
import com.effizent.esplapp.databinding.ItemDashboardBinding;
import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.CustomViewHodler> {
    private Context mContext;
    ArrayList<Timeline_Post_List> timelinePostDTOArrayList;
    ItemDashboardBinding binding;



    public PostAdapter(Context context, ArrayList<Timeline_Post_List> timelinePostDTOArrayList) {
        this.mContext = context;
        this.timelinePostDTOArrayList = timelinePostDTOArrayList;
    }

    @Override
    public CustomViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_dashboard, parent, false);
        return new CustomViewHodler(binding);
    }

    @Override
    public void onBindViewHolder(final CustomViewHodler h, final int position) {


        if (h instanceof CustomViewHodler)
        {

            final CustomViewHodler holder = (CustomViewHodler) h;

            try {
                Log.e("size",timelinePostDTOArrayList.get(position).getTimelineImageDTOArrayList().get(0).getImage()+"");


                if (timelinePostDTOArrayList.get(position).getTitle().isEmpty())
                {
                    holder.binding.titleTV.setVisibility(View.GONE);
                }
                else
                {
                    holder.binding.titleTV.setVisibility(View.VISIBLE);
                    holder.binding.titleTV.setText(fromBase64(timelinePostDTOArrayList.get(position).getTitle()));

                }

                if (timelinePostDTOArrayList.get(position).getDescription().isEmpty())
                {
                    holder.binding.descriptionTV.setVisibility(View.GONE);
                }
                else
                {
                    holder.binding.descriptionTV.setVisibility(View.VISIBLE);
                    holder.binding.descriptionTV.setText(fromBase64(timelinePostDTOArrayList.get(position).getDescription()));

                }
                holder.binding.nameTV.setText(timelinePostDTOArrayList.get(position).getName());
                holder.binding.timeTV.setText(timelinePostDTOArrayList.get(position).getDate());

                if (!timelinePostDTOArrayList.get(position).getProfilePic().isEmpty())
                {
                    Picasso.with(mContext)
                            .load(timelinePostDTOArrayList.get(position).getProfilePic())
                            .fit().placeholder(R.mipmap.defaultpic)
                            .into(holder.binding.profileImage);
                }




                if ((timelinePostDTOArrayList.get(position).getTimelineImageDTOArrayList().size())==0)
                {
                    Log.e("size",timelinePostDTOArrayList.get(position).getTimelineImageDTOArrayList().size()+"");

                    holder.binding.imageLL.setVisibility(View.GONE);
                }
                else if ((timelinePostDTOArrayList.get(position).getTimelineImageDTOArrayList().size())==1)
                {
                    Log.e("size",timelinePostDTOArrayList.get(position).getTimelineImageDTOArrayList().size()+"");

                    holder.binding.imageLL.setVisibility(View.VISIBLE);
                    holder.binding.singleImageLL.setVisibility(View.VISIBLE);
                    holder.binding.doubleimageImageLL.setVisibility(View.GONE);
                    Glide.with(mContext).load(timelinePostDTOArrayList.get(position).getTimelineImageDTOArrayList().get(0).getImage())
                            .placeholder(R.color.grey).into(holder.binding.singleimageIV);


                }

                else if ((timelinePostDTOArrayList.get(position).getTimelineImageDTOArrayList().size())==2)
                {
                    Log.e("size",timelinePostDTOArrayList.get(position).getTimelineImageDTOArrayList().size()+"");

                    holder.binding.imageLL.setVisibility(View.VISIBLE);
                    holder.binding.singleImageLL.setVisibility(View.GONE);
                    holder.binding.doubleimageImageLL.setVisibility(View.VISIBLE);
                    holder.binding.countLL.setVisibility(View.GONE);

                    Glide.with(mContext).load(timelinePostDTOArrayList.get(position).getTimelineImageDTOArrayList().get(0).getImage())
                            .placeholder(R.color.grey).into(holder.binding.doubleimage1IV);


                    Glide.with(mContext).load(timelinePostDTOArrayList.get(position).getTimelineImageDTOArrayList().get(1).getImage())
                            .placeholder(R.color.grey).into(holder.binding.doubleimage2IV);
                }

                else if ((timelinePostDTOArrayList.get(position).getTimelineImageDTOArrayList().size())==3)
                {
                    Log.e("size",timelinePostDTOArrayList.get(position).getTimelineImageDTOArrayList().size()+"");

                    holder.binding.imageLL.setVisibility(View.VISIBLE);
                    holder.binding.singleImageLL.setVisibility(View.VISIBLE);
                    holder.binding.doubleimageImageLL.setVisibility(View.VISIBLE);
                    holder.binding.countLL.setVisibility(View.GONE);

                    Glide.with(mContext).load(timelinePostDTOArrayList.get(position).getTimelineImageDTOArrayList().get(0).getImage())
                            .placeholder(R.color.grey).into(holder.binding.singleimageIV);


                    Glide.with(mContext).load(timelinePostDTOArrayList.get(position).getTimelineImageDTOArrayList().get(1).getImage())
                            .placeholder(R.color.grey).into(holder.binding.doubleimage1IV);


                    Glide.with(mContext).load(timelinePostDTOArrayList.get(position).getTimelineImageDTOArrayList().get(2).getImage())
                            .placeholder(R.color.grey).into(holder.binding.doubleimage2IV);
                }

                else
                {
                    Log.e("size",timelinePostDTOArrayList.get(position).getTimelineImageDTOArrayList().size()+"");

                    holder.binding.imageLL.setVisibility(View.VISIBLE);
                    holder.binding.singleImageLL.setVisibility(View.VISIBLE);
                    holder.binding.doubleimageImageLL.setVisibility(View.VISIBLE);
                    holder.binding.countLL.setVisibility(View.VISIBLE);

                    Glide.with(mContext).load(timelinePostDTOArrayList.get(position).getTimelineImageDTOArrayList().get(0).getImage())
                            .placeholder(R.color.grey).into(holder.binding.singleimageIV);


                    Glide.with(mContext).load(timelinePostDTOArrayList.get(position).getTimelineImageDTOArrayList().get(1).getImage())
                            .placeholder(R.color.grey).into(holder.binding.doubleimage1IV);


                    Glide.with(mContext).load(timelinePostDTOArrayList.get(position).getTimelineImageDTOArrayList().get(2).getImage())
                            .placeholder(R.color.grey).into(holder.binding.doubleimage2IV);
                    int count=timelinePostDTOArrayList.get(position).getTimelineImageDTOArrayList().size()-3;
                    holder.binding.countTV.setText("+"+count);
                }
            }
            catch (Exception e)
            {
                Log.e("exception",e.getLocalizedMessage());
            }


        }



    }

    public String fromBase64(String message) {
        byte[] data = Base64.decode(message, Base64.DEFAULT);
        try {
            return new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }
    @Override
    public int getItemCount() {
        return timelinePostDTOArrayList == null ? 0 : timelinePostDTOArrayList.size();
    }

    public class CustomViewHodler extends RecyclerView.ViewHolder {
        //            ImageView imageView;
        private ItemDashboardBinding binding;

        public CustomViewHodler(ItemDashboardBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

    }
}
