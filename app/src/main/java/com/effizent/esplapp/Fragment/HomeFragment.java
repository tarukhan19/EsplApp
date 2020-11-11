package com.effizent.esplapp.Fragment;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.effizent.esplapp.DialogFragment.CreatePostFragment;
import com.effizent.esplapp.R;
import com.effizent.esplapp.RetroApiResponses.LoadDashBoardResult;
import com.effizent.esplapp.RetroApiResponses.TimelineImageDTO;
import com.effizent.esplapp.RetroApiResponses.TimelinePostDTO;
import com.effizent.esplapp.Retropack.APIServices;
import com.effizent.esplapp.Retropack.RetrofitFactory;
import com.effizent.esplapp.databinding.FragmentHomeBinding;
import com.effizent.esplapp.session.SessionManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class HomeFragment extends Fragment {

FragmentHomeBinding binding;
    SessionManager session;
    ArrayList<TimelinePostDTO> timelinePostDTOArrayList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        View view = binding.getRoot();
        initialize();

        binding.createpostLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreatePostFragment createPostFragment = new CreatePostFragment();
                FragmentTransaction ft =getActivity().getSupportFragmentManager().beginTransaction();
                createPostFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogFragmentTheme);
                ft.addToBackStack(null);
                createPostFragment.show(ft, "dialog");
            }
        });
        return view;
    }

    private void initialize()
    {
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimary));
        }
        session = new SessionManager(getActivity());
        timelinePostDTOArrayList=new ArrayList<>();
        getData();

    }

    private void getData(){
        Retrofit retrofit = RetrofitFactory.getRetrofit();
        APIServices service = retrofit.create(APIServices.class);
        service.loadDashboardData(session.getLoginDetails().get(SessionManager.KEY_ID));
        Call<LoadDashBoardResult> call = service.loadDashboardData(session.getLoginDetails().get(SessionManager.KEY_ID));


        call.enqueue(new Callback<LoadDashBoardResult>() {
            @Override
            public void onResponse(Call<LoadDashBoardResult> call, Response<LoadDashBoardResult> response) {

                LoadDashBoardResult modelTestResult = response.body();
                if (modelTestResult.getCode().equalsIgnoreCase("200") && modelTestResult.getStatus().equalsIgnoreCase("Success"))
                {
                    for (int i = 0; i < modelTestResult.getTimelinePostDTOArrayList().size(); i++) {
                        TimelinePostDTO timelinePostDTO = new TimelinePostDTO();
                        timelinePostDTO.setId(modelTestResult.getTimelinePostDTOArrayList().get(i).getId());
                        timelinePostDTO.setTitle(modelTestResult.getTimelinePostDTOArrayList().get(i).getTitle());
                        timelinePostDTO.setDescription(modelTestResult.getTimelinePostDTOArrayList().get(i).getDescription());
                        ArrayList<TimelineImageDTO> timelineImageDTOArrayList = new ArrayList<>();

                        for (int j = 0; j < modelTestResult.getTimelinePostDTOArrayList().get(i).getTimelineImageDTOArrayList().size(); j++)
                        {
                            TimelineImageDTO timelineImageDTO = new TimelineImageDTO();
                            timelineImageDTO.setImage(modelTestResult.getTimelinePostDTOArrayList().get(i).getTimelineImageDTOArrayList().get(j).getImage());


                            timelineImageDTOArrayList.add(timelineImageDTO);
                            timelinePostDTO.setTimelineImageDTOArrayList(timelineImageDTOArrayList);

                        }
                        timelinePostDTOArrayList.add(timelinePostDTO);
                    }

                }

            }


            @Override
            public void onFailure(Call<LoadDashBoardResult> call, Throwable t) {

                Toast.makeText(getActivity().getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}