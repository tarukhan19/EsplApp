package com.effizent.esplapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.effizent.esplapp.Fragment.HomeFragment;
import com.effizent.esplapp.Fragment.NotificationFragment;
import com.effizent.esplapp.Fragment.ProfileFragment;
import com.effizent.esplapp.R;
import com.effizent.esplapp.databinding.ActivityHomeBinding;
import com.effizent.esplapp.session.SessionManager;
import com.tenclouds.fluidbottomnavigation.FluidBottomNavigationItem;
import com.tenclouds.fluidbottomnavigation.listener.OnTabSelectedListener;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
ActivityHomeBinding binding;
ArrayList<FluidBottomNavigationItem> fluidBottomNavigationItemArrayList;
    private final static int ID_HOME = 0;
    private final static int ID_PROFILE = 1;

    private final static int ID_NOTIFICATION = 2;
    public static int CURRENT_TAG = ID_HOME;
    TextView toolbartitle;
    ImageView backIV,logoutIV;
    private Handler mHandler;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);

        initialize();

        binding.fluidBottomNavigation.setOnTabSelectedListener(new OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                switch (position)
                {
                    case 0:
                        CURRENT_TAG = ID_HOME;
                        loadHomeFragment();
                        break;
                    case 1:
                        CURRENT_TAG = ID_PROFILE;
                        loadHomeFragment();
                        break;
                    case 2:
                        CURRENT_TAG = ID_NOTIFICATION;
                        loadHomeFragment();
                        break;
                }
            }
        });

        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CURRENT_TAG == ID_HOME) {
                    finish();
                } else {
                    CURRENT_TAG = ID_HOME;
                    loadHomeFragment();
                    binding.fluidBottomNavigation.selectTab(0);
                }
            }
        });

        logoutIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLogoutDialog();
            }
        });



    }

    private void showLogoutDialog()
    {
        final Dialog dialog = new Dialog(HomeActivity.this, R.style.CustomDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.item_logout);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        Button ok = (Button) dialog.findViewById(R.id.yesBTN);
        Button cancel = (Button) dialog.findViewById(R.id.noBTN);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //logOut();
                dialog.dismiss();
                Intent in7 = new Intent(HomeActivity.this, LoginActivity.class);
                in7.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(in7);
                sessionManager.logoutUser();

                overridePendingTransition(0, 0);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void initialize() {
        fluidBottomNavigationItemArrayList=new ArrayList<>();
        fluidBottomNavigationItemArrayList.add(new FluidBottomNavigationItem(
                getString(R.string.home),
                ContextCompat.getDrawable(this, R.mipmap.home)));


        fluidBottomNavigationItemArrayList.add(new FluidBottomNavigationItem(
                getString(R.string.profile),
                ContextCompat.getDrawable(this, R.mipmap.profile)));
        fluidBottomNavigationItemArrayList.add(new FluidBottomNavigationItem(
                getString(R.string.notification),
                ContextCompat.getDrawable(this, R.mipmap.notification)));


        sessionManager = new SessionManager(getApplicationContext());


        binding.fluidBottomNavigation.setItems(fluidBottomNavigationItemArrayList);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        backIV = toolbar.findViewById(R.id.backIV);
        logoutIV = toolbar.findViewById(R.id.logoutIV);
        toolbartitle = toolbar.findViewById(R.id.toolbartitle);
        mHandler = new Handler();

        Log.e("userid",sessionManager.getLoginDetails().get(SessionManager.KEY_ID));
        loadHomeFragment();


    }

    private void loadHomeFragment() {

        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.content_frame, fragment, String.valueOf(CURRENT_TAG));
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        };

        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }

        invalidateOptionsMenu();
    }


    private Fragment getHomeFragment()
    {
        switch (CURRENT_TAG)
        {
            case 0:
                HomeFragment homeFragment = new HomeFragment();
                CURRENT_TAG = ID_HOME;
                toolbartitle.setText("Home");
                return homeFragment;

            case 1:

                ProfileFragment profileFragment = new ProfileFragment();
                CURRENT_TAG = ID_PROFILE;
                toolbartitle.setText("Profile");
                return profileFragment;


            case 2:

                NotificationFragment notificationFragment = new NotificationFragment();
                CURRENT_TAG = ID_NOTIFICATION;
                toolbartitle.setText("Notification");
                return notificationFragment;



            default:
                return new HomeFragment();
        }


    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        if (CURRENT_TAG == ID_HOME) {
            finish();
        } else {
            CURRENT_TAG = ID_HOME;
            loadHomeFragment();
            binding.fluidBottomNavigation.selectTab(0);
        }
    }



}