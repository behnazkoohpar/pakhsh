package com.koohpar.dstrbt.utils;

/**
 * Created by shb on 10/29/2017.
 */
public interface AppConstants {

    String BASE_URL = "http://192.168.1.172:3000";
//    String BASE_URL = "http://185.187.51.209:3000";
//    String BASE_URL = "http://185.112.34.184:3000";
    String PREF_NAME = "distribution_pref";
    String TIMESTAMP_FORMAT = "yyyyMMdd_HHmmss";

    boolean LOGTRUE = false;

    // API KEY STRINGS
    String REQUEST_KEY_PASSWORD = "user_password";
    String REQUEST_KEY_NEW_PASSWORD = "new_password";
    String REQUEST_KEY_USERNAME = "vUserName";
    String REQUEST_KEY_PHONE = "phone";
    String REQUEST_KEY_ACTIVATION_CODE = "activation_code";
    String REQUEST_KEY_NAME = "first_name";
    String REQUEST_KEY_FAMILY = "last_name";
    String REQUEST_KEY_MOBILE_NUMBER = "mobile_number";
    String REQUEST_KEY_BIRTH_DATE = "birth_date";
    String REQUEST_KEY_USER_ID = "user_id";
    String REQUEST_KEY_IMAGE = "image";
    String REQUEST_KEY_CITY = "city_id";
    String REQUEST_KEY_AREA = "zone_number";
    String REQUEST_KEY_STORE_NAME = "store_name";
    String REQUEST_KEY_STORE_TEL = "phone_number";
    String REQUEST_KEY_ADDRESS = "store_address";
    String REQUEST_KEY_LATITUDE = "latitude";
    String REQUEST_KEY_LONGITUDE = "longitude";
    String REQUEST_KEY_POSTAL_CODE = "postal_code";
    String REQUEST_KEY_EMAIL = "email";
    String REQUEST_KEY_TELEGRAM = "telegram";
    String REQUEST_KEY_INSTAGRAM = "instagram";
    String REQUEST_KEY_CATEGORY_ID = "category_id";
    String REQUEST_KEY_BRAND_ID = "brand_id";
    String REQUEST_KEY_SEARCH = "keyword";
    String REQUEST_KEY_SUM_PRICE = "sum_price";
    String REQUEST_KEY_STUFFS_ACCOUNT = "stuffs_count";
    String REQUEST_KEY_REQUEST_ITEM = "request_item";
    String REQUEST_KEY_REQUEST_ID = "request_id";
    String REQUEST_KEY_TOKEN = "token";
    String REQUEST_KEY_REQUEST_ITEM_ID = "item_id";
    String REQUEST_KEY_ITEM_COUNT = "item_count";
    String REQUEST_KEY_CUSTOMER_PRICE = "consumer_price";
    String REQUEST_KEY_PRICE = "price";
    String REQUEST_KEY_OFFER = "offer";
    String REQUEST_KEY_ITEM_SUM_PRICE = "item_sum_price";
    String REQUEST_KEY_CODE = "code";
    String REQUEST_KEY_STUFF_BRAND_ID = "stuff_brand_id";
    String REQUEST_KEY_CREATE_REQUEST = "create_request";

    // Headers
    String HEADER_SECURITY_KEY = "security_key";
    String HEADER_TYPE = "type";

    //Permission code
    int MY_PERMISSIONS_REQUEST_CAMERA = 101;
    int MY_PERMISSIONS_REQUEST_LOCATION = 102;
    // API CALL CODES
    int API_CALL_LOGIN = 1001;
    int API_CALL_SEND_FORGET_PASSWORD = 1002;
    int API_CALL_REGISTER = 1003;
    int API_CALL_SIGN_IN = 1004;
    int API_CALL_ACTIVATION = 1005;
    int API_CALL_PROFILE_USER = 1006;
    int API_CALL_EDIT_PROFILE_USER = 1007;
    int API_CALL_EDIT_PICTURE = 1008;
    int API_CALL_EDIT_LOCATION = 1009;
    int API_CALL_EDIT_INTERNET = 1010;
    int API_CALL_ALL_BANER_LIST = 1011;
    int API_CALL_ALL_CATEGORY = 1012;
    int API_CALL_ALL_SPECIAL_OFFER = 1013;
    int API_CALL_ALL_CATEGORY_STUFF = 1014;
    int API_CALL_ALL_BRAND = 1015;
    int API_CALL_ALL_CATEGORY_STUFF_BY_BRAND = 1016;
    int API_CALL_ALL_SEARCH = 1017;
    int API_CALL_SET_REQUEST = 1018;
    int API_CALL_GET_LAST_VERSION = 1019;
    int API_CALL_CHANGE_PASSWORD = 1020;
    int API_CALL_REPORT_LIST = 1021;
    int API_CALL_DETAIL_REQUEST = 1022;
    int API_CALL_DELETE_DETAIL_REQUEST = 1023;
    int API_CALL_DELETE_ITEM_DETAIL_REQUEST = 1024;
    int API_CALL_EDIT_ITEM_DETAIL_REQUEST = 1025;
    int API_CALL_CHECK_CODE = 1026;
    int API_CALL_SET_NOTIF = 1027;
    int API_CALL_GET_NOTIFY_INVENTORY_LIST = 1028;

    String REQ_KEY_FACTOR_ID = "factor_id";

    //notification
    String KEY_NOTIFICATION_MESSAGE = "notification_msg";
    String KEY_NOTIFICATION_TYPE = "eNotificationType";
    String PREF_KEY_TOKEN = "PREF_KEY_TOKEN";
}

