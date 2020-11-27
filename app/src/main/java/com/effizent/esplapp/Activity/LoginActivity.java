package com.effizent.esplapp.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.effizent.esplapp.Fragment.HomeFragment;
import com.effizent.esplapp.R;
import com.effizent.esplapp.RetroApiResponses.LoginResult;
import com.effizent.esplapp.Retropack.APIServices;
import com.effizent.esplapp.Retropack.RetrofitFactory;
import com.effizent.esplapp.databinding.ActivityLoginBinding;
import com.effizent.esplapp.javaclass.Api;
import com.effizent.esplapp.javaclass.DeviceToken;
import com.effizent.esplapp.session.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityLoginBinding binding;
    String emailId, password,deviceId;
    SessionManager session;
    DeviceToken deviceToken=new DeviceToken();
   static LoginActivity loginActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);


        initialize();
    }

    private void initialize()
    {
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimary));
        }
        session = new SessionManager(this);
        binding.progressbar.setProgressTintList(ColorStateList.valueOf(Color.WHITE));

        binding.loginBTN.setOnClickListener(this);
        loginActivity=this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginBTN:
                checkValidation();
                break;
        }
    }

    private void checkValidation()
    {
        emailId = binding.emailIdET.getText().toString();
        password = binding.passwordET.getText().toString();
        deviceId=session.getDeviceToken().get(SessionManager.KEY_DEVICE_TOKEN);
        Log.e("deviceId",deviceId);
        if (emailId.isEmpty()) {
            binding.transLL.setVisibility(View.VISIBLE);
            openDialog("Enter valid Email ID.", "Warning!");
        } else if (password.isEmpty()) {
            binding.transLL.setVisibility(View.VISIBLE);

            openDialog("Enter valid password.", "Warning!");

        } else {
            binding.transLL.setVisibility(View.VISIBLE);
            binding.progressbar.setVisibility(View.VISIBLE);
            login();
        }
    }

    private void login() {

        /////Step 4) Call this method when you want to login.//////
        Retrofit retrofit = RetrofitFactory.getRetrofit();
        APIServices service = retrofit.create(APIServices.class);
        service.loginUser(emailId, password, deviceId );
        Call<LoginResult> call = service.loginUser(emailId, password, deviceId);
        Log.e("deviceId", deviceId);




        ///Step 5) Define callback methods to be called when API sends response.

        call.enqueue(new Callback<LoginResult>() {
            @Override
            public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {
                binding.progressbar.setVisibility(View.GONE);

                binding.transLL.setVisibility(View.GONE);
                LoginResult loginResult = response.body();
                if (loginResult.getCode().equalsIgnoreCase("200") &&
                        loginResult.getMessage().equalsIgnoreCase("Success"))
                {
                    String id=loginResult.getDetailsArrayList().get(0).getId();
                    String department=loginResult.getDetailsArrayList().get(0).getDepartment();
                    String name=loginResult.getDetailsArrayList().get(0).getName();
                    String email=loginResult.getDetailsArrayList().get(0).getEmail();
                    String mobile=loginResult.getDetailsArrayList().get(0).getMobile();
                    String teamLeader=loginResult.getDetailsArrayList().get(0).getTeamLeader();
                    String profilePicture=loginResult.getDetailsArrayList().get(0).getProfilePicture();

                    session.setLoginDetails(id,department,name,email,mobile,teamLeader,profilePicture);
                    new Api().getData(session,LoginActivity.this,"login");

                }
                else
                {
                    openDialog(loginResult.getMessage(),"Failure!");
                }




            }


            @Override
            public void onFailure(Call<LoginResult> call, Throwable t) {
                binding.progressbar.setVisibility(View.GONE);
                Log.e("errorrrmessageeee", t.getMessage());

                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }



    public static LoginActivity getInstance() {
        return loginActivity;
    }


    public void runUi() {

        new Thread() {
            public void run() {
                try {
                    runOnUiThread(new Runnable() {

                        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                        @Override
                        public void run() {
                            Intent intent=new Intent(LoginActivity.this,HomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);

                            overridePendingTransition(R.anim.trans_left_in,
                                    R.anim.trans_left_out);

                        }
                    });
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }

    private void openDialog(String description, String title)
    {
        final Dialog dialog = new Dialog(this, R.style.CustomDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.item_dialog);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();
        TextView descriptionTV = dialog.findViewById(R.id.descriptionTV);
        TextView titleTV = dialog.findViewById(R.id.titleTV);
        ImageView imageView = dialog.findViewById(R.id.imageview);
        Button cancelBTN = dialog.findViewById(R.id.cancelBTN);
        Button okBTN = dialog.findViewById(R.id.okBTN);

        descriptionTV.setText(description);
        titleTV.setText(title);
        if (title.equalsIgnoreCase("Warning!")) {
            imageView.setImageResource(R.drawable.warning);
            titleTV.setTextColor(getResources().getColor(R.color.red));
            imageView.setImageResource(R.drawable.warning);
        } else if (title.equalsIgnoreCase("Success!")) {
            titleTV.setTextColor(getResources().getColor(R.color.green));
            imageView.setImageResource(R.drawable.success);
            titleTV.setText("Success!");
        } else if (title.equalsIgnoreCase("Failure!")) {
            titleTV.setTextColor(getResources().getColor(R.color.red));
            imageView.setImageResource(R.drawable.error);
            titleTV.setText("Failure!");
        }
        okBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.transLL.setVisibility(View.GONE);

                if (title.equalsIgnoreCase("Warning!") || title.equalsIgnoreCase("Failure!")) {
                    dialog.dismiss();
                } else if (title.equalsIgnoreCase("Success!")) {
                    dialog.dismiss();
                }
            }
        });

        cancelBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.transLL.setVisibility(View.GONE);

                dialog.dismiss();
            }
        });
    }
}