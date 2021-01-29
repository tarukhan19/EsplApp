package com.effizent.esplapp.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.effizent.esplapp.Activity.LoginActivity;
import com.effizent.esplapp.Adapter.NotificationAdapter;
import com.effizent.esplapp.DialogFragment.CreatePostFragment;
import com.effizent.esplapp.Model.Images;
import com.effizent.esplapp.R;
import com.effizent.esplapp.RetroApiResponses.LoadDashBoardResult;
import com.effizent.esplapp.RetroApiResponses.LoadNotificationResult;
import com.effizent.esplapp.RetroApiResponses.NotificationListApi;
import com.effizent.esplapp.RetroApiResponses.Timeline_Post_List;
import com.effizent.esplapp.Retropack.APIServices;
import com.effizent.esplapp.Retropack.RetrofitFactory;
import com.effizent.esplapp.databinding.FragmentNotificationBinding;
import com.effizent.esplapp.session.SessionManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class NotificationFragment extends Fragment {
    private FragmentNotificationBinding binding;
    private ProgressDialog progressDialog;
    private RequestQueue queue;
    private SessionManager sessionManager;
    private NotificationAdapter adapter;
    private List<NotificationListApi> notificationListDTOList;
    public NotificationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_notification, container, false);
        View view = binding.getRoot();
        progressDialog = new ProgressDialog(getActivity());
        queue = Volley.newRequestQueue(getActivity());
        sessionManager = new SessionManager(getActivity());

        notificationListDTOList = new ArrayList<>();
        adapter = new NotificationAdapter(getActivity(), notificationListDTOList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());

        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setItemViewCacheSize(20);
        binding.recyclerView.setDrawingCacheEnabled(true);
        binding.recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        binding.recyclerView.scheduleLayoutAnimation();
        binding.recyclerView.setNestedScrollingEnabled(false);

        binding.recyclerView.setLayoutManager(mLayoutManager);
        binding.recyclerView.setAdapter(adapter);
        loadNotification();
        return view;
    }

    private void loadNotification() {
        Retrofit retrofit = RetrofitFactory.getRetrofit();
        APIServices service = retrofit.create(APIServices.class);
        service.loadNotificationData(sessionManager.getLoginDetails().get(SessionManager.KEY_ID),sessionManager.getLoginDetails().get(SessionManager.KEY_DEPARTMENT));
        Call<LoadNotificationResult> call = service.loadNotificationData(sessionManager.getLoginDetails().get(SessionManager.KEY_ID),sessionManager.getLoginDetails().get(SessionManager.KEY_DEPARTMENT));

        call.enqueue(new Callback<LoadNotificationResult>() {

            @Override
            public void onResponse(Call<LoadNotificationResult> call, retrofit2.Response<LoadNotificationResult> response) {
                LoadNotificationResult modelTestResult = response.body();
                Log.e("responegetcode",modelTestResult.getCode());
              //  session.setDashBoardRespone(modelTestResult);

                if (modelTestResult.getCode().equalsIgnoreCase("200")
                        && modelTestResult.getStatus().equalsIgnoreCase("Success"))
                {
                    for (int i = 0; i < modelTestResult.getNotificationList().size(); i++)
                    {
                        NotificationListApi notificationListApi = new NotificationListApi();
                        notificationListApi.setCreatedDate(modelTestResult.getNotificationList().get(i).getCreatedDate());
                        notificationListApi.setTitle(modelTestResult.getNotificationList().get(i).getTitle());
                        notificationListApi.setNotificationMessage(modelTestResult.getNotificationList().get(i).getNotificationMessage());
                        notificationListDTOList.add(notificationListApi);
                    }
                    adapter.notifyDataSetChanged();
                }



            }

            @Override
            public void onFailure(Call<LoadNotificationResult> call, Throwable t)
            {
                Toast.makeText(getActivity().getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });



        }

}
