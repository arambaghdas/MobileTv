package train.apitrainclient.utils;

/**
 * StatusCodes holds different status code int values to denote multiple server API's and local statuses and errors.
 */

public class StatusCodes {
    //status codes set on our own choice
    public static final int ERROR_CODE_NETWORK_UNAVAILABLE            = 2221;
    public static final int ERROR_CODE_INVALID_INPUT                  = 2222;
    public static final int ERROR_CODE_REQUIRED_EMAIL                 = 2223;
    public static final int ERROR_CODE_REQUIRED_PASSWORD              = 2224;
    public static final int ERROR_CODE_INVALID_EMAIL                  = 2225;
    public static final int ERROR_CODE_NO_PENDING_ROUTINE             = 2226;
    public static final int ERROR_CODE_USER_NOT_LOGGED_IN             = 2227;
    public static final int ERROR_CODE_USER_ALREADY_LOGGED_IN         = 2228;
    public static final int ERROR_CODE_REQUIRED_FIRST_NAME            = 2229;
    public static final int ERROR_CODE_REQUIRED_SUR_NAME              = 2230;
    public static final int INVALID_PAGE_CALLED_TRAINING_LOG          = 2240;
}
