package com.crmmarketing.hmt.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.crmmarketing.hmt.model.CusInfo;
import com.crmmarketing.hmt.model.InqChild;
import com.crmmarketing.hmt.model.InqMaster;
import com.crmmarketing.hmt.model.InquiryMaster;
import com.crmmarketing.hmt.model.InquiryTransaction;
import com.crmmarketing.hmt.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class PostsDatabaseHelper extends SQLiteOpenHelper {
    private static PostsDatabaseHelper sInstance;
    private String TAG = "DatabaseHelper";

    // Database Info
    private static final String DATABASE_NAME = "postsDatabase";
    private static final int DATABASE_VERSION = 2;

    // Table Names
    private static final String TABLE_USERS = "users";
    private static final String TABLE_INQUIRY_MASTER = "inquiry_master";
    private static final String TABLE_INQUIRY_TRANSACTION = "inquiry_transaction";


    // User Table Columns
    private static final String KEY_USER_ID = "id";
    private static final String KEY_USER_NAME = "user_name";
    private static final String KEY_USER_SOURCE_DETAIL = "source_name";
    private static final String KEY_USER_REFEREE_NAME = "referee_name";
    private static final String KEY_USER_REFEREE_CODE = "referee_code";
    private static final String KEY_USER_REFEREE_EMAIL = "referee_email";
    private static final String KEY_USER_REFEREE_MOBILE = "referee_mobile";
    private static final String KEY_USER_EMAIL = "user_email";
    private static final String KEY_USER_ALTERNATE_EMAIL = "user_optional_email";
    private static final String KEY_USER_MOBILE_NO = "user_mobile_no";
    private static final String KEY_USER_LANDLINE_NO = "user_landline_no";
    private static final String KEY_USER_ADDRESS = "user_addr";
    private static final String KEY_USER_COMMENT = "user_comment";
    private static final String KEY_USER_BIRTH = "user_birth";
    private static final String KEY_USER_OCCUPATION = "user_occupation";
    private static final String KEY_USER_RANGE = "user_range";


    //Inquiry table Columns

    private static final String KEY_INQUIRY_MASTER_ID = "id";
    private static final String KEY_INQUIRY_MASTER_TIMESTAMP = "inquiry_time";
    private static final String KEY_INQUIRY_MASTER_USER_ID_FK = "user_id";
    private static final String KEY_INQUIRY_MASTER_CUSTOMER_ID_FK = "customer_id";
    private static final String KEY_INQUIRY_MASTER_INVEST_RANGE = "invest_range";
    private static final String KEY_INQUIRY_MASTER_FEEDBACK_TIME = "feedback_time";
    private static final String KEY_INQUIRY_MASTER_STATUS = "status";
    private static final String KEY_INQUIRY_MASTER_STATUS_CHANGE_TIMESTAMP = "status_change_time";
    private static final String KEY_INQUIRY_MASTER_SYNC_STATUS = "sync_status_master";


    //Inquiry transaction Columns

    private static final String KEY_INQUIRY_TRANSACTION_ID = "id";
    private static final String KEY_INQUIRY_TRANSACTION_TIMESTAMP = "transaction_time";
    private static final String KEY_INQUIRY_TRANSACTION_STATUS_CHANGE_TIMESTAMP = "status_change_time";
    private static final String KEY_INQUIRY_TRANSACTION_INQ_MASTER_ID_FK = "inq_master_id";
    private static final String KEY_INQUIRY_TRANSACTION_PRODUCT_ID = "product_id";
    private static final String KEY_INQUIRY_TRANSACTION_QUANTITY = "quantity";
    private static final String KEY_INQUIRY_TRANSACTION_STATUS = "status";
    private static final String KEY_INQUIRY_TRANSACTION_SYNC_STATUS = "sync_status_child";


    private HashMap<String, List<InqMaster>> hashMap = new HashMap<>();

    public static synchronized PostsDatabaseHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new PostsDatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    /**
     * Constructor should be private to prevent direct instantiation.
     * Make a call to the static method "getInstance()" instead.
     */
    private PostsDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Called when the database connection is being configured.
    // Configure database settings for things like foreign key support, write-ahead logging, etc.
    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    // Called when the database is created for the FIRST time.
    // If a database already exists on disk with the same DATABASE_NAME, this method will NOT be called.
    @Override
    public void onCreate(SQLiteDatabase db) {


        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS +
                "(" +
                KEY_USER_ID + " INTEGER PRIMARY KEY," +
                KEY_USER_NAME + " TEXT," +
                KEY_USER_OCCUPATION + " TEXT, " +
                KEY_USER_SOURCE_DETAIL + " TEXT, " +
                KEY_USER_REFEREE_NAME + " TEXT, " +
                KEY_USER_REFEREE_CODE + " TEXT, " +
                KEY_USER_REFEREE_EMAIL + " TEXT, " +
                KEY_USER_REFEREE_MOBILE + " TEXT, " +
                KEY_USER_EMAIL + " TEXT, " +
                KEY_USER_ALTERNATE_EMAIL + " TEXT, " +
                KEY_USER_MOBILE_NO + " TEXT, " +
                KEY_USER_LANDLINE_NO + " TEXT, " +
                KEY_USER_ADDRESS + " TEXT, " +
                KEY_USER_RANGE + " TEXT, " +
                KEY_USER_COMMENT + " TEXT, " +
                KEY_USER_BIRTH + " TEXT" +
                ")";


        String CREATE_INQUIRY_MASTER_TABLE = "CREATE TABLE " + TABLE_INQUIRY_MASTER +
                "(" +
                KEY_INQUIRY_MASTER_ID + " INTEGER PRIMARY KEY," +
                KEY_INQUIRY_MASTER_TIMESTAMP + " TEXT," +
                KEY_INQUIRY_MASTER_STATUS_CHANGE_TIMESTAMP + " TEXT," +
                KEY_INQUIRY_MASTER_USER_ID_FK + " TEXT," +
                KEY_INQUIRY_MASTER_CUSTOMER_ID_FK + " INTEGER," +
                KEY_INQUIRY_MASTER_INVEST_RANGE + " TEXT, " +
                KEY_INQUIRY_MASTER_FEEDBACK_TIME + " TEXT, " +
                KEY_INQUIRY_MASTER_STATUS + " INTEGER, " +
                KEY_INQUIRY_MASTER_SYNC_STATUS + " INTEGER, " +
                "FOREIGN KEY(" + KEY_INQUIRY_MASTER_CUSTOMER_ID_FK + ") REFERENCES "
                + TABLE_USERS + "(" + KEY_USER_ID + ") " + ")";


        String CREATE_INQUIRY_TRANSACTION_TABLE = "CREATE TABLE " + TABLE_INQUIRY_TRANSACTION +
                "(" +
                KEY_INQUIRY_TRANSACTION_ID + " INTEGER PRIMARY KEY," +
                KEY_INQUIRY_TRANSACTION_TIMESTAMP + " TEXT," +
                KEY_INQUIRY_TRANSACTION_STATUS_CHANGE_TIMESTAMP + " TEXT," +
                KEY_INQUIRY_TRANSACTION_INQ_MASTER_ID_FK + " INTEGER," +
                KEY_INQUIRY_TRANSACTION_QUANTITY + " INTEGER," +
                KEY_INQUIRY_TRANSACTION_PRODUCT_ID + " TEXT, " +
                KEY_INQUIRY_TRANSACTION_STATUS + " INTEGER, " +
                KEY_INQUIRY_TRANSACTION_SYNC_STATUS + " INTEGER, " +
                "FOREIGN KEY(" + KEY_INQUIRY_TRANSACTION_INQ_MASTER_ID_FK + ") REFERENCES "
                + TABLE_INQUIRY_MASTER + "(" + KEY_INQUIRY_MASTER_ID + ") " + ")";


        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_INQUIRY_MASTER_TABLE);
        db.execSQL(CREATE_INQUIRY_TRANSACTION_TABLE);
    }

    // Called when the database needs to be upgraded.
    // This method will only be called if a database already exists on disk with the same DATABASE_NAME,
    // but the DATABASE_VERSION is different than the version of the database that exists on disk.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_INQUIRY_TRANSACTION);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_INQUIRY_MASTER);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
            onCreate(db);
        }
    }


    // Insert or update a user in the database
    // Since SQLite doesn't support "upsert" we need to fall back on an attempt to UPDATE (in case the
    // user already exists) optionally followed by an INSERT (in case the user does not already exist).
    // Unfortunately, there is a bug with the insertOnConflict method
    // (https://code.google.com/p/android/issues/detail?id=]\) so we need to fall back to the more
    // verbose option of querying for the user's primary key if we did an update.
    public long addOrUpdateUser(User user) {
        // The database connection is cached so it's not expensive to call getWriteableDatabase() multiple times.
        SQLiteDatabase db = getWritableDatabase();
        long userId = -1;

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_USER_NAME, user.getUserName());
            values.put(KEY_USER_EMAIL, user.getUserEmail());
            values.put(KEY_USER_MOBILE_NO, user.getUserMobileNo());
            values.put(KEY_USER_ADDRESS, user.getUserAddr());
            values.put(KEY_USER_LANDLINE_NO, user.getUserLandlineNo());
            values.put(KEY_USER_REFEREE_CODE, user.getRefereeCode());
            values.put(KEY_USER_REFEREE_EMAIL, user.getRefereeEmail());
            values.put(KEY_USER_REFEREE_MOBILE, user.getRefereeMobile());
            values.put(KEY_USER_REFEREE_NAME, user.getRefereeName());
            values.put(KEY_USER_SOURCE_DETAIL, user.getSourceName());
            values.put(KEY_USER_COMMENT, user.getUserComment());
            values.put(KEY_USER_BIRTH, user.getUserBirth());
            values.put(KEY_USER_RANGE, user.getUserRange());
            values.put(KEY_USER_OCCUPATION, user.getOccupation());


            // First try to update the user in case the user already exists in the database
            // This assumes userNames are unique
            int rows = db.update(TABLE_USERS, values, KEY_USER_EMAIL + "= ? AND " + KEY_USER_NAME + "= ? OR " + KEY_USER_MOBILE_NO + "= ?", new String[]{user.getUserEmail(), user.getUserName(), user.getUserMobileNo()});


            // Check if update succeeded
            if (rows == 1) {
                // Get the primary key of the user we just updated
                String usersSelectQuery = String.format("SELECT %s FROM %s WHERE %s = ?",
                        KEY_USER_ID, TABLE_USERS, KEY_USER_EMAIL);
                Cursor cursor = db.rawQuery(usersSelectQuery, new String[]{String.valueOf(user.getUserEmail())});
                try {
                    if (cursor.moveToFirst()) {
                        userId = cursor.getInt(0);
                        db.setTransactionSuccessful();
                    }
                } finally {
                    if (cursor != null && !cursor.isClosed()) {
                        cursor.close();
                    }
                }
            } else {
                // user with this userName did not already exist, so insert new user
                userId = db.insertOrThrow(TABLE_USERS, null, values);
                db.setTransactionSuccessful();
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add or update user");
        } finally {
            db.endTransaction();
        }
        return userId;
    }


    public long addOrUpdateInquiryMaster(InquiryMaster inquiryMaster) {
        // The database connection is cached so it's not expensive to call getWriteableDatabase() multiple times.
        SQLiteDatabase db = getWritableDatabase();
        long userId = -1;

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_INQUIRY_MASTER_USER_ID_FK, inquiryMaster.getKEY_INQUIRY_MASTER_USER_ID_FK());
            values.put(KEY_INQUIRY_MASTER_TIMESTAMP, inquiryMaster.getKEY_INQUIRY_MASTER_TIMESTAMP());
            values.put(KEY_INQUIRY_MASTER_INVEST_RANGE, inquiryMaster.getKEY_INQUIRY_MASTER_INVEST_RANGE());
            values.put(KEY_INQUIRY_MASTER_CUSTOMER_ID_FK, inquiryMaster.getKEY_INQUIRY_MASTER_CUSTOMER_ID_FK());
            values.put(KEY_INQUIRY_MASTER_STATUS, inquiryMaster.getKEY_INQUIRY_MASTER_STATUS());
            values.put(KEY_INQUIRY_MASTER_FEEDBACK_TIME, inquiryMaster.getKEY_INQUIRY_MASTER_FEEDBACK_TIME());


            int rows = db.update(TABLE_INQUIRY_MASTER, values, KEY_INQUIRY_MASTER_ID + "= ? ", new String[]{inquiryMaster.getKEY_INQUIRY_MASTER_ID()});


            // Check if update succeeded
            if (rows == 1) {
                // Get the primary key of the user we just updated
                String usersSelectQuery = String.format("SELECT %s FROM %s WHERE %s = ?",
                        KEY_INQUIRY_MASTER_ID, TABLE_INQUIRY_MASTER, KEY_INQUIRY_MASTER_ID);
                Cursor cursor = db.rawQuery(usersSelectQuery, new String[]{String.valueOf(inquiryMaster.getKEY_INQUIRY_MASTER_ID())});
                try {
                    if (cursor.moveToFirst()) {
                        userId = cursor.getInt(0);
                        db.setTransactionSuccessful();
                    }
                } finally {
                    if (cursor != null && !cursor.isClosed()) {
                        cursor.close();
                    }
                }
            } else {
                // user with this userName did not already exist, so insert new user
                userId = db.insertOrThrow(TABLE_INQUIRY_MASTER, null, values);
                db.setTransactionSuccessful();
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add or update user");
        } finally {
            db.endTransaction();

        }
        return userId;
    }


    public long addOrUpdateInquiryTransaction(InquiryTransaction inquiryTransaction) {
        // The database connection is cached so it's not expensive to call getWriteableDatabase() multiple times.
        SQLiteDatabase db = getWritableDatabase();
        long userId = -1;

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_INQUIRY_TRANSACTION_INQ_MASTER_ID_FK, inquiryTransaction.getKEY_INQUIRY_TRANSACTION_INQ_MASTER_ID_FK());
            values.put(KEY_INQUIRY_TRANSACTION_TIMESTAMP, inquiryTransaction.getKEY_INQUIRY_TRANSACTION_TIMESTAMP());
            values.put(KEY_INQUIRY_TRANSACTION_PRODUCT_ID, inquiryTransaction.getKEY_INQUIRY_TRANSACTION_PRODUCT_ID());
            values.put(KEY_INQUIRY_TRANSACTION_QUANTITY, inquiryTransaction.getKEY_INQUIRY_TRANSACTION_QUANTITY());
            values.put(KEY_INQUIRY_TRANSACTION_STATUS, inquiryTransaction.getKEY_INQUIRY_TRANSACTION_STATUS());

            int rows = db.update(TABLE_INQUIRY_TRANSACTION, values, KEY_INQUIRY_TRANSACTION_TIMESTAMP + "= ? ", new String[]{inquiryTransaction.getKEY_INQUIRY_TRANSACTION_TIMESTAMP()});


            // Check if update succeeded
            if (rows == 1) {
                // Get the primary key of the user we just updated
                String usersSelectQuery = String.format("SELECT %s FROM %s WHERE %s = ?",
                        KEY_INQUIRY_TRANSACTION_ID, TABLE_INQUIRY_TRANSACTION, KEY_INQUIRY_TRANSACTION_PRODUCT_ID);
                Cursor cursor = db.rawQuery(usersSelectQuery, new String[]{String.valueOf(inquiryTransaction.getKEY_INQUIRY_TRANSACTION_ID())});
                try {
                    if (cursor.moveToFirst()) {
                        userId = cursor.getInt(0);
                        db.setTransactionSuccessful();
                    }
                } finally {
                    if (cursor != null && !cursor.isClosed()) {
                        cursor.close();
                    }
                }
            } else {
                // user with this userName did not already exist, so insert new user
                userId = db.insertOrThrow(TABLE_INQUIRY_TRANSACTION, null, values);
                db.setTransactionSuccessful();
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add or update user");
        } finally {
            db.endTransaction();

        }
        return userId;
    }


    public ArrayList<InqMaster> getAllPosts() {
        List<InqMaster> posts = new ArrayList<>();


        ArrayList<InqMaster> inqMasterArrayList = null;
        ArrayList<InqChild> inqChildArrayList = new ArrayList<>();
        String last = null;
        String temp = null;


        // SELECT * FROM POSTS
        // LEFT OUTER JOIN USERS
        // ON POSTS.KEY_POST_USER_ID_FK = USERS.KEY_USER_ID
        String POSTS_SELECT_QUERY =
                String.format("SELECT  * FROM %s LEFT OUTER JOIN %s ON %s.%s = %s.%s LEFT OUTER JOIN %s ON %s.%s = %s.%s",
                        TABLE_INQUIRY_MASTER,
                        TABLE_USERS,
                        TABLE_INQUIRY_MASTER, KEY_INQUIRY_MASTER_CUSTOMER_ID_FK,
                        TABLE_USERS, KEY_USER_ID,
                        TABLE_INQUIRY_TRANSACTION,
                        TABLE_INQUIRY_MASTER, KEY_INQUIRY_MASTER_ID,
                        TABLE_INQUIRY_TRANSACTION, KEY_INQUIRY_TRANSACTION_INQ_MASTER_ID_FK

                );

        String POSTS_SELECT_MASTER =
                String.format("SELECT * FROM %s ",
                        TABLE_INQUIRY_MASTER
                );


        String POSTS_SELECT_USER =
                String.format("SELECT * FROM %s ",
                        TABLE_USERS
                );

        // "getReadableDatabase()" and "getWriteableDatabase()" return the same object (except under low
        // disk space scenarios)
        SQLiteDatabase db = getReadableDatabase();
        HashMap<String, InqMaster> productMap = new HashMap<>();
        Cursor cursor = db.rawQuery(POSTS_SELECT_QUERY, null);

        try {
            if (cursor.moveToFirst()) {
                do {


                    Log.e("colomn count", String.valueOf(cursor.getColumnCount()));

                    InqChild inqChild = new InqChild();
                    inqChild.setInqChildId(cursor.getString(cursor.getColumnIndex(KEY_INQUIRY_TRANSACTION_ID)));
                    inqChild.setInqChildLastUpdate(cursor.getString(cursor.getColumnIndex(KEY_INQUIRY_TRANSACTION_TIMESTAMP)));
                    inqChild.setInqChildStatusUpdate(cursor.getString(cursor.getColumnIndex(KEY_INQUIRY_TRANSACTION_STATUS_CHANGE_TIMESTAMP)));
                    inqChild.setInqChildProductId(cursor.getString(cursor.getColumnIndex(KEY_INQUIRY_TRANSACTION_PRODUCT_ID)));
                    inqChild.setInqChildQty(cursor.getString(cursor.getColumnIndex(KEY_INQUIRY_TRANSACTION_QUANTITY)));
                    inqChild.setInqChildStatus(cursor.getString(cursor.getColumnIndex(KEY_INQUIRY_TRANSACTION_STATUS)));
                    inqChild.setInqMasterId(cursor.getString(cursor.getColumnIndex(KEY_INQUIRY_TRANSACTION_INQ_MASTER_ID_FK)));
                    inqChild.setSyncStatus(cursor.getString(cursor.getColumnIndex(KEY_INQUIRY_TRANSACTION_SYNC_STATUS)));
                    inqChildArrayList.add(inqChild);


                    if (inqMasterArrayList == null) {

                        inqMasterArrayList = new ArrayList<>();


                        final CusInfo cusInfo = new CusInfo();
                        cusInfo.setCusId(cursor.getString(cursor.getColumnIndex(KEY_USER_ID)));
                        cusInfo.setCusEmail(cursor.getString(cursor.getColumnIndex(KEY_USER_EMAIL)));
                        cusInfo.setCusName(cursor.getString(cursor.getColumnIndex(KEY_USER_NAME)));
                        cusInfo.setCusMobile(cursor.getString(cursor.getColumnIndex(KEY_USER_MOBILE_NO)));
                        cusInfo.setCusDob(cursor.getString(cursor.getColumnIndex(KEY_USER_BIRTH)));
                        cusInfo.setCusLand(cursor.getString(cursor.getColumnIndex(KEY_USER_LANDLINE_NO)));
                        cusInfo.setCusOptEmail(cursor.getString(cursor.getColumnIndex(KEY_USER_ALTERNATE_EMAIL)));
                        cusInfo.setCusAddr(cursor.getString(cursor.getColumnIndex(KEY_USER_ADDRESS)));
                        cusInfo.setCusOccupation(cursor.getString(cursor.getColumnIndex(KEY_USER_OCCUPATION)));
                        cusInfo.setCusRefCode(cursor.getString(cursor.getColumnIndex(KEY_USER_REFEREE_CODE)));
                        cusInfo.setCusRefEmail(cursor.getString(cursor.getColumnIndex(KEY_USER_REFEREE_EMAIL)));
                        cusInfo.setCusRefMobile(cursor.getString(cursor.getColumnIndex(KEY_USER_REFEREE_MOBILE)));
                        cusInfo.setCusRefName(cursor.getString(cursor.getColumnIndex(KEY_USER_REFEREE_NAME)));
                        cusInfo.setCusRefOther(cursor.getString(cursor.getColumnIndex(KEY_USER_COMMENT)));


                        final InqMaster inqMaster = new InqMaster();
                        inqMaster.setCusInfo(cusInfo);
                        inqMaster.setInqEmpId(cursor.getString(cursor.getColumnIndex(KEY_INQUIRY_MASTER_USER_ID_FK)));
                        inqMaster.setInqMasterId(cursor.getString(cursor.getColumnIndex(KEY_INQUIRY_TRANSACTION_INQ_MASTER_ID_FK)));
                        inqMaster.setInqFeedbackTime(cursor.getString(cursor.getColumnIndex((KEY_INQUIRY_MASTER_FEEDBACK_TIME))));
                        inqMaster.setInqInvestRange(cursor.getString(cursor.getColumnIndex((KEY_INQUIRY_MASTER_INVEST_RANGE))));
                        inqMaster.setInqMasterLastUpdate(cursor.getString(cursor.getColumnIndex((KEY_INQUIRY_MASTER_TIMESTAMP))));
                        inqMaster.setInqMasterStatusUpdate(cursor.getString(cursor.getColumnIndex((KEY_INQUIRY_MASTER_STATUS_CHANGE_TIMESTAMP))));
                        inqMaster.setInqMasterStatus(cursor.getString(cursor.getColumnIndex((KEY_INQUIRY_MASTER_STATUS))));
                        inqMaster.setSyncStatus(cursor.getString(cursor.getColumnIndex(KEY_INQUIRY_MASTER_SYNC_STATUS)));
                        inqMasterArrayList.add(inqMaster);


                    } else {


                        if (cursor.moveToPrevious()) {

                            last = String.valueOf(cursor.getString(cursor.getColumnIndex(KEY_INQUIRY_TRANSACTION_INQ_MASTER_ID_FK)));
                            cursor.moveToNext();


                        }


                        if (!last.equals(cursor.getString(cursor.getColumnIndex(KEY_INQUIRY_TRANSACTION_INQ_MASTER_ID_FK)))) {

                            final CusInfo cusInfo = new CusInfo();
                            cusInfo.setCusId(cursor.getString(cursor.getColumnIndex(KEY_INQUIRY_MASTER_CUSTOMER_ID_FK)));
                            cusInfo.setCusEmail(cursor.getString(cursor.getColumnIndex(KEY_USER_EMAIL)));
                            cusInfo.setCusName(cursor.getString(cursor.getColumnIndex(KEY_USER_NAME)));
                            cusInfo.setCusMobile(cursor.getString(cursor.getColumnIndex(KEY_USER_MOBILE_NO)));
                            cusInfo.setCusDob(cursor.getString(cursor.getColumnIndex(KEY_USER_BIRTH)));
                            cusInfo.setCusLand(cursor.getString(cursor.getColumnIndex(KEY_USER_LANDLINE_NO)));
                            cusInfo.setCusOptEmail(cursor.getString(cursor.getColumnIndex(KEY_USER_ALTERNATE_EMAIL)));
                            cusInfo.setCusAddr(cursor.getString(cursor.getColumnIndex(KEY_USER_ADDRESS)));
                            cusInfo.setCusOccupation(cursor.getString(cursor.getColumnIndex(KEY_USER_OCCUPATION)));
                            cusInfo.setCusRefCode(cursor.getString(cursor.getColumnIndex(KEY_USER_REFEREE_CODE)));
                            cusInfo.setCusRefEmail(cursor.getString(cursor.getColumnIndex(KEY_USER_REFEREE_EMAIL)));
                            cusInfo.setCusRefMobile(cursor.getString(cursor.getColumnIndex(KEY_USER_REFEREE_MOBILE)));
                            cusInfo.setCusRefName(cursor.getString(cursor.getColumnIndex(KEY_USER_REFEREE_NAME)));
                            cusInfo.setCusRefOther(cursor.getString(cursor.getColumnIndex(KEY_USER_COMMENT)));

                            final InqMaster inqMaster = new InqMaster();
                            inqMaster.setCusInfo(cusInfo);
                            inqMaster.setInqEmpId(cursor.getString(cursor.getColumnIndex(KEY_INQUIRY_MASTER_USER_ID_FK)));
                            inqMaster.setInqMasterId(cursor.getString(cursor.getColumnIndex(KEY_INQUIRY_TRANSACTION_INQ_MASTER_ID_FK)));
                            inqMaster.setInqFeedbackTime(cursor.getString(cursor.getColumnIndex((KEY_INQUIRY_MASTER_FEEDBACK_TIME))));
                            inqMaster.setInqInvestRange(cursor.getString(cursor.getColumnIndex((KEY_INQUIRY_MASTER_INVEST_RANGE))));
                            inqMaster.setInqMasterLastUpdate(cursor.getString(cursor.getColumnIndex((KEY_INQUIRY_MASTER_TIMESTAMP))));
                            inqMaster.setInqMasterStatusUpdate(cursor.getString(cursor.getColumnIndex((KEY_INQUIRY_MASTER_STATUS_CHANGE_TIMESTAMP))));
                            inqMaster.setInqMasterStatus(cursor.getString(cursor.getColumnIndex((KEY_INQUIRY_MASTER_STATUS))));
                            inqMaster.setSyncStatus(cursor.getString(cursor.getColumnIndex(KEY_INQUIRY_MASTER_SYNC_STATUS)));
                            inqMasterArrayList.add(inqMaster);


                        } else {

                        }

                    }


                    Log.e("child foreign id", "" + cursor.getString(cursor.getColumnIndex(KEY_INQUIRY_TRANSACTION_INQ_MASTER_ID_FK)));


                } while (cursor.moveToNext());


                for (int i = 0; i < inqMasterArrayList.size(); i++) {

                    ArrayList<InqChild> arrayList = new ArrayList<>();
                    for (int j = 0; j < inqChildArrayList.size(); j++) {


                        if (inqMasterArrayList.get(i).getInqMasterId().equals(inqChildArrayList.get(j).getInqMasterId())) {

                            arrayList.add(inqChildArrayList.get(j));

                        }
                        inqMasterArrayList.get(i).setInqChild(arrayList);
                    }
                }

//                    Iterator<InqMaster> it1=inqMasterArrayList.iterator();
//
//                    while (it1.hasNext()){
//
//                        Iterator<InqChild> it2 = it1.next().getInqChild().iterator();
//                        while (it2.hasNext()) {
//                            if (!it2.next().getInqMasterId().equals(it2.next().getInqMasterId())) {
//
//                               it1.remove();
//
//                            }
//                        }
//                    }
//
//                for (InqMaster student : inqMasterArrayList) {
//                        String key  = student.getInqMasterLastUpdate();
//                        if(hashMap.containsKey(key)){
//                            posts = hashMap.get(key);
//                            posts.add(student);
//
//                        }else{
//                            posts = new ArrayList<InqMaster>();
//                            posts.add(student);
//                            hashMap.put(key, posts);
//                        }
//
//                    }


            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get posts from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return inqMasterArrayList;
    }


    public int updateStatus(String values, String masterId, String[] childId) {
        SQLiteDatabase db = getWritableDatabase();
        int userId = -1;

        db.beginTransaction();

        try {

            ContentValues contentValues = new ContentValues();
            contentValues.put(KEY_INQUIRY_MASTER_SYNC_STATUS, Integer.parseInt(values));
            userId = db.update(TABLE_INQUIRY_MASTER, contentValues, KEY_INQUIRY_MASTER_ID + "= ? ", new String[]{masterId});

            if (userId == 1) {


                for (int i = 0; i < childId.length; i++) {
                    ContentValues contentValues12 = new ContentValues();
                    contentValues12.put(KEY_INQUIRY_TRANSACTION_SYNC_STATUS, Integer.parseInt(values));

                    userId = db.update(TABLE_INQUIRY_TRANSACTION, contentValues12, KEY_INQUIRY_TRANSACTION_ID + "= ? ", new String[]{childId[i]});


                }

                if (userId == 1) {
                    db.setTransactionSuccessful();
                }

            }


        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get posts from database");
        } finally {
            db.endTransaction();
        }

        return userId;
    }


    public List<String> getCusName() {

        List<String> stringList = new ArrayList<>();


        SQLiteDatabase db = getReadableDatabase();

// Define a projection that specifies which columns from the database
// you will actually use after this query.
        String[] projection = {
                KEY_USER_NAME,

        };

// Filter results WHERE "title" = 'My Title'
        //  String selection = FeedEntry.COLUMN_NAME_TITLE + " = ?";
        //   String[] selectionArgs = { "My Title" };

// How you want the results sorted in the resulting Cursor
//        String sortOrder =
//                FeedEntry.COLUMN_NAME_SUBTITLE + " DESC";

        Cursor cursor = db.query(
                TABLE_USERS,                     // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );

        while (cursor.moveToNext()) {
            final String username = cursor.getString(cursor.getColumnIndex(KEY_USER_NAME));
            stringList.add(username);
        }
        cursor.close();

        return stringList;
    }
}

