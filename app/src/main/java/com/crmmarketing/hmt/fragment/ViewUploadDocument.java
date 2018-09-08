package com.crmmarketing.hmt.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DownloadManager;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.crmmarketing.hmt.GsonModel.Document;
import com.crmmarketing.hmt.GsonModel.DocumentList;

import com.crmmarketing.hmt.InquirtDetailActivity;
import com.crmmarketing.hmt.R;
import com.crmmarketing.hmt.Utils.Utils;
import com.crmmarketing.hmt.common.RetrofitClient;
import com.crmmarketing.hmt.cons.Constants;
import com.crmmarketing.hmt.model.InqMaster;
import com.crmmarketing.hmt.model.MyRes;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by USER on 05-07-2017.
 */

public class ViewUploadDocument extends Fragment {
    private static final long DOUBLE_TAP = 2000;
    private static final String IMAGE_DIRECTORY = "/CrmMarketing";
    private static final String DOWNLOAD_PATH = "http://www.tirthwealthmgt.in/crm/document/";
    private RecyclerView rvDocList;
    private Spinner spDocument;
    private String inqId;
    private DLManager dlManager;
    private String viewcase;
    private RetrofitClient.getDocumentInfo getDocInfo;
    private ArrayList<Document> documentArrayList;
    private InqMaster inqMaster;
    private Context context;
    private CustomAdapter customAdapter;
    private ProgressBar progressBar;
    private String cu_id;
    private RetrofitClient.DeleteDocument deleteDocument;
    private ProgressDialog progressDialog;


    /**
     * Create a file Uri for saving an image or video
     */
    private static Uri getOutputMediaFileUri() {
        return Uri.fromFile(getOutputMediaFile());
    }

