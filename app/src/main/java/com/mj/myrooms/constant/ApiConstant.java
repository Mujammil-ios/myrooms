package com.mj.myrooms.constant;

public class ApiConstant {
    public static final int STATUS_SUCCESS = 1;

    // response code
    public static final int STATUS_CODE_SUCCESS = 200;
    public static final int STATUS_CODE_SUCCESS1 = 201;
    public static final int STATUS_CODE_CONNECTION_ERROR = -101;
    public static final int STATUS_CODE_ETAG = 304;
    public static final int STATUS_CODE_FORBIDDEN = 403;

    public static final int CODE_ETAG_CATEGORY = 1500;
    public static final int CODE_ETAG_PRODUCT_FEATURE = 1501;
    public static final int CODE_ETAG_PRODUCT_RECOMMENDED = 1502;

    // http request method type
    public static final String UTF8 = "UTF-8";
    public static final String GET = "GET";
    public static final String POST = "POST";
    public static final String PUT = "PUT";
    public static final String DELETE = "DELETE";

    // server
//    public static final String SERVER_USER = BuildConfig.SERVER_USER;
//    public static final String SERVER_CUSTOMER = BuildConfig.SERVER_CUSTOMER;
//    public static final String SERVER_SUPPLIER = BuildConfig.SERVER_SUPPLIER;
//    public static final String SERVER_DRIVER = BuildConfig.SERVER_DRIVER;
//    public static final String SERVER_IMAGE = BuildConfig.SERVER_IMAGE;

    // request header
    public static final String HEADER_CONTENT_TYPE = "Content-Type";
    public static final String HEADER_AUTH_TOKEN = "auth-token";

    // request header - content type
    public static final String CONTENT_FORM_DATA = "form-data";
    public static final String CONTENT_TYPE_JSON = "application/json";

    // extra params
    public static final String DEFAULT_PARAM_LATLONG = "&latitude=%s&longitude=%s";
    public static final String DEFAULT_PARAM_LATLONG_MAP = "&map_lat=%s&map_long=%s";
    public static final String FROM = "?from=%s";
}
