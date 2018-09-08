package com.crmmarketing.hmt.common;


import com.crmmarketing.hmt.GsonModel.AttendanceHistory22;
import com.crmmarketing.hmt.GsonModel.AttendanceSummary;
import com.crmmarketing.hmt.GsonModel.Attendancelist;
import com.crmmarketing.hmt.GsonModel.BranchList;
import com.crmmarketing.hmt.GsonModel.ChainInquiry;
import com.crmmarketing.hmt.GsonModel.Chaindatum;
import com.crmmarketing.hmt.GsonModel.CheckListAll;
import com.crmmarketing.hmt.GsonModel.ConfirmCheckList;
import com.crmmarketing.hmt.GsonModel.Counter;
import com.crmmarketing.hmt.GsonModel.DocumentList;
import com.crmmarketing.hmt.GsonModel.Example;
import com.crmmarketing.hmt.GsonModel.User;
import com.crmmarketing.hmt.GsonModel.UserDetail;
import com.crmmarketing.hmt.gsonmodel22.ApprLeave;
import com.crmmarketing.hmt.gsonmodel22.AttendanceDetail;
import com.crmmarketing.hmt.gsonmodel22.ClientDetail;
import com.crmmarketing.hmt.gsonmodel22.EventDetail;
import com.crmmarketing.hmt.gsonmodel22.EventList;
import com.crmmarketing.hmt.gsonmodel22.InvitedCustomer;
import com.crmmarketing.hmt.gsonmodel22.InvitedList;
import com.crmmarketing.hmt.gsonmodel22.LeaveHistoryList;
import com.crmmarketing.hmt.gsonmodel22.LeaveRequest;
import com.crmmarketing.hmt.gsonmodel22.MapData;
import com.crmmarketing.hmt.gsonmodel22.Present;
import com.crmmarketing.hmt.gsonmodel22.SendInvitation;
import com.crmmarketing.hmt.gsonmodel22.TravelReport;
import com.crmmarketing.hmt.gsonmodel22.VolumeData;
import com.crmmarketing.hmt.gsonmodel33.EcsList;
import com.crmmarketing.hmt.model.GetByDateInquiry;
import com.crmmarketing.hmt.model.InqMaster;
import com.crmmarketing.hmt.model.MyRes;
import com.crmmarketing.hmt.model.Travel;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class RetrofitClient {

    private static Retrofit retrofit = null;


    private static Retrofit getClient(String baseUrl) {
        if (retrofit == null) {
            final OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder().connectTimeout(3000, TimeUnit.MILLISECONDS);

            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(okHttpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;


    }

    public static getBranchList getBranchList(String baseurl) {
        return RetrofitClient.getClient(baseurl).create(getBranchList.class);
    }

    public static APIServiceCheckLoginStatus checkLoginStatus(String baseurl) {
        return RetrofitClient.getClient(baseurl).create(APIServiceCheckLoginStatus.class);
    }

    public static APIServiceChangePwd changePwd(String baseurl) {
        return RetrofitClient.getClient(baseurl).create(APIServiceChangePwd.class);
    }

    public static APIServiceSendLocation sendLocation(String baseurl) {
        return RetrofitClient.getClient(baseurl).create(APIServiceSendLocation.class);
    }

    public static APIServiceCheckLocation checkLocation(String baseurl) {
        return RetrofitClient.getClient(baseurl).create(APIServiceCheckLocation.class);
    }

    public static APIServiceLogOut logOut(String baseurl) {
        return RetrofitClient.getClient(baseurl).create(APIServiceLogOut.class);
    }

    public static APIServicegetInqByDate getInQByDate(String baseurl) {
        return RetrofitClient.getClient(baseurl).create(APIServicegetInqByDate.class);
    }

    public static APIServicegetAttendanceSummary getAttendanceSummary(String baseurl) {
        return RetrofitClient.getClient(baseurl).create(APIServicegetAttendanceSummary.class);
    }

    public static APIServicegetAttendanceDetail getAttendanceDetail(String baseurl) {
        return RetrofitClient.getClient(baseurl).create(APIServicegetAttendanceDetail.class);
    }

    public static APIServicegetAttendanceHistory getAttendanceHistory(String baseurl) {
        return RetrofitClient.getClient(baseurl).create(APIServicegetAttendanceHistory.class);
    }

    public static APIServicegetChainInq getInqChain(String baseurl) {
        return RetrofitClient.getClient(baseurl).create(APIServicegetChainInq.class);
    }

    public static APIServiceGetDistanceMap getDistanceMap(String baseurl) {
        return new RetrofitClient().getClient22(baseurl).create(APIServiceGetDistanceMap.class);
    }

    public static UploadImageToServer uploadImage(String baseurl) {
        return new RetrofitClient().getClient22(baseurl).create(UploadImageToServer.class);
    }

    public static getDocumentInfo getDocument(String baseurl) {
        return new RetrofitClient().getClient22(baseurl).create(getDocumentInfo.class);
    }

    public static getCounterInfo getCounter(String baseurl) {
        return new RetrofitClient().getClient22(baseurl).create(getCounterInfo.class);
    }

    public static setChecklist setCheck(String baseurl) {
        return new RetrofitClient().getClient22(baseurl).create(setChecklist.class);
    }

    public static getCheckListAll getCheckList(String baseurl) {
        return new RetrofitClient().getClient22(baseurl).create(getCheckListAll.class);
    }

    public static Confirmchecklistapi confirmchecklist(String baseurl) {
        return new RetrofitClient().getClient22(baseurl).create(Confirmchecklistapi.class);
    }

    public static MakeMyTrip makeTrip(String baseurl) {
        return new RetrofitClient().getClient22(baseurl).create(MakeMyTrip.class);
    }

    public static StartTrip starttrip(String baseurl) {
        return new RetrofitClient().getClient22(baseurl).create(StartTrip.class);
    }

    public static SearchByTSBPAN searchbytsbpan(String baseurl) {
        return new RetrofitClient().getClient22(baseurl).create(SearchByTSBPAN.class);
    }

    public static GetClientList getclientlist(String baseurl) {
        return new RetrofitClient().getClient22(baseurl).create(GetClientList.class);
    }

    public static RequestExpense requestExpense(String baseurl) {
        return new RetrofitClient().getClient22(baseurl).create(RequestExpense.class);
    }

    public static EventList geteventlist(String baseurl) {
        return new RetrofitClient().getClient22(baseurl).create(EventList.class);
    }

    public static EventDetailList geteventdetaillist(String baseurl) {
        return new RetrofitClient().getClient22(baseurl).create(EventDetailList.class);
    }

    public static TakeAttd takeattd(String baseurl) {
        return new RetrofitClient().getClient22(baseurl).create(TakeAttd.class);
    }

    public static TravelReport getTravelReport(String baseurl) {
        return new RetrofitClient().getClient22(baseurl).create(TravelReport.class);
    }

    public static EmpList getEmpList(String baseurl) {
        return new RetrofitClient().getClient22(baseurl).create(EmpList.class);
    }

    public static RequestLeave requestLeave(String baseurl) {
        return new RetrofitClient().getClient22(baseurl).create(RequestLeave.class);
    }

    public static ViewLeave viewleave(String baseurl) {
        return new RetrofitClient().getClient22(baseurl).create(ViewLeave.class);
    }

    public static CancelLeave cancelleaveOf(String baseurl) {
        return new RetrofitClient().getClient22(baseurl).create(CancelLeave.class);
    }

    public static EventReport eventReport(String baseurl) {
        return new RetrofitClient().getClient22(baseurl).create(EventReport.class);
    }


    public static InvitedCustomer invitedCustomer(String baseurl) {
        return new RetrofitClient().getClient22(baseurl).create(InvitedCustomer.class);
    }

    public static Volumedata getvolume(String baseurl) {
        return new RetrofitClient().getClient22(baseurl).create(Volumedata.class);
    }

    public static ViewApproveListLeave getApprLeave(String baseurl) {
        return new RetrofitClient().getClient22(baseurl).create(ViewApproveListLeave.class);
    }

    public static ApproveLeave setApprove(String baseurl) {
        return new RetrofitClient().getClient22(baseurl).create(ApproveLeave.class);
    }

    public static EditLeave editLeaveReq(String baseurl) {
        return new RetrofitClient().getClient22(baseurl).create(EditLeave.class);
    }

    public static FetchInvitationList getInvitationList(String baseurl) {
        return new RetrofitClient().getClient22(baseurl).create(FetchInvitationList.class);
    }

    public static InviteCustomer sendInvitsationTo(String baseurl) {
        return new RetrofitClient().getClient22(baseurl).create(InviteCustomer.class);
    }

    public static DeleteDocument cancelDoc(String baseUrl) {
        return new RetrofitClient().getClient22(baseUrl).create(DeleteDocument.class);
    }

    public static ApproveByOperation approveInq(String baseUrl) {
        return new RetrofitClient().getClient22(baseUrl).create(ApproveByOperation.class);
    }


    public static GetECSList ecslistOf(String baseUrl) {
        return new RetrofitClient().getClient22(baseUrl).create(GetECSList.class);
    }

    public static ECSAction setApproveEcs(String baseUrl) {
        return new RetrofitClient().getClient22(baseUrl).create(ECSAction.class);
    }


    public static  CheckFollowUPStatus checkStatus(String baseUrl){
        return  new RetrofitClient().getClient22(baseUrl).create(CheckFollowUPStatus.class);
    }

    public static  SubmitFollowUp submitFollowUp(String baseUrl){
        return  new RetrofitClient().getClient22(baseUrl).create(SubmitFollowUp.class);
    }

    private Retrofit getClient22(String baseUrl) {
        final Retrofit retrofit22;

        final OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder().connectTimeout(5000, TimeUnit.MILLISECONDS);

        retrofit22 = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit22;


    }

    public interface getBranchList {

        @POST("home/branchlistapi")
        Call<BranchList> branch();

    }

    public interface APIServiceChangePwd {
        @POST("home/changepassword_api")
        @FormUrlEncoded
        Call<MyRes> changePwd(@Field("id") String id, @Field("old_pwd") String old_pwd, @Field("new_pwd") String new_pwd);
    }

    public interface APIServiceSendLocation {

        @POST("home/addattendanceapi/")
        @FormUrlEncoded
        Call<MyRes> sendLoc(@Field("id") String id, @Field("lat") String lat, @Field("log") String log, @Field("case") String caseOf, @Field("role") String role);
    }

    public interface APIServiceCheckLocation {

        @POST("home/checkattendanceapi")
        @FormUrlEncoded
        Call<MyRes> checKLoc(@Field("id") String id);
    }

    public interface APIServiceLogOut {

        @POST("home/logoutapi")
        @FormUrlEncoded
        Call<MyRes> logOut(@Field("id") String id);
    }

    public interface APIServiceCheckLoginStatus {

        @POST("user/checkuserloginapi/")
        @FormUrlEncoded
        Call<MyRes> checkLoginStatus(@Field("id") String id, @Field("root") String root);
    }


    public interface APIServicegetInqByDate {

        @POST("lead/filterleadsapi/")
        @FormUrlEncoded
        Call<Example> checkInqByDate(@Field("id") String id, @Field("case") String caseOf, @Field("filter") String filter);
    }

    public interface APIServicegetChainInq {
        @POST("lead/leadchainapi/")
        @FormUrlEncoded
        Call<ChainInquiry> checkInqByDate(@Field("id") String id);

    }

    public interface APIServicegetAttendanceSummary {
        @POST("attendance/attendancesummaryapi/")
        @FormUrlEncoded
        Call<AttendanceSummary> getAttendanceSummary(@Field("id") String id, @Field("month") String month, @Field("root") String root);

    }

    public interface APIServicegetAttendanceDetail {
        @POST("attendance/viewattendanceapi/")
        @FormUrlEncoded
        Call<AttendanceDetail> getAttendanceSummary(@Field("id") String id, @Field("month") String month, @Field("root") String root);

    }


    public interface APIServicegetAttendanceHistory {
        @POST("attendance/attendancehistoryapi/")
        @FormUrlEncoded
        Call<AttendanceHistory22> getAttendanceSummary(@Field("id") String id, @Field("date") String date, @Field("root") String root);

    }


    public interface APIServiceGetDistanceMap {
        @GET("maps/api/distancematrix/json?")
        Call<MapData> getMapDate(@Query("origins") String origin, @Query("destinations") String destination, @Query("mode") String mode);


    }

    public interface UploadImageToServer {
        @Multipart
        @POST("lead/uploaddocumentapi/")
        Call<MyRes> uploadFile(@Part MultipartBody.Part file, @Part("file") RequestBody name, @Part("id") RequestBody id, @Part("type") RequestBody type, @Part("cu_id") RequestBody cu_id);
    }

    public interface getDocumentInfo {
        @POST("lead/viewdocumentapi/")
        @FormUrlEncoded
        Call<DocumentList> getDocument(@Field("cu_id") String id, @Field("type") String type);

    }

    public interface getCounterInfo {
        @POST("lead/countleadapi/")
        @FormUrlEncoded
        Call<Counter> getCounter(@Field("id") String id, @Field("role") String role, @Field("branch") String branch);

    }

    public interface setChecklist {
        @POST("lead/addchecklistapi/")
        @FormUrlEncoded
        Call<MyRes> getCounter(@Field("id") String id, @Field("l_id") String lid, @Field("remark") String remark);

    }

    public interface getCheckListAll {
        @POST("lead/checklistapi/")
        @FormUrlEncoded
        Call<CheckListAll> getAllChecklist(@Field("id") String id);

    }


    public interface Confirmchecklistapi {
        @POST("lead/confirmchecklistapi/")
        Call<MyRes> confirmCheckList(@Body ConfirmCheckList confirmCheckList);

    }

    public interface MakeMyTrip {
        @POST("home/traveltranscationapi/")
        @FormUrlEncoded
        Call<MyRes> makeTrip(@Field("id") String id, @Field("master_id") String master_id,
                             @Field("src_lat") String src_lat, @Field("src_log") String src_log,
                             @Field("dest_lat") String dest_lat, @Field("dest_log") String dest_log,
                             @Field("dist") String dist


        );

    }

    public interface StartTrip {
        @POST("home/starttravelapi/")
        @FormUrlEncoded
        Call<MyRes> startTrip(@Field("id") String id, @Field("src_lat") String src_lat, @Field("src_log") String src_log,
                              @Field("dest_lat") String dest_lat, @Field("dest_log") String dest_log, @Field("total") String total
        );
    }


    public interface SearchByTSBPAN {
        @POST("lead/searchbytsbpanapi/")
        @FormUrlEncoded
        Call<ClientDetail> tsbPanvala(@Field("id") String id, @Field("case") String caseOff, @Field("val") String val);

    }

    public interface GetClientList {
        @POST("customer/customerlistapi/")
        @FormUrlEncoded
        Call<ClientDetail> getclientlist(@Field("id") String id, @Field("type") String type,
                                         @Field("value") String value
        );

    }

    public interface RequestExpense {
        @POST("expense/requestexpenseapi/")
        @FormUrlEncoded
        Call<MyRes> requestexpense(@Field("id") String id, @Field("amount") String amount, @Field("type") String type
                , @Field("desc") String desc, @Field("date") String date, @Field("name") String name
        );

    }

    public interface EventList {
        @POST("event/eventlistapi/")
        Call<com.crmmarketing.hmt.gsonmodel22.EventList> getEventList();

    }


    public interface EventDetailList {
        @POST("event/eventcustomerapi/")
        @FormUrlEncoded
        Call<EventDetail> getEventDetailCustomeList(@Field("id") String id, @Field("event_id") String event_id);

    }

    public interface TakeAttd {
        @POST("event/addattendanceapi/")
        Call<MyRes> putattd(@Body Present present);

    }

    public interface TravelReport {
        @POST("home/travelhistoryapi/")
        @FormUrlEncoded
        Call<com.crmmarketing.hmt.gsonmodel22.TravelReport> gettravelreport(@Field("id") String id, @Field("month") String month);
    }

    public interface EmpList {
        @POST("user/userlistapi/")
        @FormUrlEncoded
        Call<UserDetail> getemplist(@Field("id") String id, @Field("root") String root);
    }

    public interface RequestLeave {
        @POST("attendance/addleaveapi/")
        @FormUrlEncoded
        Call<MyRes> requestleave(@Field("id") String id, @Field("role") String role, @Field("start") String start,
                                 @Field("end") String end, @Field("reason") String reason,
                                 @Field("type") String type
        );

    }

    public interface ViewLeave {
        @POST("attendance/leavelistapi/")
        @FormUrlEncoded
        Call<LeaveHistoryList> getLeavelist(@Field("id") String id, @Field("month") String month, @Field("role") String role);
    }

    public interface CancelLeave {
        @POST("attendance/leavecancelapi/")
        @FormUrlEncoded
        Call<MyRes> cancelleave(@Field("id") String id);

    }

    public interface EventReport {
        @POST("event/eventreportapi/")
        Call<com.crmmarketing.hmt.gsonmodel22.EventList> getEventReport();
    }

    public interface InvitedCustomer {
        @POST("event/invitedcustomerapi/")
        @FormUrlEncoded
        Call<com.crmmarketing.hmt.gsonmodel22.InvitedCustomer> getInvitedCustomer(@Field("id") String id, @Field("event_id") String event_id);
    }

    public interface Volumedata {
        @POST("upload/uploadlistapi/")
        @FormUrlEncoded
        Call<VolumeData> getVolumeData(@Field("id") String id, @Field("role") String role, @Field("type") String type
                , @Field("date") String date
        );

    }


    public interface ViewApproveListLeave {
        @POST("attendance/approveleavelistapi/")
        @FormUrlEncoded
        Call<ApprLeave> getLeavelist(@Field("id") String id, @Field("role") String role);
    }

    public interface ApproveLeave {
        @POST("attendance/leaveapproveapi/")
        @FormUrlEncoded
        Call<MyRes> setApproveLeave(@Field("id") String id, @Field("role") String role);


    }

    public interface EditLeave {

        @POST("attendance/leaveupdateapi/")
        @FormUrlEncoded
        Call<MyRes> editLeave(@Field("id") String id, @Field("start") String start
                , @Field("end") String end, @Field("remark") String remark,
                              @Field("type") String type
        );

    }

    public interface FetchInvitationList {

        @POST("event/requestinviteapi/")
        @FormUrlEncoded
        Call<InvitedList> getInvitation(@Field("id") String id, @Field("role") String role
                , @Field("event_id") String event_id
        );


    }

    public interface InviteCustomer {
        @POST("event/approverequestinviteapi/")
        Call<MyRes> sendInvitation(@Body SendInvitation present);

    }

    public interface DeleteDocument {
        @POST("document/canceldocumentapi/")
        @FormUrlEncoded
        Call<MyRes> deleteDoc(@Field("id") String id, @Field("name") String name);
    }

    public interface ApproveByOperation {
        @POST("lead/approveleadapi/")
        @FormUrlEncoded
        Call<MyRes> approvebyop(@Field("inq_id") String id);
    }

    public interface GetECSList {
        @POST("lead/ecslistapi/")
        @FormUrlEncoded
        Call<EcsList> getecslist(@Field("id") String id, @Field("role") String role, @Field("branch") String branch);


    }

    public interface ECSAction {
        @POST("lead/ecsapprovalapi/")
        @FormUrlEncoded
        Call<MyRes> setEcsAction(@Field("l_id") String l_id, @Field("inq_id") String inq_id, @Field("remark") String remark, @Field("status") String status);


    }

   public  interface  CheckFollowUPStatus{
        @POST("upload/checkstatus_api/")
        @FormUrlEncoded
        Call<MyRes> checkfollowUpStatus(@Field("id") String id,@Field("type") String type);

    }

    public interface  SubmitFollowUp{

        @POST("upload/updateremarkapi")
        @FormUrlEncoded
        Call<MyRes> submitFollow(@Field("id") String id,
                                 @Field("type") String type,
                                 @Field("remark") String remark ,
                                 @Field("days") String day,
                                 @Field("status") String status,
                                 @Field("cnd") String cnd);



    }
}