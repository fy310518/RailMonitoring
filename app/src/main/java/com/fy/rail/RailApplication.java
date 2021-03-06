package com.fy.rail;

import com.fy.baselibrary.application.BaseActivityLifecycleCallbacks;
import com.fy.baselibrary.ioc.ConfigUtils;
import com.fy.baselibrary.utils.L;
import com.fy.baselibrary.utils.ResUtils;
import com.fy.baselibrary.utils.ScreenUtils;

import org.litepal.LitePalApplication;

/**
 * 应用 Application
 * Created by fangs on 2018/10/23 10:39.
 */
public class RailApplication extends LitePalApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        new ConfigUtils.ConfigBiuder()
                .setBackImg(R.drawable.svg_arrow_left)
                .setBgColor(R.color.appHeadBg)
                .setTitleColor(R.color.titleBarCon)
                .setTitleCenter(true)
                .setCer(CER)
                .setBASE_URL("http://www.wanandroid.com/")
                .create(this);

        int designWidth = (int) ResUtils.getMetaData("Rudeness_Adapter_Screen_width", 0);
        L.e("designWidth", designWidth + "----");
        ScreenUtils.setCustomDensity(this, designWidth);

//        设置activity 生命周期回调
        registerActivityLifecycleCallbacks(new BaseActivityLifecycleCallbacks(designWidth));
    }




    public static String CER = "-----BEGIN CERTIFICATE-----\n" +
            "MIIDXzCCAkegAwIBAgIEMr+CgTANBgkqhkiG9w0BAQsFADBgMQwwCgYDVQQGEwMx\n" +
            "MjMxDDAKBgNVBAgTAzEyMzEMMAoGA1UEBxMDMTIzMQwwCgYDVQQKEwMxMjMxDDAK\n" +
            "BgNVBAsTAzEyMzEYMBYGA1UEAxMPMTkyLjE2OC4xMDAuMjUxMB4XDTE4MDcyMDAz\n" +
            "NTgyN1oXDTE4MTAxODAzNTgyN1owYDEMMAoGA1UEBhMDMTIzMQwwCgYDVQQIEwMx\n" +
            "MjMxDDAKBgNVBAcTAzEyMzEMMAoGA1UEChMDMTIzMQwwCgYDVQQLEwMxMjMxGDAW\n" +
            "BgNVBAMTDzE5Mi4xNjguMTAwLjI1MTCCASIwDQYJKoZIhvcNAQEBBQADggEPADCC\n" +
            "AQoCggEBAIzONpjYUosVfh1r0cUOGXRAZ8Pxjny4D/pEQp3H5yaocAznLcDc9BD+\n" +
            "hX4fnKeE/xz2MaRody08a1HjxFlVOld2exvunrC3ErjEBcxGjWyzM7+1ArCKp3j8\n" +
            "YyzVoFw6Qx6F0Xw2DLf5YF6MMAmQyBdxWO+YhnYoLG8/ZBYE7zRBcbsm9riZOfEJ\n" +
            "gW+1zQ6LUnTOZOk2eYm1NquqIiopzLKc+BH/S8hBAwL+lh5iKlOFJyGkW4U1HDCZ\n" +
            "2qsaMeCY2UJOoPr6y2/eUkAnjSVl4IvVMPEdTfD6XcxIsvGQhDtVGzEgY8w4unoO\n" +
            "jXk7qPiHUXbFQzx9e7Mgqx2lxrGhx8UCAwEAAaMhMB8wHQYDVR0OBBYEFBNg4twq\n" +
            "4jHnGW/5d+RKreimcBc5MA0GCSqGSIb3DQEBCwUAA4IBAQBytAlH8QiY5FqwcJ4+\n" +
            "UwiwppTNNm7V8QqkIbfC8jKW7EueObLSRHqYV7+yMG/SIyPg50Pc2aDHziukhQ2s\n" +
            "QmORK/xwXPZhW3iajeDQ23ejvvSeGEYap7jMPzDaoumGfzv8cjgMFX1CLhqKp8er\n" +
            "5RAxmF4uFGJqiS1nqvcV7D0iR39s2SLWllvcY4TGqGQ/KvfPh7zyjaqbavXQjR2/\n" +
            "m4TcBsGFi8LJioLbbEPL9ya8kbjGtRfJ/usXpzEypSMqPdwV3QfabKo0tegk71N7\n" +
            "I8OoatUrvA/JHA1iYHh1edwxWLvWJF3+FEzY779nrZWUQUwFInuhY3HnaaTJmChF\n" +
            "RHNT\n" +
            "-----END CERTIFICATE-----";
}
