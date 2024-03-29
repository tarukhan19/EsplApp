package com.effizent.esplapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.effizent.esplapp.R;
import com.effizent.esplapp.javaclass.DeviceToken;
import com.effizent.esplapp.session.SessionManager;

public class MainActivity extends AppCompatActivity {
    private static final long SPLASH_TIME_OUT =5000 ;
    SessionManager session;
    DeviceToken deviceToken=new DeviceToken();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        session = new SessionManager(getApplicationContext());
        deviceToken.getDeviceToken(session);
        if (android.os.Build.VERSION.SDK_INT >= 21)
        {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimary));
        }

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {

                if (session.isLoggedIn()) {
                    Intent intent=new Intent(MainActivity.this, HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    overridePendingTransition(R.anim.trans_left_in,
                            R.anim.trans_left_out);
                    finish();
                } else

                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                overridePendingTransition(R.anim.trans_left_in,
                        R.anim.trans_left_out);
                finish();


            }
        }, SPLASH_TIME_OUT);


    }
}