package com.crmmarketing.hmt.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DownloadManager;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class UploadDocumentFragment extends Fragment {

    private static final long DOUBLE_TAP = 2000;
    private static final String IMAGE_DIRECTORY = "/CrmMarketing";
    private static final String DOWNLOAD_PATH = "http://tcrm.codefuelindia.com/document/";
    private final int GALLERY = 1, CAMERA = 2;
    private Button btnUpload;
    private RecyclerView rvDocList;
    private long lastclick;
    private RetrofitClient.UploadImageToServer uploadImageToServer;
    private ProgressDialog progressDialog;
    private Uri fileUri;
    private InqMaster inqMaster;
    private Context context;
    private String inqId;
    private String cusId;
    private CustomAdapter customAdapter;
    private RetrofitClient.getDocumentInfo getDocInfo;
    private ArrayList<Document> documentArrayList;
    private DLManager dlManager;
    private RadioGroup rgDocument;
    private RadioButton rbPAN;
    private RadioButton rbAdhar;
    private RadioButton rbBank;
    private RadioButton rbPhoto;
    private String caseOff = "";
    private String viewcase = "";
    private Spinner spDocument;

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
        uploadImageToServer = RetrofitClient.uploadImage(Constants.BASE_URL);
        getDocInfo = RetrofitClient.getDocument(Constants.BASE_URL);
        documentArrayList = new ArrayList<>();
        dlManager = new DLManager();

        if (getArguments() != null) {
            inqId = getArguments().getString("inq_id");
            cusId=getArguments().getString("cu_id");
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
                cusId=inqMaster.getCusInfo().getCusId();
            }
        }


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_upload_document, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvDocList = (RecyclerView) view.findViewById(R.id.rvDocList);
        btnUpload = (Button) view.findViewById(R.id.btnUpload);
        rgDocument = (RadioGroup) view.findViewById(R.id.rgDocument);
        rbPAN = (RadioButton) view.findViewById(R.id.rbPAN);
        rbAdhar = (RadioButton) view.findViewById(R.id.rbAdhar);
        rbBank = (RadioButton) view.findViewById(R.id.rbBank);
        rbPhoto = (RadioButton) view.findViewById(R.id.rbPhoto);
        spDocument = (Spinner) view.findViewById(R.id.spDocument);

//        spDocument.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//                switch (position) {
//
//                    case 0:
//                        viewcase = "1";
//                        getDocInfo.getDocument(inqId, viewcase).enqueue(new Callback<DocumentList>() {
//                            @Override
//                            public void onResponse(Call<DocumentList> call, Response<DocumentList> response) {
//
//
//                                if (response.isSuccessful()) {
//
//                                    documentArrayList = (ArrayList<Document>) response.body().getDocument();
//
//                                    if (documentArrayList.size() > 0) {
//
//                                        customAdapter = new CustomAdapter(documentArrayList);
//                                        rvDocList.setAdapter(customAdapter);
//                                    } else {
//                                        customAdapter = new CustomAdapter(documentArrayList);
//                                        rvDocList.setAdapter(customAdapter);
//                                        Toast.makeText(getActivity(), "No data available", Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//
//                            }
//
//                            @Override
//                            public void onFailure(Call<DocumentList> call, Throwable t) {
//                                Toast.makeText(getActivity(), "No data available", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//
//                        break;
//                    case 1:
//                        viewcase = "2";
//                        getDocInfo.getDocument(inqId, viewcase).enqueue(new Callback<DocumentList>() {
//                            @Override
//                            public void onResponse(Call<DocumentList> call, Response<DocumentList> response) {
//
//
//                                if (response.isSuccessful()) {
//
//                                    documentArrayList = (ArrayList<Document>) response.body().getDocument();
//
//                                    if (documentArrayList.size() > 0) {
//
//                                        customAdapter = new CustomAdapter(documentArrayList);
//                                        rvDocList.setAdapter(customAdapter);
//                                    } else {
//                                        customAdapter = new CustomAdapter(documentArrayList);
//                                        rvDocList.setAdapter(customAdapter);
//                                        Toast.makeText(getActivity(), "No data available", Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//
//                            }
//
//                            @Override
//                            public void onFailure(Call<DocumentList> call, Throwable t) {
//                                Toast.makeText(getActivity(), "No data available", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//
//                        break;
//                    case 2:
//                        viewcase = "3";
//                        getDocInfo.getDocument(inqId, viewcase).enqueue(new Callback<DocumentList>() {
//                            @Override
//                            public void onResponse(Call<DocumentList> call, Response<DocumentList> response) {
//
//
//                                if (response.isSuccessful()) {
//
//                                    documentArrayList = (ArrayList<Document>) response.body().getDocument();
//
//                                    if (documentArrayList.size() > 0) {
//
//                                        customAdapter = new CustomAdapter(documentArrayList);
//                                        rvDocList.setAdapter(customAdapter);
//                                    } else {
//                                        customAdapter = new CustomAdapter(documentArrayList);
//                                        rvDocList.setAdapter(customAdapter);
//                                        Toast.makeText(getActivity(), "No data available", Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//
//                            }
//
//                            @Override
//                            public void onFailure(Call<DocumentList> call, Throwable t) {
//                                Toast.makeText(getActivity(), "No data available", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//
//                        break;
//                    case 3:
//                        viewcase = "4";
//                        getDocInfo.getDocument(inqId, viewcase).enqueue(new Callback<DocumentList>() {
//                            @Override
//                            public void onResponse(Call<DocumentList> call, Response<DocumentList> response) {
//
//
//                                if (response.isSuccessful()) {
//
//                                    documentArrayList = (ArrayList<Document>) response.body().getDocument();
//
//                                    if (documentArrayList.size() > 0) {
//
//                                        customAdapter = new CustomAdapter(documentArrayList);
//                                        rvDocList.setAdapter(customAdapter);
//                                    } else {
//                                        customAdapter = new CustomAdapter(documentArrayList);
//                                        rvDocList.setAdapter(customAdapter);
//                                        Toast.makeText(getActivity(), "No data available", Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//
//                            }
//
//                            @Override
//                            public void onFailure(Call<DocumentList> call, Throwable t) {
//                                Toast.makeText(getActivity(), "No data available", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//
//                        break;
//                }
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

        btnUpload.setEnabled(false);


        rgDocument.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

