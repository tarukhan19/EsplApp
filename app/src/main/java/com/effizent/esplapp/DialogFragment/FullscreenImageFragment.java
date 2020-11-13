package com.effizent.esplapp.DialogFragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.effizent.esplapp.Adapter.FullScreenImageAdapter;
import com.effizent.esplapp.R;
import com.effizent.esplapp.databinding.FragmentFullscreenImageBinding;
import com.effizent.esplapp.session.SessionManager;

import java.util.ArrayList;
import java.util.Arrays;


public class FullscreenImageFragment extends DialogFragment {
    Dialog maindialog;
    private FullScreenImageAdapter adapter;

    ArrayList<String> imageDTOArrayList;
    int position;
    SessionManager session;
    RequestQueue requestQueue;
    FragmentFullscreenImageBinding   binding;

    public Dialog onCreateDialog(final Bundle savedInstanceState)
    {

        maindialog = super.onCreateDialog(savedInstanceState);
        binding = DataBindingUtil.inflate(LayoutInflater.from(maindialog.getContext()), R.layout.fragment_fullscreen_image, null, false);
        maindialog.getWindow().getAttributes().windowAnimations = R.style.CustomDialogFragmentAnimation;
        maindialog.setContentView(binding.getRoot());
        maindialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        maindialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        maindialog.show();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        initialize();

        return maindialog;
    }

    private void initialize()
    {
        if (android.os.Build.VERSION.SDK_INT >= 21)
        {
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimary));
        }

        session = new SessionManager(getActivity());
        imageDTOArrayList=new ArrayList<>();
        requestQueue = Volley.newRequestQueue(getActivity());
        String pos=session.getList().get(SessionManager.KEY_POSITION);
        position=Integer.parseInt(pos);

        String str=session.getList().get(SessionManager.KEY_LIST);
        str=str.replace("[","");
        str=str.replace("]","");
        imageDTOArrayList = new ArrayList<String>(Arrays.asList(str.split(",")));
        Log.e("imagelist",position+"    "+imageDTOArrayList);


        adapter = new FullScreenImageAdapter(getActivity(),
                imageDTOArrayList);
        binding.pager.setAdapter(adapter);
        binding.pager.setCurrentItem(position);
        bind(binding.pager);
    }



    public void bind(final ViewPager viewPager) {

        binding.rightNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isFirstPage()) {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() - 1, true);
                }
            }
        });

        binding.leftNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isLastPage()) {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
                }
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                handleVisibility();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        handleVisibility();

    }


    private void handleVisibility() {
        if (isFirstPage()) {
            binding.rightNav.setVisibility(View.INVISIBLE);
        } else {
            binding.rightNav.setVisibility(View.VISIBLE);
        }

        if (isLastPage()) {
            binding.leftNav.setVisibility(View.INVISIBLE);
        } else {
            binding.leftNav.setVisibility(View.VISIBLE);
        }
    }

    private boolean isLastPage() {
        return binding.pager.getCurrentItem() == binding.pager.getAdapter().getCount() - 1;
    }

    private boolean isFirstPage() {
        return binding.pager.getCurrentItem() == 0;
    }
}