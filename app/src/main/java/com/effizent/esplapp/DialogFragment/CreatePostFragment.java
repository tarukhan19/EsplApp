package com.effizent.esplapp.DialogFragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.effizent.esplapp.Activity.CustomGallery_Activity;
import com.effizent.esplapp.Activity.HomeActivity;
import com.effizent.esplapp.Activity.LoginActivity;
import com.effizent.esplapp.Adapter.BitmapImageAdapter;
import com.effizent.esplapp.Adapter.DepartmentAdapter;
import com.effizent.esplapp.Adapter.DepartmentAdp;
import com.effizent.esplapp.Model.DepartmentDTO;
import com.effizent.esplapp.R;
import com.effizent.esplapp.RetroApiResponses.CreatePostResult;
import com.effizent.esplapp.RetroApiResponses.LoadDepartmentResult;
import com.effizent.esplapp.Retropack.APIServices;
import com.effizent.esplapp.Retropack.RetrofitFactory;
import com.effizent.esplapp.Utils.FileUtils;
import com.effizent.esplapp.databinding.FragmentCreatePostBinding;
import com.effizent.esplapp.session.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class CreatePostFragment extends DialogFragment {
    Dialog maindialog;
    FragmentCreatePostBinding binding;
    TextView toolbartitle;
    ImageView backIV,logoutIV;
    String title="",description="",department="";
    SessionManager session;

    private ArrayList<String> permissionsToRequest;
    private ArrayList<String> permissionsRejected = new ArrayList<>();
    private ArrayList<String> permissions = new ArrayList<>();
    List<String> selectedImages;

    private final static int ALL_PERMISSIONS_RESULT = 107;
    private final static int IMAGE_RESULT = 200;

    Bitmap scaledBitmap ;
    Uri uri;
    private ArrayList<Bitmap> photoBMList;
    private ArrayList<Uri> uriArrayList;

    private BitmapImageAdapter photoADP;
    public static final String CustomGalleryIntentKey = "ImageArray";//Set Intent Key Value


    Dialog departmentDialog;
    AlertDialog.Builder departmentalertdialog;
    View departmentView;
    ArrayList<String>  departmentlistnamerray;

    ArrayList<DepartmentDTO> departmentDTOArrayList;

    RecyclerView departmentrv;

    DepartmentAdapter departmentAdapter;


    public Dialog onCreateDialog(final Bundle savedInstanceState)
    {
        maindialog = super.onCreateDialog(savedInstanceState);
        binding = DataBindingUtil.inflate(LayoutInflater.from(maindialog.getContext()), R.layout.fragment_create_post, null, false);
        maindialog.getWindow().getAttributes().windowAnimations = R.style.CustomDialogFragmentAnimation;
        maindialog.setContentView(binding.getRoot());
        maindialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        maindialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        maindialog.show();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        initialize();

        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                getDialog().dismiss();
            }
        });

        binding.photoUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                selectImage(getActivity());


            }
        });

        binding.submitBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidation();
            }
        });


        binding.departspinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                departmentalertdialog.setView(departmentView);
                departmentDialog.show();
            }
        });


        return maindialog;
    }

    private void checkValidation() {
        title = toBase64(binding.titleET.getText().toString());
        description = toBase64(binding.descriptionET.getText().toString());
        department=departmentAdapter.getDepartmentName();
        Log.e("department",department);
        if (title.isEmpty() ) {
            binding.transLL.setVisibility(View.VISIBLE);
            openDialog("Empty fields ", "Warning!");
        }  else {
            binding.transLL.setVisibility(View.VISIBLE);
            binding.progressbar.setVisibility(View.VISIBLE);
            submit();
        }
    }


    private void initialize()
    {
        Toolbar toolbar = (Toolbar) maindialog.findViewById(R.id.toolbar);
        backIV = toolbar.findViewById(R.id.backIV);
        logoutIV = toolbar.findViewById(R.id.logoutIV);
        toolbartitle = toolbar.findViewById(R.id.toolbartitle);
        session = new SessionManager(getActivity());


        logoutIV.setVisibility(View.GONE);
        toolbartitle.setText("Create Post");

        permissions.add(Manifest.permission.CAMERA);
        permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        permissionsToRequest = findUnAskedPermissions(permissions);

        binding.photoView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        binding.photoView.setHasFixedSize(true);
        binding.photoView.setItemViewCacheSize(20);
        binding.photoView.setDrawingCacheEnabled(true);
        binding.photoView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        photoBMList = new ArrayList<>();
        uriArrayList=new ArrayList<>();
        photoADP = new BitmapImageAdapter(getActivity(), photoBMList);
        binding.photoView.setAdapter(photoADP);
        departmentlistnamerray = new ArrayList<>();

        departmentDTOArrayList = new ArrayList<>();
        departmentlistnamerray.add(session.getLoginDetails().get(SessionManager.KEY_DEPARTMENT));

        departmentAdapter = new DepartmentAdapter(getActivity(), departmentDTOArrayList,binding.departmentrecycle,departmentlistnamerray);

        departmentalertdialog = new AlertDialog.Builder(getActivity());
        departmentalertdialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
            }
        });
        departmentView = LayoutInflater.from(getActivity()).inflate(R.layout.item_rv, null);
        departmentalertdialog.setView(departmentView);
        departmentDialog = departmentalertdialog.create();
        departmentrv = (RecyclerView) departmentView.findViewById(R.id.rv);

        departmentrv.setLayoutManager(new LinearLayoutManager(getActivity()));
        departmentrv.setHasFixedSize(true);
        departmentrv.setAdapter(departmentAdapter);

        loadSpinnerData();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (permissionsToRequest.size() > 0)
                requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
        }
        if (android.os.Build.VERSION.SDK_INT >= 21)
        {
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimary));
        }

    }

    private void selectImage(Context context) {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        builder.setTitle("Choose your profile picture");

        builder.setItems(options, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo")) {
                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);

                } else if (options[item].equals("Choose from Gallery")) {
//                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                    startActivityForResult(pickPhoto, 1);

                    startActivityForResult(new Intent(getActivity(), CustomGallery_Activity.class), 1);
                    dialog.dismiss();


                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
        {
            switch (requestCode) {



                case 0:
                    try {
                        scaledBitmap = (Bitmap) data.getExtras().get("data");
                        uri=getImageUri(getActivity(),scaledBitmap);

                        binding.photoView.setVisibility(View.VISIBLE);

                        photoBMList.add(scaledBitmap);
                        uriArrayList.add(uri);
                        photoADP.notifyDataSetChanged();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;

                case 1:
                    String imagesArray = data.getStringExtra(CustomGalleryIntentKey);//get Intent data
                    Log.e("imagesArray",imagesArray);
                    selectedImages = Arrays.asList(imagesArray.substring(1, imagesArray.length() - 1).split(", "));
                    for (int i=0;i<selectedImages.size();i++)
                    {
                        String image=selectedImages.get(i);
                        String filePath = getRealPathFromURI(image);
                        Log.e("filePath",filePath);

                        try {
                            File imgFile = new  File(filePath);
                            if(imgFile.exists()){
                                scaledBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                               uri = Uri.fromFile(imgFile);
                            }
                            Log.e("scaledBitmap",scaledBitmap+"");

                            binding.photoView.setVisibility(View.VISIBLE);
                            photoBMList.add(scaledBitmap);
                            uriArrayList.add(uri);

                        } catch(Exception e) {
                            e.getMessage();
                            Toast.makeText(getActivity(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                        }


                    }

                    photoADP.notifyDataSetChanged();
                    //  loadGridView(new ArrayList<String>(selectedImages));//call load gridview method by passing converted list into arrayList

                    break;
            }
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    private String getRealPathFromURI(String contentURI) {
        Uri contentUri = Uri.parse(contentURI);
        Cursor cursor = getActivity().getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }

    private ArrayList<String> findUnAskedPermissions(ArrayList<String> wanted) {
        ArrayList<String> result = new ArrayList<String>();

        for (String perm : wanted) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }

        return result;
    }

    private boolean hasPermission(String permission) {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (getActivity().checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new androidx.appcompat.app.AlertDialog.Builder(getActivity())
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {

            case ALL_PERMISSIONS_RESULT:
                for (String perms : permissionsToRequest) {
                    if (!hasPermission(perms)) {
                        permissionsRejected.add(perms);
                    }
                }

                if (permissionsRejected.size() > 0) {


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                            showMessageOKCancel("These permissions are mandatory for the application. Please allow access.",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {


                                                requestPermissions(permissionsRejected.toArray(new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                            }
                                        }
                                    });
                            return;
                        }
                    }

                }

                break;
        }

    }

    private void openDialog(String description, String title)
    {
        final Dialog dialog = new Dialog(getActivity(), R.style.CustomDialog);
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
                    maindialog.dismiss();
                    Intent intent=new Intent(getActivity(), HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

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


    private void submit()
    {
       binding.progressbar.setVisibility(View.VISIBLE);
       binding.transLL.setVisibility(View.VISIBLE);

        RequestBody rbTitle = RequestBody.create(MediaType.parse("multipart/form-data"), title);
        RequestBody rbDescription = RequestBody.create(MediaType.parse("multipart/form-data"), description);
        RequestBody rbId = RequestBody.create(MediaType.parse("multipart/form-data"), session.getLoginDetails().get(SessionManager.KEY_ID));
        RequestBody rbDepartment = RequestBody.create(MediaType.parse("multipart/form-data"), department);


        // create list of file parts (photo, video, ...)
        List<MultipartBody.Part> parts = new ArrayList<>();

        // create upload service client

        if (uriArrayList != null) {
            // create part for file (photo, video, ...)
            for (int i = 0; i < photoBMList.size(); i++) {
                parts.add(prepareFilePart("image"+i, uriArrayList.get(i)));
            }
        }


        Retrofit retrofit = RetrofitFactory.getRetrofit();
        APIServices service = retrofit.create(APIServices.class);

        Call<CreatePostResult> call = service.createPost(rbId,rbTitle,rbDescription,rbDepartment,parts);

        call.enqueue(new Callback<CreatePostResult>() {
            @Override
            public void onResponse(Call<CreatePostResult> call, Response<CreatePostResult> response)
            {
                binding.transLL.setVisibility(View.GONE);
                binding.progressbar.setVisibility(View.GONE);
                Log.e("response.isSuccessful()",response.isSuccessful()+"");

                CreatePostResult  createPostResult = response.body();
                if (createPostResult.getCode().equalsIgnoreCase("200")  && createPostResult.getStatus().equalsIgnoreCase("Success"))
                {
                    binding.titleET.setText("");
                    binding.descriptionET.setText("");
                    photoBMList.clear();
                    uriArrayList.clear();
                    departmentlistnamerray.clear();
                    binding.departmentrecycle.setVisibility(View.GONE);
                    binding.photoView.setVisibility(View.GONE);

                    openDialog("Post created successfully!","Success!");
                }
                else
                {
                    openDialog(createPostResult.getStatus(),"Failure!");
                }
            }

            @Override
            public void onFailure(Call<CreatePostResult> call, Throwable t) {
                binding.transLL.setVisibility(View.GONE);
                binding.progressbar.setVisibility(View.GONE);
                Log.e("registrationerrort",t.toString());
                openDialog(t.getLocalizedMessage(),"Failure!");

            }
        });

    }


    @NonNull
    private MultipartBody.Part prepareFilePart(String partName, Uri fileUri) {
        // use the FileUtils to get the actual file by uri
        File file = FileUtils.getFile(getActivity(), fileUri);

        // create RequestBody instance from file
        RequestBody requestFile = RequestBody.create (MediaType.parse(FileUtils.MIME_TYPE_IMAGE), file);

        // MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }

    public static String toBase64(String message) {
        byte[] data;
        try {
            data = message.getBytes("UTF-8");
            String base64Sms = Base64.encodeToString(data, Base64.DEFAULT);
            return base64Sms;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void loadSpinnerData()
    {
       binding.progressbar.setVisibility(View.VISIBLE);
       binding.transLL.setVisibility(View.VISIBLE);
        Retrofit retrofit = RetrofitFactory.getRetrofit();
        APIServices service = retrofit.create(APIServices.class);
        service.loadDepartment();
        Call<LoadDepartmentResult> call = service.loadDepartment();


        call.enqueue(new Callback<LoadDepartmentResult>() {
            @Override
            public void onResponse(Call<LoadDepartmentResult> call, Response<LoadDepartmentResult> response) {
                binding.progressbar.setVisibility(View.GONE);
                binding.transLL.setVisibility(View.GONE);
                LoadDepartmentResult modelTestResult = response.body();
                if (modelTestResult.getCode().equalsIgnoreCase("200") && modelTestResult.getStatus().equalsIgnoreCase("Success"))
                {
                    for (int i = 0; i < modelTestResult.getDepartmentDTOArrayList().size(); i++) {
                        DepartmentDTO departmentDTO = new DepartmentDTO();
                        departmentDTO.setDeptName(modelTestResult.getDepartmentDTOArrayList().get(i).getDeptName());

                        Log.e("getDeptName",modelTestResult.getDepartmentDTOArrayList().get(i).getDeptName());

                        departmentDTOArrayList.add(departmentDTO);
                    }
                    departmentrv.setAdapter(departmentAdapter);

                }

            }


            @Override
            public void onFailure(Call<LoadDepartmentResult> call, Throwable t) {
                binding.progressbar.setVisibility(View.GONE);
                binding.transLL.setVisibility(View.GONE);

                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

}