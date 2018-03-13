package cpumonitor.ybdesire.com.androidcpumonitor;

import android.os.Build;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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

    public static double getCpuUsage(){
        double cupersent = 0;
        try {
            int[] cpu_usage_list = getCpuUsageStatistic();

            for (int i = 0; i < cpu_usage_list.length; i++) {
                cupersent += (double) cpu_usage_list[i] / 100;
            }
        }catch (NumberFormatException e)
        {
            cupersent=-1.234;
        }
        return cupersent;
    }

    private static int[] getCpuUsageStatistic() {

        String tempString = executeTop();

        tempString = tempString.replaceAll(",", "");
        tempString = tempString.replaceAll("User", "");
        tempString = tempString.replaceAll("System", "");
        tempString = tempString.replaceAll("IOW", "");
        tempString = tempString.replaceAll("IRQ", "");
        tempString = tempString.replaceAll("%", "");
        for (int i = 0; i < 10; i++) {
            tempString = tempString.replaceAll("  ", " ");
        }
        tempString = tempString.trim();
        String[] myString = tempString.split(" ");
        int[] cpuUsageAsInt = new int[myString.length];
        for (int i = 0; i < myString.length; i++) {
            myString[i] = myString[i].trim();
            cpuUsageAsInt[i] = Integer.parseInt(myString[i]);
        }
        return cpuUsageAsInt;
    }

    private static String executeTop() {
        java.lang.Process p = null;
        BufferedReader in = null;
        String returnString = null;
        try {
            p = Runtime.getRuntime().exec("top -n 1");
            in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while (returnString == null || returnString.contentEquals("")) {
                returnString = in.readLine();
            }
        } catch (IOException e) {
            Log.e("executeTop", "error in getting first line of top");
            e.printStackTrace();
        } finally {
            try {
                in.close();
                p.destroy();
            } catch (IOException e) {
                Log.e("executeTop",
                        "error in closing and destroying top process");
                e.printStackTrace();
            }
        }
        return returnString;
    }

}
