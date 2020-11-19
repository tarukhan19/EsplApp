package com.effizent.esplapp.javaclass;


import android.util.Log;

import androidx.annotation.NonNull;

import com.effizent.esplapp.session.SessionManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class DeviceToken {
    String token="";
public String getDeviceToken(SessionManager session)
{
    FirebaseMessaging.getInstance().getToken()
            .addOnCompleteListener(new OnCompleteListener<String>() {
                @Override
                public void onComplete(@NonNull Task<String> task) {
                    if (!task.isSuccessful()) {
                       Log.e("Fetching FCM ", task.getException()+"");
                        return;
                    }

                    // Get new FCM registration token
                    token = task.getResult();
                    session.setDeviceToken(token);
                   Log.e("token",token);


                }
            });
    return token;
}




}
