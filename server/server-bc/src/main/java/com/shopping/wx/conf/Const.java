package com.shopping.wx.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class Const {

    public static String BASE_PATH;

    public static String RBLC_BASE_PATH;

    public static String APP_PATH;

    public static String LOGIN_SESSION_KEY = "wx_user";

    public static String PASSWORD_KEY = "@#$%^&*()OPG#$%^&*(HG";

    public static String DES3_KEY = "9964DYByKL967c3308imytCB";

    public static String default_logo = "img/logo.jpg";

    public static String userAgent = "Mozilla";

    public static String default_Profile = BASE_PATH + "/img/logo.jpg";

    public static String LAST_REFERER = "LAST_REFERER";

    public static int COOKIE_TIMEOUT = 30 * 24 * 60 * 60;


    @Bean
    public Boolean constInit( String basePath,
                             String rblcBasePath,
                             String appPath) {
        Const.BASE_PATH = basePath;
        Const.RBLC_BASE_PATH = rblcBasePath;
        Const.APP_PATH = appPath;
        return Boolean.TRUE;
    }
}