    /**
     * Create a File for saving an image or video
     */
    private static File getOutputMediaFile() {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

//        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
//                Environment.DIRECTORY_PICTURES), IMAGE_DIRECTORY);


        File mediaStorageDir = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY, "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile = null;
        try {
            mediaFile = File.createTempFile("IMG".concat(timeStamp), ".jpg", mediaStorageDir);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return mediaFile;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.context = activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            cu_id = getArguments().getString("cu_id");
        } else {
            if (getParentFragment() != null) {

                if (getParentFragment().getArguments() != null) {

                    if (getParentFragment().getArguments().getParcelable("detail") != null) {

                        inqMaster = getParentFragment().getArguments().getParcelable("detail");
                    }
                }


            } else {
                inqMaster = ((InquirtDetailActivity) context).inqMaster;
            }

            if (inqMaster != null) {

                inqId = inqMaster.getInqMasterId();
                cu_id = inqMaster.getCusInfo().getCusId();
            }
        }


        getDocInfo = RetrofitClient.getDocument(Constants.BASE_URL);
        deleteDocument = RetrofitClient.cancelDoc(Constants.BASE_URL);
        documentArrayList = new ArrayList<>();
        dlManager = new DLManager();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_view_upload_document, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvDocList = (RecyclerView) view.findViewById(R.id.rvDocList);
        spDocument = (Spinner) view.findViewById(R.id.spDocument);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        rvDocList.setLayoutManager(new LinearLayoutManager(getActivity()));


        spDocument.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                progressBar.setVisibility(View.VISIBLE);
                switch (position) {

                    case 0:
                        viewcase = "1";
                        getDocInfo.getDocument(cu_id, viewcase).enqueue(new Callback<DocumentList>() {
                            @Override
                            public void onResponse(Call<DocumentList> call, Response<DocumentList> response) {

                                progressBar.setVisibility(View.GONE);

                                if (response.isSuccessful()) {

                                    documentArrayList = (ArrayList<Document>) response.body().getDocument();

                                    if (documentArrayList.size() > 0) {

                                        customAdapter = new CustomAdapter(documentArrayList);
                                        rvDocList.setAdapter(customAdapter);
                                    } else {
                                        customAdapter = new CustomAdapter(documentArrayList);
                                        rvDocList.setAdapter(customAdapter);
                                        Toast.makeText(getActivity(), "No data available", Toast.LENGTH_SHORT).show();
                                    }
                                }

                            }

                            @Override
                            public void onFailure(Call<DocumentList> call, Throwable t) {
                                Toast.makeText(getActivity(), "No data available", Toast.LENGTH_SHORT).show();
                            }
                        });

                        break;
                    case 1:
                        viewcase = "2";
                        getDocInfo.getDocument(cu_id, viewcase).enqueue(new Callback<DocumentList>() {
                            @Override
                            public void onResponse(Call<DocumentList> call, Response<DocumentList> response) {
                                progressBar.setVisibility(View.GONE);

                                if (response.isSuccessful()) {

                                    documentArrayList = (ArrayList<Document>) response.body().getDocument();

                                    if (documentArrayList.size() > 0) {

                                        customAdapter = new CustomAdapter(documentArrayList);
                                        rvDocList.setAdapter(customAdapter);
                                    } else {
                                        customAdapter = new CustomAdapter(documentArrayList);
                                        rvDocList.setAdapter(customAdapter);
                                        Toast.makeText(getActivity(), "No data available", Toast.LENGTH_SHORT).show();
                                    }
                                }

                            }

                            @Override
                            public void onFailure(Call<DocumentList> call, Throwable t) {
                                Toast.makeText(getActivity(), "No data available", Toast.LENGTH_SHORT).show();
                            }
                        });

                        break;
                    case 2:
                        viewcase = "3";
                        getDocInfo.getDocument(cu_id, viewcase).enqueue(new Callback<DocumentList>() {
                            @Override
                            public void onResponse(Call<DocumentList> call, Response<DocumentList> response) {
                                progressBar.setVisibility(View.GONE);

                                if (response.isSuccessful()) {

                                    documentArrayList = (ArrayList<Document>) response.body().getDocument();

                                    if (documentArrayList.size() > 0) {

                                        customAdapter = new CustomAdapter(documentArrayList);
                                        rvDocList.setAdapter(customAdapter);
                                    } else {
                                        customAdapter = new CustomAdapter(documentArrayList);
                                        rvDocList.setAdapter(customAdapter);
                                        Toast.makeText(getActivity(), "No data available", Toast.LENGTH_SHORT).show();
                                    }
                                }

                            }

                            @Override
                            public void onFailure(Call<DocumentList> call, Throwable t) {
                                Toast.makeText(getActivity(), "No data available", Toast.LENGTH_SHORT).show();
                            }
                        });

                        break;
                    case 3:
                        viewcase = "4";
                        getDocInfo.getDocument(cu_id, viewcase).enqueue(new Callback<DocumentList>() {
                            @Override
                            public void onResponse(Call<DocumentList> call, Response<DocumentList> response) {

                                progressBar.setVisibility(View.GONE);
                                if (response.isSuccessful()) {

                                    documentArrayList = (ArrayList<Document>) response.body().getDocument();

                                    if (documentArrayList.size() > 0) {

                                        customAdapter = new CustomAdapter(documentArrayList);
                                        rvDocList.setAdapter(customAdapter);
                                    } else {
                                        customAdapter = new CustomAdapter(documentArrayList);
                                        rvDocList.setAdapter(customAdapter);
                                        Toast.makeText(getActivity(), "No data available", Toast.LENGTH_SHORT).show();
                                    }
                                }

                            }

                            @Override
                            public void onFailure(Call<DocumentList> call, Throwable t) {
                                Toast.makeText(getActivity(), "No data available", Toast.LENGTH_SHORT).show();
                            }
                        });

                        break;

                    case 4:

                        viewcase = "5";
                        getDocInfo.getDocument(cu_id, viewcase).enqueue(new Callback<DocumentList>() {
                            @Override
                            public void onResponse(Call<DocumentList> call, Response<DocumentList> response) {

                                progressBar.setVisibility(View.GONE);
                                if (response.isSuccessful()) {

                                    documentArrayList = (ArrayList<Document>) response.body().getDocument();

                                    if (documentArrayList.size() > 0) {

                                        customAdapter = new CustomAdapter(documentArrayList);
                                        rvDocList.setAdapter(customAdapter);
                                    } else {
                                        customAdapter = new CustomAdapter(documentArrayList);
                                        rvDocList.setAdapter(customAdapter);
                                        Toast.makeText(getActivity(), "No data available", Toast.LENGTH_SHORT).show();
                                    }
                                }

                            }

                            @Override
                            public void onFailure(Call<DocumentList> call, Throwable t) {
                                Toast.makeText(getActivity(), "No data available", Toast.LENGTH_SHORT).show();
                            }
                        });

                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void showProgressDialog() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Loading..");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
        private static final String TAG = "CustomAdapter";

        private ArrayList<Document> mDataSet;

        // BEGIN_INCLUDE(recyclerViewSampleViewHolder)

        /**
         * Initialize the dataset of the Adapter.
         *
         * @param dataSet String[] containing the data to populate views to be used by RecyclerView.
         */
        public CustomAdapter(ArrayList<Document> dataSet) {
            mDataSet = dataSet;
        }
        // END_INCLUDE(recyclerViewSampleViewHolder)

        // BEGIN_INCLUDE(recyclerViewOnCreateViewHolder)
        // Create new views (invoked by the layout manager)
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            // Create a new view.
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.row_document_info, viewGroup, false);

            return new ViewHolder(v);
        }

