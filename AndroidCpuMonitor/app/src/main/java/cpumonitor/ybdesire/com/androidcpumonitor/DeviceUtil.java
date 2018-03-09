package cpumonitor.ybdesire.com.androidcpumonitor;

import android.os.Build;

/**
 * Created by bin_yin on 2018/2/26.
 */

public class DeviceUtil {
    /*
    * such as HUAWEI
    * */
    public static String getManufacturer() {
        return Build.MANUFACTURER;
    }

    /*
    * such as HUAWEINXT-DL00
    * */
    public static String getDeviceType() {
        String model = Build.MODEL;
        if (model != null) {
            model = model.trim().replaceAll("\\s*", "");
        } else {
            model = "";
        }
        return model;
    }


    public static String getAndroidOSVersion()
    {
        String ver = Build.VERSION.RELEASE;
        return ver;
    }

}