//                switch (checkedId) {
//
//                    case R.id.rbPAN:
//                        caseOff = "1";
//                        break;
//                    case R.id.rbAdhar:
//                        caseOff = "2";
//                        break;
//                    case R.id.rbBank:
//                        caseOff = "3";
//                        break;
//                    case R.id.rbPhoto:
//                        caseOff = "4";
//                        break;
//                }

            }
        });

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvDocList.getContext(), new LinearLayoutManager(getActivity()).getOrientation());

        rvDocList.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvDocList.addItemDecoration(dividerItemDecoration);

//        getDocInfo.getDocument(inqId, viewcase).enqueue(new Callback<DocumentList>() {
//            @Override
//            public void onResponse(Call<DocumentList> call, Response<DocumentList> response) {
//
//
//                if (response.isSuccessful()) {
//
//                    documentArrayList = (ArrayList<Document>) response.body().getDocument();
//
//                    if (documentArrayList.size() > 0) {
//
//                        customAdapter = new CustomAdapter(documentArrayList);
//                        rvDocList.setAdapter(customAdapter);
//                    } else {
//                        Toast.makeText(getActivity(), "No data available", Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<DocumentList> call, Throwable t) {
//                Toast.makeText(getActivity(), "No data available", Toast.LENGTH_SHORT).show();
//            }
//        });

        if (inqMaster != null && inqMaster.getStausInq().equals("1")) {

            btnUpload.setEnabled(true);
        }
        else if(getArguments()!=null){
            btnUpload.setEnabled(true);
        }



        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isDoubleClick()) {
                    return;
                }

                showPictureDialog();


            }
        });
    }

    public boolean isDoubleClick() {
        long clickTime = System.currentTimeMillis();
        if (clickTime - lastclick < DOUBLE_TAP) {
            lastclick = clickTime;
            return true;
        }
        lastclick = clickTime;
        return false;
    }

    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(getActivity());
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {

        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        fileUri = getOutputMediaFileUri();
        Log.e("file uri", fileUri.toString());

        // call upload service
        if (fileUri != null) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            startActivityForResult(intent, CAMERA);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), contentURI);
                    String path = saveImage(bitmap);
                    Log.e("path", path);

                    //call upload service
                    uploadFile(Uri.parse(path));


                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            if (fileUri != null)

            {

                Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath());
                saveImage(bitmap);

                uploadFile(fileUri);
            }


        }
    }

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(getActivity(),
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }


    // Uploading Image/Video
    private void uploadFile(Uri fileUri) {

        showProgressDialog();

        // Map is used to multipart the file using okhttp3.RequestBody
        File file = new File(fileUri.getPath());

        switch (rgDocument.getCheckedRadioButtonId()) {

            case R.id.rbPAN:
                caseOff = "1";
                break;
            case R.id.rbAdhar:
                caseOff = "2";
                break;
            case R.id.rbBank:
                caseOff = "3";
                break;
            case R.id.rbPhoto:
                caseOff = "4";
                break;
            case R.id.rbNomineePAN:
                caseOff = "5";
                break;

        }


        // Parsing any Media type file
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());
        RequestBody formBody = RequestBody.create(MediaType.parse("text/plain"), inqId);
        RequestBody typeBody = RequestBody.create(MediaType.parse("text/plain"), caseOff);
        RequestBody cusBody=RequestBody.create(MediaType.parse("text/plain"),cusId);


        uploadImageToServer.uploadFile(fileToUpload, filename, formBody, typeBody,cusBody).enqueue(new Callback<MyRes>() {
            @Override
            public void onResponse(Call<MyRes> call, Response<MyRes> response) {

                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                if (response.isSuccessful()) {

                    if (response.body().getMsg().equalsIgnoreCase("true")) {

                        Toast.makeText(getActivity(), "Upload Successfully", Toast.LENGTH_SHORT).show();

                        if(getArguments()!=null){
                            getFragmentManager().popBackStack();
                        }

                    } else {
                        Toast.makeText(getActivity(), "internal error", Toast.LENGTH_SHORT).show();
                        if(getArguments()!=null){
                            getFragmentManager().popBackStack();
                        }
                    }
                }


            }

            @Override
            public void onFailure(Call<MyRes> call, Throwable t) {
                Toast.makeText(getActivity(), "internal error", Toast.LENGTH_SHORT).show();

                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                if(getArguments()!=null){
                    getFragmentManager().popBackStack();
                }
            }
        });


    }

    public void showProgressDialog() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Loading..");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }


    /**
     * Provide views to RecyclerView with data from mDataSet.
     */
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