        // BEGIN_INCLUDE(recyclerViewOnBindViewHolder)
        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {
            Log.d(TAG, "Element " + position + " set.");

            // Get element from your dataset at this position and replace the contents of the view
            // with that element
            viewHolder.textView.setText(mDataSet.get(position).getName());
            viewHolder.tvTime.setText(mDataSet.get(position).getDateTime());

            Glide
                    .with(context)
                    .load(DOWNLOAD_PATH.concat(mDataSet.get(position).getName()))
                    .placeholder(R.mipmap.ic_launcher)
                    .crossFade()
                    .into(viewHolder.ivPic);


        }
        // END_INCLUDE(recyclerViewOnCreateViewHolder)

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mDataSet.size();
        }
        // END_INCLUDE(recyclerViewOnBindViewHolder)

        /**
         * Provide a reference to the type of views that you are using (custom ViewHolder)
         */
        public class ViewHolder extends RecyclerView.ViewHolder {
            private final TextView textView;
            private final ImageView ivPic;
            private final TextView tvTime;
            private final ImageView ivDelete;

            public ViewHolder(View v) {
                super(v);
                // Define click listener for the ViewHolder's View.
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d(TAG, "Element " + getAdapterPosition() + " clicked.");

                        if (Utils.checkInternetConnection(getActivity()))

                            dlManager.useDownloadManager(DOWNLOAD_PATH.concat(mDataSet.get(getAdapterPosition()).getName()), mDataSet.get(getAdapterPosition()).getName(), getActivity());

                    }
                });
                textView = (TextView) v.findViewById(R.id.tvImageName);
                ivPic = (ImageView) v.findViewById(R.id.ivPic);
                tvTime = (TextView) v.findViewById(R.id.tvTime);
                ivDelete = (ImageView) v.findViewById(R.id.ivDelete);

                ivDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // TODO delete icon service

                        showProgressDialog();

                        deleteDocument.deleteDoc(mDataSet.get(getAdapterPosition()).getId(),
                                mDataSet.get(getAdapterPosition()).getName())
                                .enqueue(new Callback<MyRes>() {
                                    @Override
                                    public void onResponse(Call<MyRes> call, Response<MyRes> response) {

                                        if (progressDialog.isShowing()) {
                                            progressDialog.dismiss();
                                        }

                                        if (response.isSuccessful()) {
                                            if (response.body().getMsg().equalsIgnoreCase("true")) {

                                                if (getActivity() != null) {
                                                    Toast.makeText(getActivity(), "Successfully deleted", Toast.LENGTH_SHORT).show();
                                                    getActivity().finish();
                                                }

                                            } else {
                                                if (getActivity() != null) {
                                                    Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        } else {
                                            if (getActivity() != null) {
                                                Toast.makeText(getActivity(), "Something went wrong..", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                    }

                                    @Override
                                    public void onFailure(Call<MyRes> call, Throwable t) {
                                        if (progressDialog.isShowing()) {
                                            progressDialog.dismiss();
                                        }

                                        if (getActivity() != null) {
                                            Toast.makeText(getActivity(), "Connection error", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                    }
                });
            }

            public TextView getTextView() {
                return textView;
            }
        }
    }

    public class DLManager {
        @SuppressLint("NewApi")
        public void useDownloadManager(String url, String name, Context c) {
            DownloadManager dm = (DownloadManager) c
                    .getSystemService(Context.DOWNLOAD_SERVICE);
            DownloadManager.Request dlrequest = new DownloadManager.Request(
                    Uri.parse(url));
            dlrequest
                    .setAllowedNetworkTypes(
                            DownloadManager.Request.NETWORK_WIFI
                                    | DownloadManager.Request.NETWORK_MOBILE)
                    .setTitle("Document")
                    .setDescription("Downloading in Progress..")
                    .setDestinationInExternalPublicDir("CrmMarketing", name + ".jpg")
                    .allowScanningByMediaScanner();

            dm.enqueue(dlrequest);

        }
    }
}
