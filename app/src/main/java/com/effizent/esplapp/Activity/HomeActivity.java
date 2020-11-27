package com.effizent.esplapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Application;
import android.app.Dialog;
import android.content.Intent;
import android.content.IntentSender;
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
import com.effizent.esplapp.javaclass.Api;
import com.effizent.esplapp.javaclass.DeviceToken;
import com.effizent.esplapp.session.SessionManager;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.google.android.play.core.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.tenclouds.fluidbottomnavigation.FluidBottomNavigationItem;
import com.tenclouds.fluidbottomnavigation.listener.OnTabSelectedListener;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity
{
    ActivityHomeBinding binding;
    ArrayList<FluidBottomNavigationItem> fluidBottomNavigationItemArrayList;
    private final static int ID_HOME = 0;
    private final static int ID_PROFILE = 1;
    private final static int ID_NOTIFICATION = 2;
    public static int CURRENT_TAG = ID_HOME;
    TextView toolbartitle;
    ImageView backIV,logoutIV;
    SessionManager sessionManager;
    private static final int HIGH_PRIORITY_UPDATE =1 ;
    AppUpdateManager appUpdateManager;
    private static final int MY_REQUEST_CODE = 11;


    private HomeFragment homeFragment = new HomeFragment();
    private ProfileFragment profileFragment = new ProfileFragment();
    private NotificationFragment notificationFragment = new NotificationFragment();
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private Fragment activeFragment=homeFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        initialize();

        binding.fluidBottomNavigation.setOnTabSelectedListener(new OnTabSelectedListener()
        {
            @Override
            public void onTabSelected(int position) {
                switch (position)
                {
                    case 0:
                        CURRENT_TAG = ID_HOME;
                        fragmentManager.beginTransaction().hide(activeFragment).show(homeFragment).commit();
                        activeFragment=homeFragment;
                        toolbartitle.setText("Home");
                        break;
                    case 1:
                        CURRENT_TAG = ID_PROFILE;
                        fragmentManager.beginTransaction().hide(activeFragment).show(profileFragment).commit();
                        activeFragment=profileFragment;
                        toolbartitle.setText("Profile");
                        break;
                    case 2:
                        CURRENT_TAG = ID_NOTIFICATION;
                        fragmentManager.beginTransaction().hide(activeFragment).show(notificationFragment).commit();
                        activeFragment=notificationFragment;
                        toolbartitle.setText("Notification");
                        break;
                }
            }
        });

        backIV.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if (CURRENT_TAG == ID_HOME) {
                    finish();
                } else {
                    CURRENT_TAG = ID_HOME;
                    fragmentManager.beginTransaction().hide(activeFragment).show(homeFragment).commit();
                    activeFragment=homeFragment;
                    toolbartitle.setText("Home");
                    binding.fluidBottomNavigation.selectTab(0);
                }
            }
        });

        logoutIV.setOnClickListener(new View.OnClickListener()
        {
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

    private void initialize()
    {
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
        //new Api().getData(sessionManager,this,"home");

        fragmentManager.beginTransaction().add(R.id.content_frame, homeFragment, "USER").hide(homeFragment)
                .add(R.id.content_frame, profileFragment, "PROFILE").hide(profileFragment)
                .add(R.id.content_frame, notificationFragment, "NOTIFICATION").hide(notificationFragment).commit();


        fragmentManager.beginTransaction().hide(activeFragment).show(homeFragment).commit();
        toolbartitle.setText("Home");

        checkUpdate();




    }



    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        if (CURRENT_TAG == ID_HOME) {
            finish();
        } else {
            CURRENT_TAG = ID_HOME;
            fragmentManager.beginTransaction().hide(activeFragment).show(homeFragment).commit();
            activeFragment=homeFragment;
            toolbartitle.setText("Home");
            binding.fluidBottomNavigation.selectTab(0);
        }
    }

    private void checkUpdate()
    {
        // Creates instance of the manager.
        appUpdateManager = AppUpdateManagerFactory.create(this);

        // Returns an intent object that you use to check for an update.
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();

        // Checks that the platform will allow the specified type of update.
        // For a flexible update, use AppUpdateType.FLEXIBLE
        appUpdateInfoTask.addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo appUpdateInfo) {
                if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                        && appUpdateInfo.updatePriority() >= HIGH_PRIORITY_UPDATE
                        && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE))
                {
                    // Request the update.

                    try {
                        appUpdateManager.startUpdateFlowForResult(appUpdateInfo, AppUpdateType.IMMEDIATE, HomeActivity.this,
                                MY_REQUEST_CODE);
                    } catch (IntentSender.SendIntentException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MY_REQUEST_CODE) {
            if (resultCode != RESULT_OK)
            {

            }
        }
    }


}