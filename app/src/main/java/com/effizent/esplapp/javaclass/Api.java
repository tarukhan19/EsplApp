package com.effizent.esplapp.javaclass;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.effizent.esplapp.Activity.LoginActivity;
import com.effizent.esplapp.DialogFragment.CreatePostFragment;
import com.effizent.esplapp.Fragment.HomeFragment;
import com.effizent.esplapp.RetroApiResponses.LoadDashBoardResult;
import com.effizent.esplapp.Retropack.APIServices;
import com.effizent.esplapp.Retropack.RetrofitFactory;
import com.effizent.esplapp.session.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class Api {

    public void getData(SessionManager session, Context mcontext, String from)
    {
        Retrofit retrofit = RetrofitFactory.getRetrofit();
        APIServices service = retrofit.create(APIServices.class);
        service.loadDashboardData(session.getLoginDetails().get(SessionManager.KEY_ID));
        Call<LoadDashBoardResult> call = service.loadDashboardData(session.getLoginDetails().get(SessionManager.KEY_ID));

        call.enqueue(new Callback<LoadDashBoardResult>() {



            @Override
            public void onResponse(Call<LoadDashBoardResult> call, retrofit2.Response<LoadDashBoardResult> response) {
                LoadDashBoardResult modelTestResult = response.body();
                Log.e("responegetcode",modelTestResult.getCode());
                session.setDashBoardRespone(modelTestResult);

                if (from.equalsIgnoreCase("home"))
                {
                    HomeFragment.getInstance().runUi();
                }
                else if (from.equalsIgnoreCase("login"))
                {
                  LoginActivity.getInstance().runUi();

                }




            }

            @Override
            public void onFailure(Call<LoadDashBoardResult> call, Throwable t)
            {
                Toast.makeText(mcontext.getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

}
