package com.dclover.gpsutilities.taxi.Utils;

/**
 * Created by Kinghero on 6/6/2016.
 */
public class Constants {

    public static String MY_APP="gpsutility";
    public static String endpoint="http://tms.giamsatxeviet.vn:8080";
    public static final String CAR_TYPE = "CAR_TYPE";
    public static final int CAR_TYPE_4SEAT = 1;
    public static final int CAR_TYPE_7SEAT = 2;
    public static final int CAR_TYPE_AIRPORT = 3;
    public static final int CAR_TYPE_ANY = 0;
    public static final String CMD_CLIENT_CHANGE_CAR_TYPE = "$rider_update_car_type=";
    public static final String CMD_CLIENT_LOGIN = "$riderlogin=";
    public static final String CMD_CLIENT_RIDER_CANCEL_TRIP = "$rider_cancel=";
    public static final String CMD_CLIENT_RIDER_RATING = "$rider_rating=";
    public static final String CMD_CLIENT_RIDER_REQUEST_TRACKING = "$rider_request_tracking=";
    public static final String CMD_CLIENT_RIDER_REQUEST_TRIP = "$rider_request_trip=";
    public static final String CMD_CLIENT_RIDER_UPDATE_TRIP_INFO = "$update_trip_info=";
    public static final String CMD_CLIENT_UPDATE_LOCATION = "$rider_update_loc=";
    public static final String CMD_END = "$end";
    public static final String CMD_END_REGEX = "\\$end";
    public static final String CMD_SERVER_CANCEL_RETURN = "$rider_cancel_return=";
    public static final String CMD_SERVER_LOGIN_SUCCESS = "$login_success=";
    public static final String CMD_SERVER_NOTIFY_ARRIVED = "$driver_in_start_position=";
    public static final String CMD_SERVER_RATING_RESULT = "$rider_rating_result=";
    public static final String CMD_SERVER_REQUEST_TRIP_FAIL = "$requestTripFails=";
    public static final String CMD_SERVER_REQUEST_TRIP_RETURN = "$rider_request_trip_return=";
    public static final String CMD_SERVER_UPDATE_DRIVER_INFO = "$updateDriverInfo=";
    public static final String CMD_SERVER_UPDATE_TRIP = "$trip_update=";
    public static final String CMD_SERVER_UPDATE_TRIP_SUMMARY = "$update_driver_info=";
    public static final int DEFAULT_CAR_AMOUNT = 5;
    public static final int DEFAULT_CAR_DISTANCE = 500;
    public static final int FAILURE_RESULT = 0;
    public static final String GOOGLE_SERVICE_DIRECTION_JSON = "http://maps.googleapis.com/maps/api/directions/json";
    public static final String GOOGLE_SERVICE_DIRECTION_XML = "http://maps.googleapis.com/maps/api/directions/xml";
    public static final String HELP_PAGE = "HELP_PAGE";
    public static final String LOCATION_SEARCH = "LOCATION_SEARCH";
    public static final String PLACE_SELECTED_ADDR = "PLACE_SELECTED_ADDR";
    public static final String PLACE_SELECTED_DESC = "PLACE_SELECTED_DESC";
    public static final String PLACE_SELECTED_LATLNG = "PLACE_SELECTED_LATLNG";
    public static final String PLACE_SELECTED_NAME = "PLACE_SELECTED_NAME";
    public static final String PREFERENCE_FILE_KEY = "com.vietek.taximailinh.PREFERENCE_FILE_KEY";
    public static final String REQUEST_CODE = "REQUEST_CODE";
    public static final int REQUEST_PASSWORD_RESET_CODE = 3;
    public static final int REQUEST_PICK_IMAGE_CODE = 10;
    public static final int REQUEST_REGISTER_CODE = 2;
    public static final int REQUEST_SEARCH_PLACE_CODE = 1;
    public static final int REQUEST_VERIFY_CODE = 4;
    public static final int RESULT_CODE_FAIL = 200;
    public static final int RESULT_CODE_SUCCESS = 100;
    public static final String RESULT_DATA_KEY = "RESULT_DATA_KEY";
    public static final int SOCKET_CONNECT_TIMEOUT = 5000;
    public static final String SOCKET_SERVER_IP = "tms.giamsatxeviet.vn";
    public static final int SOCKET_SERVER_PORT = 6969;
    public static final int SOCKET_SO_TIMEOUT = 5000;
    public static final int SUCCESS_RESULT = 1;
    public static final String TAXI_RESULT = "TAXI_RESULT";
    public static final int TAXI_STATUS_AVAILABLE = 4;
    public static final Integer TYPE_APP_USER;
    public static final String USER_DEVICE_UUID = "USER_DEVICE_UUID";
    public static final String USER_EMAIL = "USER_EMAIL";
    public static final String USER_NAME = "USER_NAME";
    public static final String USER_PASSWORD = "USER_PASSWORD";
    public static final String USER_PHONE_NO = "USER_PHONE_NO";
    public static final String USER_REG_ID = "USER_REG_ID";
    public static final String VERIFY_CODE = "VERIFY_CODE";
    public static final String WS_BASE_URL = "http://tms.giamsatxeviet.vn:8080/TaxiOperation/rest/MobileWS/";
    public static final int WS_CONNECT_TIMEOUT = 10000;
    public static final long WS_DELAY = 5000;
    public static final String WS_GetTripHistory = "getHistory";
    public static final String WS_Get_DriverInfo = "get_driver_info";
    public static final String WS_List_CallCenter = "get_callcenter";
    public static final String WS_Log_Phone_Call = "phoneLog";
    public static final String WS_Login = "login";
    public static final String WS_PasswordChange = "changePassword";
    public static final String WS_PasswordReset = "resetPassword";
    public static final String WS_PostTaxiOrder = "postTaxiOrder";
    public static final String WS_PostTaxiPosition = "postTaxiPosition";
    public static final String WS_PostTaxiType = "postTaxiType";
    public static final String WS_Push_Feedback = "push_feedbackCustomer";
    public static final int WS_READ_TIMEOUT = 10000;
    public static final String WS_Register = "register";
    public static final String WS_UpdateAccountInfo = "updateAccountInfo";
    public static final String WS_UpdateRegId = "updateRegId";
    public static final String WS_Verify = "verify";

    static {
        TYPE_APP_USER = Integer.valueOf(SUCCESS_RESULT);
    }

}
