package ro.ubbcluj.mobileapp.dogdate.Utils;

import ro.ubbcluj.mobileapp.dogdate.service.UserService;

/**
 * Created by elega on 2018-01-13.
 */

public class ApiUtils {

    public static final String BASE_URL = "http://192.168.0.107:3000/";

    public static UserService getUserService() {
        return RetrofitClient.getClient(BASE_URL).create(UserService.class);
    }

}
