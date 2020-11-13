package com.effizent.esplapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.effizent.esplapp.R;
import com.effizent.esplapp.helper.TouchImageView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class FullScreenImageAdapter extends PagerAdapter {
    private Activity _activity;
    private ArrayList<String> _imagePaths;
    private LayoutInflater inflater;

    // constructor
    public FullScreenImageAdapter(Activity activity,
                                  ArrayList<String> imagePaths) {
        this._activity = activity;
        this._imagePaths = imagePaths;
    }

    @Override
    public int getCount() {
        return this._imagePaths.size();
    }
    @Override
    public boolean isViewFromObject(View view, Object object)
    {
        return view == ((RelativeLayout) object);
    }
    @Override
    public Object instantiateItem(ViewGroup container, final int position)
    {
        TouchImageView imgDisplay;
        inflater = (LayoutInflater) _activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewLayout = inflater.inflate(R.layout.layout_fullscreen_image, container,
                false);
        imgDisplay = (TouchImageView) viewLayout.findViewById(R.id.imgDisplay);

        Log.e("_imagePaths",_imagePaths.get(position));

        try {
            Glide.with(_activity).load(new URL(_imagePaths.get(position))).placeholder(R.color.lite_grey).error(R.color.accentColor)
                    .into(imgDisplay);
        } catch (Exception e) {
            Log.e("Exception",e.getLocalizedMessage());
            e.printStackTrace();
        }


        ((ViewPager) container).addView(viewLayout);

        return viewLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((RelativeLayout) object);

    }

}
