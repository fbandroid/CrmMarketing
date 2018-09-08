package com.crmmarketing.hmt.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.crmmarketing.hmt.Utils.Utils;
import com.crmmarketing.hmt.database.PostsDatabaseHelper;

import com.crmmarketing.hmt.model.InqChild;
import com.crmmarketing.hmt.model.InqMaster;

import com.crmmarketing.hmt.webservice.WSADDInquiry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class SynchToServer extends IntentService {
    @Override
    public void onCreate() {
        super.onCreate();

    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */


    public SynchToServer() {
        super("test");
    }

    @Override
    protected void onHandleIntent(Intent intent) {


        Log.e("intent service", "started");

        PostsDatabaseHelper postsDatabaseHelper = PostsDatabaseHelper.getInstance(getApplicationContext());
        ArrayList<InqMaster> arrayList = postsDatabaseHelper.getAllPosts();


        if (arrayList != null) {
            for (int i = 0; i < arrayList.size(); i++) {


                if (arrayList.get(i).getSyncStatus() != null) {


                } else {

                    if (Utils.isTimeAutomatic(getApplicationContext())) {

                        final InqMaster inqMaster = arrayList.get(i);

                        // service call

                        try {
                            final JSONObject topJsonObject = new JSONObject();

                            final JSONObject jsonObject = new JSONObject();


                            final JSONObject personal_info_json = new JSONObject();
                            personal_info_json.put("cus_name", inqMaster.getCusInfo().getCusName());
                            personal_info_json.put("cus_addr", inqMaster.getCusInfo().getCusAddr());
                            personal_info_json.put("cus_mobile", inqMaster.getCusInfo().getCusMobile());
                            personal_info_json.put("cus_land", inqMaster.getCusInfo().getCusLand());
                            personal_info_json.put("cus_email", inqMaster.getCusInfo().getCusEmail());
                            personal_info_json.put("cus_alt_email", inqMaster.getCusInfo().getCusOptEmail() == null ? "" : inqMaster.getCusInfo().getCusOptEmail());
                            personal_info_json.put("cus_birth", inqMaster.getCusInfo().getCusDob());
                            personal_info_json.put("cus_occupation", inqMaster.getCusInfo().getCusOccupation());


                            JSONObject product_info = new JSONObject();
                            String[] inqChild = new String[inqMaster.getInqChild().size()];

                            final JSONArray jsonArray = new JSONArray();
                            for (int j = 0; j < inqMaster.getInqChild().size(); j++) {
                                final List<InqChild> inqChildren = inqMaster.getInqChild();
                                inqChild[j] = inqChildren.get(j).getInqChildId();
                                final JSONObject product_info_json = new JSONObject();
                                product_info_json.put("pr_id", inqChildren.get(j).getInqChildProductId());
                                // product_info_json.put("pr_name", selectedArrayList.get(i).getProductName());
                                //   product_info_json.put("pr_category", selectedArrayList.get(i).getProductCategory());
                                jsonArray.put(j, product_info_json);
                            }
                            product_info.put("pr_list", jsonArray);

                            final JSONObject feedback_info_json = new JSONObject();
                            feedback_info_json.put("feed_date", inqMaster.getInqFeedbackTime());
                            feedback_info_json.put("feed_remark", "");

                            final JSONObject ref_info_json = new JSONObject();
                            ref_info_json.put("ref_name", inqMaster.getCusInfo().getCusRefName());
                            ref_info_json.put("ref_email", inqMaster.getCusInfo().getCusRefEmail());
                            ref_info_json.put("ref_phone", inqMaster.getCusInfo().getCusRefMobile());
                            ref_info_json.put("ref_code", inqMaster.getCusInfo().getCusRefCode() == null ? "" : inqMaster.getCusInfo().getCusRefCode());
                            ref_info_json.put("ref_other", inqMaster.getCusInfo().getCusRefOther());


                            jsonObject.put("personal_info", personal_info_json);
                            jsonObject.put("product_detail", product_info);
                            jsonObject.put("feedback_info", feedback_info_json);
                            jsonObject.put("ref_info", ref_info_json);
                            jsonObject.put("emp_id", String.valueOf(inqMaster.getInqEmpId()));
                            jsonObject.put("pr_range", inqMaster.getInqInvestRange());
                            topJsonObject.put("inquiry", jsonObject);

                            Log.e("offline", topJsonObject.toString());


                            //service call
                            WSADDInquiry wsaddInquiry = new WSADDInquiry(getApplicationContext());
                            try {
                                String responce = wsaddInquiry.postInquiry(topJsonObject.toString());
                                JSONObject jsonObject1 = new JSONObject(responce);
                                String isOk = jsonObject1.optString("msg");


                                if (isOk != null && isOk.equalsIgnoreCase("true")) {

                                    postsDatabaseHelper.updateStatus("1", inqMaster.getInqMasterId(), inqChild);
                                }

                            } catch (IOException e) {
                                e.printStackTrace();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    } else {
                        getApplicationContext().startActivity(new Intent(android.provider.Settings.ACTION_DATE_SETTINGS));
                        Toast.makeText(getApplicationContext(), "Please make time and date Automatic", Toast.LENGTH_LONG).show();
                    }


                }
            }
        }


    }
}
