package com.effizent.esplapp.Fragment;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.effizent.esplapp.Adapter.PostAdapter;
import com.effizent.esplapp.DialogFragment.CreatePostFragment;
import com.effizent.esplapp.R;
import com.effizent.esplapp.RetroApiResponses.LoadDashBoardResult;
import com.effizent.esplapp.Model.Images;
import com.effizent.esplapp.RetroApiResponses.Timeline_Post_List;
import com.effizent.esplapp.Retropack.APIServices;
import com.effizent.esplapp.Retropack.RetrofitFactory;
import com.effizent.esplapp.databinding.FragmentHomeBinding;
import com.effizent.esplapp.session.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;


public class HomeFragment extends Fragment {

FragmentHomeBinding binding;
    SessionManager session;
    ArrayList<Timeline_Post_List> timelinePostDTOArrayList;
    PostAdapter adapter;
    RequestQueue requestQueue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        View view = binding.getRoot();
        initialize();

        binding.createpostLL.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
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
        requestQueue = Volley.newRequestQueue(getActivity());

        adapter = new PostAdapter(getActivity(), timelinePostDTOArrayList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        binding.recycleview.setLayoutManager(mLayoutManager);
        binding.recycleview.setHasFixedSize(true);
        binding.recycleview.setItemViewCacheSize(20);
        binding.recycleview.setDrawingCacheEnabled(true);
        binding.recycleview.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        binding.recycleview.setAdapter(adapter);
        binding.recycleview.scheduleLayoutAnimation();

        getData();

    }

    private void getData() {


        StringRequest postRequest = new StringRequest(Request.Method.POST, "https://gstsuvidhacenters.in/Show_Timeline_Post.ashx?",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // pd.dismiss();

                        try {
                            JSONObject obj = new JSONObject(response);
                            Log.e("response",response);
                            int code = obj.getInt("Code");
                            String status = obj.getString("Status");
                            if (code == 200 && status.equalsIgnoreCase("Success"))
                            {
                                JSONArray jsonArray=obj.getJSONArray("Timeline_Post_List");
                                for (int i=0;i<jsonArray.length();i++)
                                {

                                    JSONObject listObj=jsonArray.getJSONObject(i);
                                    String Title=listObj.getString("Title");
                                    String Id=listObj.getString("Id");
                                    String Name=listObj.getString("Name");
                                    String ProfilePic=listObj.getString("ProfilePic");
                                    String Description=listObj.getString("Description");
                                    String Date=listObj.getString("Date");

                                    Timeline_Post_List timelinePostList=new Timeline_Post_List();
                                    timelinePostList.setId(Id);
                                    timelinePostList.setTitle(Title);
                                    timelinePostList.setDate(Date);
                                    timelinePostList.setDescription(Description);
                                    timelinePostList.setName(Name);
                                    timelinePostList.setProfilePic(ProfilePic);
                                    ArrayList<Images> imagesArrayList=new ArrayList<>();

                                    JSONArray innerjsonArray=listObj.getJSONArray("Images");
                                    for (int j=0;j<innerjsonArray.length();j++)
                                    {
                                        JSONObject innerJsonObj=innerjsonArray.getJSONObject(j);
                                        String image=innerJsonObj.getString("Image");
                                        Images images=new Images();
                                        images.setImage(image);
                                        imagesArrayList.add(images);
                                    }

                                    timelinePostList.setTimelineImageDTOArrayList(imagesArrayList);

                                    timelinePostDTOArrayList.add(timelinePostList);


                                }
                                adapter.notifyDataSetChanged();
                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (ArrayIndexOutOfBoundsException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ) {  @Override
        protected Map<String, String> getParams() {
            Map<String, String> headers = new HashMap<String, String>();
            headers.put("EmpId", session.getLoginDetails().get(SessionManager.KEY_ID));
            Log.e("header",headers.toString());


//

            return headers;
        }};
        int socketTimeout = 50000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        postRequest.setRetryPolicy(policy);
        requestQueue.add(postRequest);
    }

//    private void getData()
//    {
//        Retrofit retrofit = RetrofitFactory.getRetrofit();
//        APIServices service = retrofit.create(APIServices.class);
//        service.loadDashboardData(session.getLoginDetails().get(SessionManager.KEY_ID));
//        Call<LoadDashBoardResult> call = service.loadDashboardData(session.getLoginDetails().get(SessionManager.KEY_ID));
//
//        call.enqueue(new Callback<LoadDashBoardResult>() {
//
//
//
//            @Override
//            public void onResponse(Call<LoadDashBoardResult> call, retrofit2.Response<LoadDashBoardResult> response) {
//                LoadDashBoardResult modelTestResult = response.body();
//                if (modelTestResult.getCode().equalsIgnoreCase("200")
//                        && modelTestResult.getStatus().equalsIgnoreCase("Success"))
//                {
//                    for (int i = 0; i < modelTestResult.getTimelinePostDTOArrayList().size(); i++)
//                    {
//                        Timeline_Post_List timelinePostDTO = new Timeline_Post_List();
//                        timelinePostDTO.setId(modelTestResult.getTimelinePostDTOArrayList().get(i).getId());
//                        timelinePostDTO.setTitle(modelTestResult.getTimelinePostDTOArrayList().get(i).getTitle());
//                        timelinePostDTO.setDescription(modelTestResult.getTimelinePostDTOArrayList().get(i).getDescription());
//                        timelinePostDTO.setDate(modelTestResult.getTimelinePostDTOArrayList().get(i).getDate());
//                        timelinePostDTO.setName(modelTestResult.getTimelinePostDTOArrayList().get(i).getName());
//                        timelinePostDTO.setProfilePic(modelTestResult.getTimelinePostDTOArrayList().get(i).getProfilePic());
//
//                         if (modelTestResult.getTimelinePostDTOArrayList().get(i).getTimelineImageDTOArrayList().size()>0)
//                         {
//                             ArrayList<Images> timelineImageDTOArrayList = new ArrayList<>();
//
//                             for (int j = 0; j < modelTestResult.getTimelinePostDTOArrayList().get(i).getTimelineImageDTOArrayList().size(); j++)
//                             {
//                                 Images timelineImageDTO = new Images();
//                                 timelineImageDTO.setImage(modelTestResult.getTimelinePostDTOArrayList().get(i).getTimelineImageDTOArrayList().get(j).getImage());
//                                 timelineImageDTOArrayList.add(timelineImageDTO);
//
//                             }
//
//                             timelinePostDTO.setTimelineImageDTOArrayList(timelineImageDTOArrayList);
//                         }
//
//
//
//
//
//                            timelinePostDTOArrayList.add(timelinePostDTO);
//                            adapter.notifyDataSetChanged();
//
//
//
//
//                    }
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<LoadDashBoardResult> call, Throwable t)
//            {
//                Toast.makeText(getActivity().getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
//            }
//        });
//    }
//


}